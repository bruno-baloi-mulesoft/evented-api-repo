package org.mule.modules.eventedapi.validation;

import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.messaging.ISubject;
import org.mule.modules.eventedapi.vo.PolicyVO;

public interface IValidator 
{
	public void setSubject(ISubject pSubject);
	public ValidationResponse validateEvent(Event pEvent, PolicyVO pPolicy, boolean inbound,String pDirection);
	
	
}
