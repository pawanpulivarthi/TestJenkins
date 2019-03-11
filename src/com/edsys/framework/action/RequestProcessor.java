package com.edsys.framework.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.tiles.TilesRequestProcessor;

import com.edsys.framework.log.DiagnosticLogger;

public class RequestProcessor
  extends TilesRequestProcessor
{
  private static DiagnosticLogger log = DiagnosticLogger.getLogger(RequestProcessor.class);
  private static final String LOGIN_PAGE = "/navigate/user/login";
  private static final String MLOGIN_PAGE = "/navigate/muser/login";
  private static final String SESSION_EXPIRED_PAGE = "/navigate/timeout/error";
  
  protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response)
  {
    log.info("Requested URL - " + request.getRequestURI());
    
    HttpSession session = request.getSession(true);
    String httpAccept;
    if (session.isNew())
    {
      String userAgent = request.getHeader("User-Agent");
      httpAccept = request.getHeader("Accept");
    }
    return isAuthorizedRequest(request, response);
  }
  
  private final boolean isAuthorizedRequest(HttpServletRequest request, HttpServletResponse response)
  {
    String requestURI = request.getRequestURI();
    
    HttpSession session = request.getSession(true);
    
    String redirectPage = "/navigate/user/login";
    String ajaxReq = request.getParameter("ajax");
    String userAgent = (String)session.getAttribute("userAgent");
    if ((ajaxReq != null) && (ajaxReq.equalsIgnoreCase("true"))) {
      redirectPage = "/navigate/timeout/error";
    } else if ((userAgent != null) && (userAgent.equalsIgnoreCase("mobile"))) {
      redirectPage = "/navigate/muser/login";
    }
    if (requestURI.equals("/")) {}
    return true;
  }
  
  private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String url)
  {
    try
    {
      request.getRequestDispatcher(url).forward(request, response);
    }
    catch (Exception exception)
    {
      log.error(exception);
    }
  }
}
