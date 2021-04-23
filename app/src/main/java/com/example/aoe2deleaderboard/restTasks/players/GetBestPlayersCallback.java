package com.example.aoe2deleaderboard.restTasks.players;

import com.example.aoe2deleaderboard.dto.Player;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;

import java.util.List;

public interface GetBestPlayersCallback {
    void onGetBestPlayersFinish(RestTaskResult<List<Player>> taskResult);
    void onGetBestPlayersStart(String msg);
}
