package com.jbaxenom.laget.domain.core.actions;

import com.jbaxenom.laget.configuration.Configuration;
import com.jbaxenom.laget.configuration.Environment;
import hu.meza.aao.Action;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriverException;

/**
 * @author jbaxenom on 4/6/14.
 */
public abstract class MobileAction implements Action {

    protected Environment environment;
    protected final AppiumDriver driver;
    protected boolean isSuccessful;

    public MobileAction(AppiumDriver driver) {
        this.driver = driver;
        this.environment = Configuration.getEnvironment();
    }

    /**
     * {@inheritDoc}
     */
    public abstract void execute() throws WebDriverException;

    /**
     * {@inheritDoc}
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

}
