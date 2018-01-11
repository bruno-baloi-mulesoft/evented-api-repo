package org.mule.modules.eventedapi.clients;

public interface IMuleProducer 
{
	public String send(String subject, Object key, Object message);
}
