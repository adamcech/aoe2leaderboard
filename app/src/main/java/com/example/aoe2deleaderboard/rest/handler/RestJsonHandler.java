package com.example.aoe2deleaderboard.rest.handler;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RestJsonHandler extends JsonHttpResponseHandler {

    private final RestJsonResult response = new RestJsonResult();;

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        this.response.jsonObject = response;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
        this.response.jsonArray = timeline;
    }

    public boolean hasResponse() {
        return this.response.jsonObject != null || this.response.jsonArray != null;
    }

    public RestJsonResult getResponse() {
        return this.response;
    }
}
