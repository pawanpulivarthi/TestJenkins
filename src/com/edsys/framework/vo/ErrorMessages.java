package com.edsys.framework.vo;

import java.util.ArrayList;

public class ErrorMessages
  extends BaseVO
{
  private static final long serialVersionUID = 1L;
  ArrayList<ErrorMessage> errorMessages = new ArrayList();
  
  public void addErrorMessage(ErrorMessage errorMessage)
  {
    this.errorMessages.add(errorMessage);
  }
  
  public void addErrorMessage(ErrorMessage errorMessage, String... args)
  {
    errorMessage.setArgs(args);
    this.errorMessages.add(errorMessage);
  }
  
  public ArrayList<ErrorMessage> getErrorMessages()
  {
    return this.errorMessages;
  }
  
  public void setErrorMessages(ArrayList<ErrorMessage> errorMessages)
  {
    this.errorMessages = errorMessages;
  }
}
