package com.example.policeman996.presenter.Interface;

import com.example.policeman996.model.bean.CompanyDetailData;

public interface ICompanyDetailPageView {

  void getData(CompanyDetailData detailData);
  void requestFail(String code);

}
