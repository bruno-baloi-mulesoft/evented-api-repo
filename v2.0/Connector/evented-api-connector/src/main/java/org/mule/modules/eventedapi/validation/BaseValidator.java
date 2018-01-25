package org.mule.modules.eventedapi.validation;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.messaging.ISubject;
import org.mule.modules.eventedapi.util.GeneralConstants;
import org.mule.modules.eventedapi.vo.EventVO;
import org.mule.modules.eventedapi.vo.PolicyVO;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BaseValidator implements IValidator
{
	
	private static Logger logger = LoggerFactory.getLogger(BaseValidator.class);
	
	protected ISubject _subject;

	@Override
	public void setSubject(ISubject pSubject) {
		// TODO Auto-generated method stub
		_subject = pSubject;
		
	}

	@Override
	public  abstract ValidationResponse validateEvent(Event pEvent, PolicyVO pPolicy,boolean inbound, String pDirection);
	
	
	protected EventVO constructEvent(String pEvent)
	{
		EventVO  retVal=null;
		
		if(pEvent.contains(GeneralConstants.eventPayloaJson))
		{
			retVal =  buildFromJSON(pEvent);
		}
		if(pEvent.contains(GeneralConstants.eventPayloadXml))
		{
			retVal =  buildFromXML(pEvent);
		}
		if(pEvent.contains(GeneralConstants.eventPayloadCsv))
		{
			retVal =  buildFromCSV(pEvent);
		}
		if(pEvent.contains(GeneralConstants.eventPayloadBin))
		{
			retVal =  buildFromBinary(pEvent);
		}
		
		return retVal;
	}
	
	private EventVO buildFromJSON(String pEvent)
	{
		String _eventId=null ;
		String _eventType=null;
		String _eventDomain=null;
		String _eventCategory=null;
		String _eventPayloadType=null;
		String _eventPayload=null;
		
		
		EventVO retVal=null;
		try
		{
			
			JSONObject _event = new JSONObject(pEvent);
			if(_event.has(GeneralConstants.eventId))
				_eventId = _event.getString(GeneralConstants.eventId);
			if(_event.has(GeneralConstants.eventType))		
			  _eventType = _event.getString(GeneralConstants.eventType);
			if(_event.has(GeneralConstants.eventDomain))
			  _eventDomain = _event.getString(GeneralConstants.eventDomain);
			if(_event.has(GeneralConstants.eventCategory))
				_eventCategory = _event.getString(GeneralConstants.eventCategory);
			if(_event.has(GeneralConstants.eventPayloadType))
				_eventPayloadType = _event.getString(GeneralConstants.eventPayloadType);
			if(_event.has(GeneralConstants.eventPayload))
			{
				JSONObject _payload  =  _event.getJSONObject(GeneralConstants.eventPayload);
				_eventPayload =  _payload.toString();
			}
				
			
			
			logger.info("---Parsed Event: eventId="+_eventId+",eventType="+_eventType+",eventDomain="+_eventDomain+",eventCategory="+
						_eventCategory+",payloadType="+_eventPayloadType+",payload="+_eventPayload);
			EventVO _eVo = new EventVO();
			_eVo.setEventId(_eventId);
			_eVo.setEventType(_eventType);
			_eVo.setEventDomain(_eventDomain);
			_eVo.setEventCategory(_eventCategory);
			_eVo.setEventPayloadType(_eventPayloadType);
			_eVo.setEventPayload(_eventPayload);
			retVal = _eVo;
		
			
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
			
		return retVal;
	}
	
	private EventVO buildFromXML(String pEvent)
	{
		return null;
	}
	private EventVO buildFromCSV(String pEvent)
	{
		return null;
	}
	private EventVO buildFromBinary(String pEvent)
	{
		return null;
	}
	
}
