package com.example.policeman996.presenter;

import com.example.policeman996.Constants;
import com.example.policeman996.model.bean.body.RegisterBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.IWelcomeView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import android.os.Handler;

public class StartPresenter {

  private IWelcomeView mWelcomeView;
  private Handler mHandler;

  public StartPresenter(IWelcomeView view) {
    mWelcomeView = view;
    mHandler = new Handler();
  }

  public void autoLogin() {
    String account = Hawk.get(Constants.ACCOUNT, "");
    String pw = Hawk.get(Constants.PASSWORD, "");
    if (account.equals("") || pw.equals("")) {
      mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
          mWelcomeView.jumpToLogin();
          mWelcomeView.closeActivity();
        }
      }, 2000);
      return;
    }

    RegisterBody body = new RegisterBody();
    body.pw = pw;
    body.username = account;

    RetrofitManager.getInstance().getAppApis().login(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          Hawk.put(Constants.TOKEN, stringBaseResponse.getData());
          mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
              mWelcomeView.jumpToMain();
            }
          }, 2000);
        } else {
          mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
              mWelcomeView.jumpToLogin();
              mWelcomeView.closeActivity();
            }
          }, 2000);
        }

      }

      @Override
      public void onError(Throwable e) {
        mHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
            mWelcomeView.jumpToLogin();
            mWelcomeView.closeActivity();
          }
        }, 2000);
      }

      @Override
      public void onComplete() {

      }
    });
  }
}
