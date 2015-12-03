package com.jbaxenom.laget.interactions.webdriver.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

/**
 * @author jbaxenom
 */
public abstract class AbstractMobilePage {

    protected final AppiumDriver driver;

    public AbstractMobilePage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isElementDisplayed(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            return false;
        }
    }

}
