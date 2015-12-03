package com.jbaxenom.laget.interactions.api.payloads;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author jbaxenom on 6/11/14.
 */
public class Payload {

    private final String data;

    public Payload(String data) {
        this.data = data;
    }

    public Payload(JSONObject data) {
        this.data = data.toString();
    }

    public String getStringData() {
        return data;
    }

    public JSONObject getJSONData() {
        return new JSONObject(data);
    }

    public String getValueFromResponse(String key) {
        try {
            return new JSONObject(data).getString(key);
        } catch (JSONException e) {
            return "";
        }
    }

    public boolean equals(Payload payload) {
        JSONObject jsonData = getJSONData();
        JSONObject payloadData = payload.getJSONData();
        for (Iterator<String> keysIterator = jsonData.keys(); keysIterator.hasNext(); ) {
            String key = keysIterator.next();
            if (!payloadData.get(key).equals(jsonData.get(key))) {
                return false;
            }
        }
        return true;
    }

}
