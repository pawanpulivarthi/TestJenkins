package com.edsys.framework.util;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
  public static final String DATE_FORMAT_DISPLAY_FORMAT = "MM/dd/yyyy";
  public static final String DATE_FORMAT_PDF_FORMAT = "MMM dd',' yyyy";
  public static final String DATE_FORMAT_MMMYYYY = "MMMM yyyy";
  public static final String DATE_FORMAT_MODIFIED_DATE = "MM/dd/yyyy HH:mm";
  public static final String DATE_FORMAT_JSCAL_FORMAT = "yyyy-MM-dd HH:mm";
  public static final String DATE_FORMAT_JS_DATE = "yyyy','MM','dd";
  public static final String DATE_FORMAT_EVENT_TIME = "dd MMM yyyy HH:mm";
  public static final String DATE_FORMAT_EVENT_TIME_NEW = "MMM dd',' yyyy HH:mm";
  public static final String DATE_FORMAT_CHART_FORMAT = "dd MMM";
  public static final int YEAR = 1;
  public static final int MONTH = 2;
  public static final int DAY = 5;
  
  public static final Date getCurrentDateTime()
  {
    return new Date();
  }
  
  public static final Date getDate(String dateStr, String dateFormat)
  {
    Date newDate = null;
    if ((dateStr != null) && (dateFormat != null)) {
      try
      {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        
        simpleDateFormat.setLenient(true);
        newDate = simpleDateFormat.parse(dateStr);
      }
      catch (ParseException exception)
      {
        throw new RuntimeException(exception);
      }
    }
    return newDate;
  }
  
  public static final Timestamp getSqlDate()
  {
    return getSqlDate(new Date());
  }
  
  public static final Timestamp getSqlDate(Date date)
  {
    return date != null ? new Timestamp(date.getTime()) : null;
  }
  
  public static final Timestamp getSqlDate(String dateStr, String dateFormat)
  {
    return getSqlDate(getDate(dateStr, dateFormat));
  }
  
  public static final Date add(Date date, int fieldToModify, int size)
  {
    Date newDate = null;
    try
    {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(date);
      c1.add(fieldToModify, size);
      newDate = c1.getTime();
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
    return newDate;
  }
  
  public static final String getFormatedTime(int testDuration)
  {
    String ftime = "";
    try
    {
      int temp = 0;
      int hr = testDuration / 3600;
      temp = testDuration % 3600;
      int min = temp / 60;
      int sec = temp % 60;
      if (hr < 10) {
        ftime = ftime + 0 + hr;
      } else {
        ftime = ftime + hr;
      }
      if (min < 10) {
        ftime = ftime + ":" + 0 + min;
      } else {
        ftime = ftime + ":" + min;
      }
      if (sec < 10) {
        ftime = ftime + ":" + 0 + sec;
      } else {
        ftime = ftime + ":" + sec;
      }
    }
    catch (Exception exception)
    {
      throw new RuntimeException(exception);
    }
    return ftime;
  }
  
  public static void main(String[] a)
  {
    System.out.println(System.currentTimeMillis());
  }
}
