package org.mule.modules.eventedapi.automation.functional;

import static org.junit.Assert.*;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class GetRecentMessagesTestCases extends 
		AbstractTestCase<EventedApiConnector> {

	public GetRecentMessagesTestCases() {
		super(EventedApiConnector.class);
	}

	@Before
	public void setup() {
		// TODO
	}

	@After
	public void tearDown() {
		// TODO
	}

    @Test
	public void verify() throws Throwable {
		Object[] args = new Object[] { null }; // TODO initialize parameters
		int expectedSize = 0;
		Collection<?> result = (Collection<?>) getDispatcher()
				.runPaginatedMethod("getRecentMessages", args);
		assertEquals(expectedSize, result.size());
	}
	
}