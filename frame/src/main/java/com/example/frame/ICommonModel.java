package com.example.frame;

public interface ICommonModel<T> {
    /**
     * 用于model层执行耗时任务，处理刷新和加载逻辑
     *
     * @param whichApi 区别接口的标识
     * @param params   泛型可变参数
     */
    void getData(ICommonPresenter presenter, int whichApi, T... params);
}
