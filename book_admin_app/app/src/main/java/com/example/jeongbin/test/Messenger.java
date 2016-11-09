package com.example.jeongbin.test;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;
/**
 * Created by JeongBin on 2016-11-10.
 */

public class Messenger {
    private Context mContext;

    public Messenger(Context mContext) {
        this.mContext = mContext;
    }

    public void sendMessageTo(String phoneNum, String message) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, message, null, null);

        Toast.makeText(mContext, "성공적으로 메세지를 보냈습니다.",
                Toast.LENGTH_SHORT).show();
    }
}
