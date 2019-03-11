package com.edsys.framework.exception;

public class ServiceException
  extends BDException
{
  private static final long serialVersionUID = 1L;
  
  public ServiceException() {}
  
  public ServiceException(Exception exception)
  {
    super(exception);
    this.message = exception.getMessage();
  }
  
  public ServiceException(String message)
  {
    super(message);
    this.message = message;
  }
}
