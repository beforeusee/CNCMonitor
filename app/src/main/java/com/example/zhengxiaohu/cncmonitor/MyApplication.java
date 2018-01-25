package com.example.zhengxiaohu.cncmonitor;

import android.app.Activity;
import android.app.Application;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public class MyApplication extends Application {
    private static Activity currentActivity=null;

    public static Activity getCurrentActivity(){
        return currentActivity;
    }
    public static void setCurrentActivity(Activity activity){
        currentActivity=activity;
    }
}
