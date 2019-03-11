package com.edsys.framework.util;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberUtil
{
  private static final NumberFormat formatter = new DecimalFormat("#,###,###");
  private static final NumberFormat formatter1 = new DecimalFormat("0.00");
  
  public static final String formatNumber(double num)
  {
    return formatter.format(num);
  }
  
  public static final String formatNumber(int num)
  {
    return formatter.format(num);
  }
  
  public static final int parseNumber(String num)
  {
    int newNum = 0;
    try
    {
      newNum = formatter.parse(num).intValue();
    }
    catch (ParseException exception)
    {
      throw new RuntimeException(exception);
    }
    return newNum;
  }
  
  public static final double round(double num)
  {
    return Double.parseDouble(formatter1.format(num));
  }
  
  public static final int roundInt(int num, int count)
  {
    float value = new Float(Math.round(num / count)).floatValue();
    return (int)value;
  }
  
  public static boolean isNumeric(String str)
  {
    try
    {
      Double.parseDouble(str);
    }
    catch (NumberFormatException nfe)
    {
      return false;
    }
    return true;
  }
  
  public static void main(String[] a)
  {
    System.out.println(round(2.33333333333D));
  }
}
