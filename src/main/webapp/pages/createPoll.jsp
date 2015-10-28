<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Poll | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<style type="text/css">
	.error {
   		color: #ff0000;
   		font-style: italic;
	}
	.star{
		color: red;
	}
</style>
<script type="text/javascript">
	var tripId;
	
	function validate(){
		
		var flag = true;
		
		if($('#question').val().trim().length == 0){	
			$('#questionError').text("Question field can't be empty");
			flag = false;
		}else{
			$('#questionError').text("");
		}
		
		$('#pollForm > span').each(function(){
			//alert(this.id);
			if($('#option'+this.id).val().trim() == 0){
				$('#option'+this.id+'err').text("option field can't be empty");
				flag = false;
			}else{
				$('#option'+this.id+'err').text("");
			}
		});
		
		return flag;
	}
	
	function savePoll(){
		
		if(validate() == true){
			$('#pollForm').submit();	
		}
	}
	
	function cancel(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=poll";
	}
	
	function addChoice(){
		
		var id;
		$('#pollForm > span').each(function(){
			id=this.id;
		});
		var spanTag=document.createElement("span");
		spanTag.setAttribute("id",parseInt(id)+1);
		var append='<input type="hidden" name="optionId['+parseInt(id)+']" value="0" /> Option '+(parseInt(id)+1)+' : <input type="text" id="option'+(parseInt(id)+1)+'" name="options['+parseInt(id)+']" />'
		+'<span id="option'+(parseInt(id)+1)+'err"></span> <br /><br />';
		spanTag.innerHTML=append;
		document.getElementById("pollForm").appendChild(spanTag);
		
	}
	
</script>
<style type="text/css">
	.star{
		color: red;
	}
</style>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	
	<h3>Create Poll</h3>
	<!-- Trip Details -->
	<p>	<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} />
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName } </a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	<p>${trip.welcomeMessage}</p>
	
	<!-- Poll questions -->
	<form:form action="createPoll.do" method="post" id="pollForm" modelAttribute="poll">
		<input type="hidden" name="pollId" value="0"/>
		<input type="hidden" name="tripId" value="${trip.tripId }"/>
		<P>
			Poll Question <span class="star">*</span>:&nbsp;&nbsp;<input type="text" id="question" name="questions"/>
			<span id="questionError"></span>
		</P>
		<p>Choice <span class="star">*</span>:</p>	
		<input type="hidden" name="optionId[0]" value="0"/>
		<span id="1">Option 1 : <input type="text" id="option1" name="options[0]" />
		<span id="option1err"></span>
		</span>
		<br/><br />
		<input type="hidden" name="optionId[1]" value="0"/>
		<span id="2">Option 2 : <input type="text" id="option2" name="options[1]" />
		<span id="option2err"></span>
		</span>
		
		<br/><br />
	</form:form>
	<p><input type="button" value="Add Another Choice" onclick="addChoice()" />
	<p>
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Save" onclick="savePoll();" />&nbsp;<input type="button" value="Cancel" onclick="cancel()" />
	</p>
</body>
</html>