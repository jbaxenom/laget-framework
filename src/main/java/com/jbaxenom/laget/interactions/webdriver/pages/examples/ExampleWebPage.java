package com.jbaxenom.laget.interactions.webdriver.pages.examples;

import com.jbaxenom.laget.interactions.webdriver.pages.AbstractWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object implementation example of fictitious web page with one element named "ThisElement"
 *
 * @author jbaxenom
 */
public class ExampleWebPage extends AbstractWebPage {

    @FindBy(id = "thisElementId")
    private WebElement thisElement;

    public ExampleWebPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Example Interaction
     *
     * @return same page for method chaining purposes
     */
    public ExampleWebPage clickThisElement() {
        thisElement.click();
        return this;
    }

    /**
     * Example Verifier
     *
     * @return True if element is displayed, False otherwise
     */
    public boolean isThisElementDisplayed() {
        return isElementDisplayed(thisElement);
    }

    /**
     * Example Information Getter
     *
     * @return the inner text in the ThisElement element
     */
    public String getThisElementText() {
        return thisElement.getText();
    }
}
