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
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
	
	<script type="text/javascript">
	function validateEmail(sEmail) {
	    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	    if (filter.test(sEmail)) {
	        return true;
	    }
	    else {
	        return false;
	    }
	}
	
	function cancelRegistration(){
		window.location.href="./";
	}
	$(function() {
		$('#textDate').datepicker({
			changeYear:true,
			yearRange:"1900:2050",
			autoSize:true
		});
	});
	$(document).ready(function(){
		 $("#username").bind('keydown',function(e){
			alert('hi');
			if(e.shiftKey){
				alert(e.which);
			}
		});
		
		$("#username").focusout(function(){
			var username=$('#username').val();
	    	if(username == "" || username == null || username.indexOf(" ") != -1 ){
	    		$('#usernameValidate').text("username is required.Spaces are not allowed");
	    	}
	    	else{
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
	    	if(fname == "" || fname == null || fname.indexOf(" ") != -1 ){
	    		$('#fnameValidate').text("FirstName is required.Spaces are not allowed")
	    	}
	    	else
	    		$('#fnameValidate').text("")
		});
		$("#lname").focusout(function(){
			var lname=$('#lname').val();
	    	if(lname == "" || lname == null || lname.indexOf(" ") != -1 ){
	    		$('#lnameValidate').text("LastName is required.Spaces are not allowed")
	    	}
	    	else
	    		$('#lnameValidate').text("")
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
	});
	</script>
</head>
<body>
	<form method="post" action="registerUser.do">	
		<p>Username:<input id="username" type="text" name="username"/><label id="usernameValidate"></label></p>
		<p>Password:<input id="password" type="password" name="password"/><label id="passwordValidate"></label></p>
		<p>Confirmation Password:<input id="cpassword" type="password" name="cpassword"/><label id="CpasswordValidate"></label></p>
		<p>Email:<input type="text" id="email" name="email"/><label id="emailValidate"></label></p>
		<p>Fisrt Name:<input type="text" id="fname" name="fname"/><label id="fnameValidate"></label></p>
		<p>Last name:<input type="text" id="lname" name="lname"/><label id="lnameValidate"></label></p>
		<p>Date of birth:<input type="text" name="dob" id="textDate" /></p>
		<p><%
		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6LfnhNQSAAAAADO4gTE3JIGZHNamPe0TMS1g3Ncu", "6LfnhNQSAAAAAA7te8VxVWW2dVzXSbLZPtKEjHwx", false);
		out.print(captcha.createRecaptchaHtml(null, null));
    	%></p><%-- <form:errors path="captcha" /> --%>
		<p>Clicking on Join means you have read and agreed to the<a href="#">Privacy Policy</a> and the <a href="#">Terms of Use</a>.</p>
		<p><input type="submit" value="Join" />  <input type="button" value="Cancel" onclick="cancelRegistration();"/></p>
	</form>
</body>
</html>