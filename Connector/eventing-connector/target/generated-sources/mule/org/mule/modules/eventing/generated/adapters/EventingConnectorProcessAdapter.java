
package org.mule.modules.eventing.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.eventing.EventingConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>EventingConnectorProcessAdapter</code> is a wrapper around {@link EventingConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-08-14T09:05:37-04:00", comments = "Build UNNAMED.2793.f49b6c7")
public class EventingConnectorProcessAdapter
    extends EventingConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<EventingConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, EventingConnectorCapabilitiesAdapter> getProcessTemplate() {
        final EventingConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,EventingConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, EventingConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, EventingConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
