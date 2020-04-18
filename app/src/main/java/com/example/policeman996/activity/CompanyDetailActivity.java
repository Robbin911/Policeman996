package com.example.policeman996.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.policeman996.Constants;
import com.example.policeman996.R;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.model.bean.CompanyDetailData;
import com.example.policeman996.presenter.CompanyDetailPresenter;
import com.example.policeman996.presenter.Interface.ICompanyDetailPageView;
import com.example.policeman996.view.RefreshLayout;
import es.dmoral.toasty.Toasty;

public class CompanyDetailActivity extends AppCompatActivity implements ICompanyDetailPageView {

  @BindView(R.id.company_rate)
  TextView mCompanyRate;
  @BindView(R.id.btn_week)
  CardView mWeekButton;
  @BindView(R.id.btn_month)
  CardView mMonthButton;
  @BindView(R.id.on_work_time)
  TextView mOnWorkTime;
  @BindView(R.id.off_work_time)
  TextView mOffWorkTime;
  @BindView(R.id.overtime_percentage)
  TextView mOvertime;
  @BindView(R.id.welfare_score)
  TextView mWelfare;
  @BindView(R.id.back_btn)
  ImageView mBackBtn;
  @BindView(R.id.company_name_title)
  TextView mCompanyNameTitle;
  @BindView(R.id.company_detail_refresh)
  RefreshLayout mRefresh;
  @BindView(R.id.content_layout)
  RelativeLayout mContent;

  private String mCompanyName;
  private long mCompanyId;
  private int mRate;

  private CompanyDetailData mData;

  private CompanyDetailPresenter mPresenter;

  private OnClickListener mListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.back_btn:
          finish();
          break;
        case R.id.refresh_icon:
          mPresenter.getData(mCompanyId);
          break;
        case R.id.btn_week:
          clickWeek();
          break;
        case R.id.btn_month:
          clickMonth();
          break;
      }
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.getData(mCompanyId);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.company_detail_layout);
    ButterKnife.bind(this);

    mPresenter = new CompanyDetailPresenter(this);
    mBackBtn.setOnClickListener(mListener);
    mRefresh.setOnClick(mListener);
    mWeekButton.setOnClickListener(mListener);
    mMonthButton.setOnClickListener(mListener);

    initView();
  }

  private void initView() {

    mRefresh.setVisibility(View.GONE);
    mContent.setVisibility(View.GONE);

    mCompanyName = getIntent().getStringExtra(Constants.COMPANY_DETAIL_NAME);
    mCompanyId = getIntent().getLongExtra(Constants.COMPANY_DETAIL_ID, 0);
    if (mCompanyId == 0) {
      Toasty.error(this, "Unexpected Error").show();
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          finish();
        }
      }, 2000);
    }
    mRate = getIntent().getIntExtra(Constants.COMPANY_DETAIL_RATE, -1);
    mCompanyNameTitle.setText(mCompanyName);

  }


  @Override
  public void getData(CompanyDetailData detailData) {
    mData = detailData;
    mRefresh.setVisibility(View.GONE);
    mContent.setVisibility(View.VISIBLE);

    mCompanyRate.setText(mData.getTotalRate() + "");
    if (mData.getTotalRate() < 40) {
      mCompanyRate.setTextColor(getResources().getColor(R.color.colorRed));
    } else if (mData.getTotalRate() >= 40 && mData.getTotalRate() < 70) {
      mCompanyRate.setTextColor(getResources().getColor(R.color.colorYellow));
    } else if (mData.getTotalRate() >= 70) {
      mCompanyRate.setTextColor(getResources().getColor(R.color.colorGreen));
    }
    clickWeek();
    mOvertime.setText(mData.getWorkOvertime() + "%");
    mWelfare.setText(mData.getWelfare() + "");
  }

  @Override
  public void requestFail(String code) {
    mRefresh.reset();
    ResponseUtils.handleErrorText(this, code);
    mRefresh.setVisibility(View.VISIBLE);
    mContent.setVisibility(View.GONE);
  }


  private boolean dataCheck() {
    if (mData == null) {
      mContent.setVisibility(View.GONE);
      mRefresh.setVisibility(View.VISIBLE);
      mRefresh.reset();
      return true;
    }
    return false;
  }

  private void clickWeek() {
    if (dataCheck()) {
      return;
    }
    mWeekButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
    mMonthButton.setCardBackgroundColor(getResources().getColor(R.color.colorGray));
    mOnWorkTime.setText(convertTime(mData.getStartWorkTimeWeek()));
    mOffWorkTime.setText(convertTime(mData.getOffWorkTimeWeek()));
  }

  private void clickMonth() {
    if (dataCheck()) {
      return;
    }
    mWeekButton.setCardBackgroundColor(getResources().getColor(R.color.colorGray));
    mMonthButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
    mOnWorkTime.setText(convertTime(mData.getStartWorkTimeMonth()));
    mOffWorkTime.setText(convertTime(mData.getOffWorkTimeMonth()));
  }

  private String convertTime(int time) {
    String result = "";
    int seconds = time / 60;
    int hour = seconds / 60;
    if (hour < 10) {
      result = "0" + hour;
    } else {
      result = hour + "";
    }
    result = result + ":";
    int second = seconds - hour * 60;
    if (second < 10) {
      result = result + "0" + second;
    } else {
      result = result + second;
    }
    return result;

  }
}
