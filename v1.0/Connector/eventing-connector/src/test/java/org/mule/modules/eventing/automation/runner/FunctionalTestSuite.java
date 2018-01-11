package org.mule.modules.eventing.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.eventing.automation.functional.AddEntityTestCases;
import org.mule.modules.eventing.automation.functional.GetRecentMessagesTestCases;
import org.mule.modules.eventing.automation.functional.GreetTestCases;
import org.mule.modules.eventing.automation.functional.QueryProcessorTestCases;
import org.mule.modules.eventing.EventingConnector;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
	AddEntityTestCases.class,
	GetRecentMessagesTestCases.class,

GreetTestCases.class
	,QueryProcessorTestCases.class
})

public class FunctionalTestSuite {
	
	@BeforeClass
	public static void initialiseSuite(){
		ConnectorTestContext.initialize(EventingConnector.class);
	}
	
	@AfterClass
    public static void shutdownSuite() {
    	ConnectorTestContext.shutDown();
    }
	
}