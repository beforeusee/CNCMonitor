package com.example.zhengxiaohu.cncmonitor;

import com.example.zhengxiaohu.cncmonitor.device_details.DeviceStatus;

/**
 * Created by XiaoHu Zheng on 2017/6/1.
 * Email: 1050087728@qq.com
 */

public class DeviceStatusMessageEvent {
    public final DeviceStatus mDeviceStatus;

    public DeviceStatusMessageEvent(DeviceStatus deviceStatus) {
        mDeviceStatus = deviceStatus;
    }
}
