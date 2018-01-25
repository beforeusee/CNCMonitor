package com.example.zhengxiaohu.cncmonitor.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by XiaoHu Zheng on 2017/6/4.
 * Email: 1050087728@qq.com
 */

public class TextResourceReader {
    public static String readTextFileFromResource(Context context,int resourceId){
        StringBuilder builder=new StringBuilder();

        try {
            InputStream inputStream=context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            String  nextLine;
            while ((nextLine=bufferedReader.readLine())!=null){
                builder.append(nextLine);
                builder.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("could not open resource: "+resourceId,e);
        } catch (Resources.NotFoundException nfe){
            throw new RuntimeException("resource not found: "+resourceId,nfe);
        }

        return builder.toString();
    }
}
