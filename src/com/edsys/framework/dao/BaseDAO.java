package com.edsys.framework.dao;

import com.edsys.framework.exception.DAOException;
import com.edsys.framework.log.DiagnosticLogger;
import com.edsys.framework.util.ExceptionUtil;
import com.edsys.framework.util.ServiceLocator;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO
{
  private static DiagnosticLogger logger = DiagnosticLogger.getLogger(BaseDAO.class);
  
  protected boolean closeCallableStatement(CallableStatement callableStatement)
    throws DAOException
  {
    boolean status = false;
    try
    {
      if (callableStatement != null) {
        callableStatement.close();
      }
      status = true;
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    return status;
  }
  
  protected boolean closeConnection(Connection connection)
    throws DAOException
  {
    boolean status = false;
    try
    {
      if ((connection != null) && (!connection.isClosed())) {
        connection.close();
      }
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    return status;
  }
  
  protected boolean closeConnection(Connection connection, boolean transaction)
    throws DAOException
  {
    logger.trace("closeConnection started");
    
    boolean status = false;
    try
    {
      if ((connection != null) && (!connection.isClosed()))
      {
        if (transaction) {
          connection.commit();
        }
        connection.close();
      }
      status = true;
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
      throw new DAOException(ExceptionUtil.getMessage(exception));
    }
    logger.trace("closeConnection ended");
    
    return status;
  }
  
  protected boolean closePreparedStatement(PreparedStatement preparedStatement)
    throws DAOException
  {
    boolean status = false;
    try
    {
      if (preparedStatement != null) {
        preparedStatement.close();
      }
      status = true;
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    return status;
  }
  
  protected boolean closeResultSet(ResultSet resultSet)
    throws DAOException
  {
    boolean status = false;
    try
    {
      if (resultSet != null) {
        resultSet.close();
      }
      status = true;
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    return status;
  }
  
  protected void displayColumns(ResultSet resultSet)
    throws SQLException
  {
    ResultSetMetaData metaData = resultSet.getMetaData();
    if (metaData != null) {
      for (int i = 0; i < metaData.getColumnCount(); i++) {
        logger.trace(metaData.getColumnName(i + 1));
      }
    }
  }
  
  protected CallableStatement getCallbleStatement(Connection connection, String procedureName)
    throws SQLException
  {
    logger.trace("getCallbleStatement started");
    
    CallableStatement callableStatement = null;
    if (connection != null) {
      callableStatement = connection.prepareCall(procedureName);
    }
    logger.trace("getCallbleStatement ended");
    return callableStatement;
  }
  
  protected Connection getConnection()
    throws DAOException
  {
    return ServiceLocator.getInstance().getConnection();
  }
  
  protected Connection getConnection(String driver, String jdbcURL, String user, String password)
    throws DAOException
  {
    return ServiceLocator.getInstance().getConnection(driver, jdbcURL, user, password);
  }
  
  protected ResultSet getCursor(CallableStatement statement, int pos)
    throws DAOException
  {
    logger.trace("getCursor started");
    
    ResultSet resultSet = null;
    try
    {
      resultSet = (ResultSet)statement.getObject(pos);
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    logger.trace("getCursor ended");
    
    return resultSet;
  }
  
  protected ResultSet getCursor(CallableStatement statement, String paramName)
    throws DAOException
  {
    logger.trace("getCursor 1 started");
    
    ResultSet resultSet = null;
    try
    {
      logger.info("Reading cursor, name = " + paramName);
      resultSet = (ResultSet)statement.getObject(paramName);
    }
    catch (SQLException exception)
    {
      logger.error("Error while reading cursor : " + paramName);
    }
    logger.trace("getCursor 1 ended");
    
    return resultSet;
  }
  
  public int getIdBeforeInsert(Connection connection, String tableName)
    throws SQLException
  {
    int id = 0;
    
    Statement statement = null;
    ResultSet rs = null;
    
    statement = connection.createStatement();
    rs = statement.executeQuery(" SELECT NEXTVAL('SEQ_" + tableName + "') AS SEQ_VALUE ");
    
    rs.next();
    id = rs.getInt(1);
    rs.close();
    statement.close();
    
    return id;
  }
  
  protected PreparedStatement getPreparedStatement(Connection connection, String query, int queryType)
    throws SQLException
  {
    logger.trace("getPreparedStatement started");
    
    PreparedStatement preparedStatement = null;
    if (connection != null) {
      if (queryType == 1) {
        preparedStatement = connection.prepareStatement(query, 1004, 1007);
      } else {
        preparedStatement = connection.prepareStatement(query, 1004, 1008);
      }
    }
    if (preparedStatement != null) {
      logger.debug(preparedStatement.toString());
    }
    logger.trace("getPreparedStatement ended");
    
    return preparedStatement;
  }
  
  protected int getRecordCount(ResultSet resultSet)
    throws SQLException
  {
    logger.trace("getRecordCount started");
    
    int recordCount = 0;
    try
    {
      if ((resultSet != null) && (resultSet.last()))
      {
        recordCount = resultSet.getRow();
        resultSet.beforeFirst();
      }
    }
    catch (SQLException exception)
    {
      logger.fatal(exception);
    }
    logger.trace("getRecordCount ended");
    
    return recordCount;
  }
}
