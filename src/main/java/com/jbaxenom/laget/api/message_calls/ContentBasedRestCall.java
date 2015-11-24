package com.jbaxenom.laget.api.message_calls;

import com.jbaxenom.laget.api.entities.AuthorisedUser;
import com.jbaxenom.laget.api.payloads.Payload;
import hu.meza.tools.HttpCall;
import hu.meza.tools.HttpClientWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by jbaxenom on 2014-11-21.
 */
public abstract class ContentBasedRestCall extends RestCall {

    private final AuthorisedUser authorisedUser;
    private final String contentType;

    /**
     * Constructor for personalized content types
     *
     * @param client
     * @param payload
     * @param contentType
     * @param authorisedUser
     */
    public ContentBasedRestCall(HttpClientWrapper client, Payload payload, String contentType, AuthorisedUser authorisedUser) {
        super(client, payload);
        this.contentType = contentType;
        this.authorisedUser = authorisedUser;
    }

    /**
     * Constructor for default application/json contents
     *
     * @param client
     * @param payload
     * @param authorisedUser
     */
    public ContentBasedRestCall(HttpClientWrapper client, Payload payload, AuthorisedUser authorisedUser) {
        super(client, payload);
        this.authorisedUser = authorisedUser;
        this.contentType = "application/json";
    }

    @Override
    public abstract HttpCall send(String url);

    protected BasicHeader authorizationHeader() {
        String authStr = String.format("%s:%s", authorisedUser.getUsername(), authorisedUser.getPassword());
        return new BasicHeader("Authorization", "Basic " + Base64.encodeBase64String(authStr.getBytes()));
    }

    protected BasicHeader contentTypeHeader() {
        return new BasicHeader("Content-Type", contentType);
    }

    protected Header[] createHeaders() {
        return new Header[]{authorizationHeader(), contentTypeHeader()};
    }
}
