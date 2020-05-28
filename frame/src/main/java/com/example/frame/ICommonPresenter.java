package com.example.frame;

public interface ICommonPresenter<P> extends ICommonView {
    //有它作为中间层来发送网络请求 2.将请求到的结果回调到view层
    void getData(int whichApi, P... ps);
}
