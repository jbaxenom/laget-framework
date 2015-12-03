package com.jbaxenom.laget.domain.core.actions;

import com.jbaxenom.laget.interactions.api.entities.APICredentials;
import com.jbaxenom.laget.interactions.api.message_calls.RestCall;
import com.jbaxenom.laget.interactions.api.payloads.Payload;
import hu.meza.aao.Action;
import hu.meza.tools.HttpClientWrapper;
import org.json.JSONObject;

/**
 * Abstract Action to create API calls. It is essentially a wrapper around RestCall to give it an Action context
 *
 * @author jbaxenom on 11/11/14.
 */
public abstract class APIAction implements Action {

    protected HttpClientWrapper client;
    protected String endpointUrl;
    protected APICredentials credentials;
    protected RestCall call;
    private Payload payload;

    public APIAction(HttpClientWrapper client, String endpointUrl) {
        this.client = client;
        this.endpointUrl = endpointUrl;
    }

    public APIAction withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public APIAction withPayload(String payload) {
        this.payload = new Payload(payload);
        return this;
    }

    public APIAction withPayload(JSONObject payload) {
        this.payload = new Payload(payload);
        return this;
    }

    public APIAction withCredentials(String username, String password) {
        this.credentials = new APICredentials(username, password);
        return this;
    }

    public APIAction withCredentials(APICredentials credentials) {
        this.credentials = credentials;
        return this;
    }


    /**
     * Executes the action, which basically implies calling the right RestCall and save it in the {@code call} attribute.
     */
    @Override
    public abstract void execute();

    public boolean wasSuccessful() {
        return call.wasSuccessful();
    }

    public boolean wasError() {
        return call.wasError();
    }

    public String getResponseStatusCode() {
        return call.getResponseStatusCode();
    }

    public Payload getPayload() {
        return this.payload;
    }

    public String getResponseBody() {
        return call.getResponseBody();
    }

    public JSONObject getJSONResponse() {
        return call.getJSONResponse();
    }

    public RestCall getLastCall() {
        return this.call;
    }

    @Override
    public APIAction copyOf() {
        return this;
    }

}
