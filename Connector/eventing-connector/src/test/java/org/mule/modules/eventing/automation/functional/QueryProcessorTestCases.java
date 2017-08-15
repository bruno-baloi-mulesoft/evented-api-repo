package org.mule.modules.eventing.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.eventing.EventingConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class QueryProcessorTestCases extends
		AbstractTestCase<EventingConnector> {

	public QueryProcessorTestCases() {
		super(EventingConnector.class);
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
    public void verify() {
        java.util.List<java.lang.Object> expected = null;
		java.lang.String query = null;
		//assertEquals(getConnector().queryProcessor(query), expected);
    }
    
}