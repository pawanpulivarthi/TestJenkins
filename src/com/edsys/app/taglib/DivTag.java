package com.edsys.app.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DivTag
  extends BodyTagSupport
{
  private static final long serialVersionUID = 6302621615800769683L;
  private int component;
  private String access;
  
  public int doAfterBody()
    throws JspException
  {
    try
    {
      BodyContent bodycontent = getBodyContent();
      String body = bodycontent.getString();
      JspWriter out = bodycontent.getEnclosingWriter();
      
      HttpServletRequest req = (HttpServletRequest)this.pageContext.getRequest();
      
      HttpSession session = req.getSession(true);
      
      int access = 0;
      if (this.access != null) {
        if (this.access.equalsIgnoreCase("r")) {
          access = 1;
        } else if (this.access.equalsIgnoreCase("w")) {
          access = 2;
        }
      }
      if (body != null) {
        out.print(prepareTag(body));
      } else {
        out.print("");
      }
    }
    catch (IOException e)
    {
      throw new JspException(e.getMessage());
    }
    return 0;
  }
  
  private String prepareTag(String body)
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<div>");
    buffer.append(body);
    buffer.append("</div>");
    return buffer.toString();
  }
  
  public void setComponent(int component)
  {
    this.component = component;
  }
  
  public void setAccess(String access)
  {
    this.access = access;
  }
}
