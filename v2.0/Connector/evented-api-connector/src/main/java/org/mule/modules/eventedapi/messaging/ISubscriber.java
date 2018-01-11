package org.mule.modules.eventedapi.messaging;

import java.util.Collection;

public interface ISubscriber 
{
	public void registerConsumer(ICallback pConsumer) throws Exception;
	public Event getNextEvent();
	public Collection getLatestEvents();
}
