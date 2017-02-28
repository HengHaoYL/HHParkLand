package com.henghao.parkland.utils;

import android.content.Context;
import android.content.Intent;

import com.henghao.parkland.activity.SiftImagesActivity;

import java.util.ArrayList;

/**
 * Created by zhangxianwen on 2017/2/28 0028.
 */

public class ScanImageUtils {
    /**
     * 图片查看跳转
     * @param mContext
     * @param mUrl 图片地址集合
     * @param page 当前图片0,1,2......
     */
    public static void ScanImage(Context mContext, ArrayList<String> mUrl, int page) {
        Intent intent = new Intent();
        intent.setClass(mContext, SiftImagesActivity.class);
        intent.putExtra("PAGE", page + 1);
        intent.putStringArrayListExtra("path", mUrl);
        mContext.startActivity(intent);
    }

}
