package org.mule.modules.eventedapi.messaging;

import java.util.Collection;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class mqttActivemqTransport extends MessagingTransport 
{
	private MqttClient mqttProducerClient;
	private MqttClient mqttConsumerClient;
	
	private EventedApiConnector _connector;
	
	private static Logger logger =  LoggerFactory.getLogger(mqttActivemqTransport.class);
	
	public mqttActivemqTransport(String pTransportId,String pTransportName,String pTrasnportType,String pSubjectName, String pPattern, ConnectionVO pConnection)
	{
		super(pTransportId,pTransportName,pTrasnportType,pSubjectName, pPattern, pConnection);
	}
	
	protected void init()
	{
		
		//1) create connection
		String _url = "tcp://"+host+":"+port;
		try
		{
			logger.info("Active MQ MQTT: Creating Conection to : "+_url);
			//mqttProducerClient = new MqttClient(_url,MqttClient.generateClientId());
			mqttProducerClient = new MqttClient(_url,"Producer-"+MqttClient.generateClientId());
			mqttProducerClient.connect();
			mqttConsumerClient = new MqttClient(_url,"Consumer-"+MqttClient.generateClientId());
			mqttConsumerClient.connect();
			
		
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
		
	}
	public int publishEvent(Event pEvent) throws Exception
	{
		MqttMessage message = new MqttMessage();
		
		//message.setId(pEvent.getEventId());
		message.setPayload(pEvent.getMessagePayload().toString().getBytes());
		mqttProducerClient.publish(subject+".mqtt", message);
		logger.info("Active MQ MQTTL sent message:topic="+subject+", payload="+pEvent.getMessagePayload().toString());
		//client.disconnect();
		
		return 0;
	}
	public void registerConsumer(ICallback pConsumer) throws Exception
	{
		mqttConsumerClient.subscribe(subject+".mqtt");
		mqttConsumerClient.setCallback( (MqttCallback) pConsumer );
		
	}
	public Event getNextEvent()
	{
		return null;
	}

	@Override
	public Collection getLatestEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
