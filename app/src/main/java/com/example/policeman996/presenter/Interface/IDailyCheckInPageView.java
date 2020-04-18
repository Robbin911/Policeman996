package com.example.policeman996.presenter.Interface;

import java.util.List;

public interface IDailyCheckInPageView {

  void onWork(boolean result, String code);

  void offWork(boolean result, String code);

  void setStatus(int status);

  void setStatusFail(String code);
}
