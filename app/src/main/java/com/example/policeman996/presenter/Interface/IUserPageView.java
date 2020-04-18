package com.example.policeman996.presenter.Interface;

import com.example.policeman996.model.bean.CompanyData;
import java.util.List;

public interface IUserPageView {

  void updateList(List<CompanyData> data);
  void requestFail(String code);
  void requestSuccess(int type);

  void selectCompany(CompanyData data);

}
