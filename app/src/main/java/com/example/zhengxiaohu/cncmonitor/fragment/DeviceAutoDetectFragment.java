package com.example.zhengxiaohu.cncmonitor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhengxiaohu.cncmonitor.R;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 */

public class DeviceAutoDetectFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_device_auto_detect,container,false);
        return view;
    }
}
