package org.mule.modules.eventing.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.eventing.EventingConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class AddEntityTestCases extends
		AbstractTestCase<EventingConnector> {

	public AddEntityTestCases() {
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
    public void verify() throws Exception {
    	java.util.Map<java.lang.String, java.lang.Object> expected = null;
		java.lang.String key = null;
		java.util.Map<java.lang.String, java.lang.Object> entity = null;
		//assertEquals(getConnector().addEntity(key, entity), expected);
    }
    
}