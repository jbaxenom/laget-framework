package com.jbaxenom.laget.api.message_calls;

import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/**
 * Created by jbaxenom on 2014-11-21.
 */
public abstract class RestCall {

    private HttpClientWrapper client;
    private HttpCall httpCall;
    private Payload payload;

    public RestCall(HttpClientWrapper client) {
        setClient(client);
    }

    public RestCall(Payload payload) {
        setPayload(payload);
    }

    public RestCall(HttpClientWrapper client, Payload payload) {
        setPayload(payload);
        setClient(client);
    }

    public void setClient(HttpClientWrapper client) {
        this.client = client;
    }

    public HttpClientWrapper client() {
        return this.client;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload payload() {
        return this.payload;
    }

    public void setHttpCall(HttpCall httpCall) {
        this.httpCall = httpCall;
    }

    public boolean isSuccessful() {
        switch (Integer.parseInt(getStatusCode())) {
            case HttpStatus.SC_CREATED:
            case HttpStatus.SC_OK:
            case HttpStatus.SC_NO_CONTENT:
                return true;
            default:
                return false;
        }
    }

    public boolean isError() {
        switch (Integer.parseInt(getStatusCode())) {
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_BAD_REQUEST:
            case HttpStatus.SC_METHOD_NOT_ALLOWED:
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
            case HttpStatus.SC_BAD_GATEWAY:
            case HttpStatus.SC_CONFLICT:
            case HttpStatus.SC_UNPROCESSABLE_ENTITY:
            case HttpStatus.SC_FORBIDDEN:
                return true;
            default:
                return false;
        }

    }

    public abstract HttpCall send(String url);

    public String getStatusCode() {
        return (httpCall != null) ? String.valueOf(httpCall.response().getStatusLine().getStatusCode()) : "";
    }

    public JSONObject getErrorFromResponse() {
        if (isError()) {
            if (!getResponseBody().equals("")) {
                return new JSONObject(getResponseBody());
            }
        }
        return new JSONObject();
    }

    public String getResponseBody() {
        return httpCall.body();
    }

    public Header[] getResponseHeader(String header) {
        return httpCall.response().getHeaders(header);
    }

}
