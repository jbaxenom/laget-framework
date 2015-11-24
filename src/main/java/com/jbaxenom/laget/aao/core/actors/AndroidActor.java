package com.jbaxenom.laget.aao.core.actors;

import com.jbaxenom.laget.aao.core.actions.MobileAction;
import com.jbaxenom.laget.configuration.Configuration;
import hu.meza.aao.Actor;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jbaxenom on 2014-08-19.
 */
public abstract class AndroidActor extends Actor implements MobileActor {

    private final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public AndroidActor() {
        this.driver.set(initializeAppium());
    }

    public AndroidDriver getDriver() {
        return this.driver.get();
    }

    private AndroidDriver initializeAppium() {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, Configuration.androidAppPath.get());
        File app = new File(appDir, Configuration.androidAppName.get());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", Configuration.androidAppPackage.get());
        capabilities.setCapability("appActivity", Configuration.androidAppActivity.get());

        URL gridUrl;
        try {
            gridUrl = new URL(Configuration.appiumGridUrl.get());
        } catch (MalformedURLException e) {
            gridUrl = null;
        }
        return new AndroidDriver(gridUrl, capabilities);
    }

    public abstract MobileAction login();

}
