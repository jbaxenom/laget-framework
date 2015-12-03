package com.jbaxenom.laget.domain.core.actors;

import com.jbaxenom.laget.configuration.Configuration;
import com.jbaxenom.laget.interactions.webdriver.WebDriverBuilder;
import hu.meza.aao.Actor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jbaxenom on 8/19/14.
 */
public abstract class WebActor extends Actor {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebActor() {
        this.driver.set(initializeWebDriver());
    }

    public WebActor(WebDriver driver) {
        this.driver.set(driver);
    }

    // Actions

    public void openURL(String url) {
        getDriver().get(url);
    }

    public void waitUntil(ExpectedCondition condition, long timeoutInSeconds) {
        new WebDriverWait(getDriver(), timeoutInSeconds).until(condition);
    }


    // WebDriver Initialisation
    private WebDriver initializeWebDriver() {
        return new WebDriverBuilder()
                .withBrowser(Configuration.getBrowser())
                .withBrowserVersion(Configuration.getBrowserVersion())
                .withOS(Configuration.getOS())
                .withGridUrl(Configuration.getGridUrl())
                .build();
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Saves a screenshot, in png format, of the contents of the focused window
     * in the precise moment the method is called. The file is saved in the
     * "target/screenshots/" folder inside the project root and it includes
     * the timestamp in the file name.
     */
    public void saveScreensthot() {
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            String basePath = "target/screenshots/";
            String filename = "error_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
            String filepath = basePath + filename;
            FileUtils.copyFile(scrFile, new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] saveRawScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
