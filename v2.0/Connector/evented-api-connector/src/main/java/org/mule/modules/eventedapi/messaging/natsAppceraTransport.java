package org.mule.modules.eventedapi.messaging;

import java.util.Collection;
import io.nats.client.*;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class natsAppceraTransport extends MessagingTransport
{
	
	private EventedApiConnector _connector;
	
	private static Logger logger =  LoggerFactory.getLogger(natsAppceraTransport.class);
	
	private ConnectionFactory _connectionFactory;
	private Connection _connection;
	private Subscription _subscription;
	
	

	public natsAppceraTransport(String pTransportId, String pTransportName, String pTrasnportType, String pSubjectName,
			String pPattern, ConnectionVO pConnection) {
		super(pTransportId, pTransportName, pTrasnportType, pSubjectName, pPattern, pConnection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection getLatestEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		

		//1) create connection
		String _url = "nats://"+host+":"+port;
		try
		{
			logger.info("NATS : Creating Conection to : "+_url);
		
			_connectionFactory = new ConnectionFactory(_url);
		    _connection = _connectionFactory.createConnection();
		
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
	}

	@Override
	public int publishEvent(Event pEvent) throws Exception {
		// TODO Auto-generated method stub
		
		_connection.publish(subject,  ((String)pEvent.getMessagePayload()).getBytes());
		
		return 0;
	}

	@Override
	public void registerConsumer(ICallback pConsumer) throws Exception {
		// TODO Auto-generated method stub
		
		_subscription = _connection.subscribe(subject,(MessageHandler) pConsumer);
		
		
	}

	@Override
	public Event getNextEvent() {
		// TODO Auto-generated method stub
		return null;
	}

}
