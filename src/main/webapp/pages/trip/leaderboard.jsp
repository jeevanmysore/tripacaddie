<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leaderboard | TripCaddie</title>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<!-- <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script> -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<style type="text/css">
		.form-required{
			color: red;
			font-size: 8pt;
		}
		.ui-state-error{
		background-color: lime;
		}
	</style>
<script type="text/javascript">
function displayTripHome(){
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}
function back(){
	window.history.back();
}

function initialize(){
	<c:if test="${homePage}">
	$('#TripIdSelectBox').val(${tripId});
	$('#pBoxContent').empty();
	$('#pBoxContent').text('TripCaddie allows you to easily view a Leaderboard from any trip you attended. Select a trip you attended to view its associated Leaderboard. Scorecards for Rounds are accessible by selecting the Round on the Leaderboard where it was played.');
	</c:if>
}
function tripvideo(id){
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}

function openlbLock(){
	$.post('/tripcaddie/trip/leaderboard/updateLock.do',{
		lblock : 1,
		tripId : tripId
	},function(data){
		if(data=="success"){
			window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?tripId="+tripId;
		}else{
			
		}
	});
}

function closelbLock(){
	
	$.post('/tripcaddie/trip/leaderboard/updateLock.do',{
		lblock : 0,
		tripId : tripId
	},function(data){
		if(data=="success"){
			window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?tripId="+tripId;
		}else{
			
		}
	});
}


</script>
</head>
<body onload="initialize();">
	<%@ include file="/pages/header2.jsp" %>
	<h3>Leaderboard</h3> <input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<p id="pBoxContent">The TripCaddie Leaderboard is where you can track your scores and your group's standings per round and for the entire trip. Select a Round on the Leaderboard to view the scorecard and/or enter your scores during or after your trip. 

Please note: Your Trip Leader has special privileges to edit the scores and manage the Leaderboard. Remember, there must be a "Golf" type activity on your itinerary to use this module.</p>

<p><c:if test="${homePage}">
 <select id="TripIdSelectBox" onchange="changeTripId(this.value);">
 <option value='-1'>--Select--</option>
	<c:forEach var="trip" items="${trips}" varStatus="items">
       <option value="${trip.tripId}">${trip.tripName }</option>
   </c:forEach>
    </select>

</c:if>

<c:choose>
			<c:when test="${tripId == -1 }">
					<p>No Information Available.</p>
					
				</c:when>
				<c:otherwise>
					
				
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	
	<!-- 
	 Trip Details....
	 -->
	<table style="width: 45%;">
	<tr>
	<td>
	<img alt='Embedded Image' width='180px' height='150px' src=data:image/png;base64,${trip.imagebase64} /></td>
	<td><h2><a href="#" style="font-weight: bold;" onclick="tripvideo(${trip.tripId});">${trip.tripName }</a></h2>
	${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }
	<fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long" /> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/>
	</td>
	</tr>
	</table>
	<br>
	Scorecard Status:
	<c:choose>
			<c:when test="${trip.leaderboardlock == 0 }">
				opened( <a href="#" onclick="openlbLock();" >Lock</a>)
					
				</c:when>
				<c:otherwise>
				Locked( <a href="#" onclick="closelbLock();">Unlock</a>)
				</c:otherwise>
</c:choose>
	
	
	<br><br>
	
	Select the Round Heading to view or edit the scorecards for that round
	<br/><br/>
	
	<div id="leaderboard">
	
	<table id="defaultTable" >
	<tr id="default">
	<td>PLAYER NAME</td>
	<td>CITY /STATE</td>
	<td>POS</td>
	</tr>
	</table>
	</div>
	
	
	<div id="RoundPopup" style="display: none;">
	<br/><br/>
	<div id="hidden" style="display: none;"></div>
	<form id="RoundForm" action="updateRound.do" method="post">
	<input type="hidden" name="tripId" value="${trip.tripId}"
				id="hiddentripId"> 
				<input type="hidden" name="roundId" 
				id="HiddenroundId"> 
		<table id="roundTable">
		</table>
		<div id="buttons"></div>
			</form>
	</div>
	</c:otherwise>
</c:choose>
	
	<script type="text/javascript">
	var RoundArray = [];
	
	function onlyNumbers(evt)
	{
	       var e = event || evt; // for trans-browser compatibility
	       var charCode = e.which || e.keyCode;

	       if (charCode > 31 && (charCode < 48 || charCode > 57))
	               return false;

	       return true;

	}
	function openRoundPopup(RoundId){
		
		var tripdate=new Date(${trip.startDate.time.time});
		var curdate=new Date();
		
		if(tripdate<=curdate){
		
		
		$.post('/tripcaddie/trip/leaderboard/getRound.do',{
			roundId : RoundId
		},function(data){
		
			addPopupOptions("RoundPopup");
			   jQuery('#RoundPopup').dialog('open');
			   $('#HiddenroundId').val(RoundId);
			   $('#hidden').empty();
			   $('#roundTable').empty();
			   $('#roundTable').append("<tr><td>PLAYER</td> <td>FRONT9</td> <td>BACK9</td> <td>TOTAL</td></tr>");
			   for(var i=0 ;i<data.json.scores.length;i++){
				   
				   var id='member'+data.json.scores[i].id;
				   RoundArray.push(id);
				   $('#hidden').append("<div id=m"+id+">"+data.json.scores[i].memberId+"</div>")
				   $('#roundTable').append("<tr id="+id+">");
				   $('#'+id).append("<td>"+data.json.scores[i].membername+"</td>");
				   var front="f"+id;
				   var back="b"+id;
				   var total="t"+id;
				   var m1="sumFront('"+front+"','"+back+"','"+total+"');" ;
				   //var m2="sumBack('"+back+"','"+front+"','"+total+"');" ;
				   $('#'+id).append("<td><input onChange="+m1+" id='"+front+"' type='text' onkeypress='return onlyNumbers();' value='"+data.json.scores[i].front9+"' ></td>");
				   $('#'+id).append("<td><input onChange="+m1+" id='"+back+"' type='text' onkeypress='return onlyNumbers();' value='"+data.json.scores[i].back9+"' ></td>");
				   $('#'+id).append("<td><input id='"+total+"' type='text' readonly='readonly' onkeypress='return onlyNumbers();' value='"+data.json.scores[i].total+"' ></td>");
				   $('#roundTable').append("</tr>");
			   }
			   $('#buttons').empty();
			   $('#buttons').append("<input  type='button' value='Save' onClick='saveRoundPopup();' >");
			   $('#buttons').append("<input  type='button' value='cancel' onClick='cancelRoundPopup();' >");
			   
			
		});
		}
				
		}
	
	function saveRoundPopup(){
		var roundid=$('#HiddenroundId').val();
		jQuery('#RoundPopup')
		.dialog('close');
		var flag=false;
		for (i in RoundArray) {
			var front =$('#f'+RoundArray[i]).val();
			var back =$('#b'+RoundArray[i]).val();
			var total =$('#t'+RoundArray[i]).val();
			var memberId=$('#m'+RoundArray[i]).text();
			$.post('/tripcaddie/trip/leaderboard/updateRound.do',{
				roundId : roundid ,
				memberId:memberId,
				front:front,
				back:back,
				total:total
			},function(data){
			
				if(RoundArray.length==(parseInt(i)+1)){
					window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?tripId="+tripId;
				}
			});
		
		
		}
				
	}
	function sumFront(front,back,total){
		if($('#'+front).val()>0 && $('#'+back).val()>0 ){
			var t=parseInt($('#'+front).val()) +parseInt($('#'+back).val());
			$('#'+total).val(t);
		}
		else{
			$('#'+total).val(0);
		}
		
	}
	
	function addPopupOptions(divid){
		  jQuery('#'+divid).dialog(
			{
				resizable : false,
				autoOpen : false,
				height : 425,
				width : 620,
				open: function () {
              $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
          }
			
			});
	}
	function cancelRoundPopup(){
		jQuery('#RoundPopup')
		.dialog('close');
	}
	$(function(){
		
		
		<c:forEach var="activity" items="${activities}" varStatus="items">
			var ap="<td><a href='#' onclick='openRoundPopup(${activity.activityId});'>R"+${items.count}+"</a> </td>";
			$('#default').append(ap);
			</c:forEach>
		$('#default').append("<td>TOTAL</td>");
		$('#defaultTable').append("</tr>");
		
		<c:forEach var="tripmember" items="${members}" varStatus="items">
		$('#defaultTable').append("<tr id='${tripmember.memberId}'><td>${tripmember.membername}</td><td> - </td>");
		var appen="<td>${tripmember.posiotion}</td>";
		<c:if test="${tripmember.posiotion==0}">
		appen="<td> - </td>";
		</c:if>
		$('#${tripmember.memberId}').append(appen);
		
		<c:if test="${empty tripmember.roundScores}">
		for(i=1 ;i<=${roundsize};i++){
			var ap="<td> - </td>";
			$('#${tripmember.memberId}').append(ap);
		}
		   </c:if>
		   
		    <c:forEach var="roundScore" items="${tripmember.roundScores}" varStatus="items">
		    
		    $('#${tripmember.memberId}').append("<td>${roundScore.total}</td>");
		     </c:forEach>
		     $('#${tripmember.memberId}').append("<td>${tripmember.total}</td>");
		     $('#defaultTable').append("</tr>");
		  
		</c:forEach>
		
	});

	function changeTripId(id){
				window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?userhome=1&tripId="+id;
		}
	</script>
</body> 
</html>