<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<title>User Profile</title>
</head>
<body>
<%@ include file="/pages/header2.jsp" %>
	<h2><strong>My profile</strong></h2>
	<div id="profile">
	<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${userDetails.imageBase64} />
		<p><span id="name">${ userDetails.firstName} ${userDetails.lastName}</span>&nbsp;<a href="/tripcaddie/user/profile/editAccount.do">Edit</a></p>
		<p>Email address:<span id="email"><a href="mailto:${userDetails.email} ">${userDetails.email}</a></span></p>
		<p><span style="font-weight: bold;">Golf Vitals</span></p>
		<p>Golf Handicap:-<span>${userDetails.golfHandicap}</span></p>
		<p>Average Score For Last 3 18 Hole Rounds:-${userDetails.averageScore}<span></span></p>
		<p>Awards Won<span></span></p>
		<!-- I think java phase 2 -->
		<!-- Awards won -->
	</div>
</body>
</html>