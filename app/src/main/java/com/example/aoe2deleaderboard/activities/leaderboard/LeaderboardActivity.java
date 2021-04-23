package com.example.aoe2deleaderboard.activities.leaderboard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aoe2deleaderboard.R;
import com.example.aoe2deleaderboard.activities.OnlineActivity;
import com.example.aoe2deleaderboard.dto.StringsAOE2;
import com.example.aoe2deleaderboard.activities.lastmatch.LastmatchActivity;
import com.example.aoe2deleaderboard.dto.Player;
import com.example.aoe2deleaderboard.rest.config.RestClientGameMode;
import com.example.aoe2deleaderboard.restTasks.players.GetBestPlayers;
import com.example.aoe2deleaderboard.restTasks.players.GetBestPlayersCallback;
import com.example.aoe2deleaderboard.restTasks.strings.GetStrings;
import com.example.aoe2deleaderboard.restTasks.strings.GetStringsCallback;
import com.example.aoe2deleaderboard.restTasks.RestTaskResult;


import java.util.List;

public class LeaderboardActivity extends OnlineActivity implements GetBestPlayersCallback, GetStringsCallback {

    private TextView textviewLeaderboardMsg;
    private ListView listviewLeaderboard;

    private StringsAOE2 strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.leaderboard);

        this.listviewLeaderboard = (ListView) findViewById(R.id.leaderboardListviewPlayers);
        this.textviewLeaderboardMsg = (TextView) findViewById(R.id.leaderboardMsg);

        this.getData();
    }

    public void getData() {
        if (this.isOnline()) {
            new GetBestPlayers(this).execute();
            new GetStrings(this).execute();
        } else {
            new PostponeGetDataTask().execute();
        }
    }

    private void refreshMsg(String msg) {
        this.textviewLeaderboardMsg.setText(msg);
    }

    private void refreshLeaderboardAdapter(List<Player> playersList) {
        if (playersList.size() >= 1) {
            this.refreshMsg("");
        }

        listviewLeaderboard.setAdapter(new LeaderboardAdapter(getBaseContext(), R.layout.leaderboard_entry, playersList));
        listviewLeaderboard.setOnItemClickListener((parent, view, position, id) -> {
            Player player = (Player) parent.getItemAtPosition(position);

            if (this.strings != null) {
                Intent intent = new Intent(this, LastmatchActivity.class);
                intent.putExtra("player", player);
                intent.putExtra("strings", this.strings);
                startActivity(intent);
            } else {
                refreshMsg("Error show last match");
            }
        });
    }

    @Override
    public void onGetStringsStart(String msg) { }

    @Override
    public void onGetStringsFinish(RestTaskResult<StringsAOE2> taskResult) {
        this.strings = taskResult.getResult();
    }

    @Override
    public void onGetBestPlayersStart(String msg) {
        this.refreshMsg(msg);
    }

    @Override
    public void onGetBestPlayersFinish(RestTaskResult<List<Player>> taskResult) {
        if (taskResult.isSuccessful()) {
            this.refreshLeaderboardAdapter(taskResult.getResult());
        } else {
            this.refreshMsg(taskResult.getMsg());
        }
    }

    private class PostponeGetDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            refreshMsg("Waiting for internet connection to be available!");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getData();
        }
    }
}