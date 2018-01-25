package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class AxesRotarySInfo extends AxesRotaryInfo {
    //实际主轴转速,spindle actual speed,revolution per minute
    private String rotaryVelocityActual;
    //编程主轴转速,spindle actual speed,revolution per minute
    private String rotaryVelocityProgrammed;
    //spindle override
    private String axisSFeedrateOverride;
    //temperature of spindle
    private String temp;
    //temperature condition of spindle
    private String tempCondition;

    private static final String SAMPLES="Samples";
    private static final String CONDITION="Condition";
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";

    //SPINDLE
        private static final String S_RPM_ACTUAL="s_rpm_actual";
        private static final String S_RPM_PROGRAMMED="s_rpm_programmed";
        private static final String S_TEMP="s_temp";
        public static final String S_TEMP_CONDITION="s_temp_condition";
        private static final String S_SPEED_OVERRIDE="s_speed_override";

    private static final String TAG = "AxesRotarySInfo";


    public String getRotaryVelocityActual() {
        return rotaryVelocityActual;
    }

    public void setRotaryVelocityActual(String rotaryVelocityActual) {
        this.rotaryVelocityActual = rotaryVelocityActual;
    }

    public String getRotaryVelocityProgrammed() {
        return rotaryVelocityProgrammed;
    }

    public void setRotaryVelocityProgrammed(String rotaryVelocityProgrammed) {
        this.rotaryVelocityProgrammed = rotaryVelocityProgrammed;
    }

    public String getAxisSFeedrateOverride() {
        return axisSFeedrateOverride;
    }

    public void setAxisSFeedrateOverride(String axisSFeedrateOverride) {
        this.axisSFeedrateOverride = axisSFeedrateOverride;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempCondition() {
        return tempCondition;
    }

    public void setTempCondition(String tempCondition) {
        this.tempCondition = tempCondition;
    }

    public static AxesRotarySInfo parse(Element rotaryComponentStream){
        AxesRotarySInfo result=null;
        if (rotaryComponentStream==null){
            Log.d(TAG, "parse: rotaryComponentStream is null");
        }
        if (rotaryComponentStream!=null){
            result= new AxesRotarySInfo();
            Element samples=rotaryComponentStream.element(SAMPLES);
            Element condition=rotaryComponentStream.element(CONDITION);
            Element events=rotaryComponentStream.element(EVENTS);

            //Samples
            Element rotaryVelocityActual=null;
            Element rotaryVelocityProgrammed=null;
            Element temperature=null;
            //Events
            Element axisFeedrateOverride=null;
            //Condition
            Element tempCondition=null;
            //Samples类型
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator = samples.elementIterator(); iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case S_RPM_ACTUAL:
                            rotaryVelocityActual=element;
                            break;
                        case S_RPM_PROGRAMMED:
                            rotaryVelocityProgrammed=element;
                            break;
                        case S_TEMP:
                            temperature=element;
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
                        case S_TEMP_CONDITION:
                            tempCondition=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            //Events类型
            if (events==null){
                Log.d(TAG, "parse: events is null");
            }
            if (events!=null){
                for (Iterator iterator=events.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case S_SPEED_OVERRIDE:
                            axisFeedrateOverride=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(rotaryComponentStream.attributeValue(NAME));
            result.setId(rotaryComponentStream.attributeValue(COMPONENT_ID));
            //rotaryVelocityActual
            if (rotaryVelocityActual==null){
                Log.d(TAG, "parse: rotaryVelocityActual is null");
            }
            if (rotaryVelocityActual!=null){
                result.setRotaryVelocityActual(rotaryVelocityActual.getStringValue());
            }
            //rotaryVelocityProgrammed
            if (rotaryVelocityProgrammed==null){
                Log.d(TAG, "parse: rotaryVelocityProgrammed is null");
            }
            if (rotaryVelocityProgrammed!=null){
                result.setRotaryVelocityProgrammed(rotaryVelocityProgrammed.getStringValue());
            }
            //temperature
            if (temperature==null){
                Log.d(TAG, "parse: temperature is null");
            }
            if (temperature!=null){
                result.setTemp(temperature.getStringValue());
            }
            //tempCondition
            if (tempCondition==null){
                Log.d(TAG, "parse: tempCondition is null");
            }
            if (tempCondition!=null){
                result.setTempCondition(tempCondition.getStringValue());
            }
            //axisFeedrateOverride
            if (axisFeedrateOverride==null){
                Log.d(TAG, "parse: axisFeedrateOverride is null");
            }
            if (axisFeedrateOverride!=null){
                result.setAxisSFeedrateOverride(axisFeedrateOverride.getStringValue());
            }
        }
        return result;
    }
}
