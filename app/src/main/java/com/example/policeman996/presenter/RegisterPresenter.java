package com.example.policeman996.presenter;

import com.example.policeman996.Constants;
import com.example.policeman996.model.bean.body.RegisterBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.IRegisterView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter {

  private IRegisterView mView;

  public RegisterPresenter(IRegisterView view) {
    mView = view;
  }

  public void register(String username, String pw) {
    RegisterBody body = new RegisterBody();
    body.pw = pw;
    body.username = username;
    RetrofitManager.getInstance().getAppApis().register(body)
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
          mView.onRegisterSuccess();

        } else {
          mView.onRegisterFail(stringBaseResponse.getErrorCode());
        }

      }

      @Override
      public void onError(Throwable e) {
        mView.onRegisterFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
