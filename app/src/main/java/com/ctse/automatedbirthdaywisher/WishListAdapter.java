package com.ctse.automatedbirthdaywisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Madupoorna on 3/28/2018.
 */

public class WishListAdapter extends ArrayAdapter<DbData> {

    Process process;
    MyDBHelper dbHelper;
    private Context mContext;
    private List<DbData> wishList;

    public WishListAdapter(Context mContext, int resource, List<DbData> contactsList) {
        super(mContext, resource, contactsList);

        this.mContext = mContext;
        this.wishList = contactsList;
        dbHelper = new MyDBHelper(mContext);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        DbData wishProperty = wishList.get(position);
        process = new Process();
        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_item_2, null);

        TextView nameTv = view.findViewById(R.id.nameTv);
        TextView numberTv = view.findViewById(R.id.numberTv);
        TextView timeTv = view.findViewById(R.id.timeTv);
        TextView dateTv = view.findViewById(R.id.dateTv);
        ImageView image = view.findViewById(R.id.contact_image_view);
        ImageView moreDetails = view.findViewById(R.id.moreDetailsIV);

        final int id = wishProperty.getId();
        String name = wishProperty.getName();
        String number = wishProperty.getPhoneNumber();
        String date = wishProperty.getDate();
        String time = wishProperty.getTime();
        byte[] img = wishProperty.getImg();

        nameTv.setText(name);
        numberTv.setText(number);
        timeTv.setText(time);
        dateTv.setText(date);

        image.setImageBitmap(process.getRoundedShape(process.byteToBitMap(img)));

        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddWishActivity.class);
                intent.putExtra("modify", "mod");
                intent.putExtra("id", id);
                getContext().startActivity(intent);
            }
        });

        return view;
    }


}
