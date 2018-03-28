package com.ctse.automatedbirthdaywisher;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactLsitActivity extends AppCompatActivity {

    Process process;
    ListView listView;
    private List<Contact> contactList = new ArrayList<>();
    private String TAG = "ContactLsitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_lsit);

        this.setTitle("Contact list");

        process = new Process();

        getContacts();
        ArrayAdapter<Contact> adapter = new ContactAdapter(this, 0, contactList);
        listView = (ListView) findViewById(R.id.contact_list);
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);

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

    public List<Contact> getContacts() {

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        String id, name, phoneNumber;
        Bitmap bitmap;
        while (phones.moveToNext()) {
            id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = process.normalizeMobileNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            bitmap = process.retrieveContactPhoto(this, id);

            if (phoneNumber.length() == 10) {
                contactList.add(new Contact(bitmap, name, phoneNumber));
            }
        }

        phones.close();
        return contactList;
    }


}
