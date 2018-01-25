package com.example.zhengxiaohu.cncmonitor.api.data.machine.systems;

/**
 * Created by XiaoHu Zheng on 2017/5/20.
 * Email: 1050087728@qq.com
 */

public abstract class SystemsInfo {
   //name of the sub system
    private String name;
    //id of the sub system
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
