package com.jbaxenom.laget.webdriver.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Abstract Web Page Object class that implements lazy initialization of WebElement attributes using the PageFactory
 * class, to be used in all web page objects. It also provides error-safe encapsulations for verifying and waiting for
 * elements to be displayed, which allows for more precise logging of test results.
 *
 * @author jbaxenom
 */
public abstract class AbstractWebPage {

    protected final WebDriver driver;

    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 5;

    public AbstractWebPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies if an element is displayed or not.
     *
     * @param element {@link WebElement} to verify if it is displayed or not
     * @return {@code true} if it is displayed, {@code false} otherwise
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (TimeoutException | ElementNotVisibleException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Verifies sequentially if a list of elements are displayed or not.
     *
     * @param elements comma-separated list of {@link WebElement} to verify visibility
     * @return {@code true} if all are displayed, {@code false} if at least one is not
     */
    public boolean areElementsDisplayed(WebElement... elements) {
        for (WebElement element : elements) {
            if (!isElementDisplayed(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if at least one element in the list of elements is displayed.
     *
     * @param elements comma-separated list of {@link WebElement} to verify visibility
     * @return {@code true} if at least one is displayed, {@code false} if none are
     */
    public boolean isAnyElementDisplayed(WebElement... elements) {
        for (WebElement element : elements) {
            if (isElementDisplayed(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Waits {@code timeoutInSeconds} seconds for an element to be displayed.
     *
     * @param element          {@link WebElement} to wait until it is displayed
     * @param timeoutInSeconds time out in seconds
     * @return {@code true} if it was displayed, {@code false} if it timed out
     */
    public boolean waitForElementDisplayed(WebElement element, int timeoutInSeconds) {
        try {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void explicitWait(int timeoutInSeconds) {
        try {
            Thread.sleep(timeoutInSeconds * 1000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Waits {@code DEFAULT_TIMEOUT_IN_SECONDS} seconds for an element to be displayed.
     *
     * @param element {@link WebElement} to wait until it is displayed
     * @return {@code true} if the element appeared, {@code false} if it timed out
     */
    public boolean waitForElementDisplayed(WebElement element) {
        return waitForElementDisplayed(element, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    /**
     * Waits {@code timeoutInSeconds} seconds for each of the elements passed to be displayed. Returns as soon as one
     * is not but waits sequentially for all of them.
     *
     * @param timeoutInSeconds time out in seconds
     * @param elements         comma-separated list of {@link WebElement} to wait until they are displayed
     * @return {@code true} if all elements appeared, {@code false} if at least one did not
     */
    public boolean waitForElementsDisplayed(int timeoutInSeconds, WebElement... elements) {
        for (WebElement element : elements) {
            if (!waitForElementDisplayed(element, timeoutInSeconds)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Waits {@code DEFAULT_TIMEOUT_IN_SECONDS} seconds for each of the elements passed to be displayed. Returns as soon
     * as one is not but waits sequentially for all of them.
     *
     * @param elements comma-separated list of {@link WebElement} to wait until they are displayed
     * @return {@code true} if all elements appeared, {@code false} if at least one did not
     */
    public void waitForElementsDisplayed(WebElement... elements) {
        waitForElementsDisplayed(DEFAULT_TIMEOUT_IN_SECONDS, elements);
    }

}
