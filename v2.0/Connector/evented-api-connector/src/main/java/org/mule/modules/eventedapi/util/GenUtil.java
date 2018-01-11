package org.mule.modules.eventedapi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.mule.modules.eventedapi.vo.ChannelVO;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.mule.modules.eventedapi.vo.ConsumerVO;
import org.mule.modules.eventedapi.vo.EventVO;
import org.mule.modules.eventedapi.vo.EventedAPIConnectorConfigVO;
import org.mule.modules.eventedapi.vo.ProducerVO;
import org.mule.modules.eventedapi.vo.SubjectVO;
import org.mule.modules.eventedapi.vo.TransportVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenUtil 
{
	
	private static Logger logger = LoggerFactory.getLogger(GenUtil.class);
	
	private static GenUtil instance=null;
	
	private Properties connectorProps = new Properties();
	
	public static GenUtil getInstance()
	{
		if(instance==null)
			instance = new GenUtil();
		
		return instance;
	}
	
	public void printConnctorConfig(EventedAPIConnectorConfigVO pConfig)
	{
		logger.info("*************** Evented API Conector Config ************");
		logger.info("API Persona: "+ pConfig.getPersona());
		printProducer(pConfig.getProducer());
		printConsumer(pConfig.getConsumer());
		
		
	}
	
	private void printProducer(ProducerVO pVo)
	{
		logger.info("***Producer");
		
		logger.info(" Producer ID: "+ pVo.getProducerId());
		logger.info(" Prodcuer NS: "+ pVo.getProducerNS());
		logger.info(" Producer Type: "+ pVo.getProducerType());
		logger.info(" Producer Name: "+ pVo.getProducerName());
		logger.info(" Producer App ID: "+pVo.getApplicationId());
		logger.info(" Producer User ID: "+ pVo.getUserId());
		
		printSubjects(pVo.getSubjectList());
		printChannels(pVo.getChannel());
		
		
	}
	private void printConsumer(ConsumerVO pVo)
	{

		logger.info("*****Consumer");
		
		logger.info(" Consumer ID: "+ pVo.getConsumerId());
		logger.info(" Consumer NS: "+ pVo.getConsumerNS());
		logger.info(" Consumer Type: "+ pVo.getConsumerType());
		logger.info(" Consumer Name: "+ pVo.getConsumerName());
		logger.info(" Consumer App ID: "+pVo.getApplicationId());
		logger.info(" Consumer User ID: "+ pVo.getUserId());
		printSubjects(pVo.getSubjectList());
		printChannels(pVo.getChannel());
		
		
	}
	
	private void printSubjects(List pSubjList)
	{
		logger.info("	-----Allowed subjects------");
		Iterator _subjIt = pSubjList.iterator();
		int _subjI = 1;
		while(_subjIt.hasNext())
		{
			SubjectVO _subjVo = (SubjectVO) _subjIt.next();
			logger.info("   ---Subject: "+_subjI+" ----");
			logger.info("	Subject ID:"+_subjVo.getSubjectId());
			logger.info("	Subject NS:" + _subjVo.getSubjectNS());
			logger.info("	Subject Name: "+ _subjVo.getSubjectName());
			logger.info("	Subject Type"+ _subjVo.getSubjectType());
			logger.info("	SUbject Event Pattern: "+ _subjVo.getEventPattern());
			
			List _evList = _subjVo.getSupportedEventList();
			logger.info("	----Permitted Events-----");
			Iterator _evIt = _evList.iterator();
			while(_evIt.hasNext())
			{
				EventVO _evo = (EventVO) _evIt.next();
				logger.info("	Event ID: " + _evo.getEventId());
				logger.info("	Event Name: " + _evo.getEventName());
				logger.info("	Event NS: "+ _evo.getEventNS());
				logger.info("	Event Type: " + _evo.getEventType());
				logger.info("	Event Category: " + _evo.getEventCategory());
				logger.info("	Event Domain: "+ _evo.getEventDomain());
				logger.info("	Event Payload Type: "+ _evo.getEventPayloadType());
				
			}
			
			
			logger.info("	----Permitted Transports-----");
			List _transportList = _subjVo.getTransportList();
			Iterator _tit =  _transportList.iterator();
			while(_tit.hasNext())
			{
				TransportVO _tvo = (TransportVO) _tit.next();
				
				logger.info("	Transport ID: " + _tvo.getTransportId());
				logger.info("	Transport Name: " + _tvo.getTransportName());
				logger.info("	Transport NS: "+_tvo.getTransportNS());
				logger.info("	Transport Type: " + _tvo.getTransportType());
				logger.info("	Transport Vendor: " + _tvo.getTransportVendor());
				ConnectionVO _con = _tvo.getConnection();
				logger.info("	-----Connection----");
					logger.info("	Connection ID: "+_con.getConnectionId());
					logger.info("	Connection NS: " + _con.getConnectionNS());
					logger.info("	Connection Host: "+_con.getHost());
					logger.info("	Connection Port: "+_con.getPort());
					logger.info("	Connection User: "+_con.getUser());
					logger.info("	Connection Password: "+_con.getPassword());
			}

			_subjI++;
			
		}
		
	}
	private void printChannels(ChannelVO pVo)
	{
		logger.info("	-----Channel------");
		
		logger.info("	Channel ID: "+ pVo.getChannelId());
		logger.info("	Channel Name: "+pVo.getChannelName());
		logger.info("	Channel NS: " + pVo.getChannelNS());
		logger.info("	Channel Type: " + pVo.getChannelType());
		printSubjects (pVo.getSubjectList());
		
	}

	public String genEventUUID()
	{
		 UUID idOne = UUID.randomUUID();
		 
		 return idOne.toString();
	}
	
	public void loadConnectorProperties(String pPath)
	{
		InputStream input = null;

		try {

			input = new FileInputStream(pPath);
			// load a properties file
			connectorProps.load(input);


		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (input != null) 
			{
				try 
				{
					input.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	public Properties getConnectorProps() {
		return connectorProps;
	}
	

}
