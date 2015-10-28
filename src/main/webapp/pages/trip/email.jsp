<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Email Participants | TripCaddoe</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	var tripId;
	function cancel(){
		window.location.href='./getTrip.do?tripId='+tripId;
	}
	
	function sendMemberMail(){
		var text=$('#memberEmail').text();
		text=text.substring(0,text.lastIndexOf(","));
		var mail=text.trim().split(",");
		//validation left
		$.post("./email.do",{
			subject : $('#subject').val(),
			body : $('#body').val(),
			tripId : tripId,
			email : mail.join(",")
		},function(data){
			/*if(data=="success"){
				window.location.href='./getTrip.do?tripId='+tripId;
			}else{
				alert('Need to check');    //By me
			}*/
		});
	window.location.href='./getTrip.do?tripId='+tripId;
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h2>Email Participants</h2>
	<p>An email will be sent to the participant(s) you selected.</p>
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image } /></p><p>
		<span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span>
		<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	<script type="text/javascript">
		tripId=${trip.tripId}
	</script>
	<div id="content">
		<p>To:&nbsp;&nbsp;&nbsp;
			<span id="memberEmail">
				<c:forEach var="member" items="${tripmembers}" varStatus="status">
					<c:choose>
						<c:when test="${ member.tripMemberStatusDto.memberStatus == 'INVITED' }">
							${member.invitedEmail },
						</c:when>
						<c:otherwise>
							${member.tripCaddieUserDto.email },
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</span>
		</p>
		<p>Subject:&nbsp;&nbsp;&nbsp;<input type="text" name="subject" id="subject"/> </p>
		<p>Body:&nbsp;&nbsp;&nbsp;<input type="text" name="body" id="body"/></p>
		<p><input id="sendBtn" type="button" value="send" onclick="sendMemberMail();"/> <input type="button" value="cancel" onclick="cancel();"/> </p>
	</div>
	<br>
		<br>
		<br>
		<br>
</body>
</html>