package com.edsys.framework.exception;

public class BDException
  extends ActionException
{
  private static final long serialVersionUID = 1L;
  
  public BDException() {}
  
  public BDException(Exception exception)
  {
    super(exception);
    this.message = exception.getMessage();
  }
  
  public BDException(String message)
  {
    super(message);
    this.message = message;
  }
}
