package org.mule.modules.eventedapi.messaging;

import org.mule.modules.eventedapi.util.GenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Subscription;

public class natsAppceraCallbackHandler implements ICallback,MessageHandler{
	
	
	private static Logger logger =  LoggerFactory.getLogger(natsAppceraCallbackHandler.class);
	private ISubject _subject;

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		
		String _subject = arg0.getSubject();
		byte [] _bytes = arg0.getData();
		try
		{
			handleMessage(new String(_bytes));
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
	}

	@Override
	public void setSubject(ISubject pSubject) {
		// TODO Auto-generated method stub
		_subject=pSubject;
		
	}

	@Override
	public void handleMessage(Object pMessage) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Got Incomming NATS Message: "+pMessage.toString());
		//_connector.eventConsumer(subject, event, callback);
		String _payload = (String) pMessage;
		
		Event _newEvent = new Event();
		_newEvent.setMessagePayload(_payload);
		_newEvent.setEventId(GenUtil.getInstance().genEventUUID());
		_newEvent.setTransportType("natsAppcera");
		_subject.addInboundEvent(_newEvent);
		
	}

}
