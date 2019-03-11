package com.edsys.framework.config;

import java.io.Serializable;

public class Parameter
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String _name;
  private String _value;
  
  public String getName()
  {
    return this._name;
  }
  
  public String getValue()
  {
    return this._value;
  }
  
  public void setName(String name)
  {
    this._name = name;
  }
  
  public void setValue(String value)
  {
    this._value = value;
  }
}
