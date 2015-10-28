package com.tripcaddie.common.fileValidation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {
	
	@RequestMapping(value="fileSizeValidation.do",method=RequestMethod.POST)
	public void fileValidation(HttpServletRequest request){
		
		try{
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
			System.out.println("Size:"+multipartHttpServletRequest.getFile("image").getSize());
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
