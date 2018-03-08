package com.ctse.automatedbirthdaywisher;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Madupoorna on 3/6/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<RecyclerViewDataModel> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<RecyclerViewDataModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.title.setText(itemList.get(position).getName());
        holder.image.setImageResource(itemList.get(position).getPhoto());
        holder.relativeLayout.setBackgroundColor(Color.parseColor(itemList.get(position).getColor()));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
