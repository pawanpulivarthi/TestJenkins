package com.edsys.app.common.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RedirectAction
  extends DispatchAction
{
  private static final String ADDITIONAL_PARAM = "ADDITIONAL_PARAM";
  
  public ActionForward spin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    String parameter = (String)request.getAttribute("ADDITIONAL_PARAM");
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("/individual/scorecard/scorecard?SPIN=" + parameter);
    try
    {
      dispatcher.forward(request, response);
    }
    catch (ServletException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public ActionForward test(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    String parameter = (String)request.getAttribute("ADDITIONAL_PARAM");
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("/individual/test/showTest?testId=");
    try
    {
      dispatcher.forward(request, response);
    }
    catch (ServletException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
    throws Exception
  {
    String[] params = parameter.split("~");
    request.setAttribute("ADDITIONAL_PARAM", params[1]);
    return params[0];
  }
}
