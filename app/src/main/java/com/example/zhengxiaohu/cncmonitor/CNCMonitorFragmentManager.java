package com.example.zhengxiaohu.cncmonitor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.zhengxiaohu.cncmonitor.fragment.CNCBasisFragment;
import com.example.zhengxiaohu.cncmonitor.fragment.CNCPathSimulationFragment;

import java.util.ArrayList;

/**
 * Created by XiaoHu Zheng on 2017/5/18.
 * Email: 1050087728@qq.com
 *
 * CNCMonitorFragment的嵌套Fragment的管理类,用于管理其子fragment
 */

public class CNCMonitorFragmentManager {
    private int containerId;
    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;

    private static CNCMonitorFragmentManager sManager;

    /**
     * 静态工厂方法获取该类的实例,懒汉式
     * @param parentFragment 父Fragment
     * @param containerId 布局容器的id
     * @return 返回该管理类实例sManager
     */
    public static CNCMonitorFragmentManager getInstance(Fragment parentFragment,int containerId){
        if (sManager==null){
            sManager=new CNCMonitorFragmentManager(parentFragment,containerId);
        }
        return sManager;
    }

    /**
     * 私有构造函数
     * @param fragment 传入的fragment
     * @param containerId 布局容器的id
     */
    private CNCMonitorFragmentManager(Fragment fragment,int containerId){
        this.containerId=containerId;
        mFragmentManager=fragment.getChildFragmentManager();
        initFragment();
    }

    /**
     * 初始化所有的子fragment，添加到fragmentTransaction事务中
     */
    private void initFragment() {
        mFragments=new ArrayList<>();
        mFragments.add(new CNCBasisFragment());
        mFragments.add(new CNCPathSimulationFragment());

        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        for (Fragment fragment:mFragments){
            fragmentTransaction.add(containerId,fragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 根据position显示对应的fragment
     * @param position fragment的位置索引
     */
    public void showFragment(int position){
        hideFragments();
        Fragment fragment=mFragments.get(position);
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有的子fragment
     */
    private void hideFragments() {
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        for (Fragment fragment:mFragments){
            if (fragment!=null){
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 根据索引获取对应的fragment
     * @param position fragment的位置索引
     * @return 返回对应索引的实例
     */
    public Fragment getFragment(int position){
        return mFragments.get(position);
    }

    /**
     * 销毁 CNCMonitorFragmentManager的实例sManager
     */
    public static void destroyManager(){
        sManager=null;
    }
}
