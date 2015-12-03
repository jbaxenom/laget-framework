package com.jbaxenom.laget.domain.examples.actions;

import com.jbaxenom.laget.domain.core.actions.WebAction;
import com.jbaxenom.laget.interactions.webdriver.pages.examples.ExampleWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * @author jbaxenom
 */
public class ExampleWebAction extends WebAction {

    protected final String username;
    protected final String password;

    private boolean isSuccessful;
    private ExampleWebPage examplePage;

    public ExampleWebAction(WebDriver driver, String username, String password) {
        super(driver);
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() throws WebDriverException {
        driver.get(environment.appURL());
        examplePage = new ExampleWebPage(driver);
        isSuccessful = examplePage.clickThisElement().isThisElementDisplayed();
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    public ExampleWebPage examplePage() {
        return examplePage;
    }

    @Override
    public ExampleWebAction copyOf() {
        return this;
    }

}
