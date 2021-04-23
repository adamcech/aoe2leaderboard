package com.example.aoe2deleaderboard.restTasks.lastMatch;

import android.util.Log;

import com.example.aoe2deleaderboard.dto.MatchInfo;
import com.example.aoe2deleaderboard.rest.config.RestClientRESTApiEndpoints;
import com.example.aoe2deleaderboard.rest.handler.RestJsonExceptionNoResult;
import com.example.aoe2deleaderboard.rest.handler.RestJsonResult;
import com.example.aoe2deleaderboard.restTasks.RestTask;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;

import org.json.JSONException;


public class GetLastMatch extends RestTask<MatchInfo> {

    protected final GetLastMatchCallback activityToCallback;

    public GetLastMatch(GetLastMatchCallback activityToCallback, int profileId) {
        super(RestClientRESTApiEndpoints.LAST_MATCH);
        this.activityToCallback = activityToCallback;

        this.restClient.addParam("profile_id", String.valueOf(profileId));
        this.restClient.addParam("game", "aoe2de");
    }

    @Override
    protected RestTaskResult<MatchInfo> doInBackground(Void... voids) {

        try {
            RestJsonResult response = this.restClient.get();
            return new RestTaskResult<>(true, new MatchInfo(response.jsonObject));
        } catch (JSONException e) {
            Log.i("JSONException", e.getMessage());
            return new RestTaskResult<>(false, "Failed to parse JSON!");
        } catch (RestJsonExceptionNoResult e) {
            return new RestTaskResult<>(false, "Failed to process get leaderboard!");
        }
    }

    @Override
    protected void onPreExecute() {
        activityToCallback.onGetLastMatchStart("Loading Last Match...");
    }

    @Override
    protected void onPostExecute(RestTaskResult<MatchInfo> lastMatch) {
        activityToCallback.onGetLastMatchFinish(lastMatch);
    }

}