package com.edsys.app.common.service;

import com.edsys.app.common.dao.CommonDAO;

public class CommonService
  extends BaseService
{
  private final CommonDAO dao;
  
  public CommonService()
  {
    this.dao = new CommonDAO();
  }
}
