<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Plan a New Trip</title>
	<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script> -->
	<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" /> -->
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
	<script type="text/javascript" src="javascript/js/createTrip.js"></script>	
	<script type="text/javascript" src="javascript/js/jquery.form.js"></script>
	<script type="text/javascript" src="javascript/js/ajaxfileupload.js"></script>
	<script type="text/javascript">
	 $(document).ready(function()
			 {
		 		$("#tripName").change(function()
				{		
					 if($('#tripName').val()!='${tripMap.trip.tripName}'){
						$.post("/tripcaddie/tripExistance.do",{
			    			tripName : $('#tripName').val()
			    		},
			    		function(data){
			    			if(data=="success"){
			    				$('#tripnameError').text("");
			    				$('#tripnameError').text("Trip Name already exists");	    					
			    			}
			    			else
			    				{
			    					$('#tripnameError').text("");
			    				}
						});
						
						 }
						
					});		
		 		
					
			 });
	</script>
</head>
<body>
	<%@ include file="/pages/header.jsp" %>
	<!-- <form name="tripForm" enctype="multipart/form-data" method="post" action="./createTrip.do" onsubmit="();"> -->
	<form id="tripForm" name="tripForm" enctype="multipart/form-data" method="post" >
		<p>Name your trip</p>
		<p><input type="text" id="tripName" name="tripName" /><span id="tripnameError"></span></p>
		<p>Destination</p>
		<p><input type="text" id="destination" name="destination" value="${golfCourse.courseName}" readonly="readonly"/></p>
		<input type="hidden" name="courseId" value="${golfCourse.golfCourseId}">
		<p>Trip start date</p>
		<p><input type="text" id="startDatePicker" name="startDate" readonly="readonly"/><span id="startDateError"></span></p>
		<p>Trip End date</p>
		<p><input type="text" id="endDatePicker" name="endDate" readonly="readonly"/><span id="endDateError"></span></p>
		<p>Welcome Message:</p>
		<p><input type="text" name="message" id="welcomeMessage" value=""><span id="messageError"></span></p>
		<p>Golf Trip or Group Logo:</p>
		<p><input type="file" name="image" id="image"><span id="fileuploadError"></span></p>
		<p>Promo code:</p>
		<p><input type="text" id="promoCode" name="promoCode" value=""/></p>
		<p>Is this a Annual trip?:</p>
		<input type="radio" id="YesBtn" value="yes" checked="checked" name="annualTrip" />Yes&nbsp;<input type="radio" name="annualTrip" id="noBtn" value="no" />No
		<p><input type="button" value="Create" name="createBtn" onclick="submitForm();"/>
		<input type="button" value="Cancel" onclick="cancel();" /></p>
	</form>
</body>
</html>