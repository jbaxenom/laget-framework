package com.jbaxenom.laget.aao.examples.actors;

import com.jbaxenom.laget.aao.core.actors.WebAppActor;
import com.jbaxenom.laget.aao.examples.actions.ExampleWebAction;

/**
 * @author jbaxenom
 */
public class ExampleWebUser extends WebAppActor {

    public ExampleWebUser(String username, String password) {
        super(username, password);
    }

    // Actions
    public ExampleWebAction exampleWebAction() {
        ExampleWebAction action = new ExampleWebAction(getDriver(), username(), password());
        action.execute();
        return action;
    }

}
