<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Profile | TripCaddie</title>

<link rel="stylesheet" media="all" type="text/css" href="/tripcaddie/css/jquery-ui-timepicker-addon.css" />
<!-- <link rel="stylesheet" media="all" type="text/css" href="/tripcaddie/css/jquery.utils.css" /> -->
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript" src="javascript/js/addActivity.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery-ui-sliderAccess.js"></script>
<!-- <script type="text/javascript" src="javascript/timepicker/jquery.utils.js"></script>
<script type="text/javascript" src="javascript/timepicker/ui.timepickr.js"></script>
<script type="text/javascript" src="javascript/timepicker/ui.all.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery.anchorHandler.js"></script>
<script type="text/javascript" src="javascript/timepicker/jquery.strings.js"></script> -->

<script type="text/javascript">
			
	$(function(){
		
		$('#time').timepicker({
			hourGrid: 4,
			minuteGrid: 10,
			timeFormat: 'hh:mm tt'
		}); 
		/* $('#time').timepickr({
			handle: '#trigger-test',
		    convention: 12
		}); */
		
       // $('#time').timepickr({convention:12});
        
        // temporary fix..
        //$('.ui-timepickr ol:eq(0) li:first').mouseover();
	});
</script>

<script type="text/javascript">
	var pending=${activity.timePending};
	var activityDate='${activity.activitydateString}';
	var activityTime='${activity.activityTimeString}';
	var actytype='${activity.activityTypeDto.activityTypeId}';
	var hh,mm,ampm;
	var timeSet;
	var activityId;
	activityId=${activity.activityId};
		
	function initialize1(){
		
		/* startDateArray=startDate.split("/");
		endDateArray=endDate.split("/"); */
		
		//alert(startDateArray);
		
		$('#teeTimeBtn').hide();
		$('#date').val(activityDate);
		
		if(pending==0){
			var timeArr=activityTime.split(":");
			
			if(timeArr[0] > 12){
				hh=timeArr[0]-12;
				ampm='pm';
			}else{
				hh=timeArr[0];
				ampm='am';
			}
			mm=timeArr[1];
			timeSet = hh+':'+mm+' '+ampm;
			setTime();
		}else{
			$('#pending').attr('checked','checked');
			$('#time').attr('disabled','disabled');
		}
		
		$('#activityType > option').each(function(){
			if(this.id == actytype){
				$(this).attr('selected','selected');
			}
		});
	}
	
	function setTime(){
		
		$('#time').val(timeSet);
		
	}
	
	function deleteActivity(){
		$.post('deleteActivity.do',{
			activityId:id
		},function(data){
			if(data=="success"){
				window.loaction.href='/tripcaddie/activities.do?tripId='+tripId;	
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	
	function cancel(){
		window.location.href='/tripcaddie/activities.do?tripId='+tripId;
	}
	
	function saveEdit(){
		if(validate()){
			var type=$('#activityType').val();
			var title=$('#title').val();
			var date=$('#date').val();
			var timetxt=$('#time').val();
			var pending=$('#pending').is(':checked');
			var url=$('#url').val();
			var comments=$('#comments').val();
			var time=timetxt.split(' ').join(':');
			//alert(time);			
			if(pending){
				pending=1;
			}else{
				pending=0;
			}
			if(validate()){
				/* query="activityId="+activityId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&mapInfo="+mapInfo+"&comment="+comments+"&pending="+pending; */
				query="activityId="+activityId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&comment="+comments+"&pending="+pending;
				$.ajax({
					url: "editActivity.do",
					type: "POST",
					async: false,
					data: query,
					success: function(data){
						if(data=="error"){
							window.location.href='/tripcaddie/error.do';
						}else{
							window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
						}
					}
				});
			}
		}
	}
</script>
<style type="text/css">
	.star{
		color: red;
	}
</style>
</head>
<body onload="initialize1()">
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
		<p><input type="text" id="title" value="${activity.activityName}"/> 
		<label id="titleError"></label></p>
		
		<p>Date:<span class="star">*</span></p>
		<p><input type="text" id="date" readonly="readonly" value="" /> 
		<label id="dateError"></label></p>
		
		<p><label><fmt:formatDate value="${now }" type="date" dateStyle="long"/></label></p>
		<p>Time:<span class="star">*</span>&nbsp;&nbsp;Time Pending: <input type="checkbox" id="pending" /></p>
		<p>	
			<input type="text" name="time" id="time" value="" />
			<label id="timeError"></label>
		</p>
		<p>URL:</p>
		<p><input type="text" id="url" value="${activity.url }"/>
		<label id="urlError"></label> </p>
		<p>Comments:</p>
		<p><textarea rows="5" cols="20" id="comments">${activity.comment }</textarea>
		<label id="commentError"></label> </p>
		<p>
			<input type="button" value="Add Tee Time" id="teeTimeBtn" onclick="addTeeTime()" />
			<input type="button" value="Save" id="saveBtn" onclick="saveEdit()" />
			<input type="button" value="delete" id="deleteBtn" onclick="deleteActivity()" />
			<input type="button" value="Cancel" id="cancelBtn" onclick="cancel()" />
		</p> 
		
	</form>
</body>
</html>