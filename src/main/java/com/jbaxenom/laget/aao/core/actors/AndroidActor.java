package com.jbaxenom.laget.aao.core.actors;

import com.jbaxenom.laget.configuration.Configuration;
import com.jbaxenom.laget.webdriver.WebDriverBuilder;
import hu.meza.aao.Actor;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author jbaxenom on 8/19/14.
 */
public abstract class AndroidActor extends Actor implements MobileActor {

    private final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public AndroidActor() {
        this.driver.set(initializeAppium());
    }

    public AndroidDriver getDriver() {
        return this.driver.get();
    }

    public AndroidDriver initializeAppium() {
        return (AndroidDriver) new WebDriverBuilder()
                .withBrowser(Configuration.getBrowser())
                .withBrowserVersion(Configuration.getBrowserVersion())
                .withOS(Configuration.getOS())
                .withGridUrl(Configuration.getGridUrl())
                .buildMobile();
    }

}
