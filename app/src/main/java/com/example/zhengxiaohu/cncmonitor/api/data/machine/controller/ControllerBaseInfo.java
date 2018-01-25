package com.example.zhengxiaohu.cncmonitor.api.data.machine.controller;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class ControllerBaseInfo extends ControllerInfo {

    private static final String EVENTS ="Events";
    private static final String CONDITION = "Condition";
    private static final String DATA_ITEM_ID = "dataItemId";
    private static final String E_STOP="e_stop";
    private static final String ADS_CONDITION="ads_condition";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";

    private static final String TAG = "ControllerBaseInfo";

    //emergency stop condition of the cnc
    private String emergencyStop;
    //communication condition of cnc
    private String communication;
    private String communicationValue;

    public String getEmergencyStop() {
        return emergencyStop;
    }

    public void setEmergencyStop(String emergencyStop) {
        this.emergencyStop = emergencyStop;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getCommunicationValue() {
        return communicationValue;
    }

    public void setCommunicationValue(String communicationValue) {
        this.communicationValue = communicationValue;
    }


    public static ControllerBaseInfo parse(Element controllerComponentStream){
        ControllerBaseInfo result=null;
        if (controllerComponentStream==null){
            Log.d(TAG, "parse: controllerComponentStream is null");
        }
        if (controllerComponentStream!=null){
            result=new ControllerBaseInfo();

            Element events=controllerComponentStream.element(EVENTS);
            Element condition=controllerComponentStream.element(CONDITION);

            //Events
            Element emergencyStop=null;
            //Condition
            Element communication=null;

            //Events
            if (events==null){
                Log.d(TAG, "parse: events is null");
            }
            if (events!=null){
                for (Iterator iterator=events.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case E_STOP:
                            emergencyStop=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            //Condition
            if (condition==null){
                Log.d(TAG, "parse: condition is null");
            }
            if (condition!=null){
                for (Iterator iterator=condition.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case ADS_CONDITION:
                            communication=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(controllerComponentStream.attributeValue(NAME));
            result.setId(controllerComponentStream.attributeValue(COMPONENT_ID));
            //emergencyStop
            if (emergencyStop==null){
                Log.d(TAG, "parse: emergencyStop is null");
            }
            if (emergencyStop!=null){
                result.setEmergencyStop(emergencyStop.getStringValue());
            }
            //communication
            if (communication==null){
                Log.d(TAG, "parse: communication is null");
            }
            if (communication!=null){
                result.setCommunication(communication.getName());
                result.setCommunicationValue(communication.getStringValue());
            }
        }
        return result;
    }
}
