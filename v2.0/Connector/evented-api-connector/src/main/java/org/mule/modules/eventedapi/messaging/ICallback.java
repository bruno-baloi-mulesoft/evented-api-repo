package org.mule.modules.eventedapi.messaging;

import org.mule.modules.eventedapi.EventedApiConnector;

public interface ICallback 
{
		  public void setSubject(ISubject pSubject);
		  public void handleMessage( Object pMessage) throws Exception ;

}
