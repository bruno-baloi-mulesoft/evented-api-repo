package org.mule.modules.eventedapi.clients;

import org.mule.api.callback.SourceCallback;

public interface IMuleConsumer 
{
	public void read(SourceCallback callback, String subject) throws Exception;
}
