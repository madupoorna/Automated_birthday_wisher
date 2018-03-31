package com.ctse.automatedbirthdaywisher.background_task;

import android.content.Context;
import android.telephony.SmsManager;

import com.ctse.automatedbirthdaywisher.DbData;
import com.ctse.automatedbirthdaywisher.MyDBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kasun on 3/30/2018.
 */

public class MsgSentLogic {

    Calendar now = Calendar.getInstance();
    String systemDate, number, name, msg, date, sentNames;
    List<DbData> list = new ArrayList<>();
    MyDBHelper dbHelper;
    int id, flag;

    private Context mContext;

    public MsgSentLogic(Context context) {
        mContext = context;
    }

    public String sendMsg() {

        dbHelper = new MyDBHelper(mContext);
        list = dbHelper.getAllWishes();
        systemDate = getSystemDate();
        sentNames = "";

        for (int x = 0; x < list.size(); x++) {
            id = list.get(x).getId();
            flag = list.get(x).getFlag();
            number = list.get(x).getPhoneNumber();
            msg = list.get(x).getMsg();
            name = list.get(x).getName();
            date = list.get(x).getTime();

            if (!date.substring(0, 4).equals(String.valueOf(now.get(Calendar.YEAR)))) {
                dbHelper.updateFlag(id, 0);
            }

            if (date.substring(5).equals(systemDate) && flag == 0) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, msg, null, null);
                dbHelper.updateFlag(id, 1);
                sentNames = sentNames + (name + ", ");
            }
        }

        return sentNames;
    }

    public String getSystemDate() {
        int month = now.get(Calendar.MONTH) + 1; // Get hour in 24 hour format
        int day = now.get(Calendar.DAY_OF_MONTH);

        return month + "/" + day;
    }
}
