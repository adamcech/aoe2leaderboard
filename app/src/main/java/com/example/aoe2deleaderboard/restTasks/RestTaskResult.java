package com.example.aoe2deleaderboard.restTasks;

public class RestTaskResult<T> {

    private final boolean isSuccessful;
    private final String msg;
    private final T result;

    public RestTaskResult(boolean isSuccessful, String msg) {
        this(isSuccessful, null, msg);
    }

    public RestTaskResult(boolean isSuccessful, T result) {
        this(isSuccessful, result, "");
    }


    public RestTaskResult(boolean isSuccessful, T result, String msg) {
        this.isSuccessful = isSuccessful;
        this.result = result;
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public T getResult() {
        return result;
    }

    public String getMsg() {
        return this.msg;
    }
}
