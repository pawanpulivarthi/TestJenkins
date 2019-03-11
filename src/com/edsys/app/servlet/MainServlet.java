package com.edsys.app.servlet;

import com.edsys.framework.config.AppConfig;
import com.edsys.framework.globals.Constants;

import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.xml.DOMConfigurator;

public class MainServlet
  extends HttpServlet
{
  private static final long serialVersionUID = -6429409338124886146L;
  
  public void init(ServletConfig config)
  {
    System.out.println("edsys365 Application deployment started..");
    
    DOMConfigurator.configure(Constants.PARCALA_LOG4J_CONFIG_FILE);
    
    AppConfig.APP_REAL_PATH = config.getServletContext().getRealPath("/");
    
    AppConfig.loadConfig();
    
    System.out.println("edsys365 Application deployed successfully....." + new Date().toString());
  }
  
  public void destroy()
  {
    super.destroy();
  }
}
