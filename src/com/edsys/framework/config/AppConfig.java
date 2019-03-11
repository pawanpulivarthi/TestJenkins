package com.edsys.framework.config;

import com.edsys.framework.globals.Constants;
import com.edsys.framework.util.XMLUtil;

import java.util.HashMap;
import java.util.Map;

public class AppConfig
{
  private static Map<String, String> _nameValueMap = null;
  public static String APP_REAL_PATH;
  
  public static boolean loadConfig()
  {
    try
    {
      _nameValueMap = XMLUtil.readConfig(Constants.PARCALA_APP_CONFIG_FILE);
    }
    catch (Exception exception)
    {
      _nameValueMap = new HashMap<String, String>();
      exception.printStackTrace();
    }
    return true;
  }
  
  public static boolean refresh()
  {
    loadConfig();
    return true;
  }
  
  public static String getParameterValue(String paramName)
  {
    return (String)_nameValueMap.get(paramName);
  }
}
