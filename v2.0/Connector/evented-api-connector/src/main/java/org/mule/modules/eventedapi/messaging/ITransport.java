package org.mule.modules.eventedapi.messaging;

import org.mule.modules.eventedapi.EventedApiConnector;

public interface ITransport extends IPublisher, ISubscriber
{
	//public void setSubject(ISubject pSubject);
	public String getTransportName();
	public String getTransportType();
	
	
}
