package org.mule.modules.eventedapi.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.util.GeneralConstants;
import org.mule.modules.eventedapi.vo.EventVO;
import org.mule.modules.eventedapi.vo.PolicyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class patternValidator extends BaseValidator
{

	private static Logger logger = LoggerFactory.getLogger(patternValidator.class);
	
	@Override
	public ValidationResponse  validateEvent(Event pEvent, PolicyVO pPolicy, boolean inbound, String pDirection) {
		// TODO Auto-generated method stub
		boolean validated = true;
		SearchEngine _se = new SearchEngine();
		
		ValidationResponse  _retVal = new ValidationResponse();
		String _policyType = pPolicy.getPolicyType();
		String _criteria =  pPolicy.getEnforcementCriteria();
		EventVO _rvo = constructEvent((String)pEvent.getMessagePayload());
		
		if(_policyType.equals(GeneralConstants.policyPattern))
		{
			logger.info(" --^--Policy (inbound="+inbound+") : "+pPolicy.getPolicyName() +", type="+pPolicy.getPolicyType()+", validating the pattern of Event:"+pEvent.getEventId());
			List _eventList = _subject.getSupportedEventList();
			String _eventPayload =  _rvo.getEventPayload();
			List <String> _patterns = parsePatterns(_criteria);
			
			
			Iterator _pIt = _patterns.iterator();
			
			while(_pIt.hasNext())
			{
				try
				{
					String _query = (String) _pIt.next();
					long numHits = _se.searchText(_query,"PATTERN_POLICY", _eventPayload);
					
					logger.info(" *** Found : "+numHits+ " of '"+_query+"'");
					if(numHits==0)
					{
						//logger.error(" ### Pattern Policy violated !");
						_retVal.setValid(false);
						_retVal.setValidationMessage(" ### Pattern Policy violated for pattern: "+ _query +"...!");
						validated = false;
						break;
					}
				}
				catch(Exception excp)
				{
					excp.printStackTrace();
				}
			}

			
			if(validated)
			{
				_retVal.setValid(true);
				_retVal.setValidationMessage(" ### Event id="+pEvent.getEventId()+" has been validated against pattern: "+_criteria);
			}
			
			
			
		}
		
		_se.close();
		
		return _retVal;
	}
	
	private List parsePatterns(String pCriteria) 
	{
		ArrayList _patternList = new ArrayList();
		
		StringTokenizer _tokenizer = new StringTokenizer(pCriteria,",");
		
		while(_tokenizer.hasMoreTokens())
		{
			String _token = _tokenizer.nextToken();
			_patternList.add(_token);
		}
		
		return _patternList;
	}

}
