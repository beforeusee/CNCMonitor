package com.example.zhengxiaohu.cncmonitor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by XiaoHu Zheng on 2017/6/1.
 * Email: 1050087728@qq.com
 */

public class NetworkStatus {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();

        return (info!=null&&info.isAvailable());
    }
}
