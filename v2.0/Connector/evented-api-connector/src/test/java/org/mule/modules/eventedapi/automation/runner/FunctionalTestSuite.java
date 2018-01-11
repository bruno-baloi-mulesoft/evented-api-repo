package org.mule.modules.eventedapi.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.eventedapi.automation.functional.AddEntityTestCases;
import org.mule.modules.eventedapi.automation.functional.GetRecentMessagesTestCases;
import org.mule.modules.eventedapi.automation.functional.GreetTestCases;
import org.mule.modules.eventedapi.EventedApiConnector;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
	AddEntityTestCases.class,
	GetRecentMessagesTestCases.class,

GreetTestCases.class
})

public class FunctionalTestSuite {
	
	@BeforeClass
	public static void initialiseSuite(){
		ConnectorTestContext.initialize(EventedApiConnector.class);
	}
	
	@AfterClass
    public static void shutdownSuite() {
    	ConnectorTestContext.shutDown();
    }
	
}