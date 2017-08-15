package org.mule.modules.eventing.clients;

public interface IMuleProducer 
{
	public String send(String subject, Object key, Object message);
}
