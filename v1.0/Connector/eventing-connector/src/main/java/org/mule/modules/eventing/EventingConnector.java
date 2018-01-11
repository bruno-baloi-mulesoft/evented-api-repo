package org.mule.modules.eventing;

import java.util.Map;


import java.util.ArrayList;
import java.util.List;

import org.mule.api.annotations.Query;
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
import org.mule.modules.eventing.clients.BaseMuleConsumer;
import org.mule.modules.eventing.clients.BaseMuleProducer;
import org.mule.modules.eventing.clients.MuleConsumerFactory;
import org.mule.modules.eventing.clients.MuleProducerFactory;
import org.mule.modules.eventing.config.ConnectorConfig;
import org.mule.modules.eventing.error.ErrorHandler;
import org.mule.modules.eventing.hdl.GenConsumerCallback;
import org.mule.modules.eventing.persistence.ModelPersistence;
import org.mule.modules.eventing.raml.RAMLParserWrapper;
import org.mule.modules.eventing.util.Util;
import org.mule.modules.eventing.vo.ConnectorConfigVO;
import org.mule.modules.eventing.vo.TransportConfigConnectorVO;
import org.mule.modules.eventing.vo.TransportConfigVO;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.modules.eventing.clients.*;

@Connector(name="eventing", friendlyName="Eventing")
@MetaDataScope( DataSenseResolver.class )
@OnException(handler=ErrorHandler.class)
public class EventingConnector {
	
	private static Logger logger = LoggerFactory.getLogger(EventingConnector.class);

    @Config
    ConnectorConfig config;

    

	@Start
	public void initialize() {
		
		logger.info("-----Iitializing Eventing Connector------");
		ConnectorConfigVO conVo= new ConnectorConfigVO();
    	conVo.setConsumerRAML(config.getConsumerSpecificationFile());
    	conVo.setProducerRAML(config.getProducerSpecificationFile());
    	
		TransportConfigConnectorVO _transport = RAMLParserWrapper.getInstance().parseConfigurationFiles(conVo);
    	//Util.getInstance().setTransportConfigConsumer(_transport);
		
		logger.info("+++Attempting to do a Message connector connection with params: user="+_transport.getConsumerTransport().getUserName()+", pass="+_transport.getConsumerTransport().getPassword()+",host="+_transport.getConsumerTransport().getHost()+",port="+_transport.getConsumerTransport().getPort());
    	
    	
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getConsumerApi());
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getProducerApi());
    	
	}
	
  

    @Processor(name = "SendEvent", friendlyName = "SendEvent")
    public void sendEvent(Object message) {
	//public void sendEvent(String subject, Object message) {
   // public void sendEvent() {
    	
    	BaseMuleProducer producer=MuleProducerFactory.getInstance().getProducer();
    	producer.send(Util.getInstance().getTransportConfigProducer().getSubject(),message);
		//BaseMuleProducer producer = new BaseMuleProducer(config.getProducerProperties())
		//producer.send(topic, key, message, events);
		//producer.shutdown();
    	System.out.println("Sending Event:"+message);
	}
    
  
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
    //@Source(name = "MuleConsumer", friendlyName = "MuleConsumer")
    @Source(name = "EventConsumer", friendlyName = "EventConsumer")
    public void eventConsumer(SourceCallback callback) throws Exception
	//public void eventConsumer(SourceCallback callback, String protocol, String vendor, String subject, long maxReads) throws Exception
    {
    	
		BaseMuleConsumer consumer = MuleConsumerFactory.getInstance().createConsumer(Util.getInstance().getTransportConfigConsumer().getProtocol(),Util.getInstance().getTransportConfigConsumer().getVendor());
		
		if(consumer == null)
			logger.info("Consumer is NULL");
		if(callback == null)
			logger.info("callback is NULL");
		
		try {
			consumer.read(callback, Util.getInstance().getTransportConfigConsumer().getSubject());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("ERROR", e1);
		}
			
	}

   
    /**
     *  Get Paginated Result
     *
     *  @param pagingConfiguration Page size.
     *  @throws Exception error produced while processing the request
     */
    /*
    @Paged
    @Processor
    public ProviderAwarePagingDelegate<Object, EventingConnector> getRecentMessages(PagingConfiguration pagingConfiguration) throws Exception {
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