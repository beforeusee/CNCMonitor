package com.example.zhengxiaohu.cncmonitor.device_details;

import android.util.Log;

import com.example.zhengxiaohu.cncmonitor.api.data.machine.DeviceMachineInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearXInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearYInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearZInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotaryAInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotaryCInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotarySInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerBaseInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerPathInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.systems.PneumaticInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.AccelerationSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.DeviceChatterInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter.SoundPressureSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.ABPhaseVoltageSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.APhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.BPhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.CBPhaseVoltageSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.CPhaseCurrentSensorInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy.DeviceEnergyConsumptionInfo;
import com.example.zhengxiaohu.cncmonitor.api.http.Requests;
import com.example.zhengxiaohu.cncmonitor.devices.ChatterMonitorSensor;
import com.example.zhengxiaohu.cncmonitor.devices.EnergyConsumptionMonitorSensor;
import com.example.zhengxiaohu.cncmonitor.devices.MachineTool;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by XiaoHu Zheng on 2017/5/21.
 * Email: 1050087728@qq.com
 */

public class DeviceStatus {
    //MachineTool对象
    private MachineTool mMachineTool;
    //ChatterMonitorSensor对象
    private ChatterMonitorSensor mChatterMonitorSensor;
    //EnergyConsumptionMonitorSensor对象
    private EnergyConsumptionMonitorSensor mEnergyConsumptionMonitorSensor;

    private static DeviceStatus mDeviceStatus;
    private static final String TAG = "DeviceStatus";

    private static final String LUNAN_XH7132A="LUNAN-XH7132A";
    private static final String ELECTRIC_SENSOR="Electric-Sensor";
    private static final String CHATTER_SENSOR="NI-cDAQ-9188";
    private static final String MTCONNECT_STREAMS="MTConnectStreams";
    private static final String STREAMS="Streams";
    private static final String DEVICE_STREAM="DeviceStream";
    private static final String COMPONENT_STREAM="ComponentStream";
    private static final String UUID="uuid";
    private static final String COMPONENT_ID="componentId";

    //Machine Tool相关ComponentStream的ID
    private static final String DEVICE_LU_NAN_ID="mtconnect_device_lunan";
    private static final String AXES_ID="axes";
    private static final String LINEAR_X_ID="x";
    private static final String LINEAR_Y_ID="y";
    private static final String LINEAR_Z_ID="z";
    private static final String ROTARY_A_ID="a";
    private static final String ROTARY_C_ID="c";
    private static final String ROTARY_SPINDLE_ID="spindle";
    private static final String CONTROLLER_ID="controller";
    private static final String PATH_ID="path";
    private static final String SYSTEMS_ID="systems";
    private static final String PNEUMATIC_ID="pneumatic";

    //PowerSensor相关的ComponentStream的ID
    private static final String DEVICE_POWER_SENSOR_ID="power_sensor";
    private static final String SENSOR_A_PHASE_CURRENT_ID="aph_cur_sensor";
    private static final String SENSOR_B_PHASE_CURRENT_ID="bph_cur_sensor";
    private static final String SENSOR_C_PHASE_CURRENT_ID="cph_cur_sensor";
    private static final String SENSOR_AB_PHASE_VOLTAGE_ID="ab_ph_volt_sensor";
    private static final String SENSOR_CB_PHASE_VOLTAGE_ID="cb_ph_volt_sensor";

    //ChatterSensor相关的ComponentStream的ID
    private static final String DEVICE_CHATTER_SENSOR_ID="chatter_sensor";
    private static final String SENSOR_SOUND_PRESSURE_ID="sound_pressure";
    private static final String SENSOR_ACCELERATION_ID="acceleration";

    private DeviceStatus(){
        mMachineTool=new MachineTool();
        mChatterMonitorSensor=new ChatterMonitorSensor();
        mEnergyConsumptionMonitorSensor=new EnergyConsumptionMonitorSensor();
    }

    /**
     * get the device status precise information
     * @param url MTConnect Agent的address
     * @return mDeviceStatus
     */
    public static DeviceStatus getDeviceStatus(String url){

        Document document= Requests.getResult(url);
        if (document==null){
            Log.d(TAG, "getDeviceStatus: "+"document is null.");
            return null;
        }

        try{
            mDeviceStatus=new DeviceStatus();

            //应用框架dom4j解析xml文档

            //获取根元素
            Element root=document.getRootElement();

            //获取Streams元素
            Element streams=root.element(STREAMS);
            Log.d(TAG, "getDeviceStatus: streams is null?"+String.valueOf(streams==null));


            //获取机床,功率传感器设备,颤振监测设备的DeviceStream
            Element machineDeviceStream=null;
            Element powerSensorDeviceStream=null;
            Element chatterSensorDeviceStream=null;

            if (streams==null){
                Log.d(TAG, "getDeviceStatus: streams is null");
            }else {
                for (Iterator iterator=streams.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    //uuid属性是所有Device都必须设置的标记属性,用于区分不同的Device,对于每个uuid值是独特的
                    switch (element.attributeValue(UUID)){
                        case LUNAN_XH7132A:
                            machineDeviceStream=element;
                            Log.d(TAG, "getDeviceStatus: initialize machineDeviceStream success");
                            break;
                        case ELECTRIC_SENSOR:
                            powerSensorDeviceStream=element;
                            Log.d(TAG, "getDeviceStatus: initialize powerSensorDeviceStream success");
                            break;
                        case CHATTER_SENSOR:
                            chatterSensorDeviceStream=element;
                            Log.d(TAG, "getDeviceStatus: initialize chatterSensorDeviceStream success");
                            break;
                        default:
                            break;
                    }
                }
            }

            //解析机床
            if (machineDeviceStream==null){
                Log.d(TAG, "getDeviceStatus: machineDeviceStream is null");
            }else {
                parseMachineDevice(machineDeviceStream);
            }

            //解析功率传感器
            if (powerSensorDeviceStream==null){
                Log.d(TAG, "getDeviceStatus: powerSensorDeviceStream is null");
            }else {
                parsePowerSensorDevice(powerSensorDeviceStream);
            }

            //解析颤振监测传感器
            if (chatterSensorDeviceStream==null){
                Log.d(TAG, "getDeviceStatus: chatterSensorDeviceStream is null");
            }else {
                parseChatterSensorDevice(chatterSensorDeviceStream);
            }

        } finally {

        }
        return mDeviceStatus;
    }

    /**
     * parse chatterSensorDeviceStream and save the data to model
     * @param chatterSensorDeviceStream chatterSensor device stream
     */
    private static void parseChatterSensorDevice(Element chatterSensorDeviceStream) {
        //解析chatterSensorDeviceStream并将信息存入模型
        if (chatterSensorDeviceStream==null){
            Log.d(TAG, "parseChatterSensorDevice: chatterSensorDeviceStream is null");
        }
        if (chatterSensorDeviceStream!=null){
            List<Element> chatterSensorComponentStreamList=chatterSensorDeviceStream.elements();
            //定义CHATTER_SENSOR的Component组件流ComponentStream
            Element deviceChatterSensorComponentStream=null;
            Element sensorSoundPressureComponentStream=null;
            Element sensorAccelerationComponentStream=null;

            //初始化CHATTER_SENSOR的ComponentStream

            for (Iterator iterator = chatterSensorDeviceStream.elementIterator(); iterator.hasNext();){
                Element element= (Element) iterator.next();
                switch (element.attributeValue(COMPONENT_ID)){
                    case DEVICE_CHATTER_SENSOR_ID:
                        deviceChatterSensorComponentStream=element;
                        break;
                    case SENSOR_SOUND_PRESSURE_ID:
                        sensorSoundPressureComponentStream=element;
                        break;
                    case SENSOR_ACCELERATION_ID:
                        sensorAccelerationComponentStream=element;
                        break;
                    default:
                        break;
                }
            }
            //解析各组件ComponentStream信息
            mDeviceStatus.mChatterMonitorSensor.setUniqueId(chatterSensorDeviceStream.attributeValue(UUID));
            //deviceChatterSensorComponentStream
            if (deviceChatterSensorComponentStream==null){
                Log.d(TAG, "parseChatterSensorDevice: deviceChatterSensorComponentStream is null");
            }else {
                mDeviceStatus.mChatterMonitorSensor.setDeviceChatterInfo(DeviceChatterInfo.parse(deviceChatterSensorComponentStream));
            }
            //sensorSoundPressureComponentStream
            if (sensorSoundPressureComponentStream==null){
                Log.d(TAG, "parseChatterSensorDevice: sensorSoundPressureComponentStream is null");
            }else {
                mDeviceStatus.mChatterMonitorSensor.setSoundPressureSensorInfo(SoundPressureSensorInfo.parse(sensorSoundPressureComponentStream));
            }
            //sensorAccelerationComponentStream
            if (sensorAccelerationComponentStream==null){
                Log.d(TAG, "parseChatterSensorDevice: sensorAccelerationComponentStream is null");
            }else {
                mDeviceStatus.mChatterMonitorSensor.setAccelerationSensorInfo(AccelerationSensorInfo.parse(sensorAccelerationComponentStream));
            }
        }
    }

    /**
     * parse powerSensorDeviceStream and save the data to model
     * @param powerSensorDeviceStream powerSensor device stream
     */
    private static void parsePowerSensorDevice(Element powerSensorDeviceStream) {
        //解析powerSensorDeviceStream并将信息存入模型
        if (powerSensorDeviceStream==null){
            Log.d(TAG, "parsePowerSensorDevice: powerSensorDeviceStream is null");
        }
        if (powerSensorDeviceStream!=null){
            List<Element> powerSensorComponentStreamList=powerSensorDeviceStream.elements();
            //定义ELECTRIC_SENSOR的Component组件流ComponentStream
            Element devicePowerSensorComponentStream=null;
            Element sensorAPhaseCurrentComponentStream=null;
            Element sensorBPhaseCurrentComponentStream=null;
            Element sensorCPhaseCurrentComponentStream=null;
            Element sensorABPhaseVoltageComponentStream=null;
            Element sensorCBPhaseVoltageComponentStream=null;

            //初始化ELECTRIC_SENSOR的ComponentStream
            for (Iterator iterator = powerSensorDeviceStream.elementIterator(); iterator.hasNext();){
                Element element= (Element) iterator.next();
                switch (element.attributeValue(COMPONENT_ID)){
                    case DEVICE_POWER_SENSOR_ID:
                        devicePowerSensorComponentStream=element;
                        break;
                    case SENSOR_A_PHASE_CURRENT_ID:
                        sensorAPhaseCurrentComponentStream=element;
                        break;
                    case SENSOR_B_PHASE_CURRENT_ID:
                        sensorBPhaseCurrentComponentStream=element;
                        break;
                    case SENSOR_C_PHASE_CURRENT_ID:
                        sensorCPhaseCurrentComponentStream=element;
                        break;
                    case SENSOR_AB_PHASE_VOLTAGE_ID:
                        sensorABPhaseVoltageComponentStream=element;
                        break;
                    case SENSOR_CB_PHASE_VOLTAGE_ID:
                        sensorCBPhaseVoltageComponentStream=element;
                        break;
                    default:
                        break;
                }
            }
            //解析各组件ComponentStream信息
            mDeviceStatus.mEnergyConsumptionMonitorSensor.setUniqueId(powerSensorDeviceStream.attributeValue(UUID));
            //devicePowerSensorComponentStream
            if (devicePowerSensorComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: devicePowerSensorComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setDeviceEnergyConsumptionInfo(DeviceEnergyConsumptionInfo.parse(devicePowerSensorComponentStream));
            }
            //sensorAPhaseCurrentComponentStream
            if (sensorAPhaseCurrentComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: sensorAPhaseCurrentComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setAPhaseCurrentSensorInfo(APhaseCurrentSensorInfo.parse(sensorAPhaseCurrentComponentStream));
            }
            //sensorBPhaseCurrentComponentStream
            if (sensorBPhaseCurrentComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: sensorBPhaseCurrentComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setBPhaseCurrentSensorInfo(BPhaseCurrentSensorInfo.parse(sensorBPhaseCurrentComponentStream));
            }
            //sensorCPhaseCurrentComponentStream
            if (sensorCPhaseCurrentComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: sensorCPhaseCurrentComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setCPhaseCurrentSensorInfo(CPhaseCurrentSensorInfo.parse(sensorCPhaseCurrentComponentStream));
            }
            //sensorABPhaseVoltageComponentStream
            if (sensorABPhaseVoltageComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: sensorABPhaseVoltageComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setCBPhaseVoltageSensorInfo(CBPhaseVoltageSensorInfo.parse(sensorCBPhaseVoltageComponentStream));
            }
            //sensorABPhaseVoltageComponentStream
            if (sensorABPhaseVoltageComponentStream==null){
                Log.d(TAG, "parsePowerSensorDevice: sensorABPhaseVoltageComponentStream is null");
            }else {
                mDeviceStatus.mEnergyConsumptionMonitorSensor.setABPhaseVoltageSensorInfo(ABPhaseVoltageSensorInfo.parse(sensorABPhaseVoltageComponentStream));
            }
        }
    }

    /**
     * parse machineDeviceStream and save the data to model
     * @param machineDeviceStream machine device stream
     */
    private static void parseMachineDevice(Element machineDeviceStream) {
        //解析machineComponentStream并将信息存入模型中
        if (machineDeviceStream==null){
            Log.d(TAG, "parseMachineDevice: machineDeviceStream is null");
        }
        if (machineDeviceStream!=null){
            //定义Machine Tool的所有Component组件流ComponentStream，最好参考componentId进行命名以区分类似的组件
            Element deviceLuNanComponentStream=null;
            Element axesComponentStream=null;
            Element linearXComponentStream=null;
            Element linearYComponentStream=null;
            Element linearZComponentStream=null;
            Element rotaryAComponentStream=null;
            Element rotaryCComponentStream=null;
            Element rotarySComponentStream=null;
            Element controllerBaseComponentStream=null;
            Element controllerPathComponentStream=null;
            Element systemsComponentStream=null;
            Element pneumaticComponentStream=null;

            //初始化以上定义的Machine Tool的ComponentStream
            for (Iterator iterator = machineDeviceStream.elementIterator(); iterator.hasNext();){
                Element element= (Element) iterator.next();
                switch (element.attributeValue(COMPONENT_ID)){
                    case DEVICE_LU_NAN_ID:
                        deviceLuNanComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize deviceLuNanComponentStream success");
                        break;
                    case AXES_ID:
                        axesComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize axesComponentStream success");
                        break;
                    case LINEAR_X_ID:
                        linearXComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize linearXComponentStream success");
                        break;
                    case LINEAR_Y_ID:
                        linearYComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize linearYComponentStream success");
                        break;
                    case LINEAR_Z_ID:
                        linearZComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize linearZComponentStream success");
                        break;
                    case ROTARY_A_ID:
                        rotaryAComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize rotaryAComponentStream success");
                        break;
                    case ROTARY_C_ID:
                        rotaryCComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize rotaryCComponentStream success");
                        break;
                    case ROTARY_SPINDLE_ID:
                        rotarySComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize rotarySComponentStream success");
                        break;
                    case CONTROLLER_ID:
                        controllerBaseComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize controllerBaseComponentStream success");
                        break;
                    case PATH_ID:
                        controllerPathComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize controllerPathComponentStream success");
                        break;
                    case SYSTEMS_ID:
                        systemsComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize systemsComponentStream success");
                        break;
                    case PNEUMATIC_ID:
                        pneumaticComponentStream=element;
                        Log.d(TAG, "parseMachineDevice: initialize pneumaticComponentStream success");
                        break;
                    default:
                        break;
                }
            }

            //解析各组件ComponentStream信息
            mDeviceStatus.mMachineTool.setUniqueId(machineDeviceStream.attributeValue(UUID));

            //deviceLuNanComponentStream
            if (deviceLuNanComponentStream==null){
                Log.d(TAG, "parseMachineDevice: deviceLuNanComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setDeviceMachineInfo(DeviceMachineInfo.parse(deviceLuNanComponentStream));
            }
            //axesComponentStream
            if (axesComponentStream==null){
                Log.d(TAG, "parseMachineDevice: axesComponentStream is null");
            }else {
                //in this case ,doesn't exist
            }
            //linearXComponentStream
            if (linearXComponentStream==null){
                Log.d(TAG, "parseMachineDevice: linearXComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesLinearXInfo(AxesLinearXInfo.parse(linearXComponentStream));
            }
            //linearYComponentStream
            if (linearYComponentStream==null){
                Log.d(TAG, "parseMachineDevice: linearYComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesLinearYInfo(AxesLinearYInfo.parse(linearYComponentStream));
            }
            //linearZComponentStream
            if (linearZComponentStream==null){
                Log.d(TAG, "parseMachineDevice: linearZComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesLinearZInfo(AxesLinearZInfo.parse(linearZComponentStream));
            }
            //rotaryAComponentStream
            if (rotaryAComponentStream==null){
                Log.d(TAG, "parseMachineDevice: rotaryAComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesRotaryAInfo(AxesRotaryAInfo.parse(rotaryAComponentStream));
            }
            //rotaryCComponentStream
            if (rotaryCComponentStream==null){
                Log.d(TAG, "parseMachineDevice: rotaryCComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesRotaryCInfo(AxesRotaryCInfo.parse(rotaryCComponentStream));
            }
            //rotarySComponentStream
            if (rotarySComponentStream==null){
                Log.d(TAG, "parseMachineDevice: rotarySComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setAxesRotarySInfo(AxesRotarySInfo.parse(rotarySComponentStream));
            }
            //controllerBaseComponentStream
            if (controllerBaseComponentStream==null){
                Log.d(TAG, "parseMachineDevice: controllerBaseComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setControllerBaseInfo(ControllerBaseInfo.parse(controllerBaseComponentStream));
            }
            //controllerPathComponentStream
            if (controllerPathComponentStream==null){
                Log.d(TAG, "parseMachineDevice: controllerPathComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setControllerPathInfo(ControllerPathInfo.parse(controllerPathComponentStream));
            }
            //pneumaticComponentStream
            if (pneumaticComponentStream==null){
                Log.d(TAG, "parseMachineDevice: pneumaticComponentStream is null");
            }else {
                mDeviceStatus.mMachineTool.setPneumaticInfo(PneumaticInfo.parse(pneumaticComponentStream));
            }
        }
    }

    public MachineTool getMachineTool() {
        return mMachineTool;
    }

    public ChatterMonitorSensor getChatterMonitorSensor() {
        return mChatterMonitorSensor;
    }

    public EnergyConsumptionMonitorSensor getEnergyConsumptionMonitorSensor() {
        return mEnergyConsumptionMonitorSensor;
    }

    public void setMachineTool(MachineTool machineTool) {
        mMachineTool = machineTool;
    }

    public void setChatterMonitorSensor(ChatterMonitorSensor chatterMonitorSensor) {
        mChatterMonitorSensor = chatterMonitorSensor;
    }

    public void setEnergyConsumptionMonitorSensor(EnergyConsumptionMonitorSensor energyConsumptionMonitorSensor) {
        mEnergyConsumptionMonitorSensor = energyConsumptionMonitorSensor;
    }
}
