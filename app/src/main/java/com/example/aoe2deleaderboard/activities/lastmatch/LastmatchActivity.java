package com.example.aoe2deleaderboard.activities.lastmatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aoe2deleaderboard.R;
import com.example.aoe2deleaderboard.activities.OnlineActivity;
import com.example.aoe2deleaderboard.activities.lastmatch.LastmatchAdapter;
import com.example.aoe2deleaderboard.activities.leaderboard.LeaderboardAdapter;
import com.example.aoe2deleaderboard.dto.MatchInfo;
import com.example.aoe2deleaderboard.dto.MatchPlayer;
import com.example.aoe2deleaderboard.dto.Player;
import com.example.aoe2deleaderboard.dto.StringsAOE2;
import com.example.aoe2deleaderboard.rest.config.RestClientGameMode;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;
import com.example.aoe2deleaderboard.restTasks.lastMatch.GetLastMatch;
import com.example.aoe2deleaderboard.restTasks.lastMatch.GetLastMatchCallback;
import com.example.aoe2deleaderboard.restTasks.players.GetBestPlayers;
import com.example.aoe2deleaderboard.restTasks.strings.GetStrings;

import java.util.HashMap;
import java.util.List;

public class LastmatchActivity extends OnlineActivity implements GetLastMatchCallback {

    private Player player;
    private StringsAOE2 strings;

    private TextView textviewPlayerName;
    private TextView textviewMsg;
    private TextView textviewMatchName;
    private TextView textviewMatchLength;
    private TextView textviewMatchMap;

    private ListView listviewPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.lastmatch);

        // Init fields
        this.player = (Player) getIntent().getSerializableExtra("player");
        this.strings = (StringsAOE2) getIntent().getSerializableExtra("strings");

        this.textviewPlayerName = (TextView) findViewById(R.id.lastmatchPlayerName);
        this.textviewMsg = (TextView) findViewById(R.id.lastmatchMsg);
        this.textviewMatchName = (TextView) findViewById(R.id.lastmatchMatchName);
        this.textviewMatchLength = (TextView) findViewById(R.id.lastmatchMatchLength);
        this.textviewMatchMap = (TextView) findViewById(R.id.lastmatchMatchMap);

        this.listviewPlayers = (ListView) findViewById(R.id.lastmatchListviewPlayers);

        // Prepare UI
        this.textviewPlayerName.setText("Last Match of: " + player.getName());
        this.textviewMsg.setText("");
        this.textviewMatchName.setText("");
        this.textviewMatchLength.setText("");
        this.textviewMatchMap.setText("");

        if (this.isOnline()) {
            new GetLastMatch(this, player.getProfileId()).execute();
        } else {
            finish();
        }
    }

    private void refreshPlayersAdapter(List<MatchPlayer> matchPlayers) {
        this.listviewPlayers.setAdapter(
                new LastmatchAdapter(getBaseContext(), R.layout.lastmatch_entry, matchPlayers, this.strings));
    }

    private void refreshMsg(String msg) {
        this.textviewMsg.setText(msg);
    }

    @Override
    public void onGetLastMatchStart(String msg) {
        this.refreshMsg(msg);
    }

    @Override
    public void onGetLastMatchFinish(RestTaskResult<MatchInfo> taskResult) {
        if (taskResult.isSuccessful()) {
            MatchInfo matchInfo = taskResult.getResult();

            this.textviewMatchName.setText("Name: " + matchInfo.getMatchName());

            if (matchInfo.isInProgress()) {
                this.textviewMatchLength.setText("Length: In progress (" + matchInfo.getLength() + "+ mins)");
            } else {
                this.textviewMatchLength.setText("Length: " + matchInfo.getLength() + " mins");
            }

            this.textviewMatchMap.setText("Map: " + matchInfo.getMapName(this.strings));
            this.refreshPlayersAdapter(matchInfo.getMatchPlayers());

            this.refreshMsg("");
        } else {
            this.refreshMsg(taskResult.getMsg());
        }
    }
}
