package com.jbaxenom.laget.aao.core.actions;

import com.jbaxenom.laget.api.entities.AuthorisedUser;
import com.jbaxenom.laget.api.message_calls.RestCall;
import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.aao.Action;
import hu.meza.tools.HttpClientWrapper;
import org.json.JSONObject;

/**
 * Created by chema.delbarco on 11/11/14.
 */
public abstract class APIAction implements Action {

    protected HttpClientWrapper client;
    protected String url;
    protected Payload payload;
    protected AuthorisedUser authorisedUser;
    protected RestCall call;

    /**
     * Constructor for POST and PATCH calls
     *
     * @param client
     * @param url
     * @param payload
     * @param authorisedUser
     */
    public APIAction(HttpClientWrapper client, String url, Payload payload, AuthorisedUser authorisedUser) {
        this.client = client;
        this.url = url;
        this.payload = payload;
        this.authorisedUser = authorisedUser;
    }

    /**
     * Constructor for GET calls
     *
     * @param client
     * @param url
     * @param authorisedUser
     */
    public APIAction(HttpClientWrapper client, String url, AuthorisedUser authorisedUser) {
        this.client = client;
        this.url = url;
        this.authorisedUser = authorisedUser;
    }

    /**
     * Specifies the HttpClient to use
     *
     * @param client
     */
    public void useClient(HttpClientWrapper client) {
        this.client = client;
    }

    /**
     * Executes the action, which implies performing the API call set up in the
     * constructor. The call data will be saved in the {@code call} attribute.
     */
    @Override
    public abstract void execute();

    public boolean isSuccessful() {
        return call.isSuccessful();
    }

    public boolean isError() {
        return call.isError();
    }

    public String getResponseStatusCode() {
        return call.getStatusCode();
    }

    /**
     * Returns a JSONObject with the error string (empty if no error)
     *
     * @return
     */
    public JSONObject getErrorFromResponse() {
        if (isError()) {
            if (!getResponseBody().equals("")) {
                return new JSONObject(getResponseBody());
            }
        }
        return new JSONObject();
    }

    public Payload getPayload() {
        return this.payload;
    }

    public String getResponseBody() {
        return call.getResponseBody();
    }

    public JSONObject getJSONResponseBody() {
        return new JSONObject(getResponseBody());
    }

    @Override
    public APIAction copyOf() {
        return this;
    }

}
