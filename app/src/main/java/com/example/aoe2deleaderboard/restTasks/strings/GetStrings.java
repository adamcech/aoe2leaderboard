package com.example.aoe2deleaderboard.restTasks.strings;

import com.example.aoe2deleaderboard.dto.StringsAOE2;
import com.example.aoe2deleaderboard.rest.config.RestClientRESTApiEndpoints;
import com.example.aoe2deleaderboard.rest.handler.RestJsonExceptionNoResult;
import com.example.aoe2deleaderboard.rest.handler.RestJsonResult;
import com.example.aoe2deleaderboard.restTasks.RestTask;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;


public class GetStrings extends RestTask<StringsAOE2> {

    private final GetStringsCallback activityToCallback;

    public GetStrings(GetStringsCallback activityToCallback) {
        super(RestClientRESTApiEndpoints.STRINGS);
        this.activityToCallback = activityToCallback;
    }

    @Override
    protected RestTaskResult<StringsAOE2> doInBackground(Void... voids) {
        try {
            RestJsonResult response = this.restClient.get();

            if (response.jsonObject == null) {
                throw new RestJsonExceptionNoResult("null");
            }

            return new RestTaskResult<>(true, new StringsAOE2(response.jsonObject.toString()));

        } catch (RestJsonExceptionNoResult e) {
            return new RestTaskResult<>(false, "Failed to process get strings!");
        }
    }

    @Override
    protected void onPreExecute() {
        activityToCallback.onGetStringsStart("Loading Leaderboard...");
    }

    @Override
    protected void onPostExecute(RestTaskResult<StringsAOE2> strings) {
        activityToCallback.onGetStringsFinish(strings);
    }

}