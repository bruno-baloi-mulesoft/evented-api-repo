package org.mule.modules.eventedapi.parser;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.mule.modules.eventedapi.util.AmfParseUtil;
import org.mule.modules.eventedapi.util.AmfConstants;
import org.mule.modules.eventedapi.vo.ChannelVO;
import org.mule.modules.eventedapi.vo.ConsumerVO;
import org.mule.modules.eventedapi.vo.ProducerVO;
import org.mule.modules.eventedapi.vo.SubjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.utils.Obj;

public class AmfEncodingParser
{
	
	private static Logger logger = LoggerFactory.getLogger(AmfEncodingParser.class); 
	
	public HashMap processCoreDoc(ArrayList pDoc)
	{
		//logger.info("+++Processing Core RAML Doc / Encoding !");
		
		HashMap _docMap = new HashMap();
		
		try
		{
			Iterator _valIt2 = pDoc.iterator();
			while(_valIt2.hasNext())
			{
				Object _valObj2 = _valIt2.next();
				
					
				if(_valObj2 instanceof LinkedHashMap)
				{
					LinkedHashMap _map = (LinkedHashMap) _valObj2;
					
					
					Set _keySet = _map.keySet();
					Set _entrySet = _map.entrySet();
					Iterator _keyIt = _keySet.iterator();
					while(_keyIt.hasNext())
					{
						String _ko = (String) _keyIt.next();
						//logger.info("Encoding Key:"+_ko+",Value class:"+_map.get(_ko).getClass()+",value="+_map.get(_ko));
						

					}
					
					String _apiPersona =(String) ((HashMap) ((ArrayList) _map.get(AmfConstants.API_PERSONA)).get(0)).get(AmfConstants.VALUE);
					//logger.info("API Persona= " + _apiPersona);
					ArrayList<SubjectVO> _subjectVoList = processSubjects((ArrayList)_map.get(AmfConstants.SUBJECTS_NS));
					ChannelVO _channelVo = processChannels((ArrayList)_map.get(AmfConstants.CHANNELS_NS));
					ProducerVO _producerVo = processProducer((ArrayList) _map.get(AmfConstants.PRODUCERS_NS));
					ConsumerVO _consumerVo = processConsumer((ArrayList) _map.get(AmfConstants.CONSUMERS_NS));
					ProducerVO _producerVo1 = processEventProducer((ArrayList) _map.get(AmfConstants.EVENT_PRODUCER));
					ConsumerVO _consumerVo1 = processEventConsumer((ArrayList) _map.get(AmfConstants.EVENT_CONSUMER));
					
					_docMap.put(AmfConstants.PERSONA_REF, _apiPersona);
					_docMap.put(AmfConstants.SUBJECT_REF, _subjectVoList);
					_docMap.put(AmfConstants.CHANNEL_REF, _channelVo);
					_docMap.put(AmfConstants.PRODUCER_REF, _producerVo);
					_docMap.put(AmfConstants.CONSUMER_REF, _consumerVo);
					_docMap.put(AmfConstants.PRODUCER_REF_EVENT, _producerVo1);
					_docMap.put(AmfConstants.CONSUMER_REF_EVENT, _consumerVo1);
					
				}
			}
				
		
			
		}
		catch(Exception pExcp)
		{
			pExcp.printStackTrace();
		}
		return _docMap;
	}
	
	
	//==============Channels=========================
	
	
	private ChannelVO processChannels(ArrayList pChannelList)
	{
		ChannelVO _retVo = new ChannelVO();
		
		//logger.info("----Processing Channels----");

		Iterator _kit  = pChannelList.iterator();
		
		while(_kit.hasNext())
		{
			LinkedHashMap _objMap =(LinkedHashMap ) _kit.next();
			
			Set _keySet=_objMap.keySet();
			Iterator _chit = _keySet.iterator();
			
			while(_chit.hasNext())
			{
				String _key = (String) _chit.next(); 
				//logger.info("Channel Key="+_key+", value class="+_objMap.get(_key).getClass()+", value="+_objMap.get(_key));
			}
			
			String _chNS = (String) _objMap.get(AmfConstants.ID);
			String _chType = (String) ((ArrayList) _objMap.get(AmfConstants.TYPE)).get(0);
			
			String _chID = (String) ((HashMap)(((ArrayList) _objMap.get(AmfConstants.CHANNEL_ID_NS)).get(0))).get(AmfConstants.VALUE);
			String _chName = (String) ((HashMap)(((ArrayList) _objMap.get(AmfConstants.EVENT_NAME)).get(0))).get(AmfConstants.VALUE);
				
			
			ArrayList _subjList = (ArrayList) _objMap.get(AmfConstants.EVENT_SUBJECT);			
			
			_retVo.setChannelId(_chID);
			_retVo.setChannelName(_chName);
			_retVo.setChannelNS(_chNS);
			_retVo.setChannelType(_chType);
			_retVo.setSubjectList(processSubjects(_subjList));
			
		}
		
		
		return _retVo;
		
	}
	
	//==============Subjects=========================
	
	
	private ArrayList <SubjectVO> processSubjects(ArrayList pSubjList)
	{
		ArrayList<SubjectVO> _subjList = new ArrayList();
		
		Iterator _subjIt = pSubjList.iterator();
		
		//logger.info("-----Processing Subjects:"+pSubjList.size() + "-------");
		
		
		while(_subjIt.hasNext())
		{
			// read all Subject declarations
			Object _obj = _subjIt.next();
			
			
			if(_obj instanceof LinkedHashMap)
			{
				LinkedHashMap _map = (LinkedHashMap) _obj;
				
				/*
				Set _keySet = _map.keySet();
				Iterator _kit = _keySet.iterator();
				while(_kit.hasNext())
				{
					String _key = (String) _kit.next();
					System.out.println("Subject Key:"+_key+", value class:"+ _map.get(_key).getClass()+", value:"+_map.get(_key));
					
				}
				*/
				
				String _sNS = (String) _map.get(AmfConstants.ID);
				String _sType =  (String) ((ArrayList) _map.get(AmfConstants.TYPE)).get(0);
			
				String _subjId = (String) ((HashMap) ((ArrayList) _map.get(AmfConstants.SUBJECT_ID_NS)).get(0)).get(AmfConstants.VALUE);
				String _subjPattern = (String) ((HashMap) ((ArrayList) _map.get(AmfConstants.SUBJECT_PATTERN_NS)).get(0)).get(AmfConstants.VALUE);
				String _subjName =(String) ((HashMap) ((ArrayList) _map.get(AmfConstants.EVENT_NAME)).get(0)).get(AmfConstants.VALUE);
				
				
				List eventTransportList = processEventTransports((ArrayList) _map.get(AmfConstants.EVENT_TRANSPORT));
				List permittedEvetsList = processPermittedEvents((ArrayList) _map.get(AmfConstants.BASE_EVENT_SUBJECT));
				
				
				SubjectVO _subjVo = new SubjectVO();
				
				_subjVo.setEventPattern(_subjPattern);
				_subjVo.setSubjectId(_subjId);
				_subjVo.setSubjectNS(_sNS);
				_subjVo.setSubjectType(_sType);
				_subjVo.setSubjectName(_subjName);
				_subjVo.setSupportedEventList(permittedEvetsList);
				_subjVo.setTransportList(eventTransportList);
			
				
				//logger.info("###Subject ID:"+_subjVo.getSubjectId()+", Subj Name: "+_subjVo.getSubjectName()+", pattern: "+_subjVo.getEventPattern());
				
				_subjList.add(_subjVo);
			
				
			}
		}
	
		return _subjList;
	}
	
	private List processEventTransports(ArrayList pTransportList)
	{
		
		return AmfReferencesParser.processTransports(pTransportList);
		
	}
	
	private List processPermittedEvents(ArrayList pEventList)
	{
		return AmfReferencesParser.processEvents(pEventList);
		
	}
	private ProducerVO processProducer(ArrayList pProdList)
	{
		//logger.info("----Processing Producer: "+pProdList.size()+"-----");
		ProducerVO _producerVo = new ProducerVO();
		
		Iterator _pIt = pProdList.iterator();
		while(_pIt.hasNext())
		{
			Object _obj = _pIt.next();
				
			if(_obj instanceof LinkedHashMap)
			{
				LinkedHashMap _prodMap = (LinkedHashMap) _obj;
				
				String _prodNS = (String) _prodMap.get(AmfConstants.ID);
				String _prodType =  (String) ((ArrayList) _prodMap.get(AmfConstants.TYPE)).get(0);
			
				String _appId = (String) ((HashMap) ((ArrayList) _prodMap.get(AmfConstants.APPLICATION_ID)).get(0)).get(AmfConstants.VALUE);
				String _prodId = (String) ((HashMap) ((ArrayList) _prodMap.get(AmfConstants.PRODUCER_ID)).get(0)).get(AmfConstants.VALUE);
				String _userId = (String) ((HashMap) ((ArrayList) _prodMap.get(AmfConstants.USER_ID)).get(0)).get(AmfConstants.VALUE);
				String _prodName =(String) ((HashMap) ((ArrayList) _prodMap.get(AmfConstants.EVENT_NAME)).get(0)).get(AmfConstants.VALUE);
				
				
				ArrayList _prodChList = (ArrayList) _prodMap.get(AmfConstants.EVENT_CHANNEL);
				ArrayList _prodSubjList = (ArrayList) _prodMap.get(AmfConstants.EVENT_SUBJECT);
				
				ChannelVO _chVo = processChannels(_prodChList);
				ArrayList <SubjectVO> _subjVoList = processSubjects(_prodSubjList);
				
				/*
				LinkedHashMap _prodChannelMap =  (LinkedHashMap) ((ArrayList) _prodMap.get(AmfConstants.EVENT_CHANNEL)).get(0);
				LinkedHashMap _prodSubjMap = (LinkedHashMap) ((ArrayList) _prodMap.get(AmfConstants.EVENT_SUBJECT)).get(0);
				Set _prodChKeySet = _prodChannelMap.keySet();
				Set _prodSubjKeySet = _prodSubjMap.keySet();
				Iterator _prodChIt = _prodChKeySet.iterator();
				while(_prodChIt.hasNext())
				{
					String  _chKey = (String) _prodChIt.next();
					System.out.println("Prod Channel Key: :"+_chKey+", value class="+_prodChannelMap.get(_chKey).getClass()+", value = "+_prodChannelMap.get(_chKey));
					
				}
				Iterator _prodSubjIt =_prodSubjKeySet.iterator();
				while(_prodSubjIt.hasNext())
				{
					String _subjKey =(String) _prodSubjIt.next();
					System.out.println("Prod Subjects Key:"+_subjKey+", value class ="+_prodSubjMap.get(_subjKey).getClass()+", value="+_prodSubjMap.get(_subjKey));
					
				}
				*/
				
				/*
				Set _keySet = _prodMap.keySet();
				Iterator _it = _keySet.iterator();
				while(_it.hasNext())
				{
					String _key = (String) _it.next();
					System.out.println("Producer key:"+_key+", value class="+_prodMap.get(_key).getClass()+", value= "+_prodMap.get(_key));
				
				}
				*/
				
				_producerVo.setApplicationId(_appId);
				_producerVo.setProducerId(_prodId);
				_producerVo.setProducerNS(_prodNS);
				_producerVo.setProducerType(_prodType);
				_producerVo.setProducerName(_prodName);
				_producerVo.setUserId(_userId);
				_producerVo.setChannel(_chVo);
				_producerVo.setSubjectList(_subjVoList);
				
				//logger.info("-ProducerID="+_producerVo.getProducerId()+", UserId="+_producerVo.getUserId()+", ApplicationID="+_producerVo.getApplicationId());
			}
		}
		
		
		return _producerVo;
		
	}
	
	private ConsumerVO processConsumer(ArrayList pConsList)
	{
		ConsumerVO _consumerVo = new ConsumerVO();
		
		//logger.info("----Processing Consumer-----");
		Iterator _pIt = pConsList.iterator();
		while(_pIt.hasNext())
		{
			Object _obj = _pIt.next();
			
			if(_obj instanceof LinkedHashMap)
			{
				LinkedHashMap _consMap = (LinkedHashMap) _obj;
				
				String _consNS = (String) _consMap.get(AmfConstants.ID);
				String _consType =  (String) ((ArrayList) _consMap.get(AmfConstants.TYPE)).get(0);
			
				String _appId = (String) ((HashMap) ((ArrayList) _consMap.get(AmfConstants.APPLICATION_ID)).get(0)).get(AmfConstants.VALUE);
				String _consId = (String) ((HashMap) ((ArrayList) _consMap.get(AmfConstants.CONSUMER_ID)).get(0)).get(AmfConstants.VALUE);
				String _userId = (String) ((HashMap) ((ArrayList) _consMap.get(AmfConstants.USER_ID)).get(0)).get(AmfConstants.VALUE);
				String _consName =(String) ((HashMap) ((ArrayList) _consMap.get(AmfConstants.EVENT_NAME)).get(0)).get(AmfConstants.VALUE);
				
				ArrayList _consChannel = (ArrayList) _consMap.get(AmfConstants.EVENT_CHANNEL);
				ArrayList _consSubjects = (ArrayList) _consMap.get(AmfConstants.EVENT_SUBJECT);
				

				ChannelVO _chVo = processChannels(_consChannel);
				ArrayList <SubjectVO> _subjVoList = processSubjects(_consSubjects);
				
				/*
				Set _keySet = _consMap.keySet();
				Iterator _it = _keySet.iterator();
				while(_it.hasNext())
				{
					String _key = (String) _it.next();
					System.out.println("Consumer key:"+_key+", value class="+_consMap.get(_key).getClass()+", value= "+_consMap.get(_key));
				
				}
				*/
				
				_consumerVo.setApplicationId(_appId);
				_consumerVo.setConsumerId(_consId);
				_consumerVo.setConsumerNS(_consNS);
				_consumerVo.setConsumerType(_consType);
				_consumerVo.setUserId(_userId);
				_consumerVo.setChannel(_chVo);
				_consumerVo.setSubjectList(_subjVoList);
				
				//logger.info("-ConsumerID="+_consumerVo.getConsumerId()+", UserId="+_consumerVo.getUserId()+", ApplicationID="+_consumerVo.getApplicationId());
				
			}
			
			
		}
		
		
		return _consumerVo;
	}
	
	private ProducerVO processEventProducer(ArrayList pProdList)
	{
		//logger.info("======Processing Event Producer: "+pProdList.size()+"======");
		//ProducerVO _producerVo = new ProducerVO();
		ProducerVO _producerVo = processProducer(pProdList);
		
		/*
		Iterator _pIt = pProdList.iterator();
		while(_pIt.hasNext())
		{
			Object _obj = _pIt.next();
			System.out.println("Event Producer Obj class="+_obj.getClass()+", value="+_obj);
			
			if(_obj instanceof LinkedHashMap)
			{
				LinkedHashMap _prodMap = (LinkedHashMap) _obj;
				Set _keySet = _prodMap.keySet();
				Iterator _it = _keySet.iterator();
				while(_it.hasNext())
				{
					String _key = (String) _it.next();
					System.out.println("Event Producer key:"+_key+", value class="+_prodMap.get(_key).getClass()+", value= "+_prodMap.get(_key));
				
				}
				
			}
		
		
		}
		*/
		
		return _producerVo;
	}
	private ConsumerVO processEventConsumer(ArrayList pConsList)
	{
		logger.info("======Processing Event Consumer: "+pConsList.size()+"======");
		ConsumerVO _consVo = processConsumer(pConsList);

		return _consVo;
		
	}


}
