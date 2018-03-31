package com.ctse.automatedbirthdaywisher.background_task;

import android.content.Context;
import android.os.AsyncTask;


/**
 * Created by Kasun on 3/30/2018.
 */

public class MsgJobExecuter extends AsyncTask<Void, Void, String> {


    MsgSentLogic logic;
    private Context mContext;

    public MsgJobExecuter(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        logic = new MsgSentLogic(mContext);
        return logic.sendMsg();
    }

}
