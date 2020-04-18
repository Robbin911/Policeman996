package com.example.policeman996.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.example.policeman996.Constants;
import com.example.policeman996.R;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.Utils.ResponseUtils;
import com.example.policeman996.fragments.CompanyListFragment;
import com.example.policeman996.fragments.DailyCheckInPageFragment;
import com.example.policeman996.presenter.Interface.IMainPageView;
import com.example.policeman996.presenter.MainPresenter;
import com.example.policeman996.view.BottomBarView;
import com.example.policeman996.view.HorizontalPosition;
import com.example.policeman996.view.SmartPopWindow;
import com.example.policeman996.view.SurveyView;
import com.example.policeman996.view.TopBarView;
import com.example.policeman996.view.VerticalPosition;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.List;

public class MainPagerActivity extends AppCompatActivity implements IMainPageView {

  private ViewPager mViewPager;
  private FragmentPagerAdapter mAdapter;
  private List<Fragment> mFragments;

  private DrawerLayout mDrawer;

  private LinearLayout mEditCompany;
  private LinearLayout mSurvey;
  private CardView mLogout;

  private TopBarView mTopBar;
  private BottomBarView mBottomBar;


  private MainPresenter mPresenter;

  private SurveyView mSurveyView;
  private SmartPopWindow mPopwin;


  OnClickListener mOnCLickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.tab_check:
          selectTab(0);
          break;
        case R.id.tab_company:
          selectTab(1);
          break;
        case R.id.close_btn:
          if (mPopwin != null) {
            mPopwin.dismiss();
          }
          break;
        case R.id.btn_submit:
          if (mSurveyView != null) {
            mPresenter.submitSurvey(mSurveyView.getScore());
          }
          break;
        case R.id.iv_user:
          mDrawer.openDrawer(Gravity.LEFT);
          break;
        case R.id.tv_edit_company:
          JumpUtils.startUserActivity(MainPagerActivity.this);
          break;
        case R.id.tv_survey:
          mDrawer.closeDrawer(Gravity.LEFT);
          openSurvey();
          break;
        case R.id.btn_logout:
          Hawk.put(Constants.ACCOUNT,"");
          Hawk.put(Constants.PASSWORD,"");
          Hawk.put(Constants.TOKEN,"");
          JumpUtils.startLoginActivity(MainPagerActivity.this);
          break;
      }
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    initViews();
    initFragments();
    initClickEvents();
    initPopwin();
    selectTab(0);

  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.checkCompany();
  }

  private void initFragments() {
    mFragments = new ArrayList<>();
    mFragments.add(new DailyCheckInPageFragment());
    mFragments.add(new CompanyListFragment(mTopBar));

    mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return mFragments.get(position);
      }

      @Override
      public int getCount() {
        return mFragments.size();
      }

    };
    mViewPager.setAdapter(mAdapter);
    mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
        selectTab(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  private void initClickEvents() {
    mEditCompany.setOnClickListener(mOnCLickListener);
    mSurvey.setOnClickListener(mOnCLickListener);
    mLogout.setOnClickListener(mOnCLickListener);

    if (mOnCLickListener != null) {
      if (mTopBar != null) {
        mTopBar.setOnUserClick(mOnCLickListener);
      }
      if (mBottomBar != null) {
        mBottomBar.setOnClick(mOnCLickListener);
      }
    }
  }

  //初始化控件
  private void initViews() {
    mViewPager = findViewById(R.id.view_pager);
    mTopBar = findViewById(R.id.top_bar);
    mBottomBar = findViewById(R.id.bottom_bar);
    mLogout = findViewById(R.id.btn_logout);
    mEditCompany= findViewById(R.id.tv_edit_company);
    mSurvey = findViewById(R.id.tv_survey);

    mDrawer = (DrawerLayout) findViewById(R.id.drawer);

    mPresenter = new MainPresenter(this);

  }

  private void selectTab(int i) {
    if (mViewPager != null) {
      mTopBar.setTitle(i);
      mBottomBar.setSelect(i);
      mViewPager.setCurrentItem(i);
    }
  }


  public void initPopwin() {
    mSurveyView = new SurveyView(this);
    mSurveyView.setOnClick(mOnCLickListener);
    mPopwin = SmartPopWindow.Builder
        .build(this, mSurveyView)
        .setOutsideTouchDismiss(true)
        .createPopupWindow();

  }

  public void openSurvey() {
    if (mSurveyView == null) {
      initPopwin();
    }
    View anchorView = mBottomBar;
    try {
      mPopwin
          .showAtAnchorView(anchorView,
              VerticalPosition.ABOVE,
              HorizontalPosition.CENTER, 0, 60);
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
  public void checkCompanySuccess() {
    mPresenter.checkSurvey();
  }

  @Override
  public void showSetCompany() {
    JumpUtils.startUserActivity(this);
  }

  @Override
  public void showSurvey() {
    openSurvey();
  }

  @Override
  public void requestFail(String code) {
    ResponseUtils.handleErrorText(this, code);
  }

  @Override
  public void closeSurvey() {
    if (mPopwin != null) {
      mPopwin.dismiss();
    }
  }
}
