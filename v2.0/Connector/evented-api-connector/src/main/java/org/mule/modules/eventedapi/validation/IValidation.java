package org.mule.modules.eventedapi.validation;

import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.vo.PolicyVO;

public interface IValidation 
{
	public boolean validateEventStructure(Event pEvent, PolicyVO pPolicy);
	public boolean validateEventPattern(Event pEvent,PolicyVO pPolicy);
	public boolean validateEventShape(Event pEvent,PolicyVO pPolicy);
	public boolean validateEventCompliance(Event pEvent,PolicyVO pPolicy);
	public boolean validateACL(Event pEvent,PolicyVO pPolicy);
	public boolean validateVolume(Event pEvent,PolicyVO pPolicy);
	
}
