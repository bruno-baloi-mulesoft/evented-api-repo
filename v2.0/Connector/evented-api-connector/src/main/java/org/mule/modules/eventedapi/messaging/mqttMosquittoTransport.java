package org.mule.modules.eventedapi.messaging;

import java.util.Collection;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.vo.ConnectionVO;

public class mqttMosquittoTransport extends MessagingTransport
{
	private MqttClient mqttClient;
	private EventedApiConnector _connector;
	
	public mqttMosquittoTransport(String pTransportId,String pTransportName,String pTransportType,String pSubjectName, String pPattern, ConnectionVO pConnection)
	{
		super(pTransportId,pTransportName,pTransportType,pSubjectName, pPattern, pConnection);
	}
	
	protected void init()
	{
		
		//1) create connection
		String _url = "tcp://"+host+":"+port;
		try
		{
			mqttClient = new MqttClient(_url,MqttClient.generateClientId());
			mqttClient.connect();
			
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
		mqttClient.publish(subject, message);
		
		//client.disconnect();
		
		return 0;
	}
	public void registerConsumer(ICallback pConsumer) throws Exception
	{
		
		mqttClient.setCallback( (MqttCallback) pConsumer );
		
	}
	public Event getNextEvent()
	{
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true);
		
	}

	@Override
	public Collection getLatestEvents() {
		// TODO Auto-generated method stub
		return null;
	}


}
