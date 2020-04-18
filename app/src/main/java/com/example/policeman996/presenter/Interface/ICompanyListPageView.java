package com.example.policeman996.presenter.Interface;

import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.model.bean.CompanyListData;
import java.util.List;

public interface ICompanyListPageView {

  void updateList(List<CompanyData> data);
  void requestError(String msg);
}
