<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset password</title>
<script type="text/javascript">
	function submitForm(){
	if(validateForm()==true)
		$('#resetPasswordForm').submit();
	}
	
	function validateForm(){
		var flag=true;
		var password,cpassword;
		username=$("#username").val();
		password=$("#password").val();
		
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
		return flag;
	}
</script>
</head>
<body>
	<div style="background-color: gray;height: 50px" >
		<p style="text-align: right;"><a href="./">Home</a></p>
	</div>
	<form id="resetPasswordForm" action="updatePassword.do" method="post">
		<p>New Password: <input type="password" name="passwordtxt" id="passwordtxt" /></p>
		<p>Confirm Password: <input type="password" name="cpasswordtxt" id="cpasswordtxt" /></p>
		<input type="submit" value="Submit" onclick="">
	</form>
</body>
</html>