package com.jbaxenom.laget.interactions.webdriver.pages.examples;

import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object implementation example of fictitious web page with one element named "ThisElement"
 *
 * @author jbaxenom
 */
public class ExampleWebPage {

    /**
     * Example Interaction using Selenide
     *
     * @return same page for method chaining purposes
     */
    public ExampleWebPage clickThisElement() {
        $("#thisElemendId").click();
        return this;
    }

    /**
     * Example Verifier
     *
     * @return True if element is displayed, False otherwise
     */
    public boolean isThisElementDisplayed() {
        return $("thisElemendId").isDisplayed();
    }

    /**
     * Example Information Getter
     *
     * @return the inner text in the ThisElement element
     */
    public String getThisElementText() {
        return $("thisElementId").text();
    }
}
