package com.edsys.framework.exception;

public class DAOException
  extends ServiceException
{
  private static final long serialVersionUID = 1L;
  
  public DAOException() {}
  
  public DAOException(Exception exception)
  {
    super(exception);
    this.message = exception.getMessage();
  }
  
  public DAOException(String message)
  {
    super(message);
    this.message = message;
  }
}
