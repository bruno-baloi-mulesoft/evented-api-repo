package org.mule.modules.eventing;

import java.util.List;

import org.mule.api.MuleException;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

public class PagesProvider extends
		ProviderAwarePagingDelegate<Object, EventingConnector> {

	public PagesProvider(PagingConfiguration pagingConfiguration) {
		// If you don't need the page configuration this can be removed.
	}

	@Override
	public void close() throws MuleException {
		// Close any resource if required
	}

	@Override
	public List<Object> getPage(EventingConnector connector) throws Exception {
		// Use the connector to get the pages
		// Return null or an empty list to indicate no more pages available
		return null;
	}

	@Override
	public int getTotalResults(EventingConnector connector) throws Exception {
		// To specify an unknown amount of pages, return -1
		return -1;
	}

}
