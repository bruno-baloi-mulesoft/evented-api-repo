
package org.mule.modules.eventing.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.modules.eventing.EventingConnector;


/**
 * A <code>EventingConnectorCapabilitiesAdapter</code> is a wrapper around {@link EventingConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-08-14T09:05:37-04:00", comments = "Build UNNAMED.2793.f49b6c7")
public class EventingConnectorCapabilitiesAdapter
    extends EventingConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
