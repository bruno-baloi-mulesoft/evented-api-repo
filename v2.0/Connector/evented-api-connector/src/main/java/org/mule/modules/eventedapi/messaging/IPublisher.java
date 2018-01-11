package org.mule.modules.eventedapi.messaging;

public interface IPublisher 
{
	public int publishEvent(Event pEvent) throws Exception;

}
