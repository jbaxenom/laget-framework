package com.jbaxenom.laget.cucumber;

import com.jbaxenom.laget.configuration.Environment;
import hu.meza.aao.DefaultScenarioContext;

/**
 * Adds an Environment variable to the default AAO's context for easier handling in the cucumber test suites
 * <p>
 * @author jbaxenom on 11/14/14.
 */
public class EnvironmentAwareContext extends DefaultScenarioContext {

    private Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void clean() {
        super.clean();
        environment = null;
    }

}
