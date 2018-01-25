package com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/26.
 * Email: 1050087728@qq.com
 */

public class CBPhaseVoltageSensorInfo {
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String SAMPLES="Samples";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String CB_VOLTAGE="cb_voltage";
    private static final String TAG = "CBPhaseVoltageSensorInf";
    //name of the CB Phase Voltage Sensor
    private String name;
    //id of the CB Phase Voltage Sensor
    private String id;
    //voltage of the CB Phase Voltage Sensor
    private String voltage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public static CBPhaseVoltageSensorInfo parse(Element sensorComponentStream){
        CBPhaseVoltageSensorInfo result=null;
        if (sensorComponentStream==null){
            Log.d(TAG, "parse: sensorComponentStream is null");
        }
        if (sensorComponentStream!=null){
            result=new CBPhaseVoltageSensorInfo();
            Element samples=sensorComponentStream.element(SAMPLES);

            //Samples
            Element CBPhaseVoltage=null;

            //Samples
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator = samples.elementIterator(); iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case CB_VOLTAGE:
                            CBPhaseVoltage=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(sensorComponentStream.attributeValue(NAME));
            result.setId(sensorComponentStream.attributeValue(COMPONENT_ID));
            //CBPhaseVoltage
            if (CBPhaseVoltage==null){
                Log.d(TAG, "parse: CBPhaseVoltage is null");
            }
            if (CBPhaseVoltage!=null){
                result.setVoltage(CBPhaseVoltage.getStringValue());
            }
        }
        return result;
    }
}
