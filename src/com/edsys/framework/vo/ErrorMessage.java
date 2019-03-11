package com.edsys.framework.vo;

public class ErrorMessage
  extends BaseVO
{
  private static final long serialVersionUID = 6434888758456941869L;
  private String key;
  private String[] args;
  
  public ErrorMessage(String key)
  {
    this.key = key;
  }
  
  public ErrorMessage(String key, String... args)
  {
    this.key = key;
    this.args = args;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String[] getArgs()
  {
    return this.args;
  }
  
  public void setArgs(String... args)
  {
    this.args = args;
  }
}
