
package org.mule.modules.eventedapi.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.modules.eventedapi.EventedApiConnector;


/**
 * A <code>EventedApiConnectorCapabilitiesAdapter</code> is a wrapper around {@link EventedApiConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2018-02-13T08:01:58-05:00", comments = "Build UNNAMED.2793.f49b6c7")
public class EventedApiConnectorCapabilitiesAdapter
    extends EventedApiConnector
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
