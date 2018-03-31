package com.ctse.automatedbirthdaywisher;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddWishManuallyActivity extends AppCompatActivity {

    private static final String TAG = "AddWishManuallyActivity";
    private static int RESULT_LOAD_IMAGE = 1;

    ImageView imageView;
    TextView setDateTv, setTimeTv;
    EditText nameTv, numbertv, msgEt;
    Button browse, save;
    String name, number, time, date, msg;
    byte[] img;
    Process process;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish_manually);

        this.setTitle(getString(R.string.add_wish));

        imageView = (ImageView) findViewById(R.id.imageView3);
        nameTv = (EditText) findViewById(R.id.editText);
        numbertv = (EditText) findViewById(R.id.editText2);
        setDateTv = (TextView) findViewById(R.id.textView15);
        setTimeTv = (TextView) findViewById(R.id.textView16);
        msgEt = (EditText) findViewById(R.id.textView9);
        browse = (Button) findViewById(R.id.button2);
        save = (Button) findViewById(R.id.button);

        process = new Process();
        dbHelper = new MyDBHelper(this);

        //open gallery
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        //set time
        setTimeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AddWishManuallyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        setTimeTv.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle(R.string.set_time);
                mTimePicker.show();
            }
        });

        //set date
        setDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle args = new Bundle();
                args.putInt("textField", R.id.textView15);
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTv.getText().toString();
                number = numbertv.getText().toString();
                time = setTimeTv.getText().toString();
                date = setDateTv.getText().toString();
                msg = msgEt.getText().toString();
                img = process.drawableTobyte(imageView.getDrawable());

                if (name != null && number != null && msg != null && !msg.isEmpty() && !name.isEmpty() && !number.isEmpty()) {
                    if (process.isValidMobile(number) && Pattern.matches("[0-9:]*", time) && Pattern.matches("[0-9\\/]*", date)) {
                        if (date.substring(5).equals(process.getSystemDate())) {
                            dbHelper.insertWish(number, time, date, msg, name, img, 1);
                        } else {
                            dbHelper.insertWish(number, time, date, msg, name, img, 0);
                        }
                        Snackbar.make(v, R.string.data_added, Snackbar.LENGTH_LONG).show();
                        boolean sent = process.sentMessage(getApplicationContext(), date, name, number, msg);
                        if (sent) {
                            Snackbar.make(v, "Your wish sent to " + name, Snackbar.LENGTH_LONG).show();
                        }
                        Log.d(TAG, "data added");
                        AddWishManuallyActivity.super.onBackPressed();
                    } else {
                        Snackbar.make(v, R.string.invalid_data_formats, Snackbar.LENGTH_LONG).show();
                        Log.d(TAG, "data not added,invalid data formats");
                    }
                } else {
                    Snackbar.make(v, R.string.data_not_added, Snackbar.LENGTH_LONG).show();
                    Log.d(TAG, "data not added,empty fields");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //choose image from gallery
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView.setImageBitmap(process.getRoundedShape(BitmapFactory.decodeFile(picturePath)));
        }
    }
}
