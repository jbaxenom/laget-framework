package com.jbaxenom.laget.domain.examples.actors;

import com.jbaxenom.laget.domain.core.actors.WebAppActor;
import com.jbaxenom.laget.domain.examples.actions.ExampleWebAction;

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
