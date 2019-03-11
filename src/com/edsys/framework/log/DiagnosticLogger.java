package com.edsys.framework.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DiagnosticLogger
{
  public static DiagnosticLogger getLogger(Class<?> targetClass)
  {
    return new DiagnosticLogger(targetClass);
  }
  
  private Log log = null;
  
  public DiagnosticLogger(Class<?> targetClass)
  {
    this.log = LogFactory.getLog(targetClass);
  }
  
  public void debug(Exception exception)
  {
    if (exception != null) {
      this.log.debug(exception.getMessage(), exception);
    }
  }
  
  public void debug(String message)
  {
    this.log.debug(message);
  }
  
  public void error(Exception exception)
  {
    if (exception != null) {
      this.log.error(exception.getMessage(), exception);
    }
  }
  
  public void error(String errorMessage)
  {
    this.log.error(errorMessage);
  }
  
  public void fatal(Exception exception)
  {
    if (exception != null) {
      this.log.fatal(exception.getMessage(), exception);
    }
  }
  
  public void info(String message)
  {
    this.log.info(message);
  }
  
  public void trace(String message)
  {
    this.log.trace(message);
  }
  
  public void warn(String message)
  {
    this.log.warn(message);
  }
}
