package org.mule.modules.eventedapi.messaging;

import java.util.UUID;

import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Callable;

public class UUIDGen implements MuleContextAware,Callable
{
	private String genEventUUID()
		{
			 UUID idOne = UUID.randomUUID();
			 
			 return idOne.toString();
		}

	@Override
	public void setMuleContext(MuleContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		return genEventUUID();
	}
}

