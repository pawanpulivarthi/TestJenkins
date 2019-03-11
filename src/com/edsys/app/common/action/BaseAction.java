package com.edsys.app.common.action;

import com.edsys.framework.log.DiagnosticLogger;
import com.edsys.framework.vo.ErrorMessage;
import com.edsys.framework.vo.ErrorMessages;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class BaseAction
  extends DispatchAction
{
  private final DiagnosticLogger logger;
  
  public BaseAction()
  {
    this.logger = DiagnosticLogger.getLogger(BaseAction.class);
  }
  
  protected void addErrors(HttpServletRequest request, String property, ErrorMessages errorMessages)
  {
    int i = 0;
    if (errorMessages != null)
    {
      List<ErrorMessage> messages = errorMessages.getErrorMessages();
      
      ActionMessages actionMessages = getErrors(request);
      if (actionMessages == null) {
        actionMessages = new ActionMessages();
      }
      for (ErrorMessage errorMessage : messages)
      {
        i++;
        actionMessages.add(property, new ActionMessage(errorMessage.getKey(), errorMessage.getArgs()));
        if (i == 10) {
          break;
        }
      }
      if (messages.size() > 11)
      {
        ErrorMessage lastMessage = (ErrorMessage)messages.get(messages.size() - 1);
        actionMessages.add(property, new ActionMessage(lastMessage.getKey(), lastMessage.getArgs()));
      }
      addErrors(request, actionMessages);
    }
  }
  
  protected void addErrors(HttpServletRequest request, String property, String key, Object... args)
  {
    ActionMessages messages = getErrors(request);
    if (messages == null) {
      messages = new ActionMessages();
    }
    messages.add(property, new ActionMessage(key, args));
    addErrors(request, messages);
  }
  
  protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
    throws Exception
  {
    String[] tokens = parameter.split("~");
    if ((tokens != null) && (tokens.length == 2))
    {
      String group = tokens[0];
      if (group != null) {
        group = group.replaceAll("/", "");
      }
      parameter = tokens[1];
    }
    System.out.println("Method Name : " + parameter);
    return parameter;
  }
  
  protected void copyProperties(Object dest, Object orig)
  {
    try
    {
      BeanUtils.copyProperties(dest, orig);
    }
    catch (IllegalAccessException exception)
    {
      this.logger.error(exception);
    }
    catch (InvocationTargetException exception)
    {
      this.logger.error(exception);
    }
  }
  
  protected void updateCurrentURL(HttpServletRequest request)
  {
    request.getSession().setAttribute("currentURL", request.getServletPath());
  }
  
  protected void redirectCurrentURL(HttpServletRequest request, HttpServletResponse response)
  {
    String url = (String)request.getSession().getAttribute("currentURL");
    try
    {
      RequestDispatcher dispatcher = request.getRequestDispatcher(url);
      dispatcher.forward(request, response);
    }
    catch (Exception exception)
    {
      this.logger.error(exception);
    }
  }
}
