package com.jbaxenom.laget.interactions.api.message_calls;

import com.jbaxenom.laget.interactions.api.entities.APICredentials;
import com.jbaxenom.laget.interactions.api.payloads.Payload;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author jbaxenom on 11/21/14.
 */
public class RestCall {

    private final HttpClientWrapper client;
    private final String callType;
    private String endpoint;
    private Payload payload;
    private APICredentials credentials;
    private String contentType;
    private Header[] headers;

    private HttpCall httpCall;

    public RestCall(HttpClientWrapper client, String endpoint, String callType) {
        this.client = client;
        this.endpoint = endpoint;
        this.callType = callType;
    }

    public RestCall withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public RestCall withPayload(String jsonString) {
        this.payload = new Payload(jsonString);
        return this;
    }

    public RestCall withCredentials(APICredentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public RestCall withCredentials(String username, String password) {
        this.credentials = new APICredentials(username, password);
        return this;
    }

    public RestCall withContentType(String contentType) {
        this.contentType = contentType;
        addContentTypeHeader();
        return this;
    }

    public RestCall withHeader(Header header) {
        addHeader(header);
        return this;
    }

    public RestCall withHeaders(Header[] headers) {
        addHeaders(headers);
        return this;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public RestCall addAuthorizationHeader() {
        if (credentials != null) {
            String authStr = String.format("%s:%s", credentials.getUsername(), credentials.getPassword());
            addHeader(new BasicHeader("Authorization", "Basic " + Base64.encodeBase64String(authStr.getBytes())));
        } else {
            throw new RuntimeException("Error when creating authorization header: credentials has not been set");
        }
        return this;
    }

    private RestCall addContentTypeHeader() {
        if (contentType != null) {
            addHeader(new BasicHeader("Content-Type", contentType));
        } else {
            throw new RuntimeException("Error when creating content-type header: contentType has not been set");
        }
        return this;
    }

    public HttpCall send() {

        switch (callType) {
            case "get":
                if (headers == null && credentials == null) {
                    this.httpCall = client.getFrom(endpoint);
                } else if (headers == null) {
                    this.httpCall = client.getFrom(endpoint, credentials.getUsername(), credentials.getPassword());
                } else if (credentials == null) {
                    this.httpCall = client.getFrom(endpoint, headers);
                } else {
                    addAuthorizationHeader();
                    this.httpCall = client.getFrom(endpoint, headers);
                }
                break;
            case "post":
                if (payload == null) {
                    throw new RuntimeException("Payload not defined. Cannot do a POST call without payload!");
                } else if (headers == null && credentials == null) {
                    this.httpCall = client.postTo(endpoint, payload.getStringData());
                } else if (credentials == null) {
                    this.httpCall = client.postTo(endpoint, payload.getStringData(), headers, "UTF-8");
                } else {
                    addAuthorizationHeader();
                    this.httpCall = client.postTo(endpoint, payload.getStringData(), headers, "UTF-8");
                }
                break;
            case "patch":
                if (payload == null) {
                    throw new RuntimeException("Payload not defined. Cannot do a PATCH call without payload!");
                } else if (headers == null && credentials == null) {
                    throw new RuntimeException("No headers defined. Cannot do a PATCH call without at least content-type!");
                } else if (credentials == null) {
                    this.httpCall = client.patch(endpoint, payload.getStringData(), headers);
                } else {
                    addAuthorizationHeader();
                    this.httpCall = client.patch(endpoint, payload.getStringData(), headers);
                }
                break;
            case "delete":
                if (payload == null) {
                    throw new RuntimeException("Payload not defined. Cannot do a PATCH call without payload!");
                } else if (headers == null && credentials == null) {
                    throw new RuntimeException("No headers defined. Cannot do a DELETE call without at least content-type!");
                } else if (credentials == null) {
                    this.httpCall = client.delete(endpoint, payload.getStringData(), headers);
                } else {
                    addAuthorizationHeader();
                    this.httpCall = client.delete(endpoint, payload.getStringData(), headers);
                }
                break;
            default:
                throw new RuntimeException("REST call '" + callType + "' not supported");
        }

        return httpCall;
    }

    public boolean wasSuccessful() {
        switch (Integer.parseInt(getResponseStatusCode())) {
            case HttpStatus.SC_CREATED:
            case HttpStatus.SC_OK:
            case HttpStatus.SC_NO_CONTENT:
                return true;
            default:
                return false;
        }
    }

    public boolean wasError() {
        switch (Integer.parseInt(getResponseStatusCode())) {
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

    public String getResponseStatusCode() {
        return (httpCall != null) ? String.valueOf(httpCall.response().getStatusLine().getStatusCode()) : "";
    }

    public Header[] getResponseHeader(String header) {
        return httpCall.response().getHeaders(header);
    }

    public String getResponseBody() {
        return httpCall.body();
    }

    public JSONObject getJSONResponse() {
        return new JSONObject(getResponseBody());
    }


    private RestCall addHeader(Header header) {
        if (headers != null && headers.length > 0) {

            ArrayList newHeaders = new ArrayList<>();
            for (Header temp : headers) {
                newHeaders.add(temp);
            }
            newHeaders.add(header);

            this.headers = (Header[]) newHeaders.toArray(new Header[newHeaders.size()]);
        } else {
            this.headers = new Header[]{header};
        }

        return this;
    }

    private RestCall addHeaders(Header[] headers) {
        if (headers != null && headers.length > 0) {

            ArrayList newHeaders = new ArrayList<>();
            for (Header temp : this.headers) {
                newHeaders.add(temp);
            }

            for (Header temp : headers) {
                newHeaders.add(temp);
            }

            this.headers = (Header[]) newHeaders.toArray();
        } else {
            this.headers = headers;
        }

        return this;
    }

}
