package com.example.policeman996.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.example.policeman996.Constants;
import com.example.policeman996.R;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.presenter.Interface.IUserPageView;
import com.example.policeman996.presenter.UserPresenter;
import com.example.policeman996.recycler.UserCompanyAdapter;
import com.orhanobut.hawk.Hawk;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements IUserPageView {


  @BindView(R.id.back_btn)
  ImageView mBackBtn;
  @BindView(R.id.current_company)
  TextView mCurrentCompany;
  @BindView(R.id.switch_company)
  TextView mSwitchCompany;
  @BindView(R.id.btn_switch)
  CardView mSwitchButton;
  @BindView(R.id.search_edit)
  MaterialEditText mSearchEdit;
  @BindView(R.id.user_company_refresh)
  RefreshLayout mListRefreshLayout;
  @BindView(R.id.user_company_list_recycler_view)
  RecyclerView mList;
  @BindView(R.id.new_edit)
  MaterialEditText mNewCompanyName;
  @BindView(R.id.new_edit_place)
  MaterialEditText mNewCompanyPlace;
  @BindView(R.id.btn_new)
  CardView mNewButton;
  @BindView(R.id.content_layout)
  RelativeLayout mContent;
  @BindView(R.id.back_layou)
  RelativeLayout mBackLayout;

  private String mCompanyName;
  private long mCompanyId;

  private UserPresenter mPresenter;
  private List<CompanyData> mDataList;
  private CompanyData mSelectedCompany;
  private UserCompanyAdapter mAdapter;

  private int mPage = 0;
  private String mNewname;
  private String mNewLocation;
  private static int PAGE_SIZE = 20;
  private String mKey = "";
  private boolean isLoadMore = false;

  private OnClickListener mListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.back_layou:
        case R.id.content_layout:
          if (mSearchEdit != null && mNewCompanyPlace != null && mNewCompanyName != null) {
            mSearchEdit.clearFocus();
            mNewCompanyName.clearFocus();
            mNewCompanyPlace.clearFocus();
            InputMethodManager imm = (InputMethodManager) UserActivity.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            if (imm != null) {
              imm.hideSoftInputFromWindow(
                  UserActivity.this.getWindow().getDecorView().getWindowToken(), 0);
            }
          }
          break;
        case R.id.back_btn:
          finish();
          break;
        case R.id.btn_switch:
          if (mSelectedCompany != null) {
            mPresenter.changeCompany(mSelectedCompany.getCompanyId());
          }
          break;
        case R.id.btn_new:
          if (mNewCompanyName.getText() != null) {
            mNewname = mNewCompanyName.getText().toString();
          }
          if (mNewCompanyPlace.getText() != null) {
            mNewLocation = mNewCompanyPlace.getText().toString();
          }

          if (!mNewLocation.equals("") && !mNewname.equals("")) {
            mPresenter.submitCompany(mNewname, mNewLocation);
          } else {
            Toasty.error(UserActivity.this, "Information Incomplete！").show();
          }
          break;
      }
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
  }

  public UserActivity() {
    super();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.user_activity);
    ButterKnife.bind(this);

    mPresenter = new UserPresenter(this);
    mBackBtn.setOnClickListener(mListener);
    mNewButton.setOnClickListener(mListener);
    mSwitchButton.setOnClickListener(mListener);
    mBackLayout.setOnClickListener(mListener);
    mContent.setOnClickListener(mListener);

    initView();
  }

  private void initView() {
    setRefresh();
    mCompanyName = Hawk.get(Constants.COMPANY_NAME, "");
    mCompanyId = Hawk.get(Constants.COMPANY_ID, 0L);
    if (mCompanyName.equals("")) {
      mCurrentCompany.setText("You Have No Company Information!");
      mCurrentCompany.setTextColor(getResources().getColor(R.color.colorRed));
    } else {
      mCurrentCompany.setText(mCompanyName);
      mCurrentCompany.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    mSearchEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        isLoadMore = false;
        mKey = s.toString();
        mPage = 0;
        mPresenter.getData(mKey, mPage, PAGE_SIZE);
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    mDataList = new ArrayList<>();
    mAdapter = new UserCompanyAdapter(R.layout.user_compay_item, mDataList);
    mAdapter.setmView(this);

    mList.setLayoutManager(new LinearLayoutManager(this));
    mList.setHasFixedSize(true);
    mList.setAdapter(mAdapter);


    mPresenter.getData(mKey, mPage, PAGE_SIZE);

  }

  @Override
  public void selectCompany(CompanyData data) {
    mSelectedCompany = data;
    mSwitchCompany.setText(data.getCompanyName());
  }

  @Override
  public void updateList(List<CompanyData> data) {
    if (isLoadMore) {
      if (data.size() > 0) {
        mAdapter.addData(data);
      } else {
        mPage--;
        Toasty.info(this, "No More Data.", Toast.LENGTH_SHORT, true).show();
      }
    } else {
      mAdapter.replaceData(data);
    }


  }

  @Override
  public void requestFail(String code) {
    ResponseUtils.handleErrorText(this, code);
  }

  @Override
  public void requestSuccess(int type) {
    if (type == 0) {
      Toasty.info(this, "Company Information Submitted!").show();
    } else if (type == 1) {
      Toasty.info(this, "Change Company Success!").show();
    }

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        finish();
      }
    }, 2000);

  }


  private void setRefresh() {
    mListRefreshLayout.setPrimaryColors(getResources().getColor(R.color.colorPrimary));
    mListRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isLoadMore = true;
        mPage++;
        mPresenter.getData(mKey, mPage, PAGE_SIZE);
        refreshLayout.finishLoadMore(1000);
      }
    });
  }


}
