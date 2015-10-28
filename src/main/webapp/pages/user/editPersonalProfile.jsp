<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
	<script type="text/javascript" src="/tripcaddie/javascript/js/profile.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#birthdaydate').datepicker({
				changeYear:true,
				yearRange:"1900:2050",
				autoSize:true
			});
		});
		
		function cancel(){
			window.location.href="/tripcaddie/user/profile.do";
		}
	
		function checkboxes(){
			
			if($("#notify_tripleader").val()=="yes"){
				$("#notify_tripleader").attr('checked',true);
			}else{
				$("#notify_tripleader").attr('checked',false);
			}
			
			if($("#notify_tripanyone").val() == "yes"){
				$("#notify_tripanyone").attr('checked',true);
			}else{
				$("#notify_tripanyone").attr('checked',false);
			}
			if($("#notify_acceptedinv").val()== "yes"){
				$("#notify_acceptedinv").attr('checked',true);
			}else{
				$("#notify_acceptedinv").attr('checked',false);
			}
			if($("#notify_rejectedinv").val()== "yes"){
				$("#notify_rejectedinv").attr('checked',true);
			}else{
				$("#notify_rejectedinv").attr('checked',false);
			}
			if($("#notify_awardcreated").val()== "yes"){
				$("#notify_awardcreated").attr('checked',true);
			}else{
				$("#notify_awardcreated").attr('checked',false);
			}
			if($("#notify_sendemail").val() == "yes"){
				
				$("#notify_sendemail").attr('checked',true);
			}else{
				$("#notify_sendemail").attr('checked',false);	
			}
		}
	</script>

</head>
<body onload="checkboxes()">
<%@ include file="/pages/header2.jsp" %>
	<p>
		<strong>My profile</strong>
	</p>
	<p>
		<a href="./editAccount.do">Account Information</a> <a href="./editProfile.do">Personal Information</a>
	</p>
	<div>
	<form method="post" name="profileform" id="profileform">
	<p>First Name:<input type="text" id="firstname" name="firstname" value="${userDetails.firstName}">
	<span id="firstnameError" style="color: red;"></span>
	</p> 
	<p>Last Name:<input type="text" id="lastname" name="lastname" value="${userDetails.lastName}">
	<span style="color: red;" id="lastnameError"></span></p>
	<p>Nickname:<input type="text" id="nickname" name="nickname" value="${userDetails.nickName}">
	<span id="nicknameError" style="color: red;"></span></p>
	<p>Phone Number:<input type="text" id="phonenumber"  name="phonenumber"value="${userDetails.phoneNo}">
	<span id="phonenumberError" style="color: red;"></span></p>
	<p>City:<input type="text" name="city" id="city" value="${userDetails.city}">
	<span id="cityError" style="color: red;"></span></p>
	<p>State: <select ><option>select a state </option></select></p>
	<p>Golf Handicap:<input type="text"  maxlength="2" id="golfHandicap" name="golfHandicap" value="${userDetails.golfHandicap}">
	<span id="golfHandicapError" style="color: red;"></span></p>
	<p>Average Score:<input type="text" id="averageScore" maxlength="3" name="averageScore" value="${userDetails.averageScore}">
	<span id="averageScoreError" style="color: red;"></span></p>
	<p>Birthday:<input type="text" value="" id="birthdaydate" name="date"  />
	<span id="birthdaydateError" style="color: red;"></span></p>
	Email Notifications
	<%-- <p><input type="checkbox" name="notify_tripleader" checked='${userDetails.updateByTripLeaderNotification }'>Notify me of trip updates made by Trip Leader</p>
	<p><input type="checkbox" name="notify_tripanyone" checked='${userDetails.updateMadeByAnyoneNotification }'>Notify me of trip updates made by anyone</p>
	<p><input type="checkbox" name="notify_acceptedinv" checked='${userDetails.notifeeAcceptedNotification }'>Notify me as Trip Leader that invitee has accepted invitation</p>
	<p><input type="checkbox" name="notify_rejectedinv" checked='${userDetails.notifeeRejectedNotification }'>Notify me as Trip Leader that invitee has rejected invitation</p>
	<p><input type="checkbox" name="notify_awardcreated" checked='${userDetails.awardCreationNotification }'>Notify me when an award is created</p>
	<p><input type="checkbox" name="notify_sendemail" checked='${userDetails.birthdayEmailSend }'>Send birthday email</p>
	 --%>
	<p><input type="checkbox" name="notify_tripleader" id="notify_tripleader" value='${userDetails.updateByTripLeaderNotification }'>Notify me of trip updates made by Trip Leader</p>
	<p><input type="checkbox" name="notify_tripanyone" id="notify_tripanyone" value='${userDetails.updateMadeByAnyoneNotification }'>Notify me of trip updates made by anyone</p>
	<p><input type="checkbox" name="notify_acceptedinv" id="notify_acceptedinv" value='${userDetails.notifeeAcceptedNotification }'>Notify me as Trip Leader that invitee has accepted invitation</p>
	<p><input type="checkbox" name="notify_rejectedinv" id="notify_rejectedinv" value='${userDetails.notifeeRejectedNotification }'>Notify me as Trip Leader that invitee has rejected invitation</p>
	<p><input type="checkbox" name="notify_awardcreated" id="notify_awardcreated" value='${userDetails.awardCreationNotification }'>Notify me when an award is created</p>
	<p><input type="checkbox" name="notify_sendemail" id="notify_sendemail" value='${userDetails.birthdayEmailSend }'>Send birthday email</p>
	
	<p><input type="button" value="save" onclick="submitProfilePersonalForm();">
	<input type="button" value="cancel" onclick="cancel()"></p>
	</form>
	
	</div>

</body>
</html>