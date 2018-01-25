package com.example.zhengxiaohu.cncmonitor.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.devices.EnergyConsumptionMonitorSensor;
import com.example.zhengxiaohu.cncmonitor.util.ChartUtil;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 */

public class EnergyConsumptionMonitorFragment extends Fragment {
    private static final String TAG = "EnergyConsumpMonitor";
    private static String CHART_TITLE="有功功率曲线";
    private static String CURVE_TITLE="有功功率";
    private static String Y_TITLE ="功率/W";
    private static String X_TITLE="时间/s";
    TextView tvDeviceEnergyConsumptionName;
    TextView tvDeviceEnergyConsumptionAvailability;
    TextView tvDeviceEnergyConsumptionUUID;
    TextView tvDeviceEnergyConsumptionID;
    TextView tvACurrent;
    TextView tvBCurrent;
    TextView tvCCurrent;
    TextView tvABVoltage;
    TextView tvActivePower;
    TextView tvEnergyConsumption;
    //有功功率图表容器
    LinearLayout activePowerViewLayout;
    private ChartUtil mChartUtil;
    double[] mPower=new double[]{474.812,473.266,474.031,480.031,1038.359,1167.031,616.016,611.594,586.125,582.766,584.219,610.578,649.359,647.328,647.703,653.844,654.192,649.828,657.172,
            609.375,483.234,474.844	,474.875,473.469,474.25};

    //图表
//    GraphicalView activePowerView;
    //单条曲线数据集,存放曲线的数据
    private XYSeries mXYSeries;
    //单条曲线渲染器,存放曲线的参数,比如线条颜色,描点大小等
    private XYSeriesRenderer mXYSeriesRenderer;
    //渲染器容器,此类初始化坐标系,网格,标题等,还用来存放多条曲线的渲染器
    private XYMultipleSeriesRenderer mXYMultipleSeriesRenderer;
    //数据集容器,在此类中存放多条曲线的数据集合
    private XYMultipleSeriesDataset mDataset;

    //x坐标起始值(表示时间)
    private int x=0;
    private int count=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_energy_consumption_monitor,container,false);
        initView(view);
        initChart();
        Log.d(TAG, "onCreateView: execute");
        return view;
    }

    /**
     * 初始化有功功率图表
     */
    private void initChart() {
        //初始化的顺序不能错，getGraphicalView必须在Dataset和Renderer初始化之后才能调用
        mChartUtil=new ChartUtil(getContext());
        mChartUtil.setXYMultipleSeriesDataset(CURVE_TITLE);
        mChartUtil.setXYMultipleSeriesRenderer(CHART_TITLE,X_TITLE, Y_TITLE, Color.BLACK,
                Color.RED,Color.RED,Color.BLACK);

        GraphicalView activePowerView= mChartUtil.getGraphicalView();

        //将图表添加到布局容器中
        activePowerViewLayout.addView(activePowerView,new ViewGroup.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Log.d(TAG, "initChart: activePowerViewLayout child Count= "+activePowerViewLayout.getChildCount());
        //初始化图表
        mChartUtil.initChartCoordinate(x);
    }

    /**
     * onDeviceStatusMessageEvent,EventBus的注册者,获取发送的消息并获取消息对象带的数据
     * @param event DeviceStatusMessageEvent的对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceStatusMessageEvent(DeviceStatusMessageEvent event){
        EnergyConsumptionMonitorSensor sensor=event.mDeviceStatus.getEnergyConsumptionMonitorSensor();
        setData(sensor);
    }

    /**
     * 设置能耗传感器的UI数据更新
     * @param sensor 能耗监测传感器
     */
    private void setData(EnergyConsumptionMonitorSensor sensor) {
        /*
        tvDeviceEnergyConsumptionName.setText(sensor.getDeviceEnergyConsumptionInfo().getName());
        tvDeviceEnergyConsumptionAvailability.setText(sensor.getDeviceEnergyConsumptionInfo().getAvailability());
        tvDeviceEnergyConsumptionUUID.setText(sensor.getUniqueId());
        tvDeviceEnergyConsumptionID.setText(sensor.getDeviceEnergyConsumptionInfo().getId());
        tvACurrent.setText(sensor.getAPhaseCurrentSensorInfo().getCurrent());
        tvBCurrent.setText(sensor.getBPhaseCurrentSensorInfo().getCurrent());
        tvCCurrent.setText(sensor.getCPhaseCurrentSensorInfo().getCurrent());
        tvABVoltage.setText(sensor.getABPhaseVoltageSensorInfo().getVoltage());
        tvActivePower.setText(sensor.getDeviceEnergyConsumptionInfo().getActivePower());
        tvEnergyConsumption.setText(sensor.getDeviceEnergyConsumptionInfo().getEnergyConsumption());

        mChartUtil.updateChart(x,Double.valueOf(sensor.getDeviceEnergyConsumptionInfo().getActivePower()));
        x++;
        */
        test();
    }

    private  void test(){
        //FOR TEST
        tvDeviceEnergyConsumptionName.setText("PowerSensor");
        tvDeviceEnergyConsumptionAvailability.setText("AVAILABLE");
        tvDeviceEnergyConsumptionUUID.setText("Electric-Sensor-001");
        tvDeviceEnergyConsumptionID.setText("power_sensor");
        tvACurrent.setText("1.324");
        tvBCurrent.setText("0.879");
        tvCCurrent.setText("0.755");
        tvABVoltage.setText("397.115");
        double aPower=getActivePower();
        tvActivePower.setText(String.valueOf(aPower));
        tvEnergyConsumption.setText("243312");

        mChartUtil.updateChart(x,aPower);
        x++;
    }
    //for test
    private double getActivePower(){
        if (count==25){
            count=count-25;
        }
        return mPower[count++];
    }

    /**
     * 初始化EnergyConsumptionMonitorFragment的显示界面
     */
    private void initView(View view) {
        //初始化设备信息
        tvDeviceEnergyConsumptionName= (TextView) view.findViewById(R.id.tv_device_energy_consumption_name);
        tvDeviceEnergyConsumptionAvailability= (TextView) view.findViewById(R.id.tv_device_energy_consumption_availability);
        tvDeviceEnergyConsumptionUUID= (TextView) view.findViewById(R.id.tv_device_enery_consumption_uuid);
        tvDeviceEnergyConsumptionID= (TextView) view.findViewById(R.id.tv_device_enery_consumption_id);
        tvACurrent= (TextView) view.findViewById(R.id.tvACurrent);
        tvBCurrent= (TextView) view.findViewById(R.id.tvBCurrent);
        tvCCurrent= (TextView) view.findViewById(R.id.tvCCurrent);
        tvABVoltage= (TextView) view.findViewById(R.id.tvABVoltage);
        tvActivePower= (TextView) view.findViewById(R.id.tvActivePower);
        tvEnergyConsumption= (TextView) view.findViewById(R.id.tvEnergyCosumption);
        activePowerViewLayout= (LinearLayout) view.findViewById(R.id.activePowerViewLayout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: execute");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy: execute");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: execute");
    }

    /**
     * EnergyConsumptionMonitorFragment注册EventBus
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: execute");
    }

    /**
     * EnergyConsumptionMonitorFragment注销EventBus
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: execute");
    }

}
