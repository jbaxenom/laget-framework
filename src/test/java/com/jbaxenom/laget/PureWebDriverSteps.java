package com.jbaxenom.laget;

import com.jbaxenom.laget.aao.core.actors.WebAppActor;
import com.jbaxenom.laget.aao.examples.actors.ExampleWebUser;
import com.jbaxenom.laget.configuration.Configuration;
import com.jbaxenom.laget.cucumber.EnvironmentAwareContext;
import com.jbaxenom.laget.webdriver.WebDriverBuilder;
import com.saucelabs.saucerest.SauceREST;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author jbaxenom
 */
public class PureWebDriverSteps {

    private Scenario scenario;
    private WebDriver driver;
    private final EnvironmentAwareContext context;

    public PureWebDriverSteps(EnvironmentAwareContext context) {
        this.context = context;

        // Retrieve the environment where to run the tests
        this.context.setEnvironment(Configuration.getEnvironment());
    }

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;

        String testParameters = " (" + Configuration.getBrowser() + " " + Configuration.getBrowserVersion() + ", "
                + Configuration.getOS() + ")";
        System.setProperty("SCENARIO_NAME", this.scenario.getName() + testParameters);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            if (scenario.isFailed()) {
                scenario.embed(saveRawScreenshot(), "image/png");
            }

            if (Configuration.getGridUrl().equals("SAUCE")) {
                reportTestResultToSauce();
            }

            driver.quit();
        }
    }

    private void reportTestResultToSauce() {
        SauceREST client = new SauceREST(Configuration.sauceLabsUser.get(), Configuration.sauceLabsKey.get());
        String jobId = System.getProperty("SESSION_ID");
        if (scenario.isFailed()) {
            client.jobFailed(jobId);
        } else {
            client.jobPassed(jobId);
        }
    }

    /**
     * Saves a screenshot, in png format, of the contents of the focused window
     * in the precise moment the method is called. The file is saved in the
     * "target/screenshots/" folder inside the project root and it includes
     * the timestamp in the file name.
     */
    private byte[] saveRawScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Given("^there is a user with username \"([^\"]*)\" and password \"([^\"]*)\" in the system$")
    public void thereIsAUserWithUsernameAndPassword(String username, String password) {
        ExampleWebUser user = new ExampleWebUser(username, password);
        this.driver = user.getDriver();
        context.setLastActor(user);
    }

    @Given("^the (.*) opens the page \"([^\"]*)\"$")
    public void theUserOpensThePage(String user, String url) {
        if (driver == null) {
            driver = new WebDriverBuilder()
                    .withBrowser(Configuration.getBrowser())
                    .withBrowserVersion(Configuration.getBrowserVersion())
                    .withOS(Configuration.getOS())
                    .withGridUrl(Configuration.getGridUrl())
                    .build();
        }

        driver.get(url);
    }

    @Given("^he maximises the window$")
    public void heMaximisesTheWindow() {
        driver.manage().window().maximize();
    }

    @When("^he types his ([^\"]*) in the element with ([^\"]*) \"(.*)\"$")
    public void heTypesHisCredentialInElement(String field, String locatorType, String locator) {
        String text;
        switch (field) {
            case "username":
                text = ((WebAppActor) context.getLastActor()).username();
                break;
            case "password":
                text = ((WebAppActor) context.getLastActor()).password();
                break;
            default:
                throw new UnsupportedOperationException("The user field '" + field + "' is not supported yet.");
        }

        driver.findElement(locateElement(locatorType, locator)).sendKeys(text);
    }

    @When("^he types \"([^\"]*)\" in the element with ([^\"]*) \"(.*)\"$")
    public void heTypesSomethingInElement(String text, String locatorType, String locator) {
        driver.findElement(locateElement(locatorType, locator)).sendKeys(text);
    }

    @When("^he (?!types)(.*) in the element with ([^\"]*) \"(.*)\"$")
    public void heDoesSomethingInTheElement(String action, String locatorType, String locator) {
        WebElement element = driver.findElement(locateElement(locatorType, locator));

        switch (action) {
            case "hovers":
                Actions builder = new Actions(driver);
                builder.moveToElement(element).build().perform();
                break;
            case "clicks":
                element.click();
                break;
            default:
                throw new UnsupportedOperationException("The action '" + action + "' is not supported yet.");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    @When("^he presses the \"(.*)\" key on the element with ([^\"]*) \"(.*)\"$")
    public void hePressesTheKeyInAnElement(String key, String locatorType, String locator) {
        Actions builder = new Actions(driver);
        Keys keyName;
        switch (key) {
            case "ENTER":
                keyName = Keys.ENTER;
                break;
            default:
                throw new UnsupportedOperationException("The key '" + key + "' is not supported yet.");
        }
        builder.sendKeys(driver.findElement(locateElement(locatorType, locator)), keyName).build().perform();
    }

    @When("^he presses the \"(.*)\" key$")
    public void hePressesTheKey(String key) {
        Actions builder = new Actions(driver);
        Keys keyName;
        switch (key) {
            case "ENTER":
                keyName = Keys.ENTER;
                break;
            default:
                throw new UnsupportedOperationException("The key '" + key + "' is not supported yet.");
        }
        builder.sendKeys(keyName).build().perform();
    }


    @Then("the element with ([^\"]*) \"(.*)\" should ([^\"]*)")
    public void theElementShould(String locatorType, String locator, String condition) {
        switch (condition) {
            case "be visible":
            case "be displayed":
                assertThat(driver.findElement(locateElement(locatorType, locator)).isDisplayed()).isTrue();
                break;
            case "not be visible":
            case "not be displayed":
                boolean displayed;
                try {
                    displayed = driver.findElement(locateElement(locatorType, locator)).isDisplayed();
                } catch (NoSuchElementException e) {
                    displayed = false;
                }
                assertThat(displayed).isFalse();
                break;
            case "be enabled":
                assertThat(driver.findElement(locateElement(locatorType, locator)).isEnabled()).isTrue();
                break;
            case "be disabled":
                assertThat(driver.findElement(locateElement(locatorType, locator)).isEnabled()).isFalse();
                break;
            case "be selected":
                assertThat(driver.findElement(locateElement(locatorType, locator)).isSelected()).isTrue();
                break;
            case "not be selected":
                assertThat(driver.findElement(locateElement(locatorType, locator)).isSelected()).isFalse();
                break;
            default:
                throw new UnsupportedOperationException("The condition '" + condition + "' is not supported yet.");
        }
    }

    @Then("the element with ([^\"]*) \"(.*)\" should have the text \"([^\"]*)\"")
    public void theElementHasTheText(String locatorType, String locator, String text) {
        WebElement element = driver.findElement(locateElement(locatorType, locator));

        assertThat(element.getText()).isEqualTo(text);
    }

    private By locateElement(String locatorType, String locator) {
        By by;
        switch (locatorType) {
            case "css":
                by = By.cssSelector(locator);
                break;
            case "xpath":
                by = By.xpath(locator);
                break;
            case "id":
                by = By.id(locator);
                break;
            case "className":
                by = By.className(locator);
                break;
            case "name":
                by = By.name(locator);
                break;
            case "linkText":
                by = By.linkText(locator);
                break;
            case "tagName":
                by = By.tagName(locator);
                break;
            case "partialLinkText":
                by = By.partialLinkText(locator);
                break;
            default:
                throw new UnsupportedOperationException("The locatorType '" + locatorType + "' is not supported");
        }
        return by;
    }

}
