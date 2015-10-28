<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Discussion | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript">
	var tripId;
	
	function validate(){
		
		var flag = true;
		
		if($('#title').val().trim().length == 0){
			$('#titleError').text("Title is required");
			flag = false;
		}else{
			$('#titleError').text("");
		}
		
		return flag;
	}
	
	function save(){
		//alert(validate());
		if(validate() == true){
		
			$.post('/tripcaddie/createDiscussion.do',{
				tripId : tripId,
				title : $('#title').val(),
				description : $('#description').val()
			},function(data){
				if(data="success"){
					window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=discussion";
				}else{
					window.location.href="/tripcaddie/error.do";
				}
			});
		}
	}
	
	function cancel(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=discussion";
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Create Discussion</h3>
	<script type="text/javascript">
		tripId=${trip.tripId}
	</script>
	<!--------- Trip Details ---------->
	<p>	<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} />
	<p><span style="font-weight: bold;">${trip.tripName } &nbsp; &nbsp;
		<c:if test="${trip.tripLeader eq username }">
			<a href="./editTrip.do?tripId=${trip.tripId}">Edit</a>
		</c:if>
	</span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	<p>${trip.welcomeMessage}</p>
	
	<p>Title : &nbsp;&nbsp; <input type="text" name="title" id="title" />
		<span id="titleError"></span>
	</p>
	<p>Description :<textarea id="description" rows="5" cols="60"></textarea> </p>
	<p>
		<input type="button" value="save" onclick="save()" />
		<input type="button" value="Cancel" onclick="cancel()" />
	</p>
	
</body>
</html>