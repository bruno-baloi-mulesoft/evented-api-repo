package org.mule.modules.eventedapi.validation;

import java.util.Iterator;
import java.util.List;

import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.messaging.SubjectController;
import org.mule.modules.eventedapi.util.GeneralConstants;
import org.mule.modules.eventedapi.vo.EventVO;
import org.mule.modules.eventedapi.vo.PolicyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class structureValidator extends BaseValidator
{
	private static Logger logger = LoggerFactory.getLogger(structureValidator.class);
	private String _permitMessage=null;
	private String _structMessage=null;
	
	@Override
	public ValidationResponse validateEvent(Event pEvent, PolicyVO pPolicy,boolean inbound, String pDirection) {
		// TODO Auto-generated method stub
		
		 _permitMessage=null;
		 _structMessage=null;
		
		ValidationResponse  _retVal = new ValidationResponse();
		String _policyType = pPolicy.getPolicyType();
		String _criteria =  pPolicy.getEnforcementCriteria();
		
		if(_policyType.equals(GeneralConstants.policyStructure) && _criteria.equals("true"))
		{
			logger.info(" --^--Policy (inbound="+inbound+") : "+pPolicy.getPolicyName() +", type="+pPolicy.getPolicyType()+", validating the structure of Event:"+pEvent.getEventId());
			List _eventList = _subject.getSupportedEventList();
			String _eventPayload = (String) pEvent.getMessagePayload();
			
			
			EventVO _rvo = constructEvent(_eventPayload);
			//validate that the Event has al the nodes
			//-----1) we do this NOW via text search 
			//-----2)  TO - D: use SHACL to validate the strture of the entire Event node
			
			boolean evStructure = isValidStrutureBase(_rvo);
			//boolean evStructure = isValidStrutureAmf(_rvo);
			if(!evStructure)
			{
				_retVal.setValid(false);
				_retVal.setValidationMessage(_structMessage);
				return _retVal;
				
			}
			boolean evPermitted = isPermittedEvent(_rvo,_eventList);
			if(!evPermitted)
			{
				_retVal.setValid(false);
				_retVal.setValidationMessage(_permitMessage);
				return _retVal;
			}
			
			
				_retVal.setValid(true);
				_retVal.setValidationMessage(" ### Event "+pEvent.getEventId() +" is structurally valid !");
			
		}
		else
		{
			_retVal.setValid(true);
			_retVal.setValidationMessage(" ### Policy "+_policyType+" does not apply to Event "+pEvent.getEventId() );
		}
		
		return _retVal;
	}
	
	private boolean validateViaSHACL(String pEventPayload)
	{
		boolean retVal=true;
		
		
		
		return retVal;
	}
	private boolean isPermittedEvent(EventVO pLiveEvent, List pPermittedEventList)
	{
		boolean retVal = false;
		boolean finished=true;
	
		
		Iterator _it = pPermittedEventList.iterator();
		while(_it.hasNext())
		{
			EventVO _vo = (EventVO) _it.next();
			
			//logger.info("++++Incoming Event:"+pLiveEvent+", permittedEvent="+_vo);
			
			if ( pLiveEvent.getEventCategory().equals(_vo.getEventCategory()) && 
					pLiveEvent.getEventDomain().equals(_vo.getEventDomain()) && pLiveEvent.getEventType().equals(_vo.getEventType()) )
			{
				logger.info(" ### Event "+pLiveEvent.getEventId() +" of type="+pLiveEvent.getEventType()+" is permitted !");
				_permitMessage = " ### Event "+pLiveEvent.getEventId() +" of type="+pLiveEvent.getEventType()+" is permitted !";
				retVal=true;
				return retVal;
			}
		}
		retVal=false;
		_permitMessage = " ### Event "+pLiveEvent.getEventId() +" of type="+pLiveEvent.getEventType()+" does not exist in the list of Permitted Events !";
		
		return retVal;
	}
	
	private boolean isValidStrutureBase(EventVO pVo)
	{
		boolean retVal=true;
	
		
		if(pVo.getEventId()==null || pVo.getEventCategory()==null || pVo.getEventDomain()==null ||  pVo.getEventPayloadType() ==null ||
				pVo.getEventType() ==null || pVo.getEventPayload()==null)	
		{
			_structMessage = " ### Event "+pVo.getEventId() +" of type="+pVo.getEventType()+" is NOT permitted  - INVALID STRUCTURE!";
			logger.info(" ### Event "+pVo.getEventId() +" of type="+pVo.getEventType()+" is NOT permitted  - INVALID STRUCTURE!");
			retVal=false;
		}
		
		
		return retVal;
	}
	private boolean isValidStruturAMF(EventVO pVo)
	{
		//this is where we use the AMF Validatio framework for the struture.
		
		return false;
	}

}
