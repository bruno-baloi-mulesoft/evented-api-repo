
package org.mule.modules.eventedapi.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>EventedApiConnectorProcessAdapter</code> is a wrapper around {@link EventedApiConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2018-02-13T08:01:58-05:00", comments = "Build UNNAMED.2793.f49b6c7")
public class EventedApiConnectorProcessAdapter
    extends EventedApiConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<EventedApiConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, EventedApiConnectorCapabilitiesAdapter> getProcessTemplate() {
        final EventedApiConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,EventedApiConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, EventedApiConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, EventedApiConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
