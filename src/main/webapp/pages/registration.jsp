<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Registration</title>

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	<script type="text/javascript" src="javascript/js/jquery.form.js"></script>
	
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
	
	<script type="text/javascript">
	 var filter= /[^a-zA-Z 0-9]+/g;
	 var filter1=/[^a-zA-Z]+/g;
		function submitForm(){
			
			if(validateForm()==true)
				$('#registerationForm').submit();
		}
		
		function validateForm(){
			var flag=true;
			var username,password,cpassword,fname,lname,email;
			
			/*var challengeField = Recaptcha.get_challenge();
		    var responseField = Recaptcha.get_response();*/
			username=$("#username").val();
			password=$("#password").val();
			cpassword=$("#cpassword").val();
			fname=$("#fname").val();
			lname=$("#lname").val();
			email=$("#email").val();
			
			$.ajax({
				url: "userExistance.do",
				type: "POST",
				async: false,
				data: {username:$('#username').val()},
				success: function(data){
					if(data=="success"){
						$('#usernameValidate').text("username is already exist");
					}
				}
			});
				
			if($('#usernameValidate').text() !=""){
				
				flag=false;
			}
			if($('#CpasswordValidate').text() !=""){
				flag=false;
			}
			if($('#emailValidate').text() !=""){
				//alert('in correct place');
				flag=false;
			}
			if($('#fnameValidate').text() !=""){
				flag=false;
			}
			if($('#lnameValidate').text() !=""){
				flag=false;
			}
			if($('#passwordValidate').text() !=""){
				flag=false;
			}
			
			if(filter.test(username)){
				flag=false;
				$('#usernameValidate').text("User name should not have special characters");
	    	}else
			if(username == "" || username == null || username.indexOf(" ") != -1 ){
				flag=false;
				$('#usernameValidate').text("username is required.Spaces are not allowed");
			}
			
			if(password == "" || password == null || password.indexOf(" ") != -1 ){
				$('#passwordValidate').text("password is required.Spaces are not allowed")
				flag=false;
			}
			
			if(cpassword == "" || cpassword == null || cpassword.indexOf(" ") != -1 ){
				flag=false;
	    		$('#CpasswordValidate').text("password is required.Spaces are not allowed")
	    	}
	    	else if(cpassword != password){
	    		flag=false;
	    		$('#CpasswordValidate').text("Password is not matched");
			}
			
			if(filter1.test(fname)){
				flag=false;
				$('#fnameValidate').text("First name should not have special characters");
    		}else
	    	if(fname == "" || fname == null || fname.indexOf(" ") != -1 ){
	    		flag=false;
	    		$('#fnameValidate').text("FirstName is required.Spaces are not allowed")
	    	}
			
			if($.trim(email).length == 0) {
				//alert("in 1");
				flag=false;
				$('#emailValidate').text("Email address is not valid");
	        }
			else if (!validateEmail(email)) {
				//alert("in 2");				
				flag=false;
	        	$('#emailValidate').text("Email address is not valid");
			}
			
			if(filter1.test(lname)){
				flag=false;
				$('#lnameValidate').text("Last name should not have special characters");
    		}else
    		if(lname == "" || lname == null || lname.indexOf(" ") != -1 ){
    			flag=false;
	    		$('#lnameValidate').text("LastName is required.Spaces are not allowed")
	    	}
			
			//Ajax request for captcha validation
			var queryString=$('#registerationForm').formSerialize();
			$.ajax({
				url: "reCaptchaValidation.do",
				type: "POST",
				async: false,
				data: queryString,
				success: function(data){
					if(data=="fail"){
						flag=false;
						Recaptcha.reload();
					}
				}
			});
			return flag;
		}
		
	</script>
	
	<script type="text/javascript">
	function validateEmail(sEmail) {
	    var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	       
	    if (emailFilter.test(sEmail)) {
	        return true;
	    }
	    else {
	        return false;
	    }
	 }
	 function cancelRegistration(){
		window.location.href="./";
	 }
	/*  $(function() {
		$('#textDate').datepicker({
			changeYear:true,
			yearRange:"1900:2050",
			autoSize:true
		});
	 }); */
 	 $(document).ready(function(){
			
		$("#username").focusout(function(){
			var username=$('#username').val();
			if(filter.test(username)){
				$('#usernameValidate').text("User name should not have special characters");
    		}else
	    	if(username == "" || username == null || username.indexOf(" ") != -1 ){
	    		$('#usernameValidate').text("username is required.Spaces are not allowed");
	    	}
	    	else {
	    		$('#usernameValidate').text("");
	    		$.post("./userExistance.do",{
	    			username : $('#username').val()
	    		},
	    		function(data){
	    			if(data=="success"){
	    				$('#usernameValidate').text("username is already exist");
	    			}
	    			
	    		});
	    	}
	    });
		
		$("#password").focusout(function(){
			var password=$('#password').val();
	    	if(password == "" || password == null || password.indexOf(" ") != -1 ){
	    		$('#passwordValidate').text("password is required.Spaces are not allowed")
	    	}
	    	else
	    		$('#passwordValidate').text("")
		});
		$("#cpassword").focusout(function(){
			
			var cpassword=$('#cpassword').val();
			var password=$('#password').val();
			
	    	if(cpassword == "" || cpassword == null || cpassword.indexOf(" ") != -1 ){
	    		$('#CpasswordValidate').text("password is required.Spaces are not allowed")
	    	}
	    	else if(password === cpassword){
	    		$('#CpasswordValidate').text("")
	    		
	    	}
	    	else{
	    		$('#CpasswordValidate').text("Password is not matched");
			}
		});
		$("#fname").focusout(function(){
			var fname=$('#fname').val();
			if(filter.test(fname)){
				$('#fnameValidate').text("First name should not have special characters");
    		}else
	    	if(fname == "" || fname == null || fname.indexOf(" ") != -1 ){
	    		$('#fnameValidate').text("FirstName is required.Spaces are not allowed")
	    	}
	    	else
	    		$('#fnameValidate').text("")
		});
		 
		$("#email").focusout(function(){
			var email=$('#email').val();
			if ($.trim(email).length == 0) {
				$('#emailValidate').text("Email address is not valid");
	        }
			else if (!validateEmail(email)) {
	        	$('#emailValidate').text("Email address is not valid");
	        }else{
	        	$('#emailValidate').text("");
	        	$.post("./emailExistance.do",{
	    			email : $('#email').val()
	    		},
	    		function(data){
	    			if(data=="success"){
	    				$('#emailValidate').text("email is already exist");
	    			}
				});
	        }
	    });
		$("#lname").focusout(function(){
			var lname=$('#lname').val();
			if(filter.test(lname)){
				$('#lnameValidate').text("Last name should not have special characters");
    		}else
    		if(lname == "" || lname == null || lname.indexOf(" ") != -1 ){
	    		$('#lnameValidate').text("LastName is required.Spaces are not allowed")
	    	}
	    	else
	    		$('#lnameValidate').text("");
		});
	});
	</script>
</head>
<body>
	<%-- <%@ include file="/pages/header.jsp" %> --%>
	<div style="background-color: gray;height: 50px" >
		<p style="text-align: right;"><a href="./">Home</a></p>
	</div>
	<form id="registerationForm" method="post" action="registerUser.do">	
		<p>Username:<input id="username" type="text" name="username"/><label id="usernameValidate"></label></p>
		<p>Password:<input id="password" type="password" name="password"/><label id="passwordValidate"></label></p>
		<p>Confirmation Password:<input id="cpassword" type="password" name="cpassword"/><label id="CpasswordValidate"></label></p>
		<p>Email:<input type="text" id="email" name="email"/><label id="emailValidate"></label></p>
		<p>Fisrt Name:<input type="text" id="fname" name="fname"/><label id="fnameValidate"></label></p>
		<p>Last name:<input type="text" id="lname" name="lname"/><label id="lnameValidate"></label></p>
	<!-- 	<p>Date of birth:<input type="text" name="dob" id="textDate" /></p> -->
		<p style="text-align: center;"><%
			ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Lcm7tUSAAAAAIWLbH2FKzhzsMq59yJOzwiK90l0", "6Lcm7tUSAAAAAJi0BxzsmQEKuCqJqS9YdcBZu1V4", false);
			out.print(captcha.createRecaptchaHtml(null, null));
    	%></p><label id="captchaError"></label>
		<p>Clicking on Join means you have read and agreed to the<a href="#">Privacy Policy</a> and the <a href="#">Terms of Use</a>.</p>
		<p><input type="button" value="Join" onclick="submitForm();" />  <input type="button" value="Cancel" onclick="cancelRegistration();"/></p>
	</form>
</body>
</html>