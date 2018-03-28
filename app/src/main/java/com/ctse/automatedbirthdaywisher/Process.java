package com.ctse.automatedbirthdaywisher;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Madupoorna on 3/23/2018.
 */

public class Process {

    Bitmap bitmap;
    String uri = null;

    //make phone number a consistent format
    public String normalizeMobileNumber(String number) {
        number = number.replaceAll("[^+0-9]", "");
        if (number.substring(0, 1).compareTo("0") == 0 && number.substring(1, 2).compareTo("0") != 0) {
            number = "0" + number.substring(1);
        }

        return number;
    }

    public Uri getPhotoById(Context ctx, String contactId) {
        Uri uri = null;
        long id = Long.valueOf(contactId);
        if (Long.valueOf(contactId) == null) {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
            uri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        }

        if (uri.EMPTY.equals(null)) {
            bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_account_circle_black_48dp);
        }
        return uri;
    }

    public byte[] getBytes(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteBuffer.toByteArray();
    }


    public Bitmap byteToBitMap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    public byte[] drawableTobyte(Drawable drawable) {

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    public Bitmap retrieveContactPhoto(Context context, String contactID) {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            } else {
                photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_account_circle_black_48dp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;

    }

}

