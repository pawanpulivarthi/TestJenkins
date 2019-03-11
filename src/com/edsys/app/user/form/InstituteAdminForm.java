package com.edsys.app.user.form;

import com.edsys.app.common.form.BaseForm;

public class InstituteAdminForm extends BaseForm {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String password;
	
	private String dbName;
	
	private String instituteCode;
	
	private String instituteName;
	
	private String address;
	
	private String instituteContactNumber;
	
	private String websiteURL;
	
	private String fax;
	
	private String email;
	
	private String aboutInstitute;

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

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getInstituteCode() {
		return instituteCode;
	}

	public void setInstituteCode(String instituteCode) {
		this.instituteCode = instituteCode;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInstituteContactNumber() {
		return instituteContactNumber;
	}

	public void setInstituteContactNumber(String instituteContactNumber) {
		this.instituteContactNumber = instituteContactNumber;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAboutInstitute() {
		return aboutInstitute;
	}

	public void setAboutInstitute(String aboutInstitute) {
		this.aboutInstitute = aboutInstitute;
	}

}