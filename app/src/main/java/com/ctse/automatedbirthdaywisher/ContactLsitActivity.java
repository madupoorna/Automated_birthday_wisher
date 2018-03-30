package com.ctse.automatedbirthdaywisher;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactLsitActivity extends AppCompatActivity {

    private static final String TAG = "ContactLsitActivity";

    Process process;
    ListView listView;
    Context context;
    ProgressBar progressBar;
    TextView progressTv;
    ArrayAdapter<Contact> adapter;
    private List<Contact> contactList;
    private List<String> contactNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_lsit);

        this.setTitle(getString(R.string.contact_list));
        context = this;
        process = new Process();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressTv = (TextView) findViewById(R.id.textView23);

        contactList = new ArrayList<>();
        contactNumbers = new ArrayList<>();

        //Async task to reteive contatcs
        new retreiveContacts().execute();
    }

    private class retreiveContacts extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            getContacts();
            return "completed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Contact list loading " + result);
            progressBar.setVisibility(View.GONE);
            progressTv.setVisibility(View.GONE);
            refreshList();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        public void refreshList() {

            adapter = new ContactAdapter(context, 0, contactList);
            listView = (ListView) findViewById(R.id.contact_list);
            listView.setAdapter(adapter);
            listView.setFastScrollEnabled(true);

            //add item click listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ContactLsitActivity.this, AddWishActivity.class);
                    intent.putExtra("name", ((TextView) view.findViewById(R.id.nameTv)).getText().toString());
                    intent.putExtra("phoneNumber", ((TextView) view.findViewById(R.id.numberTv)).getText().toString());
                    intent.putExtra("photo", process.drawableTobyte(((ImageView) view.findViewById(R.id.contact_image_view)).getDrawable()));
                    intent.putExtra("modify", "add");
                    ContactLsitActivity.this.startActivity(intent);
                }
            });
        }

        //get contacts
        public void getContacts() {

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            String id, name, phoneNumber;
            Bitmap bitmap;
            while (phones.moveToNext()) {
                id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNumber = process.normalizeMobileNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                bitmap = process.getRoundedShape(process.retrieveContactPhoto(context, id));

                if (phoneNumber.length() == 10 && !contactNumbers.contains(phoneNumber)) {
                    contactNumbers.add(phoneNumber);
                    contactList.add(new Contact(bitmap, name, phoneNumber));
                }
            }

            phones.close();
        }
    }

}
