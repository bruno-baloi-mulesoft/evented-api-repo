package org.mule.modules.eventing.config;

import org.mule.api.annotations.components.Configuration;


import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Path;
import org.mule.api.annotations.display.Placement;
import org.mule.api.ConnectionException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.eventing.EventingConnector;
import org.mule.modules.eventing.persistence.ModelPersistence;
import org.mule.modules.eventing.raml.RAMLParserWrapper;
import org.mule.modules.eventing.util.Util;
import org.mule.modules.eventing.vo.ConnectorConfigVO;
import org.mule.modules.eventing.vo.TransportConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration(friendlyName = "Configuration")
//@ConnectionManagement(friendlyName = "Configuration")
public class ConnectorConfig {
	
	private static Logger logger = LoggerFactory.getLogger(ConnectorConfig.class);
	
	 @Configurable
	 @Placement(group = "RAML Specification")
	 @Path
	 @FriendlyName("Consumer Specification")
	 private String consumerSpecificationFile;
	 @Configurable
	 @Placement(group = "RAML Specification")
	 @Path
	 @FriendlyName("Producer Specification")
	 private String producerSpecificationFile;
	
	 /*
	@Configurable
	@Optional
	@Path
    @Placement(tab="Implementation Details", group = "General Information", order = 1)
    private String transportLibrary;
	@Configurable
	@Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 2)
    private String connectionClass;
    @Configurable
    @Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 3)
    private String vendor;
    public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	@Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 4)
    private String destinationName;
    @Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 5)
    private String destinationType;
    @Configurable
    @Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 6)
    private String connectionUrl;
    @Configurable
    @Optional
    @Placement(tab="Implementation Details", group = "General Information", order = 7)
    private String persistenceServiceUrl;
   */
    
 /*
	@Connect
   @TestConnectivity(active = true)
   public void connect(@ConnectionKey String username, @Password String password,@ConnectionKey String host, @ConnectionKey String port) throws ConnectionException {
   
		System.out.println("+++Attempting to do a Message connector connection with params: user="+username+", pass="+password+",host="+host+",port="+port);
    	logger.info("-----Info Logging------");
    
    	ConnectorConfigVO conVo= new ConnectorConfigVO();
    	conVo.setConsumerRAML(consumerSpecificationFile);
    	conVo.setProdcuerRAML(producerSpecificationFile);
    	conVo.setHost(host);
    	conVo.setPort(port);
    	conVo.setUsername(username);
    	conVo.setPassword(password);
    	
    	TransportConfigVO _transport = RAMLParserWrapper.getInstance().parseConfigurationFile(conVo);
    	Util.getInstance().setTransportConfig(_transport);
    	
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getConsumerApi());
    	ModelPersistence.getInstance().storeModel(RAMLParserWrapper.getInstance().getProducerApi());
    	
    	
    }
	*/
	 
    /**
     * Disconnect
     */
	/*
    @Disconnect
    public void disconnect() {
        
    	System.out.println("---Disconnecting---");
    }
	 */

    /**
     * Are we connected
     */
	
	 /*
    @ValidateConnection
    public boolean isConnected() {
        
    	System.out.println("---Validating Connection !!!!!");
        return false;
    }
   */

    /**
     * Are we connected
     */
	 /*
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }
	*/
	 

	public String getConsumerSpecificationFile() {
		return consumerSpecificationFile;
	}

	public void setConsumerSpecificationFile(String specificationFile) {
		this.consumerSpecificationFile = specificationFile;
	}
	

	public String getProducerSpecificationFile() {
		return producerSpecificationFile;
	}
	public void setProducerSpecificationFile(String producerSpecificationFile) {
		this.producerSpecificationFile = producerSpecificationFile;
	}
	/*
	public String getTransportLibrary() {
		return transportLibrary;
	}

	public void setTransportLibrary(String transportLibrary) {
		this.transportLibrary = transportLibrary;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}


	public String getConnectionClass() {
		return connectionClass;
	}

	public void setConnectionClass(String conectionClass) {
		this.connectionClass = conectionClass;
	}
	   public String getPersistenceServiceUrl() {
			return persistenceServiceUrl;
		}
		public void setPersistenceServiceUrl(String persistenceServiceUrl) {
			this.persistenceServiceUrl = persistenceServiceUrl;
		}
		*/



}