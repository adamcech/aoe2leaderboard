package com.example.aoe2deleaderboard.restTasks.lastMatch;

import com.example.aoe2deleaderboard.dto.MatchInfo;
import com.example.aoe2deleaderboard.dto.Player;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;

import java.util.List;

public interface GetLastMatchCallback {
    void onGetLastMatchFinish(RestTaskResult<MatchInfo> taskResult);
    void onGetLastMatchStart(String msg);
}
