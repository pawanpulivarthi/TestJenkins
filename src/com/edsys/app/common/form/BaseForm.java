package com.edsys.app.common.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract class BaseForm
  extends ActionForm
{
  private static final long serialVersionUID = 6824836732292696101L;
  
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
    return null;
  }
}
