package com.example.aoe2deleaderboard.activities.lastmatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aoe2deleaderboard.R;
import com.example.aoe2deleaderboard.dto.MatchPlayer;
import com.example.aoe2deleaderboard.dto.StringsAOE2;

import java.util.HashMap;
import java.util.List;

public class LastmatchAdapter extends ArrayAdapter<MatchPlayer> {

    private final Context context;
    private final int layoutResourceId;
    private final List<MatchPlayer> data;
    private final StringsAOE2 strings;

    public LastmatchAdapter(Context context, int layoutResourceId, List<MatchPlayer> data, StringsAOE2 strings) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.strings = strings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MatchPlayerHolder holder;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MatchPlayerHolder();

            holder.winIcon = (ImageView) row.findViewById(R.id.lastmatchImageViewWinIcon);
            holder.playerName = (TextView) row.findViewById(R.id.lastmatchTextViewPlayerName);
            holder.playerCivilization = (TextView) row.findViewById(R.id.lastmatchTextViewPLayerCivilization);

            row.setTag(holder);
        } else {
            holder = (MatchPlayerHolder) row.getTag();
        }


        MatchPlayer p = data.get(position);

        holder.playerName.setText(p.getName());
        holder.playerCivilization.setText(p.getCivName(this.strings.getCivnames()));

        if (p.isInProgress()) {
            holder.winIcon.setImageResource(R.drawable.loading);
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(3000);
            holder.winIcon.startAnimation(anim);
        } else {
            holder.winIcon.setImageResource(p.isWinner() ? R.drawable.win : R.drawable.lose);
        }

        return row;
    }

    static class MatchPlayerHolder
    {
        ImageView winIcon;
        TextView playerName;
        TextView playerCivilization;
    }
}
