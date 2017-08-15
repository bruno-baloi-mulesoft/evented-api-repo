package org.mule.modules.eventing.connection;

public class MuleConnectionFactory 
{
	private static MuleConnectionFactory instance=null;
	
	private  MuleConnectionFactory()
	{
		
	}
	
	public static MuleConnectionFactory getInstance()
	{
		if(instance==null)
			instance = new MuleConnectionFactory();
			
		return instance;
	}
	
	public MuleConnection connect()
	{
		//create the correpsongin Connections to the various brokers.
		return null;
	}

}
