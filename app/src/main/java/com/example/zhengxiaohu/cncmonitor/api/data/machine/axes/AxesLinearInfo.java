package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public abstract class AxesLinearInfo extends AxesInfo {
    //position of linear axis
    private String position;
    //axis feedRate of linear axis
    private String axisFeedRate;
    //travel condition of linear axis
    private String travel;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAxisFeedRate() {
        return axisFeedRate;
    }

    public void setAxisFeedRate(String axisFeedRate) {
        this.axisFeedRate = axisFeedRate;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }
}
