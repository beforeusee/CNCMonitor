package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class AxesLinearXInfo extends AxesLinearInfo {
    //定义负载，为百分比
    private String load;

    private static final String SAMPLES="Samples";
    private static final String CONDITION="Condition";
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";
    //X
    private static final String X_POS="x_pos";
    private static final String X_FRT="x_frt";
    private static final String X_TORQ="x_torq";
    private static final String X_TRAVEL="x_travel";

    private static final String TAG = "AxesLinearXInfo";

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public static AxesLinearXInfo parse(Element linearComponentStream) {
        AxesLinearXInfo result=null;
        if (linearComponentStream==null){
            Log.d(TAG, "parse: linearComponentStream is null");
        }
        if (linearComponentStream!=null){
            result=new AxesLinearXInfo();
            Element samples=linearComponentStream.element(SAMPLES);
            Element condition=linearComponentStream.element(CONDITION);

            //Samples
            Element positionActual=null;
            Element axisFeedrateActual=null;
            Element torqueActual=null;
            //Condition
            Element travelCondition=null;
            //Samples类型
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for (Iterator iterator = samples.elementIterator(); iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case X_POS:
                            positionActual=element;
                            break;
                        case X_FRT:
                            axisFeedrateActual=element;
                            break;
                        case X_TORQ:
                            torqueActual=element;
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
                        case X_TRAVEL:
                            travelCondition=element;
                            break;
                        default:
                            break;
                    }
                }
            }


            result.setName(linearComponentStream.attributeValue(NAME));
            result.setId(linearComponentStream.attributeValue(COMPONENT_ID));
            //positionActual
            if (positionActual==null){
                Log.d(TAG, "parse: positionActual is null");
            }
            if (positionActual!=null){
                result.setPosition(positionActual.getStringValue());
            }
            //axisFeedrateActual
            if (axisFeedrateActual==null){
                Log.d(TAG, "parse: axisFeedrateActual is null");
            }
            if (axisFeedrateActual!=null){
                result.setAxisFeedRate(axisFeedrateActual.getStringValue());
            }
            //torqueActual
            if (torqueActual==null)
                Log.d(TAG, "parse: torqueActual is null");
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
