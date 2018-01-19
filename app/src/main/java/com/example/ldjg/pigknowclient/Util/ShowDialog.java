package com.example.ldjg.pigknowclient.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.ldjg.pigknowclient.SelectFarmsActivity;

/**
 * Created by ldjg on 2017/12/28.
 */

public class ShowDialog {
    public static void showFillFarmsDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("开启录像功能前需要完善您的个人信息")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(context, SelectFarmsActivity.class);
                        context.startActivity(intent);
                    }
                })
                .create().show();
    }

    public static void showFillPhoneDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("你还未填写手机号")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("去填写", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public static void showCheckPhoneDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("您还未验证手机号")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("去验证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public static void showResetNumEmptyDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("修改数量不能为空")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public static void showRestNumErrorDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("修改的数量与原数量相同")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public static void showDateWrongDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("pigknow")
                .setMessage("日期选择错误")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

}
