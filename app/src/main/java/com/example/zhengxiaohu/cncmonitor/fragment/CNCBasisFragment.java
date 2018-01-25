package com.example.zhengxiaohu.cncmonitor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.devices.MachineTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 */

public class CNCBasisFragment extends Fragment {

    private static final String TAG = "CNCBasisFragment";
    //定义UI各控件
    //设备信息
    TextView tvDeviceMachineName;
    TextView tvDeviceMachineAvailability;
    TextView tvDeviceMachineUUID;
    TextView tvDeviceMachineID;
    //控制器
    TextView tvControllerType;
    TextView tvControllerCommunication;
    TextView tvControllerMode;
    TextView tvControllerExec;
    TextView tvControllerEmergencyStop;
    TextView tvControllerTime;
    //各轴位置
    TextView tvXPos;
    TextView tvYPos;
    TextView tvZPos;
    TextView tvAPos;
    TextView tvCPos;
    //各轴进给速度
    TextView tvXFeedrate;
    TextView tvYFeedrate;
    TextView tvZFeedrate;
    TextView tvAFeedrate;
    TextView tvCFeedrate;
    //各轴负载
    TextView tvXLoad;
    TextView tvYLoad;
    TextView tvZLoad;
    TextView tvALoad;
    TextView tvCLoad;
    //主轴的编程值,实际值和倍率
    TextView tvSpindleRotaryVelocityProgrammed;
    TextView tvSpindleRotaryVelocityActual;
    TextView tvSpindleFeedrateOverride;
    //进给速度的编程值，实际值和倍率
    TextView tvPathFeedrateProgrammed;
    TextView tvPathFeedrateActual;
    TextView tvPathFeedrateOverride;
    //生产状态
    //程序名
    TextView tvProgramName;
    //程序块名称
    TextView tvBlock;
    //总行数
    TextView tvLineTotal;
    //当前行
    TextView tvLineCurrent;
    //刀具刀号
    TextView tvToolNumber;
    //加工产品数量
    TextView tvPressure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cnc_basis,container,false);
        initView(view);
        Log.d(TAG, "onCreateView: execute");
        return view;
    }

    private void initView(View view) {
        //初始化设备信息
        tvDeviceMachineName= (TextView) view.findViewById(R.id.tv_device_machine_name);
        tvDeviceMachineAvailability= (TextView) view.findViewById(R.id.tv_device_machine_availability);
        tvDeviceMachineUUID= (TextView) view.findViewById(R.id.tv_device_machine_uuid);
        tvDeviceMachineID= (TextView) view.findViewById(R.id.tv_device_machine_id);
        //初始化控制器
        tvControllerType= (TextView) view.findViewById(R.id.tv_controller_type);
        tvControllerCommunication= (TextView) view.findViewById(R.id.tv_controller_communication);
        tvControllerMode= (TextView) view.findViewById(R.id.tv_controller_mode);
        tvControllerExec= (TextView) view.findViewById(R.id.tv_controller_exec);
        tvControllerEmergencyStop= (TextView) view.findViewById(R.id.tv_controller_emergency_stop);
        tvControllerTime= (TextView) view.findViewById(R.id.tv_controller_time);
        //初始化轴信息
        //轴位置
        tvXPos= (TextView) view.findViewById(R.id.tv_x_position);
        tvYPos= (TextView) view.findViewById(R.id.tv_y_position);
        tvZPos= (TextView) view.findViewById(R.id.tv_z_position);
        tvAPos= (TextView) view.findViewById(R.id.tv_a_position);
        tvCPos= (TextView) view.findViewById(R.id.tv_c_position);
        //轴速度
        tvXFeedrate= (TextView) view.findViewById(R.id.tv_x_feedrate);
        tvYFeedrate= (TextView) view.findViewById(R.id.tv_y_feedrate);
        tvZFeedrate= (TextView) view.findViewById(R.id.tv_z_feedrate);
        tvAFeedrate= (TextView) view.findViewById(R.id.tv_a_feedrate);
        tvCFeedrate= (TextView) view.findViewById(R.id.tv_c_feedrate);
        //轴负载
        tvXLoad= (TextView) view.findViewById(R.id.tv_x_load);
        tvYLoad= (TextView) view.findViewById(R.id.tv_y_load);
        tvZLoad= (TextView) view.findViewById(R.id.tv_z_load);
        tvALoad= (TextView) view.findViewById(R.id.tv_a_load);
        tvCLoad= (TextView) view.findViewById(R.id.tv_c_load);
        //初始化主轴
        tvSpindleRotaryVelocityProgrammed= (TextView) view.findViewById(R.id.tv_spindle_rotary_velocity_programmed);
        tvSpindleRotaryVelocityActual= (TextView) view.findViewById(R.id.tv_spindle_rotary_velocity_actual);
        tvSpindleFeedrateOverride= (TextView) view.findViewById(R.id.tv_spindle_feedrate_override);
        //初始化进给
        tvPathFeedrateProgrammed= (TextView) view.findViewById(R.id.tv_path_feedrate_programmed);
        tvPathFeedrateActual= (TextView) view.findViewById(R.id.tv_path_feedrate_actual);
        tvPathFeedrateOverride= (TextView) view.findViewById(R.id.tv_path_feedrate_override);
        //初始化生产状态
        //程序名
        tvProgramName= (TextView) view.findViewById(R.id.tv_program_name);
        //程序块
        tvBlock= (TextView) view.findViewById(R.id.tv_block);
        //程序总行数
        tvLineTotal= (TextView) view.findViewById(R.id.tv_line_total);
        //当前行号
        tvLineCurrent= (TextView) view.findViewById(R.id.tv_line_current);
//        //加工产品数
//        tvPartCount= (TextView) view.findViewById(R.id.tv_part_count);
//        //加工时间
//        tvMachiningTime= (TextView) view.findViewById(R.id.tv_machining_time);
        //刀具的刀号
        tvToolNumber= (TextView) view.findViewById(R.id.tv_tool_number);
        //气压状态
        tvPressure= (TextView) view.findViewById(R.id.tv_pressure);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceStatusMessageEvent(DeviceStatusMessageEvent event){
        MachineTool machineTool=event.mDeviceStatus.getMachineTool();
        //设置数据
        setData(machineTool);
    }

    private void setData(MachineTool machineTool) {
        /*
        //设置设备信息
        tvDeviceMachineName.setText(machineTool.getDeviceMachineInfo().getName());
        tvDeviceMachineAvailability.setText(machineTool.getDeviceMachineInfo().getAvailability());
        tvDeviceMachineUUID.setText(machineTool.getUniqueId());
        tvDeviceMachineID.setText(machineTool.getDeviceMachineInfo().getId());
        //设置控制器数据
        tvControllerType.setText(machineTool.getControllerBaseInfo().getName());
        tvControllerCommunication.setText(machineTool.getControllerBaseInfo().getCommunication());
        tvControllerEmergencyStop.setText(machineTool.getControllerBaseInfo().getEmergencyStop());
        tvControllerMode.setText(machineTool.getControllerPathInfo().getControllerMode());
        tvControllerExec.setText(machineTool.getControllerPathInfo().getExecution());
        tvControllerTime.setText(machineTool.getDeviceMachineInfo().getTimeStamp());
        //设置轴相关数据
        //轴位置
        tvXPos.setText(machineTool.getAxesLinearXInfo().getPosition());
        tvYPos.setText(machineTool.getAxesLinearYInfo().getPosition());
        tvZPos.setText(machineTool.getAxesLinearZInfo().getPosition());
        tvAPos.setText(machineTool.getAxesRotaryAInfo().getAngle());
        tvCPos.setText(machineTool.getAxesRotaryCInfo().getAngle());
        //轴速度
        tvXFeedrate.setText(machineTool.getAxesLinearXInfo().getAxisFeedRate());
        tvYFeedrate.setText(machineTool.getAxesLinearYInfo().getAxisFeedRate());
        tvZFeedrate.setText(machineTool.getAxesLinearZInfo().getAxisFeedRate());
        tvAFeedrate.setText(machineTool.getAxesRotaryAInfo().getAngularVelocity());
        tvCFeedrate.setText(machineTool.getAxesRotaryCInfo().getAngularVelocity());
        //轴负载
        tvXLoad.setText(machineTool.getAxesLinearXInfo().getLoad());
        tvYLoad.setText(machineTool.getAxesLinearYInfo().getLoad());
        tvZLoad.setText(machineTool.getAxesLinearZInfo().getLoad());
        tvALoad.setText(machineTool.getAxesRotaryAInfo().getLoad());
        tvCLoad.setText(machineTool.getAxesRotaryCInfo().getLoad());
        //主轴与进给
        //主轴
        tvSpindleRotaryVelocityProgrammed.setText(machineTool.getAxesRotarySInfo().getRotaryVelocityProgrammed());
        tvSpindleRotaryVelocityActual.setText(machineTool.getAxesRotarySInfo().getRotaryVelocityActual());
        tvSpindleFeedrateOverride.setText(machineTool.getAxesRotarySInfo().getAxisSFeedrateOverride());
        //进给
        tvPathFeedrateProgrammed.setText(machineTool.getControllerPathInfo().getPathFeedRateProgrammed());
        tvPathFeedrateActual.setText(machineTool.getControllerPathInfo().getPathFeedRateActual());
        tvPathFeedrateOverride.setText(machineTool.getControllerPathInfo().getPathFeedRateOverride());
        //生产状态
        tvProgramName.setText(machineTool.getControllerPathInfo().getProgram());
        tvBlock.setText(machineTool.getControllerPathInfo().getBlock());
        tvLineTotal.setText(machineTool.getControllerPathInfo().getLineTotal());
        tvLineCurrent.setText(machineTool.getControllerPathInfo().getLine());
        //加工产品数
//        tvPartCount.setText(machineTool.getControllerPathInfo().getPartCount());
//        tvPartCount.setText("0");
        //加工时间
//        tvMachiningTime.setText(machineTool.getControllerPathInfo().getMachiningTime());
//        tvMachiningTime.setText("00:01:15");

        //刀具的刀号
        tvToolNumber.setText(machineTool.getControllerPathInfo().getToolNumber());
        //气压
        tvPressure.setText(machineTool.getPneumaticInfo().getPneumaticCondition());
        */

        //TEST
        test();
    }

    private  void test(){
        tvDeviceMachineName.setText("LUNAN-XH7132A");
        tvDeviceMachineAvailability.setText("AVAILABLE");
        tvDeviceMachineUUID.setText("XH7132A");
        tvDeviceMachineID.setText("lunan_xh7132a");
        //设置控制器数据
        tvControllerType.setText("IntelligentCNC");
        tvControllerCommunication.setText("NORMAL");
        tvControllerEmergencyStop.setText("ARMED");
        tvControllerMode.setText("AUTO");
        tvControllerExec.setText("ACTIVE");
        tvControllerTime.setText("2017-11-22 20:47:55");
        //设置轴相关数据
        //轴位置
        tvXPos.setText("-133.962");
        tvYPos.setText("-137.375");
        tvZPos.setText("-161.089");
        tvAPos.setText("-18.683");
        tvCPos.setText("310.790");
        //轴速度5
        tvXFeedrate.setText("0.320");
        tvYFeedrate.setText("2.312");
        tvZFeedrate.setText("0.755");
        tvAFeedrate.setText("0.137");
        tvCFeedrate.setText("2.594");
        //轴负载
        tvXLoad.setText("0.124");
        tvYLoad.setText("0.096");
        tvZLoad.setText("0.167");
        tvALoad.setText("0.087");
        tvCLoad.setText("0.058");
        //主轴与进给
        //主轴
        tvSpindleRotaryVelocityProgrammed.setText("2000");
        tvSpindleRotaryVelocityActual.setText("1992");
        tvSpindleFeedrateOverride.setText("100");
        //进给
        tvPathFeedrateProgrammed.setText("500");
        tvPathFeedrateActual.setText("302.4");
        tvPathFeedrateOverride.setText("100");
        //生产状态
        tvProgramName.setText("DNURBS");
        tvBlock.setText("Dual NURBS");
//        tvLineTotal.setText(machineTool.getControllerPathInfo().getLineTotal());
        tvLineTotal.setText("57");
        tvLineCurrent.setText("34");
//        //加工产品数
////        tvPartCount.setText(machineTool.getControllerPathInfo().getPartCount());
//        tvPartCount.setText("1");
//        //加工时间
////        tvMachiningTime.setText(machineTool.getControllerPathInfo().getMachiningTime());
//        tvMachiningTime.setText("00:01:15");

        //刀具的刀号
        tvToolNumber.setText("T01");
        //气压状态
        tvPressure.setText("NORMAL");
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
