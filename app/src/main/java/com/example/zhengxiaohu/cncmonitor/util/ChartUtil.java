package com.example.zhengxiaohu.cncmonitor.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;
import java.util.logging.XMLFormatter;

/**
 * Created by XiaoHu Zheng on 2017/6/1.
 * Email: 1050087728@qq.com
 */

public class ChartUtil {
    //X轴点的数目
    private static final int X_MAX_POINT=25;

    private GraphicalView mGraphicalView;
    //数据集容器
    private XYMultipleSeriesDataset mXYMultipleSeriesDataset;
    //渲染器容器
    private XYMultipleSeriesRenderer mXYMultipleSeriesRenderer;
    //单条曲线数据集
    private XYSeries mSeries;
    //单条曲线渲染器
    private XYSeriesRenderer mSeriesRenderer;
    private int count=0;
    private int XAxisMin=0;
    private Context mContext;

    public ChartUtil(Context context){
        mContext=context;
    }

    /**
     * 获取图标
     * @return mGraphicalView
     */
    public GraphicalView getGraphicalView(){
        mGraphicalView= ChartFactory.getLineChartView(mContext,
                mXYMultipleSeriesDataset,mXYMultipleSeriesRenderer);
        return mGraphicalView;
    }

    /**
     * 获取数据集，及XY坐标的集合
     * @param curveTitle 曲线名称
     */
    public void setXYMultipleSeriesDataset(String curveTitle){
        mXYMultipleSeriesDataset=new XYMultipleSeriesDataset();
        mSeries=new XYSeries(curveTitle);
        mXYMultipleSeriesDataset.addSeries(mSeries);
    }

    /**
     * 设置渲染器

     * @param chartTitle 图标的标题
     * @param xTitle X轴标题
     * @param yTitle Y轴标题
     * @param axeColor 坐标轴颜色
     * @param labelColor 标题颜色
     * @param curveColor 曲线颜色
     * @param gridColor 网格颜色
     */
    public void setXYMultipleSeriesRenderer(String chartTitle,
                                            String xTitle,String yTitle,int axeColor,
                                            int labelColor,int curveColor,int gridColor){
        mXYMultipleSeriesRenderer=new XYMultipleSeriesRenderer();
        //设置图标名称和大小
        if (chartTitle!=null){
            mXYMultipleSeriesRenderer.setChartTitle(chartTitle);
            mXYMultipleSeriesRenderer.setChartTitleTextSize(35);
        }

        //设置X轴名称
        mXYMultipleSeriesRenderer.setXTitle(xTitle);
        //设置Y轴名称
        mXYMultipleSeriesRenderer.setYTitle(yTitle);

        mXYMultipleSeriesRenderer.setLabelsColor(labelColor);
        //设置XY坐标轴label的的颜色
        mXYMultipleSeriesRenderer.setXLabelsColor(Color.BLACK);
        mXYMultipleSeriesRenderer.setYLabelsColor(0,Color.BLACK);
        mXYMultipleSeriesRenderer.setLabelsColor(Color.BLACK);

        //设置XY的labels
        mXYMultipleSeriesRenderer.setXLabels(15);
        mXYMultipleSeriesRenderer.setYLabels(15);
        //设置对齐方式
        mXYMultipleSeriesRenderer.setXLabelsAlign(Paint.Align.RIGHT);
        mXYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        //设置轴名称的大小
        mXYMultipleSeriesRenderer.setAxisTitleTextSize(30);
        //设置label的尺寸大小
        mXYMultipleSeriesRenderer.setLabelsTextSize(30);
        //设置图例的尺寸大小
        mXYMultipleSeriesRenderer.setLegendTextSize(30);
        //曲线描点的尺寸大小
        mXYMultipleSeriesRenderer.setPointSize(2.5f);
        //
        mXYMultipleSeriesRenderer.setFitLegend(true);
        //设置边距,顺序是上,左,下,右即逆时针方向
        mXYMultipleSeriesRenderer.setMargins(new int[]{50,100,50,15});

        //设置网格是否显示
        mXYMultipleSeriesRenderer.setShowGrid(true);
        //设置是否允许缩放
        mXYMultipleSeriesRenderer.setZoomEnabled(true,true);
        //设置坐标轴颜色
        mXYMultipleSeriesRenderer.setAxesColor(axeColor);
        //设置网格颜色
        mXYMultipleSeriesRenderer.setGridColor(gridColor);
        //设置背景色
        mXYMultipleSeriesRenderer.setBackgroundColor(Color.WHITE);
        //设置允许设置背景色
        mXYMultipleSeriesRenderer.setApplyBackgroundColor(true);
        //设置边距背景色,默认为黑色,此处设置为白色
        mXYMultipleSeriesRenderer.setMarginsColor(Color.WHITE);
        //实例化mSeriesRenderer
        mSeriesRenderer=new XYSeriesRenderer();
        //设置曲线颜色
        mSeriesRenderer.setColor(curveColor);
        //设置描点风格，可以为圆点,方形点等
        mSeriesRenderer.setPointStyle(PointStyle.CIRCLE);
        //将此渲染器添加到渲染器容器中
        mXYMultipleSeriesRenderer.addSeriesRenderer(mSeriesRenderer);
    }

    /**
     * 根据新加的数据，更新曲线，只能运行在主线程
     * @param x 新加点的x坐标
     * @param y 新加点的y坐标
     */
    public void updateChart(double x,double y){
        mSeries.add(x,y);
        count++;
        //设置X轴向左平移的动态效果
        if (count< X_MAX_POINT){
            mXYMultipleSeriesRenderer.setXAxisMin(XAxisMin);
            mXYMultipleSeriesRenderer.setXAxisMax(XAxisMin+X_MAX_POINT);
        }else {
            mXYMultipleSeriesRenderer.setXAxisMin(XAxisMin+mSeries.getItemCount()-X_MAX_POINT);
            mXYMultipleSeriesRenderer.setXAxisMax(XAxisMin+mSeries.getItemCount());
        }
        //设置Y轴自适应大小效果
        if (count<X_MAX_POINT){
            //获取y坐标最大值
            double maxY=mSeries.getMaxY();
            mXYMultipleSeriesRenderer.setYAxisMin(0);
            mXYMultipleSeriesRenderer.setYAxisMax(4*maxY/3);
        }else {
            //x的坐标从索引(series.getItemCount() - MAX_POINT)开始，至索引series.getItemCount()结束，找出该区间内
            //的Y坐标最大的值,然后乘以4/3作为Y轴的最大值
            double maxY=0;
            for (int i=0;i<X_MAX_POINT;i++){
                double addY=mSeries.getY(mSeries.getItemCount()-X_MAX_POINT+i);
                maxY=getMaxY(maxY,addY);
            }
            mXYMultipleSeriesRenderer.setYAxisMin(0);
            mXYMultipleSeriesRenderer.setYAxisMax(4*maxY/3);
        }
        //刷新
        mGraphicalView.invalidate();
    }

    public void initChartCoordinate(double x){
        XAxisMin= (int) x;
    }
    /**
     * 获取两个数中较大的值
     * @param maxY 比较的参数1
     * @param y 比较的参数2
     * @return 二者中较大的数
     */
    private double getMaxY(double maxY, double y) {
        return (maxY>y)?maxY:y;
    }

    /**
     * 添加新的数据,多组,更新曲线,只能运行在主线程
     * @param xList
     * @param yList
     */
    public void updateChart(List<Double> xList,List<Double> yList){
        for (int i=0;i<xList.size();i++){
            mSeries.add(xList.get(i),yList.get(i));
        }
        //刷新
        mGraphicalView.invalidate();
    }
}
