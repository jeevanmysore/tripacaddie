<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Profile | TripCaddie</title>

<link rel="stylesheet" media="all" type="text/css" href="/tripcaddie/css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript" src="javascript/js/addActivity.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery-ui-sliderAccess.js"></script>

<style type="text/css">
	.star{
		color: red;
	}
</style>
<script type="text/javascript">
			
	$(function(){
		
		$('#time').timepicker({
			hourGrid: 4,
			minuteGrid: 10,
			timeFormat: 'hh:mm tt'
		});
	});
</script>
</head>
<body onload="initialize()">
	<%@ include file="/pages/header2.jsp" %>
	<h2>Activity Profile</h2>
	<script type="text/javascript">
		tripId=${trip.tripId};
		startDate='<fmt:formatDate value="${trip.startDate.time}" type="date" pattern="MM/dd/yyyy"/>';
		endDate='<fmt:formatDate value="${trip.endDate.time}" type="date" pattern="MM/dd/yyyy"/>';
		minDays=caluculateDays(startDate);
		maxDays=caluculateDays(endDate);
	</script>
	<!-- Form -->
	
	<form id="activityForm">
		<p>Activity Type:<span class="star">*</span> </p>
		<p><select id="activityType">
			<option>-Type-</option>
			<c:forEach items="${activityType }" var="type">
				<option id="${type.activityTypeId }" value="${type.activityTypeId }">${type.activityType }</option>	
			</c:forEach>
		</select>
		<label id="activityTypeError"></label>
		</p>
		
		<p>Activity Title:<span class="star">*</span></p>
		<p><input type="text" id="title" /> 
		<label id="titleError"></label></p>
		
		<p>Date:<span class="star">*</span></p>
		<p><input type="text" id="date" readonly="readonly" /> 
		<label id="dateError"></label></p>
		
		<p><label><fmt:formatDate value="${now }" type="date" dateStyle="long"/></label></p>
		<p>Time:<span class="star">*</span>&nbsp;&nbsp;Time Pending: <input type="checkbox" id="pending" /></p>
		<p>	
			<input type="text" name="time" id="time" value="" />
			<label id="timeError"></label>
		</p>
		<p>URL:</p>
		<p><input type="text" id="url" />
		<label id="urlError"></label> </p>
		<!-- <p>Map Info:</p>
		<p><textarea rows="5" cols="20" id="mapInfo"></textarea>
		<label id="mapInfoError"></label> </p> -->
		<p>Comments:</p>
		<p><textarea rows="5" cols="20" id="comments"></textarea>
		<label id="commentError"></label> </p>
		<p>
			<input type="button" value="Add Tee Time" id="teeTimeBtn" onclick="addTeeTime()" />
			<input type="button" value="Add another" id="addAnotherBtn" onclick="addAnother()" />
			<input type="button" value="Save" id="saveBtn" onclick="save()" />
			<input type="button" value="Cancel" id="cancelBtn" onclick="cancel()" />
		</p> 
		
	</form>
</body>
</html>