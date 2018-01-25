package com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public class DeviceChatterInfo {
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID = "dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String NATIONAL_INSTRUMENTS_AVAIL="national_instruments_avail";
    //name of the chatterDevice
    private String name;
    //id of the chatterDevice
    private String id;
    //availability of the device
    private String availability;

    private static final String TAG = "DeviceChatterInfo";

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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public static DeviceChatterInfo parse(Element deviceComponentStream){
        DeviceChatterInfo result=null;
        if (deviceComponentStream==null){
            Log.d(TAG, "parse: deviceComponentStream is null");
        }
        if (deviceComponentStream!=null){
            result=new DeviceChatterInfo();
            Element events=deviceComponentStream.element(EVENTS);
            //Events
            Element availability=null;

            //Events
            if (events==null){
                Log.d(TAG, "parse: events is null");
            }
            if (events!=null){
                for (Iterator iterator=events.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case NATIONAL_INSTRUMENTS_AVAIL:
                            availability=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(deviceComponentStream.attributeValue(NAME));
            result.setId(deviceComponentStream.attributeValue(COMPONENT_ID));
            //availability
            if (availability==null){
                Log.d(TAG, "parse: availability is null");
            }
            if (availability!=null){
                result.setAvailability(availability.getStringValue());
            }
        }
        return result;
    }
}
