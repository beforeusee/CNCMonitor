package com.example.zhengxiaohu.cncmonitor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.devices.ChatterMonitorSensor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 */

public class ChatterMonitorFragment extends Fragment {

    //设备信息
    TextView tvDeviceChatterName;
    TextView tvDeviceChatterAvailability;
    TextView tvDeviceChatterUUID;
    TextView tvDeviceChatterID;
    //控件信息
    TextView tvSoundChatter;
    TextView tvXChatter;
    TextView tvYChatter;
    TextView tvZChatter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chatter_monitor,container,false);
        initView(view);
        return view;
    }

    /**
     * 初始化UI控件
     * @param view 控件所在的布局
     */
    private void initView(View view) {
        tvDeviceChatterName= (TextView) view.findViewById(R.id.tv_device_chatter_name);
        tvDeviceChatterAvailability= (TextView) view.findViewById(R.id.tv_device_chatter_availability);
        tvDeviceChatterUUID= (TextView) view.findViewById(R.id.tv_device_chatter_uuid);
        tvDeviceChatterID= (TextView) view.findViewById(R.id.tv_device_chatter_id);
        tvSoundChatter= (TextView) view.findViewById(R.id.tvSoundChatter);
        tvXChatter= (TextView) view.findViewById(R.id.tvXChatter);
        tvYChatter= (TextView) view.findViewById(R.id.tvYChatter);
        tvZChatter= (TextView) view.findViewById(R.id.tvZChatter);
    }

    /**
     * 接收EventBus消息
     * @param event DeviceStatusMessageEvent类型的参数
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceStatusMessageEvent(DeviceStatusMessageEvent event){
        ChatterMonitorSensor sensor=event.mDeviceStatus.getChatterMonitorSensor();
        setData(sensor);
    }

    /**
     * 设置UI数据
     * @param sensor 需要设置的传感器
     */
    private void setData(ChatterMonitorSensor sensor) {
        tvDeviceChatterName.setText(sensor.getDeviceChatterInfo().getName());
        tvDeviceChatterAvailability.setText(sensor.getDeviceChatterInfo().getAvailability());
        tvDeviceChatterUUID.setText(sensor.getUniqueId());
        tvDeviceChatterID.setText(sensor.getDeviceChatterInfo().getId());
        tvSoundChatter.setText(sensor.getSoundPressureSensorInfo().getSoundChatter());
        tvXChatter.setText(sensor.getAccelerationSensorInfo().getXChatter());
        tvYChatter.setText(sensor.getAccelerationSensorInfo().getYChatter());
        tvZChatter.setText(sensor.getAccelerationSensorInfo().getZChatter());

        //Test
        test();
    }

    private  void test(){
        tvDeviceChatterName.setText("ChatterSensor");
        tvDeviceChatterAvailability.setText("AVAILABLE");
        tvDeviceChatterUUID.setText("NI-cDAQ-9188");
        tvDeviceChatterID.setText("chatter_sensor");
        tvSoundChatter.setText("NORMAL");
        tvXChatter.setText("NORMAL");
        tvYChatter.setText("NORMAL");
        tvZChatter.setText("NORMAL");
    }
    /**
     * 注册EventBus
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * 注销EventBus
     */
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
