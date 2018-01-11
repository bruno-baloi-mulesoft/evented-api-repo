package org.mule.modules.eventedapi.messaging;

import java.util.List;

public interface ISubject extends ISubscriber, IPublisher
{
	public void addTransportReference(String pTrasnportName, ITransport pTransport);
	public void addTransports(List pTransportList);
	public void addSupportedEvents(List pEvents);
	public boolean validateEvent(Event pEvent);
	public void addInboundEvent(Event pEvent);
	public void startTransports();

}
