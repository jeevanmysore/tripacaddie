<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tee Times and Pairings | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	var tripId;
	function getTeeTimes(){
		$('#golfSchedule').hide();
		$('#teeTimes').show();
	}
	
	function getGolfSchedule(){
		$('#golfSchedule').show();
		$('#teeTimes').hide();
	}
	function editPairings(){
		window.location.href="/tripcaddie/trip/teeTime.do?tripId="+tripId;	
	}
	function initialize(){
		$('#golfSchedule').hide();
	}
	function printTeeTime(){	
		window.open('/tripcaddie/teeTimePdf.do?tripId='+tripId+'&format=view','_newtab');	
	}
	function saveTeeTime(){	
		window.location.href="/tripcaddie/teeTimePdf.do?tripId="+tripId+"&format=download";
	}
	function back(){
		window.history.back();
	}
	function getTripHome(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
</script>
</head>
<body onload="initialize()">
	<%@ include file="/pages/header2.jsp" %>
	<h1>Tee times and Pairings</h1>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	<div>
		<input type="button" value="Back" name="backBtn" id="backBtn" onclick="back()" />
		<input type="button" value="Return to Trip Home" name="tripHome" id="tripHomeId" onclick="getTripHome()"/>
	</div>
	<p>
		The TripCaddie Pairings Module is for managing your trips tee times at each golf course. After your tee times are made with the courses,
		your Trip Leader has special privileges to enter the tee times and make the pairings for the group. Remember,
		there must be a "Golf" type activity on your itinerary to use this module.
	</p>
	<p><img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} /></p>
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	<p><a href="#" onclick="printTeeTime();">Print</a> | <a href="#" onclick="saveTeeTime();">Save</a> </p>
	<p>
		<a href="#" onclick="getTeeTimes()">TEE TIMES</a> | <a href="#" onclick="getGolfSchedule()">GOLF SCHEDULE</a> 
	</p>
	<div id="teeTimes">
		<c:if test="${trip.tripLeader eq username }">
			<input type="button" value="Edit Pairings" id="editPair" name="editPair" onclick="editPairings()"/>
		</c:if>
		<!-- Tee times -->
		<c:forEach items="${activities }" var="activity" varStatus="activityList">
			<table id="table${activityList.count}">
					<thead>
						<tr>
							<td colspan="6">
								<a>
									Round ${activityList.count} - ${activity.activityName } 
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long" />
							</td>
						</tr>
						<tr>
							<th>Tee time</th>
							<th>Player 1</th>
							<th>Player 2</th>
							<th>Player 3</th>
							<th>Player 4</th>
							<th>Player 5</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${activity.teeTimeDtos }" var="teeTime">
						<tr>
							<td>
								<p><fmt:formatDate value="${teeTime.timings}" type="time" timeStyle="medium" pattern="hh:mm aa"/> </p>
							</td>
							<td>
								<c:if test="${not empty teeTime.player1}">
								<c:choose>
									<c:when test="${teeTime.player1.tripCaddieUserDto.userId eq 0 }">
										<c:out value="${teeTime.player1.invitedEmail }" />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${teeTime.player1.tripCaddieUserDto.userName eq username}">
												<a href="/tripcaddie/user/profile.do">${teeTime.player1.tripCaddieUserDto.firstName } ${teeTime.player1.tripCaddieUserDto.lastName }
												<c:choose>
													<c:when test="${empty teeTime.player1.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
													<c:otherwise>(teeTime.player1.tripCaddieUserDto.golfHandicap)</c:otherwise>
												</c:choose></a>
											</c:when>
											<c:otherwise>
												<a href="#" onclick="profilePopup();">${teeTime.player1.tripCaddieUserDto.firstName } ${teeTime.player1.tripCaddieUserDto.lastName }
												<c:choose>
													<c:when test="${empty teeTime.player1.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
													<c:otherwise>(teeTime.player1.tripCaddieUserDto.golfHandicap)</c:otherwise>
												</c:choose></a>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose></c:if>
							</td>
							<td>
								<c:if test="${not empty teeTime.player2}">
									<c:choose>
										<c:when test="${teeTime.player2.tripCaddieUserDto.userId eq 0 }">
											<c:out value="${teeTime.player2.invitedEmail }" />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${teeTime.player2.tripCaddieUserDto.userName eq username}">
													<a href="/tripcaddie/user/profile.do">${teeTime.player2.tripCaddieUserDto.firstName } ${teeTime.player2.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player2.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player2.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:when>
												<c:otherwise>
													<a href="#" onclick="profilePopup();">${teeTime.player2.tripCaddieUserDto.firstName } ${teeTime.player2.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player2.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player2.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose></c:if>
							</td>
							<td>
								<c:if test="${not empty teeTime.player3}">
										<c:choose>
											<c:when test="${teeTime.player3.tripCaddieUserDto.userId eq 0 }">
												<c:out value="${teeTime.player3.invitedEmail }" />
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${teeTime.player3.tripCaddieUserDto.userName eq username}">
														<a href="/tripcaddie/user/profile.do">${teeTime.player3.tripCaddieUserDto.firstName } ${teeTime.player3.tripCaddieUserDto.lastName }
														<c:choose>
															<c:when test="${empty teeTime.player3.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
															<c:otherwise>(teeTime.player3.tripCaddieUserDto.golfHandicap)</c:otherwise>
														</c:choose></a>
													</c:when>
													<c:otherwise>
														<a href="#" onclick="profilePopup();">${teeTime.player3.tripCaddieUserDto.firstName } ${teeTime.player3.tripCaddieUserDto.lastName }
														<c:choose>
															<c:when test="${empty teeTime.player3.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
															<c:otherwise>(teeTime.player3.tripCaddieUserDto.golfHandicap)</c:otherwise>
														</c:choose></a>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:if>
							</td>
							<td>
								<c:if test="${not empty teeTime.player4}">
									<c:choose>
										<c:when test="${teeTime.player4.tripCaddieUserDto.userId eq 0 }">
											<c:out value="${teeTime.player4.invitedEmail }" />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${teeTime.player4.tripCaddieUserDto.userName eq username}">
													<a href="/tripcaddie/user/profile.do">${teeTime.player4.tripCaddieUserDto.firstName } ${teeTime.player4.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player4.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player4.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:when>
												<c:otherwise>
													<a href="#" onclick="profilePopup();">${teeTime.player4.tripCaddieUserDto.firstName } ${teeTime.player4.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player4.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player4.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
							<td>
								<c:if test="${not empty teeTime.player5}">
									<c:choose>
										<c:when test="${teeTime.player5.tripCaddieUserDto.userId eq 0 }">
											<c:out value="${teeTime.player5.invitedEmail }" />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${teeTime.player5.tripCaddieUserDto.userName eq username}">
													<a href="/tripcaddie/user/profile.do">${teeTime.player5.tripCaddieUserDto.firstName } ${teeTime.player5.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player5.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player5.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:when>
												<c:otherwise>
													<a href="#" onclick="profilePopup();">${teeTime.player5.tripCaddieUserDto.firstName } ${teeTime.player5.tripCaddieUserDto.lastName }
													<c:choose>
														<c:when test="${empty teeTime.player5.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
														<c:otherwise>(teeTime.player5.tripCaddieUserDto.golfHandicap)</c:otherwise>
													</c:choose></a>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>	
		</c:forEach>
	</div>
	
	<div id="golfSchedule">
		<span><c:if test="${trip.tripLeader eq username && trip.startDate.time gt now}">
			<a href="/tripcaddie/addActivities.do?tripId=${trip.tripId }">Add Golf Activity</a>
		</c:if></span>
		<p><span style="font-weight: bold;">ROUND</span>&nbsp;<span style="font-weight: bold;">DATE</span>&nbsp;<span style="font-weight: bold;">GOLF COURSE</span></p>
		<c:forEach items="${activities }" var="activity" varStatus="item">
			<p>
				<span>Round <c:out value="${item.count}" /></span>
				<span><fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long"/> </span>
				<span><c:out value="${activity.activityName }"/></span>
			<p>
		</c:forEach>
	</div>
</body>
</html>