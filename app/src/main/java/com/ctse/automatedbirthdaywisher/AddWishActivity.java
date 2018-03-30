package com.ctse.automatedbirthdaywisher;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

public class AddWishActivity extends AppCompatActivity {

    private static final String TAG = "AddWishActivity";

    String name, mobileNumber, mod;
    TextView nameTv, numberTv, setBirthDay, setTime;
    EditText msgEt;
    ImageView imgView;
    DbData data;
    int id;
    Button saveBtn;
    byte[] img;
    MyDBHelper dbHelper;
    Process process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        process = new Process();

        setBirthDay = (TextView) findViewById(R.id.textView4);
        setTime = (TextView) findViewById(R.id.textView7);
        saveBtn = (Button) findViewById(R.id.saveButton);
        msgEt = (EditText) findViewById(R.id.textView9);
        nameTv = (TextView) findViewById(R.id.nameTextView);
        numberTv = (TextView) findViewById(R.id.numberTextView);
        imgView = (ImageView) findViewById(R.id.imageView);

        dbHelper = new MyDBHelper(this);
        mod = getIntent().getStringExtra("modify");

        if (!mod.equals("mod")) {
            //add wish
            Log.d(TAG, "add wish activity");
            this.setTitle(getString(R.string.add_wish));

            name = getIntent().getStringExtra("name");
            mobileNumber = getIntent().getStringExtra("phoneNumber");
            img = getIntent().getByteArrayExtra("photo");

            nameTv.setText(name);
            numberTv.setText(mobileNumber);
            imgView.setImageBitmap(process.getRoundedShape(process.byteToBitMap(img)));
        } else {
            //modify wish
            Log.d(TAG, "modify wish activity");
            this.setTitle(getString(R.string.modify_wish));

            id = getIntent().getIntExtra("id", 0);
            data = dbHelper.getDetailsById(id);

            nameTv.setText(data.getName());
            numberTv.setText(data.getPhoneNumber());
            setBirthDay.setText(data.getDate());
            setTime.setText(data.getTime());
            msgEt.setText(data.getMsg());
            imgView.setImageBitmap(process.getRoundedShape(process.byteToBitMap(data.getImg())));
        }

        //set time
        setTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AddWishActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        setTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //set date
        setBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle args = new Bundle();
                args.putInt("textField", R.id.textView4);
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        //save
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (msgEt.getText() != null) {
                    if (Pattern.matches("[0-9:]*", setTime.getText().toString())
                            && Pattern.matches("[0-9\\/]*", setBirthDay.getText().toString())
                            && !msgEt.getText().toString().isEmpty()) {
                        if (mod.equals("add")) {
                            dbHelper.insertWish(mobileNumber, setTime.getText().toString(), setBirthDay.getText().toString(), msgEt.getText().toString(), name, img);
                            Log.d(TAG, "data inserted");
                            Snackbar.make(v, R.string.data_added, Snackbar.LENGTH_LONG).show();
                            AddWishActivity.super.onBackPressed();
                        } else {
                            dbHelper.update(id, setTime.getText().toString(), setBirthDay.getText().toString(), msgEt.getText().toString());
                            Log.d(TAG, "data updated");
                            Snackbar.make(v, R.string.data_modified, Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(v, R.string.data_not_added, Snackbar.LENGTH_LONG).show();
                        Log.d(TAG, "data insertion failed. invalid fields");
                    }
                } else {
                    Snackbar.make(v, R.string.please_enter_msg, Snackbar.LENGTH_LONG).show();
                    Log.d(TAG, "data insertion failed. message field empty");
                }
            }
        });
    }
}
