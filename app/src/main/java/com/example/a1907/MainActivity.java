package com.example.a1907;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1907.adapter.TestAdapter;
import com.example.data.TestInfo;
import com.example.frame.ApiConFig;
import com.example.frame.CommonPresenter;
import com.example.frame.ICommonView;
import com.example.frame.LoadTypeConfig;
import com.example.frame.TestModel;
import com.example.frame.utils.ParamHashMap;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ICommonView {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private TestModel mModel;
    private CommonPresenter mPresenter;
    private List<TestInfo.DataInfo> datas = new ArrayList<>();
    private int pageId = 0;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = new TestModel();
        mPresenter = new CommonPresenter(this, mModel);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        final Map<String, Object> params = new ParamHashMap().add("c", "api").add("a", "getList");
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageId++;
                mPresenter.getData(ApiConFig.TEST_GET, LoadTypeConfig.MORE, params, pageId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 0;
                mPresenter.getData(ApiConFig.TEST_GET, LoadTypeConfig.REFRESH, params, pageId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter = new TestAdapter(datas, this);
        recyclerView.setAdapter(testAdapter);
        mPresenter.getData(ApiConFig.TEST_GET, LoadTypeConfig.NORMAL, params, pageId);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi) {
            case ApiConFig.TEST_GET:
                if (loadType == LoadTypeConfig.MORE) {
                    refreshLayout.finishLoadMore();
                } else if (loadType == LoadTypeConfig.REFRESH) {
                    refreshLayout.finishRefresh();
                    datas.clear();
                }
                List<TestInfo.DataInfo> datas = ((TestInfo) pD[0]).datas;
                this.datas.addAll(datas);
                testAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        Toast.makeText(this, throwable.getMessage() != null ? throwable.getMessage() : "网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
