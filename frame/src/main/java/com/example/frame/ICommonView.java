package com.example.frame;

public interface ICommonView<D> {
    /*
     *whichApi 成功接口得标识
     * loadType 类型的回调（正常加载，刷新，加载更多）
     * pD一般是实体类的回调 ，但为了框架的灵活性，确保其他一些数据的偶发性，故未将长度写死
     */
    void onSuccess(int whichApi, int loadType, D... pD);

    void onFailed(int whichApi, Throwable throwable);
}
