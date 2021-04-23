package com.example.aoe2deleaderboard.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Player implements Serializable {

    private int profileId;
    private int steamId;

    private String rank;
    private String name;
    private String rating;

    private int wins;
    private int games;

    public Player(JSONObject jsonObject) throws JSONException {
        this.profileId = jsonObject.getInt("profile_id");
        this.steamId = jsonObject.getInt("steam_id");

        this.rank = jsonObject.getString("rank");
        this.name = jsonObject.getString("name");
        this.rating = jsonObject.getString("rating");

        this.wins = jsonObject.getInt("wins");
        this.games = jsonObject.getInt("games");
    }

    public int getProfileId() {
        return profileId;
    }

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getWinPercentage() {
        return String.valueOf(Math.round( (((float) wins / games) * 100) ));
    }

    @Override
    public String toString() {
        return this.getRank() + ". " + this.getName() + " " + this.getRating() + " " + this.getWinPercentage();
    }

}
