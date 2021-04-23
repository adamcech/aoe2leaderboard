package com.example.aoe2deleaderboard.restTasks.players;

import com.example.aoe2deleaderboard.dto.Player;
import com.example.aoe2deleaderboard.rest.config.RestClientGameMode;
import com.example.aoe2deleaderboard.rest.config.RestClientRESTApiEndpoints;
import com.example.aoe2deleaderboard.rest.handler.RestJsonExceptionNoResult;
import com.example.aoe2deleaderboard.rest.handler.RestJsonResult;
import com.example.aoe2deleaderboard.restTasks.RestTask;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;
import com.example.aoe2deleaderboard.restTasks.lastMatch.GetLastMatchCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GetBestPlayers extends RestTask<List<Player>> {

    protected final GetBestPlayersCallback activityToCallback;

    public GetBestPlayers(GetBestPlayersCallback activityToCallback) {
        this(activityToCallback, RestClientGameMode.ONE_VS_ONE_RANDOM_MAP, 20);
    }

    public GetBestPlayers(GetBestPlayersCallback activityToCallback, String gameMode, int count) {
        super(RestClientRESTApiEndpoints.LEADERBOARD);
        this.activityToCallback = activityToCallback;

        this.restClient.addParam("leaderboard_id", gameMode);
        this.restClient.addParam("count", String.valueOf(count));
    }

    @Override
    protected RestTaskResult<List<Player>> doInBackground(Void... voids) {
        List<Player> players = new ArrayList<>();

        try {
            RestJsonResult response = this.restClient.get();
            JSONArray leaderboard = response.jsonObject.getJSONArray("leaderboard");

            for (int i = 0; i < leaderboard.length(); i++) {
                players.add(new Player(leaderboard.getJSONObject(i)));
            }

            return new RestTaskResult<>(true, players);
        } catch (JSONException e) {
            return new RestTaskResult<>(false, "Failed to parse JSON!");
        } catch (RestJsonExceptionNoResult e) {
            return new RestTaskResult<>(false, "Failed to process get leaderboard!");
        }
    }

    @Override
    protected void onPreExecute() {
        activityToCallback.onGetBestPlayersStart("Loading data...");
    }

    @Override
    protected void onPostExecute(RestTaskResult<List<Player>> players) {
        activityToCallback.onGetBestPlayersFinish(players);
    }

}