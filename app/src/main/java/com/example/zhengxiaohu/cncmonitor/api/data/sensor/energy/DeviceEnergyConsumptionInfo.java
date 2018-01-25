package com.example.zhengxiaohu.cncmonitor.api.data.sensor.energy;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public class DeviceEnergyConsumptionInfo {
    private static final String SAMPLES = "Samples";
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID = "dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String ACTIVE_POWER="active_power";
    private static final String ENERGY_CONSUMPTION="energy_consumption";
    private static final String POWER_SENSOR_AVAIL="power_sensor_avail";
    private static final String TAG = "DeviceEnergyConsumption";
    //name of the device
    private String name;
    //id of the device
    private String id;
    //availability of the device
    private String availability;
    //active power of the machine
    private String activePower;
    //energy consumption of the machine
    private String energyConsumption;

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

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(String energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public static DeviceEnergyConsumptionInfo parse(Element deviceComponentStream){
        DeviceEnergyConsumptionInfo result=null;
        if (deviceComponentStream==null){
            Log.d(TAG, "parse: deviceComponentStream is null");
        }
        if (deviceComponentStream!=null){
            result=new DeviceEnergyConsumptionInfo();
            Element samples=deviceComponentStream.element(SAMPLES);
            Element events=deviceComponentStream.element(EVENTS);

            //Samples
            Element activePower=null;
            Element energyConsumption=null;
            //Events
            Element availability=null;

            //Samples
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator=samples.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case ACTIVE_POWER:
                            activePower=element;
                            break;
                        case ENERGY_CONSUMPTION:
                            energyConsumption=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            //Events
            if (events==null){
                Log.d(TAG, "parse: events is null");
            }
            if (events!=null){
                for (Iterator iterator=events.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case POWER_SENSOR_AVAIL:
                            availability=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(deviceComponentStream.attributeValue(NAME));
            result.setId(deviceComponentStream.attributeValue(COMPONENT_ID));

            //activePower
            if (activePower==null){
                Log.d(TAG, "parse: activePower is null");
            }
            if (activePower!=null){
                result.setActivePower(activePower.getStringValue());
            }
            //energyConsumption
            if (energyConsumption==null){
                Log.d(TAG, "parse: energyConsumption is null");
            }
            if (energyConsumption!=null){
                result.setEnergyConsumption(energyConsumption.getStringValue());
            }
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
