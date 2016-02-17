package com.elisoft.appstud.utils;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

public class DeviceManager {

    public static final String TAG = "DeviceManager";
    public static final String ANDROID = "Android";

    /**
     * Get device id from telephony service
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        SmartLog.error(DeviceManager.class, deviceId);
        return deviceId;
    }

    /**
     * Get device name
     * @param context
     * @return
     */
    public static String getDeviceName(Context context) {
        String deviceName = DeviceManager.ANDROID + "-" + Build.MANUFACTURER + "-" + Build.MODEL;
        SmartLog.error(DeviceManager.class, deviceName);
        return deviceName;
    }

    /**
     * Get screen width
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        int width;
        if (Build.VERSION.SDK_INT >= 13) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            width = display.getWidth();
        }
        return width;
    }

    /**
     * Get screen height
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        int height;
        if (Build.VERSION.SDK_INT >= 13) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            height = display.getHeight();  // deprecated
        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            height -= Constant.statusBarHeight;
//        }
        return height;
    }

    /**
     * Get phone number if possible
     *
     * @param context The context where we want to get phone number
     * @return The phone number
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        SmartLog.error(DeviceManager.class, phoneNumber);
        return phoneNumber;
    }

    public static Location getPhoneLocation() {
        Location location = null;
        return location;
    }
}
