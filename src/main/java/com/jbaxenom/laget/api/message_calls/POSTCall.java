package com.jbaxenom.laget.api.message_calls;

import com.jbaxenom.laget.api.entities.AuthorisedUser;
import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;

/**
 * Created by jbaxenom on 2014-11-21.
 */
public class POSTCall extends ContentBasedRestCall {

    /**
     * Constructor for specific content types
     *
     * @param client
     * @param payload
     * @param contentType
     * @param authorisedUser
     */
    public POSTCall(HttpClientWrapper client, Payload payload, String contentType, AuthorisedUser authorisedUser) {
        super(client, payload, contentType, authorisedUser);
    }

    /**
     * Constructor for default application/json content types
     *
     * @param client
     * @param payload
     * @param authorisedUser
     */
    public POSTCall(HttpClientWrapper client, Payload payload, AuthorisedUser authorisedUser) {
        super(client, payload, authorisedUser);
    }

    @Override
    public HttpCall send(String url) {
        HttpCall call = client().postTo(
                url,
                payload().getStringData(),
                createHeaders(),
                HttpClientWrapper.DEFAULT_CHARSET
        );
        setHttpCall(call);
        return call;
    }

}
