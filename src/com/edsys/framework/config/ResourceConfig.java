package com.edsys.framework.config;

import com.edsys.framework.globals.Constants;
import com.edsys.framework.util.XMLUtil;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfig
{
  private static Map<String, String> _nameValueMap = null;
  
  public static boolean loadConfig()
  {
    try
    {
      _nameValueMap = XMLUtil.readConfig(Constants.PARCALA_RESOURCE_CONFIG_FILE);
    }
    catch (Exception exception)
    {
      _nameValueMap = new HashMap();
      exception.printStackTrace();
    }
    return true;
  }
  
  public static boolean refresh()
  {
    loadConfig();
    return true;
  }
  
  public static String getValue(String paramName)
  {
    return (String)_nameValueMap.get(paramName);
  }
  
  public static final String getCorporateHome()
  {
    return (String)_nameValueMap.get("CorporateHome");
  }
  
  public static final String getCorporateAdmin()
  {
    return (String)_nameValueMap.get("CorporateAdmin");
  }
  
  public static boolean isSecureResource(String paramName)
  {
    return _nameValueMap.containsKey(paramName);
  }
}
