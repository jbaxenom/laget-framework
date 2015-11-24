package com.jbaxenom.laget.webdriver.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Android Page Object implementation example of a typical login using Appium framework
 *
 * @author jbaxenom
 */
public class ExampleAndroidLoginPage extends AbstractMobilePage {

    // Elements

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ImageView[1]")
    private AndroidElement logo;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.EditText[1]")
    private AndroidElement usernameInput;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.EditText[2]")
    private AndroidElement passwordInput;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.Switch[1]")
    private AndroidElement autoLoginButton;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.widget.Button[1]")
    private AndroidElement submitButton;

    // Constructor

    public ExampleAndroidLoginPage(AppiumDriver driver) {
        super(driver);
    }

    // Interactors

    public ExampleAndroidLoginPage enterUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public ExampleAndroidLoginPage enterPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public ExampleAndroidHomePage clickSubmitButton() {
        submitButton.click();
        return new ExampleAndroidHomePage(driver);
    }

    // Presence Verifiers

    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }

    // Actions

    public ExampleAndroidHomePage login(String username, String password) {
        return enterUsername(username).enterPassword(password).clickSubmitButton();
    }

}
