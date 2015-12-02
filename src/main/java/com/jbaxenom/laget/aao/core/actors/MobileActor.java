package com.jbaxenom.laget.aao.core.actors;

import io.appium.java_client.AppiumDriver;

/**
 * @author jbaxenom on 8/19/14.
 */
public interface MobileActor {

    AppiumDriver getDriver();

    AppiumDriver initializeAppium();

}
