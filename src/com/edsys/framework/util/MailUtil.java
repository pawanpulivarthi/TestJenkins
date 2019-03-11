package com.edsys.framework.util;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.edsys.framework.log.DiagnosticLogger;

public class MailUtil
{
  private static DiagnosticLogger logger = DiagnosticLogger.getLogger(MailUtil.class);
  private static final String FROM_ADDRESS = "Service";
  private static final String PROPERTY_SMTP_HOST = "mail.host";
  private static final String PROPERTY_PROTOCOL_TYPE = "mail.transport.protocol";
  private static final String PROTOCOL_TYPE = "smtp";
  private static final String PROPERTY_SMTP_AUTH = "mail.smtp.auth";
  private static final String SMTP_AUTH = "true";
  private static final String PROPERTY_SMTP_PORT = "mail.smtp.port";
  private static final String PROPERTY_SMTP_FACTORY_PORT = "mail.smtp.socketFactory.port";
  private static final String PROPERTY_SMTP_FACTORY_CLASS = "mail.smtp.socketFactory.class";
  private static final String SMTP_FACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
  private static final String PROPERTY_SMTP_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
  private static final String SMTP_FACTORY_FALLBACK = "false";
  private static final String PROPERTY_SMTP_QUITWAIT = "mail.smtp.quitwait";
  private static final String SMTP_QUITWAIT = "false";
  private static final String PROPERTY_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
  private static final String SMTP_STARTTLS_ENABLE = "true";
  private static final String CONTENT_TYPE_HTML = "text/html";
  private static final String CONTENT_TYPE_PDF = "application/pdf";
  private Session session;
  
  public void init(final String userName, final String passWord, String smtpHost, String smtpPort, int encrytionType)
  {
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.host", smtpHost);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", smtpPort);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.socketFactory.port", smtpPort);
    if (encrytionType == 1) {
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    } else if (encrytionType == 2) {
      props.put("mail.smtp.starttls.enable", "true");
    }
    props.put("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.quitwait", "false");
    
    this.session = Session.getInstance(props, new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication(userName, passWord);
      }
    });
    logger.info("session initialized successfully.");
  }
  
  public final boolean sendMail(String toAddress, String subject, String message, String userName, String passWord, String smtpHost, String smtpPort, int encryptionType)
  {
    boolean status = false;
    
    logger.info("Started sendMail() ");
    if (this.session == null) {
      init(userName, passWord, smtpHost, smtpPort, encryptionType);
    }
    try
    {
      Message mimeMessage = new MimeMessage(this.session);
      
      Address[] toAddresses = InternetAddress.parse(toAddress);
      mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
      
      mimeMessage.setSubject(subject);
      mimeMessage.setFrom(new InternetAddress("Service"));
      
      mimeMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(message, "text/html")));
      
      Message messageToSend = mimeMessage;
      try
      {
        Transport.send(messageToSend);
      }
      catch (MessagingException exception)
      {
        logger.error(exception);
      }
      status = true;
    }
    catch (SendFailedException exception)
    {
      logger.error(exception);
      status = false;
    }
    catch (MessagingException exception)
    {
      logger.error(exception);
      status = false;
    }
    catch (Exception exception)
    {
      logger.error(exception);
      status = false;
    }
    logger.info("Leaving sendMail()");
    
    return status;
  }
  
  public final boolean sendMail(String toAddress, String subject, String message, String userName, String passWord, String smtpHost, String smtpPort, int encryptionType, ByteArrayOutputStream outputStream, String fileName)
  {
    boolean status = false;
    
    logger.info("Started sendMail()");
    if (this.session == null) {
      init(userName, passWord, smtpHost, smtpPort, encryptionType);
    }
    try
    {
      Message mimeMessage = new MimeMessage(this.session);
      
      Address[] toAddresses = InternetAddress.parse(toAddress);
      mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
      mimeMessage.setSubject(subject);
      mimeMessage.setFrom(new InternetAddress("Service"));
      
      Multipart multipart = new MimeMultipart();
      MimeBodyPart bodyPart = new MimeBodyPart();
      bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(message, "text/html")));
      
      multipart.addBodyPart(bodyPart);
      
      bodyPart = new MimeBodyPart();
      bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf")));
      
      bodyPart.setFileName(fileName);
      multipart.addBodyPart(bodyPart);
      
      mimeMessage.setContent(multipart);
      
      Message messageToSend = mimeMessage;
      try
      {
        Transport.send(messageToSend);
      }
      catch (MessagingException exception)
      {
        logger.error(exception);
      }
      status = true;
    }
    catch (SendFailedException exception)
    {
      logger.error(exception);
      status = false;
    }
    catch (MessagingException exception)
    {
      logger.error(exception);
      status = false;
    }
    catch (Exception exception)
    {
      logger.error(exception);
      status = false;
    }
    logger.info("Leaving sendMail()");
    
    return status;
  }
}
