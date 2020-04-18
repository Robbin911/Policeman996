package com.example.policeman996.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.policeman996.Constants;
import com.example.policeman996.R;
import com.example.policeman996.Utils.DateTimeUtil;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.presenter.Interface.IDailyCheckInPageView;
import com.example.policeman996.presenter.DailyCheckInPagePresenter;
import com.example.policeman996.view.RefreshLayout;
import com.orhanobut.hawk.Hawk;
import com.sackcentury.shinebuttonlib.ShineButton;
import es.dmoral.toasty.Toasty;

public class DailyCheckInPageFragment extends Fragment implements IDailyCheckInPageView {

  private DailyCheckInPagePresenter mPresenter;

  @BindView(R.id.on_work_btn)
  ShineButton mOnWorkButton;

  @BindView(R.id.off_work_btn)
  ShineButton mOffWorkButton;

  @BindView(R.id.check_layout)
  LinearLayout mCheckLayout;

  @BindView(R.id.check_refresh_layout)
  RefreshLayout mRefreshLayout;

  @BindView(R.id.on_work_time)
  TextView mOnworkTime;

  @BindView(R.id.off_work_time)
  TextView mOffWorkTime;

  @BindView(R.id.company_name)
  TextView mCompanyName;

  @BindView(R.id.tv_time)
  TextClock mClock;

  @BindView(R.id.edit)
  ImageView mEdit;

  private OnClickListener mListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.on_work_btn:
          mPresenter.onWork();
          break;
        case R.id.off_work_btn:
          mPresenter.offWork();
          break;
        case R.id.refresh_icon:
          mPresenter.checkStatus();
          break;
        case R.id.edit:
          JumpUtils.startUserActivity(getContext());
          break;
      }
    }
  };

  Unbinder unbinder;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.daily_check_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    initView();

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (Hawk.get(Constants.COMPANY_NAME, "").equals("")) {
      mCompanyName.setText("No Company Information");
    } else {
      mCompanyName.setText(Hawk.get(Constants.COMPANY_NAME, ""));
    }
    mPresenter.checkStatus();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void initView() {

    mPresenter = new DailyCheckInPagePresenter(this);
    mOffWorkButton.setOnClickListener(mListener);
    mOnWorkButton.setOnClickListener(mListener);
    mRefreshLayout.setOnClick(mListener);
    mEdit.setOnClickListener(mListener);
    mRefreshLayout.setVisibility(View.GONE);
    mCheckLayout.setVisibility(View.GONE);
    mOnworkTime.setVisibility(View.GONE);
    mOffWorkTime.setVisibility(View.GONE);

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onWork(boolean result, String code) {
    if (result) {
      Hawk.put(Constants.ON_WORK_TIME, DateTimeUtil.getHourAndMinute());
      setCheckStatus(1);
      if (getContext() != null) {
        Toasty.info(getContext(), "Have A Nice Day At Work!", Toast.LENGTH_SHORT, true).show();
      }
      mPresenter.checkStatus();

    } else {
      setCheckStatus(0);
      ResponseUtils.handleErrorText(getContext(), code);
    }
  }

  @Override
  public void offWork(boolean result, String code) {
    if (result) {
      Hawk.put(Constants.OFF_WORK_TIME, DateTimeUtil.getHourAndMinute());
      setCheckStatus(2);
      if (getContext() != null) {
        Toasty.info(getContext(), "Have A Good Rest!", Toast.LENGTH_SHORT, true).show();
      }
      mPresenter.checkStatus();
    } else {
      setCheckStatus(1);
      ResponseUtils.handleErrorText(getContext(), code);
    }
  }

  @Override
  public void setStatus(int status) {
    setCheckStatus(status);
    mCheckLayout.setVisibility(View.VISIBLE);
    mRefreshLayout.setVisibility(View.GONE);
  }

  @Override
  public void setStatusFail(String code) {
    mRefreshLayout.reset();
    ResponseUtils.handleErrorText(getContext(), code);
    mRefreshLayout.setVisibility(View.VISIBLE);
    mCheckLayout.setVisibility(View.GONE);
  }

  private void setCheckStatus(int status) {
    switch (status) {
      case 0:
        mOnWorkButton.setChecked(false);
        mOnWorkButton.setClickable(true);
        mOffWorkButton.setClickable(false);
        mOffWorkButton.setChecked(false);
        Hawk.put(Constants.ON_WORK_TIME, "");
        Hawk.put(Constants.OFF_WORK_TIME, "");
        mOffWorkTime.setVisibility(View.GONE);
        mOnworkTime.setVisibility(View.GONE);
        break;
      case 1:
        mOnWorkButton.setChecked(true);
        mOnWorkButton.setClickable(false);
        mOffWorkButton.setClickable(true);
        mOffWorkButton.setChecked(false);
        Hawk.put(Constants.OFF_WORK_TIME, "");

        mOnworkTime.setVisibility(View.VISIBLE);
        mOffWorkTime.setVisibility(View.GONE);
        if (Hawk.get(Constants.ON_WORK_TIME, "").equals("")) {
          mOnworkTime.setVisibility(View.GONE);
        } else {
          mOnworkTime.setText(Hawk.get(Constants.ON_WORK_TIME, ""));
        }
        break;
      case 2:
        mOnWorkButton.setChecked(true);
        mOnWorkButton.setClickable(false);
        mOffWorkButton.setClickable(false);
        mOffWorkButton.setChecked(true);

        mOnworkTime.setVisibility(View.VISIBLE);
        mOffWorkTime.setVisibility(View.VISIBLE);
        if (Hawk.get(Constants.OFF_WORK_TIME, "").equals("")) {
          mOffWorkTime.setVisibility(View.GONE);
        } else {
          mOffWorkTime.setText(Hawk.get(Constants.OFF_WORK_TIME, ""));
        }
        break;
    }
  }
}
