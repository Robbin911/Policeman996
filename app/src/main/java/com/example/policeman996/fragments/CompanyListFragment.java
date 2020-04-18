package com.example.policeman996.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.policeman996.R;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.model.bean.CompanyListData;
import com.example.policeman996.presenter.Interface.ICompanyListPageView;
import com.example.policeman996.presenter.CompanyListPresenter;
import com.example.policeman996.recycler.CompanyListPageAdapter;
import com.example.policeman996.view.HorizontalPosition;
import com.example.policeman996.view.SearchView;
import com.example.policeman996.view.SmartPopWindow;
import com.example.policeman996.view.TopBarView;
import com.example.policeman996.view.VerticalPosition;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class CompanyListFragment extends Fragment implements ICompanyListPageView {

  @BindView(R.id.company_list_recycler_view)
  RecyclerView mRecyclerView;
  private CompanyListPageAdapter mAdapter;
  @BindView(R.id.company_refresh)
  SmartRefreshLayout mRefresh;
  private CompanyListPresenter mPresenter;

  private SearchView mSearchView;
  private SmartPopWindow mPopwin;


  private List<CompanyData> mDataList;

  private TopBarView mTop;

  private String mKey = "";
  private int mCurrentPage;
  private static int PAGE_SIZE = 20;
  private boolean isRefresh = true;

  Unbinder unbinder;

  private OnClickListener mListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.iv_search:
          if (mSearchView != null) {
            openSearch();
          } else {
            initPopwin();
            openSearch();
          }
          break;
        case R.id.btn_search:
          if (mSearchView != null) {
            isRefresh = true;
            mKey = mSearchView.getKeyString();
            mCurrentPage = 0;
            mPresenter.getData(mKey, mCurrentPage, PAGE_SIZE);
            mTop.setSearchKey(mKey);
            mPopwin.dismiss();
          }
          break;
      }
    }
  };

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if(isVisibleToUser){

    }
  }

  public CompanyListFragment(TopBarView topBarView) {
    mTop = topBarView;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.company_list_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    initView();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void initView() {
    mCurrentPage = 0;
    setRefresh();
    initPopwin();

    mTop.setOnSearchClick(mListener);

    mPresenter = new CompanyListPresenter(this);
    mDataList = new ArrayList<>();
    mAdapter = new CompanyListPageAdapter(R.layout.company_list_item, mDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);

    mPresenter.getData(mKey, mCurrentPage, PAGE_SIZE);
    mTop.setSearchKey(mKey);

  }

  public void initPopwin() {
    mSearchView = new SearchView(getContext());
    mSearchView.setOnClick(mListener);
    mPopwin = SmartPopWindow.Builder
        .build(getContext(), mSearchView)
        .setOutsideTouchDismiss(true)
        .createPopupWindow();

  }


  public void openSearch() {
    if (mSearchView == null) {
      initPopwin();
    }
    View anchorView = mTop.findViewById(R.id.iv_search);
    try {
      mPopwin
          .showAtAnchorView(anchorView,
              VerticalPosition.BELOW,
              HorizontalPosition.ALIGN_RIGHT, 0, 40);
    } catch (Exception e) {
      e.printStackTrace();
      try {
        mPopwin.dismiss();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void updateList(List<CompanyData> data) {
    if (isRefresh) {
      mAdapter.replaceData(data);
    } else {
      if (data.size() > 0) {
        mAdapter.addData(data);
      } else {
        if (getContext() != null) {
          Toasty.info(getContext(), "No More Data.", Toast.LENGTH_SHORT, true).show();
        }
      }
    }
  }

  @Override
  public void requestError(String msg) {
    ResponseUtils.handleErrorText(getContext(), msg);
  }

  private void setRefresh() {
    mRefresh.setPrimaryColors(getResources().getColor(R.color.colorPrimary));
    mRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        mCurrentPage = 0;
        mPresenter.getData(mKey, mCurrentPage, PAGE_SIZE);
        refreshLayout.finishRefresh(1000);
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        mCurrentPage++;
        mPresenter.getData(mKey, mCurrentPage, PAGE_SIZE);
        refreshLayout.finishLoadMore(1000);
      }
    });
  }

}
