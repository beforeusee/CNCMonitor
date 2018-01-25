package com.example.zhengxiaohu.cncmonitor.api.http;

/**
 * Created by XiaoHu Zheng on 2017/5/20.
 * Email: 1050087728@qq.com
 *
 * data for http post
 */

public class PostData {
    public String id;
    public String value;

    public PostData(String id,String value){
        this.id=id;
        this.value=value;
    }
}
