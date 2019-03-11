package com.edsys.framework.exception;

public class ActionException
  extends GlobalException
{
  private static final long serialVersionUID = 1L;
  
  public ActionException() {}
  
  public ActionException(Exception exception)
  {
    super(exception);
    this.message = exception.getMessage();
  }
  
  public ActionException(String message)
  {
    super(message);
    this.message = message;
  }
}
