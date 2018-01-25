package com.example.zhengxiaohu.cncmonitor.api.data.machine.controller;


/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public abstract class ControllerInfo {

    //name of the controller component
    private String name;
    //id of the controller component
    private String id;

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
}
