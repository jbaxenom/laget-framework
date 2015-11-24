package com.jbaxenom.laget.aao.core.actors;

import io.appium.java_client.AppiumDriver;

/**
 * Created by jbaxenom on 2014-08-19.
 */
public interface MobileActor {

    AppiumDriver getDriver();

    AppiumDriver initializeAppium();

}
