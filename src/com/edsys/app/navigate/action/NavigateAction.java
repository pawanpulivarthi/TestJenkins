package com.edsys.app.navigate.action;

import com.edsys.app.common.action.BaseAction;
import com.edsys.app.common.dao.UserDAO;
import com.edsys.app.user.form.UserForm;
import com.edsys.framework.exception.DAOException;
import com.edsys.framework.log.DiagnosticLogger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NavigateAction
  extends BaseAction
{
  private final DiagnosticLogger logger;
  private final UserDAO dao;
  
  public NavigateAction()
  {
    this.logger = DiagnosticLogger.getLogger(NavigateAction.class);
    this.dao = new UserDAO();
  }
  
  public ActionForward userGuide(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting userGuide()");
    return mapping.findForward("userGuide");
  }
  
  public ActionForward services(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting services()");
    return mapping.findForward("services");
  }
  
  public ActionForward ts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting ts()");
    return mapping.findForward("ts");
  }
  
  public ActionForward contact(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting contact()");
    return mapping.findForward("contact");
  }
  
  public ActionForward about(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting about()");
    return mapping.findForward("about");
  }
  
  public ActionForward navigate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.logger.debug("starting navigate()");
        
    return mapping.findForward("navigate");
  }
}
