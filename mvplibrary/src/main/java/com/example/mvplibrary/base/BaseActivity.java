package com.example.mvplibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mvplibrary.utils.TypeUtil;


import butterknife.ButterKnife;

/**
 * Created by dell on 2017/3/24.
 */

public abstract class BaseActivity<P extends BasePresenter ,M extends BaseModel> extends AppCompatActivity {

    public P mPresenter;

    public M mModel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局前做的操作
        doBeforeLayout();
        //设置布局
        setContentView(getLayoutId());
        //View注入
        ButterKnife.bind(this);
        //初始化Presenter和Model
        mPresenter = TypeUtil.getObject(this,0);
        mModel = TypeUtil.getObject(this,1);
        //初始化View和Model
        if (mPresenter != null){
//            mPresenter.setVM(,mModel);
            //将View实现
            this.initPresenter();
        }
        //初始化View
        this.initView();

    }

    protected void doBeforeLayout(){

    }

    public abstract int getLayoutId();


    public abstract void initPresenter();


    public abstract void initView();





}
