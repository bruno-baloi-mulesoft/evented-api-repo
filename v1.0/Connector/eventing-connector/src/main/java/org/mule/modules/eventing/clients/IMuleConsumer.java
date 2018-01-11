package org.mule.modules.eventing.clients;

import org.mule.api.callback.SourceCallback;

public interface IMuleConsumer 
{
	public void read(SourceCallback callback, String subject) throws Exception;
}
