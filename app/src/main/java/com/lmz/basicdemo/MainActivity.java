package com.lmz.basicdemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lmz.baselibrary.ui.BaseActivity;
import com.lmz.basicdemo.adapters.HistoryAdapter;
import com.lmz.basicdemo.model.HistoryModle;
import com.lmz.basicdemo.present.implPresenter.HistoryPresent;
import com.lmz.basicdemo.present.implView.IHistoryView;
import com.lmz.basicdemo.ui.UpLoadPicActivity;
import com.lmz.basicdemo.ui.activitys.CityChooseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IHistoryView {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private HistoryPresent mHistoryPresent;
    private HistoryAdapter mHistoryAdapter;

    View EmptyView;
    View ErrorView;

    @Override
    protected void initConvetView(Bundle saveInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        EmptyView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recycleView.getParent(), false);
        ErrorView = getLayoutInflater().inflate(R.layout.eorry_updata, (ViewGroup) recycleView.getParent(), false);
        ErrorView.findViewById(R.id.btnEorry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);
                mHistoryPresent.onRefresh();
            }
        });
    }

    @Override
    protected void initData() {
        mHistoryPresent = new HistoryPresent(this);
        mHistoryAdapter = mHistoryPresent.getAdapter();
        refreshLayout.setOnRefreshListener(mHistoryPresent);
        refreshLayout.setRefreshing(true);
        mHistoryPresent.onRefresh();
        recycleView.setAdapter(mHistoryAdapter);
        /**
         * rv监听
         */
        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Toast.makeText(MainActivity.this, "删除，删除" + i, Toast.LENGTH_SHORT).show();
            }
        });
        recycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(CityChooseActivity.class);
            }
        });
    }

    /**
     * 上传图片页面
     */
    public void upload(View view) {
        startActivity(UpLoadPicActivity.class);
    }

    @Override
    public void updataHistoryData(HistoryModle historyModle) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hiteProgressDialog() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        mHistoryAdapter.setEmptyView(ErrorView);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mHistoryPresent.unsubcrible();
        super.onDestroy();
    }
}
