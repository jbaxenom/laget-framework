package com.jbaxenom.laget.api.message_calls;

import com.jbaxenom.laget.api.entities.AuthorisedUser;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;

/**
 * Created by jbaxenom on 2014-11-21.
 */
public class GETCall extends RestCall {

    private final AuthorisedUser authorisedUser;

    public GETCall(HttpClientWrapper client, AuthorisedUser authorisedUser) {
        super(client);
        this.authorisedUser = authorisedUser;
    }

    @Override
    public HttpCall send(String url) {
        HttpCall call = client().getFrom(
                url,
                authorisedUser.getUsername(),
                authorisedUser.getPassword()
        );
        setHttpCall(call);
        return call;
    }

}
