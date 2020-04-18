package com.example.policeman996.Utils;

import android.content.Context;
import android.content.Intent;
import com.example.policeman996.Constants;
import com.example.policeman996.activity.CompanyDetailActivity;
import com.example.policeman996.activity.LoginActivity;
import com.example.policeman996.activity.MainPagerActivity;
import com.example.policeman996.activity.RegisterActivity;
import com.example.policeman996.activity.UserActivity;
import com.example.policeman996.model.bean.CompanyData;

public class JumpUtils {

  public static void startCompanyDetailActivity(Context mActivity, CompanyData data) {

    Intent intent = new Intent(mActivity, CompanyDetailActivity.class);
    intent.putExtra(Constants.COMPANY_DETAIL_NAME, data.getCompanyName());
    intent.putExtra(Constants.COMPANY_DETAIL_ID, data.getCompanyId());
    intent.putExtra(Constants.COMPANY_DETAIL_RATE, data.getRate());
    mActivity.startActivity(intent);

  }

  public static void startLoginActivity(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }


  public static void startRegisterActivity(Context context) {
    Intent intent = new Intent(context, RegisterActivity.class);
    context.startActivity(intent);
  }


  public static void startMainPagerActivity(Context context) {
    Intent intent = new Intent(context, MainPagerActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }


  public static void startUserActivity(Context context) {
    Intent intent = new Intent(context, UserActivity.class);
    context.startActivity(intent);
  }



}
