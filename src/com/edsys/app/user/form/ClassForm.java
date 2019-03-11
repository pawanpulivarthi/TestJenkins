package com.edsys.app.user.form;

public class ClassForm {
	
	private int classId;
	
	private String className;
	
	private String section;
	
	private String classTeacher;
	
	private String tutionFees;
	
	private String admissionFees;
	
	private String classTeacherProfilePic;
	
	// 14th July 2016(MAHADEV) , FOR SETTING CLASS TEACHER ID 
	
	private int classTeacherUserId;
	
	public int getClassTeacherUserId() {
		return classTeacherUserId;
	}

	public void setClassTeacherUserId(int classTeacherUserId) {
		this.classTeacherUserId = classTeacherUserId;
	}

	//END
	
	public String getClassTeacherProfilePic() {
		return classTeacherProfilePic;
	}

	public void setClassTeacherProfilePic(String classTeacherProfilePic) {
		this.classTeacherProfilePic = classTeacherProfilePic;
	}

	public String getTutionFees() {
		return tutionFees;
	}

	public void setTutionFees(String tutionFees) {
		this.tutionFees = tutionFees;
	}

	public String getAdmissionFees() {
		return admissionFees;
	}

	public void setAdmissionFees(String admissionFees) {
		this.admissionFees = admissionFees;
	}

	public String getClassTeacher() {
		return classTeacher;
	}

	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;
	
	

}
