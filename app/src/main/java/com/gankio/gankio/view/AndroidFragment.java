package com.gankio.gankio.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gankio.R;
import com.gankio.gankio.adapter.AndroidRecycleViewAdapter;
import com.gankio.gankio.adapter.PicRecycleViewAdapter;
import com.gankio.gankio.base.AndroidContracts;
import com.gankio.gankio.model.AndroidModel;
import com.gankio.gankio.presenter.AndroidPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyongxing on 2018-11-22.
 */
public class AndroidFragment extends Fragment implements AndroidContracts.View{
    public static final String pageCount = "10";
    public static int pageNum = 1;
    private AndroidContracts.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private AndroidRecycleViewAdapter myRecycleViewAdapter;
    private PicRecycleViewAdapter mPicRecycleViewAdapter;
    List<AndroidModel.ResultsBean> beans = new ArrayList<>();
    static  String info;

    private static final String ARG_PARAM1 = "type";

    private String type = "Android";

    public static AndroidFragment newInstance(String param1) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_android_fragment,container,false);
        type = getArguments().getString(ARG_PARAM1);
        System.out.println("GanKioViewType=" + type);
        initData(view);
        return view;
    }

    private void initData(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.androidList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.androidSwipeFresh);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (type.equals("福利")){
            mLinearLayoutManager = new GridLayoutManager(getActivity(),2);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            mPicRecycleViewAdapter = new PicRecycleViewAdapter(this.getActivity());
            mRecyclerView.setAdapter(mPicRecycleViewAdapter);
        }else{
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            myRecycleViewAdapter = new AndroidRecycleViewAdapter(this.getActivity());
            mRecyclerView.setAdapter(myRecycleViewAdapter);
        }

//        mRecyclerView.setAdapter(myRecycleViewAdapter);
        mPresenter = new AndroidPresenter(this);
        getLifecycle().addObserver(mPresenter);
        getData(pageNum);
//        initSpruce();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                beans.clear();
                getData(pageNum);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    //加载更多
                    pageNum ++;
                    getData(pageNum);
                }

            }
        });
    }
    //获取数据
    private void getData(int pageNum){
        if (!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
        if (null != mPresenter){
            mPresenter.getAndroidData(type,pageCount,pageNum);
        }
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void setPresenter(AndroidContracts.Presenter presenter) {
        if (null != presenter){
            this.mPresenter = presenter;
        }
    }
    @Override
    public void showData(AndroidModel string) {
        if (pageNum == 1){
            beans.addAll(string.getResults());
            beans = string.getResults();
        }else {
            beans.addAll(string.getResults());
        }
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (type.equals("福利")){
            mPicRecycleViewAdapter.setLists(beans);
        }else{
            myRecycleViewAdapter.setLists(beans);
        }
    }


    /**
     * 如果采用lifecycle的话 就不需要在view中通知presenter的ondestory了
     * presenter可以自动根据activity的生命周期同步
     */
   /* @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter){
            mPresenter.onDestory();
            mPresenter = null;
        }
    }*/
}
