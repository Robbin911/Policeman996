package com.example.policeman996.model.https;

import com.example.policeman996.model.bean.CheckCompanyData;
import com.example.policeman996.model.bean.CompanyData;
import com.example.policeman996.model.bean.body.CheckBody;
import com.example.policeman996.model.bean.CompanyDetailData;
import com.example.policeman996.model.bean.CompanyListData;
import com.example.policeman996.model.bean.body.CompanyListBody;
import com.example.policeman996.model.bean.body.IdBody;
import com.example.policeman996.model.bean.body.RegisterBody;
import com.example.policeman996.model.bean.body.SubmitCompanyBody;
import com.example.policeman996.model.bean.body.WelfareBody;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author quchao
 * @date 2018/2/12
 */

public interface AppApis {

  String HOST = "http://118.178.181.104:8080/";

  /**
   * 上报上工时间
   */

  @POST("/checkin")
  Observable<BaseResponse<String>> checkin(@Body CheckBody body);

  /**
   * 获取公司列表
   */
  @POST("/getCompanyList")
  Observable<BaseResponse<List<CompanyData>>> getCompanyList(@Body CompanyListBody body);

  /**
   * 获取公司详情
   */
  @POST("/getCompanyInfo")
  Observable<BaseResponse<CompanyDetailData>> getCompanyInfo(@Body IdBody body);


  /**
   * 上报下工时间
   */

  @POST("/checkout")
  Observable<BaseResponse<String>> checkout(@Body CheckBody body);


  /**
   * 检查用户公司信息
   */
  @POST("/checkcom")
  Observable<BaseResponse<CheckCompanyData>> checkCompany();

  /**
   * 登陆
   */
  @POST("/login")
  Observable<BaseResponse<String>> login(@Body RegisterBody body);

  /**
   * 修改公司信息
   */
  @POST("/modifycom")
  Observable<BaseResponse<String>> modifyCompany(@Body IdBody body);

  /**
   * 创建新公司信息
   */
  @POST("/submitnewcom")
  Observable<BaseResponse<String>> submitNewCompany(@Body SubmitCompanyBody body);

  /**
   * 注册
   */
  @POST("/register")
  Observable<BaseResponse<String>> register(@Body RegisterBody body);

  /**
   * 每月调查
   */
  @POST("/survey")
  Observable<BaseResponse<String>> survey(@Body WelfareBody body);


  @POST("/checkcheck")
  Observable<BaseResponse<Integer>> checkStatus();


  @POST("/checksurvey")
  Observable<BaseResponse<Boolean>> checkSurvey();

}
