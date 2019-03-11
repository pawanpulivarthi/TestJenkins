package com.edsys.framework.util;

import com.edsys.framework.config.Parameter;
import com.edsys.framework.log.DiagnosticLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class EdsysUtil
{
  private static DiagnosticLogger logger = DiagnosticLogger.getLogger(EdsysUtil.class);
//  private static final String mail = "<div style=\"width: 100%;height: 400px;border-radius: 3px;background: #EEECEC\">  <div style=\"width: 100%;height: 50px;background-color: #E05353;color: white;padding-top: 14px;\">  \t<a href=\"http://www.cricrally.com\" style=\"font-size: x-large;font-family: sans-serif;text-decoration: none;cursor: pointer;color: white;\" target=\"_new\">Cricrally.com</a>  </div>  <div style=\"padding-top: 10px;padding-left: 15px;height: 300px;font-family: Arial;color: green;\">  <b>Dear #USER_NAME#,</b><br><br>   Welcome to cricrally, home of your fantasy cricket. You have registered with cricrally successfully. <br><br>  <table style=\"border: 1px solid green;border-collapse: collapse;\">  <tr style=\"height: 40px;border: 1px solid green;\">  <td style=\"font-size: large;border: 1px solid green;\">Login Name</td><td style=\"padding: 5px;\"> #USER_EMAIL#</td></tr> <tr style=\"height: 40px;border: 1px solid green;\"><td style=\"font-size: large;border: 1px solid green;\">Password</td><td style=\"padding: 5px;\"> #USER_PWD#</td></tr>  </table><br>  Now login to <a href=\"http://cricrally.com/new/user/startLogin\" target=\"_new\" style=\"color: green;font-weight: bold;\">cricrally.com</a> and play fantasy cricket to win real cash. </div> <div style=\"width: 100%;height: 50px;background-color: #E05353;color: white;padding-top: 14px;text-align: center;\"> \t<a href=\"http://www.cricrally.com\" style=\"font-size: large;font-family: Arial;text-decoration: none;cursor: pointer;color: white;\" target=\"_new\">www.cricrally.com, India</a>  | (<a href=\"https://www.facebook.com/cricrally\" target=\"_new1\" style=\"height: 20px;width: 20px;color: blue;\">FB Page</a>)</div> </div>";
  
  public static String getConfigDir()
  {
	  System.out.println("in method");
//    return "/home/mekzone1/cricrally";
	  return "E:/edsys";  
  }
  
  public static void search(String keyword, String content)
  {
    String[] searchStrings = content.split(keyword);
    System.out.println("Number of Occurances : " + (searchStrings.length - 1));
  }
  
  public static final String getParameterValue(Parameter[] parameters, String paramName)
  {
    String paramValue = null;
    if (parameters != null) {
      for (int i = 0; i < parameters.length; i++) {
        if (parameters[i].getName().equals(paramName))
        {
          paramValue = parameters[i].getValue();
          break;
        }
      }
    }
    return paramValue;
  }
  
  public static String getDate(String oldDate)
    throws ParseException
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = formatter.parse(oldDate.substring(0, 19));
    SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd, yyyy HH:mm");
    return formatter1.format(date);
  }
  
  public static void main(String[] args)
    throws Exception
  {
    /*Random rand = new Random();
    int value = 10000000 + rand.nextInt(1000000);
    
    System.out.println(value);*/
	  
	  sendMailWonAmountAndRank("pawan.pulivarthi@gmail.com", "Test1", "Test =ing....", "user", "Test", 1, "30");
  }
  
  class HashTest
  {
    private String str;
    
    public HashTest(String str)
    {
      this.str = str;
    }
    
    public int hashCode()
    {
      return this.str.hashCode();
    }
    
    public boolean equals(Object obj)
    {
      return this.str.equals(obj);
    }
  }
  
  public static synchronized void sendMailAfterSignup(String to, String subject, String text, String userEMail, String password, String userName)
    throws UnsupportedEncodingException
  {
    String emailUserName = "play@cricrally.com";
    String emailPWD = "play@526";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    
    props.put("mail.smtp.host", "mail.cricrally.com");
    
    props.put("mail.smtp.port", "2525");
    
    text = text.replaceAll("#USER_NAME#", userName);
    text = text.replaceAll("#USER_EMAIL#", userEMail);
    text = text.replaceAll("#USER_PWD#", password);
    
    Session session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication("play@cricrally.com", "play@526");
      }
    });
    try
    {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("play@cricrally.com", "Cricrally.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      
      message.setSubject(subject);
      message.setText(text);
      
      message.setContent(text, "text/html; charset=utf-8");
      
      Transport.send(message);
      
      System.out.println("Done : " + to);
    }
    catch (MessagingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static synchronized void sendMailAfterDeposit(String to, String subject, String text, String amount, String txnId, String userName)
    throws UnsupportedEncodingException
  {
    String emailUserName = "play@cricrally.com";
    String emailPWD = "play@526";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    
    props.put("mail.smtp.host", "mail.cricrally.com");
    
    props.put("mail.smtp.port", "2525");
    
    text = text.replaceAll("#USERNAME#", userName);
    text = text.replaceAll("#AMOUNT#", amount);
    text = text.replaceAll("#TXNID#", txnId);
    
    Session session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication("play@cricrally.com", "play@526");
      }
    });
    try
    {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("play@cricrally.com", "Cricrally.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      
      message.setSubject(subject);
      message.setText(text);
      
      message.setContent(text, "text/html; charset=utf-8");
      
      Transport.send(message);
      
      System.out.println("Done : " + to);
    }
    catch (MessagingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static synchronized void sendMailWonAmountAndRank(String to, String subject, String text, String userName, String leagueName, int rank, String wonAmount)
    throws UnsupportedEncodingException
  {
    String emailUserName = "play@cricrally.com";
    String emailPWD = "play@526";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    
    props.put("mail.smtp.host", "mail.cricrally.com");
    
    props.put("mail.smtp.port", "2525");
    
    text = text.replaceAll("#RANK#", rank + "");
    String powerVal = "st";
    if (rank % 10 == 1) {
      powerVal = "st";
    } else if (rank % 10 == 2) {
      powerVal = "nd";
    } else if (rank % 10 == 3) {
      powerVal = "rd";
    }
    text = text.replaceAll("#POWER_VAL#", powerVal);
    text = text.replaceAll("#USER_NAME#", userName);
    
    text = text.replaceAll("#LEAGUE_NAME#", leagueName);
    text = text.replaceAll("#WON_AMOUNT#", wonAmount);
    
    Session session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication("play@cricrally.com", "play@526");
      }
    });
    try
    {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("play@cricrally.com", "Cricrally.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      
      message.setSubject(subject);
      message.setText(text);
      
      message.setContent(text, "text/html; charset=utf-8");
      
      Transport.send(message);
      
      System.out.println("Done : " + to);
    }
    catch (MessagingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static synchronized void sendMail2ReferFriend(String to, String subject, String text, String referalCode)
    throws UnsupportedEncodingException
  {
    String emailUserName = "play@cricrally.com";
    String emailPWD = "play@526";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    
    props.put("mail.smtp.host", "mail.cricrally.com");
    
    props.put("mail.smtp.port", "2525");
    
    text = text.replaceAll("#REFER_CODE#", referalCode);
    
    Session session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication("play@cricrally.com", "play@526");
      }
    });
    try
    {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("play@cricrally.com", "Cricrally.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      
      message.setSubject(subject);
      message.setText(text);
      
      message.setContent(text, "text/html; charset=utf-8");
      
      Transport.send(message);
      
      System.out.println("Done : " + to);
    }
    catch (MessagingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static synchronized void sendMail4EmailVerification(String to, String subject, String text, String verificationCode)
    throws UnsupportedEncodingException
  {
    String emailUserName = "verify_email@cricrally.com";
    String emailPWD = "play@526";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    
    props.put("mail.smtp.host", "mail.cricrally.com");
    
    props.put("mail.smtp.port", "2525");
    
    text = text.replaceAll("#VERIFICATION_CODE#", verificationCode);
    
    Session session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication("verify_email@cricrally.com", "play@526");
      }
    });
    try
    {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("verify_email@cricrally.com", "Cricrally.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      
      message.setSubject(subject);
      message.setText(text);
      
      message.setContent(text, "text/html; charset=utf-8");
      
      Transport.send(message);
      
      System.out.println("Done : " + to);
    }
    catch (MessagingException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static List<String> getSubCategories(String passedCategory)
  {
    List<String> subCategories = new ArrayList<String>();
    if (passedCategory != null)
    {
      String[] splitCategories = passedCategory.split("\\|");
      for (String string : splitCategories) {
        subCategories.add(string);
      }
      if (subCategories.size() == 0) {
        subCategories.add(passedCategory);
      }
    }
    return subCategories;
  }
  
  public static String splitFilePath(String path)
  {
    String[] output = path.split("\\\\");
    System.out.println(output[(output.length - 1)]);
    return output[(output.length - 1)];
  }
  
  public static String splitFilePath2(String path)
  {
    String[] output = path.split("/");
    System.out.println(output[(output.length - 1)]);
    return output[(output.length - 1)];
  }
  
  public static String encode(String plainText)
  {
    try
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStream b64os = MimeUtility.encode(baos, "base64");
      b64os.write(plainText.getBytes());
      b64os.close();
      return new String(baos.toByteArray());
    }
    catch (Exception exception)
    {
      logger.error(exception);
    }
    return null;
  }
  
  public static String decode(String plainText)
    throws Exception
  {
    try
    {
      byte[] text = plainText.getBytes();
      ByteArrayInputStream bais = new ByteArrayInputStream(text);
      InputStream b64is = MimeUtility.decode(bais, "base64");
      byte[] tmp = new byte[text.length];
      int n = b64is.read(tmp);
      byte[] res = new byte[n];
      System.arraycopy(tmp, 0, res, 0, n);
      return new String(res);
    }
    catch (Exception exception)
    {
      logger.error(exception);
    }
    return null;
  }
  
  public static String getPropertyFromFile(String key)
  {
    Properties prop = new Properties();
    InputStream input = null;
    String value = null;
    try
    {
      input = new FileInputStream(getConfigDir() + "\\config\\baseConfig\\cricrally.properties");
      
      prop.load(input);
      
      value = prop.getProperty(key);
      if (input != null) {
        try
        {
          input.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      System.out.println(key + " ==> " + value);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      if (input != null) {
        try
        {
          input.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    return value;
  }
}
