package org.mule.modules.eventedapi;

import java.util.List;

import org.mule.api.MuleException;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.ProviderAwarePagingDelegate;

public class PagesProvider extends
		ProviderAwarePagingDelegate<Object, EventedApiConnector> {

	public PagesProvider(PagingConfiguration pagingConfiguration) {
		// If you don't need the page configuration this can be removed.
	}

	@Override
	public void close() throws MuleException {
		// Close any resource if required
	}

	@Override
	public List<Object> getPage(EventedApiConnector connector) throws Exception {
		// Use the connector to get the pages
		// Return null or an empty list to indicate no more pages available
		return null;
	}

	@Override
	public int getTotalResults(EventedApiConnector connector) throws Exception {
		// To specify an unknown amount of pages, return -1
		return -1;
	}

}
