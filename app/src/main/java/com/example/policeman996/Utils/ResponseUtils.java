package com.example.policeman996.Utils;

import android.content.Context;
import android.widget.Toast;
import com.example.policeman996.model.https.BaseResponse;
import es.dmoral.toasty.Toasty;

public class ResponseUtils {


  public static void handleErrorText(Context context, String msg) {
    if(context == null){
      return;
    }
    String result = "Unexpected Error";
    switch (msg) {

      case BaseResponse.AuthError:
        result = "Invalid Login State";
        JumpUtils.startLoginActivity(context);
        break;
      case BaseResponse.CompanyNotExistError:
        result = "Company Not Exist";
        break;
      case BaseResponse.InternalServerError:
        result = "Internal Server Error";
        break;
      case BaseResponse.ModifyColdDownError:
        result = "Can't Modify Company Yet";
        break;
      case BaseResponse.RepeatMonthlySurveyError:
        result = "Repeat Monthly Survey";
        break;
      case BaseResponse.UsernameExistError:
        result = "User Name Exist";
        break;
      case BaseResponse.PasswordFormatError:
        result = "Illegal Password Format";
        break;
      case BaseResponse.RequestFormatError:
        result = "Illegal Request Format";
        break;
      case BaseResponse.CompanyExistError:
        result = "Company Exist";
        break;
      case BaseResponse.UsernameFormatError:
        result = "Illegal User Name Format";
        break;
    }
    Toasty.error(context, result, Toast.LENGTH_SHORT, true).show();
  }

}
