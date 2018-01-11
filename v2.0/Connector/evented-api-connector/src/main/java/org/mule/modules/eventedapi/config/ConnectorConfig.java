package org.mule.modules.eventedapi.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Path;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

	
	private static Logger logger = LoggerFactory.getLogger(EventedApiConnector.class);
	
	 @Configurable
	 @Placement(group = "AMF Parse Command")
	 @Path
	 @FriendlyName("AMF Parse Command (full path)")
	 private String amParseCmd;
	 @Configurable
	 @Placement(group = "AMF Library Location")
	 @Path
	 @FriendlyName("AMF Model Location (full path)")
	 private String amfLib;
	 @Configurable
	 @Placement(group = "AMF Document Specification")
	 @Path
	 @FriendlyName("AMF Document Location (full path)")
	 private String amfDocSpecificationFile;
	 @Configurable
	 @Placement(group = "AMF Dialect Specification")
	 @Path
	 @FriendlyName("AMF Dialect Location (directory path)")
	 private String amfDialectSpecificationDir;	 
	 @Configurable
	 @Placement(group = "EventedAPI Connector config")
	 @Path
	 @FriendlyName("EventedAPI Connector properties (directory path)")
	 private String eventedAPiConnectorProperties;	
	 
	
	 public String getAmfDocSpecificationFile() {
			return amfDocSpecificationFile;
		}
		public void setAmfDocSpecificationFile(String amfDocSpecificationFile) {
			this.amfDocSpecificationFile = amfDocSpecificationFile;
		}
		public String getAmfDialectSpecificationDir() {
			return amfDialectSpecificationDir;
		}
		public void setAmfDialectSpecificationDir(String amfDialectSpecificationDir) {
			this.amfDialectSpecificationDir = amfDialectSpecificationDir;
		}
		
		public String getAmfLib() {
			return amfLib;
		}
		public void setAmfLib(String amfLib) {
			this.amfLib = amfLib;
		}
		public String getAmParseCmd() {
			return amParseCmd;
		}
		public void setAmParseCmd(String amParseCmd) {
			this.amParseCmd = amParseCmd;
		}
		public String getEventedAPiConnectorProperties() {
			return eventedAPiConnectorProperties;
		}
		public void setEventedAPiConnectorProperties(String eventedAPiConnectorProperties) {
			this.eventedAPiConnectorProperties = eventedAPiConnectorProperties;
		}
		
	
	/*
    
    @Configurable
    @Default("Hello")
    private String greeting;

    
    @Configurable
    @Default("How are you?")
    private String reply;

  
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

   
    public String getGreeting() {
        return this.greeting;
    }

  
    public void setReply(String reply) {
        this.reply = reply;
    }

    
    public String getReply() {
        return this.reply;
    }
    */

}