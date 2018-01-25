package com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/26.
 * Email: 1050087728@qq.com
 */

public class AccelerationSensorInfo {
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String CONDITION ="Condition";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String X_CHATTER="x_chatter";
    private static final String Y_CHATTER="y_chatter";
    private static final String Z_CHATTER="z_chatter";
    //name of the accelerationSensor
    private String name;
    //id of the accelerationSensor
    private String id;
    //information of chatter in X direction
    private String XChatter;
    //information of chatter in Y direction
    private String YChatter;
    //information of chatter in Z direction
    private String ZChatter;
    //value of XChatter
    private String XChatterValue;
    //value of YChatter
    private String YChatterValue;
    //value of ZChatter
    private String ZChatterValue;

    private static final String TAG = "AccelerationSensorInfo";

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

    public String getXChatter() {
        return XChatter;
    }

    public void setXChatter(String XChatter) {
        this.XChatter = XChatter;
    }

    public String getYChatter() {
        return YChatter;
    }

    public void setYChatter(String YChatter) {
        this.YChatter = YChatter;
    }

    public String getZChatter() {
        return ZChatter;
    }

    public void setZChatter(String ZChatter) {
        this.ZChatter = ZChatter;
    }

    public String getXChatterValue() {
        return XChatterValue;
    }

    public void setXChatterValue(String XChatterValue) {
        this.XChatterValue = XChatterValue;
    }

    public String getYChatterValue() {
        return YChatterValue;
    }

    public void setYChatterValue(String YChatterValue) {
        this.YChatterValue = YChatterValue;
    }

    public String getZChatterValue() {
        return ZChatterValue;
    }

    public void setZChatterValue(String ZChatterValue) {
        this.ZChatterValue = ZChatterValue;
    }

    public static AccelerationSensorInfo parse(Element sensorComponentStream){
        AccelerationSensorInfo result=null;
        if (sensorComponentStream==null){
            Log.d(TAG, "parse: sensorComponentStream is null");
        }
        if (sensorComponentStream!=null){
            result=new AccelerationSensorInfo();
            Element condition=sensorComponentStream.element(CONDITION);

            //Events
            Element XChatter=null;
            Element YChatter=null;
            Element ZChatter=null;

            //Events
            if (condition==null){
                Log.d(TAG, "parse: condition is null");
            }
            if (condition!=null){
                for (Iterator iterator=condition.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case X_CHATTER:
                            XChatter=element;
                            break;
                        case Y_CHATTER:
                            YChatter=element;
                            break;
                        case Z_CHATTER:
                            ZChatter=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(sensorComponentStream.attributeValue(NAME));
            result.setId(sensorComponentStream.attributeValue(COMPONENT_ID));
            //XChatter
            if (XChatter==null){
                Log.d(TAG, "parse: XChatter is null");
            }
            if (XChatter!=null){
                result.setXChatter(XChatter.getName());
                result.setXChatterValue(XChatter.getStringValue());
            }
            //YChatter
            if (YChatter==null){
                Log.d(TAG, "parse: YChatter is null");
            }
            if (YChatter!= null){
                result.setYChatter(YChatter.getName());
                result.setYChatterValue(YChatter.getStringValue());
            }
            //ZChatter
            if (ZChatter==null){
                Log.d(TAG, "parse: ZChatter is null");
            }
            if (ZChatter!=null){
                result.setZChatter(ZChatter.getName());
                result.setZChatterValue(ZChatter.getStringValue());
            }
        }
        return result;
    }
}
