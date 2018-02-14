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
	public void addConsumerPolicyReference(IPolicy pPolicy);
	public void addProducerPolocyReference(IPolicy pPolicy);
	public void setPolicies(List pPolicyList);
	public List getSupportedEventList();
	public void setProducer(boolean pProducer);

}
