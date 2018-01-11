package org.mule.modules.eventing.clients;

import java.util.Properties;

import org.mule.api.callback.SourceCallback;

public abstract class BaseMuleProducer implements IMuleProducer
{
	
	public void  BaseMuleProduer()
	{
	}
	
	protected abstract void initialize(Properties properties);
	public abstract String send(String subject,  Object message);
	public abstract String send();
	
	
	

}
