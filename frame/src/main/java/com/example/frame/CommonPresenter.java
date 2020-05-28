package com.example.frame;

public class CommonPresenter implements ICommonPresenter {
    private ICommonView mView;
    private ICommonModel mModel;

    public CommonPresenter(ICommonView iCommonView, ICommonModel iCommonModel) {
        mView = iCommonView;
        mModel = iCommonModel;
    }

    @Override
    public void getData(int whichApi, Object... objects) {
        mModel.getData(this, whichApi, objects);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        mView.onSuccess(whichApi, loadType, pD);

    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        mView.onFailed(whichApi, throwable);
    }
}
