package com.edsys.framework.vo;

import java.io.Serializable;

public abstract class BaseVO
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  private boolean active = true;
  private int createdBy;
  private String createdDate;
  private int modifiedBy;
  private String modifiedDate;
  
  public boolean isActive()
  {
    return this.active;
  }
  
  public void setActive(boolean active)
  {
    this.active = active;
  }
  
  public String getCreatedDate()
  {
    return this.createdDate;
  }
  
  public void setCreatedDate(String createdDate)
  {
    this.createdDate = createdDate;
  }
  
  public String getModifiedDate()
  {
    return this.modifiedDate;
  }
  
  public void setModifiedDate(String modifiedDate)
  {
    this.modifiedDate = modifiedDate;
  }
  
  public void setCreatedBy(int createdBy)
  {
    this.createdBy = createdBy;
  }
  
  public int getCreatedBy()
  {
    return this.createdBy;
  }
  
  public void setModifiedBy(int modifiedBy)
  {
    this.modifiedBy = modifiedBy;
  }
  
  public int getModifiedBy()
  {
    return this.modifiedBy;
  }
}
