package com.example.zhengxiaohu.cncmonitor.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhengxiaohu.cncmonitor.DeviceStatusMessageEvent;
import com.example.zhengxiaohu.cncmonitor.R;
import com.example.zhengxiaohu.cncmonitor.devices.MachineTool;
import com.example.zhengxiaohu.cncmonitor.util.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataManagementFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private static final String TAG = "DataManagementFragment";

    private String mParam;
    EditText et_user_account;
    EditText et_user_password;
    Button btn_start_data_connection;
    Button btn_stop_data_connection;
    TextView tv_result;

    public DataManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter.
     * @return A new instance of fragment DataManagementFragment.
     */
    public static DataManagementFragment newInstance(String param) {
        DataManagementFragment fragment = new DataManagementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_management,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view==null){
            Log.d(TAG, "initView: view is null");
        }else {
            et_user_account= (EditText) view.findViewById(R.id.et_user_account);
            et_user_password= (EditText) view.findViewById(R.id.et_user_password);
            btn_start_data_connection= (Button) view.findViewById(R.id.btn_start_data_connection);
            btn_stop_data_connection= (Button) view.findViewById(R.id.btn_stop_data_connection);
            tv_result= (TextView) view.findViewById(R.id.tv_result);
            //设置开始数据采集按钮的监听函数
            btn_start_data_connection.setOnClickListener(this);
            //设置停止采集按钮的监听函数
            btn_stop_data_connection.setOnClickListener(this);
        }
    }

    /**
     * EventBus处理事件
     * @param event 设备状态数据事件
     */
    public void onDeviceStatusMessageEvent(DeviceStatusMessageEvent event){
        MachineTool machineTool=event.mDeviceStatus.getMachineTool();
        //存储数据
        storeDataToMySQL(machineTool);
    }

    /**
     * 存储数据到服务器端
     * @param machineTool 机床参数
     */
    private void storeDataToMySQL(MachineTool machineTool) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_data_connection:
                if (!(et_user_account.getText().toString()).equals("")
                        &&!(et_user_password.getText().toString().equals(""))){
                    Log.e(TAG, "onClick: "+"姓名和账号均不为空" );
                    login(et_user_account.getText().toString(),et_user_password.getText().toString());
                } else {
                    Snackbar.make(v,"账号和密码不能为空!",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_stop_data_connection:
                if (!(et_user_account.getText().toString()).equals("")
                        &&!(et_user_password.getText().toString().equals(""))){
                    Log.e(TAG, "onClick: "+"姓名和账号均不为空" );
                    register(et_user_account.getText().toString(),et_user_password.getText().toString());
                } else {
                    Snackbar.make(v,"账号和密码不能为空!",Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void register(String account, String password) {
        String registerUrlStr= Constant.URL_Register+"?accou。nt="+account+"&password="+password;
        new MyAsyncTask(tv_result).execute(registerUrlStr);
    }

    private void login(String account,String password){
        String loginUrlStr=Constant.URL_Login+"?account="+account+"&password="+password;
        new MyAsyncTask(tv_result).execute(loginUrlStr);
    }

    public static class MyAsyncTask extends AsyncTask<String,Integer,String>{

        private TextView mTextView;

        public MyAsyncTask(TextView textView){
            mTextView=textView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute: "+"task onPreExecute" );
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e(TAG, "doInBackground: "+"task doInBackground" );
            HttpURLConnection connection=null;
            StringBuilder response=new StringBuilder();
            try {
                URL url=new URL(params[0]);
                //开启连接
                connection= (HttpURLConnection) url.openConnection();
                //设置请求方法
                connection.setRequestMethod("GET");
                //设置建立连接的超时时间
                connection.setConnectTimeout(8000);
                //设置网络报文收发超时时间
                connection.setReadTimeout(8000);
                //通过连接的输入流获取下发报文,然后就是java的流处理
                InputStream inputStream=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line=reader.readLine())!=null){
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //此处的返回结果作为
            return response.toString();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e(TAG, "onPostExecute: "+"task onPostExecute" );
            mTextView.setText(s);
        }
    }

}
