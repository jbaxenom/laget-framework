package com.jbaxenom.laget.api.message_calls;

import com.jbaxenom.laget.api.entities.AuthorisedUser;
import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;

/**
 * Created by jbaxenom on 2014-11-21.
 */
public class PATCHCall extends ContentBasedRestCall {

    public PATCHCall(HttpClientWrapper client, Payload payload, String contentType, AuthorisedUser authorisedUser) {
        super(client, payload, contentType, authorisedUser);
    }

    @Override
    public HttpCall send(String url) {
        HttpCall call = client().patch(
                url,
                payload().getStringData(),
                createHeaders()
        );
        setHttpCall(call);
        return call;
    }

}
