package com.jbaxenom.laget;

import com.jbaxenom.laget.configuration.Configuration;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.Rule;
import org.testng.annotations.AfterClass;

/**
 * Created by jbaxenom on 5/6/14.
 */
@CucumberOptions(features = "src/test/resources/com/jbaxenom/laget",
        plugin = {"pretty", "html:target/cucumber"},
        glue = "com.jbaxenom.laget")
public class RunCucumberTest extends AbstractTestNGCucumberTests implements SauceOnDemandSessionIdProvider {

    public SauceOnDemandAuthentication authentication =
            new SauceOnDemandAuthentication(Configuration.sauceLabsUser.get(), Configuration.sauceLabsKey.get());

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @AfterClass
    public void printReportLinkInSystemOut() {
        System.out.println("\n\nSee the Cucumber HTML report in: \"file://" + System.getProperty("user.dir")
                + "/target/cucumber/index.html\" (CTRL+Click to go to link)\n\n");
    }

    @Override
    public String getSessionId() {
        return System.getProperty("SESSION_ID");
    }

}
