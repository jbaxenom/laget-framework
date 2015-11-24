package com.jbaxenom.laget.aao.core.actors;

import com.jbaxenom.laget.aao.core.actions.APIAction;
import hu.meza.aao.Actor;
import hu.meza.tools.HttpClientWrapper;

/**
 * Created by jbaxenom on 4/30/14.
 */
public class APIActor extends Actor {

    private final HttpClientWrapper client;

    public APIActor() {
        client = new HttpClientWrapper();
    }

    public HttpClientWrapper getClient() {
        return client;
    }

    public void execute(APIAction action) {
        super.execute(action);
    }

}
