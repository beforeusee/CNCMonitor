package com.example.zhengxiaohu.cncmonitor.devices;

import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.AccelerationSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.DeviceChatterInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.SoundPressureSensorInfo;

/**
 * Created by XiaoHu Zheng on 2017/5/21.
 * Email: 1050087728@qq.com
 */

public class ChatterMonitorSensor {

    //uniqueId(uuid)of the chatter monitor sensor device
    private String uniqueId;
    //the base device information of the device
    private DeviceChatterInfo mDeviceChatterInfo;
    //SoundPressure sensor information
    private SoundPressureSensorInfo mSoundPressureSensorInfo;
    //Acceleration sensor information
    private AccelerationSensorInfo mAccelerationSensorInfo;

    public ChatterMonitorSensor(){
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public DeviceChatterInfo getDeviceChatterInfo() {
        return mDeviceChatterInfo;
    }

    public void setDeviceChatterInfo(DeviceChatterInfo deviceChatterInfo) {
        mDeviceChatterInfo = deviceChatterInfo;
    }

    public SoundPressureSensorInfo getSoundPressureSensorInfo() {
        return mSoundPressureSensorInfo;
    }

    public void setSoundPressureSensorInfo(SoundPressureSensorInfo soundPressureSensorInfo) {
        mSoundPressureSensorInfo = soundPressureSensorInfo;
    }

    public AccelerationSensorInfo getAccelerationSensorInfo() {
        return mAccelerationSensorInfo;
    }

    public void setAccelerationSensorInfo(AccelerationSensorInfo accelerationSensorInfo) {
        mAccelerationSensorInfo = accelerationSensorInfo;
    }
}
