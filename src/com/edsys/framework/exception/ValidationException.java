package com.edsys.framework.exception;

import com.edsys.framework.vo.ErrorMessage;
import com.edsys.framework.vo.ErrorMessages;

public class ValidationException
  extends Exception
{
  private static final long serialVersionUID = -3951519729234714282L;
  private ErrorMessages errorMessages = null;
  
  public ValidationException()
  {
    this.errorMessages = new ErrorMessages();
  }
  
  public ValidationException(ErrorMessages errorMessages)
  {
    this.errorMessages = errorMessages;
  }
  
  public ValidationException(ErrorMessage errorMessage)
  {
    this.errorMessages = new ErrorMessages();
    this.errorMessages.addErrorMessage(errorMessage);
  }
  
  public ErrorMessages getMessages()
  {
    return this.errorMessages;
  }
}
