package org.mule.modules.eventedapi.clients;

import org.mule.api.callback.SourceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolaceConsumer extends BaseMuleConsumer
{

	private static Logger logger = LoggerFactory.getLogger(SolaceConsumer.class);
	
	public void read(SourceCallback callback, long maxReads, String topic, int partition) throws Exception {
		// TODO Auto-generated method stub
		
		
		Object payload=null;
		
		//read data from the MQTT Message Pipe
		try{
			callback.process(payload);
		} 
		catch (Exception e) {
			logger.error("ERROR", e);
		}
		
	}
}
