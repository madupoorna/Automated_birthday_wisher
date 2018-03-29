package com.ctse.automatedbirthdaywisher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Madupoorna on 3/28/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        long today = c.getTimeInMillis();

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(today);

        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        int value = getArguments().getInt("textField");
        textView = getActivity().findViewById(value);
        textView.setText(year + "/" + ++month + "/" + day);

    }
}
