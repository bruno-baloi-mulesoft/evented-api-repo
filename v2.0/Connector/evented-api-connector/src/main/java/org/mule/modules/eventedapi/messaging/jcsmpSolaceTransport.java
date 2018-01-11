package org.mule.modules.eventedapi.messaging;

import java.util.Collection;

import java.util.concurrent.CountDownLatch;

import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.util.MessagingConstants;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.ConsumerFlowProperties;
import com.solacesystems.jcsmp.DeliveryMode;
import com.solacesystems.jcsmp.EndpointProperties;
import com.solacesystems.jcsmp.FlowReceiver;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;
import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;
import com.solacesystems.jcsmp.XMLMessageProducer;

public class jcsmpSolaceTransport extends MessagingTransport
{
	
	private EventedApiConnector _connector;
	private static Logger logger =  LoggerFactory.getLogger(jcsmpSolaceTransport.class);
	
	private Topic topic;
	private Queue queue;
	private JCSMPSession session;
	private XMLMessageConsumer consumer;
	private XMLMessageProducer producer;
	

	public jcsmpSolaceTransport(String pTransportId,String pTransportName, String pTrasnportType, String pSubjectName, String pPattern,
			ConnectionVO pConnection) {
		super(pTransportId,pTransportName, pTrasnportType, pSubjectName, pPattern, pConnection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection getLatestEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() {
		
		try
		{
		  //final CountDownLatch latch = new CountDownLatch(1);
			String _connectionUrl = "tcp://"+host+":"+port;
			logger.info("Connecting to Solace: "+ _connectionUrl);
	        JCSMPProperties properties = new JCSMPProperties();
	        properties.setProperty(JCSMPProperties.HOST, _connectionUrl);
	        properties.setProperty(JCSMPProperties.USERNAME, user);
	        properties.setProperty(JCSMPProperties.PASSWORD, password);
	        properties.setProperty(JCSMPProperties.VPN_NAME, connectionId);
	        properties.setProperty(JCSMPProperties.CLIENT_NAME, transportId);
	        
	        
	        if(pattern.equals(MessagingConstants.pub_sub))
	        	topic = JCSMPFactory.onlyInstance().createTopic(subject+".jcsmp");
	        
	        if(pattern.equals(MessagingConstants.p2p))
	        	queue = JCSMPFactory.onlyInstance().createQueue(subject+".jcsmp");
	        
	        	session = JCSMPFactory.onlyInstance().createSession(properties);
	        	
	        	session.connect();
	        	
	        	
		        try
		        {
			        initProducer();
			       //initConsumer();
			      
			      
		        }
		        catch(Exception excp)
		        {
		        	excp.printStackTrace();
		        }
	        
	        
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
	        
		
	}

	@Override
	public int publishEvent(Event pEvent) throws Exception {
		// TODO Auto-generated method stub
		
		 TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
         msg.setText((String) pEvent.getMessagePayload());

         logger.info("Solace JSCMP Publishing message: " + pEvent.getMessagePayload());
         
         if(pattern.equals(MessagingConstants.pub_sub))
         {
        	 producer.send(msg, topic);
         }
         if(pattern.equals(MessagingConstants.p2p))
         {
        	 msg.setDeliveryMode(DeliveryMode.PERSISTENT);
        	 producer.send(msg, queue);
         }

		return 0;
	}

	@Override
	public void registerConsumer(ICallback pConsumer) throws Exception {
		// TODO Auto-generated method stub
		
		 try
		 {
	        consumer = session.getMessageConsumer((XMLMessageListener) pConsumer );
	        
	        if(queue!=null)
	        	createQueueFlowReceiver( (XMLMessageListener) pConsumer);
	        	
            if(topic !=null)
         	   session.addSubscription(topic);
	        
    	       // logger.info("Awaiting messages...");
    	        consumer.start();
		 }
		 catch(Exception excp)
		 {
			 excp.printStackTrace();
		 }	       
		  
      
		
	}

	@Override
	public Event getNextEvent() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 private void initConsumer()
	 {
		 
		 try
		 {
	        consumer = session.getMessageConsumer(new XMLMessageListener() {
	            @Override
	            public void onReceive(BytesXMLMessage msg) {
	                if (msg instanceof TextMessage) {
	                    logger.info("SOlace JCSMP TextMessage received: '%s'%n",
	                            ((TextMessage) msg).getText());
	                } else {
	                    System.out.println("Message received");
	                }
	                System.out.printf("Message Dump:%n%s%n", msg.dump());
	                //latch.countDown();
	            }

	            @Override
	            public void onException(JCSMPException e) {
	                System.out.printf("Consumer received exception: %s%n", e);
	                //latch.countDown();
	            }
	        });
		 }
		 catch(Exception excp)
		 {
			 excp.printStackTrace();
		 }	       
	}
	 */
	
    private void initProducer()
    {
    	
        try
        {
        //-----why do we need JCSMPStreamingPublishEventHandler-----????
          producer = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
            @Override
            public void responseReceived(String messageID) {
                logger.info("JCSMP Producer received response for msg: " + messageID);
            }

            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                logger.info("JCSMP Producer received error for msg: %s@%s - %s%n",
                        messageID, timestamp, e);
            }
        });
        }
        catch(Exception excp)
        {
        	excp.printStackTrace();
        }
    }
    
    private void createQueueFlowReceiver( XMLMessageListener callBack)
    {
    	final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
    	flow_prop.setEndpoint(queue);
    	flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_AUTO);

    	EndpointProperties endpoint_props = new EndpointProperties();
    	endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

    	try
    	{
    	
    	 final FlowReceiver cons = session.createFlow(callBack, flow_prop, endpoint_props);
    	
    	}
    	catch(Exception excp)
    	{
    		excp.printStackTrace();
    	}
    }

}
