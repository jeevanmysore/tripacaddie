package com.tripcaddie.frontend.user.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tripcaddie.backend.user.vo.UserTestBean;
import com.tripcaddie.frontend.user.service.RegistrationService;

import junit.framework.TestCase;

public class RegistrationControllerTest extends TestCase {
	
	public RegistrationService registrationService;
	
	@Before
	protected void setUp(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		registrationService=(RegistrationService) ctx.getBean("registrationService");
	}
	
	@Test
	public void testIsExistUser() throws Exception{
		
		UserTestBean userTestBean=new UserTestBean();
		userTestBean.setUserName("praga");
		boolean exist=registrationService.isExistUser(userTestBean.getUserName());
		assertEquals(true, exist);	
	}
	
	@Test
	public void testIsExistEmail() throws Exception{
		
		UserTestBean userTestBean=new UserTestBean();
		userTestBean.setEmail("acp2010@gmail.com");
		boolean exist=registrationService.isExistEmail(userTestBean.getEmail());
		assertEquals(true, exist);
	}

}
