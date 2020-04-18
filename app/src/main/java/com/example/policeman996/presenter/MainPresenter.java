package com.example.policeman996.presenter;

import com.example.policeman996.Constants;
import com.example.policeman996.model.bean.CheckCompanyData;
import com.example.policeman996.model.bean.CompanyDetailData;
import com.example.policeman996.model.bean.body.WelfareBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.IMainPageView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

  private IMainPageView mView;

  public MainPresenter(IMainPageView view) {
    mView = view;
  }

  public void checkCompany() {
    RetrofitManager.getInstance().getAppApis().checkCompany()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<CheckCompanyData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<CheckCompanyData> checkCompanyDataBaseResponse) {
        if (checkCompanyDataBaseResponse.getData() != null) {
          if (checkCompanyDataBaseResponse.getData().isValid()) {
            Hawk.put(Constants.COMPANY_ID, checkCompanyDataBaseResponse.getData().getCompanyId());
            Hawk.put(Constants.COMPANY_NAME,
                checkCompanyDataBaseResponse.getData().getCompanyName());
            mView.checkCompanySuccess();
          } else {
            Hawk.put(Constants.COMPANY_ID, 0L);
            Hawk.put(Constants.COMPANY_NAME, "");
            mView.showSetCompany();
          }
        } else {
          mView.requestFail(checkCompanyDataBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.requestFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }

  public void checkSurvey() {
    RetrofitManager.getInstance().getAppApis().checkSurvey()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<Boolean>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<Boolean> booleanBaseResponse) {
        if (booleanBaseResponse.getData() != null) {
          if (booleanBaseResponse.getData()) {
            mView.showSurvey();
          }
        } else {
          mView.requestFail(booleanBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.requestFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }

  public void submitSurvey(int welfare) {
    WelfareBody body = new WelfareBody();
    body.welfare = welfare;
    RetrofitManager.getInstance().getAppApis().survey(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          mView.closeSurvey();
        } else {
          mView.requestFail(stringBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.requestFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }

}
