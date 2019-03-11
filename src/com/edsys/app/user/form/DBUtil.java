package com.edsys.app.user.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {/*

	// 14th July 2016(MAHADEV) , This one is needed to avoid result set closed
	// exception
	public static Connection getConnection(String dbName) {
		Connection connetion = null;
		System.out.println("DB Name : " + dbName);
		try {
			Class.forName(DatabaseConfiguration.driverName);
			connetion = DriverManager.getConnection(
					DatabaseConfiguration.driverURL + dbName,
					DatabaseConfiguration.userName,
					DatabaseConfiguration.password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		System.out.println("DB Connection ==> " + connetion);

		return connetion;
	}

	// changes done by Sudarshan to store index page data on 29/07/2016

	public static int storeIndexData(IndexForm indexForm, String dbName) {

		int res = 0;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String query = "INSERT INTO " + dbName
				+ ".VISITORS(NAME,EMAIL,SUBJECT,MESSAGE) VALUES(?,?,?,?)";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			if (connection != null) {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, indexForm.getName());
				preparedStatement.setString(2, indexForm.getEmail());
				preparedStatement.setString(3, indexForm.getSubject());
				preparedStatement.setString(4, indexForm.getMessage());
				res = preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return res;
	}

	// Method to get DB Name

	public static String getDataBaseName(String schoolid) {

		System.out.println("School Id from request = " + schoolid);

		String schoolIdFromRequest = schoolid.split("_")[schoolid.split("_").length - 1];
		
		System.out.println("schoolIdFromRequest ==> " + schoolIdFromRequest);

		String DBName = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String query = "select * from SCHOOLS where SL_NO=?";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			if (connection != null) {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1,
						Integer.parseInt(schoolIdFromRequest));
				resultSet = preparedStatement.executeQuery();
				while (resultSet != null && resultSet.next()) {

					DBName = resultSet.getString("DB_NAME");

					// 9th July 2016(MAHADEV)
					System.out.println("Database obtained = > " + DBName);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		System.out.println("Evaluated DBName ==> "+ DBName);

		return DBName;

	}

	// end Method of get get DB name

	// MyAdmission login or student login
	public static StudentForm myAdmissionLogin(StudentForm sUser, String dbName) {

		String query = "";
		StudentForm user = null;
		int studentId = sUser.getStudentId();
		String password = sUser.getPassword();
		
		 * System.out.println(studentId); System.out.println(password);
		 

		query = "SELECT * FROM " + dbName
				+ ".STUDENT WHERE STUDENT_ID = ? AND PASSWORD = ? ";

		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);

		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {

			ps = connection.prepareStatement(query);
			ps.setInt(1, studentId);
			ps.setString(2, password);
			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {
				user = new StudentForm();

				user.setStudentId(resultSet.getInt("STUDENT_ID"));
				user.setStudentName(resultSet.getString("STUDENT_NAME"));
				user.setParentName(resultSet.getString("PARENT_NAME"));
				user.setStudentClass(resultSet.getString("STUDENT_CLASS"));
				user.setStudentSection(resultSet.getString("SECTION"));
				user.setEmail(resultSet.getString("EMAIL"));
				 user.setFeePaid(resultSet.getString("FEE_PAID")); 
				user.setPassword(resultSet.getString("PASSWORD"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;

	}

	// end of student authentication

	// Admin storing student data and sending unique id and password

	public static StudentForm createAdmin(StudentForm student, String dbName) {

		int stdId = 0;

		 System.out.println(); 

		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		System.out.println("student roll numer " + student.getRollNumber());
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		StudentForm studentLoginDetail = null;
		// 14th July2016(MAHADEV),ROLL NUMBER HAS CHANGED
		String query2 = "INSERT INTO "
				+ dbName
				+ ".STUDENT(STUDENT_NAME, PARENT_NAME, STUDENT_CLASS, SECTION, EMAIL,PASSWORD,PARENT_PASSWORD,ROLL_NUMBER) values(?,?,?,?,?,?,?,?)";
		try {

			ps = con.prepareStatement(query2);

			ps.setString(1, student.getStudentName());
			ps.setString(2, student.getParentName());
			ps.setString(3, student.getStudentClass());
			ps.setString(4, student.getStudentSection());
			ps.setString(5, student.getEmail());
			ps.setString(6, student.getPassword());
			ps.setString(7, student.getParentpassword());
			ps.setString(8, student.getRollNumber());// 14th
														// July2016(MAHADEV),ROLL
														// NUMBER HAS CHANGED

			ps.executeUpdate();
			con.setAutoCommit(true);

			String query = "SELECT STUDENT_ID, PASSWORD, PARENT_PASSWORD from "
					+ dbName + ".STUDENT where EMAIL = ?";
			ps = con.prepareStatement(query);
			 System.out.println(student.getEmail()); 
			ps.setString(1, student.getEmail());

			resultSet = ps.executeQuery();
			if (resultSet != null && resultSet.next()) {
				studentLoginDetail = new StudentForm();
				stdId = resultSet.getInt("STUDENT_ID");
				studentLoginDetail.setStudentId(resultSet.getInt("STUDENT_ID"));
				studentLoginDetail.setPassword(resultSet.getString("PASSWORD"));
				studentLoginDetail.setParentpassword(resultSet
						.getString("PARENT_PASSWORD"));

				System.out.println(resultSet.getInt("STUDENT_ID"));
				System.out.println(resultSet.getString("PASSWORD"));
				System.out.println(resultSet.getString("PARENT_PASSWORD"));

				System.out.println("insert into userLogin table");

				String toMail = student.getEmail();

				String fromMail = AppConfig.FROM_MAIL;
				String passWord = AppConfig.MAIL_PASSWORD;

				String stdmail = AppConfig.STUEDT_REGISTRATION;

				stdmail = stdmail
						.replace("#USER_NAME", student.getStudentName())
						.replace("#PASSWORD", student.getPassword())
						.replace("#STUDENT_ID", "" + stdId)
						.replace("#STUDENT_CLASS", student.getStudentClass())
						.replace("#STUDENT_SECTION", student.getStudentSection())
						.replace("#ADMISSION_FEE",
						student.getAdmissionFee())
						.replace("#TUTION_FEE", student.getActFee())
						.replace("#ROLL_NUMBER", student.getRollNumber());
				
				new Thread(){
					void run(){
						
					}
				};

				String[] arry = dbName.split("_");

				String schoolId = arry[2];
				stdmail.replace("#SCHOOL_ID", schoolId);

				String mailBody = stdmail;

				MailUtil.sendMailForStudent(fromMail, passWord, toMail,
						mailBody);

				System.out
						.println("after mail body......................>>>>>>>>");

				// inserting necessary details into userLogin table

				String query1 = "insert into "
						+ dbName
						+ ".USER_LOGIN (USER_ID, LOGIN_NAME, PASSWORD, PARENT_PASSWORD, USER_TYPE) VALUES(?,?,?,?,?)";
				ps = con.prepareStatement(query1);
				
				 * System.out.println(resultSet.getInt("STUDENT_ID"));
				 * System.out
				 * .println("MZ_STUD_"+resultSet.getInt("STUDENT_ID"));
				 * System.out.println(resultSet.getString("PASSWORD"));
				 * System.out.println(10);
				 

				ps.setInt(1, resultSet.getInt("STUDENT_ID"));
				ps.setString(2, "MZ_STUD_" + resultSet.getInt("STUDENT_ID"));
				ps.setString(3, resultSet.getString("PASSWORD"));
				ps.setString(4, resultSet.getString("PARENT_PASSWORD"));
				ps.setInt(5, 10);

				int result = ps.executeUpdate();

				con.setAutoCommit(true);

				if (result > 0) {
					System.out.println("insertion is successfull");
				} else {

					System.out.println("unsuccesfull inserrton process");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(con);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return studentLoginDetail;

	}

	// End of Admin storing student data

	// Student form data storing in database
	public static int studentRegister(StudentForm student, String dbName) {
		int status = 0;

		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;

		query = "UPDATE "
				+ dbName
				+ ".STUDENT SET CONTACT=?, ADDRESS=?, GENDER=?, DOB=?, BLOOD_GROUP=?, COUNTRY=?, UPDATED_DATE=CURRENT_TIMESTAMP WHERE EMAIL = ?";
		try {

			ps = connection.prepareStatement(query);

			ps.setString(1, student.getContactNum());
			ps.setString(2, student.getAddress());
			ps.setString(3, student.getGender());
			ps.setString(4, student.getDateOfBirth());
			ps.setString(5, student.getBloodGroup());
			ps.setString(6, student.getCountry());
			// ps.setString(7, null);
			ps.setString(7, student.getEmail());

			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return status;
	}

	// admin storing data into database

	public static int adminRegister(StudentForm u, String dbName) {
		int status = 0;
		Connection con = null;
		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			PreparedStatement ps = con
					.prepareStatement("insert into "
							+ dbName
							+ ".ADMIN(`AMOUNT`,`CLASS`, `FIRST_NAME`, `LAST_NAME`, `KEY`,`PASSWORD`) values(?,?,?,?,?,?)");

			 ps.setDouble(1, u.getAmount()); 
			ps.setString(2, u.getStudentClass());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getKey());
			ps.setString(6, u.getPassword());

			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(con);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return status;
	}

	// Retrieve all records from the class

	public static List<ClassForm> getData(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = null;

		List<ClassForm> studentClasses = null;

		String query = "SELECT * FROM " + dbName + ".CLASS ";
		try {
			System.out.println("getData == Query ==> " + query);
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentClasses = new ArrayList<ClassForm>();
				while (resultSet.next()) {
					ClassForm studentClass = new ClassForm();
					
					 * System.out.println(resultSet.getString("CLASS_NAME"));
					 * System.out.println(resultSet.getString("SECTION"));
					 * System.out.println(resultSet.getString("DESCRIPTION"));
					 * System.out.println();
					 
					studentClass.setClassId(resultSet.getInt("CLASS_ID"));
					studentClass
							.setClassName(resultSet.getString("CLASS_NAME"));
					studentClass.setSection(resultSet.getString("SECTION"));
					studentClass.setDescription(resultSet
							.getString("DESCRIPTION"));

					studentClasses.add(studentClass);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return studentClasses;
	}

	// Retrieve all the data from student

	public static List<StudentForm> getStudentData1(String className,
			String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<StudentForm> studentStandards = null;

		String query = "SELECT * FROM " + dbName
				+ ".STUDENT WHERE STUDENT_CLASS=? ";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, className);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentStandards = new ArrayList<StudentForm>();
				while (resultSet.next()) {
					StudentForm studentStandard = new StudentForm();

					System.out.println(resultSet.getInt("STUDENT_ID"));
					System.out.println(resultSet.getString("STUDENT_NAME"));

					
					 * studentStandard.setStudentId(resultSet.getInt("STUDENT_ID"
					 * )); studentStandard.setStudentName(resultSet.getString(
					 * "STUDENT_NAME"));
					 * studentStandard.setParentName(resultSet.
					 * getString("PARENT_NAME"));
					 * studentStandard.setPassword(resultSet
					 * .getString("PASSWORD"));
					 * studentStandard.setStudentClass(resultSet
					 * .getString("STUDENT_CLASS"));
					 * studentStandard.setStudentSection
					 * (resultSet.getString("SECTION"));
					 * studentStandard.setEmail(resultSet.getString("EMAIL"));
					 * studentStandard
					 * .setContactNum(resultSet.getString("CONTACT"));
					 * studentStandard
					 * .setAddress(resultSet.getString("ADDRESS"));
					 * studentStandard.setGender(resultSet.getString("GENDER"));
					 * studentStandard
					 * .setDateOfBirth(resultSet.getString("DOB"));
					 * studentStandard
					 * .setBloodGroup(resultSet.getString("BLOOD_GROUP"));
					 * studentStandard
					 * .setCountry(resultSet.getString("COUNTRY"));
					 
					// studentStandard.setFeePaid(resultSet.getString("FEE_PAID"));

					
					 * studentStandard.setVerifyStatus(resultSet.getString(
					 * "VERIFICATION_STATUS"));
					 * studentStandard.setAdmissionDate(
					 * resultSet.getString("ADMISSION_DATE"));
					 

					// 14th July 2016(MAHADEV),TO AVOID NULL VALUES AND PROPER
					// DISPLAY

					String studentName = resultSet.getString("STUDENT_NAME");
					String parentName = resultSet.getString("PARENT_NAME");
					String password = resultSet.getString("PASSWORD");
					String studentClass = resultSet.getString("STUDENT_CLASS");
					String studentSection = resultSet.getString("SECTION");
					String email = resultSet.getString("EMAIL");
					String contactNumber = resultSet.getString("CONTACT");
					String address = resultSet.getString("ADDRESS");
					String gender = resultSet.getString("GENDER");
					String country = resultSet.getString("COUNTRY");
					String dateOfBirth = resultSet.getString("DOB");
					String bloodGroup = resultSet.getString("BLOOD_GROUP");

					String verifyStatus = resultSet
							.getString("VERIFICATION_STATUS");
					String admissionDate = resultSet
							.getString("ADMISSION_DATE");
					String lastUpdatedDate = resultSet
							.getString("UPDATED_DATE");

					studentStandard
							.setStudentId(resultSet.getInt("STUDENT_ID"));

					String profilePic = resultSet.getString("PROFILE_PIC");
					if (profilePic == null) {
						studentStandard.setProfilePic("student_default.png"); // 14th
																				// July
																				// 2016
																				// ,
																				// (MAHADEV)For
																				// specific
																				// profile
					} else {
						studentStandard.setProfilePic(profilePic);
					}
					System.out.println("profile pic=>"
							+ studentStandard.getProfilePic());

					if (lastUpdatedDate == null) {
						studentStandard.setLastUpdate("NA");
					} else {
						String onlylastUpdatedDate = lastUpdatedDate.substring(
								0, 10);
						studentStandard.setLastUpdate(onlylastUpdatedDate);
					}
					System.out.println("lastUpdatedDate jd "
							+ studentStandard.getLastUpdate());
					if (admissionDate == null) {
						studentStandard.setAdmissionDate("NA");
					} else {
						String onlyAdmissionDate = admissionDate.substring(0,
								10);
						studentStandard.setAdmissionDate(onlyAdmissionDate);
					}
					System.out.println("admissionDate jd "
							+ studentStandard.getAdmissionDate());
					if (verifyStatus == null) {
						studentStandard.setVerifyStatus("NA");
					} else {
						studentStandard.setVerifyStatus(verifyStatus);
					}
					System.out.println("verification status "
							+ studentStandard.getVerifyStatus());

					if (bloodGroup == null) {
						studentStandard.setBloodGroup("NA");
					} else {
						studentStandard.setBloodGroup(bloodGroup);
					}
					System.out.println("bloodGroup jd "
							+ studentStandard.getBloodGroup());
					if (dateOfBirth == null) {
						studentStandard.setDateOfBirth("NA");
					} else {
						String onlydateOfBirth = dateOfBirth.substring(0, 10);
						studentStandard.setDateOfBirth(onlydateOfBirth);
					}
					System.out.println("dateOfBirth jd "
							+ studentStandard.getDateOfBirth());

					if (country == null) {
						studentStandard.setCountry("NA");
					} else {
						studentStandard.setCountry(country);
					}
					System.out.println("setCountry "
							+ studentStandard.getCountry());
					if (address == null) {
						studentStandard.setAddress("NA");
					} else {
						studentStandard.setAddress(address);
					}
					System.out.println("Address "
							+ studentStandard.getAddress());
					if (gender == null) {
						studentStandard.setGender("NA");
					} else {
						studentStandard.setGender(gender);
					}
					System.out
							.println("Address " + studentStandard.getGender());

					if (studentName == null) {
						studentStandard.setStudentName("NA");
					} else {
						studentStandard.setStudentName(studentName);
					}
					System.out.println("student name= >"
							+ studentStandard.getStudentName());
					if (parentName == null) {
						studentStandard.setParentName("NA");
					} else {
						studentStandard.setParentName(parentName);
					}
					System.out.println("Parent name"
							+ studentStandard.getParentName());
					if (password == null) {
						studentStandard.setPassword("NA");
					} else {
						studentStandard.setPassword(password);
					}
					System.out.println("password name"
							+ studentStandard.getPassword());
					if (studentClass == null) {
						studentStandard.setStudentClass("NA");
					} else {
						studentStandard.setStudentClass(studentClass);
					}
					System.out.println("studentClass name"
							+ studentStandard.getStudentClass());
					if (studentSection == null) {
						studentStandard.setStudentSection("NA");
					} else {
						studentStandard.setStudentSection(studentSection);
					}
					System.out.println("studentSection name"
							+ studentStandard.getStudentSection());
					if (email == null) {
						studentStandard.setEmail("NA");
					} else {
						studentStandard.setEmail(email);
					}
					System.out.println("email name"
							+ studentStandard.getEmail());
					if (contactNumber == null) {
						studentStandard.setContactNum("NA");
					} else {
						studentStandard.setContactNum(contactNumber);
					}
					System.out.println("contactNumber name"
							+ studentStandard.getContactNum());

					// END
					
					 * String profilePic = resultSet.getString("PROFILE_PIC");
					 * if(profilePic == null){
					 * studentStandard.setProfilePic("default.gif"); }else{
					 * studentStandard.setProfilePic(profilePic); }
					 * System.out.println
					 * ("Porfile Pic Name=> "+studentStandard.getProfilePic());
					 

					studentStandards.add(studentStandard);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentStandards;
	}

	// getting student content for verification
	
	 * public static void main(String[] args) {
	 * getAdmissionData("schoolmanagement"); }
	 
	public static List<StudentForm> getAdmissionData(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<StudentForm> students = null;

		String query = "SELECT SF.ADMISION_FEE, SF.PAID_FEE, S.STUDENT_ID, S.STUDENT_NAME, S.STUDENT_CLASS, S.VERIFICATION_STATUS "
				+ " FROM "
				+ dbName
				+ ".STUDENT S, "
				+ dbName
				+ ".STUDENT_FEE SF "
				+ "WHERE (S.VERIFICATION_STATUS = 'N' OR S.VERIFICATION_STATUS = 'H') "
				+ "AND S.STUDENT_ID = SF.STUDENT_ID;";
		try {
			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				students = new ArrayList<StudentForm>();
				while (resultSet.next()) {
					StudentForm student = new StudentForm();

					student.setStudentId(resultSet.getInt("STUDENT_ID"));
					student.setStudentName(resultSet.getString("STUDENT_NAME"));
					student.setStudentClass(resultSet
							.getString("STUDENT_CLASS"));
					student.setAdmissionFee(resultSet.getString("ADMISION_FEE"));
					student.setTuitionFee(resultSet.getString("PAID_FEE"));
					student.setVerifyStatus(resultSet
							.getString("VERIFICATION_STATUS"));

					System.out.println(resultSet.getString("ADMISION_FEE"));
					System.out.println("Paid Fees"
							+ resultSet.getString("PAID_FEE"));

					students.add(student);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return students;
	}

	public static List<SubjectForm> getListOfSubjects(String className,
			String dbName) {

		System.out.println(className);

		int classId = Integer.parseInt(className);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<SubjectForm> subjects = null;

		String query = "SELECT * FROM " + dbName
				+ ".SUBJECTS WHERE CLASS_ID = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, classId);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				subjects = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm subject = new SubjectForm();

					subject.setSubject_Id(resultSet.getString("SUBJ_ID"));
					subject.setSubject_Name(resultSet.getString("NAME"));
					subject.setClass_id(resultSet.getInt("CLASS_ID"));
					subject.setDiscription(resultSet.getString("DESCRIPTION"));

					System.out.println(resultSet.getString("SUBJ_ID") + " "
							+ resultSet.getString("NAME") + " "
							+ resultSet.getInt("CLASS_ID") + " "
							+ resultSet.getString("DESCRIPTION"));
					subjects.add(subject);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return subjects;
	}

	// Displaying student data into page

	public static StudentForm getStudentData(String studentId, String dbName) {
		
		System.out.println("Inside getStudentData() & DBNAME ==> " + dbName);

		StudentForm student = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		String query = "SELECT * FROM " + dbName
				+ ".STUDENT where STUDENT_ID=?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, studentId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				student = new StudentForm();

				student.setStudentId(resultSet.getInt("STUDENT_ID"));

				String studentName = resultSet.getString("STUDENT_NAME");
				String parentName = resultSet.getString("PARENT_NAME");
				String password = resultSet.getString("PASSWORD");
				String studentClass = resultSet.getString("STUDENT_CLASS");
				String studentSection = resultSet.getString("SECTION");
				String email = resultSet.getString("EMAIL");
				String contactNumber = resultSet.getString("CONTACT");
				String address = resultSet.getString("ADDRESS");
				String gender = resultSet.getString("GENDER");
				String country = resultSet.getString("COUNTRY");
				String dateOfBirth = resultSet.getString("DOB");
				String bloodGroup = resultSet.getString("BLOOD_GROUP");

				String verifyStatus = resultSet
						.getString("VERIFICATION_STATUS");
				String admissionDate = resultSet.getString("ADMISSION_DATE");
				String lastUpdatedDate = resultSet.getString("UPDATED_DATE");

				String profilePic = resultSet.getString("PROFILE_PIC");

				if (profilePic == null) {
					student.setProfilePic("student_default.png"); // 14th July
																	// 2016 ,
																	// (MAHADEV)For
																	// specific
																	// profile
				} else {
					student.setProfilePic(profilePic);
				}
				System.out.println("profile pic=>" + student.getProfilePic());

				if (lastUpdatedDate == null) {
					student.setLastUpdate("NA");
				} else {
					String onlylastUpdatedDate = lastUpdatedDate.substring(0,
							10);
					student.setLastUpdate(onlylastUpdatedDate);
				}
				System.out.println("lastUpdatedDate jd "
						+ student.getLastUpdate());
				if (admissionDate == null) {
					student.setAdmissionDate("NA");
				} else {
					String onlyAdmissionDate = admissionDate.substring(0, 10);
					student.setAdmissionDate(onlyAdmissionDate);
				}
				System.out.println("admissionDate jd "
						+ student.getAdmissionDate());
				if (verifyStatus == null) {
					student.setVerifyStatus("NA");
				} else {
					student.setVerifyStatus(verifyStatus);
				}
				System.out.println("feePaid jd " + student.getVerifyStatus());

				if (bloodGroup == null) {
					student.setBloodGroup("NA");
				} else {
					student.setBloodGroup(bloodGroup);
				}
				System.out.println("bloodGroup jd " + student.getBloodGroup());
				if (dateOfBirth == null) {
					student.setDateOfBirth("NA");
				} else {
					String onlydateOfBirth = dateOfBirth.substring(0, 10);
					student.setDateOfBirth(onlydateOfBirth);
				}
				System.out
						.println("dateOfBirth jd " + student.getDateOfBirth());
				if (country == null) {
					student.setCountry("NA");
				} else {
					student.setCountry(country);
				}
				System.out.println("setCountry " + student.getCountry());
				if (address == null) {
					student.setAddress("NA");
				} else {
					student.setAddress(address);
				}
				System.out.println("Address " + student.getAddress());
				if (gender == null) {
					student.setGender("NA");
				} else {
					student.setGender(gender);
				}
				System.out.println("Address " + student.getGender());

				if (studentName == null) {
					student.setStudentName("NA");
				} else {
					student.setStudentName(studentName);
				}
				System.out
						.println("student name= >" + student.getStudentName());
				if (parentName == null) {
					student.setParentName("NA");
				} else {
					student.setParentName(parentName);
				}
				System.out.println("Parent name" + student.getParentName());
				if (password == null) {
					student.setPassword("NA");
				} else {
					student.setPassword(password);
				}
				System.out.println("password name" + student.getPassword());
				if (studentClass == null) {
					student.setStudentClass("NA");
				} else {
					student.setStudentClass(studentClass);
				}
				System.out.println("studentClass name"
						+ student.getStudentClass());
				if (studentSection == null) {
					student.setStudentSection("NA");
				} else {
					student.setStudentSection(studentSection);
				}
				System.out.println("studentSection name"
						+ student.getStudentSection());
				if (email == null) {
					student.setEmail("NA");
				} else {
					student.setEmail(email);
				}
				System.out.println("email name" + student.getEmail());
				if (contactNumber == null) {
					student.setContactNum("NA");
				} else {
					student.setContactNum(contactNumber);
				}
				System.out.println("contactNumber name"
						+ student.getContactNum());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return student;

	}

	// after verification done , setting verification status to Y .

	public static int verify(String studentId, String dbName) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		int n = 0;

		String query = "UPDATE " + dbName
				+ ".STUDENT SET VERIFICATION_STATUS='Y' WHERE STUDENT_ID=?";

		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, studentId);

			n = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n;
	}

	// storing form data of staff form into data base and giving id and password
	// back

	public static StaffForm createStaff(StaffForm staff, String dbName) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StaffForm staffdetail = null;

		 System.out.println(staff.getEmailId()); 

		String Query = "INSERT INTO "
				+ dbName
				+ ".STAFF (FIRST_NAME, LAST_NAME, PASSWORD, CONTACT_NUM, ADDRESS, EMAIL_ID ,USER_NAME) VALUES (?,?,?,?,?,?,?);";

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		try {
			preparedStatement = connection.prepareStatement(Query);

			preparedStatement.setString(1, staff.getFirstName());
			preparedStatement.setString(2, staff.getLastName());

			preparedStatement.setString(3, staff.getPassword());
			preparedStatement.setString(4, staff.getContactNum());
			preparedStatement.setString(5, staff.getAddress());
			preparedStatement.setString(6, staff.getEmailId());
			preparedStatement.setString(7, staff.getUserName());

			preparedStatement.executeUpdate();

			String query = "SELECT STAFF_ID, USER_NAME, PASSWORD from "
					+ dbName + ".STAFF where EMAIL_ID = ?";
			preparedStatement = connection.prepareStatement(query);
			 System.out.println(staff.getEmailId()); 
			preparedStatement.setString(1, staff.getEmailId());

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				staffdetail = new StaffForm();
				int staffId = resultSet.getInt("STAFF_ID");
				String newStaffId = String.valueOf(staffId);
				newStaffId = "MZ_STAFF_" + newStaffId;
				System.out.println("staff id = > " + newStaffId);

				// inserting necessary details into userLogin table

				String query1 = "INSERT INTO "
						+ dbName
						+ ".USER_LOGIN (USER_ID, LOGIN_NAME, PASSWORD, USER_TYPE) VALUES(?,?,?,?)";

				preparedStatement = connection.prepareStatement(query1);

				preparedStatement.setString(1, newStaffId);
				preparedStatement
						.setString(2, resultSet.getString("USER_NAME"));
				preparedStatement.setString(3, resultSet.getString("PASSWORD"));
				preparedStatement.setInt(4, 5);

				int result = preparedStatement.executeUpdate();

				if (result > 0) {
					System.out.println("insertion is successfull");
				} else {

					System.out.println("unsuccesfull inserrton process");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return staffdetail;

	}

	// staff login validation code

	public static StaffForm staffLoginValidation(StaffForm staff, String dbName) {
		StaffForm user = null;
		String query = "";
		int userId = staff.getUserId();
		String password = staff.getPassword();

		
		 * System.out.println(userId); System.out.println(password);
		 

		query = "SELECT * FROM " + dbName
				+ ".STAFF WHERE STAFF_ID = ? AND PASSWORD = ? ";

		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);

		ResultSet resultSet = null;
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, password);
			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {

				user = new StaffForm();

				user.setUserId(resultSet.getInt("STAFF_ID"));
				user.setUserName(resultSet.getString("USER_NAME"));
				user.setFirstName(resultSet.getString("FIRST_NAME"));
				user.setLastName(resultSet.getString("LAST_NAME"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setAddress(resultSet.getString("ADDRESS"));
				user.setEmailId(resultSet.getString("EMAIL_ID"));
				user.setContactNum(resultSet.getString("CONTACT_NUM"));

				 System.out.println(resultSet.getInt("STAFF_ID")); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;

	}

	// staff data register by staff into data base

	public static int staffDataRegister(StaffForm staff, String[] array,
			String dbName) {
		int status = 0;

		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;

		query = "UPDATE "
				+ dbName
				+ ".STAFF SET SPECIALIZATION=?, EXPERIENCE=?, PAST_ORGANIZATION=?, DOJ=?, GENDER=?, COUNTRY=?, DEAL_SUBJECTS=?  WHERE EMAIL_ID = ?";

		try {
			String str1 = "";
			ps = connection.prepareStatement(query);

			
			 * System.out.println(staff.getAddress());
			 * System.out.println(staff.getDateOfJoining());
			 
			 System.out.println(staff.getDealSubject()); 

			for (String str : array) {

				str1 = str1 + str + "~";

			}
			System.out.println(str1);

			ps.setString(1, staff.getSpecialization());
			ps.setString(2, staff.getExperience());
			ps.setString(3, staff.getPastOrganization());
			ps.setString(4, staff.getDateOfJoining());
			ps.setString(5, staff.getGender());
			ps.setString(6, staff.getCountry());
			ps.setString(7, str1);

			 ps.setString(7, staff.getDealSubject()); 

			ps.setString(8, staff.getEmailId());
			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return status;
	}

	//

	// 8th July 2016(MAHADEV), GETTING SATFF DETAILS TO AVOID NO CHANGES AFTER
	// UPDATION

	
	 * public static void main(String[] args) {
	 * getStaffInfoForDisplay("sarojaniLila","edsys365_MZSCHOOL_30"); }
	 

	public static StaffForm getStaffInfoForDisplay(String userName,
			String dbName) {
		StaffForm user = null;
		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		query = "SELECT * FROM " + dbName + ".STAFF WHERE USER_NAME = ?";

		try {
			user = new StaffForm();
			ps = connection.prepareStatement(query);
			ps.setString(1, userName);

			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {

				user.setUserId(resultSet.getInt("STAFF_ID"));

				System.out
						.println("staff id = " + resultSet.getInt("STAFF_ID"));

				user.setUserName(resultSet.getString("USER_NAME"));

				System.out.println("Staff id => "
						+ resultSet.getInt("STAFF_ID"));
				System.out.println(resultSet.getString("USER_NAME"));

				String FirstName = resultSet.getString("FIRST_NAME");
				if (FirstName == null) {
					FirstName = "NA";
				}
				user.setFirstName(FirstName);
				System.out.println("First name = " + user.getFirstName());

				String lastName = resultSet.getString("LAST_NAME");
				if (lastName == null) {
					lastName = "NA";
				}
				user.setLastName(lastName);
				System.out.println("last name = " + user.getLastName());

				String password = resultSet.getString("PASSWORD");
				if (password == null) {
					password = "NA";
				}
				user.setPassword(password);
				System.out.println("password = " + user.getPassword());

				String contactNumber = resultSet.getString("CONTACT_NUM");
				if (contactNumber == null) {
					contactNumber = "NA";
				}
				user.setContactNum(contactNumber);
				System.out
						.println("contact number = > " + user.getContactNum());

				String address = resultSet.getString("ADDRESS");
				if (address == null) {
					address = "NA";
				}
				user.setAddress(address);
				System.out.println("address = " + user.getAddress());

				String emailId = resultSet.getString("EMAIL_ID");
				if (emailId == null) {
					emailId = "NA";
				}
				user.setEmailId(emailId);
				System.out.println("email id = " + user.getEmailId());

				String specialization = resultSet.getString("SPECIALIZATION");
				if (specialization == null) {
					specialization = "NA";
				}
				user.setSpecialization(specialization);
				System.out.println("spcialization = "
						+ user.getSpecialization());

				String experience = resultSet.getString("EXPERIENCE");
				if (experience == null) {
					experience = "NA";
				}
				user.setExperience(experience);
				System.out.println("experience " + user.getExperience());

				String pastOrganization = resultSet
						.getString("PAST_ORGANIZATION");
				if (pastOrganization == null) {
					pastOrganization = "NA";
				}
				user.setPastOrganization(pastOrganization);
				System.out.println("past organization = "
						+ user.getPastOrganization());

				String gender = resultSet.getString("GENDER");
				if (gender == null) {
					gender = "NA";
				}
				user.setGender(gender);
				System.out.println("gender = " + user.getGender());

				String country = resultSet.getString("COUNTRY");
				if (country == null) {
					country = "NA";
				}
				user.setCountry(country);
				System.out.println("countr " + user.getCountry());

				String dealSubjects = resultSet.getString("DEAL_SUBJECTs");
				if (dealSubjects == null) {
					dealSubjects = "NA";
					user.setDealSubject(dealSubjects);
					System.out
							.println("Deal subjects " + user.getDealSubject());
				} else {
					String newStr = dealSubjects.replace('~', ',');
					user.setDealSubject(newStr);
					System.out
							.println("Deal subjects " + user.getDealSubject());
				}

				String dateOfJoining = resultSet.getString("DOJ");

				if (dateOfJoining == null) {
					dateOfJoining = "NA";
					user.setDateOfJoining(dateOfJoining);
					System.out.println("date of joining "
							+ user.getDateOfJoining());
				} else {
					String onlyDateOfJoining = dateOfJoining.substring(0, 10);
					user.setDateOfJoining(onlyDateOfJoining);
					System.out.println("date of joining "
							+ user.getDateOfJoining());
				}

				String profilePic = resultSet.getString("PROFILE_PIC");
				if (profilePic == null) {
					user.setProfilePic("staff_default.png");// 14th July
															// 2016(MAHADEV)
				} else {
					user.setProfilePic(profilePic);
				}
				System.out.println(user.getProfilePic());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;
	}

	// End

	// fetching staff data
	public static StaffForm getStaffInfo(String userName, String password,
			String dbName) {
		StaffForm user = null;
		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		query = "SELECT * FROM " + dbName
				+ ".STAFF WHERE USER_NAME = ? AND PASSWORD = ?";

		try {
			user = new StaffForm();
			ps = connection.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {

				user.setUserId(resultSet.getInt("STAFF_ID"));
				System.out.println("Staff id => "
						+ resultSet.getInt("STAFF_ID"));
				user.setUserName(resultSet.getString("USER_NAME"));
				System.out.println(resultSet.getString("USER_NAME"));
				user.setFirstName(resultSet.getString("FIRST_NAME"));
				user.setLastName(resultSet.getString("LAST_NAME"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setContactNum(resultSet.getString("CONTACT_NUM"));
				user.setAddress(resultSet.getString("ADDRESS"));
				user.setEmailId(resultSet.getString("EMAIL_ID"));
				user.setSpecialization(resultSet.getString("SPECIALIZATION"));
				user.setExperience(resultSet.getString("EXPERIENCE"));

				user.setPastOrganization(resultSet
						.getString("PAST_ORGANIZATION"));
				user.setDateOfJoining(resultSet.getString("DOJ"));
				user.setGender(resultSet.getString("GENDER"));
				user.setCountry(resultSet.getString("COUNTRY"));
				user.setDealSubject(resultSet.getString("DEAL_SUBJECTs"));

				String profilePic = resultSet.getString("PROFILE_PIC");
				if (profilePic == null) {
					user.setProfilePic("default.gif");
				} else {
					user.setProfilePic(profilePic);
				}

				System.out.println(user.getProfilePic());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;
	}

	// fetching student data
	
	 * public static void main(String[] args) {
	 * getStudentInfo(126,"schoolmanagement"); }
	 
	public static StudentForm getStudentInfo(int studentId, String dbName) {
		StudentForm user = null;
		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		query = "SELECT S.*, (SF.ADMISION_FEE + PAID_FEE) TOTAL_FEE_PAID FROM "
				+ dbName
				+ ".STUDENT S, "
				+ dbName
				+ ".STUDENT_FEE SF WHERE S.STUDENT_ID = ? AND S.STUDENT_ID = SF.STUDENT_ID";

		try {
			user = new StudentForm();
			ps = connection.prepareStatement(query);
			ps.setInt(1, studentId);

			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {
				user.setDbName(dbName);

				user.setStdId(resultSet.getString("STUDENT_ID"));

				String studentName = resultSet.getString("STUDENT_NAME");
				String parentName = resultSet.getString("PARENT_NAME");
				String password = resultSet.getString("PASSWORD");
				String studentClass = resultSet.getString("STUDENT_CLASS");
				String studentSection = resultSet.getString("SECTION");
				String email = resultSet.getString("EMAIL");

				String contactNumber = resultSet.getString("CONTACT");
				String address = resultSet.getString("ADDRESS");
				String gender = resultSet.getString("GENDER");
				String country = resultSet.getString("COUNTRY");
				String dateOfBirth = resultSet.getString("DOB");
				String bloodGroup = resultSet.getString("BLOOD_GROUP");

				String verifyStatus = resultSet
						.getString("VERIFICATION_STATUS");
				String resaonForHold = resultSet.getString("REASONFORHOLD");
				String admissionDate = resultSet.getString("ADMISSION_DATE");
				String lastUpdatedDate = resultSet.getString("UPDATED_DATE");

				user.setFeePaid(resultSet.getString("TOTAL_FEE_PAID"));

				if (resaonForHold == null) {
					user.setReasonForHold("NA");
				} else {
					user.setReasonForHold(resaonForHold);
				}
				System.out.println("Reason for hold =>"
						+ user.getReasonForHold());
				if (lastUpdatedDate == null) {
					user.setLastUpdate("NA");
				} else {
					String onlylastUpdatedDate = lastUpdatedDate.substring(0,
							10);
					user.setLastUpdate(onlylastUpdatedDate);
				}
				System.out
						.println("lastUpdatedDate jd " + user.getLastUpdate());

				if (admissionDate == null) {
					user.setAdmissionDate("NA");
				} else {
					String onlyAdmissionDate = admissionDate.substring(0, 10);
					user.setAdmissionDate(onlyAdmissionDate);
				}
				System.out.println("admissionDate jd "
						+ user.getAdmissionDate());

				if (verifyStatus == null) {
					user.setVerifyStatus("NA");
				} else {
					user.setVerifyStatus(verifyStatus);
				}
				System.out.println("feePaid jd " + verifyStatus);

				if (bloodGroup == null) {
					user.setBloodGroup("NA");
				} else {
					user.setBloodGroup(bloodGroup);
				}
				System.out.println("bloodGroup jd " + bloodGroup);
				if (dateOfBirth == null) {
					user.setDateOfBirth("NA");
				} else {
					String onlydateOfBirth = dateOfBirth.substring(0, 10);
					user.setDateOfBirth(onlydateOfBirth);
				}
				System.out.println("dateOfBirth jd " + user.getDateOfBirth());
				if (country == null) {
					user.setCountry("NA");
				} else {
					user.setCountry(country);
				}
				System.out.println("setCountry " + country);
				if (address == null) {
					user.setAddress("NA");
				} else {
					user.setAddress(address);
				}
				System.out.println("Address " + address);
				if (gender == null) {
					user.setGender("NA");
				} else {
					user.setGender(gender);
				}
				System.out.println("Address " + address);

				if (studentName == null) {
					user.setStudentName("NA");
				} else {
					user.setStudentName(studentName);
				}
				System.out.println(studentName);
				if (parentName == null) {
					user.setParentName("NA");
				} else {
					user.setParentName(parentName);
				}
				System.out.println("Parent name" + parentName);
				if (password == null) {
					user.setPassword("NA");
				} else {
					user.setPassword(password);
				}
				System.out.println("password name" + password);
				if (studentClass == null) {
					user.setStudentClass("NA");
				} else {
					user.setStudentClass(studentClass);
				}
				System.out.println("studentClass name" + studentClass);
				if (studentSection == null) {
					user.setStudentSection("NA");
				} else {
					user.setStudentSection(studentSection);
				}
				System.out.println("studentSection name" + studentSection);
				if (email == null) {
					user.setEmail("NA");
				} else {
					user.setEmail(email);
				}
				System.out.println("email name" + email);
				if (contactNumber == null) {
					user.setContactNum("NA");
				} else {
					user.setContactNum(contactNumber);
				}
				System.out.println("contactNumber name" + contactNumber);

				String profilePic = resultSet.getString("PROFILE_PIC");
				if (profilePic == null) {
					user.setProfilePic("default.gif");
				} else {
					user.setProfilePic(profilePic);
				}

				System.out.println(user.getProfilePic());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;
	}

	// Common login validation

	public static Map<Integer, List<Object>> loginValidation(String userName,
			String password, String dbName) {

		System.out.println(userName);
		System.out.println(password);
		System.out.println(dbName);
		Map<Integer, List<Object>> user = null;
		String query = "";
		int userType = 0;
		Connection connection = null;

		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		System.out.println("User Name ==> " + userName);
		System.out.println("User Password ==> " + password);

		try {
			if (dbName.equalsIgnoreCase(AppConfig.MASTER_DB))
				query = "SELECT  USER_TYPE FROM USER_LOGIN WHERE LOGIN_NAME = ? AND (PASSWORD = ? OR PARENT_PASSWORD = ? )";
			else
				query = "SELECT  USER_TYPE FROM "
						+ dbName
						+ ".USER_LOGIN WHERE LOGIN_NAME = ? AND (PASSWORD = ? OR PARENT_PASSWORD = ? )";

			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			System.out.println(connection.getMetaData().getURL());
			if (connection != null) {
				System.out.println("Connection established.....");

				ps = connection.prepareStatement(query);
				ps.setString(1, userName);
				ps.setString(2, password);
				ps.setString(3, password);

				resultSet = ps.executeQuery();

				if (resultSet != null && resultSet.next()) {
					 System.out.println(resultSet.getInt("USER_TYPE")); 
					userType = resultSet.getInt("USER_TYPE");

					System.out.println(userType);
					if (userType == 5) {
						user = new HashMap<Integer, List<Object>>();
						StaffForm staffUser = getStaffInfo(userName, password,
								dbName);
						List<Object> staffList = new ArrayList<Object>();
						staffList.add(staffUser);

						user.put(userType, staffList);
					} else if (userType == 10) {
						user = new HashMap<Integer, List<Object>>();
						int studentId = Integer.parseInt(userName
								.substring("MZ_STUD_".length()));

						System.out.println(studentId);
						StudentForm studenUser = getStudentInfo(studentId,
								dbName);

						List<Object> studentList = new ArrayList<Object>();
						studentList.add(studenUser);

						user.put(userType, studentList);
					} else if (userType == 1) {
						System.out.println("user..1--admin");
						user = new HashMap<Integer, List<Object>>();

						List<Object> adminList = new ArrayList<Object>();
						adminList.add("Admin");
						user.put(userType, adminList);
						System.out.println("user..1 end--admin");

					} else if (userType == 50) {
						user = new HashMap<Integer, List<Object>>();

						List<Object> libList = new ArrayList<Object>();
						libList.add("lib_staff");

						user.put(userType, libList);
					}

					else if (userType == 100) {

						user = new HashMap<Integer, List<Object>>();

						List<Object> libList = new ArrayList<Object>();
						libList.add("master_Admin");
						user.put(userType, libList);

					}

					return user;
				}

			}
			System.out.println(connection + " << connection");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				if (connection != null)
					ConnectDatabase.closeConnection(connection);

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;

	}

	// sudarshan recent work

	public static List<SubjectForm> getSubjects(String dbName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<SubjectForm> subjects = null;
		ResultSet resultSet = null;
		String query = "select * from " + dbName + ".SUBJECTS";
		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				subjects = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm subject = new SubjectForm();
					subject.setSubject_Id(resultSet.getString("SUBJ_ID"));

					subject.setSubject_Name(resultSet.getString("NAME"));
					subject.setClass_id(resultSet.getInt("CLASS_ID"));
					subject.setDiscription(resultSet.getString("DESCRIPTION"));
					subjects.add(subject);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return subjects;

	}

	public static int storeSubjects(SubjectForm subject, String dbName) {

		Connection connection = null;
		int count = 0;

		PreparedStatement preparedStatement = null;

		// ResultSet resultSet = null;

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		String query = "INSERT INTO  "
				+ dbName
				+ ".SUBJECTS (SUBJ_ID, NAME, CLASS_ID, DESCRIPTION) VALUES (?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, subject.getSubject_Id());
			preparedStatement.setString(2, subject.getSubject_Name());
			preparedStatement.setInt(3, subject.getClass_id());
			preparedStatement.setString(4, subject.getDiscription());

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;

	}

	public static int createBankAccountDetails(StaffForm staff,
			String userName, String dbName) {
		int count = 0;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE "
				+ dbName
				+ ".STAFF SET ACCOUNT_NUMBER=?, ACCOUNT_NAME=?, BRANCH_NAME=?, IFSC_CODE=?, "
				+ "BANK_ADDRESS=? WHERE USER_NAME = ?";

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, staff.getAccountNumber());
			preparedStatement.setString(2, staff.getAccountName());
			preparedStatement.setString(3, staff.getBranchName());
			preparedStatement.setString(4, staff.getIfscCode());
			preparedStatement.setString(5, staff.getBankAddress());

			preparedStatement.setString(6, userName);

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return count;

	}

	// method for creating & registering announcement into database

	public static int createAnnouncements(Announcements announcements,
			String dbName) {
		int count = 0;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO "
				+ dbName
				+ ".NOTICE_BOARD(`EVENT_NAME`, `CLASS`, `SECTION`,`ANNOUNCED_DATE`, `EVENT_GOING_TO_HAPPEN`, `DISCRIPTION`,STAFF) VALUES (?,?,?,?,?,?,?);";

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, announcements.getEventName());
			preparedStatement.setString(2, announcements.getStudentClass());
			preparedStatement.setString(3, announcements.getStudentSection());
			preparedStatement.setString(4, announcements.getAnnoncedDate());
			preparedStatement.setString(5,
					announcements.getEventGoingToHappen());
			preparedStatement.setString(6, announcements.getDiscription());
			preparedStatement.setString(7, announcements.getEventForStaff());

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return count;

	}

	// getting details of notice for student

	public static List<Announcements> getAnnounceDetails(String sClass,
			String dbName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Announcements> announces = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + dbName
				+ ".NOTICE_BOARD WHERE CLASS=? ";
		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, sClass);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				announces = new ArrayList<Announcements>();
				while (resultSet.next()) {
					Announcements announce = new Announcements();

					announce.setEventName(resultSet.getString("EVENT_NAME"));
					announce.setStudentClass(resultSet.getString("CLASS"));
					announce.setStudentSection(resultSet.getString("SECTION"));
					announce.setAnnoncedDate(resultSet
							.getString("ANNOUNCED_DATE"));
					announce.setEventGoingToHappen(resultSet
							.getString("EVENT_GOING_TO_HAPPEN"));
					announce.setDiscription(resultSet.getString("DISCRIPTION"));

					announces.add(announce);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return announces;

	}

	// getting subjects from subject table

	public static List<SubjectForm> getSubjectsName(String dbName) {

		List<SubjectForm> subjects = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		String query = "";

		query = "SELECT DISTINCT NAME FROM " + dbName + ".SUBJECTS";

		try {

			subjects = new ArrayList<SubjectForm>();

			ps = con.prepareStatement(query);
			resultSet = ps.executeQuery();
			if (resultSet != null) {
				subjects = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm subj = new SubjectForm();
					subj.setSubject_Name(resultSet.getString("NAME"));

					subjects.add(subj);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return subjects;
	}

	// 2 - getting list of subjects based on class name

	public static List<SubjectForm> getSubjectsData(String className,
			String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = null;

		List<SubjectForm> subjects = null;

		String query = "SELECT * FROM " + dbName
				+ ".SUBJECTS WHERE CLASS_ID = ?";
		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, className);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				subjects = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm sub = new SubjectForm();

					sub.setSubject_Id(resultSet.getString("SUBJ_ID"));
					sub.setSubject_Name(resultSet.getString("NAME"));
					sub.setClass_id(resultSet.getInt("CLASS_ID"));
					sub.setStaffName(resultSet.getString("STAFF_NAME"));

					subjects.add(sub);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return subjects;
	}

	// getting classes
	public static List<ClassForm> getData1(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<ClassForm> studentClasses = null;

		String query = "SELECT * FROM " + dbName + ".CLASS";
		try {
			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentClasses = new ArrayList<ClassForm>();
				while (resultSet.next()) {
					ClassForm studentClass = new ClassForm();

					studentClass.setClassId(resultSet.getInt("CLASS_ID"));
					studentClass
							.setClassName(resultSet.getString("CLASS_NAME"));
					studentClass.setSection(resultSet.getString("SECTION"));
					studentClass.setDescription(resultSet
							.getString("DESCRIPTION"));

					studentClasses.add(studentClass);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentClasses;
	}

	// getting staff based on subjects
	public static List<StaffForm> getStaffSubjects(String subjectName,
			String dbName) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = null;
		 System.out.println(subjectName); 

		List<StaffForm> staffNames = null;

		String query = "SELECT FIRST_NAME , USER_NAME FROM " + dbName
				+ ".STAFF WHERE DEAL_SUBJECTS LIKE '%" + subjectName + "%' ";
		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				staffNames = new ArrayList<StaffForm>();
				while (resultSet.next()) {
					StaffForm staff = new StaffForm();

					staff.setUserName(resultSet.getString("USER_NAME"));
					staff.setFirstName(resultSet.getString("FIRST_NAME"));

					
					 * System.out.println(resultSet.getString("USER_NAME"));
					 * System.out.println(resultSet.getString("FIRST_NAME"));
					 

					staffNames.add(staff);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return staffNames;
	}

	// Updation in Subject table

	public static int updateStaffName(String standard, String subjectName,
			String staffName, String dbName) {
		PreparedStatement ps = null;
		int res = 0;
		Connection con = null;

		
		 * System.out.println(standard);; System.out.println(subjectName);
		 * System.out.println(staffName);
		 

		staffName = staffName.trim();

		String query = "UPDATE " + dbName
				+ ".SUBJECTS SET STAFF_NAME= ? WHERE NAME = ? AND CLASS_ID = ?";

		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			ps = con.prepareStatement(query);
			ps.setString(1, staffName);
			ps.setString(2, subjectName);
			ps.setString(3, standard);

			res = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	// get classes from subject table for creation time table

	public static List<ClassForm> getClasses(String dbName) {

		PreparedStatement ps = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<ClassForm> Standards = null;

		String query = "SELECT DISTINCT(CLASS_NAME)  FROM " + dbName
				+ ".CLASS  ";

		try {
			ps = con.prepareStatement(query);

			resultSet = ps.executeQuery();

			if (resultSet != null) {
				Standards = new ArrayList<ClassForm>();

				while (resultSet.next()) {
					ClassForm standard = new ClassForm();

					standard.setClassName(resultSet.getString("CLASS_NAME"));

					Standards.add(standard);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return Standards;

	}

	// getting occupied seats and total seats

	public static StudentForm getTotalAndOccupiedSeats(String class_Name,
			String dbName) {

		PreparedStatement ps = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		StudentForm seat = null;

		String query = "SELECT TOTAL_SEATS ,COUNT(STUDENT_ID) FROM " + dbName
				+ ".STUDENT WHERE STUDENT_CLASS=?";

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, class_Name);

			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {

				seat = new StudentForm();

				seat.setTotalSeat(resultSet.getInt("TOTAL_SEATS"));
				seat.setOccupiedSeat(resultSet.getString("COUNT(STUDENT_ID)"));

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return seat;
	}

	// update the total seats column from Student

	public static int updateTotalSeatsOfStudent(String className,
			String tSeats, String dbName) {
		PreparedStatement ps = null;
		int res = 0;

		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		String query = "UPDATE " + dbName
				+ ".STUDENT SET TOTAL_SEATS = ? WHERE STUDENT_CLASS = ?";

		try {

			ps = con.prepareStatement(query);
			ps.setString(1, tSeats);
			ps.setString(2, className);
			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return res;
	}

	// method to get all class

	public static List<ClassForm> getAllClasses(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<ClassForm> studentClasses = null;

		String query = "SELECT * FROM " + dbName + ".CLASS ORDER BY CLASS_NAME";
		try {
			preparedStatement = con.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentClasses = new ArrayList<ClassForm>();
				while (resultSet.next()) {
					ClassForm studentClass = new ClassForm();

					studentClass
							.setClassName(resultSet.getString("CLASS_NAME"));
					studentClass.setClassTeacher(resultSet
							.getString("CLASS_TEACHER"));

					studentClasses.add(studentClass);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentClasses;
	}

	// method to get staffList

	public static List<SubjectForm> getStaffForClass(String stdClass,
			String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<SubjectForm> studentClasses = null;

		String query = "SELECT STAFF_NAME from " + dbName
				+ ".SUBJECTS where CLASS_ID=?;";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, stdClass);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentClasses = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm studentClass = new SubjectForm();

					// 22nd July 2016(MAHADEV) TO AVOID NULL VALUES IN SELECT
					// ELEMEMT.
					String staffName = resultSet.getString("STAFF_NAME");
					if (staffName == null) {
						studentClass.setStaffName("NOT ASSIGNED TO SUBJECT");
					} else {
						studentClass.setStaffName(staffName);
					}
					// END
					studentClasses.add(studentClass);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentClasses;
	}

	// Assign class teacher

	public static int assignClassTeacher(String standard, String staffName,
			String dbName) {
		PreparedStatement ps = null;
		int res = 0;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		System.out.println(standard);
		System.out.println(staffName);
		System.out.println(dbName);

		String query = "UPDATE " + dbName
				+ ".CLASS SET CLASS_TEACHER=? WHERE CLASS_NAME=?;";

		try {
			ps = con.prepareStatement(query);
			ps.setString(1, staffName);
			ps.setString(2, standard);

			res = ps.executeUpdate();
			if (res > 0) {
				System.out.println("updated sucessfully");
			} else {
				System.out.println("Fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return res;
	}

	// //method to insert holidays into database

	public static int insertHolidays(HolidaysForm holidaysForm, String dbName) {
		int n = 0;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into " + dbName
					+ ".HOLIDAYS(HD_NAME,HD_DATE,DISCRIPTION) values(?,?,?)");

			ps.setString(1, holidaysForm.getHolidayName());
			ps.setString(2, holidaysForm.getHolidayDate());
			ps.setString(3, holidaysForm.getDiscription());

			n = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n;
	}

	// method to insert subjects

	public static int insertSubjects(SubjectForm subjectForm, String dbName) {
		System.out.println("inside the subject method");
		int n = 0;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into "
					+ dbName
					+ ".SUBJECTS(SUBJ_ID,NAME,CLASS_ID,DESCRIPTION) values(?,?,?,?)");

			ps.setString(1, subjectForm.getSubject_Id());
			ps.setString(2, subjectForm.getSubject_Name());
			ps.setInt(3, subjectForm.getClass_id());
			ps.setString(4, subjectForm.getDiscription());

			n = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n;
	}

	// method to get classmates Details

	public static List<StudentForm> getclassMatesDetalis(String className,
			String dbName) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<StudentForm> studentNamesNames = null;

		String query = "SELECT * FROM " + dbName
				+ ".STUDENT WHERE STUDENT_CLASS=?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, className);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentNamesNames = new ArrayList<StudentForm>();
				while (resultSet.next()) {
					StudentForm student = new StudentForm();
					 System.out.println(resultSet.getString("STUDENT_ID")); 
					student.setStdId(resultSet.getString("STUDENT_ID"));
					student.setStudentName(resultSet.getString("STUDENT_NAME"));
					// 9th July 2016(MAHADEV), FOR AVOIDING NULL VALUES

					String address = resultSet.getString("ADDRESS");
					if (address == null) {
						address = "NA";
					}
					student.setAddress(address);

					String contactNumber = resultSet.getString("CONTACT");
					if (contactNumber == null) {
						contactNumber = "NA";
					}
					student.setContactNum(contactNumber);
					student.setEmail(resultSet.getString("EMAIL"));

					// System.out.println(resultSet.getString("STUDENT_NAME"));
					// System.out.println(resultSet.getString("ADDRESS"));

					studentNamesNames.add(student);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentNamesNames;
	}

	// method to get staffName & subject

	public static List<SubjectForm> getSubjectStaffName(String className,
			String dbName) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<SubjectForm> staffNames = null;

		String query = "SELECT NAME,STAFF_NAME FROM " + dbName
				+ ".SUBJECTS WHERE CLASS_ID=?;";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, className);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				staffNames = new ArrayList<SubjectForm>();
				while (resultSet.next()) {
					SubjectForm student = new SubjectForm();

					student.setStaffName(resultSet.getString("STAFF_NAME"));
					student.setSubject_Name(resultSet.getString("NAME"));

					staffNames.add(student);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return staffNames;
	}

	// leave page

	// method to store leave details by student

	public static int studentLeave(LeaveForm student, String dbName) {

		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		String studentId = null;
		int n = 0;
		String query = "insert INTO "
				+ dbName
				+ ".LEAVES(STUDENT_ID, STD_NAME, STD_CLASS, LV_DATE, REASON) values(?,?,?,?,?)";
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		try {

			String query1 = "select STUDENT_ID from " + dbName
					+ ".STUDENT where STUDENT_NAME=? AND STUDENT_CLASS=?;";

			String studentName = student.getStudentName();
			String studentClass = student.getStudentClass();

			ps = con.prepareStatement(query1);
			ps.setString(1, studentName);
			ps.setString(2, studentClass);
			resultSet = ps.executeQuery();
			if (resultSet != null && resultSet.next()) {

				studentId = resultSet.getString("STUDENT_ID");

			}

			ps = con.prepareStatement(query);
			ps.setString(1, studentId);
			ps.setString(2, student.getStudentName());
			ps.setString(3, student.getStudentClass());
			ps.setString(4, student.getDateOfLeaving());
			ps.setString(5, student.getReason());

			n = ps.executeUpdate();

			if (n > 0) {
				System.out.println("sucessfull");
			} else {
				System.out.println("unsucessfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n;

	}

	// getting parent details
	public static StudentForm getstdParentDetalis(int studentId, String dbName) {
		StudentForm user = null;
		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		query = "SELECT * FROM " + dbName + ".STUDENT WHERE STUDENT_ID = ?";

		try {
			user = new StudentForm();
			ps = connection.prepareStatement(query);
			ps.setInt(1, studentId);

			resultSet = ps.executeQuery();

			if (resultSet != null && resultSet.next()) {

				user.setParentName(resultSet.getString("PARENT_NAME"));
				user.setEmail(resultSet.getString("EMAIL"));
				user.setContactNum(resultSet.getString("CONTACT"));
				user.setAddress(resultSet.getString("ADDRESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return user;
	}

	// to get holiday list

	public static List<HolidaysForm> getHolidayList(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<HolidaysForm> holidays = null;

		String query = "SELECT * FROM " + dbName + ".HOLIDAYS ORDER BY HD_DATE";
		try {
			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				holidays = new ArrayList<HolidaysForm>();
				while (resultSet.next()) {
					HolidaysForm holiday = new HolidaysForm();

					holiday.setHolidayName(resultSet.getString("HD_NAME"));
					holiday.setHolidayDate(resultSet.getString("HD_DATE"));
					holiday.setDiscription(resultSet.getString("DISCRIPTION"));
					holidays.add(holiday);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return holidays;
	}

	// Leave Status

	public static List<LeaveForm> getAdmissionData1(String dbName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		List<LeaveForm> students = null;

		String query = "SELECT * FROM " + dbName + ".LEAVES WHERE STATUS = 'N'";
		try {
			preparedStatement = con.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				students = new ArrayList<LeaveForm>();
				while (resultSet.next()) {
					LeaveForm student = new LeaveForm();

					student.setStudentId(resultSet.getInt("STUDENT_ID"));
					student.setStudentName(resultSet.getString("STD_NAME"));
					student.setStudentClass(resultSet.getString("STD_CLASS"));
					student.setDateOfLeaving(resultSet.getString("LV_DATE"));
					student.setReason(resultSet.getString("REASON"));

					students.add(student);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return students;
	}

	// verifying leave status

	// after verification done , setting verification status to Y .

	public static int verifyByStaff(String studentId, String dbName) {
		String query = "UPDATE " + dbName
				+ ".LEAVES SET STATUS='Y' WHERE STUDENT_ID=?;";
		Connection con = null;
		PreparedStatement preparedStatement = null;
		int n = 0;
		con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, studentId);

			n = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n;
	}

	// getting Event details from notice_board table . and display .

	public static List<Announcements> getEventDetails(String dbName) {
		List<Announcements> eventDetails = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM " + dbName
				+ ".NOTICE_BOARD ORDER BY ANNOUNCED_DATE DESC";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			ps = connection.prepareStatement(query);

			resultSet = ps.executeQuery();

			if (resultSet != null) {
				eventDetails = new ArrayList<Announcements>();
				while (resultSet.next()) {
					Announcements eventdetail = new Announcements();
					eventdetail.setEventId(resultSet.getInt("EVENT_ID"));
					eventdetail.setEventName(resultSet.getString("EVENT_NAME"));
					eventdetail.setStudentClass(resultSet.getString("CLASS"));
					eventdetail.setStudentSection(resultSet
							.getString("SECTION"));
					eventdetail.setEventGoingToHappen(resultSet
							.getString("EVENT_GOING_TO_HAPPEN"));
					eventdetail.setDiscription(resultSet
							.getString("DISCRIPTION"));
					eventdetail.setEventForStaff(resultSet.getString("STAFF"));
					eventdetail.setAnnoncedDate(resultSet
							.getString("ANNOUNCED_DATE"));

					eventDetails.add(eventdetail);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return eventDetails;
	}

	// event deletion process
	public static int eventDeletion(String evantid, String dbName) {
		int n = 0;
		PreparedStatement ps = null;
		Connection con = null;

		String query = "DELETE FROM " + dbName
				+ ".NOTICE_BOARD WHERE EVENT_ID=? ";

		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			ps = con.prepareStatement(query);
			ps.setString(1, evantid);

			n = ps.executeUpdate();

			if (n > 0) {
				System.out.println("Deletion is Success");
			} else {
				System.out.println("Deletion is unsuccesfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return n;
	}

	public static Announcements getEventAnnounceDetail_forEdit(String eventid,
			String dbName) {
		Announcements eventDetails = null;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet resultSet = null;

		 System.out.println(eventid); 

		String query = "SELECT * FROM " + dbName
				+ ".NOTICE_BOARD where EVENT_ID = ? ";

		try {
			con = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			ps = con.prepareStatement(query);
			ps.setString(1, eventid);

			resultSet = ps.executeQuery();
			if (resultSet != null && resultSet.next()) {
				eventDetails = new Announcements();

				eventDetails.setEventName(resultSet.getString("EVENT_NAME"));
				 System.out.println(resultSet.getString("EVENT_NAME")); 
				eventDetails.setStudentClass(resultSet.getString("CLASS"));
				eventDetails.setStudentSection(resultSet.getString("SECTION"));
				eventDetails.setEventGoingToHappen(resultSet
						.getString("EVENT_GOING_TO_HAPPEN"));
				eventDetails.setDiscription(resultSet.getString("DISCRIPTION"));
				eventDetails.setEventForStaff(resultSet.getString("STAFF"));
				eventDetails.setAnnoncedDate(resultSet
						.getString("ANNOUNCED_DATE"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return eventDetails;

	}

	// event updation process

	public static int updateAnnouncements(Announcements announcements,
			String dbName) {
		int count = 0;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		 System.out.println(announcements.getEventId()); 

		String query = "UPDATE "
				+ dbName
				+ ".NOTICE_BOARD SET EVENT_NAME=?,CLASS=?,SECTION=?,ANNOUNCED_DATE=?,EVENT_GOING_TO_HAPPEN=?,DISCRIPTION=?,STAFF=?  WHERE EVENT_ID=?";

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, announcements.getEventName());
			preparedStatement.setString(2, announcements.getStudentClass());
			preparedStatement.setString(3, announcements.getStudentSection());
			preparedStatement.setString(4, announcements.getAnnoncedDate());
			preparedStatement.setString(5,
					announcements.getEventGoingToHappen());
			preparedStatement.setString(6, announcements.getDiscription());
			preparedStatement.setString(7, announcements.getEventForStaff());
			preparedStatement.setInt(8, announcements.getEventId());

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return count;

	}

	// SELECTING EVENTS ASSIGNED TO STAFF

	public static List<Announcements> getEventDetailsForStaff(String dbName) {
		List<Announcements> eventDetails = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM " + dbName
				+ ".NOTICE_BOARD WHERE STAFF='Y'";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			ps = connection.prepareStatement(query);

			resultSet = ps.executeQuery();

			if (resultSet != null) {
				eventDetails = new ArrayList<Announcements>();
				while (resultSet.next()) {
					Announcements eventdetail = new Announcements();
					 System.out.println(resultSet.getInt("EVENT_ID")); 
					eventdetail.setEventId(resultSet.getInt("EVENT_ID"));
					eventdetail.setEventName(resultSet.getString("EVENT_NAME"));
					eventdetail.setStudentClass(resultSet.getString("CLASS"));
					eventdetail.setStudentSection(resultSet
							.getString("SECTION"));
					eventdetail.setEventGoingToHappen(resultSet
							.getString("EVENT_GOING_TO_HAPPEN"));
					eventdetail.setDiscription(resultSet
							.getString("DISCRIPTION"));
					eventdetail.setEventForStaff(resultSet.getString("STAFF"));
					eventdetail.setAnnoncedDate(resultSet
							.getString("ANNOUNCED_DATE"));

					eventDetails.add(eventdetail);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return eventDetails;
	}

	
	 * // event deletion process public static int eventDeletion(String evantid,
	 * String dbName) { int n = 0; PreparedStatement ps = null; Connection con =
	 * null;
	 * 
	 * String query = "DELETE FROM " + dbName +
	 * ".NOTICE_BOARD WHERE EVENT_ID=? ";
	 * 
	 * try { con = ConnectDatabase.getConnection(AppConfig.MASTER_DB); ps =
	 * con.prepareStatement(query); ps.setString(1, evantid);
	 * 
	 * n = ps.executeUpdate();
	 * 
	 * if (n > 0) { System.out.println("Deletion is Success"); } else {
	 * System.out.println("Deletion is unsuccesfull"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { try { if(con !=
	 * null) con.close(); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * return n; }
	 

	public static StudentForm getStudentStatus(String dbName, String userName) {
		StudentForm studentForm = null;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "select * from " + dbName + ".STUDENT";
		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			studentForm = new StudentForm();
			if (resultSet != null && resultSet.next()) {
				studentForm.setVerifyStatus(resultSet
						.getString("VERIFICATION_STATUS"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentForm;

	}

	public static String getImageNamesOfEvents(String eventId, String dbName) {
		String imageNames = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String query = "SELECT IMAGEOFEVENTS FROM " + dbName
				+ ".EVENTS_IMAGE WHERE EVENTS_TYPE= ? ";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, eventId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				imageNames = resultSet.getString("IMAGEOFEVENTS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return imageNames;
	}

	// Ensure Staff bank account details are empty or filled
	// 9th July 2016(MAHADEV),TO MAKE PROPER DATA DISPLAY IF PRESENT ELSE
	// DISPLAY NA

	
	 * public static void main(String[] args) {
	 * ensureStaffBankDetails("edsys365_MZSCHOOL_30","sarojaniLila"); }
	 
	public static StaffForm ensureStaffBankDetails(String dbName,
			String userName) {
		int count = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		StaffForm staffBankDetails = null;

		String query = "SELECT ACCOUNT_NUMBER,ACCOUNT_NAME,BRANCH_NAME,IFSC_CODE,BANK_ADDRESS FROM "
				+ dbName + ".STAFF WHERE USER_NAME = ? ";

		staffBankDetails = new StaffForm();
		try {

			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {

				String accountNumber = resultSet.getString("ACCOUNT_NUMBER");
				if (accountNumber == null) {
					accountNumber = "NA";
					count++;
				}
				staffBankDetails.setAccountNumber(accountNumber);
				System.out.println("account number ="
						+ staffBankDetails.getAccountNumber());

				String accountName = resultSet.getString("ACCOUNT_NAME");

				if (accountName == null) {
					accountName = "NA";
					count++;
				}
				staffBankDetails.setAccountName(accountName);
				System.out.println("account name ="
						+ staffBankDetails.getAccountName());

				String branchName = resultSet.getString("BRANCH_NAME");
				if (branchName == null) {
					branchName = "NA";
					count++;
				}
				staffBankDetails.setBranchName(branchName);
				System.out.println("branch name ="
						+ staffBankDetails.getBranchName());

				String ifscCode = resultSet.getString("IFSC_CODE");
				if (ifscCode == null) {
					ifscCode = "NA";
					count++;
				}
				staffBankDetails.setIfscCode(ifscCode);
				System.out.println("ifsc name ="
						+ staffBankDetails.getIfscCode());

				String bankAddress = resultSet.getString("BANK_ADDRESS");
				if (bankAddress == null) {
					bankAddress = "NA";
					count++;
				}
				staffBankDetails.setBankAddress(bankAddress);
				System.out.println("Bank Address ="
						+ staffBankDetails.getBankAddress());

				staffBankDetails.setCountEmptyDetails(count);
				System.out.println("count value="
						+ staffBankDetails.getContactNum());
			}

		} catch (Exception e) {
			e.printStackTrace();
			String error = e.getMessage();
			staffBankDetails.setErrormsg(error);
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
				staffBankDetails.setErrormsg(e.getMessage().toString());

			}
		}

		return staffBankDetails;
	}

	// getting salary slips names

	public static String getSalarySlips(int staffId, String dbName) {
		String salarySlipNames = "";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String query = "SELECT SALARY_SLIPS FROM " + dbName
				+ ".STAFF WHERE STAFF_ID= ? ";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, staffId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				 System.out.println(resultSet.getString("SALARY_SLIPS")); 
				salarySlipNames = resultSet.getString("SALARY_SLIPS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return salarySlipNames;
	}

	// method to get unpaid students
	
	 * public static void main(String[] args) { System.out.println("START");
	 * unPaidStudents("edsys365_MZSCHOOL_30"); System.out.println("END"); }
	 

	public static List<StudentForm> unPaidStudents(String dbName) {

		System.out.println("Inside new method.");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<StudentForm> unpaidStudents = null;

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		String query = "SELECT F.CLASS_ID, (F.FEE+F.ADMISION_FEE) AS ACTUAL_FEE, SF.STUDENT_ID, (SF.PAID_FEE+SF.ADMISION_FEE) AS TOTAL_PAIDFEE, SF.NOTES "
				+ " FROM "
				+ dbName
				+ ".STUDENT_FEE SF, "
				+ dbName
				+ ".FEE_INFO F "
				+ " WHERE F.CLASS_ID = SF.STUDENT_CLASS "
				+ " HAVING ACTUAL_FEE > TOTAL_PAIDFEE";

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				unpaidStudents = new ArrayList<StudentForm>();
				while (resultSet.next()) {
					StudentForm student = new StudentForm();

					student.setStdId(resultSet.getString("STUDENT_ID"));
					System.out.println("Student id in un paid students"
							+ resultSet.getString("STUDENT_ID"));
					student.setStudentClass(resultSet.getString("CLASS_ID"));
					student.setPaidfeeStudent(resultSet
							.getString("TOTAL_PAIDFEE"));
					student.setDueFee(String.valueOf((Double
							.parseDouble(resultSet.getString("ACTUAL_FEE")) - Double
							.parseDouble(resultSet.getString("TOTAL_PAIDFEE")))));

					unpaidStudents.add(student);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return unpaidStudents;
	}

	public static List<StudentForm> unPaidStudentsV1(String dbName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + dbName + ".STUDENT_FEE";

		System.out.println("Query ==> " + query);

		List<StudentForm> studentForms = null;

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			studentForms = new ArrayList<StudentForm>();
			System.out.println("getting unpaid students start");
			if (resultSet != null) {
				System.out.println("getting unpaid students start - in if");
				while (resultSet.next()) {
					System.out
							.println("getting unpaid students start - in while");
					StudentForm studentForm1 = new StudentForm();
					int stdId = resultSet.getInt("STUDENT_ID");
					String stdClass = resultSet.getString("STUDENT_CLASS");
					String paidAdmissionFee = resultSet
							.getString("ADMISION_FEE");
					String paidFee = resultSet.getString("PAID_FEE");

					int paidAdminFee = Integer.parseInt(paidAdmissionFee);

					int paidFeeStudent = Integer.parseInt(paidFee);

					StudentForm studentForm = getfeeDetails(dbName, stdClass);

					String actFee = studentForm.getActFee();

					String actadmissiobFee = studentForm.getAdmissionFee();

					int actFeetoPay = Integer.parseInt(actFee);
					int actAdminFeetoPay = Integer.parseInt(actadmissiobFee);

					
					 * System.out.println(paidFee+"--"+AdmissionFee);
					 * 
					 * System.out.println(actFeSYsoe+"   "+actadmissiobFee);
					 

					if (paidAdminFee < actAdminFeetoPay
							|| paidFeeStudent < actFeetoPay) {
						int remAdmissFee = actAdminFeetoPay - paidAdminFee;
						int remFe = actFeetoPay - paidFeeStudent;

						int duefee = remAdmissFee + remFe;
						int paidFeeForStudent = paidAdminFee + paidFeeStudent;
						String dueFee = Integer.toString(duefee);
						String paidfeeStudent = Integer
								.toString(paidFeeForStudent);

						String remAdmissionFee = Integer.toString(remAdmissFee);

						String remFee = Integer.toString(remFe);

						studentForm1.setStudentId(stdId);
						studentForm1.setStudentClass(stdClass);
						
						 * studentForm1.setRemAdmissionFee(remAdmissionFee);
						 * 
						 * studentForm1.setRemFee(remFee);
						 
						studentForm1.setDueFee(dueFee);
						studentForm1.setPaidfeeStudent(paidfeeStudent);

						System.out.println(stdId + " " + stdClass + " "
								+ dueFee + " " + paidfeeStudent);

						studentForms.add(studentForm1);
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return studentForms;

	}

	public static StudentForm getfeeDetails(String dbName, String stdClass) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from " + dbName + ".FEE_INFO where CLASS_ID="
				+ stdClass;
		System.out.println("Query ==> " + query);

		StudentForm studentForm = null;

		connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				studentForm = new StudentForm();
				while (resultSet.next()) {
					studentForm.setAdmissionFee(resultSet
							.getString("ADMISION_FEE"));
					studentForm.setActFee(resultSet.getString("FEE"));

					
					 * System.out.println(resultSet.getString("ADMISION_FEE"));
					 * System.out.println(resultSet.getString("FEE"));
					 
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return studentForm;

	}

	// student feedback insertion

	public static int studentFeedbackInsertion(StudentForm studentFeedback,
			String dbName, String studentId) {
		int n = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO "
				+ dbName
				+ ".STUDENT_FEEDBACK (STUDY_HOURS, PARENT_SUPPORT, HOBBIES, TEACHER_HELP, STUDENT_ID) VALUES (?,?,?,?,?)";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, studentFeedback.getStudyHours());
			preparedStatement.setString(2, studentFeedback.getParentSupport());
			preparedStatement.setString(3, studentFeedback.getHobbies());
			preparedStatement.setString(4,
					studentFeedback.getTeacherRectifyDoughts());
			preparedStatement.setString(5, studentId);

			n = preparedStatement.executeUpdate();

			if (n > 0) {
				System.out.println("Updation succes");
			} else {
				System.out.println("updation failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return n;
	}

	public static StudentForm getStudentFeedBack(String studentId, String dbName) {
		StudentForm studentFeedBack = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + dbName
				+ ".STUDENT_FEEDBACK WHERE STUDENT_ID=?";

		try {

			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, studentId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				studentFeedBack = new StudentForm();

				studentFeedBack.setStudyHours(resultSet
						.getString("STUDY_HOURS"));
				studentFeedBack.setParentSupport(resultSet
						.getString("PARENT_SUPPORT"));
				studentFeedBack.setHobbies(resultSet.getString("HOBBIES"));
				studentFeedBack.setTeacherRectifyDoughts(resultSet
						.getString("TEACHER_HELP"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return studentFeedBack;
	}

	// all staff for mailing
	public static List<StaffForm> allStaffMembers(String dbName) {
		List<StaffForm> allStaff = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + dbName + ".STAFF";
		try {

			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				allStaff = new ArrayList<StaffForm>();
				while (resultSet.next()) {
					StaffForm staff = new StaffForm();
					staff.setUserName(resultSet.getString("USER_NAME"));
					staff.setFirstName(resultSet.getString("FIRST_NAME"));
					staff.setLastName(resultSet.getString("LAST_NAME"));

					allStaff.add(staff);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return allStaff;
	}

	// class 1 students for mailing
	public static List<StudentForm> getStandardDetails(String dbName,
			String classNum) {
		List<StudentForm> allStudent = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String query = "SELECT * FROM " + dbName
				+ ".STUDENT WHERE STUDENT_CLASS=?";

		try {
			connection = ConnectDatabase.getConnection(dbName);
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, classNum);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				allStudent = new ArrayList<StudentForm>();
				while (resultSet.next()) {
					StudentForm student = new StudentForm();
					student.setStdId(resultSet.getString("STUDENT_ID"));
					student.setStudentName(resultSet.getString("STUDENT_NAME"));
					student.setParentName(resultSet.getString("PARENT_NAME"));

					allStudent.add(student);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return allStudent;
	}

	
	 * // class 2 students for mailing public static List<StudentForm>
	 * getStandardTwoDetails(String dbName){ List<StudentForm> allStudent =
	 * null; PreparedStatement preparedStatement = null; ResultSet resultSet =
	 * null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='2'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * 
	 * // class 3 students for maiing
	 * 
	 * public static List<StudentForm> getStandardThreeDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='3'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * // class 4 students for mailing
	 * 
	 * public static List<StudentForm> getStandardFourDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='4'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * // class 5 students for mailing
	 * 
	 * public static List<StudentForm> getStandardFiveDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='5'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * // class 6 students for mailing
	 * 
	 * public static List<StudentForm> getStandardSixDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='6'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * // class 7 details for student for mailing
	 * 
	 * public static List<StudentForm> getStandardSevenDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='7'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 * 
	 * // class 8 students for mailing
	 * 
	 * public static List<StudentForm> getStandardEightDetails(String dbName){
	 * List<StudentForm> allStudent = null; PreparedStatement preparedStatement
	 * = null; ResultSet resultSet = null; Connection connection = null; String
	 * query="SELECT * FROM STUDENT WHERE STUDENT_CLASS='8'";
	 * 
	 * try { connection = ConnectDatabase.getConnection(dbName);
	 * preparedStatement = connection.prepareStatement(query);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if(resultSet!=null){
	 * allStudent = new ArrayList<StudentForm>(); while(resultSet.next()){
	 * StudentForm student = new StudentForm();
	 * student.setStdId(resultSet.getString("STUDENT_ID"));
	 * student.setStudentName(resultSet.getString("STUDENT_NAME"));
	 * student.setParentName(resultSet.getString("PARENT_NAME"));
	 * 
	 * allStudent.add(student); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } } return allStudent; }
	 

	// getting user type based on student id
	public static String getUserType(String dbName, String userName) {
		String userType = "";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		 System.out.println(userName); 
		String query = "SELECT USER_TYPE FROM " + dbName
				+ ".USER_LOGIN WHERE LOGIN_NAME=?";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				 System.out.println(resultSet.getString("USER_TYPE")); 
				userType = resultSet.getString("USER_TYPE");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return userType;
	}

	// insertion of group names

	public static int createGroup(String groupName, String groupNature,
			String[] staffMemb, String[] stC1, String[] stC2, String[] stC3,
			String[] stC4, String[] stC5, String[] stC6, String[] stC7,
			String[] stC8, String remarks, String dbName) {
		int n = 0;
		int groupId = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String requestId = String.valueOf(System.currentTimeMillis());
		String query = "INSERT INTO "
				+ dbName
				+ ".MESSAGE_GROUP (GROUP_NAME, NATURE,REQUEST_ID) VALUES(?,?,?)";
		String query1 = "SELECT GROUP_ID FROM " + dbName
				+ ".MESSAGE_GROUP WHERE REQUEST_ID=?";
		String query2 = "INSERT INTO "
				+ dbName
				+ ".GROUP_USER (GROUP_ID, USER_ID, REMARKS, USER_TYPE) VALUES (?,?,?,?)";

		String user_type_for_staff = "";
		String user_type_for_class1 = "";
		String user_type_for_class2 = "";
		String user_type_for_class3 = "";
		String user_type_for_class4 = "";
		String user_type_for_class5 = "";
		String user_type_for_class6 = "";
		String user_type_for_class7 = "";
		String user_type_for_class8 = "";
		// for staff
		if (staffMemb != null) {
			for (String staff : staffMemb) {
				if (user_type_for_staff.length() == 0) {
					user_type_for_staff = getUserType(dbName, staff);
					System.out.println(user_type_for_staff);
				}
			}
		}
		// for students of class 1
		if (stC1 != null) {
			for (String staff : stC1) {
				if (user_type_for_class1.length() == 0) {
					user_type_for_class1 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class1);
				}
			}
		}
		// for students of class 2
		if (stC2 != null) {
			for (String staff : stC2) {
				if (user_type_for_class2.length() == 0) {
					user_type_for_class2 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class2);
				}
			}
		}
		// for students of class 3
		if (stC3 != null) {
			for (String staff : stC3) {
				if (user_type_for_class3.length() == 0) {
					user_type_for_class3 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class3);
				}
			}
		}
		// for students of class 4
		if (stC4 != null) {
			for (String staff : stC4) {
				if (user_type_for_class4.length() == 0) {
					user_type_for_class4 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class4);
				}
			}
		}
		// for students of class 5
		if (stC5 != null) {
			for (String staff : stC5) {
				if (user_type_for_class5.length() == 0) {
					user_type_for_class5 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class5);
				}
			}
		}
		// for students of class 6
		if (stC6 != null) {
			for (String staff : stC6) {
				if (user_type_for_class6.length() == 0) {
					user_type_for_class6 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class6);
				}
			}
		}
		// for students of class 7
		if (stC7 != null) {
			for (String staff : stC7) {
				if (user_type_for_class7.length() == 0) {
					user_type_for_class7 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class7);
				}
			}
		}
		// for students of class 8
		if (stC8 != null) {
			for (String staff : stC8) {
				if (user_type_for_class8.length() == 0) {
					user_type_for_class8 = DBUtil.getUserType(dbName, staff);
					System.out.println(user_type_for_class8);
				}
			}
		}

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, groupName);
			preparedStatement.setString(2, groupNature);
			preparedStatement.setString(3, requestId);
			n = preparedStatement.executeUpdate();
			if (n > 0) {
				System.out
						.println("Insertion in message gorup is success full");
				try {
					preparedStatement = connection.prepareStatement(query1);
					preparedStatement.setString(1, requestId);
					resultSet = preparedStatement.executeQuery();

					if (resultSet != null && resultSet.next()) {

						System.out.println(resultSet.getInt(1));
						groupId = resultSet.getInt(1);
						System.out.println("Fetching group id is successfull");

						try {
							connection.setAutoCommit(false);
							preparedStatement = connection
									.prepareStatement(query2);
							if (staffMemb != null) {
								for (String staff : staffMemb) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_staff);
									preparedStatement.addBatch();
								}
							}
							if (stC1 != null) {
								for (String staff : stC1) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class1);
									preparedStatement.addBatch();
								}
							}

							if (stC2 != null) {
								for (String staff : stC2) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class2);
									preparedStatement.addBatch();
								}
							}

							if (stC3 != null) {
								for (String staff : stC3) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class3);
									preparedStatement.addBatch();
								}
							}

							if (stC4 != null) {
								for (String staff : stC4) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class4);
									preparedStatement.addBatch();
								}
							}

							if (stC5 != null) {
								for (String staff : stC5) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class5);
									preparedStatement.addBatch();
								}
							}
							if (stC6 != null) {
								for (String staff : stC6) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class6);
									preparedStatement.addBatch();
								}
							}
							if (stC7 != null) {
								for (String staff : stC7) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class7);
									preparedStatement.addBatch();
								}
							}
							if (stC8 != null) {
								for (String staff : stC8) {
									preparedStatement.setInt(1, groupId);
									preparedStatement.setString(2, staff);
									preparedStatement.setString(3, remarks);
									preparedStatement.setString(4,
											user_type_for_class8);
									preparedStatement.addBatch();
								}
							}
							int[] count = preparedStatement.executeBatch();

							// Explicitly commit statements to apply changes
							connection.commit();
							if (count != null) {
								System.out.println("Successfull");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return n;
	}

	// get group names
	public static List<GroupMail> availableGroups(String dbName) {
		List<GroupMail> listOfGroups = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dbName + ".MESSAGE_GROUP";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				listOfGroups = new ArrayList<GroupMail>();
				while (resultSet.next()) {
					GroupMail groups = new GroupMail();
					groups.setGroupId(resultSet.getInt(1));
					groups.setMailGroupName(resultSet.getString("GROUP_NAME"));
					groups.setMailGroupNature(resultSet.getString("NATURE"));
					// 8th July 2016(MAHADEV), To display only date
					String createdDate = resultSet.getString("CREATED_DATE");
					String createdOnlyDate = createdDate.substring(0, 10);
					groups.setGroupCreatedDate(createdOnlyDate);
					// End
					System.out.println(resultSet.getInt(1));
					System.out.println(resultSet.getString("GROUP_NAME"));
					System.out.println(resultSet.getString("NATURE"));
					System.out.println(resultSet.getString("CREATED_DATE"));
					listOfGroups.add(groups);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listOfGroups;
	}

	// compose mail for one to one
	public static void composeMail(String dbName, ComposeMail data, String toId) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String requestId = String.valueOf(System.currentTimeMillis());
		String query = "INSERT INTO "
				+ dbName
				+ ".MESSAGES (FROM_USER, TO_USER, BODY, SUBJECT, STATUS) VALUES (?,?,?,?,?)";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, data.getFromMailId());
			preparedStatement.setString(2, toId);
			preparedStatement.setString(3, data.getMessageBody());
			preparedStatement.setString(4, data.getdSubject());
			preparedStatement.setString(5, "SUCCESS");

			int n = preparedStatement.executeUpdate();

			if (n > 0) {
				System.out.println("Succss");
			} else {
				System.out.println("fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	// for one to many users

	public static void composeMailtoMany(String dbName, ComposeMail data,
			String[] toId) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String query = "INSERT INTO "
				+ dbName
				+ ".MESSAGES (FROM_USER, TO_USER, BODY, SUBJECT, STATUS) VALUES (?,?,?,?,?)";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			connection.setAutoCommit(false);

			for (String arr : toId) {
				preparedStatement.setString(1, data.getFromMailId().trim());// 9th
																			// July
																			// 2016(MAHADEV),BECAUSE
																			// OF
																			// SPACE
																			// IT
																			// IS
																			// UNABLE
																			// TO
																			// FETCH
																			// DATA
				preparedStatement.setString(2, arr.trim());// 9th July
															// 2016(MAHADEV),BECAUSE
															// OF SPACE IT IS
															// UNABLE TO FETCH
															// DATA
				preparedStatement.setString(3, data.getMessageBody());
				preparedStatement.setString(4, data.getdSubject());
				preparedStatement.setString(5, "SUCCESS");
				preparedStatement.addBatch();
			}

			int[] n = preparedStatement.executeBatch();
			connection.commit();
			if (n != null) {
				System.out.println("Succss");
			} else {
				System.out.println("fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public static List<ComposeMail> getSentMail(String dbName, String userName) {
		List<ComposeMail> sentMails = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dbName
				+ ".MESSAGES WHERE FROM_USER = ? ";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				sentMails = new ArrayList<ComposeMail>();
				while (resultSet.next()) {
					ComposeMail sentMail = new ComposeMail();
					sentMail.setMessageId(resultSet.getInt("MESSAGE_ID"));
					sentMail.setUserDMailId(resultSet.getString("TO_USER"));
					sentMail.setdSubject(resultSet.getString("SUBJECT"));
					System.out.println(resultSet.getString("TO_USER"));
					System.out.println(resultSet.getString("SUBJECT"));
					sentMails.add(sentMail);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return sentMails;
	}

	
	 * public static void main(String[] args) {
	 * getSentMailBodyDetails("schoolmanagement",9); }
	 

	// get message content
	public static ComposeMail getSentMailBodyDetails(String dbName,
			int messageId) {
		ComposeMail sentMail = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dbName
				+ ".MESSAGES WHERE MESSAGE_ID = ? ";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, messageId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				while (resultSet.next()) {
					sentMail = new ComposeMail();
					sentMail.setMessageId(resultSet.getInt("MESSAGE_ID"));
					sentMail.setFromMailId(resultSet.getString("FROM_USER"));
					sentMail.setUserDMailId(resultSet.getString("TO_USER"));
					sentMail.setdSubject(resultSet.getString("SUBJECT"));
					sentMail.setMessageBody(resultSet.getString("BODY"));
					System.out.println(resultSet.getString("BODY"));
					System.out.println(resultSet.getString("TO_USER"));
					System.out.println(resultSet.getString("SUBJECT"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return sentMail;
	}

	// in box mail

	
	 * public static void main(String[] args) {
	 * getInboxMail("edsys365_MZSCHOOL_30","sarojaniLila"); }
	 

	public static List<ComposeMail> getInboxMail(String dbName, String userName) {
		List<ComposeMail> inboxMails = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dbName + ".MESSAGES WHERE TO_USER = ? ";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				inboxMails = new ArrayList<ComposeMail>();
				while (resultSet.next()) {
					ComposeMail inboxMail = new ComposeMail();
					inboxMail.setReadStatus(resultSet.getString("READ_STATUS"));
					inboxMail.setMessageId(resultSet.getInt("MESSAGE_ID"));
					inboxMail.setFromMailId(resultSet.getString("FROM_USER"));
					inboxMail.setdSubject(resultSet.getString("SUBJECT"));
					System.out.println(resultSet.getString("FROM_USER"));
					System.out.println(resultSet.getString("SUBJECT"));
					inboxMails.add(inboxMail);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return inboxMails;
	}

	// display INBOX mail
	public static ComposeMail getInboxMailBodyDetails(String dbName,
			int messageId) {
		ComposeMail sentMail = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dbName
				+ ".MESSAGES WHERE MESSAGE_ID = ? ";
		String query = "UPDATE " + dbName
				+ ".MESSAGES SET READ_STATUS='R' WHERE MESSAGE_ID=?";
		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, messageId);
				int n = preparedStatement.executeUpdate();
				if (n > 0) {
					System.out.println("updation is successfull");
				} else {
					System.out.println("Updation is failed");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, messageId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					sentMail = new ComposeMail();
					sentMail.setMessageId(resultSet.getInt("MESSAGE_ID"));
					sentMail.setFromMailId(resultSet.getString("FROM_USER"));
					sentMail.setUserDMailId(resultSet.getString("TO_USER"));
					sentMail.setdSubject(resultSet.getString("SUBJECT"));
					sentMail.setMessageBody(resultSet.getString("BODY"));
					System.out.println(resultSet.getString("BODY"));
					System.out.println(resultSet.getString("TO_USER"));
					System.out.println(resultSet.getString("SUBJECT"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return sentMail;
	}

	// method to insert subjects

	
	 * public static int insertSubjects(SubjectForm subjectForm, String dbName)
	 * { System.out.println("inside the subject method"); int n = 0; Connection
	 * con = getConnection(dbName); PreparedStatement ps = null;
	 * 
	 * try { ps = con.prepareStatement(
	 * "insert into subjects(SUBJ_ID,NAME,CLASS_ID,DESCRIPTION) values(?,?,?,?)"
	 * );
	 * 
	 * ps.setString(1, subjectForm.getSubject_Id()); ps.setString(2,
	 * subjectForm.getSubject_Name()); ps.setInt(3, subjectForm.getClass_id());
	 * ps.setString(4, subjectForm.getDiscription());
	 * System.out.println(holidaysForm.getHolidayName());
	 * 
	 * n = ps.executeUpdate();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally { try { if(con
	 * != null) con.close(); } catch (Exception e2) { e2.printStackTrace(); } }
	 * return n; }
	 
	// get list of subjects

	
	 * public static List<SubjectForm> getListOfSubjects(String className,String
	 * dbName) { System.out.println(className); int classId =
	 * Integer.parseInt(className); PreparedStatement preparedStatement = null;
	 * ResultSet resultSet = null; Connection con =
	 * ConnectDatabase.getConnection(dbName);
	 * 
	 * List<SubjectForm> subjects = null;
	 * 
	 * String query = "SELECT * FROM subjects WHERE CLASS_ID = ?"; try {
	 * preparedStatement = con.prepareStatement(query);
	 * preparedStatement.setInt(1, classId);
	 * 
	 * resultSet = preparedStatement.executeQuery(); if (resultSet != null) {
	 * subjects = new ArrayList<SubjectForm>(); while (resultSet.next()) {
	 * SubjectForm subject = new SubjectForm();
	 * 
	 * subject.setSubject_Id(resultSet.getString("SUBJ_ID"));
	 * subject.setSubject_Name(resultSet.getString("NAME"));
	 * subject.setClass_id(resultSet.getInt("CLASS_ID"));
	 * subject.setDiscription(resultSet.getString("DESCRIPTION"));
	 * 
	 * System.out.println(resultSet.getString("SUBJ_ID") + " " +
	 * resultSet.getString("NAME") + " " + resultSet.getInt("CLASS_ID") + " " +
	 * resultSet.getString("DESCRIPTION")); subjects.add(subject); } }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { try { if(con != null) con.close(); }
	 * catch (Exception e2) { e2.printStackTrace(); } }
	 * 
	 * return subjects; }
	 

	// getting class teachers details for student

	
	 * public static void main(String[] args) {
	 * getClassTeacherDetails("1","edsys365_MZSCHOOL_30"); }
	 
	public static ClassForm getClassTeacherDetails(String className,
			String dbName) {
		ClassForm staffDetailsForStudent = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		String query = "SELECT CLASS_TEACHER FROM " + dbName
				+ ".CLASS WHERE CLASS_NAME=? ";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, className);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				staffDetailsForStudent = new ClassForm();
				System.out.println("Class TeacherName "
						+ resultSet.getString("CLASS_TEACHER"));

				String classTeacherName = resultSet.getString("CLASS_TEACHER");

				if (classTeacherName == null) {
					staffDetailsForStudent.setClassTeacher("NOT ASSIGNED");
				} else {
					staffDetailsForStudent.setClassTeacher(classTeacherName);
				}

				System.out.println("class teacher name"
						+ staffDetailsForStudent.getClassTeacher());
				// 14th July 2016(MAHADEV)
				
				 * String profilePicName =
				 * getClassTeacherProfilePicNameOrStaffProfilePic
				 * (resultSet.getString("CLASS_TEACHER"),dbName); // 14th July
				 * 2016(MAHADEV) , Getting class Teacher user id for getting
				 * image from that folder . int staffUserId =
				 * getClassTeacherUserId
				 * (resultSet.getString("CLASS_TEACHER"),dbName);
				 * staffDetailsForStudent.setClassTeacherUserId(staffUserId);
				 * System.out.println("class teacher id = "+
				 * staffDetailsForStudent.getClassTeacherUserId()); // End
				 * if(profilePicName == null || profilePicName.isEmpty()){
				 * staffDetailsForStudent
				 * .setClassTeacherProfilePic("staff_default.png"); // 14th July
				 * 2016(MAHADEV) }else{
				 * staffDetailsForStudent.setClassTeacherProfilePic
				 * (profilePicName); }
				 * 
				 * System.out.println("Porfile Pic Name=> "+staffDetailsForStudent
				 * .getClassTeacherProfilePic());
				 
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return staffDetailsForStudent;
	}

	// 14th July 2016(MAHADEV) , Getting class Teacher user id for getting image
	// from that folder . No Need Now
	
	 * public static int getClassTeacherUserId(String userName,String dbName){
	 * int staffId = 0; PreparedStatement preparedStatement = null; ResultSet
	 * resultSet = null; Connection connection = null; String
	 * query="SELECT STAFF_ID FROM " + dbName + ".STAFF WHERE USER_NAME=?";
	 * 
	 * try { connection = getConnection(dbName); preparedStatement =
	 * connection.prepareStatement(query); preparedStatement.setString(1,
	 * userName); resultSet = preparedStatement.executeQuery();
	 * 
	 * if(resultSet!=null && resultSet.next()){ System.out.println("staff id "+
	 * resultSet.getInt("STAFF_ID")); staffId = resultSet.getInt("STAFF_ID"); }
	 * } catch (Exception e) { e.printStackTrace(); }finally { try {
	 * ConnectDatabase.closeConnection(connection); } catch (Exception e2) {
	 * e2.printStackTrace(); } }
	 * 
	 * return staffId; }
	 
	// End of staff id

	// getting class teacher profile picture name
	public static String getClassTeacherProfilePicNameOrStaffProfilePic(
			String classTeacherName, String dbName) {
		String picName = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		String query = "SELECT PROFILE_PIC FROM " + dbName
				+ ".STAFF WHERE USER_NAME=?";

		try {
			connection = getConnection(dbName); // 14th July 2016(MAHADEV) , it
												// is needed for avoiding result
												// set is closed error
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, classTeacherName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				System.out.println("Profile Pic Name "
						+ resultSet.getString("PROFILE_PIC"));

				picName = resultSet.getString("PROFILE_PIC");

				if (picName == null) {
					picName = "staff_default.png";
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return picName;
	}

	// checking staff name exist in database or not.

	
	 * public static void main(String[] args) {
	 * isStaffUserNameExist("maha55","schoolmanagement"); }
	 

	public static boolean isStaffUserNameExist(String staffName,
			String dataBaseName) {
		boolean flag = false;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM " + dataBaseName
				+ ".STAFF WHERE USER_NAME=?";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, staffName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				flag = true;
				System.out.println(flag);
			} else {
				flag = false;
				System.out.println(flag);
			}

		} catch (Exception e) {
			System.err.println("Erro while checking the staff name ");
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return flag;
	}

	// Holding a student for a reason , hear we are storing the reason

	
	 * public static void main(String[] args) {
	 * studentHold("somethin","3","mzschool_27"); }
	 

	public static int studentHold(String reason, String studentId,
			String dataBaseName) {
		int status = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String query = "UPDATE "
				+ dataBaseName
				+ ".STUDENT SET VERIFICATION_STATUS='H',REASONFORHOLD=? WHERE STUDENT_ID=?";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, reason);
			preparedStatement.setString(2, studentId);
			status = preparedStatement.executeUpdate();
			if (status > 0) {
				System.out.println("success");
			} else {
				System.out.println("Failed");
			}

		} catch (Exception e) {
			System.err.println("error in updating the reason");
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return status;
	}

	// 14th July 2016(MAHADEV) , TO CHECK ROLL NUMBER IS EXIST OR NOT
	public static void main(String[] args) {
		isRollNumberExist("4", "edsys365_MZSCHOOL_30");
	}

	public static boolean isRollNumberExist(String rollNumber, String dbName) {
		boolean status = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String sql = "SELECT STUDENT_ID FROM " + dbName
				+ ".STUDENT WHERE ROLL_NUMBER=?";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, rollNumber);
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				status = true;
				System.out.println("roll number exist = " + status);
			} else {
				status = false;
				System.out.println("roll number dosent exist = " + status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDatabase.closeConnection(connection);
		}

		return status;
	}

	 changes done by SUDARSHAN on 17/7/2016 

	public static List<StaffForm> getStaffEligibleClasses(String dbName,
			String staffName) {
		List<StaffForm> staffForm = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT(CLASS_ID) FROM SUBJECTS WHERE STAFF_NAME=?";

		connection = getConnection(dbName);

		staffForm = new ArrayList<StaffForm>();

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, staffName);
			resultSet = preparedStatement.executeQuery();

			while (resultSet != null && resultSet.next()) {
				StaffForm staff = new StaffForm();

				staff.setClasses(resultSet.getString("CLASS_ID"));

				staffForm.add(staff);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return staffForm;
	}

	public static List<StaffForm> getStaffEligibleSubjects(String dbName,
			String className, String staffName) {
		List<StaffForm> staffForms = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String query = "SELECT NAME FROM SUBJECTS WHERE STAFF_NAME=? AND CLASS_ID=?";

		connection = getConnection(dbName);
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, staffName);
			preparedStatement.setString(2, className);
			resultSet = preparedStatement.executeQuery();
			staffForms = new ArrayList<StaffForm>();
			while (resultSet != null && resultSet.next()) {
				StaffForm staffForm = new StaffForm();

				staffForm.setDealSubject(resultSet.getString("NAME"));
				staffForms.add(staffForm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staffForms;

	}

	// changes done by Sudarshan to get student Subjects

	public static List<StudentForm> getStudentEligibleSubjects(String dbName,
			String className) {

		System.out.println("..................");
		List<StudentForm> studentForms = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String query = "SELECT NAME FROM SUBJECTS WHERE CLASS_ID=?";

		connection = getConnection(dbName);
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, className);
			resultSet = preparedStatement.executeQuery();
			studentForms = new ArrayList<StudentForm>();
			while (resultSet != null && resultSet.next()) {
				StudentForm studentForm = new StudentForm();

				studentForm.setSubjectName(resultSet.getString("NAME"));
				studentForms.add(studentForm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentForms;

	}

	// changes done by SUDARSHAN for class Notes on 20/7/16

	public static List<ClassNotes> getclassNotesDetails(String stdClass,
			String dbName, String subJectName) {

		List<ClassNotes> classNotes = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM " + dbName
				+ ".CLASS_NOTES WHERE CLASS_ID= ? AND SUBJECT=?";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, stdClass);
			preparedStatement.setString(2, subJectName);

			resultSet = preparedStatement.executeQuery();
			classNotes = new ArrayList<ClassNotes>();

			while (resultSet != null && resultSet.next()) {

				ClassNotes classNote = new ClassNotes();

				classNote.setDocName(resultSet.getString("DOC_NAME"));
				classNote.setSubjectName(resultSet.getString("SUBJECT"));

				classNotes.add(classNote);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return classNotes;
	}

	// changes done by Sudarshan for getting download count

	public static int getDownLoadCount(String subjectName, String stdClass,
			String fileName, String dbName) {
		int download = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String query = "SELECT DOWNLOADS FROM " + dbName
				+ ".class_notes WHERE SUBJECT= ? AND DOC_NAME=? AND CLASS_ID=?";

		try {
			connection = ConnectDatabase.getConnection(AppConfig.MASTER_DB);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, subjectName);
			preparedStatement.setString(2, fileName);
			preparedStatement.setString(3, stdClass);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {
				System.out.println(resultSet.getString("DOWNLOADS"));
				download = resultSet.getInt("DOWNLOADS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return download;
	}

	// changes done by Sudarshan for updating downloadCount

	public static int updateDownloadCount(String subjectName, String stdClass,
			String filename, String dbName, int count) {
		int status = 0;

		String query = "";
		Connection connection = ConnectDatabase
				.getConnection(AppConfig.MASTER_DB);
		PreparedStatement ps = null;

		query = "UPDATE "
				+ dbName
				+ ".class_notes SET DOWNLOADS=? WHERE SUBJECT=? AND CLASS_ID=? AND DOC_NAME=?";
		try {

			ps = connection.prepareStatement(query);

			ps.setInt(1, count);
			ps.setString(2, subjectName);
			ps.setString(3, stdClass);
			ps.setString(4, filename);

			status = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectDatabase.closeConnection(connection);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return status;
	}

*/}