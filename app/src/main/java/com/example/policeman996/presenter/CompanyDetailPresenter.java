package com.example.policeman996.presenter;

import com.example.policeman996.model.bean.CompanyDetailData;
import com.example.policeman996.model.bean.body.IdBody;
import com.example.policeman996.model.https.BaseResponse;
import com.example.policeman996.model.https.RetrofitManager;
import com.example.policeman996.presenter.Interface.ICompanyDetailPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompanyDetailPresenter {

  ICompanyDetailPageView mView;

  public CompanyDetailPresenter(ICompanyDetailPageView view){
    mView = view;
  }

  public void getData(long id){
    IdBody body = new IdBody();
    body.companyId = id;
    RetrofitManager.getInstance().getAppApis().getCompanyInfo(body)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<CompanyDetailData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<CompanyDetailData> companyDetailDataBaseResponse) {
        if(companyDetailDataBaseResponse.getData()!=null){
          mView.getData(companyDetailDataBaseResponse.getData());
        }else{
          mView.requestFail(companyDetailDataBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {

        CompanyDetailData detailData = new CompanyDetailData();
        detailData.setOffWorkTimeMonth(70*60);
        detailData.setOffWorkTimeWeek(117*60);
        detailData.setStartWorkTimeMonth(540 *60);
        detailData.setStartWorkTimeWeek(698*60);
        detailData.setTotalRate(99);
        detailData.setWelfare(78);
        detailData.setWorkOvertime(60);
        mView.getData(detailData);

        //mView.requestFail("");
      }

      @Override
      public void onComplete() {

      }
    });
  }

}
