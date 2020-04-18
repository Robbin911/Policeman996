package com.example.policeman996.presenter;

import com.example.policeman996.Constants;
import com.example.policeman996.model.bean.CheckCompanyData;
import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.model.bean.CompanyListData;
import com.example.policeman996.model.bean.body.CompanyListBody;
import com.example.policeman996.model.bean.body.IdBody;
import com.example.policeman996.model.bean.body.SubmitCompanyBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.IUserPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class UserPresenter {


  private IUserPageView mView;

  public UserPresenter(IUserPageView view) {
    mView = view;
  }

  public void submitCompany(String name,String place) {
    SubmitCompanyBody body = new SubmitCompanyBody();
    body.companyName = name;
    body.workshop = place;
    RetrofitManager.getInstance().getAppApis().submitNewCompany(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          mView.requestSuccess(0);
        } else{
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

  public void changeCompany(long id){
    IdBody body = new IdBody();
    body.companyId = id;
    RetrofitManager.getInstance().getAppApis().modifyCompany(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<String>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<String> stringBaseResponse) {
        if (stringBaseResponse.getData() != null) {
          mView.requestSuccess(1);
        } else{
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
  public void getData(String key, int page, int size) {
    CompanyListBody body = new CompanyListBody();
    body.page = page;
    body.search = key;
    body.size = size;
    RetrofitManager.getInstance().getAppApis().getCompanyList(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<List<CompanyData>>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<List<CompanyData>> companyListDataBaseResponse) {
        if (companyListDataBaseResponse.getData() != null) {
          mView.updateList(companyListDataBaseResponse.getData());
        } else {
          mView.requestFail(companyListDataBaseResponse.getErrorCode());
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
