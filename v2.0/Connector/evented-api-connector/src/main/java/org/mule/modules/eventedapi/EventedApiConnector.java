package org.mule.modules.eventedapi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;



import java.util.Map;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.api.annotations.MetaDataScope;
import org.mule.api.annotations.Paged;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.SourceStrategy;
import org.mule.api.callback.SourceCallback;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.param.Default;

import org.mule.modules.eventedapi.config.ConnectorConfig;
import org.mule.modules.eventedapi.error.ErrorHandler;
import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.messaging.ISubject;
import org.mule.modules.eventedapi.messaging.MessageInfrastructureBuilder;
import org.mule.modules.eventedapi.util.AmfParseUtil;
import org.mule.modules.eventedapi.util.GenUtil;
import org.mule.modules.eventedapi.vo.EventedAPIConnectorConfigVO;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.modules.eventedapi.messaging.Producer;
import org.mule.modules.eventedapi.parser.AmfGenerator;
import org.mule.modules.eventedapi.messaging.Consumer;


@Connector(name="evented-api", friendlyName="EventedApi")
@MetaDataScope( DataSenseResolver.class )
@OnException(handler=ErrorHandler.class)

public class EventedApiConnector {
	
	private static Logger logger = LoggerFactory.getLogger(EventedApiConnector.class);

    @Config
    ConnectorConfig config;
    
    private String amfDocPath;
    private String amfDialectDir;
    private String amfGenModelPath;
    private String amfLib;
    private String amfParseCmdPath;
    private String connectorPropertiesPath;
    
    MessageInfrastructureBuilder _infraBuilder;
    Producer _producer=null;
    Consumer _consumer=null;
   
    
    
    @Start
	public void initialize() {
		
		logger.info("-----Iitializing EventedApi Connector------");
		
		amfDocPath = config.getAmfDocSpecificationFile();
		amfDialectDir= config.getAmfDialectSpecificationDir();
		amfLib = config.getAmfLib();
		amfParseCmdPath = config.getAmParseCmd();
		connectorPropertiesPath = config.getEventedAPiConnectorProperties();
		
		System.out.println("EventedAPI Config values: doc location: "+amfDocPath);
		System.out.println("						: dialect location: "+amfDialectDir);
		System.out.println("						: dialect location: "+amfDialectDir);
		System.out.println("						: model location: "+amfGenModelPath);
		System.out.println("						: amf parse command path: "+amfParseCmdPath);
		
		
		//0. load connector properties
		GenUtil.getInstance().loadConnectorProperties(connectorPropertiesPath);
		//1. Parse JSON-LD COnfiguration
		String  _jsonldDoc  = AmfParseUtil.getInstance().parseAMFDoc(amfDocPath, amfDialectDir, amfLib,amfParseCmdPath);
		EventedAPIConnectorConfigVO _configVO = AmfParseUtil.getInstance().getConnectorConfiguration(_jsonldDoc);
		
		GenUtil.getInstance().printConnctorConfig(_configVO);
		
		try
		{
			AmfParseUtil.getInstance().setAmfDoc(AmfGenerator.parseAmfString(_jsonldDoc));
		}
		catch(Exception excp)
		{
			excp.printStackTrace();
		}
		
		//2) create Messaging infrastructure i.e. Transports + topics/queues
		_infraBuilder = MessageInfrastructureBuilder.getInstance();
		_infraBuilder.setupMsgInfra(_configVO,this);
		
		//3) Setup Eventing infrastructure i.e. validation policies for each subject
		
		
		/*
		ConnectorConfigVO conVo= new ConnectorConfigVO();
    	conVo.setConsumerRAML(config.getConsumerSpecificationFile());
    	conVo.setProducerRAML(config.getProducerSpecificationFile());
    	
		TransportConfigConnectorVO _transport = RAMLParserWrapper.getInstance().parseConfigurationFiles(conVo);
    	//Util.getInstance().setTransportConfigConsumer(_transport);
		
		logger.info("+++Attempting to do a Message connector connection with params: user="+_transport.getConsumerTransport().getUserName()+", pass="+_transport.getConsumerTransport().getPassword()+",host="+_transport.getConsumerTransport().getHost()+",port="+_transport.getConsumerTransport().getPort());
    	
		//Instantiate Transport Specific Connection
    	
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getConsumerApi());
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getProducerApi());
    	*/
	}


    @Processor(name = "SendEvent", friendlyName = "SendEvent")
    public void eventProducer(String subject, Object message) {
    	
    	//logger.info("EventedAPI Conector sending out event on subject="+subject+",payload="+message);
    	
    	Event _event = new Event();
    	_event.setEventId(GenUtil.getInstance().genEventUUID());
    	_event.setMessagePayload((String)message);
    	
    	if(_producer==null)
    		_producer =_infraBuilder.getInstance().getProducer();
    	
    	try
    	{
	    	ISubject _subject=_producer.getProdSubject(subject);
	    	_subject.publishEvent(_event);
    	}
    	catch(Exception excp)
    	{
    		excp.printStackTrace();
    	}
    	
     
	//public void sendEvent(String subject, Object message) {
   // public void sendEvent() {
    	
    	/*
    	BaseMuleProducer producer=MuleProducerFactory.getInstance().getProducer();
    	producer.send(Util.getInstance().getTransportConfigProducer().getSubject(),message);
		//BaseMuleProducer producer = new BaseMuleProducer(config.getProducerProperties())
		//producer.send(topic, key, message, events);
		//producer.shutdown();
    	System.out.println("Sending Event:"+message);
    	*/
	}
    

    /**
     *  Custom Message Source
     *
     *  @param callback The sourcecallback used to dispatch message to the flow
     *  @throws Exception error produced while processing the payload
     */
    
    
    @Source(name = "EventConsumer", friendlyName = "EventConsumer", sourceStrategy = SourceStrategy.POLLING, pollingPeriod = 500)
    public void eventConsumer(String subject,SourceCallback callback) throws Exception 
    {
    	//logger.info("in event Source");
    	try
    	{
    		if(_consumer==null)
    			_consumer = _infraBuilder.getInstance().getConsumer();
    		
    		Event _event = ((ISubject) _consumer.getConsumerSubject(subject)).getNextEvent();
    		if(_event!=null)
     		{
     			//logger.info("EventedAPI Controller received Event: transport="+_event.getTransportType()+",event-d="+_event.getEventId()+",payload="+_event.getMessagePayload());
     			callback.process(_event);
     		}
     		
    		/*
    		Collection _eventList = ((ISubject) _consumer.getConsumerSubject(subject)).getLatestEvents();
    		Iterator _it = _eventList.iterator();
    		while(_it.hasNext())
    		{
    			Event _event = (Event) _it.next();
    			logger.info("EventedAPI Controller received Event: transport="+_event.getTransportType()+",event-d="+_event.getEventId()+",payload="+_event.getMessagePayload());
     			callback.process(_event);
     	
    		}
    		*/
    		
    		
    		
    		
    	}
    	catch(Exception excp)
    	{
    		excp.printStackTrace();
    	}
    		
    }
    
    /*
   //@Source(name = "EventConsumer", friendlyName = "EventConsumer")
    @Source(name = "EventConsumer", friendlyName = "EventConsumer",sourceStrategy = SourceStrategy.POLLING, pollingPeriod = 1000)
    public void eventConsumer(String subject,Object event, SourceCallback callback) throws Exception
   {
    	try
    	{
    		if(_consumer==null)
    			_consumer = _infraBuilder.getInstance().getConsumer();
    		
    		Event _event = ((ISubject) _consumer.getConsumerSubject(subject)).getNextEvent();
    		logger.info("...getting event: "+_event);
    		//Event _event = ((ISubject) _infraBuilder.getCompleteSubjectMap().get(subject)).getNextEvent();
    	
    		
    		if(_event!=null)
    		{
    			logger.info("EventedAPI Controller received Event: transport="+_event.getTransportType()+",event-d="+_event.getEventId()+",payload="+_event.getMessagePayload());
    			callback.process(_event);
    		}
    	}
    	catch(Exception excp)
    	{
    		excp.printStackTrace();
    	}
    	
    	
	}

    */
    

    /**
     * DataSense processor

     * @param key Key to be used to populate the entity
     * @param entity Map that represents the entity
     * @return Some string
     */
    /*
    @Processor
    public Map<String,Object> addEntity( @MetaDataKeyParam String key, @Default("#[payload]") Map<String,Object> entity) {
      
        return entity;
    }
    */
    /**
     *  Custom Message Source
     *
     *  @param callback The sourcecallback used to dispatch message to the flow
     *  @throws Exception error produced while processing the payload
     */
    /*
    @Source(sourceStrategy = SourceStrategy.POLLING,pollingPeriod=5000)
    public void getNewMessages(SourceCallback callback) throws Exception {
        
         //Every 5 the flow using this processor will be called and the payload will be the one defined here.
         
         //The PAYLOAD can be anything. In this example a String is used.  
        
        callback.process("Start working");
    }
   */

    /**
     *  Get Paginated Result
     *
     *  @param pagingConfiguration Page size.
     *  @throws Exception error produced while processing the request
     */
    /*
    @Paged
    @Processor
    public ProviderAwarePagingDelegate<Object, EventedApiConnector> getRecentMessages(PagingConfiguration pagingConfiguration) throws Exception {
        //TODO The pagination logic can be defined in this custom class, or simple create an annonimous class here.
        return new PagesProvider(pagingConfiguration);
    }
	*/
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}