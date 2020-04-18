package com.example.policeman996.activity;

import android.view.Window;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.policeman996.R;
import com.example.policeman996.Utils.JumpUtils;
import com.example.policeman996.presenter.Interface.IWelcomeView;
import com.example.policeman996.presenter.StartPresenter;
import com.orhanobut.hawk.Hawk;

public class WelcomeActivity extends AppCompatActivity implements IWelcomeView {

  private StartPresenter mPresenter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Hawk.init(this).build();
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.welcome_activity_layout);
    mPresenter = new StartPresenter(this);

    mPresenter.autoLogin();


  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void jumpToLogin() {
    JumpUtils.startLoginActivity(this);
  }

  @Override
  public void jumpToMain() {
    JumpUtils.startMainPagerActivity(this);
  }

  @Override
  public void closeActivity() {
    finish();
  }
}