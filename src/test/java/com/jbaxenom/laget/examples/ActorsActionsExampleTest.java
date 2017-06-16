package com.jbaxenom.laget.examples;

import com.jbaxenom.laget.configuration.retries.Retry;
import com.jbaxenom.laget.domain.examples.actors.ExampleWebUser;
import org.testng.annotations.*;

import static com.google.common.truth.Truth.assertWithMessage;

/**
 * @author chema.delbarco
 */
public class ActorsActionsExampleTest {

    private ExampleWebUser webUser;

    @BeforeMethod
    public void setup() {

        // GIVEN there is a user with username "chema" and password "pass"
        webUser = new ExampleWebUser("chema", "pass");
    }

    @AfterMethod
    public void tearDown() {
        webUser.closeBrowser();
    }

    @Test
    @Retry(count = 3)
    public void actorsAndActionsExampleTest() {

        // WHEN the user performs its example web action, THEN he does it successfully
        assertWithMessage("The action was not successful").that(webUser.exampleWebAction().isSuccessful()).isTrue();
    }

}
