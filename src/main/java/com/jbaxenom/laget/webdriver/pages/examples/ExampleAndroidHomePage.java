package com.jbaxenom.laget.webdriver.pages.examples;

import com.jbaxenom.laget.webdriver.pages.AbstractMobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Android Page Object implementation example of a typical login using Appium framework
 *
 * @author jbaxenom
 */
public class ExampleAndroidHomePage extends AbstractMobilePage {

    // Elements

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ImageView[1]")
    private AndroidElement loggedUsername;

    // Constructor

    public ExampleAndroidHomePage(AppiumDriver driver) {
        super(driver);
    }

    // Attribute Getters
    public String getLoggedUsername() {
        return loggedUsername.getText();
    }

}
