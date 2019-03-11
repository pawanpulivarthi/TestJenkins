package com.edsys.framework.action;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.actions.TilesAction;

public class GroupHomeHelper
  extends TilesAction
  implements Controller
{
  private static final String ATTRIBUTE_HEADER = "header";
  private static final String ATTRIBUTE_FOOTER = "footer";
  private static final String ATTRIBUTE_BODY = "body";
  private static final String CORPORATE_HEADER = "/static/urlHeader.jsp";
  private static final String CORPORATE_FOOTER = "/static/corpFooter.jsp";
  private static final String GROUP_HOME = "/jsp/common/academyHomeGeneral.jsp";
  
  public void execute(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
    throws Exception
  {
    HttpSession session = request.getSession(true);
  }
  
  public final void perform(ComponentContext context, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
    throws ServletException, IOException
  {}
}
