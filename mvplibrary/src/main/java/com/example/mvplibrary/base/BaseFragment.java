package com.example.mvplibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvplibrary.utils.TypeUtil;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/3/24.
 */

public abstract class BaseFragment<P extends BasePresenter,M extends BaseModel> extends Fragment {

    protected  View layout;

    public P mPresenter;

    public M mModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化布局
        if (layout == null){
            layout = inflater.inflate(getLayoutId(),container,false);
        }
        //View注入
        ButterKnife.bind(this,layout);

        //初始化Presenter和Model
        mModel = TypeUtil.getObject(this,1);

        mPresenter = TypeUtil.getObject(this,0);


        //初始化View和Model
        if (mPresenter != null){
//            mPresenter.setVM(,mModel);
            //将View实现
            this.initPresenter();
        }
        //初始化View
        this.initView();


        return layout;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initPresenter();

}
