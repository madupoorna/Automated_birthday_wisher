package com.ctse.automatedbirthdaywisher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    public static RecyclerViewAdapter rcAdapter;
    public static RecyclerView rView;
    public static List<RecyclerViewDataModel> rowListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        rowListItem = getAllItemList();
        GridLayoutManager lLayout = new GridLayoutManager(MainScreenActivity.this, 2);

        rcAdapter = new RecyclerViewAdapter(MainScreenActivity.this, rowListItem) ;
        rView.setAdapter(rcAdapter);
    }

    public List<RecyclerViewDataModel> getAllItemList() {

        List<RecyclerViewDataModel> allItems = new ArrayList<>();
        allItems.add(new RecyclerViewDataModel("Add Number", R.drawable.ic_android_black_48dp, "#FF5252"));
        allItems.add(new RecyclerViewDataModel("Record Audio", R.drawable.ic_android_black_48dp, "#1E88E5"));
        allItems.add(new RecyclerViewDataModel("Record SMS", R.drawable.ic_android_black_48dp, "#7E57C2"));
        allItems.add(new RecyclerViewDataModel("Send OBD", R.drawable.ic_android_black_48dp, "#66BB6A"));
        allItems.add(new RecyclerViewDataModel("Send SMS", R.drawable.ic_android_black_48dp, "#FFAB40"));
        allItems.add(new RecyclerViewDataModel("Call Log", R.drawable.ic_android_black_48dp, "#607D8B"));

        return allItems;
    }

}
