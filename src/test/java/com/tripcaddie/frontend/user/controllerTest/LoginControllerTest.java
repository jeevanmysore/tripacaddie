package com.tripcaddie.frontend.user.controllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tripcaddie.backend.user.vo.UserTestBean;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;
import junit.framework.TestCase;

public class LoginControllerTest extends TestCase {

	public LoginService loginService;
		
	@Before
	protected void setUp(){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		loginService=(LoginService) ctx.getBean("loginService");
	}
	
	@Test
	public void testForgetPassword() {
		
		String email=null;
		UserTestBean userTestBean=new UserTestBean();
		userTestBean.setEmail("acp2010@gmail.com");
		TripcaddieUserDto userDto;
		try {
			userDto = loginService.getUserByEmail(userTestBean.getEmail());
			email=userDto.getEmail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(userTestBean.getEmail(),email);
	}
}
