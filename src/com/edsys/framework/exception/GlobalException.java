package com.edsys.framework.exception;

import java.io.Serializable;

public class GlobalException
  extends Exception
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected String message;
  
  public GlobalException() {}
  
  public GlobalException(Exception exception)
  {
    super(exception);
    initCause(exception);
    this.message = exception.getMessage();
  }
  
  public GlobalException(String message)
  {
    super(message);
    this.message = message;
  }
  
  public String getMessage()
  {
    return this.message;
  }
}
