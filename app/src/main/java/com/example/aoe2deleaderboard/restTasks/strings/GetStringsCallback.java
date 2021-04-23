package com.example.aoe2deleaderboard.restTasks.strings;

import com.example.aoe2deleaderboard.dto.StringsAOE2;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;

public interface GetStringsCallback {
    void onGetStringsFinish(RestTaskResult<StringsAOE2> taskResult);
    void onGetStringsStart(String msg);
}
