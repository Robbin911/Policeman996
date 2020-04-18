package com.example.policeman996.model.bean;

public class CompanyData {

  private long companyId;
  private String companyName;
  private int rate;

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public long getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }
}
