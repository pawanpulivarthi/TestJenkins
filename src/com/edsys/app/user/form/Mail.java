package com.edsys.app.user.form;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	
	
	public static void main(String a[]){

		try {
			sendMail("sudarshannp3@gmail.com", "Test", "Test 1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
		public static void sendMail(String to,String subject,String text) throws UnsupportedEncodingException{
			final String username = "mekzone.mail@gmail.com";
			final String password = "mekzone123";
	 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.startssl.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			
			//props.put("mail.smtp.host", "gmail.com");
			
			/*props.put("mail.smtp.host", "smtp.googlemail.com");*/
		// props.put("mail.smtp.port", "465");
			props.put("mail.smtp.port", "587");
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
			
			System.out.println(session);
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("contact@wsession.com", "WSession Solutions"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));//to e-mail id here....
				message.setSubject(subject);
				message.setText(text);
				
				message.setContent(text, "text/html; charset=utf-8");
	 
				Transport.send(message);
				
				System.out.println("Done : " + to);
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		
	}
	


}
