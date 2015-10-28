<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Photo/Video Gallery | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>

<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
 <link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var tripId;
	var sortType;
	
	function back(){
		window.history.back();
	}
	
	function getPhotos(){
		window.location.href="/tripcaddie/user/fav/getFavPictures.do";
	}
	function getVideos(){
		window.location.href="/tripcaddie/user/fav/getFavVideos.do";
	}
	
	function displayvideo(videoid,tripId){
		window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+videoid;
	}
	function submitform(id,tripId ){
	    var inputs = $('#'+id+' :input');
	    inputs.each(function() {
	    if(this.checked) 
	     {
	    	//send ajax request
	    	$.post("/tripcaddie/trip/addVideoRating.do",{
	    	videoId : this.name,
			tripId:tripId,
			rating:this.value
		 },function(data){
		 });
	     
	      }
	     });
	  }
	function tripvideo(tripId){
		window.location.href="/tripcaddie/trip/getVideos.do?tripId="+tripId;
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	
	<h3>Favorites - All Trips</h3> <input type="button" value="Back" onclick="back();" />
	<p>In this Photo / Video Gallery, you will be able to view all of the Photos and Videos from any of the trips you belong to. Photos and Videos are sorted by the trip in which they were taken.</p>
	
	<c:set var="count" value="0"></c:set>

	<!-- Picture Page Body -->
	<p><a href="#" onclick="getPhotos();">Photos</a>|<a href="#" onclick="getVideos();">Videos</a></p>
	
	<div id="trips">
	<c:forEach var="trip" items="${trips}" varStatus="items">
	<div >
	<table style="width: 35%;">
	<tr>
	<td>
	<img alt='Embedded Image' width='70px' height='58px' src=data:image/png;base64,${trip.imagebase64} /></td>
	<td><h2><a href="#" style="font-weight: bold;" onclick="tripvideo(${trip.tripId});">${trip.tripName }</a></h2>
	${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }
	<fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long" /> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/>
	</td>
	</tr>
	</table>
	<p>
		<c:choose><c:when test="${trip.galleriespresent==true}">
		
		
		<c:forEach var="video" items="${trip.videos}" varStatus="items">
			<a href="#" onclick="displayvideo(${video.videoId} ,${trip.tripId});">
				<img alt="Embedded Image" width='150px' height='150px' src=data:image/png;base64,${video.tbimginbase64} />
			</a>
			<p>Uploaded by ${video.tripMemberDto.tripCaddieUserDto.firstName} ${video.tripMemberDto.tripCaddieUserDto.lastName}</p>
			<p><fmt:formatDate value="${video.createdDate.time}" type="date" dateStyle="long" /> </p>
			<p>(${video.noOfComments})Comments</p>
			<!-- Rating Should come here -->
				<form id='form${video.videoId}'>

			<div class="Clear" onclick="submitform('form${video.videoId}',${trip.tripId});">
				 <c:choose>
				<c:when test="${video.avgRating==1}">
					<input class="star" type="radio" name="${video.videoId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${video.videoId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==2}">
					<input class="star" type="radio" name="${video.videoId}" value="2" title="2/5"  checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${video.videoId}" value="2" title="2/5"  />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==3}">
					<input class="star" type="radio" name="${video.videoId}" value="3" title="3/5"  checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${video.videoId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==4}">
					<input class="star" type="radio" name="${video.videoId}" value="4" title="4/5" checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${video.videoId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==5}">
					<input class="star" type="radio" name="${video.videoId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${video.videoId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
			
			</div>
			<br />
		</form>
			<c:set var="count" value="${items.count}" />
		</c:forEach>
		
		
        </c:when>
        <c:otherwise><p> no videos</p>
        </c:otherwise>
        </c:choose>
		</p><br/><br/>
	<hr/>
	</div>
	<br/>
	</c:forEach>
	</div>
	
</body> 
</html>