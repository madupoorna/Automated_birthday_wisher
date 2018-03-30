package com.ctse.automatedbirthdaywisher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Madupoorna on 3/20/2018.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    Process process;
    private Context mContext;
    private List<Contact> contactsList;

    public ContactAdapter(Context mContext, int resource, List<Contact> contactsList) {
        super(mContext, resource, contactsList);

        this.mContext = mContext;
        this.contactsList = contactsList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        Contact contactProperty = contactsList.get(position);
        process = new Process();

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_item, null);

        TextView nameTv = view.findViewById(R.id.nameTv);
        TextView numberTv = view.findViewById(R.id.numberTv);
        ImageView image = view.findViewById(R.id.contact_image_view);

        //set address and description
        String name = contactProperty.getName();
        String number = contactProperty.getNumber();
        Bitmap img = contactProperty.getImage();
        nameTv.setText(name);
        numberTv.setText(number);
        image.setImageBitmap(img);

        return view;
    }
}
