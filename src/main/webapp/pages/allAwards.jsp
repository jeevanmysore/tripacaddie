<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Awards | TripCaddie</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.js' type="text/javascript"
	language="javascript"></script>
<script type="text/javascript">
	var tripId;
	var sortType;
	
	function back(){
		window.history.back();
	}
	
	
	function getAwards(awardId ,tripId){
		window.location.href="/tripcaddie/trip/displayAward.do?tripId="+tripId+'&awardId='+awardId;
	}
	function tripLink(id){
		window.location.href="/tripcaddie/getTrip.do?tripId="+id;
	}
	
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp"%>

	<h3>Awards - All Trips</h3>
	<input type="button" value="Back" onclick="back();" />
	<p>In this Awards Gallery, you will be able to view all of the
		trophies from any trip you belong to. Trophies are sorted by the trip
		in which it was awarded. Don't forget, check any users profile to see
		all of the trophies they have won.</p>

	<c:set var="count" value="0"></c:set>

	<!-- Awards Page Body -->

	<div id="trips">
		<c:forEach var="trip" items="${trips}" varStatus="items">
			<div>
				<table style="width: 35%;">
					<tr>
						<td><img alt='Embedded Image' width='70px' height='58px'
							src=data:image/png;base64,${trip.imagebase64} /></td>
						<td><h2>
								<a href="#" style="font-weight: bold;"
									onclick="tripLink(${trip.tripId});">${trip.tripName }</a>
							</h2> ${trip.golfCourseDto.addressDto.city
							},${trip.golfCourseDto.addressDto.state
							},${trip.golfCourseDto.addressDto.country } <fmt:formatDate
								value="${trip.startDate.time }" type="date" dateStyle="long" />
							- <fmt:formatDate value="${trip.endDate.time }" type="date"
								dateStyle="long" /></td>
					</tr>
				</table>
				<p>
					<c:choose>
						<c:when test="${trip.galleriespresent==true}">


							<c:forEach var="award" items="${trip.awards}" varStatus="items">
								<p>
									<a href="#"
										onclick="getAwards(${award.awardid} ,${trip.tripId});"><img
										alt='Embedded Image' width='180px' height='180px'
										src=data:image/png;base64,${award.awardImgbas64} />
									</a>
								</p>
								<p>${award.awardname }</p>
								<p>Winner :${award.nominee.membername}</p>
	Awarded :<fmt:formatDate value="${award.pollenddate }" type="date"
									dateStyle="long" />

								<c:choose>
									<c:when test="${award.commentcount==0}">
										<a href="#" onclick="getAwards(${award.awardid} ,${trip.tripId});">Be the
											first to comment</a>
									</c:when>
									<c:otherwise>
					comment(${award.commentcount})
				</c:otherwise>
								</c:choose>
							</c:forEach>


						</c:when>
						<c:otherwise>
							<p>No Awards</p>
						</c:otherwise>
					</c:choose>
				</p>
				<hr>
			</div>
			<br />
		</c:forEach>
	</div>

</body>
</html>