package com.example.policeman996.presenter.Interface;

public interface IMainPageView {

  void checkCompanySuccess();
  void showSetCompany();
  void showSurvey();

  void requestFail(String code);
  void closeSurvey();

}
