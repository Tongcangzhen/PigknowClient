package com.example.ldjg.pigknowclient.Util;

import android.content.Context;
import android.content.Intent;

import com.example.ldjg.pigknowclient.MainActivity;

/**
 * Created by ldjg on 2018/1/7.
 */

public class UIHelper {
    public static void returnHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}

