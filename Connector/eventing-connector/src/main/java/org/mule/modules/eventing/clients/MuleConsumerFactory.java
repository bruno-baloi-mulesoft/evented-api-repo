package org.mule.modules.eventing.clients;

public class MuleConsumerFactory 
{
	
	private static BaseMuleConsumer consumer=null;
	private static MuleConsumerFactory instance;
	
	private MuleConsumerFactory()
	{
		
	}
	public static MuleConsumerFactory getInstance()
	{
		if(instance==null)
			instance= new MuleConsumerFactory();
		

		return instance;
	}
	
	public BaseMuleConsumer createConsumer(String pType,String pVendor)
	{
		
		///build the COnsumer code.
		BaseMuleConsumer consumer = new BaseMuleConsumer();
		
		return consumer;
		
	}
	
	public BaseMuleConsumer getConsumer()
	{
		return consumer;
	}

}
