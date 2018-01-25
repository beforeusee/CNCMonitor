package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class AxesLinearYInfo extends AxesLinearInfo {
    //负载
    private String load;

    private static final String SAMPLES="Samples";
    private static final String CONDITION="Condition";
    private static final String EVENTS="Events";
    private static final String DATA_ITEM_ID="dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";

    //Y
    private static final String Y_POS="y_pos";
    private static final String Y_FRT="y_frt";
    private static final String Y_TORQ="y_torq";
    private static final String Y_TRAVEL="y_travel";

    private static final String TAG = "AxesLinearYInfo";

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    /**
     * 解析linearComponentStream
     * @param linearComponentStream LinearComponent的Stream
     * @return 返回AxesLinearYInfo的实例
     */
    public static AxesLinearYInfo parse(Element linearComponentStream){
        AxesLinearYInfo result=null;
        if (linearComponentStream==null){
            Log.d(TAG, "parse: linearComponentStream is null");
        }
        if (linearComponentStream!=null){
            result=new AxesLinearYInfo();
            Element samples=linearComponentStream.element(SAMPLES);
            Element condition=linearComponentStream.element(CONDITION);

            Element positionActual=null;
            Element axisFeedrateActual=null;
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
                        case Y_POS:
                            positionActual=element;
                            break;
                        case Y_FRT:
                            axisFeedrateActual=element;
                            break;
                        case Y_TORQ:
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
                        case Y_TRAVEL:
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
