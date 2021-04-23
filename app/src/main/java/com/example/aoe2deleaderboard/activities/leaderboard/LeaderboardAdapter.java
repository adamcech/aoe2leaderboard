package com.example.aoe2deleaderboard.activities.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aoe2deleaderboard.R;
import com.example.aoe2deleaderboard.dto.Player;

import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<Player> {

    private final Context context;
    private final int layoutResourceId;
    private final List<Player> data;

    public LeaderboardAdapter(Context context, int layoutResourceId, List<Player> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayerHolder holder;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlayerHolder();
            holder.rankName = (TextView) row.findViewById(R.id.leaderboardEntryRankName);
            holder.rating = (TextView) row.findViewById(R.id.leaderboardEntryRating);
            holder.winPercentage = (TextView) row.findViewById(R.id.leaderboardEntryWinPercentage);

            row.setTag(holder);
        } else {
            holder = (PlayerHolder) row.getTag();
        }


        Player player = data.get(position);
        holder.rankName.setText(player.getRank() + ". " + player.getName());
        holder.rating.setText("Rating: " + player.getRating());
        holder.winPercentage.setText("Win: " + player.getWinPercentage() + "%");

        return row;
    }

    static class PlayerHolder
    {
        TextView rankName;
        TextView rating;
        TextView winPercentage;
    }
}
