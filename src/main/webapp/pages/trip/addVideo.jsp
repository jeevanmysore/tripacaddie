<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a Video | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript">
	var tripId;
	function save(){
		$('#videoForm').submit();
	}
	function cancel(){
		window.history.back();
	}
</script>
<style type="text/css">


.error {
   color: #ff0000;
   font-style: italic;
}
	.star{
		color: red;
	}
</style>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Add a Video</h3>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	
	<!-- Trip Details -->
	<p><img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${trip.imagebase64} /></p>
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	
	<!-- For Add photo -->
	<br>
	<form:form action="addVideo.do" method="post"  modelAttribute="videoForm" enctype="multipart/form-data">
		<form:input path="tripId" type="hidden" />
		<P>Video Upload:<span class="star">*</span>&nbsp;&nbsp;&nbsp;<form:input  path="video" type="file"  /> <form:errors path="video" cssClass="error" /></P>
		<p>Description:&nbsp;&nbsp;&nbsp;<form:input path="description" id="description" />
		<p><input type="button" value="Save" onclick="save();" /><input type="button" value="Cancel" onclick="cancel();"/></p>
	</form:form>
</body>
</html>