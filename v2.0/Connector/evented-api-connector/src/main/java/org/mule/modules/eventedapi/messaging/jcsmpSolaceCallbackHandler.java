package org.mule.modules.eventedapi.messaging;

import org.mule.modules.eventedapi.util.GenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solacesystems.jcsmp.BytesMessage;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.Destination;
import com.solacesystems.jcsmp.Endpoint;
import com.solacesystems.jcsmp.FlowReceiver;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPLogLevel;
import com.solacesystems.jcsmp.Subscription;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.XMLMessageListener;

public class jcsmpSolaceCallbackHandler implements ICallback, XMLMessageListener
{

	private static Logger logger =  LoggerFactory.getLogger(jcsmpSolaceCallbackHandler.class);
	
	private ISubject _subject;
	
	@Override
	public void setSubject(ISubject pSubject) {
		// TODO Auto-generated method stub
		_subject = pSubject;
		
	}

	@Override
	public void handleMessage(Object pMessage) throws Exception {
		// TODO Auto-generated method stub
		
		Event _newEvent = new Event();
		_newEvent.setMessagePayload((String)pMessage);
		_newEvent.setEventId(GenUtil.getInstance().genEventUUID());
		_newEvent.setTransportType("jcsmpSolace");
		_subject.addInboundEvent(_newEvent);
		
	}

	@Override
	public void onException(JCSMPException arg0) {
		// TODO Auto-generated method stub
		  logger.error("Consumer received exception: %s%n", arg0);
		
	}

	@Override
	public void onReceive(BytesXMLMessage arg0) {
		// TODO Auto-generated method stub
		
		String _retMsg=new String();
		

		try
		{
			String topicName = arg0.getDestination().getName();
			
			if (arg0 instanceof TextMessage ) {
			    logger.info("JCSMP TextMessage received: '%s'%n", ((TextMessage) arg0).getText());
			    _retMsg = ((TextMessage) arg0).getText();
			} 
			else if (arg0 instanceof BytesMessage) 
			{
			    byte[] b = new byte[(int) ((BytesMessage) arg0).getContentLength()];
			    b=((BytesMessage) arg0).getBytes();
			    _retMsg = new String(b);
			    System.out.printf("JCSMP BytesMessage received: '%s'%n", _retMsg);
			}
			
			this.handleMessage(_retMsg);
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
	}

	


}
