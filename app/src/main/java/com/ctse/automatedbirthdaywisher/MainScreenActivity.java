package com.ctse.automatedbirthdaywisher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ListView listView;
    List<DbData> wishList;
    MyDBHelper dbHelper;
    boolean state;
    ArrayAdapter<DbData> adapter;
    SharedPreferenceController preference;
    boolean doubleBackToExitPressedOnce = false;
    Process process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        this.setTitle(getString(R.string.wishes_list));
        listView = (ListView) findViewById(R.id.wishList);
        preference = new SharedPreferenceController(this);

        if (checkAndRequestPermissions()) {

            wishList = new ArrayList<>();
            dbHelper = new MyDBHelper(this);
            wishList = dbHelper.getAllWishes();
            adapter = new WishListAdapter(this, 0, wishList);
            listView.setAdapter(adapter);
            listView.setFastScrollEnabled(true);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence lang[] = new CharSequence[]{getString(R.string.add_from_contacts), getString(R.string.add_manually)};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.select_method);
                builder.setItems(lang, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent;
                        switch (id) {
                            case 0:
                                intent = new Intent(MainScreenActivity.this, ContactLsitActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(MainScreenActivity.this, AddWishManuallyActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                final int idx = wishList.get(pos).getId();

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setMessage(R.string.do_you_wnat_to_delete);
                alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        state = dbHelper.deleteWish(idx);
                        if (state) {
                            Toast.makeText(getApplicationContext(), R.string.data_removed, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.data_not_removed, Toast.LENGTH_LONG).show();
                        }
                        refreshList();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return state;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        Switch toggle = menu.findItem(R.id.langSwitch).getActionView().findViewById(R.id.switchLang);

        if (preference.getPreference("service").equals("off")) {
            toggle.setChecked(false);
        } else {
            preference.setPreference("service", "on");
            toggle.setChecked(true);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    preference.setPreference("service", "off");
                    Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                } else {
                    preference.setPreference("service", "on");
                    Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_language) {
            CharSequence lang[] = new CharSequence[]{getString(R.string.english_def), getString(R.string.sinhala)};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.choose_lang);
            builder.setItems(lang, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int langId) {
                    switch (langId) {
                        case 0:
                            alertDialog("en");
                            break;
                        case 1:
                            alertDialog("si");
                            break;
                        default:
                            alertDialog("en");
                    }
                }
            });
            builder.show();
        } else if (id == R.id.action_about) {
            process = new Process();
            process.showAboutPopUp(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void alertDialog(final String lang) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.restart);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                preference.setPreference("language", lang);
                restartApp();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void restartApp() {
        System.exit(0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshList();
    }

    public void refreshList() {
        adapter.clear();
        wishList = new ArrayList<>();
        wishList = dbHelper.getAllWishes();
        adapter.addAll(wishList);
        adapter.notifyDataSetChanged();
    }

    private boolean checkAndRequestPermissions() {

        int READ_CONTACTS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (READ_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CONTACTS);
        }
        if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
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
        if (doubleBackToExitPressedOnce) {
            closeApplication();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.click_back_twice, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void closeApplication() {
        finish();
        moveTaskToBack(true);
    }
}
