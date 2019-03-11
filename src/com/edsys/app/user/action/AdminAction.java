package com.edsys.app.user.action;

import com.edsys.app.common.action.BaseAction;
import com.edsys.app.common.dao.AdminDAO;
import com.edsys.app.user.form.InstituteAdminForm;
import com.edsys.framework.log.DiagnosticLogger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminAction extends BaseAction {
	private final DiagnosticLogger logger;
	private final AdminDAO dao;

	public AdminAction() {
		this.logger = DiagnosticLogger.getLogger(AdminAction.class);
		this.dao = new AdminDAO();
	}

	public ActionForward adminDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.debug("starting adminDashBoard()");

		InstituteAdminForm adminForm = (InstituteAdminForm) form;

		return mapping.findForward("adminDashboard");
	}

}
