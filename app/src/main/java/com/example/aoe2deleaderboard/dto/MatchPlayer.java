package com.example.aoe2deleaderboard.dto;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class MatchPlayer implements Serializable {

    private final String name;
    private final int civ;
    private final Boolean isWinner;

    public MatchPlayer(String name, int civ, Boolean isWinner) {
        this.name = name;
        this.civ = civ;
        this.isWinner = isWinner;
    }

    public String getName() {
        return name;
    }

    public String getCivName(HashMap<Integer, String> civNames) {
        return civNames.get(this.civ);
    }

    public Boolean isInProgress() {
        return isWinner == null;
    }

    public Boolean isWinner() {
        return isWinner;
    }
}
