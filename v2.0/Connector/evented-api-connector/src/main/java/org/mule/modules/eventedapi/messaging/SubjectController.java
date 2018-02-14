package org.mule.modules.eventedapi.messaging;

import java.lang.reflect.Constructor;
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
import org.mule.modules.eventedapi.util.AmfConstants;
import org.mule.modules.eventedapi.util.GenUtil;
import org.mule.modules.eventedapi.util.GeneralConstants;
import org.mule.modules.eventedapi.util.MessagingConstants;
import org.mule.modules.eventedapi.validation.IValidator;
import org.mule.modules.eventedapi.validation.ValidationResponse;
import org.mule.modules.eventedapi.vo.ConnectionVO;
import org.mule.modules.eventedapi.vo.PolicyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectController implements ISubject
{
	
	private static Logger logger = LoggerFactory.getLogger(SubjectController.class);
	private boolean isProducer;
	
	private List supportedEventList= new ArrayList();
	private List policyList = new ArrayList();
	private List validatorList = new ArrayList();
	//List trasnportList = new ArrayList();
	private HashMap transportMap=new HashMap();
	
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
			 
			  if(policyList.size()!=0)
				{
					String _retMsg = new String();
				 	ValidationResponse _resp = validateEvent(_event,_retMsg,true,AmfConstants.DIRECTION_IN);
					if (!_resp.isValid())
					{
						String _errStr = " ### Incoming Event is invalid:  "+_resp.getValidationMessage();
						logger.error(_errStr);
						throw new Exception(_errStr);
					}
				}
			  
			 
			 
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
		
		logger.info("Policy List size = "+policyList.size());
		if(policyList.size() !=0 )
		{
			//1) Validate Outbound Event
			
			String _retMsg = new String();
			ValidationResponse _valid= validateEvent(pEvent,_retMsg,false,AmfConstants.DIRECTION_OUT);
			if (! _valid.isValid())
			{
				String _errStr = " ### Outgoing Event is invalid:  "+_valid.getValidationMessage();
				logger.error(_errStr);
				throw new Exception(_errStr);
			}
		}
		
		//2) publish to all transports
		
		Set _transportList = transportMap.keySet();
		
		Iterator _it = _transportList.iterator();
		while(_it.hasNext())
		{
			 String _key =  (String) _it.next();
			 ITransport _t = (ITransport) transportMap.get(_key);
			
			_t.publishEvent(pEvent);
			//logger.info("Published event "+pEvent.getEventId()+" to tranport:"+_t.getTransportName()+"type="+_t.getTransportType()+", payload="+pEvent.getMessagePayload());
			
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
		
		supportedEventList = pEvents;
		
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

	@Override
	public void addConsumerPolicyReference(IPolicy pPolicy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProducerPolocyReference(IPolicy pPolicy) {
		// TODO Auto-generated method stub
		
	}

	public List getSupportedEventList() {
		return supportedEventList;
	}

	@Override
	public void setPolicies(List pPolicyList) {
		// TODO Auto-generated method stub
		policyList = pPolicyList;
		
		Iterator _pIt = pPolicyList.iterator();
		while(_pIt.hasNext())
		{
			
			PolicyVO _vo = (PolicyVO) _pIt.next();
			
			try
			{
				String _validatorClassName ="org.mule.modules.eventedapi.validation."+ _vo.getPolicyType()+ GenUtil.getInstance().getConnectorProps().getProperty(GeneralConstants.policyHandler);
				logger.info("Validator Classname:" +_validatorClassName );
				Class<IValidator> _validatorClass = (Class) Class.forName(_validatorClassName);
				IValidator _validator = _validatorClass.newInstance();
				_validator.setSubject(this);
				validatorList.add(_validator);
			}
			catch(Exception pExcp)
			{
				pExcp.printStackTrace();
			}
			
		}
		

	}
	private ValidationResponse validateEvent(Event pEvent, String pRetMsg,boolean inbound, String pDirection)
	{
		//boolean retVal = true;
		ValidationResponse _retVal = new ValidationResponse();
		PolicyVO _policy=null;
		
		boolean finished=true;
		boolean validationActive=true;
		
		Iterator _pIt = validatorList.iterator();
		int i=0;
		while(_pIt.hasNext())
		{
			IValidator _v = (IValidator) _pIt.next();
			_policy = (PolicyVO) policyList.get(i);
			
			if(pDirection.equals(_policy.getDirection()) || _policy.getDirection().equals(AmfConstants.DIRECTION_BI))
			{
				ValidationResponse _resp = _v.validateEvent(pEvent,_policy,inbound, pDirection);
				 
				if(_resp.isValid())			
					i++;
				else
				{
					//pRetMsg = " ### Event "+pEvent.getEventId()+ " violated Policy "+ _policy.getPolicyName() + " of type '" + _policy.getPolicyType()+"'";
					_retVal.setValid(false);
					_retVal.setValidationMessage(" ### Event "+pEvent.getEventId()+ " violated Policy "+ _policy.getPolicyName() + " of type '" + _policy.getPolicyType()+"; validation message: "+_resp.getValidationMessage());
					finished=false;
					break;
				}
			}
			validationActive=false;
			
			
		}
		if(finished  && validationActive)
		{
			_retVal.setValid(true);
			_retVal.setValidationMessage(" ### Event "+pEvent.getEventId()+ " passed policy "+ _policy.getPolicyName() + " of type '" + _policy.getPolicyType());
		}
		if(finished  && ! validationActive)
		{
			_retVal.setValid(true);
			_retVal.setValidationMessage(" ### Policy "+ _policy.getPolicyName() + " of type '" + _policy.getPolicyType()+ "does not apply on the "+pDirection);
		}
		
		
		return _retVal;
	}

	@Override
	public void setProducer(boolean pProducer) {
		// TODO Auto-generated method stub
		isProducer = pProducer;
		
	}

	

}
