package org.mule.modules.eventedapi.messaging;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class mqttMosquittoCallbackHandler implements ICallback, MqttCallback
{

	private static Logger logger =  LoggerFactory.getLogger(mqttMosquittoCallbackHandler.class);
	
	private ISubject _subject;
	
	private EventedApiConnector _connector;

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
		this.handleMessage(arg1);
		
	}

	@Override
	public void handleMessage(Object pMessage) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Got Incomming Mqtt Message: "+pMessage.toString());
		//_connector.eventConsumer(subject, event, callback);
		
		Event _newEvent = new Event();
		
		_newEvent.setMessagePayload((String)pMessage);
		_subject.addInboundEvent(_newEvent);
	}

	@Override
	public void setSubject(ISubject pSubject) {
		// TODO Auto-generated method stub
		_subject = pSubject;
	}


}
