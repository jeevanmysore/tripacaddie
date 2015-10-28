package com.tripcaddie.common.securityService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.tripcaddie.frontend.user.service.LoginService;

public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler  {
	
	@Resource(name="loginService")
	 private LoginService loginService;
	 
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request,
	            HttpServletResponse response, Authentication authentication)
	            throws ServletException, IOException {
		 System.out.println("In CustomAuthenticationHandler");
		 try {
			loginService.updateLoginDetails(request.getSession());
			System.out.println("request Url:"+request.getRequestURL());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 /*
	        // TODO Auto-generated method stub
	        String ctoken = (String) request.getSession().getAttribute(WebConstants.CSRF_TOKEN);
	        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
	        if( defaultSavedRequest != null && ctoken != null ) {
	            String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
	            requestUrl = UrlTool.addParamToURL(requestUrl, WebConstants.CSRF_TOKEN, ctoken, true);
	            getRedirectStrategy().sendRedirect(request, response, requestUrl);
	        } else {
	            super.onAuthenticationSuccess(request, response, authentication);
	        }*/
	    }

}
