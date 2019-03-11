package com.edsys.app.report.vo;

import com.edsys.framework.vo.BaseVO;

public class ColumnHeaderVO
  extends BaseVO
{
  private static final long serialVersionUID = -8021278102171303187L;
  private int type;
  private String name;
  private String title;
  private String filter;
  
  public ColumnHeaderVO(String name, String title, int type)
  {
    this.type = type;
    this.name = name;
    this.title = title;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getFilter()
  {
    return this.filter;
  }
  
  public void setFilter(String filter)
  {
    this.filter = filter;
  }
}
