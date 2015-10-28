<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${discussion.title } | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript">
	var tripId,discussionId;
	
	function saveDiscussion(){
		
		$.post("editDiscussion.do",{
			discussionId : discussionId,
			title : $('#title').val(),
			description : $('#description').val() 
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getDiscussion.do?discussionId="+discussionId;
			}else{
				window.location.href="error.do";
			}
		});
	}
	
	function cancel(){
		window.location.href="/tripcaddie/getDiscussion.do?discussionId="+discussionId;
	}
	
	function deleteDiscussion(){
		$.post("deleteDiscussion.do",{
			discussionId : discussionId
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=discussion";
			}else{
				window.location.href="error.do";
			}
		});
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		tripId=${trip.tripId};
		discussionId=${discussion.discussionId};
	</script>
	<h3><c:out value="${discussion.title }" /></h3>
	
	<!--------- Trip Details ---------->
	<p>	<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} />
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName } </a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	<p>${trip.welcomeMessage}</p>
	
	<p>Title : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="title" id="title" value="${discussion.title}"/></p>
	<p>Description :<textarea id="description" rows="5" cols="60">${discussion.description}</textarea> </p>
	<p>
		<input type="button" value="save" onclick="saveDiscussion()" />
		<input type="button" value="Delete" onclick="deleteDiscussion()" />
		<input type="button" value="Cancel" onclick="cancel()" />
	</p>
</body>
</html>