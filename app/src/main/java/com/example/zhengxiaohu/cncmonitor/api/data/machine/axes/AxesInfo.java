package com.example.zhengxiaohu.cncmonitor.api.data.machine.axes;

/**
 * Created by XiaoHu Zheng on 2017/5/20.
 * Email: 1050087728@qq.com
 */

abstract class AxesInfo {
    //name of axis
    private String name;
    //id of axis
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
