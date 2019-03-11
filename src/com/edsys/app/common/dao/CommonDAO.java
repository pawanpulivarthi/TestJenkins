package com.edsys.app.common.dao;

import com.edsys.app.user.form.UserForm;
import com.edsys.framework.dao.BaseDAO;
import com.edsys.framework.exception.DAOException;
import com.edsys.framework.log.DiagnosticLogger;
import com.edsys.framework.util.EdsysUtil;
import com.edsys.framework.util.ExceptionUtil;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonDAO
  extends BaseDAO
{
  private DiagnosticLogger logger = DiagnosticLogger.getLogger(CommonDAO.class);
  
  
}
