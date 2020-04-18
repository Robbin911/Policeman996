package com.example.policeman996.presenter;

import com.example.policeman996.Constants;
import com.example.policeman996.model.bean.body.RegisterBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.ILoginPageView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter {

  private ILoginPageView mView;

  public LoginPresenter(ILoginPageView view) {
    mView = view;
  }

  public void login(String username, String pw) {
    RegisterBody body = new RegisterBody();
    body.pw = pw;
    body.username = username;
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
          Hawk.put(Constants.ACCOUNT, username);
          Hawk.put(Constants.PASSWORD, pw);
          mView.onLoginSuccess();
        } else{
          mView.onLoginFail(stringBaseResponse.getErrorCode());
        }

      }

      @Override
      public void onError(Throwable e) {
        mView.onLoginFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
