package org.mule.modules.eventing.clients;

public class MuleProducerFactory 
{
	
	private BaseMuleProducer producer=null;
	private static MuleProducerFactory instance=null;
	
	private MuleProducerFactory()
	{
		
	}
	
	public static MuleProducerFactory getInstance()
	{
		if(instance==null)
			instance=new MuleProducerFactory();
			
		return instance;
	}
	
	public BaseMuleProducer createProducer(String pType,String pVendor)
	{
		return producer;
	}
	public BaseMuleProducer getProducer()
	{
		if(producer==null)
		{
			//create the right type of Transport producer
		}
		return producer;
	}

}
