package com.example.aoe2deleaderboard.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class StringsAOE2 implements Serializable {

    private String strings;

    public StringsAOE2(String strings) {
        this.strings = strings;
    }

    public HashMap<Integer, String> getMapnames() {
        HashMap<Integer, String> map = new HashMap<>();

        JSONArray mapNames = null;
        try {
            mapNames = this.getStrings().getJSONArray("map_type");

            for (int i = 0; i < mapNames.length(); i++) {
                JSONObject mapDetail = mapNames.getJSONObject(i);
                map.put(mapDetail.getInt("id"), mapDetail.getString("string"));
            }
        } catch (JSONException ignored) { }

        return map;
    }

    public HashMap<Integer, String> getCivnames() {
        HashMap<Integer, String> map = new HashMap<>();

        try {
            JSONArray mapNames = this.getStrings().getJSONArray("civ");

            for (int i = 0; i < mapNames.length(); i++) {
                JSONObject mapDetail = mapNames.getJSONObject(i);
                map.put(mapDetail.getInt("id"), mapDetail.getString("string"));
            }
        } catch (JSONException ignored) { }

        return map;
    }

    public JSONObject getStrings() throws JSONException {
        return new JSONObject(this.strings);
    }
}
