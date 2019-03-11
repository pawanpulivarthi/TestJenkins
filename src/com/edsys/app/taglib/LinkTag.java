package com.edsys.app.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class LinkTag
  extends BodyTagSupport
{
  private static final long serialVersionUID = 6302621615800769683L;
  private String href;
  private String styleClass;
  private String onclick;
  private int component;
  private String access;
  private String parentTag;
  private String parentId;
  
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
    if (this.parentTag != null)
    {
      buffer.append("<");
      buffer.append(this.parentTag);
      if (this.parentId != null) {
        buffer.append(" id=" + this.parentId);
      }
      buffer.append(">");
    }
    buffer.append("<a");
    buffer.append(getProperty("href", this.href));
    buffer.append(getProperty("class", this.styleClass));
    buffer.append(getProperty("onclick", this.onclick));
    buffer.append(">");
    buffer.append(body);
    buffer.append("</a>");
    if (this.parentTag != null)
    {
      buffer.append("</");
      buffer.append(this.parentTag);
      buffer.append(">");
    }
    return buffer.toString();
  }
  
  private String getProperty(String propertyName, String propertyValue)
  {
    if ((propertyValue != null) && (propertyValue.trim().length() > 0)) {
      return " " + propertyName + "=\"" + propertyValue + "\"";
    }
    return "";
  }
  
  public void setHref(String href)
  {
    this.href = href;
  }
  
  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }
  
  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }
  
  public void setComponent(int component)
  {
    this.component = component;
  }
  
  public void setAccess(String access)
  {
    this.access = access;
  }
  
  public void setParentTag(String parentTag)
  {
    this.parentTag = parentTag;
  }
  
  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }
}
