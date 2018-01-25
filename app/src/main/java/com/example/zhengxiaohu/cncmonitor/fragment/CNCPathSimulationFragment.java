package com.example.zhengxiaohu.cncmonitor.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.PathSimuGLRenderer;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.api.data.machine.controller.ControllerPathInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 */

public class CNCPathSimulationFragment extends Fragment {

    private GLSurfaceView mGLSurfaceView;
    private PathSimuGLRenderer mPathSimuGLRenderer;
    ArrayList<Float> vertices=new ArrayList<>();

    private static final String TAG = "CNCPathSimulationFragme";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cnc_path_simulation,container,false);
        initGLSurfaceView(view);
        return view;
    }

    /**
     * 初始化GLSurfaceView
     * @param view 布局的id
     */
    private void initGLSurfaceView(View view) {

        mGLSurfaceView = (GLSurfaceView) view.findViewById(R.id.path_gl_surface_view);
        mPathSimuGLRenderer=new PathSimuGLRenderer(getContext());
        if (hasEGL2()){
            //设置版本号
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setPreserveEGLContextOnPause(true);
            mGLSurfaceView.setRenderer(mPathSimuGLRenderer);
            //设置渲染模式为主动渲染
            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }else {
            Log.d(TAG, "initGLSurfaceView: "+"OpenGL ES 2.0 is not supported");
        }
    }

    /**
     * 判断手机硬件是否支持OpenGL ES 2.0
     * @return 返回是否支持OpenGL ES 2.0
     */
    private boolean hasEGL2(){
        ActivityManager activityManager= (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo=activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion>=0x20000;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    /**
     * 注册EventBus
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG, "onStart: execute");
    }

    /**
     * 注销EventBus
     */
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        Log.d(TAG, "onStop: execute");
    }

    /**
     * onDeviceStatusMessageEvent,EventBus的注册者,获取发送的消息并获取消息对象带的数据
     * @param event DeviceStatusMessageEvent的对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceStatusMessageEvent(DeviceStatusMessageEvent event){
        ControllerPathInfo controllerPathInfo=event.mDeviceStatus.getMachineTool().getControllerPathInfo();
        setData(controllerPathInfo);
    }

    /**
     * 设置数据
     * @param controllerPathInfo controllerPathInfo信息
     */
    private void setData(ControllerPathInfo controllerPathInfo) {
        String pathPosition=controllerPathInfo.getPathPosition();
        //解析字符串并加入到数组中
        String[] array=pathPosition.split(" ");
        for (String anArray : array) {
            Log.d(TAG, "setData: parse result= " + anArray);
            vertices.add(Float.parseFloat(anArray)/75);
        }

        float v[];
        v=new float[vertices.size()];
        for (int i=0;i<vertices.size();i++){
            v[i]=vertices.get(i);
        }
        mPathSimuGLRenderer.setVertices(v);
    }
}
