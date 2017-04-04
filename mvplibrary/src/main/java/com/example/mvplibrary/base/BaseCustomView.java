package com.example.mvplibrary.base;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import com.example.mvplibrary.utils.TypeUtil;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/3/30.
 */

public abstract class BaseCustomView<P extends BasePresenter,M extends BaseModel>  {
    protected View layout;

    public P mPresenter;

    public M mModel;
    private View headView;
    private Activity activity;

    public BaseCustomView(Activity activity) {
        this.activity=activity;
    }

    public View getHeadView(){
        headView = LayoutInflater.from(activity).inflate(getLayoutId(),null,false);
        ButterKnife.bind(this,headView);
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

        return headView;

    }
    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initPresenter();

}
