package com.example.zhengxiaohu.cncmonitor.devices;

import com.example.zhengxiaohu.cncmonitor.api.data.machine.DeviceMachineInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearXInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearYInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesLinearZInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotaryAInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotaryCInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotaryInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.axes.AxesRotarySInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerBaseInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerPathInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.systems.PneumaticInfo;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.systems.SystemsInfo;

/**
 * Created by XiaoHu Zheng on 2017/5/21.
 * Email: 1050087728@qq.com
 */

public class MachineTool {
    //uniqueId(uuid) of the machine tool device
    private String uniqueId;
    //description information of the machine tool
    private DeviceMachineInfo mDeviceMachineInfo;
    // base of controller information of the machine tool
    private ControllerBaseInfo mControllerBaseInfo;
    //path of controller information of the machine tool
    private ControllerPathInfo mControllerPathInfo;
    //Pneumatic system of the machine tool
    private PneumaticInfo mPneumaticInfo;

    //linear axis X information
    private AxesLinearXInfo mAxesLinearXInfo;
    //linear axis Y information
    private AxesLinearYInfo mAxesLinearYInfo;
    //linear axis Z information
    private AxesLinearZInfo mAxesLinearZInfo;
    //rotary axis A information
    private AxesRotaryAInfo mAxesRotaryAInfo;
    //rotary axis C information
    private AxesRotaryCInfo mAxesRotaryCInfo;
    //spindle information
    private AxesRotarySInfo mAxesRotarySInfo;

    public MachineTool(){
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public DeviceMachineInfo getDeviceMachineInfo() {
        return mDeviceMachineInfo;
    }

    public void setDeviceMachineInfo(DeviceMachineInfo deviceMachineInfo) {
        mDeviceMachineInfo = deviceMachineInfo;
    }

    public ControllerBaseInfo getControllerBaseInfo() {
        return mControllerBaseInfo;
    }

    public void setControllerBaseInfo(ControllerBaseInfo controllerBaseInfo) {
        mControllerBaseInfo = controllerBaseInfo;
    }

    public ControllerPathInfo getControllerPathInfo() {
        return mControllerPathInfo;
    }

    public void setControllerPathInfo(ControllerPathInfo controllerPathInfo) {
        mControllerPathInfo = controllerPathInfo;
    }

    public PneumaticInfo getPneumaticInfo() {
        return mPneumaticInfo;
    }

    public void setPneumaticInfo(PneumaticInfo pneumaticInfo) {
        mPneumaticInfo = pneumaticInfo;
    }

    public AxesLinearXInfo getAxesLinearXInfo() {
        return mAxesLinearXInfo;
    }

    public void setAxesLinearXInfo(AxesLinearXInfo axesLinearXInfo) {
        mAxesLinearXInfo = axesLinearXInfo;
    }

    public AxesLinearYInfo getAxesLinearYInfo() {
        return mAxesLinearYInfo;
    }

    public void setAxesLinearYInfo(AxesLinearYInfo axesLinearYInfo) {
        mAxesLinearYInfo = axesLinearYInfo;
    }

    public AxesLinearZInfo getAxesLinearZInfo() {
        return mAxesLinearZInfo;
    }

    public void setAxesLinearZInfo(AxesLinearZInfo axesLinearZInfo) {
        mAxesLinearZInfo = axesLinearZInfo;
    }

    public AxesRotaryAInfo getAxesRotaryAInfo() {
        return mAxesRotaryAInfo;
    }

    public void setAxesRotaryAInfo(AxesRotaryAInfo axesRotaryAInfo) {
        mAxesRotaryAInfo = axesRotaryAInfo;
    }

    public AxesRotaryCInfo getAxesRotaryCInfo() {
        return mAxesRotaryCInfo;
    }

    public void setAxesRotaryCInfo(AxesRotaryCInfo axesRotaryCInfo) {
        mAxesRotaryCInfo = axesRotaryCInfo;
    }

    public AxesRotarySInfo getAxesRotarySInfo() {
        return mAxesRotarySInfo;
    }

    public void setAxesRotarySInfo(AxesRotarySInfo axesRotarySInfo) {
        mAxesRotarySInfo = axesRotarySInfo;
    }
}
