package com.example.zhengxiaohu.cncmonitor.devices;

import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.ABPhaseVoltageSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.APhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.BPhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.CBPhaseVoltageSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.CPhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.DeviceEnergyConsumptionInfo;

/**
 * Created by XiaoHu Zheng on 2017/5/21.
 * Email: 1050087728@qq.com
 */

public class EnergyConsumptionMonitorSensor {
    //uuid of the energyConsumption device
    private String uniqueId;
    //base information of the device
    private DeviceEnergyConsumptionInfo mDeviceEnergyConsumptionInfo;
    //A phase current sensor information
    private APhaseCurrentSensorInfo mAPhaseCurrentSensorInfo;
    //B phase current sensor information
    private BPhaseCurrentSensorInfo mBPhaseCurrentSensorInfo;
    //C phase current sensor information
    private CPhaseCurrentSensorInfo mCPhaseCurrentSensorInfo;
    //AB phase voltage sensor information
    private ABPhaseVoltageSensorInfo mABPhaseVoltageSensorInfo;
    //CB phase voltage sensor information
    private CBPhaseVoltageSensorInfo mCBPhaseVoltageSensorInfo;

    public EnergyConsumptionMonitorSensor(){
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public DeviceEnergyConsumptionInfo getDeviceEnergyConsumptionInfo() {
        return mDeviceEnergyConsumptionInfo;
    }

    public void setDeviceEnergyConsumptionInfo(DeviceEnergyConsumptionInfo deviceEnergyConsumptionInfo) {
        mDeviceEnergyConsumptionInfo = deviceEnergyConsumptionInfo;
    }

    public APhaseCurrentSensorInfo getAPhaseCurrentSensorInfo() {
        return mAPhaseCurrentSensorInfo;
    }

    public void setAPhaseCurrentSensorInfo(APhaseCurrentSensorInfo APhaseCurrentSensorInfo) {
        mAPhaseCurrentSensorInfo = APhaseCurrentSensorInfo;
    }

    public BPhaseCurrentSensorInfo getBPhaseCurrentSensorInfo() {
        return mBPhaseCurrentSensorInfo;
    }

    public void setBPhaseCurrentSensorInfo(BPhaseCurrentSensorInfo BPhaseCurrentSensorInfo) {
        mBPhaseCurrentSensorInfo = BPhaseCurrentSensorInfo;
    }

    public CPhaseCurrentSensorInfo getCPhaseCurrentSensorInfo() {
        return mCPhaseCurrentSensorInfo;
    }

    public void setCPhaseCurrentSensorInfo(CPhaseCurrentSensorInfo CPhaseCurrentSensorInfo) {
        mCPhaseCurrentSensorInfo = CPhaseCurrentSensorInfo;
    }

    public ABPhaseVoltageSensorInfo getABPhaseVoltageSensorInfo() {
        return mABPhaseVoltageSensorInfo;
    }

    public void setABPhaseVoltageSensorInfo(ABPhaseVoltageSensorInfo ABPhaseVoltageSensorInfo) {
        mABPhaseVoltageSensorInfo = ABPhaseVoltageSensorInfo;
    }

    public CBPhaseVoltageSensorInfo getCBPhaseVoltageSensorInfo() {
        return mCBPhaseVoltageSensorInfo;
    }

    public void setCBPhaseVoltageSensorInfo(CBPhaseVoltageSensorInfo CBPhaseVoltageSensorInfo) {
        mCBPhaseVoltageSensorInfo = CBPhaseVoltageSensorInfo;
    }
}
