package org.mule.modules.eventedapi.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.modules.eventedapi.util.GenUtil;
import org.mule.modules.eventedapi.util.MessagingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectController implements ISubject
{
	
	private static Logger logger = LoggerFactory.getLogger(SubjectController.class);
	
	List supportedEventList= new ArrayList();
	//List trasnportList = new ArrayList();
	HashMap transportMap=new HashMap();
	
	private ArrayBlockingQueue inboundEventQueue;
	
	
	private ExecutorService executor =null;
	
	public SubjectController()
	{
		//inboundEventQueue = new ArrayBlockingQueue(10000);
		int queueDepth = Integer.parseInt(GenUtil.getInstance().getConnectorProps().getProperty(MessagingConstants.inbound_queue_depth));
		inboundEventQueue = new ArrayBlockingQueue(queueDepth);
	}

	@Override
	public void registerConsumer(ICallback pConsumer) throws Exception {
		// TODO Auto-generated method stub
		
	}

	////----The Event COnnector willcall this to deque evetns off the queue.
	@Override
	public synchronized Event getNextEvent() {
		// TODO Auto-generated method stub
		
		Event _event=null;
		try
		{
			if(! inboundEventQueue.isEmpty())
			{
				_event = (Event) inboundEventQueue.take();
			 logger.info("Events left on queue:"+inboundEventQueue.size());
			}
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		return _event;
	}
	@Override
	public Collection getLatestEvents() {
		// TODO Auto-generated method stub
		Collection eventCollection = new ArrayList();
		int numEvents;
		
		try
		{
			if(! inboundEventQueue.isEmpty())
			{
				numEvents =  inboundEventQueue.drainTo(eventCollection);
			  logger.info("Events purged from the queue:"+numEvents);
			}
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		return eventCollection;
	}

	@Override
	public synchronized int publishEvent(Event pEvent) throws Exception {
		// TODO Auto-generated method stub
		
		//1) publish to all transports
		
		Set _transportList = transportMap.keySet();
		
		Iterator _it = _transportList.iterator();
		while(_it.hasNext())
		{
			 String _key =  (String) _it.next();
			 ITransport _t = (ITransport) transportMap.get(_key);
			
			_t.publishEvent(pEvent);
			logger.info("Published event "+pEvent.getEventId()+" to tranport:"+_t.getTransportName()+"type="+_t.getTransportType()+", payload="+pEvent.getMessagePayload());
			 
		}
		
		
		return 0;
	}

	@Override
	public void addTransportReference(String pTransportName,ITransport pTransport) {
		// TODO Auto-generated method stub
		transportMap.put(pTransportName, pTransport);
		
	}

	@Override
	public void addSupportedEvents(List pEvents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateEvent(Event pEvent) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

	@Override
	public void addTransports(List pTransportList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInboundEvent(Event pEvent) {
		// TODO Auto-generated method stub
		try
		{
			inboundEventQueue.put(pEvent);
			//inboundEventQueue.add(pEvent);
			
			logger.info("Events on inner queue:"+inboundEventQueue.size());
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
	}

	@Override
	public void startTransports() {
		// TODO Auto-generated method stub
		executor = Executors.newFixedThreadPool(transportMap.size());
		Set _tList = transportMap.keySet();
		Iterator _tIt = _tList.iterator();
		while(_tIt.hasNext())
		{
			String _key = (String) _tIt.next();
			
			MessagingTransport _t = (MessagingTransport) transportMap.get(_key);
			executor.execute(_t);
						
		}
		
		
	}

	

}
