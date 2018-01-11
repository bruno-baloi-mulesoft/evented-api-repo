package org.mule.modules.eventedapi.messaging;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;

import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.util.AmfConstants;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.mule.modules.eventedapi.vo.ConsumerVO;
import org.mule.modules.eventedapi.vo.EventedAPIConnectorConfigVO;
import org.mule.modules.eventedapi.vo.ProducerVO;
import org.mule.modules.eventedapi.vo.SubjectVO;
import org.mule.modules.eventedapi.vo.TransportVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageInfrastructureBuilder
{
	private static Logger logger = LoggerFactory.getLogger(MessageInfrastructureBuilder.class);
	private static MessageInfrastructureBuilder instance=null;
	
	private Producer producer=null;
	private Consumer consumer=null;
	
	private HashMap  completeSubjectMap = new HashMap();
	
	
	private EventedApiConnector _connector;
	
	public static MessageInfrastructureBuilder getInstance()
	{
		if(instance==null)
			instance = new MessageInfrastructureBuilder();
		return instance;
	}
	public void setupMsgInfra(EventedAPIConnectorConfigVO pConfigVo, EventedApiConnector pConnector)
	{
		_connector=pConnector;
		
		logger.info("EventedApiConnector: "+ _connector);
		
		//1. Setup up Message Broker by 
		
		if(pConfigVo.getPersona().equals(AmfConstants.PERSONA_PRODUCER))
		{
			buildProducer(pConfigVo.getProducer());
		}
		if(pConfigVo.getPersona().equals(AmfConstants.PERSONA_CONSUMER))
		{
			buildConsumer(pConfigVo.getConsumer());
		}
		if(pConfigVo.getPersona().equals(AmfConstants.PERSONA_ALL))
		{
			buildProducer(pConfigVo.getProducer());
			buildConsumer(pConfigVo.getConsumer());
		}
	}
	private void buildProducer(ProducerVO pVo)
	{
		
		producer =new Producer();
		producer.setProducerVo(pVo);
		
		if(validateSubjectList(pVo.getChannel().getSubjectList(),pVo.getSubjectList()))
		{
			logger.info("Producer Subject List is Valid !");
			processSubjects(pVo.getSubjectList(),true);
			
		}
		else
		{
			logger.error("Producer Subject List is invalid !");
		}
			
		
	}
	private void buildConsumer(ConsumerVO pVo)
	{
		consumer = new Consumer();
		consumer.setConsumerVo(pVo);
		if(validateSubjectList(pVo.getChannel().getSubjectList(),pVo.getSubjectList()))
		{
			logger.info("Consumer Subject List is Valid !");
			processSubjects(pVo.getSubjectList(),false);
		}
		else
		{
			logger.error("Consumer Subject List is invalid !");
		}
	}
	
	private boolean validateSubjectList(List pRefList, List pActualList)
	{
		
		boolean _retVal = true;
		
		Iterator _it = pActualList.iterator();
		
		
		while(_it.hasNext())
		{
			SubjectVO _vo = (SubjectVO) _it.next();
			
			String _subjId = _vo.getSubjectId();
			String _subjName = _vo.getSubjectName();
			
			Iterator _refIt = pRefList.iterator();
			
			boolean _exists=false;
			while(_refIt.hasNext())
			{
				SubjectVO _refVo = (SubjectVO) _refIt.next();
				if(_subjId.equals(_refVo.getSubjectId()) && _subjName.equals(_refVo.getSubjectName()))
				{
					_exists=true;
					break;
				}
				
			}
			if(! _exists)
			{
				_retVal = false;
				break;
			}
			
		}
		
		return _retVal;
	}

	private void processSubjects(List pSubjList,boolean pProducer)
	{
			Iterator _it = pSubjList.iterator();
			while(_it.hasNext())
			{
				SubjectVO _vo = (SubjectVO) _it.next();
				
				String _subjId = _vo.getSubjectId();
				String _pattern = _vo.getEventPattern();
				String _subjName = _vo.getSubjectName();
				List _eventList = _vo.getSupportedEventList();
				List _transportList = _vo.getTransportList();
				
				String _key = _subjName;
				ISubject _subj= new SubjectController();
				
				if( ! completeSubjectMap.containsKey(_key))
				{
					//process subject
					
					List _tList = constructTransports(_subjId,_subjName,_pattern,_transportList,_subj);
					//_subj.addTransports(_tList);
					_subj.startTransports();
					
					completeSubjectMap.put(_key, _subj);
					
				}
				if(pProducer)
					producer.setProdSubjMap(completeSubjectMap);
				else
					consumer.setConsumerSubjMap(completeSubjectMap);
				
			}
	}
	
	private List constructTransports(String pSubjId, String pSubjName, String pPattern,List pTransports, ISubject pSubject)
	{
		ArrayList _tList = new ArrayList();
		Iterator _tIt = pTransports.iterator();
		
		while(_tIt.hasNext())
		{
			TransportVO _vo = (TransportVO) _tIt.next();
			
			ITransport _t = MessagingTransportFactory.getInstance().createTransport(pSubjId, pSubjName, pPattern,_vo,pSubject);
			pSubject.addTransportReference(_vo.getTransportName(), _t);
			
			_tList.add(_t);
		}
		
		
		return _tList;
		
	}
	
	private List constructEvents(List pEventList)
	{
		return null;
	}
	public Producer getProducer() {
		return producer;
	}
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	public HashMap getCompleteSubjectMap() {
		return completeSubjectMap;
	}
	public void setCompleteSubjectMap(HashMap subjectMap) {
		this.completeSubjectMap = subjectMap;
	}
	
}
