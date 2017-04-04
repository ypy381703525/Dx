package com.example.mvplibrary.base;


public abstract class BasePresenter<M extends BaseModel,V extends BaseView> {

    public M mModel;

    public V mView;

    public BasePresenter() {

    }

    public void setVM(V mView,M mModel){
        this.mView = mView;
        this.mModel = mModel;
    }








}
