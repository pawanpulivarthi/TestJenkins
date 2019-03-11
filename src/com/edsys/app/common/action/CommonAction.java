package com.edsys.app.common.action;

import com.edsys.app.common.bd.CommonDelegate;
import com.edsys.framework.log.DiagnosticLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CommonAction
  extends BaseAction
{
  private final CommonDelegate delegate;
  private final DiagnosticLogger logger;
  
  public CommonAction()
  {
    this.delegate = new CommonDelegate();
    this.logger = DiagnosticLogger.getLogger(CommonAction.class);
  }
  
  public ActionForward academyNotFound(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    return mapping.findForward("academyNotFound");
  }
}
