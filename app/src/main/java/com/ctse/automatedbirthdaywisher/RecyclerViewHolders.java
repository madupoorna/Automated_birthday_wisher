package com.ctse.automatedbirthdaywisher;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Madupoorna on 3/6/2018.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public ImageView image;
    public RelativeLayout relativeLayout;
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        title = itemView.findViewById(R.id.title);
        image = itemView.findViewById(R.id.image);
        relativeLayout = itemView.findViewById(R.id.relativeLayout);
    }

    @Override
    public void onClick(View view) {
        //button click animation
        view.startAnimation(buttonClick);

        if (getLayoutPosition() == 0) {//open Add Number

        }

    }
}
