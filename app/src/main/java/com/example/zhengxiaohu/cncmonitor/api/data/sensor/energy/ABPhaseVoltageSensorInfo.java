package com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/26.
 * Email: 1050087728@qq.com
 */

public class ABPhaseVoltageSensorInfo {
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String SAMPLES="Samples";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String AB_VOLTAGE="ab_voltage";
    private static final String TAG = "ABPhaseVoltageSensorInf";

    //name of the AB Phase Voltage Sensor
    private String name;
    //id of the AB Phase Voltage Sensor
    private String id;
    //voltage of the AB Phase Voltage Sensor
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

    public static ABPhaseVoltageSensorInfo parse(Element sensorComponentStream){
        ABPhaseVoltageSensorInfo result=null;
        if (sensorComponentStream==null){
            Log.d(TAG, "parse: sensorComponentStream is null");
        }
        if (sensorComponentStream!=null){
            result=new ABPhaseVoltageSensorInfo();
            Element samples=sensorComponentStream.element(SAMPLES);

            //Samples
            Element ABPhaseVoltage=null;

            //Samples
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator=samples.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case AB_VOLTAGE:
                            ABPhaseVoltage=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(sensorComponentStream.attributeValue(NAME));
            result.setId(sensorComponentStream.attributeValue(COMPONENT_ID));
            //ABPhaseVoltage
            if (ABPhaseVoltage==null){
                Log.d(TAG, "parse: ABPhaseVoltage is null");
            }
            if (ABPhaseVoltage!=null){
                result.setVoltage(ABPhaseVoltage.getStringValue());
            }
        }
        return result;
    }
}
