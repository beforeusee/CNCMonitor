package com.example.zhengxiaohu.cncmonitor.api.data.machine;

import android.graphics.Bitmap;
import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;


/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 * Description of the device
 */

public class DeviceMachineInfo {

    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID = "dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String LU_NAN_AVAIL="lu_nan_avail";
    public static final String TIME_STAMP="timestamp";
    //name of the device
    private String name;
    //id of the device
    private String id;
    //availability of the device
    private String availability;
    //timeStamp of machine
    private String timeStamp;

    //manufacturer of the device
    private String manufacturer;
    //model of the device
    private String model;
    //serial of the device
    private String serial;
    //controller name of the device
    private String controller;

    // Manufacturer Logo
    private String logoUrl;
    public Bitmap logo;

    // Device Image
    private String imageUrl;
    public Bitmap image;

    private static final String TAG = "DeviceMachineInfo";


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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public static DeviceMachineInfo parse(Element deviceComponentStream) {
        DeviceMachineInfo result = null;
        if (deviceComponentStream==null){
            Log.d(TAG, "parse: deviceComponentStream is null");
        }
        if (deviceComponentStream!=null){
            result=new DeviceMachineInfo();

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
                        case LU_NAN_AVAIL:
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
                result.setTimeStamp(availability.attributeValue(TIME_STAMP));
            }
        }

        return result;
    }
}
