<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<title>User home</title>
<style type="text/css">
	.ui-state-error{
		background-color: lime;
	}
</style>
<script type="text/javascript">
	
	function deleteTrip(){
		var op=confirm("do you want to delete a trip?");
		if(op && $('#tripList').val()!=0 ){
			$.post('./deleteTrip.do',{
				tripId:$('#tripList').val()
			},function(data){
				if(data=="success"){
					window.location.href="./userHome.do";	
				}else{
					window.location.href="./error.do";
				}
			});
		}
	}
	
	function getFavPictures(){
		window.location.href="/tripcaddie/user/fav/getFavPictures.do";
	}
	function getAllPictures(){
		window.location.href="/tripcaddie/trip/allPictures.do";}
	
	function getsearchDst(){
		window.location.href="/tripcaddie/getSearchDst.do";
	}
	
	function checkInvitation(){
		jQuery("#invitationsPopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			height: 'auto',
			width: 500,
			top:-535,
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		        $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		    }
			/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		});
		jQuery('#invitationsPopup').dialog('open');
		$('#closeBtn').click(function(){
			jQuery('#invitationsPopup').dialog('close');
			window.location.href="/tripcaddie/userHome.do";
		});
	}
	
	function decline(memId){
		//alert(memId);	
		$.post("declineInvite.do",{
			memberId : memId
		},function(data){
			if(data="success"){
				$('#'+memId).remove();
			}else{
				alert(data);
			}
		});
	}
	
	function accept(memId){
		//alert(memId);
		$.post("acceptInvite.do",{
			memberId : memId
		},function(data){
			if(data="success"){
				$('#'+memId).remove();
			}else{
				alert(data);
			}
		});
	}
	
	function search(){
		
		var emailId=$('#emailTxt').val();
		if(emailId != "" && emailId.trim() != ""){
			$.post("getInvites.do",{
				email : emailId 
			},function(data){
				//alert(data.inviteDetails.invitations.length);
				var dataHolder=data.inviteDetails.invitations;
				for(i=0;i<dataHolder.length;i++){
					var divTag = document.createElement("div");
					divTag.setAttribute("id",dataHolder[i].memberId);
					var append='<span>'+dataHolder[i].tripDto.tripName+'</span>&nbsp;<span>'
					+'<a href="#" onclick="accept('+dataHolder[i].memberId+');">Accept</a>'
					+'</span>&nbsp;<span>'+'<a href="#" onclick="decline('+dataHolder[i].memberId+');">Decline</a>'+'</span>';
					
					divTag.innerHTML=append;
					document.getElementById("inviteTag").appendChild(divTag);
				}
			});
		}else{
			alert("invalid id");
		}
	}
	
	function showSearch(){
		$('#showBtn').hide();
		$("#search").show();
	}
	
	function initialize(){
		<c:if test="${fn:length(invitations) > 0}">
			checkInvitation();
		</c:if>	
	}
	function sortOrder(type,order){
		window.location.href="/tripcaddie/userHome.do?type="+type+"&order="+order;
	}
	function getGames(){
		window.location.href="/tripcaddie/games/recent.do";
	}
	function getJokes(){
		window.location.href="/tripcaddie/jokes/recent.do";
	}
	
	function getLeaderBoard(){
		window.location.href="/tripcaddie/trip/leaderboard/getleaderboard.do?userhome=1&tripId=-1";
	}
	
	function getAwards(){
		window.location.href="/tripcaddie/trip/allAwards.do";
	}
	function activitybrowser(){
		window.location.href="/tripcaddie/browse/activitybrowser.do";
	}
	
</script>
</head>
<body onload="initialize()">
	<%@ include file="/pages/header2.jsp" %>
	<jsp:useBean id="now" class="java.util.Date" />
	<h2>MY TRIPS</h2>
	<img alt='Embedded Image' width='170px' height='120px' src=data:image/png;base64,${user.imageBase64}>
	<h3>${user.firstName } ${user.lastName }&nbsp;&nbsp;<a href="/tripcaddie/user/profile/editAccount.do">Edit</a></h3><br/><br/>
	<a href="#" onclick="getsearchDst();">SearchDestination</a>
	<div style="margin-left: 35%;">
    <caption><h2>COMMUNITY FEATURES</h2></caption>
<a href="#" onclick="activitybrowser();" >Activity Browser</a> &nbsp;&nbsp;<a href="#" onclick="getGames();" >Games</a> &nbsp;&nbsp;<a href="#" onclick="getJokes();">Jokes</a>
</div>
	<h4>My Trips</h4><a href="#" onclick="checkInvitation();">Check My Invitations</a>
	<h5>Current &amp; upcoming Trips</h5>
	<table style="margin-left: 350px;">
	 <caption><h2>MY TRIP GALLERIES - ALL TRIPS</h2> </caption>
	<tr><td style="width: 2%;"><img src="http://www.tripcaddie.com/sites/all/themes/tripcaddie/images/icon5.jpg">
	<a href="#" onclick="getFavPictures();" ><h4>My Favs</h4></a></td>
	
	<td style="width: 3%;"> <img src="http://www.tripcaddie.com/sites/all/themes/tripcaddie/images/icon6.jpg">
	<a href="#" onclick="getAllPictures();" ><h4>Photos / Videos</h4></a></td>
	
	<td style="width: 2%;"> <img src="http://www.tripcaddie.com/sites/all/themes/tripcaddie/images/icon7.gif">
	<a href="#" onclick="getLeaderBoard();" ><h4>Leaderboard</h4></a></td>
	
	<td style="width: 20%;"><img src="http://www.tripcaddie.com/sites/all/themes/tripcaddie/images/icon8.jpg">
	<a href="#" onclick="getAwards();" ><h4>Awards</h4></a></td>
	</tr>
	</table>
	<div style="margin-left: 20%;">RECENT UPDATES - ALL TRIPS</div>
	<table style="margin-left: 20%;">
	
	<tr><td>TRIP NAME<img onclick="sortOrder('trip','asc');" src="/tripcaddie/images/arrow-asc.png"><img onclick="sortOrder('trip','desc');" src="/tripcaddie/images/arrow-desc.png"></td> <td> WHO <img onclick="sortOrder('name','asc');" src="/tripcaddie/images/arrow-asc.png"><img  onclick="sortOrder('name','desc');" src="/tripcaddie/images/arrow-desc.png"></td><td>WHAT<img onclick="sortOrder('msg','asc');"  src="/tripcaddie/images/arrow-asc.png" ><img onclick="sortOrder('msg','desc');" src="/tripcaddie/images/arrow-desc.png"></td> <td> WHEN<img onclick="sortOrder('date','asc');" src="/tripcaddie/images/arrow-asc.png" ><img onclick="sortOrder('date','desc');" src="/tripcaddie/images/arrow-desc.png"></td></tr>
	<c:forEach var="update" items="${updates}">
		<tr><td>${update.tripDto.tripName}</td> <td>
									${update.tripMemberDto.tripCaddieUserDto.firstName } ${update.tripMemberDto.tripCaddieUserDto.lastName } 
									<c:choose>
										<c:when test="${empty update.tripMemberDto.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
										<c:otherwise>(${update.tripMemberDto.tripCaddieUserDto.golfHandicap })</c:otherwise>
									</c:choose> 
								</td><td>${update.message} </td> <td><fmt:formatDate value="${update.updatedDate.time}" type="both" pattern="MM/dd/yyyy" timeStyle="short"/> </td></tr>
	</c:forEach>
	
	
	
	</table>
	
	<table>
		<c:forEach var="trip" items="${trips }">		
			<c:if test="${trip.endDate.time ge now }">
			<tr>
				<td width="10px">
					<c:if test="${username eq trip.tripLeader }">						
						<img src="./images/thumbanils.jpg">
					</c:if>
				</td>
				<td><a href="./getTrip.do?tripId=${trip.tripId}">${trip.tripName}</a></td>
			</tr>
			<tr>
				<td colspan="2"><fmt:formatDate value="${trip.startDate.time}" type="date" dateStyle="long" /></td>
			</tr>
			</c:if>
		</c:forEach>
	</table>
	<br/><br/>
	<h5>Past trips</h5>
	<table>
		<c:forEach var="trip" items="${trips }">
			<c:if test="${trip.endDate.time le now}">
			<tr>
				<td width="10px">
					<c:if test="${username eq trip.tripLeader }">						
						<img src="./images/thumbanils.jpg">
					</c:if>
				</td>
				<td><a href="./getTrip.do?tripId=${trip.tripId}">${trip.tripName}</a></td>
			</tr>
			<tr>
				<td colspan="2"><fmt:formatDate value="${trip.startDate.time}" type="date" dateStyle="long"/></td>
			</tr>
			</c:if>
		</c:forEach>
	</table>
	<br/><br/>
	
	<h2>
    DELETE TRIP  </h2>
	
	<select name="tripList" id="tripList" onchange="deleteTrip()">
		<option value="0">Select trip</option>
		<c:forEach var="trip" items="${trips }">
			<c:if test="${username eq trip.tripLeader }">						
				<option value="${trip.tripId }">${trip.tripName }</option>
			</c:if>
		</c:forEach>
	</select>
	
	<div id="invitationsPopup"  style="display: none;">
		<p>My pending Invitations</p>
		<!-- <span msg=""></span> -->
		<div id="inviteTag">
			<div>
				<span style="font-weight: bold;">Trip Name</span>
				<span style="font-weight: bold;">Accept</span>
				<span style="font-weight: bold;">Decline</span>
			</div>
			<c:forEach items="${invitations }" var="invitation">
				<div id="${invitation.memberId }">
					<span><c:out value="${invitation.tripDto.tripName }"/></span>
					<span><a href="#" onclick="accept(${invitation.memberId });">Accept</a></span>
					<span><a href="#" onclick="decline(${invitation.memberId });">Decline</a></span>
				</div>
			</c:forEach>	
		</div>
		<input type="button" id="showBtn" value="My Invitation is Not Listed" onclick="showSearch();" />
			
		<div id="search" style="display: none;">
			<p><input type="text" id="emailTxt" />
			<input type="button" id="searchBtn" value="search" onclick="search();" /> </p>
			<p><input type="button" value="close" id="closeBtn" /></p>
		</div>
			
	
	</div>
</body>
</html>