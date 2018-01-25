package com.example.zhengxiaohu.cncmonitor.api.data.machine.systems;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class PneumaticInfo extends SystemsInfo {
    //the condition of pneumatic
    private String pneumaticCondition;
    //the value of pneumatic condition
    private String pneumaticConditionValue;

    private static final String CONDITION="Condition";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String NAME="name";
    private static final String PNEUPRESS="pneupress";
    private static final String COMPONENT_ID="componentId";

    private static final String TAG = "PneumaticInfo";

    public String getPneumaticCondition() {
        return pneumaticCondition;
    }

    public void setPneumaticCondition(String pneumaticCondition) {
        this.pneumaticCondition = pneumaticCondition;
    }

    public String getPneumaticConditionValue() {
        return pneumaticConditionValue;
    }

    public void setPneumaticConditionValue(String pneumaticConditionValue) {
        this.pneumaticConditionValue = pneumaticConditionValue;
    }

    /**
     * 解析SystemInfo方法
     * @param pneumaticComponentStream SystemsComponentStream的方法
     * @return 返回解析结果,SystemInfo实例
     */
    public static PneumaticInfo parse(Element pneumaticComponentStream){
        PneumaticInfo result=null;
        if (pneumaticComponentStream==null){
            Log.d(TAG, "parse: pneumaticComponentStream is null");
        }
        if (pneumaticComponentStream!=null){
            result=new PneumaticInfo();
            Element condition=pneumaticComponentStream.element(CONDITION);

            Element pneumaticCondition=null;

            //condition
            if (condition==null){
                Log.d(TAG, "parse: condition is null");
            }
            if (condition!=null){
                for (Iterator iterator = condition.elementIterator(); iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case PNEUPRESS:
                            pneumaticCondition=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(pneumaticComponentStream.attributeValue(NAME));
            result.setId(pneumaticComponentStream.attributeValue(COMPONENT_ID));
            //pneumaticCondition
            if (pneumaticCondition==null){
                Log.d(TAG, "parse: pneumaticCondition is null");
            }
            if (pneumaticCondition!=null){
                result.pneumaticCondition=pneumaticCondition.getName();
                result.pneumaticConditionValue=pneumaticCondition.getStringValue();
            }
        }
        return result;
    }
}
