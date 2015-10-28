<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${poll.questions } | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	var pollId,tripId;
	function vote(id){
		
		var option;
		var arrOption;
		if($('input:radio[name=poll-'+id+']:checked').size() != 0){
			option=$('input:radio[name=poll-'+id+']:checked').val();
			arrOption=option.split('-');
			id=arrOption[1];
		//	alert(arrOption);
			$.post("voteInPoll.do",{
				optionId : id
			},function(data){
				if(data == "success"){
					window.location.reload();
				}else{
					window.location.href="/tripcaddie/error.do";
				}
			});
		}else{
			alert("Select value for voting");
		}
	}
	
	function getTripHome(){
		
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	
	function back(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=poll";
	}
	
	function deletePoll(){
		
		$.get("deletePoll.do",{
			pollId : pollId
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=poll";
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	
	function editPoll(){
		window.location.href="/tripcaddie/editPoll.do?pollId="+pollId;
	}
	
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		pollId=${poll.pollId};
		tripId=${poll.tripDto.tripId};
	</script>
	
	<input type="button" value="Back" onclick="back();" />&nbsp;<input type="button" value="Return to Trip Home" onclick="getTripHome();" />
	
	<h3>${poll.questions }</h3>&nbsp;&nbsp;&nbsp;<a href="#" onclick="editPoll()">Edit</a> | <a href="#" onclick="deletePoll()"> Delete</a>
	
	<p>
		<c:out value="${poll.tripMemberDto.tripCaddieUserDto.firstName }" /> <c:out value="${poll.tripMemberDto.tripCaddieUserDto.lastName }" />
		<fmt:formatDate value="${poll.createdDate.time}" timeStyle="medium" type="both" dateStyle="full" /> 
	</p>					

	<c:choose>
		<c:when test="${poll.voteOrNot == 0}">
			<c:forEach items="${poll.pollOptionDtos }" var="option">
				<p><input type="radio" name="poll-${poll.pollId}" id="option-${option.optionId}-${poll.pollId}" value="option-${option.optionId}-${poll.pollId}">${option.options }</p>
			</c:forEach>
			<input type="button" value="vote" onclick="vote(${poll.pollId })"/>
		</c:when>
		<c:otherwise>
			<c:forEach items="${poll.pollOptionDtos }" var="option" varStatus="optionList">
				<p>Option ${optionList.count} : ${option.percentageOfVote } </p>
			</c:forEach>
			
		</c:otherwise>
	</c:choose>
	<%-- <c:forEach items="${poll.pollOptionDtos }" var="option">
		<p><input type="radio" name="poll-${poll.pollId}" id="option-${option.optionId}-${poll.pollId}" value="option-${option.optionId}-${poll.pollId}">${option.options }</p>
	</c:forEach> --%>
	<!-- <input type="button" value="vote" onclick="vote(${poll.pollId })"/>	-->
	
	 

</body>
</html>