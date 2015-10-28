<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<title>Send Invitation|TripCaddie</title>
<script type="text/javascript">
	var tripId; 
	function cancel(){
		window.location.href='./getTrip.do?tripId='+tripId;
	}
	function validateEmail(sEmail) {
	    var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	       
	    if (emailFilter.test(sEmail)) {
	        return true;
	    }
	    else {
	        return false;
	    }
	 }
	function sendInvite(){
		var email=$('#email_address').val();
		var emailId;
		var flag=true;
		var role=$('#select_trip_role').val();
			
		if(email.indexOf(' ')==1){
			alert('invalid mail id');
		}else if(email.indexOf(',')){
			emailId=email.split(',')
		}else if(email.indexOf('\n')){
			emailId=email.split('\n')
		}else{
			alert('mail id is wrong');
		}
		
		if((role=='trip leader' || role == 'trip co-leader') && emailId.length != 1){
					alert('you can assign only person as trip leader or co-leader');
		}else{
			for(i=0;i<emailId.length;i++){
				if(validateEmail(emailId[i])==false){
					flag=false;
				}
			}
			if(flag==false){
				alert('invalid mail id');
			}else{
				/*$.post("./invitation.do",{
					tripId : tripId,
					email : emailId
				},function(data){
					alert(data);
				});*/
				$.ajax({
					type : "POST",
					data : {tripId:tripId,message:$('#message').val(),userRole:role,emailArray:emailId.join(",")},
					url : "invitation.do",
					success : function(data){
						if(data=='error'){
							alert('remove the invalid email-Id(s)');
						}else{
							window.location.href="./getTrip.do?tripId="+tripId;
						}	
					}
				});
			}
		}
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h2>Send Invitation</h2>
	<p>An invitation will be sent (or resent) to the participant(s) you selected.</p>
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image } /></p><p>
		<span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span>
		<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	<form name="invitation" name="invitation">
		<p>Select Role:
			<select id="select_trip_role" name="trip_role">
				<option value="trip participant">trip participant</option>
				<option value="trip leader">Trip Leader</option>
				<option value="trip co-leader">Trip Co-leader</option>
			</select>
		</p>
		<p>Enter Email address:
			<input type="text" height="10px" id="email_address" />
		</p>
		<p>Add a personal message:
			<input type="text" height="10px" id="message" />
		</p>
		<input type="button" value="send" onclick="sendInvite();" />
		<input type="button" value="cancel" onclick="cancel();" />
	</form>
	<br>
	<br>
</body>
</html>