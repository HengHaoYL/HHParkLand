package com.henghao.parkland.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    public static final String[] permissions = {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA",
            "android.permission.GET_ACCOUNTS",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static String[] checkPermissions(Context context) {
        List<String> permissionsNeed = new ArrayList<>();
        for (String permission : permissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                permissionsNeed.add(permission);
        }
        return permissionsNeed.toArray(new String[permissionsNeed.size()]);
    }
}
