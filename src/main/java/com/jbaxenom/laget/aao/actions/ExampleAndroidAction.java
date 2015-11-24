package com.jbaxenom.laget.aao.actions;

import com.jbaxenom.laget.aao.core.actions.MobileAction;
import com.jbaxenom.laget.webdriver.pages.ExampleAndroidLoginPage;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriverException;

/**
 * @author jbaxenom
 */
public class ExampleAndroidAction extends MobileAction {

    private final String username;
    private final String password;

    private ExampleAndroidLoginPage loginPage;

    public ExampleAndroidAction(AndroidDriver driver, String username, String password) {
        super(driver);
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() throws WebDriverException {
        //driver.launchApp();
        loginPage = new ExampleAndroidLoginPage(driver);
        isSuccessful = loginPage.isLogoDisplayed() && loginPage.login(username, password).getLoggedUsername().equals(username);
    }

    public ExampleAndroidLoginPage mobilePage() {
        return loginPage;
    }

    @Override
    public ExampleAndroidAction copyOf() {
        return this;
    }

}
