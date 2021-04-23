package com.example.aoe2deleaderboard.dto;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchInfo implements Serializable  {

    private final String matchName;

    private final long started;
    private final Long finished;

    private final int mapType;

    private final List<MatchPlayer> matchPlayers = new ArrayList<>();

    public MatchInfo(JSONObject jsonObject) throws JSONException {
        JSONObject lastMatch = jsonObject.getJSONObject("last_match");

        this.matchName = lastMatch.getString("name");

        // !!! If new game finished may be NULL !!!
        Long finished = null;
        try {
            finished = lastMatch.getLong("finished");
        } catch (JSONException ignored) { }
        this.finished = finished;
        this.started = lastMatch.getLong("started");

        this.mapType = lastMatch.getInt("map_type");

        JSONArray lastMatchPlayers = lastMatch.getJSONArray("players");
        for (int i = 0; i < lastMatchPlayers.length(); i++) {
            JSONObject lastMatchPlayer = lastMatchPlayers.getJSONObject(i);

            // !!! If player is currently in progress of a game, won is equal to NULL !!!
            Boolean won = null;
            try {
                won = lastMatchPlayer.getBoolean("won");
            } catch (JSONException ignored) { }

            this.matchPlayers.add(new MatchPlayer(
                    lastMatchPlayer.getString("name"),
                    lastMatchPlayer.getInt("civ"),
                    won
            ));
        }
    }

    public String getMatchName() {
        return this.matchName;
    }

    public Boolean isInProgress() {
        return this.getMatchPlayers().get(0).isInProgress();
    }

    public String getLength() {
        long finished = this.isInProgress() ? Instant.now().getEpochSecond() : this.finished;

        long lengthInSec = finished - this.started;
        int lengthInMin = Math.round((float) lengthInSec / 60);

        return String.valueOf(lengthInMin);
    }

    public String getMapName(StringsAOE2 strings) {
        return strings.getMapnames().get(this.mapType);
    }

    public List<MatchPlayer> getMatchPlayers() {
        return matchPlayers;
    }
}
