package com.edsys.app.user.form;

import com.edsys.app.common.form.BaseForm;

public class WebsiteAdminForm extends BaseForm{
	
	private static final long serialVersionUID = -1554993836380319940L;

	private String userName;
	
	private String password;
	
	private String instituteCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInstituteCode() {
		return instituteCode;
	}

	public void setInstituteCode(String instituteCode) {
		this.instituteCode = instituteCode;
	}
	
}
