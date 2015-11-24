package com.jbaxenom.laget.aao.actors;

import com.jbaxenom.laget.aao.actions.ExampleWebAction;
import com.jbaxenom.laget.aao.core.actors.WebAppActor;

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
