package com.example.policeman996.presenter;

import com.example.policeman996.model.bean.body.CheckBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.BasePresenter;
import com.example.policeman996.presenter.Interface.IDailyCheckInPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DailyCheckInPagePresenter {

  private IDailyCheckInPageView mView;

  public DailyCheckInPagePresenter(IDailyCheckInPageView view) {
    mView = view;
  }


  public void onWork() {
    CheckBody body = new CheckBody();
    body.time = System.currentTimeMillis();
    RetrofitManager.getInstance().getAppApis().checkin(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          mView.onWork(true, "");
        } else {
          mView.onWork(false, stringBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.onWork(false, "");
      }

      @Override
      public void onComplete() {

      }
    });
  }


  public void offWork() {
    CheckBody body = new CheckBody();
    body.time = System.currentTimeMillis();
    RetrofitManager.getInstance().getAppApis().checkout(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          mView.offWork(true, "");
        } else {
          mView.offWork(false, stringBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.offWork(false, "");
      }

      @Override
      public void onComplete() {

      }
    });
  }


  public void checkStatus() {
    RetrofitManager.getInstance().getAppApis().checkStatus()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<Integer>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<Integer> integerBaseResponse) {
        if (integerBaseResponse.getData() != null) {
          mView.setStatus(integerBaseResponse.getData());
        } else {
          mView.setStatusFail(integerBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.setStatusFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }
}
