package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public abstract class AxesRotaryInfo extends AxesInfo{
    //position of rotary axis
    private String angle;
    //angularVelocity of rotary axis
    private String angularVelocity;

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(String angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
