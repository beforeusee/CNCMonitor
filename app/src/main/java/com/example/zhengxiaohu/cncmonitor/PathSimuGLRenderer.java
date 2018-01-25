package com.example.zhengxiaohu.cncmonitor;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.example.zhengxiaohu.cncmonitor.util.MatrixState;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by XiaoHu Zheng on 2017/5/19.
 * Email: 1050087728@qq.com
 */

public class PathSimuGLRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private static final String TAG = "PathSimuGLRenderer";

    //定义测试点集
    private final float testVertices[]={
            //前面
            0,0,1.0f,
            1.0f,1.0f,1.0f,
            -1.0f,1.0f,1.0f,

            //后面
            0,0,-1.0f,
            1.0f,1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
    };
    //定义ToolPath对象
    ToolPath mToolPath;
    //顶点数据集,即为刀轨的刀尖点的坐标点集
    private float vertices[];

    public PathSimuGLRenderer(Context context){
        mContext=context;
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    /**
     * 仅调用一次，创建Surface时调用，用于设置view的OpenGL ES环境
     * @param gl 参数
     * @param config 配置
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated: execute");
        //设置背景色(r,g,b,a)为淡蓝色#CAE1FF(0.79f,0.88f,1.0f,0.5f)
        GLES20.glClearColor(0.79f,0.88f,1.0f,0.5f);
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //打开背面裁剪
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        //创建ToolPath实例
        mToolPath=new ToolPath(mContext);
    }

    /**
     * Surface改变时
     * @param gl GL10参数
     * @param width 屏幕宽度
     * @param height 屏幕高度
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged: execute");
        //设置视口尺寸，即告诉opengl可以用来渲染的surface大小
        //GLES20.glViewport(0,0,width,height);

        float ratio=(float) width/height;
        //调用此方法计算产生透视投影矩阵
        MatrixState.setProjectFrustum(-ratio,ratio,-1,1,20,100);
        //调用此方法产生摄像机9参数位置矩阵
        MatrixState.setCamera(-16f,8f,25,0f,0f,0f,0f,1.0f,0.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "onDrawFrame: execute");
        //重绘背景色
        //GLES20.glClearColor(mRed,mGreen,mBlue,0.5f);
        //清楚深度缓冲与颜色缓冲
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        mToolPath.draw(vertices);
    }
}
