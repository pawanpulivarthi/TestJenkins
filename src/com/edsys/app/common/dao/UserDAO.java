package com.edsys.app.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.edsys.app.user.form.UserForm;
import com.edsys.framework.dao.BaseDAO;
import com.edsys.framework.exception.DAOException;
import com.edsys.framework.globals.Constants;
import com.edsys.framework.log.DiagnosticLogger;
import com.edsys.framework.util.ExceptionUtil;

public class UserDAO extends BaseDAO {

	private DiagnosticLogger logger = DiagnosticLogger.getLogger(UserDAO.class);

	public UserForm userLogin(UserForm userForm) throws DAOException {

		this.logger.debug("Starting userRegistration() in DAO");

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {

			String instituteCode = userForm.getInstituteCode();

			String query = "";
			if (instituteCode == null || instituteCode.trim().length() == 0)
				query = "SELECT  USER_TYPE, LOGIN_NAME, PASSWORD, PARENT_PASSWORD FROM USER_LOGIN WHERE LOGIN_NAME = ? AND PASSWORD = ? ";
			else {
				userForm.setInstituteDBName(getIinstituteDBName(userForm.getInstituteCode()));
				
				query = "SELECT  USER_TYPE,LOGIN_NAME,PASSWORD,PARENT_PASSWORD FROM " + userForm.getInstituteDBName()
						+ ".USER_LOGIN WHERE LOGIN_NAME = ? AND PASSWORD = ? ";
			}
			
			logger.debug("Final login query ==> " + query);
			
//			query = query.toUpperCase();

			connection = getConnection();
			
			statement = getPreparedStatement(connection, query, Constants.SELECT_QUERY_TYPE);

			statement.setString(1, userForm.getUserName());
			statement.setString(2, userForm.getPassword());
			
			resultSet = statement.executeQuery();
			if(resultSet != null && resultSet.next()) {
				userForm.setUserType(resultSet.getString("USER_TYPE"));
			}
				
		} catch (Exception exception) {
			this.logger.error(exception);
			throw new DAOException(ExceptionUtil.getMessage(exception));
		} finally {
			closePreparedStatement(statement);
			closeConnection(connection);
		}
		this.logger.debug("Ending login");

		return userForm;
	}
	
	public String getIinstituteDBName(String instituteCode) throws DAOException {

		this.logger.debug("Starting userRegistration() in DAO");

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		String dbName = "";
		
		try {
			int instituteCodeNumVal = 0;
			if(instituteCode.indexOf("_") > 0) {
				instituteCodeNumVal = Integer.parseInt(instituteCode.split("_")[instituteCode.split("_").length-1]);
			}
			
			String query = "SELECT * FROM edsys365_master.SCHOOLS WHERE SCHOOLID = ? ";

			query = query.toUpperCase();

			connection = getConnection();
			statement = getPreparedStatement(connection, query, Constants.SELECT_QUERY_TYPE);
			statement.setInt(1, instituteCodeNumVal);
			
			resultSet = statement.executeQuery();
			
			if(resultSet != null)
				dbName = resultSet.getString("DB_NAME");
			
		} catch (Exception exception) {
			this.logger.error(exception);
			throw new DAOException(ExceptionUtil.getMessage(exception));
		} finally {
			closePreparedStatement(statement);
			closeConnection(connection);
		}
		this.logger.debug("Ending login");

		return dbName;
	}

}
