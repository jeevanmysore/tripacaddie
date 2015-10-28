<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<title>Itinerary | Tripcaddie</title>
<script type="text/javascript">
	var tripId;
	var accommodationId;
	
	function back(){
		window.history.back();
	}
	function displayTripHome(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	function deleteActivity(id){
		var op=confirm("do you want to delete a trip?");
		if(op){
			$.post('deleteActivity.do',{
				activityId:id
			},function(data){
				if(data=="success"){
					window.location.href='/tripcaddie/activities.do?tripId='+tripId;
				}else{
					window.location.href="./error.do";
				}
			});
		}
	}
	function print(){
		window.open('/tripcaddie/pdf.do?tripId='+tripId+'&format=view','_newtab');	
	}
	function save(){
		window.location.href="/tripcaddie/pdf.do?tripId="+tripId+"&format=download";
	}
	function getAccommodation(){
		window.location.href="./accommodation.do?tripId="+tripId;	
	}
	function getLogistics(){
		window.location.href="./logistics.do?tripId="+tripId;
	}
	function getActivities(){
		window.location.href="./activities.do?tripId="+tripId;
	}
</script>
<style type="text/css">
	.popup{
		 position: absolute;
    	 top: 27%;
		 left: 30%;
		 /*margin: -200px 0 0 -275px;*/
		 width: 550px;
		 background: #fff;
		 border: 3px solid #999999;
		 z-index: 999999
	}
</style>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<jsp:useBean id="now" class="java.util.Date" />
	<input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<h2>Itinerary</h2>
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${activities.image } />
		<span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${activities.trip.tripId}">${activities.trip.tripName }</a></span>
		<p>${activities.trip.golfCourseDto.addressDto.city },${activities.trip.golfCourseDto.addressDto.state },${activities.trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${activities.trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${activities.trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	<P>
		<h2>ITINERARY DETAILS</h2>
		<a href="#" onclick="print()">Print</a>
		<a href="#" onclick="save()">Save</a>
	</P>
	<script type="text/javascript">
		tripId=${activities.trip.tripId};
	</script>
	<p><a href="#" onclick="getActivities()">Activities</a><a href="#" onclick="getAccommodation();">Accommodations</a><a href="#" onclick="getLogistics();">Logistics</a></p>
	<div id="activities" >
		<c:if test="${activities.trip.tripLeader eq username && activities.trip.startDate.time gt now}">
			<a href="addActivities.do?tripId=${activities.trip.tripId }">Add Activity</a>
		</c:if>
		<div id="activity">
			<c:set var="date" value="" />
			<c:forEach items="${activities.activity }" var="activity" >
				<c:if test="${activity.activityDate.time ne date }">
					<p>Day <c:out value="${activity.noOfDays}" /> <fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long" /></p>
					<c:set var="date" value="${activity.activityDate.time}" />		
				</c:if>
				<p>&nbsp;
					<c:choose>
					<c:when test="${activity.timePending eq 0 }">
						<fmt:formatDate value="${activity.activityTime }" type="time" timeStyle="long"/>&nbsp;&nbsp;&nbsp;${activity.activityName }
					</c:when>
					<c:otherwise>
						time pending
					</c:otherwise>
				</c:choose>
					
					&nbsp;&nbsp;
					<c:if test="${activities.trip.tripLeader eq username && activities.trip.startDate.time gt now }">
						<a href="editActivity.do?activityId=${activity.activityId}&tripId=${activities.trip.tripId}">Edit</a> | <a href="#" onclick="deleteActivity(${activity.activityId})">Delete</a>
					</c:if>
				</p> 
			</c:forEach>
		</div>
	</div>
</body>
</html>