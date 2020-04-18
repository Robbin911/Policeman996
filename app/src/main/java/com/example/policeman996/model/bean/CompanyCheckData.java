package com.example.policeman996.model.bean;

public class CompanyCheckData {


  private long companyId;
  private String companyName;
  private boolean valid;

  public boolean getRate() {
    return valid;
  }

  public void setRate(boolean rate) {
    this.valid = rate;
  }

  public long getComoanytId() {
    return companyId;
  }

  public String getCompanyname() {
    return companyName;
  }

  public void setCompanyname(String companyname) {
    this.companyName = companyname;
  }

  public void setComoanytId(long comoanytId) {
    this.companyId = comoanytId;
  }
}
