package com.example.aoe2deleaderboard.restTasks;

import android.os.AsyncTask;

import com.example.aoe2deleaderboard.rest.RestClient;

abstract public class RestTask<T> extends AsyncTask<Void, Void, RestTaskResult<T>> {

    protected final RestClient restClient;

    public RestTask(String url) {
        this.restClient = new RestClient(url);
    }

    public boolean isFinished() {
        return this.getStatus() == Status.FINISHED;
    }
}
