package com.example.zhengxiaohu.cncmonitor;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by XiaoHu Zheng on 2017/6/3.
 * Email: 1050087728@qq.com
 */

public class GLSurfaceViewCubeTest {
    private IntBuffer mVertexBuffer;
    private IntBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;

    public GLSurfaceViewCubeTest(){
        int one=0x10000;

        //每一个顶点都是由x/y/z三个坐标表示的。对于一个立方体有八个顶点，每个顶点的位置是从下到上，
        // 在每一层里由里到外逆时针方向标识
        int vertices[]={
                -one,-one,-one,
                one,-one,-one,
                one,one,-one,
                -one,one,-one,
                -one,-one,one,
                one,-one,one,
                one,one,one,
                -one,one,one
        };
        //每个顶点的颜色由四个数字表示：RED/GREEN/BLUE/ALPHA,最后一个透明度是可选的
        int colors[]={
                0,0,0,one,
                one,0,0,one,
                one,one,0,one,
                0,one,0,one,
                0,0,one,one,
                one,0,one,one,
                one,one,one,one,
                0,one,one,one
        };

        //三角面的定义，每个数字对应于正方形中特定的点。比如0代表正方体原点
        // 那么0,4,5对应的就是立方体侧面的一个三角,每个面都有两个三角组成，
        // 整个立方体有六个面，这样就有十二个三角面了
        //注意：点的排列顺序对于显示效果有很大的影响，这是因为调用了GLES20.glFrontFace(GLES20.GL_CW)
        //这样我们需要以顺时针的顺序来指定可视的三角面
        byte indices[]={
                0,4,5,0,5,1,
                1,5,6,1,6,2,
                2,6,7,2,7,3,
                3,7,4,3,4,0,
                4,7,6,4,6,5,
                3,0,1,3,1,2
        };

        //使用ByteBuffer做缓冲处理，必须通过ByteBuffer.allocateDirect()方法来实例化ByteBuffer对象
        //这些buffer必须放到本地堆栈中，以使垃圾回收器不能移除他们，这些缓冲将作为参数传入到gl.Pointer()方法
        //中，对于不同原数据类型的Buffers需要将byte设置成对应的顺序，int类型的是每个点是四个字节
        ByteBuffer vByteBuffer=ByteBuffer.allocateDirect(vertices.length*4);
        vByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer=vByteBuffer.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        ByteBuffer cByteBuffer=ByteBuffer.allocateDirect(colors.length*4);
        cByteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer=cByteBuffer.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
        mIndexBuffer=ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    /**
     * 绘制方法
     * @param gl10 传入的GL10参数
     */
    public void draw(GL10 gl10){
        gl10.glFrontFace(GL10.GL_CW);
        gl10.glVertexPointer(3,GL10.GL_FIXED,0,mVertexBuffer);
        gl10.glColorPointer(4,GL10.GL_FIXED,0,mColorBuffer);
        gl10.glDrawElements(GL10.GL_TRIANGLES,36, GL10.GL_UNSIGNED_BYTE,mIndexBuffer);
    }
}
