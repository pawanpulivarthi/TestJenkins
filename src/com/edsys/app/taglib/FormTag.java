package com.edsys.app.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public class FormTag
  extends org.apache.struts.taglib.html.FormTag
{
  private static final long serialVersionUID = 3183877882625083090L;
  
  public void setAction(String action)
  {
    HttpSession session = this.pageContext.getSession();
    String group = "";
    
    this.action = action;
  }
}
