
package org.mule.modules.eventedapi.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/evented-api</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2018-02-13T08:01:58-05:00", comments = "Build UNNAMED.2793.f49b6c7")
public class EventedApiNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(EventedApiNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [evented-api] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [evented-api] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new EventedApiConnectorConnectorConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("send-event", new EventProducerDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("send-event", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("event-consumer", new EventConsumerDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("event-consumer", "@Source", ex);
        }
    }

}
