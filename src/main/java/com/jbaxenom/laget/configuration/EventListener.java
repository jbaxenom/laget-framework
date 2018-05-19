package com.jbaxenom.laget.configuration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by jdelbarc on 29/02/16.
 */
public class EventListener extends AbstractWebDriverEventListener {

    static Logger log = Logger.getLogger(EventListener.class);

    public EventListener() {
        super();

        PropertyConfigurator.configure("java/resources/log4j.properties");
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.debug("[" + System.currentTimeMillis() + "] Clicking on element at coordinates" + element.getLocation());
    }

    @Override
    public void onException(Throwable arg0, WebDriver driver) {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere

        try {
            FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
