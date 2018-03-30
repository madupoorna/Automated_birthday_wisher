package com.ctse.automatedbirthdaywisher;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Madupoorna on 3/23/2018.
 */

public class Process {

    //make phone number a consistent format
    public String normalizeMobileNumber(String number) {
        number = number.replaceAll("[^+0-9]", "");
        if (number.substring(0, 1).compareTo("0") == 0 && number.substring(1, 2).compareTo("0") != 0) {
            number = "0" + number.substring(1);
        }

        return number;
    }

    //change languagee
    public void changeLang(Context context, String lang) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
    }

    //byte to bitmap convert
    public Bitmap byteToBitMap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    //drawable to byte convert
    public byte[] drawableTobyte(Drawable drawable) {

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    //get photo using contact id
    public Bitmap retrieveContactPhoto(Context context, String contactID) {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            } else {
                photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_contacts_launcher);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;
    }

    //make bitmaprounded
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 200;
        int targetHeight = 200;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    //about us popup
    public void showAboutPopUp(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.about_popup);
        dialog.setTitle(context.getString(R.string.about_us));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView text = dialog.findViewById(R.id.textView);
        text.setText(R.string.about_detials);
        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_blue_cake);
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //check is valid mobile number
    public boolean isValidMobile(String phone) {

        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() == 10) {
                check = true;
            } else {
                check = false;
            }
        } else {
            check = false;
        }
        return check;
    }
}

