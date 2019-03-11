package com.edsys.framework.vo;

public class ActionResponseVO
  extends BaseVO
{
  private static final long serialVersionUID = 1L;
  private Object response;
  private Object responseHeader;
  private String responseType;
  
  public Object getResponse()
  {
    return this.response;
  }
  
  public void setResponse(Object response)
  {
    this.response = response;
  }
  
  public Object getResponseHeader()
  {
    return this.responseHeader;
  }
  
  public void setResponseHeader(Object responseHeader)
  {
    this.responseHeader = responseHeader;
  }
  
  public String getResponseType()
  {
    return this.responseType;
  }
  
  public void setResponseType(String responseType)
  {
    this.responseType = responseType;
  }
}
