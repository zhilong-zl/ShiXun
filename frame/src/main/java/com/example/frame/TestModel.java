package com.example.frame;

import android.annotation.SuppressLint;

import com.example.data.TestInfo;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestModel implements ICommonModel {
    @SuppressLint("CheckResult")
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        int loadType = (int) params[0];
        Map param = (Map) params[1];
        int pageId = (int) params[2];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Observable<TestInfo> data = retrofit.create(IService.class).getTestData(param, pageId);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestInfo>() {
                    @Override
                    public void accept(TestInfo testInfo) throws Exception {
                        presenter.onSuccess(whichApi, loadType, testInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        presenter.onFailed(whichApi, throwable);
                    }
                });
    }
}
