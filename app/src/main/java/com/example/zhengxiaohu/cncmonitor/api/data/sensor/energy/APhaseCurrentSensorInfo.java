package com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/26.
 * Email: 1050087728@qq.com
 */

public class APhaseCurrentSensorInfo {
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String SAMPLES="Samples";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String A_PHASE_CURRENT="a_phase_current";
    private static final String TAG = "APhaseCurrentSensorInfo";
    //name of the A Phase Current Sensor
    private String name;
    //id of the A Phase Current Sensor
    private String id;
    //current of the A Phase Current Sensor
    private String current;

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

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public static APhaseCurrentSensorInfo parse(Element sensorComponentStream){
        APhaseCurrentSensorInfo result=null;
        if (sensorComponentStream==null){
            Log.d(TAG, "parse: sensorComponentStream is null");
        }
        if (sensorComponentStream!=null){
            result=new APhaseCurrentSensorInfo();
            Element samples=sensorComponentStream.element(SAMPLES);

            //Samples
            Element APhaseCurrent=null;

            //Samples
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator=samples.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case A_PHASE_CURRENT:
                            APhaseCurrent=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(sensorComponentStream.attributeValue(NAME));
            result.setId(sensorComponentStream.attributeValue(COMPONENT_ID));
            //APhaseCurrent
            if (APhaseCurrent==null){
                Log.d(TAG, "parse: APhaseCurrent is null");
            }
            if (APhaseCurrent!= null){
                result.setCurrent(APhaseCurrent.getStringValue());
            }
        }
        return result;
    }

}
