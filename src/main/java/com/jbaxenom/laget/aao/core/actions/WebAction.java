package com.jbaxenom.laget.aao.core.actions;

import com.jbaxenom.laget.configuration.Configuration;
import com.jbaxenom.laget.configuration.Environment;
import hu.meza.aao.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * @author jbaxenom on 4/6/14.
 */
public abstract class WebAction implements Action {

    protected Environment environment;
    protected final WebDriver driver;
    protected boolean isSuccessful;

    public WebAction(WebDriver driver) {
        this.driver = driver;
        this.environment = Configuration.getEnvironment();
    }

    /**
     * Executes the action, which implies performing a GUI interaction, usually
     * starting in the URL specified in the {@code startUrl} attribute.
     */
    @Override
    public abstract void execute() throws WebDriverException;

    /**
     * The implementation of this method must verify if the action has been
     * successful or not.
     *
     * @return
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

}
