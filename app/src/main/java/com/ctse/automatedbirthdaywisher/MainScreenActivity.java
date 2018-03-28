package com.ctse.automatedbirthdaywisher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ListView listView;
    List<DbData> wishList = new ArrayList<>();
    MyDBHelper dbHelper;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        this.setTitle("Added Birthday list");

        if (checkAndRequestPermissions()) {

            listView = (ListView) findViewById(R.id.wishList);
            dbHelper = new MyDBHelper(this);
            wishList = dbHelper.getAllWishes();
            ArrayAdapter<DbData> adapter = new WishListAdapter(this, 0, wishList);
            listView.setAdapter(adapter);
            listView.setFastScrollEnabled(true);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, ContactLsitActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                int idx = wishList.get(pos).getId();
                state = dbHelper.deleteWish(idx);
                if (state) {
                    Toast.makeText(getApplicationContext(), "Data removed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not removed", Toast.LENGTH_LONG).show();
                }
                return state;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.deferNotifyDataSetChanged();
    }

    private boolean checkAndRequestPermissions() {

        int READ_CONTACTS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (READ_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CONTACTS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {

    }
}
