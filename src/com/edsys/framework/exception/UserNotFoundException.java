package com.edsys.framework.exception;

public class UserNotFoundException
  extends Exception
{
  private static final long serialVersionUID = -6670527060605921013L;
  
  public UserNotFoundException() {}
  
  public UserNotFoundException(String arg0)
  {
    super(arg0);
  }
  
  public UserNotFoundException(Throwable arg0)
  {
    super(arg0);
  }
  
  public UserNotFoundException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
  }
}
