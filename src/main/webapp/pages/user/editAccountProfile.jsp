<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
	<script type="text/javascript" src="/tripcaddie/javascript/js/profile.js"></script>
	<script type="text/javascript">
		function cancel(){
			window.location.href="/tripcaddie/user/profile.do";
		}
	</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<p><strong>My profile</strong></p>
	<p><a href="/tripcaddie/user/profile/editAccount.do">Account Information</a>  <a href="/tripcaddie/user/profile/editProfile.do">Personal Information</a></p>
	<div id="accountInfoDiv">
		<div id="accountInfo">
		<form method="post" enctype="multipart/form-data" name="accountform" id="accountform">
			<p>Username: <input type="text" name="username" id="username" readonly="readonly" value="${userDetails.userName}" />
			<span id="usernameError" style="color: red;"></span></p>
			<p>E-mail Address: <input type="text" name="email" id="emails" value="${userDetails.email}" />
			<span id="emailErrordiv" style="color: red;"></span></p>
			<p>Password : <input type="password" name="password" id="password" value="" />
			<span id="passwordError" style="color: red;"></span></p>
			<p>Confirm Password: <input type="password" name="confirmPassword" id="cpassword" value="" />
			<span id="cpasswordError" style="color: red;"></span></p>
			<p>Profile Picture:</p>
			<div id="photo">
				<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${userDetails.imageBase64} />
			</div>
			<div>
				<p>Upload Photo: <input type="file" name="fileupload" id="file upload"  onchange="validateImageUpload();"/>
				<span id="fileuploadError" style="color: red;"></span></p>
			</div> 
			</form>
		 </div>
	</div>
	<div id="personalInfoDiv">
		<div id="personalInfo"></div>
	</div>
	<p><input type="button" value="save" onclick="submitProfileAccountForm();">
	<input type="button" value="cancel" onclick="cancel()"></p>
</body>
</html>