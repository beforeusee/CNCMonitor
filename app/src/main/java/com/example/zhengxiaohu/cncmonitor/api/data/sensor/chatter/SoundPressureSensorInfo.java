package com.example.zhengxiaohu.cncmonitor.api.data.sensor.chatter;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/26.
 * Email: 1050087728@qq.com
 */

public class SoundPressureSensorInfo {
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    private static final String CONDITION ="Condition";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String SOUND_CHATTER="sound_chatter";

    //name of the soundPressureSensor
    private String name;
    //id of the soundPressureSensor
    private String id;
    //type of DATA_RANGE,sound chatter
    private String soundChatter;
    //the value of the soundChatter
    private String soundChatterValue;

    private static final String TAG = "SoundPressureSensorInfo";

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

    public String getSoundChatter() {
        return soundChatter;
    }

    public void setSoundChatter(String soundChatter) {
        this.soundChatter = soundChatter;
    }

    public String getSoundChatterValue() {
        return soundChatterValue;
    }

    public void setSoundChatterValue(String soundChatterValue) {
        this.soundChatterValue = soundChatterValue;
    }

    public static SoundPressureSensorInfo parse(Element sensorComponentStream){
        SoundPressureSensorInfo result=null;
        if (sensorComponentStream==null){
            Log.d(TAG, "parse: sensorComponentStream is null");
        }
        if (sensorComponentStream!=null){
            result=new SoundPressureSensorInfo();
            Element condition=sensorComponentStream.element(CONDITION);

            //Condition
            Element soundChatter=null;

            //Condition
            if (condition==null){
                Log.d(TAG, "parse: condition is null");
            }
            if (condition!=null){
                for (Iterator iterator=condition.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case SOUND_CHATTER:
                            soundChatter=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(sensorComponentStream.attributeValue(NAME));
            result.setId(sensorComponentStream.attributeValue(COMPONENT_ID));
            //soundChatter
            if (soundChatter==null){
                Log.d(TAG, "parse: soundChatter is null");
            }
            if (soundChatter!=null){
                result.setSoundChatter(soundChatter.getName());
                result.setSoundChatterValue(soundChatter.getStringValue());
            }
        }
        return result;
    }
}
