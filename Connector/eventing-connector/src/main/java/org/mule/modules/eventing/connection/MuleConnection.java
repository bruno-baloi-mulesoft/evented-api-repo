package org.mule.modules.eventing.connection;

public abstract class MuleConnection implements IMuleConnection{

	public void connect()
	{
		
	}
	
	protected abstract void initialize();
}
