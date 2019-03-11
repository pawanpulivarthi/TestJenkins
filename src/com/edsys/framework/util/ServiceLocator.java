package com.edsys.framework.util;

import com.edsys.framework.config.AppConfig;
import com.edsys.framework.exception.DAOException;
import com.edsys.framework.log.DiagnosticLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator
{
  private static final DiagnosticLogger log = DiagnosticLogger.getLogger(ServiceLocator.class);
  private static ServiceLocator onlyServiceLocator;
  private final String datasource = "java:/comp/env/jdbc/wsession";
  
  public static final ServiceLocator getInstance()
  {
    if (onlyServiceLocator == null)
    {
      String val = AppConfig.getParameterValue("UseDataSource");
      if ((val != null) && (val.trim().length() > 0)) {
        useDataSource = Boolean.parseBoolean(val);
      }
      onlyServiceLocator = new ServiceLocator();
    }
    return onlyServiceLocator;
  }
  
  private static boolean useDataSource = false;
  
  public final Connection getConnection()
    throws DAOException
  {
    log.trace("Service Locator get Connection ..");
    
    Connection connection = null;
    if (useDataSource) {
      connection = getConnection("java:/comp/env/jdbc/edsys");
    } else {
      connection = getConnection(AppConfig.getParameterValue("JdbcDriver"), AppConfig.getParameterValue("JdbcUrl"), AppConfig.getParameterValue("JdbcUserName"), AppConfig.getParameterValue("JdbcPassword"));
    }
    log.trace("getConnection ended");
    return connection;
  }
  
  public final Connection getConnection(String datasource)
    throws DAOException
  {
    Connection connection = null;
    try
    {
      InitialContext context = new InitialContext();
      DataSource dataSource = (DataSource)context.lookup(datasource);
      if (dataSource != null)
      {
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
      }
    }
    catch (NamingException exception)
    {
      exception.printStackTrace();
      log.fatal(exception);
      throw new DAOException(exception);
    }
    catch (SQLException exception)
    {
      exception.printStackTrace();
      log.fatal(exception);
      throw new DAOException(exception);
    }
    return connection;
  }
  
  public final Connection getConnection(String driver, String jdbcURL, String user, String password)
    throws DAOException
  {
    Connection connection = null;
    try
    {
      Class.forName(driver);
      connection = DriverManager.getConnection(jdbcURL, user, password);
      
      connection.setAutoCommit(false);
    }
    catch (ClassNotFoundException exception)
    {
      log.fatal(exception);
      throw new DAOException(exception);
    }
    catch (SQLException exception)
    {
      log.fatal(exception);
      throw new DAOException(exception);
    }
    return connection;
  }
}
