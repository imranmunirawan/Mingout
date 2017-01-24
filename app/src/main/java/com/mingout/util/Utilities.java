package com.mingout.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

public class Utilities {

    public static void showAlertDialog(Context context, String title,
                                       String message) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } catch (Exception ex) {

        }
    }

    public static String imageToBase64(Bitmap bitmap) {
        byte[] bs = null;
        String imageBase64String = null;
        try {
            bs = getCompressedImage(bitmap, CompressFormat.JPEG, 80);
            imageBase64String = getBase64String(bs, Base64.DEFAULT);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return imageBase64String;
    }

    public static String imageToBase64Medium(Bitmap bitmap) {
        byte[] bs = null;
        String imageBase64String = null;
        try {
            bs = getCompressedImage(bitmap, CompressFormat.JPEG, 100);
            imageBase64String = getBase64String(bs, Base64.DEFAULT);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return imageBase64String;
    }

    private static byte[] getCompressedImage(Bitmap bitmap,
                                             CompressFormat format, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, quality, stream);
        return stream.toByteArray();
    }

    private static String getBase64String(byte[] input, int flag) {
        String encoded = Base64.encodeToString(input, flag);
        return encoded;
    }

    public static boolean isConnected(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting ()) {
            return true;
        } else if (mobile.isConnectedOrConnecting ()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getXMPPuid(String sEmail) {

        if(sEmail.contains("null")){
            return "";
        }else {
            String xmppUid = "", e = sEmail;
            String[] email = e.split("\\@");

            xmppUid = email[0];
            String[] emaill = email[1].toString().split("\\.");
            xmppUid += "_" + emaill[0];
            xmppUid += "_" + emaill[1];

            return xmppUid;
        }
    }

    public static int getAgeFromDOB(String DOB) {
        int age = 0, yearr;
        try {
            Calendar calendar = Calendar.getInstance();
            yearr = calendar.get(Calendar.YEAR);

            int year;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String dateTime = DOB;
            String[] dayTimer = dateTime.split("\\-");
            year = Integer.valueOf(dayTimer[0]);

            age = yearr - year;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return age;
    }
}