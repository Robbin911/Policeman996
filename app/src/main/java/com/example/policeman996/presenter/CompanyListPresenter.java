package com.example.policeman996.presenter;

import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.model.bean.CompanyListData;
import com.example.policeman996.model.bean.body.CompanyListBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.BasePresenter;
import com.example.policeman996.presenter.Interface.ICompanyListPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class CompanyListPresenter {

  private ICompanyListPageView mView;

  public CompanyListPresenter(ICompanyListPageView view) {
    mView = view;
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
          mView.requestError(companyListDataBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.requestError("");
      }

      @Override
      public void onComplete() {

      }
    });
  }

}
