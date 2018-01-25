package com.example.zhengxiaohu.cncmonitor.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.device_details.DeviceStatus;
import com.example.zhengxiaohu.cncmonitor.util.NetworkStatus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/16.
 * Email:1050087728@qq.com
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceManagerFragment.OnDeviceManagerFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DeviceManagerFragment extends Fragment {

    private static final String ARG_PARAM="param";
    private static final String TAG = "DeviceManagerFragment";
    private static final int SLEEP_TIME=500;
    private String mParam;
    private boolean isConnected=false;

    Button btnConnected;
    Button btnDisconnected;
    EditText et_ip;

    private OnDeviceManagerFragmentInteractionListener mListener;
    //需要绘制图形的有效功率的集合
    private ArrayList<Double> activePowerList=new ArrayList<>();

    /**
     * 按谷歌官方推荐Fragment类中需要有一个空的构造函数DeviceManagerFragment()
     */
    public DeviceManagerFragment(){

    }

    /**
     * 使用工厂方法创建提供参数的DeviceManagerFragment实例
     * @param s 传入实例deviceManagerFragment的参数
     * @return 返回实例deviceManagerFragment
     */
    public static DeviceManagerFragment newInstance(String s){
        DeviceManagerFragment deviceManagerFragment=new DeviceManagerFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARG_PARAM,s);
        deviceManagerFragment.setArguments(bundle);
        return deviceManagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mParam=getArguments().getString(ARG_PARAM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_device_manager,container,false);
        if (view!=null){
            btnConnected= (Button) view.findViewById(R.id.btn_connected);
            btnDisconnected= (Button) view.findViewById(R.id.btn_disconnected);
            et_ip= (EditText) view.findViewById(R.id.et_ip);
        }

        btnConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断网络是否正确连接
                boolean isNetworkAvailable= NetworkStatus.isNetworkAvailable(getContext());
                if (!isNetworkAvailable){
                    Toast.makeText(getContext(),"请检查网络是否正确连接",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String url=et_ip.getText().toString();
                Log.d(TAG, "onClick: url="+url);
                isConnected=true;

                //开启子线程执行耗时的网络操作和解析xml
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isConnected){
                            /*boolean isNetworkAvailable=NetworkStatus.isNetworkAvailable(getContext());
                            if (!isNetworkAvailable){
                                return;
                            }*/
                            DeviceStatus deviceStatus=DeviceStatus.getDeviceStatus(url);
                            if (deviceStatus!=null){
                                //EventBus将MessageEvent消息post出去,各个UI界面接收到消息后取出数据进行UI更新
                                EventBus.getDefault().post(new DeviceStatusMessageEvent(deviceStatus));

                                //开启睡眠
                                try {
                                    Thread.sleep(SLEEP_TIME);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
            }
        });

        btnDisconnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnected=false;
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDeviceManagerFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDeviceManagerFragmentInteractionListener) {
            mListener = (OnDeviceManagerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDeviceManagerFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDeviceManagerFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDeviceManagerFragmentInteraction(Uri uri);
    }
}
