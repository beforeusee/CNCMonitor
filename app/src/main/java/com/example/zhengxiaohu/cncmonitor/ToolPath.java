package com.example.zhengxiaohu.cncmonitor;

import android.content.Context;
import android.opengl.GLES20;

import com.example.zhengxiaohu.cncmonitor.util.MatrixState;
import com.example.zhengxiaohu.cncmonitor.util.ShaderHelper;
import com.example.zhengxiaohu.cncmonitor.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/6/4.
 * Email: 1050087728@qq.com
 */

public class ToolPath {

    private FloatBuffer vertexBuffer;
    private Context mContext;
    //float类型的字节数
    private static final int BYTES_PER_FLOAT=4;
    //共有6个顶点坐标
//    public static final int POSITION_COMPONENT_COUNT=6;
    //数组中每个顶点的坐标数
    private static final int COORDS_PER_VERTEX=3;

    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private static final String U_MATRIX = "u_Matrix";
    private int uMatrixLocation;
    private int uColorLocation;
    private int aPositionLocation;
    private int program;


    //定义测试点集
    private float testVertices[]={
            //前面
            0,0,1.0f,
            1.0f,1.0f,1.0f,
            -1.0f,1.0f,1.0f,

            //后面
            0,0,-1.0f,
            1.0f,1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,

    };
    private float mVertices[];
    private int POSITION_COMPONENT_COUNT=1;

    public ToolPath(Context context){
        mContext=context;
/*
        vertexBuffer= ByteBuffer.allocateDirect(testVertices.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        //把坐标加入FloatBuffer中
        vertexBuffer.put(testVertices);
        //设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);

        getProgram();
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        //传入顶点数据数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);*/
    }


    private void updateVertices(float vertices[]){
        mVertices=vertices;
        if (mVertices==null){
            mVertices=new float[3];
            POSITION_COMPONENT_COUNT=mVertices.length/COORDS_PER_VERTEX;

            vertexBuffer= ByteBuffer.allocateDirect(mVertices.length*BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();

            //把坐标加入FloatBuffer中
            vertexBuffer.put(mVertices);
            //设置buffer，从第一个坐标开始读
            vertexBuffer.position(0);

        }
        if (mVertices!=null){
            POSITION_COMPONENT_COUNT=mVertices.length/COORDS_PER_VERTEX;

            vertexBuffer= ByteBuffer.allocateDirect(mVertices.length*BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();

            //把坐标加入FloatBuffer中
            vertexBuffer.put(mVertices);
            //设置buffer，从第一个坐标开始读
            vertexBuffer.position(0);
        }

        getProgram();
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        //传入顶点数据数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }


    //获取program
    private void getProgram(){
        //获取顶点着色器文本
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        //获取片段着色器文本
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
        //获取program的id
        program = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(program);
    }

    /**
     * 画图
     * @param vertices 传入的点集
     */
    public void draw(float vertices[]){
        //更新顶点数组
        updateVertices(vertices);
        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,0.0f);
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false, MatrixState.getFinalMatrix(),0);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP,0,POSITION_COMPONENT_COUNT);
    }
}
