package com.example.policeman996.model.https;

public class BaseResponse<T> {

  public static final int SUCCESS = 0;
  public static final int FAIL = 1;

  public static final String InternalServerError = "InternalServerError";
  public static final String AuthError = "AuthError";
  public static final String ModifyColdDownError = "ModifyColdDownError";
  public static final String CompanyNotExistError = "CompanyNotExistError";
  public static final String RepeatMonthlySurveyError = "RepeatMonthlySurveyError";
  public static final String UsernameFormatError = "UsernameFormatError";
  public static final String PasswordFormatError = "PasswordFormatError";
  public static final String RequestFormatError = "RequestFormatError";
  public static final String UsernameExistError = "UsernameExistError";
  public static final String CompanyExistError = "CompanyExistError";

  /**
   * 0：成功，1：失败
   */
  private String errCode;

  private String errMsg;

  private T data;

  public String getErrorCode() {
    return errCode;
  }

  public void setErrorCode(String errorCode) {
    this.errCode = errorCode;
  }

  public String getErrorMsg() {
    return errMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errMsg = errorMsg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
