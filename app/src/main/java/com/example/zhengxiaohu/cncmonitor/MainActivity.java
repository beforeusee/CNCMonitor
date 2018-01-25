package com.example.zhengxiaohu.cncmonitor;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhengxiaohu.cncmonitor.fragment.CNCMonitorFragment;
import com.example.zhengxiaohu.cncmonitor.fragment.DataManagementFragment;
import com.example.zhengxiaohu.cncmonitor.fragment.DeviceManagerFragment;
import com.example.zhengxiaohu.cncmonitor.fragment.SensorMonitorFragment;

import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/16.
 * Email:1050087728@qq.com
 */

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        DeviceManagerFragment.OnDeviceManagerFragmentInteractionListener,
        CNCMonitorFragment.OnCNCMonitorFragmentInteractionListener,
        SensorMonitorFragment.OnSensorMonitorFragmentInteractionListener{

    //定义底部标签栏的具体标签及存储标签的数组列表以及标签的图标
    /*private TabLayout.Tab deviceManagerTab;
    private TabLayout.Tab CNCMonitorTab;
    private TabLayout.Tab SensorMonitorTab;*/
    private ArrayList<String> mTabList=new ArrayList<>();
    /*private int[] mTabIcons=new int[]{R.drawable.device_manager,R.drawable.cnc_monitor,
            R.drawable.sensor_monitor,R.drawable.data_management};*/

    private int[] mTabIcons=new int[]{R.drawable.device_manager,R.drawable.cnc_monitor,
            R.drawable.sensor_monitor};
    //定义fragments列表及CNCMonitorFragmentPagerAdapter
    private ArrayList<Fragment> mFragments= new ArrayList<>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_main);

        //初始化数据
        initData();

        //实例化适配器mFragmentPagerAdapter
        CNCMonitorFragmentPagerAdapter fragmentPagerAdapter = new CNCMonitorFragmentPagerAdapter(getSupportFragmentManager(), mTabList, mFragments);
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //必须在mTabLayout与mViewPager绑定后，这样才知道mTabLayout中Tab的数量，mTabLayout相当于容器
        for (int i = 0; i< tabLayout.getTabCount(); i++){
            try {
                tabLayout.getTabAt(i).setCustomView(getTabView(i));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        viewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(this);  //添加tab标签页选择侦听事件

        Log.d(TAG, "onCreate: "+"execution success.");
    }

    /**
     * 初始化数据，包括底部导航栏的标签和fragment
     */
    private void initData() {
        initTabList();
        initFragmentList();
    }

    /**
     * 初始化放入容器viewPager的fragment
     */
    private void initFragmentList() {
        mFragments.add(0,DeviceManagerFragment.newInstance("DeviceManager"));
        mFragments.add(1, CNCMonitorFragment.newInstance("CNCMonitor"));
        mFragments.add(2, SensorMonitorFragment.newInstance("SensorMonitor"));
//        mFragments.add(3, DataManagementFragment.newInstance("DataManagement"));
    }

    /**
     * 初始化底部标签
     */
    private void initTabList() {
        mTabList.add(0,getString(R.string.device_manager));
        mTabList.add(1,getString(R.string.cnc_monitor));
        mTabList.add(2,getString(R.string.sensor_monitor));
//        mTabList.add(3,getString(R.string.data_management));
    }

    /**
     * 获取指定位置的tab的view
     * @param position 指定的位置
     * @return 返回view
     */
    public View getTabView(int position){
        View view= LayoutInflater.from(this).inflate(R.layout.layout_tab_view,null);
        TextView tabTitle= (TextView) view.findViewById(R.id.tv_tab_text);
        ImageView tabIcon= (ImageView) view.findViewById(R.id.iv_tab_icon);
        tabTitle.setText(mTabList.get(position));
        tabIcon.setImageResource(mTabIcons[position]);
        if (0==position){
            tabTitle.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            tabIcon.setImageResource(R.drawable.device_manager_fill);
        }
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelectedState(tab);
    }

    /**
     * 设置tab选中的时候的状态
     * @param tab 底部导航栏标签tab
     */
    private void setTabSelectedState(TabLayout.Tab tab) {
        View customView=tab.getCustomView();
        TextView tabText= (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon= (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        String s=tabText.getText().toString();
        if (getString(R.string.device_manager).equals(s)){
            tabIcon.setImageResource(R.drawable.device_manager_fill);
        } else if (getString(R.string.cnc_monitor).equals(s)){
            tabIcon.setImageResource(R.drawable.cnc_monitor_fill);
        } else if (getString(R.string.sensor_monitor).equals(s)){
            tabIcon.setImageResource(R.drawable.sensor_monitor_fill);
        }
        /*else if (getString(R.string.data_management).equals(s)){
            tabIcon.setImageResource(R.drawable.data_management_fill);
        }*/
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabUnSelectedState(tab);
    }

    /**
     * 设置底部导航栏的标签未被选中时的状态
     * @param tab 底部导航栏的标签tab
     */
    private void setTabUnSelectedState(TabLayout.Tab tab) {
        View customView=tab.getCustomView();
        TextView tabText= (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon= (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(this,R.color.black));
        String s=tabText.getText().toString();
        if (getString(R.string.device_manager).equals(s)){
            tabIcon.setImageResource(R.drawable.device_manager);
        } else if (getString(R.string.cnc_monitor).equals(s)){
            tabIcon.setImageResource(R.drawable.cnc_monitor);
        } else if (getString(R.string.sensor_monitor).equals(s)){
            tabIcon.setImageResource(R.drawable.sensor_monitor);
        }
        /*else if (getString(R.string.data_management).equals(s)){
            tabIcon.setImageResource(R.drawable.data_management);
        }*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: newOrientation:"+newConfig.orientation);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onCNCMonitorFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDeviceManagerFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSensorMonitorFragmentInteraction(Uri uri) {

    }
}
