package org.mule.modules.eventedapi.messaging;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.util.GenUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class mqttActivemqCallbackHandler implements ICallback, MqttCallback
{

	private static Logger logger = LoggerFactory.getLogger(mqttActivemqCallbackHandler.class);
	
	private ISubject _subject;
	
	private EventedApiConnector _connector;
	
	public  mqttActivemqCallbackHandler()
	{
		logger.info("AMQ Mqtt Callback Handler init: !");
	}

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
		logger.info("GotIncomming Active MQ Mqtt Message: "+ arg0+ ", message="+arg1);
		this.handleMessage(arg1);
		
	}

	@Override
	public void handleMessage(Object pMessage) throws Exception {
		// TODO Auto-generated method stub
		//logger.info("Got Incomming Active MQ Mqtt Message: "+pMessage.toString());
		//_connector.eventConsumer(subject, event, callback);
		
		MqttReceivedMessage _msg = (MqttReceivedMessage) pMessage;
		String _payload = new String(_msg.getPayload());
		
		Event _newEvent = new Event();
		_newEvent.setMessagePayload(_payload);
		_newEvent.setEventId(GenUtil.getInstance().genEventUUID());
		_newEvent.setTransportType("mqttActivemq");
		_subject.addInboundEvent(_newEvent);
	}

	@Override
	public void setSubject(ISubject pSubject) {
		// TODO Auto-generated method stub
		_subject = pSubject;
	}


}
