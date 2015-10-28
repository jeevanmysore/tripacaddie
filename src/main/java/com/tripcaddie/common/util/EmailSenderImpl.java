package com.tripcaddie.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
@Transactional
@Service("emailSender")
public class EmailSenderImpl implements EmailSender{
	
	private static Logger logger = Logger
			.getLogger(EmailSenderImpl.class);

	@Resource(name="mailSender")
	private JavaMailSender mailSender;
	@Resource(name="velocityEngine")
	private VelocityEngine velocityEngine;
	
	public void sendEmail(TripcaddieUserDto userDto,String subject,String template,String contextPath){
		
		try {
			
			Assert.notNull(userDto);
			Assert.hasText(subject);
			Assert.hasText(template);
			
			System.out.println("Template:"+template);
			System.out.println("ContextPath:"+contextPath);
			velocityEngine.init();			
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			helper.setTo(userDto.getEmail());
			helper.setSubject(subject);
				
			String encryptedValue=this.encodeString(userDto.getUserName(), userDto.getEmail());			
			userDto.setEncryptValue(encryptedValue);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("user", userDto);
			model.put("path", contextPath);
			
			String url="com/templates/email/"+template+".vm";
			
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,url, model);
			helper.setText(text, true);
			mailSender.send(helper.getMimeMessage());
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public void sendEmail(String emailAddress, String username, String subject,
			String template,String contextPath){
		
		try {
			Assert.hasText(username);
			Assert.hasText(emailAddress);
			Assert.hasText(subject);
			Assert.hasText(template);
			
			System.out.println("Template:"+template);
			System.out.println("ContextPath:"+contextPath);
			velocityEngine.init();		
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			helper.setTo(emailAddress);
			helper.setSubject(subject);
				
			//encode username using md5
			String encryptedValue=this.encodeString(username, emailAddress);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("id", encryptedValue);
			model.put("username",username);
			model.put("path", contextPath);
			
			String url="com/templates/email/"+template+".vm";
			
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,url, model);
			helper.setText(text, true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}
	
	//encode username and password using Base64
	public String encodeString(String username,String emailAddress) throws Exception{
		Assert.hasText(username);
		Assert.hasText(emailAddress);
		
		String toEnc=username+","+emailAddress;
		String encryptedValue=new String(Base64.encodeBase64(toEnc.getBytes()));
		System.out.println("encryptedValue:"+encryptedValue);
		
		return encryptedValue;
	}

	@Override
	public void sendEmail(String username, String recipentName,
			String recipentEmail, String subject, String template,String contextPath){
		
		try {
			Assert.hasText(recipentName);
			Assert.hasText(recipentEmail);
			Assert.hasText(subject);
			Assert.hasText(template);
			Assert.hasText(username);
			
			System.out.println("Template:"+template);
			System.out.println("ContextPath:"+contextPath);
			velocityEngine.init();
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
			helper.setTo(recipentEmail);
			helper.setSubject(subject);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("sender", username);
			model.put("receiver", recipentName);
			model.put("path", contextPath);
				
			String url="com/templates/email/"+template+".vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,url, model);
			helper.setText(text, true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
	
	}

	@Override
	public void sendEmail(String emailAddress,TripcaddieUserDto userDto,
			String subject, String template, String contextPath,String personalMessage){
		
		try {
			Assert.hasText(contextPath);
			Assert.hasText(template);
			Assert.hasText(template);
			Assert.hasText(emailAddress);
			
			System.out.println("Template:"+template);
			System.out.println("ContextPath:"+contextPath);
			velocityEngine.init();
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
			
			helper.setTo(emailAddress);
			helper.setSubject(subject);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("message", personalMessage);
			model.put("user", userDto);
			model.put("email", emailAddress);
			
			
			String url="com/templates/email/"+template+".vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,url, model);
			helper.setText(text, true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
		
		
		
	}

	@Override
	public void sendEmail(String emailAddress, TripcaddieUserDto userDto,
			String subject, String template, String contextPath,
			String personalMessage, TripDto trip){
		
		try {
			Assert.hasText(contextPath);
			Assert.hasText(template);
			Assert.hasText(template);
			Assert.hasText(emailAddress);
			
			System.out.println("Template:"+template);
			System.out.println("ContextPath:"+contextPath);
			velocityEngine.init();
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
			
			helper.setTo(emailAddress);
			helper.setSubject(subject);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("message", personalMessage);
			model.put("user", userDto);
			model.put("email", emailAddress);
			model.put("trip",trip);
			model.put("path", contextPath);
			
			
			String url="com/templates/email/"+template+".vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,url, model);
			helper.setText(text, true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}

	//For Birthday Email
	@Override
	public void sendEmail(TripCaddieUser tripCaddieUser, String subject,
			String template){
		
		try {
			Assert.hasText(template);
			Assert.hasText(subject);
			Assert.notNull(tripCaddieUser);
			
			velocityEngine.init();
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			helper.setTo(tripCaddieUser.getEmail());
			helper.setSubject(subject);
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("user", tripCaddieUser);
			
			String url="com/templates/email/"+template+".vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, url, model);
			helper.setText(text,true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public void sendEmailForAbuse(String from, String name, String reason,
			String message) {
		
		try {
			velocityEngine.init();
			MimeMessage message2=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message2);
			
			helper.setTo("prag@knstek.com");
			helper.setSubject("Report Abuse");
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("username", from);
			model.put("name", name);
			model.put("reason", reason);
			model.put("message", message);
			
			String url="com/templates/email/reportAbuse.vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, url, model);
			helper.setText(text,true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}

	@Override
	public void sendEmailFeedback(String email, String message){
		
		try {
			velocityEngine.init();
			MimeMessage message2=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message2);
			
			helper.setTo("prag@knstek.com");
			helper.setSubject("Feedback");
			
			Map<String, Object> model=new HashMap<String, Object>();
			model.put("email", email);
			model.put("message", message);
			
			String url="com/templates/email/feedback.vm";
			String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, url, model);
			helper.setText(text,true);
			mailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}

}
