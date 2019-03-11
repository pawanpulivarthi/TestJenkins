package com.edsys.app.user.action;

import com.edsys.app.common.action.BaseAction;
import com.edsys.app.common.dao.UserDAO;
import com.edsys.app.user.form.UserForm;
import com.edsys.framework.exception.DAOException;
import com.edsys.framework.globals.Constants;
import com.edsys.framework.log.DiagnosticLogger;
import com.edsys.framework.util.EdsysUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UserAction extends BaseAction {
	private final DiagnosticLogger logger;
	private final UserDAO dao;

	public UserAction() {
		this.logger = DiagnosticLogger.getLogger(UserAction.class);
		this.dao = new UserDAO();
	}

	public ActionForward startLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.debug("starting startLogin()");

		UserForm userForm = (UserForm) form;

		return mapping.findForward("startLogin");
	}

	public ActionForward logoutRedirect(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.debug("starting startLogin()");

		UserForm userForm = (UserForm) form;

		request.getSession().invalidate();
		System.out.println(request.getSession().getAttribute("USER"));

		request.setAttribute("MSG", "You logged out successfully");

		return mapping.findForward("startLogin");
	}
	
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.debug("starting login()");
		UserForm userForm = (UserForm) form;
		
		/*String instituteCode = request.getParameter("instituteCode");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");*/
		
		String instituteCode = userForm.getInstituteCode();
		String userName = userForm.getUserName();
		String password = userForm.getPassword();
		
		String redirectParam = "masterAdminDashboard";
		
		if(instituteCode == null || instituteCode.trim().length() == 0) {
			System.out.println("This is master admin login attempt...So No need of institute_code..");
			System.out.println("instistute Code = " + instituteCode);
			System.out.println("userName = " + userName);
			System.out.println("password = " + password);
			try {
				userForm = dao.userLogin(userForm);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("This isinstitute level login...Below are the details :");
			System.out.println("instistute Code = " + instituteCode);
			System.out.println("userName = " + userName);
			System.out.println("password = " + password);
			try {
				userForm = dao.userLogin(userForm);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(userForm.getUserType());
		
		if(userForm.getUserType().equalsIgnoreCase(Constants.MASTER_ADMIN)) {
			
			request.getSession().setAttribute("USER", userForm);
			request.getSession().setAttribute("USER_TYPE", userForm.getUserType());
			
			redirectParam = "masterAdminDashboard";
			
		}else {
			
		}

		return mapping.findForward(redirectParam);
	}

}
