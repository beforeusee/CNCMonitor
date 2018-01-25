package com.example.zhengxiaohu.cncmonitor.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.zhengxiaohu.cncmonitor.CNCMonitorFragmentManager;
import com.example.zhengxiaohu.cncmonitor.MainActivity;
import com.example.zhengxiaohu.cncmonitor.R;


/**
 * Created by XiaoHu Zheng on 2017/5/16.
 * Email:1050087728@qq.com
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CNCMonitorFragment.OnCNCMonitorFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CNCMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CNCMonitorFragment extends Fragment {

    public static final String ARG_PARAM="param";
    private String mParam;

    private RadioGroup rg_child_fragment_cnc_tab;
    private CNCMonitorFragmentManager mManager;

    private OnCNCMonitorFragmentInteractionListener mListener;
    /**
     * 按谷歌官方推荐Fragment类中需要有一个空的构造函数CNCMonitorFragment()
     */
    public CNCMonitorFragment(){

    }


    /**
     * 使用工厂方法创建提供参数的CNCMonitorFragment实例
     * @param s 传入cncMonitorFragment的参数
     * @return 返回实例cncMonitorFragment
     */
    public static CNCMonitorFragment newInstance(String s){
        CNCMonitorFragment cncMonitorFragment=new CNCMonitorFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARG_PARAM,s);
        cncMonitorFragment.setArguments(bundle);
        return cncMonitorFragment;
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
        View view=inflater.inflate(R.layout.fragment_cnc_monitor,container,false);

        mManager=CNCMonitorFragmentManager.getInstance(this,R.id.fl_cnc_child_fragment_container);
        if (!getActivity().isDestroyed()){
            mManager.showFragment(0);
        }

        rg_child_fragment_cnc_tab= (RadioGroup) view.findViewById(R.id.rg_child_fragment_cnc_tab);
        rg_child_fragment_cnc_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_cnc_basis_fragment:
                        mManager.showFragment(0);
                        break;
                    case R.id.rb_cnc_path_simulation_fragment:
                        mManager.showFragment(1);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCNCMonitorFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCNCMonitorFragmentInteractionListener) {
            mListener = (OnCNCMonitorFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDeviceManagerFragmentInteractionListener");
        }
    }

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
    public interface OnCNCMonitorFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCNCMonitorFragmentInteraction(Uri uri);
    }
}
