<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trip Home</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="/tripcaddie/javascript/js/updates.js"></script>
<script type="text/javascript" src="/tripcaddie/javascript/js/weatherWidget.js"></script>

<link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet" />
<link href="/tripcaddie/css/weatherWidget.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />

<style type="text/css">
	.ui-state-error{
		background-color: lime;
	}	
</style>
<script type="text/javascript">
	var tripId;
	
	//Trip Leader Delegation
	$(document).ready(function(){
		
		$('#leaderDelegation').live('change',function(){
			//alert($('#leaderDelegation option:selected').text()+" "+$('#leaderDelegation option:selected').val());
			/*var optionid=$('#leaderDelegation option:selected').val();
			(optionid)*/
			var i=0;
			var memberId = new Array();
			$("input[type='checkbox']:checked").each(function(){
				memberId[i]=this.id;
				i++;
			});
			if(i==0){
				alert('Select members');
			}else if(i==1){
				if($('#leaderDelegation option:selected').val() != 0){
					if($('#s'+memberId[0]).text() !='INVITED'){
					$.post("leaderDelegation.do",{
						
						memberId : memberId[0],
						optionId : $('#leaderDelegation option:selected').val()
						
					},function(data){
						if(data == 'success'){
							window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
						}else{
							window.location.href="/tripcaddie/error.do";
						}
					});
					}else{
						alert("Select only invite accpeted Trip Members");
					}
				}
			}else{
				alert("Select only one member");
			}	
			
		});
	});
	
	
	
	function createPoll(){
		window.location.href="/tripcaddie/createPoll.do?tripId="+tripId;
	}
	
	function vote(id){
		//alert($('input:radio[name=poll-'+id+']:checked').val());
		var option;
		var arrOption;
		if($('input:radio[name=poll-'+id+']:checked').size() != 0){
			option=$('input:radio[name=poll-'+id+']:checked').val();
			arrOption=option.split('-');
			id=arrOption[1];
			//alert(arrOption);
			$.post("voteInPoll.do",{
				optionId : id
			},function(data){
				if(data == "success"){
					window.location.reload();
				}else{
					window.location.href="/tripcaddie/error.do"
				}
			});
		}else{
			alert("Select value for voting");
		}
	}
	
	function getAccommodation(){
		window.location.href="/tripcaddie/accommodation.do?tripId="+tripId;	
	}
	function getLogistics(){
		window.location.href="/tripcaddie/logistics.do?tripId="+tripId;
	}
	function sendEmail(){
		var memberId=new Array();
		var i=0;
		$("input[type='checkbox']:checked").each(function(){
			if(this.id != 'checkAll'){
				memberId[i]=this.id;
				i++;	
			}
		});
		if(i==0){
			alert('Select atleast one email id');
		}else{
			i=0;
			window.location.href='./email.do?tripId='+tripId+'&members='+memberId;
		}
	}
	function revokeMembers(){
		
		var flag=true;
		var memberId=new Array();
		var i=0;
		$("input[type='checkbox']:checked").each(function(){
			memberId[i]=this.id;
			i++;
		});
		if(i==0){
			alert('Select atleast one member');
		}else{
			for(i=0;i<memberId.length;i++){
				if($('#s'+memberId[i]).text() == 'TRIP LEADER'){
					flag=false;
				}
			}
			if(flag==true){
				$.post("./revoke.do",{
					tripId : tripId,
					members : memberId.join(",") 
				},function(data){
					if(data=="success"){
						window.location.reload();
					}else{
						window.location.href="/tripcaddie/error.do";
					}
				});
			}else{
				alert("You can't revoke tripleader");
			}
		}
	}
	function sendInvite(){
		window.location.href="./invitation.do?tripId="+tripId;
	}
	
	function checkall(){
		var op=$('#checkAll').is(':checked');
		if(op){
			 jQuery(function() {
			       jQuery('#itineraryForm').find(':input').each(function(){
			         if(this.type == 'checkbox'){
			        	 $('#'+this.id).attr('checked','checked');	 
			         }
			         
			       });
			});
		}else{
			 jQuery(function() {
			       jQuery('#itineraryForm').find(':input').each(function(){
			         if(this.type == 'checkbox'){
			        	 $('#'+this.id).removeAttr('checked');
			         }
			       });
			});
		}
	}
	function getWall(){
		$('#walls').show();
		$('#discussions').hide();
		$('#polls').hide();
		$('#recentUpdates').hide();
	}
	function getDiscussions(){
		$('#walls').hide();
		$('#discussions').show();
		$('#polls').hide();
		$('#recentUpdates').hide();
	}
	function getPolls(){
		$('#walls').hide();
		$('#discussions').hide();
		$('#polls').show();
		$('#recentUpdates').hide();
		
	}
	function getUpdates(){
		$('#walls').hide();
		$('#discussions').hide();
		$('#polls').hide();
		$('#recentUpdates').show();
	}
	
	function displayPairings(){
		window.location.href="/tripcaddie/trip/pairings.do?tripId="+tripId;
	}
	function displayUserHome(){
		window.location.href="/tripcaddie/userHome.do";
	}
	
	function setValuesForPopup(userId){
		
		var i,append,trTag;
		$.post("/tripcaddie/displayProfilePopup.do",{
			userId : userId
		},function(data){
			//alert(data.tripcaddieUserObject.user.userId);
			$('#usrImage').attr("src","data:image/png;base64,"+data.tripcaddieUserObject.user.imageBase64);
			$('#usrfname').text(data.tripcaddieUserObject.user.firstName);
			$('#usrlname').text(data.tripcaddieUserObject.user.lastName);
			$('#emailAddress').html('<a href="mailto:'+data.tripcaddieUserObject.user.email+'">'+data.tripcaddieUserObject.user.email+'</a>');
			$('#golfHandicap').text(data.tripcaddieUserObject.user.golfHandicap);
			$('#avgScore').text(data.tripcaddieUserObject.user.averageScore);
			
			trTag = document.createElement("tr");
			$('#awardsWon').empty();
			if(data.tripcaddieUserObject.awards.length == 0){
				append="<td colspan='3'>No Awards Received.</td>";
				trTag.innerHTML=append;
				document.getElementById("awardsWon").appendChild(trTag);
			}
			/* for(i=0;i<data.tripcaddieUserObject.awards.length;i++){
				append='<td>'+data.tripcaddieUserObject.awards[i].nominee.tripDto.tripName+'</td><td>'
				+data.tripcaddieUserObject.awards[i].pollenddate+'</td><td>'
				+data.tripcaddieUserObject.awards[i]+'</td>';
			} */
			
		});	
	}
	
	function profilePopup(userId){
		jQuery("#userProfilePopup").dialog({modal: true,
			resizable : false,
			autoOpen : false,
			height : 325,
			width : 490,
			top : '20%',
    		open: function () {
    			$(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
    		}
    	});
		setValuesForPopup(userId);
		jQuery('#userProfilePopup').dialog('open');
		$('#close').click(function(){
			jQuery('#userProfilePopup').dialog('close');
		});
	}
	function displayPhoto(){
		window.location.href="/tripcaddie/trip/getPictures.do?tripId="+tripId;
	}
	function displayAwards(){
		window.location.href="/tripcaddie/trip/getAwardsWon.do?tripId="+tripId;
	}
	function displayStrudel(){
		window.location.href="/tripcaddie/trip/strudel/getStrudelBS.do?tripId="+tripId;
	}
	function displayLeaderBoard(){
		window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?tripId="+tripId;
	}
	function shareOnWall(){   //tripId based
		
		var message = $('#wallMessage').val();
		if( message.trim().length != ""){
			$.post("/tripcaddie/writeOnWall.do",{
				tripId : tripId,
				message : message.trim()
			},function(data){
				if(data == 'success'){
					window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;	
				}else{
					window.location.href="/tripcaddie/error.do";
				}
			});
		}
		
	}
	function deleteComment(commentId){    //commentId based
		$.post("/tripcaddie/deleteComment.do",{
			commentId : commentId
		},function(data){
			if(data == 'success'){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;	
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	function comment(wallId){
		$.post("/tripcaddie/writeComment.do",{
			wallId : wallId,
			comment : $('#text-'+wallId).val()
		},function(data){
			if(data == 'success'){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;	
				$('#comment-'+wallId).hide();
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	function deleteWall(wallId){
		$.post("/tripcaddie/deleteWallPost.do",{
			wallId : wallId
		},function(data){
			if(data == 'success'){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;	
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	function displayComment(wallId){
		$('#comment-'+wallId).show();	
	}
	
	function submitform(id){
       var inputs = $('#'+id+' :input');
       inputs.each(function() {
       if(this.checked){
       	//send ajax request
      	 	$.post("/tripcaddie/ratingDiscussion.do",{
        		discussionId : this.name,
				tripId:tripId,
				rating:this.value
			},function(data){
				//alert(data);
			});
  		}
    	});
	}    
	function createDiscussion(){
		window.location.href="/tripcaddie/displayCreateDiscussion.do?tripId="+tripId;	
	}
	
	
	
	function initialize(){
		
		<c:if test="${tripMap.polltab}">
		setTimeout(getPolls, 500);
		</c:if>
		
		<c:if test="${tripMap.discussiontab}">
		setTimeout(getDiscussions, 500);
		</c:if>
		
		
		
		var city='${tripMap.trip.golfCourseDto.addressDto.city}';
		var state='${tripMap.trip.golfCourseDto.addressDto.state}';
		var state1=state.replace(" ","_");
		var city1=city.replace(" ","_");
		
		var day='';
		var high='';
		var low='';
		
		$('#discussions').hide();
		$('#polls').hide();
		$('#recentUpdates').hide();
		
		updateWeatherWidget(state,city);
	}
	function getGames(){
		window.location.href="/tripcaddie/games/recent.do";
	}
	function getJokes(){
		window.location.href="/tripcaddie/jokes/recent.do";
	}
	
</script>
<script type="text/javascript">

	function print(){
		window.open('/tripcaddie/pdf.do?tripId='+tripId+'&format=view','_newtab');
		
	}
	function save(){
		window.location.href="/tripcaddie/pdf.do?tripId="+tripId+"&format=download";
	}
</script>

</head>
<body onload="initialize()">
	<%@ include file="/pages/header2.jsp" %>
	<jsp:useBean id="now" class="java.util.Date" />
	<h3>Trip Home</h3>
	<script type="text/javascript">
		tripId=${tripMap.trip.tripId}
	</script>
	
	<p style="text-align: right;"><input type="button" value="Photos/Videos" onclick="displayPhoto();"/> </p>
	
	<p style="text-align: right;"><input type="button" value="Awards" onclick="displayAwards();"/></p>
	<p style="text-align: right;"><input type="button" value="Strudel" onclick="displayStrudel();"/></p>
		<p style="text-align: right;"><input type="button" value="LeaderBoard" onclick="displayLeaderBoard();"/></p>
	<!-- <p style="text-align: right;"><input type="button" value="My trips" onclick="displayUserHome();"/></p> -->
	<p style="text-align: right;"><input type="button" value="Pairings" onclick="displayPairings();"/></p>
	<p style="text-align: right;"><input type="button" value="Games" onclick="getGames();"/> </p>
	<p style="text-align: right;"><input type="button" value="Jokes" onclick="getJokes();"/> </p>
	<!-- <input type="button" style="text-align: right;" value="bucketList" /> --> 
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${tripMap.image } />
		<p><span style="font-weight: bold;">${tripMap.trip.tripName } &nbsp; &nbsp;
			<c:if test="${tripMap.trip.tripLeader eq username && tripMap.trip.startDate.time gt now }">
				<a href="./editTrip.do?tripId=${tripMap.trip.tripId}">Edit</a>
			</c:if>
		</span></p>
		<p>${tripMap.trip.golfCourseDto.addressDto.city },${tripMap.trip.golfCourseDto.addressDto.state },${tripMap.trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${tripMap.trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${tripMap.trip.endDate.time }" type="date" dateStyle="long"/></p>
		<p>${tripMap.trip.welcomeMessage}</p>
	</p>
	<!-- Weather report -->
	<div class="h">
		<div class="first">
			<div class="inside" id="topIcon">
				<!-- <img src="images/top_clear.png"></img> 
				 <img src="images/top_partlycloudy.png"></img> 
				<img src="images/top_mostlycloudy.png"></img> -->
				<!-- <img src="images/top_cloudy.png"></img> -->
				<!-- <img src="images/top_rain.png"></img>
				 <img src="images/top_chancerain.png"></img>
				<img src="images/top_snow.png"></img>
				<img src="images/top_chancesnow.png"></img>
				<img src="images/top_fog.png"></img>-->
			</div>
			<div class="inside1" id="city">
				
			</div>
			<div class="inside2" id="tempF">
				 
			</div>
		</div>
		<div class="lower">	
			<div class="ldata" id="day1">		
				
			</div>	
			<div class="ldata" id="day2">		
				
			</div>	
			<div class="ldata" id="day3">		
				
			</div>	
			<div class="ldata" id="day4">		
				
			</div>	
			<div class="ldata" id="day5">		
				
			</div>	
			<div class="ldata" id="day6">		
				
			</div>	
		</div>
		
		<div class="lower1">	
			<div class="ldata3" id="image1" ALIGN="MIDDLE">		
				
			</div>	
			<div class="ldata3" id="image2" ALIGN="MIDDLE">		
				
			</div>	
			<div class="ldata3" id="image3" ALIGN="MIDDLE">		
				
			</div>	
			<div class="ldata3" id="image4" ALIGN="MIDDLE">		
				
			</div>	
			<div class="ldata3" id="image5" ALIGN="MIDDLE">		
				
			</div>	
			<div class="ldata3" id="image6" ALIGN="MIDDLE">		
				
			</div>	
		</div>

		<div class="lower2">	
			<div class="ldata12" id="highTemp1">		
				
			</div>	
			<div class="ldata12" id="highTemp2">		
				
			</div>	
			<div class="ldata12" id="highTemp3">		
				
			</div>	
			<div class="ldata12" id="highTemp4">		
				
			</div>	
			<div class="ldata12" id="highTemp5">		
				
			</div>	
			<div class="ldata12" id="highTemp6">		
				
			</div>	
		</div>
		
		<div class="lower2">	
			<div class="ldata12" id="lowTemp1">		
				
			</div>	
			<div class="ldata12" id="lowTemp2">		
				
			</div>	
			<div class="ldata12" id="lowTemp3">		
				
			</div>	
			<div class="ldata12" id="lowTemp4">		
				
			</div>	
			<div class="ldata12" id="lowTemp5">		
				
			</div>	
			<div class="ldata12" id="lowTemp6">		
				
			</div>	
		</div>
	</div>
	<p>
		<h4>Itinerary Summary</h4> <a href="#" onclick="print();">Print</a> <a href="#" onclick="save();">Save</a>
		<p><a href="#" onclick="getAccommodation();">Accommodations</a>
		<a href="#" onclick="getLogistics();">Logistics</a></p>
	</p>
	<p>
		<c:choose>
			<c:when test="${tripMap.trip.tripLeader eq username }">
				<c:if test="${tripMap.trip.startDate.time gt now}">
					<a href="addActivities.do?tripId=${tripMap.trip.tripId }">Add</a>
				</c:if>
				<a href="activities.do?tripId=${tripMap.trip.tripId }">View All</a>
			</c:when>
			<c:otherwise>
				<a href="activities.do?tripId=${tripMap.trip.tripId }">View All</a>
			</c:otherwise>
		</c:choose>
	</p>	
	<!-- Acticities -->
	<div>
		<c:set var="date" value="" />
		<c:forEach items="${tripMap.activity }" var="activity" >
				<c:if test="${activity.activityDate.time ne date }">
					<p>Day <c:out value="${activity.noOfDays}" /> <fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long" /></p>
					<c:set var="date" value="${activity.activityDate.time}" />		
				</c:if>
			<p>&nbsp;
				<c:choose>
					<c:when test="${activity.timePending eq 0 }">
						<fmt:formatDate value="${activity.activityTime }" type="time" timeStyle="medium" pattern="hh:mm aa"/>
					</c:when>
					<c:otherwise>
						time pending
					</c:otherwise>
				</c:choose>
			&nbsp;${activity.activityName }</p> 
		</c:forEach>
	</div>
	
		<h4>Attendance And status</h4>
		<form name="itineraryForm" id="itineraryForm">
			<table>
				<tr>
					<th><input type="checkbox" id='checkAll' onclick="checkall();"></th>
					<th>Participant</th>
					<th>Status</th>
					<th>Payment Status</th>
				</tr>
				<c:forEach var="member" items="${tripMap.tripMembers}" varStatus="status">
					<tr>
						<td><input id="${member.memberId}" type="checkbox"></td>
						<c:choose>
							<c:when test="${member.tripMemberStatusDto.memberStatus == 'INVITED'}">
								<td>${member.invitedEmail }</td>								
							</c:when>
							<c:otherwise>
								<td>
									<c:choose>
										<c:when test="${member.tripCaddieUserDto.userName eq username}">
											<a href="/tripcaddie/user/profile.do">${member.tripCaddieUserDto.firstName } ${member.tripCaddieUserDto.lastName }(
												<c:choose>
													<c:when test="${empty member.tripCaddieUserDto.golfHandicap}">
														<a href="/tripcaddie/user/profile/editProfile.do">HCI?</a>
													</c:when>
													<c:otherwise>${member.tripCaddieUserDto.golfHandicap}</c:otherwise>
												</c:choose>
											</a>
										</c:when>
										<c:otherwise>
											<a href="#" onclick="profilePopup(${member.tripCaddieUserDto.userId});">${member.tripCaddieUserDto.firstName } ${member.tripCaddieUserDto.lastName }(
												<c:choose>
													<c:when test="${empty member.tripCaddieUserDto.golfHandicap}">HCI?</c:when>
													<c:otherwise>${member.tripCaddieUserDto.golfHandicap}</c:otherwise>
												</c:choose>
											</a>
										</c:otherwise>
									</c:choose>
									<%-- <a href="">${member.tripCaddieUserDto.firstName } ${member.tripCaddieUserDto.lastName }(
										<c:choose>
											<c:when test="${empty member.tripCaddieUserDto.golfHandicap}">HCI?</c:when>
											<c:otherwise>${member.tripCaddieUserDto.golfHandicap}</c:otherwise>
										</c:choose>
									</a> --%>
								)</td>
							</c:otherwise>
						</c:choose>
						<td id="s${member.memberId}">${member.tripMemberStatusDto.memberStatus}</td>
						<td>${member.paidModeDto.paidMode }</td>
					</tr>				
				</c:forEach>
				<c:choose>
					<c:when test="${tripMap.trip.tripLeader eq username && tripMap.trip.startDate.time gt now}">
						<tr>
							<td colspan="4">
								<select id="leaderDelegation" style="width: 180px;">
									<option id="0" value="0">Assign Role</option>
									<c:forEach items="${tripMap.delegationTypes }" var="delegationType" varStatus="list">
										<option id="${list.count }" value="${delegationType.delegationOptionId }">${delegationType.delegationOption }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>	
							<td><a href="#" onclick="sendEmail()">Email</a></td>
							<c:if test="${tripMap.trip.startDate.time gt now}">
								<td><a href="#" onclick="sendInvite();">Send Invite</a></td>
								<td><a href="#" onclick="revokeMembers();">Revoke</a></td>
							</c:if>
						</tr>	
					</c:when>
					<c:otherwise>
						<tr>	
							<td><a href="#" onclick="sendEmail()">Email</a></td>
						</tr>
					</c:otherwise>
				</c:choose>
				
			</table>
		</form>
		<!-- WALL -->
		<br>
		<br>
		<div id="socialMedia">
			<a href="#" onclick="getWall();">WALL</a> <a href="#" onclick="getDiscussions();">DISCUSSIONS</a> <a href="#" onclick="getPolls();">POLLS</a> <a href="#" onclick="getUpdates();">RECENT UPDATES</a>
			<div id="walls">
				<p><textarea rows="5" cols="60" name="wallMessage" id="wallMessage"></textarea></p>
				<p><input type="button" value="Share" onclick="shareOnWall()" />
				<c:forEach items="${tripMap.walls }" var="wall">
					<div id="wall-${wall.wallId }">
						<p>
							<img alt="Embedded Image" width='50px' height='45px'
								src=data:image/png;base64,${wall.tripMemberDto.tripCaddieUserDto.imageBase64} />
							<c:choose>
								<c:when test="${wall.tripMemberDto.tripCaddieUserDto.userName eq username}">
									<a href="/tripcaddie/user/profile.do">${wall.tripMemberDto.tripCaddieUserDto.firstName } ${wall.tripMemberDto.tripCaddieUserDto.lastName }(
										<c:choose>
											<c:when test="${empty wall.tripMemberDto.tripCaddieUserDto.golfHandicap}">
												<a href="/tripcaddie/user/profile/editProfile.do">HCI?)</a>
											</c:when>
											<c:otherwise>${wall.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
										</c:choose>
									</a>
								</c:when>
								<c:otherwise>
									<a href="#" onclick="profilePopup(${wall.tripMemberDto.tripCaddieUserDto.userId});">${wall.tripMemberDto.tripCaddieUserDto.firstName } ${wall.tripMemberDto.tripCaddieUserDto.lastName }(
										<c:choose>
											<c:when test="${empty wall.tripMemberDto.tripCaddieUserDto.golfHandicap}">HCI?)</c:when>
											<c:otherwise>${wall.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>
							</c:choose>
							<br/>
							&nbsp;&nbsp;&nbsp;<c:out value="${wall.message }" />
							<br/>
							<fmt:formatDate value="${wall.createdDate.time}" type="date" dateStyle="full"/> at <fmt:formatDate value="${wall.createdDate.time}" type="time" pattern="hh:mm aa"/> 
							<c:choose>
								<c:when test="${wall.createdBy eq username}">
									<a href="#" onclick="displayComment(${wall.wallId})">Comment</a> | <a href="#" onclick="deleteWall(${wall.wallId})">delete</a>  
								</c:when>
								<c:otherwise>
									<a href="#" onclick="displayComment(${wall.wallId})">Comment</a> 
								</c:otherwise>
							</c:choose>	
						</p>
						<c:forEach items="${wall.commentDtos }" var="wallComment">
							<div style="margin-left: 20px;">
								<p>
									<img alt="Embedded Image" width='50px' height='45px'
									 	src=data:image/png;base64,${wallComment.tripMemberDto.tripCaddieUserDto.imageBase64} />
								<c:choose>
									<c:when test="${wallComment.tripMemberDto.tripCaddieUserDto.userName eq username}">
										<a href="/tripcaddie/user/profile.do">${wallComment.tripMemberDto.tripCaddieUserDto.firstName } ${wallComment.tripMemberDto.tripCaddieUserDto.lastName }(
											<c:choose>
												<c:when test="${empty wallComment.tripMemberDto.tripCaddieUserDto.golfHandicap}">
													<a href="/tripcaddie/user/profile/editProfile.do">HCI?)</a>
												</c:when>
												<c:otherwise>${wallComment.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
											</c:choose>
										</a>
									</c:when>
									<c:otherwise>
										<a href="#" onclick="profilePopup(${wallComment.tripMemberDto.tripCaddieUserDto.userId});">${wallComment.tripMemberDto.tripCaddieUserDto.firstName } ${wallComment.tripMemberDto.tripCaddieUserDto.lastName }(
											<c:choose>
												<c:when test="${empty wallComment.tripMemberDto.tripCaddieUserDto.golfHandicap}">HCI?)</c:when>
												<c:otherwise>${wallComment.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
											</c:choose>
										</a>
									</c:otherwise>
								</c:choose></p>
								<p>&nbsp;&nbsp;&nbsp;<c:out value="${wallComment.comment }" /></p>
								<p>
									<fmt:formatDate value="${wallComment.createdDate.time }" type="date" dateStyle="full"/> at <fmt:formatDate value="${wallComment.createdDate.time }" type="time" pattern="hh:mm aa" /> 
								</p>
								<c:if test="${wallComment.createdBy eq username}">
									&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteComment(${wallComment.wallCommentId})">Delete</a>
								</c:if>
							</div>
						</c:forEach>
						<div id="comment-${wall.wallId }" style="display: none;">
							<p><textarea rows="5" cols="60" id="text-${wall.wallId }"></textarea></p>
							<p><input type="button" value="Comment" onclick="comment(${wall.wallId })">
						</div>
					</div>
				</c:forEach>
			</div>
			<div id="discussions">
				<input type="button" value="Create New" onclick="createDiscussion()" />
				<c:forEach items="${tripMap.discussions }" var="discussion">
					<p><a href="getDiscussion.do?discussionId=${discussion.discussionId}"><c:out value="${discussion.title }" /></a></p>
					
					
					<c:choose>
						<c:when test="${discussion.tripMemberDto.tripCaddieUserDto.userName eq username}">
							<a href="/tripcaddie/user/profile.do">${discussion.tripMemberDto.tripCaddieUserDto.firstName } ${discussion.tripMemberDto.tripCaddieUserDto.lastName }(
								<c:choose>
									<c:when test="${empty discussion.tripMemberDto.tripCaddieUserDto.golfHandicap}">
										<a href="/tripcaddie/user/profile/editProfile.do">HCI?)</a>
									</c:when>
									<c:otherwise>${discussion.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
								</c:choose>
							</a>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="profilePopup(${discussion.tripMemberDto.tripCaddieUserDto.userId});">${discussion.tripMemberDto.tripCaddieUserDto.firstName } ${discussion.tripMemberDto.tripCaddieUserDto.lastName }(
								<c:choose>
									<c:when test="${empty discussion.tripMemberDto.tripCaddieUserDto.golfHandicap}">HCI?)</c:when>
									<c:otherwise>${discussion.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
								</c:choose>
							</a>
						</c:otherwise>
					</c:choose>
					
					| <fmt:formatDate value="${discussion.createdDate.time }" dateStyle="full" timeStyle="medium" type="both"/>
					|
							<form id='form${discussion.discussionId}'>

			<div class="Clear" onclick="submitform('form${discussion.discussionId}');">
			
			<c:choose>
				<c:when test="${discussion.avgRating==1}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==2}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="2" title="2/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="2" title="2/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==3}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="3" title="3/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==4}">
				<input class="star" type="radio" name="${discussion.discussionId}" value="4" title="4/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==5}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
			</div>
			<br />
		</form> 
					 | (${discussion.noOfComments } comments)</p>	
				</c:forEach>
			</div>
			<c:set var="totalCount" value="0" />
			<div id="polls">
				<input type="button" value="Create New" onclick="createPoll()" /> 
				<c:forEach items="${tripMap.poll}" var="poll">
					<div>
						<p><a href="getPoll.do?pollId=${poll.pollId}" >${poll.questions}</a><p>
							<c:choose>
								<c:when test="${poll.tripMemberDto.tripCaddieUserDto.userName eq username}">
									<a href="/tripcaddie/user/profile.do">${poll.tripMemberDto.tripCaddieUserDto.firstName } ${poll.tripMemberDto.tripCaddieUserDto.lastName }(
										<c:choose>
											<c:when test="${empty poll.tripMemberDto.tripCaddieUserDto.golfHandicap}">
												<a href="/tripcaddie/user/profile/editProfile.do">HCI?)</a>
											</c:when>
											<c:otherwise>${poll.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
										</c:choose>
									</a>
								</c:when>
								<c:otherwise>
									<a href="#" onclick="profilePopup(${poll.tripMemberDto.tripCaddieUserDto.userId});">${poll.tripMemberDto.tripCaddieUserDto.firstName } ${poll.tripMemberDto.tripCaddieUserDto.lastName }(
										<c:choose>
											<c:when test="${empty poll.tripMemberDto.tripCaddieUserDto.golfHandicap}">HCI?)</c:when>
											<c:otherwise>${poll.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>
							</c:choose>
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
									<p> ${option.options} : ${option.percentageOfVote } (${option.count})</p>
									<c:set var="totalCount" value="${totalCount + option.count }" />
								</c:forEach>
								<p>Total votes: <c:out value="${totalCount }" />
								<c:set var="totalCount" value="0" />
							</c:otherwise>
						</c:choose>
						
						<!-- <input type="button" value="vote" onclick="vote(${poll.pollId })"/>	-->
					</div>
				</c:forEach>
			</div>
			<div id="recentUpdates">
				<table style="background: white;">
					<thead>
						<tr>
							<th>
								<a> WHO	<img id="who" width="13" height="13" src="images/transparent_bg.png" onclick="sortDescByWho()" /></a>
							</th>
							<th>
								<a> WHAT <img id="what" width="13" height="13" src="images/transparent_bg.png" onclick="sortDescByWhat()" /></a>
							</th>
							<th>
								<a> WHEN <img id="when" width="13" height="13" src="images/arrow-asc.png" onclick="sortAscByWhen()"/> </a>
							</th>
						</tr>
					</thead>
					<tbody id="updates">
						<c:forEach items="${tripMap.updates }" var="update">
							
							<tr>
								<td>
									${update.tripMemberDto.tripCaddieUserDto.firstName } ${update.tripMemberDto.tripCaddieUserDto.lastName } 
									<c:choose>
										<c:when test="${empty update.tripMemberDto.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
										<c:otherwise>(${update.tripMemberDto.tripCaddieUserDto.golfHandicap })</c:otherwise>
									</c:choose> 
								</td>
								<td>${update.message }</td>
								<td><fmt:formatDate value="${ update.updatedDate.time}" type="both" pattern="MM/dd/yyyy hh:mm aa" /> </td>
							</tr>
							
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br>
		<br>
		<br>
		<br>
		<!-- Profile popup -->
		<div id="userProfilePopup" style="display: none;overflow: auto;">
			<div id="profile">
				<img id="usrImage" alt="Embedded Image" width="250px" height="250px" src="" /><br/>
				<span id="usrfname"></span> <span id="usrlname"></span><br />
				<p>Email address : <span id="emailAddress"></span> </p>
				<h3>Golf Vitals</h3>
				<p>Golf Handicap: <span id="golfHandicap"></span></p>
				<p>Average Score For Last 3 18 Hole Rounds: <span id="avgScore"></span></p>
			</div>
			<div id="Awards Won">
				<h3>Awards Won</h3>
				<table>
					<thead>
						<tr>
							<td>Golf Trip Name</td>
							<td>End Date</td>
							<td>Award</td>
						</tr>
					</thead>
					<tbody id="awardsWon">
						
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>