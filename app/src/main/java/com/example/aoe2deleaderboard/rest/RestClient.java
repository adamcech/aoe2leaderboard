package com.example.aoe2deleaderboard.rest;

import com.example.aoe2deleaderboard.rest.handler.RestJsonExceptionNoResult;
import com.example.aoe2deleaderboard.rest.handler.RestJsonHandler;
import com.example.aoe2deleaderboard.rest.handler.RestJsonResult;
import com.loopj.android.http.*;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RestClient {

    private final String url;
    private final List<RestClientParams> params = new ArrayList<>();

    private static final SyncHttpClient client = new SyncHttpClient();

    public RestClient(String url) {
        this.url = url;
    }

    public void addParam(String key, String value) {
        this.params.add(new RestClientParams(key, value));
    }

    private String generateUrl() {
        StringBuilder str = new StringBuilder(this.url);

        str.append("?");

        for (RestClientParams param : this.params) {
            str.append(param.key);
            str.append("=");
            str.append(param.value);
            str.append("&");
        }

        return str.toString();
    }

    private static JSONArray ret;

    public RestJsonResult get() throws RestJsonExceptionNoResult {
        RestJsonHandler jsonHandler = new RestJsonHandler();

        client.get(this.generateUrl(), jsonHandler);

        if (!jsonHandler.hasResponse()) {
            throw new RestJsonExceptionNoResult("No result to parse");
        }

        return jsonHandler.getResponse();
    }
}