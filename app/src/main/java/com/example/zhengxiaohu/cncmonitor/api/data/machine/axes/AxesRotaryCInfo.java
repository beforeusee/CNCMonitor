package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class AxesRotaryCInfo extends AxesRotaryInfo {
    //负载
    private String load;
    //行程
    private String travel;

    private static final String SAMPLES="Samples";
    private static final String CONDITION="Condition";
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";

    //C
    private static final String C_POS="c_pos";
    private static final String C_ANGULAR_VELOCITY="c_angular_velocity";
    private static final String C_TORQ="c_torq";
    private static final String C_TRAVEL="c_travel";
    private static final String TAG = "AxesRotaryCInfo";

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public static AxesRotaryCInfo parse(Element rotaryComponentStream){
        AxesRotaryCInfo result=null;
        if (rotaryComponentStream==null){
            Log.d(TAG, "parse: rotaryComponentStream is null");
        }
        if (rotaryComponentStream!=null){
            result= new AxesRotaryCInfo();
            Element samples=rotaryComponentStream.element(SAMPLES);
            Element condition=rotaryComponentStream.element(CONDITION);

            Element angleActual=null;
            Element angularVelocityActual=null;
            Element torqueActual=null;
            Element travelCondition=null;
            //Samples类型
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator = samples.elementIterator(); iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case C_POS:
                            angleActual=element;
                            Log.d(TAG, "parse: initialize angleActual success");
                            break;
                        case C_ANGULAR_VELOCITY:
                            angularVelocityActual=element;
                            Log.d(TAG, "parse: initialize angularVelocityActual success");
                            break;
                        case C_TORQ:
                            torqueActual=element;
                            Log.d(TAG, "parse: initialize torqueActual success");
                            break;
                        default:
                            break;
                    }
                }
            }

            //Condition类型
            if (condition==null){
                Log.d(TAG, "parse: condition is null");
            }
            if (condition!=null){
                for (Iterator iterator=condition.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case C_TRAVEL:
                            travelCondition=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(rotaryComponentStream.attributeValue(NAME));
            result.setId(rotaryComponentStream.attributeValue(COMPONENT_ID));
            //angleActual
            if (angleActual==null){
                Log.d(TAG, "parse: angleActual is null");
            }
            if (angleActual!=null){
                result.setAngle(angleActual.getStringValue());
            }
            //angularVelocityActual
            if (angularVelocityActual==null){
                Log.d(TAG, "parse: angularVelocityActual is null");
            }
            if (angularVelocityActual!=null){
                result.setAngularVelocity(angularVelocityActual.getStringValue());
            }
            //torqueActual
            if (torqueActual==null){
                Log.d(TAG, "parse: torqueActual is null");
            }
            if (torqueActual!=null){
                result.setLoad(torqueActual.getStringValue());
            }
            //travelCondition
            if (travelCondition==null){
                Log.d(TAG, "parse: travelCondition is null");
            }
            if (travelCondition!=null){
                result.setTravel(travelCondition.getName());
            }
        }
        return result;
    }
}
