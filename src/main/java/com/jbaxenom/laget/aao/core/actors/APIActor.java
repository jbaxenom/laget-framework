package com.jbaxenom.laget.aao.core.actors;

import com.jbaxenom.laget.api.entities.APICredentials;
import com.jbaxenom.laget.api.message_calls.RestCall;
import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.aao.Actor;
import hu.meza.tools.HttpClientWrapper;
import org.json.JSONObject;

/**
 * Created by jbaxenom on 4/30/14.
 */
public abstract class APIActor extends Actor {

    protected String endpointUrl;
    protected Payload payload;
    protected boolean wasLastCallSuccessful;
    private final HttpClientWrapper client;
    private APICredentials credentials;
    private RestCall lastCall;

    public APIActor() {
        this.client = new HttpClientWrapper();
    }

    public APIActor(String endpointUrl) {
        this.client = new HttpClientWrapper();
        this.endpointUrl = endpointUrl;
    }

    public APIActor(String username, String password, String endpointUrl) {
        this.client = new HttpClientWrapper();
        this.credentials = new APICredentials(username, password);
        this.endpointUrl = endpointUrl;
    }

    public APIActor withUsername(String username) {
        credentials.setUsername(username);
        return this;
    }

    public APIActor withPassword(String password) {
        credentials.setPassword(password);
        return this;
    }

    public APIActor withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public APIActor withPayload(String payload) {
        this.payload = new Payload(payload);
        return this;
    }

    public APIActor withPayload(JSONObject payload) {
        this.payload = new Payload(payload);
        return this;
    }

    public APIActor withEndpoint(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public APICredentials credentials() {
        return this.credentials;
    }

    public void setLastCall(RestCall call) {
        this.lastCall = call;
    }

    public RestCall getLastCall() {
        return lastCall;
    }

    public boolean wasLastCallSuccessful() {
        return wasLastCallSuccessful;
    }

    public HttpClientWrapper client() {
        return client;
    }

}
