package com.jbaxenom.laget.domain.core.actors;

import com.jbaxenom.laget.interactions.api.entities.APICredentials;
import hu.meza.aao.Actor;
import hu.meza.tools.HttpClientWrapper;

/**
 * @author jbaxenom on 4/30/14.
 */
public abstract class APIActor extends Actor {

    private final HttpClientWrapper client;
    private APICredentials credentials;

    public APIActor() {
        this.client = new HttpClientWrapper();
        this.credentials = new APICredentials();
    }

    public APIActor(String username, String password) {
        this.client = new HttpClientWrapper();
        this.credentials = new APICredentials(username, password);
    }

    public APIActor withUsername(String username) {
        this.credentials.setUsername(username);
        return this;
    }

    public APIActor withPassword(String password) {
        this.credentials.setPassword(password);
        return this;
    }

    public APICredentials credentials() {
        return this.credentials;
    }

}
