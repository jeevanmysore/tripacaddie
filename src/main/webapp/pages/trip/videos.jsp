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
	function displayTripHome(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	function getPhotos(){
		window.location.href="/tripcaddie/trip/getPictures.do?tripId="+tripId;
	}
	function getVideos(){
		window.location.href="/tripcaddie/trip/getVideos.do?tripId="+tripId;
	}
	function addvideo(){
		window.location.href="/tripcaddie/trip/addvideo.do?tripId="+tripId;
	}
	function displayvideo(videoid){
		window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+videoid;
	}
	function submitform(id ){
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
	
	function sortVideos(order){
		window.location.href="/tripcaddie/trip/videosbysortorder.do?tripId="+tripId+"&sortorder="+order;
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Photo / Video Gallery</h3> <input type="button" value="Back" onclick="back();" />&nbsp;<input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<p>This is your private Photo and Video Gallery for this trip. Only the trip participants can upload and view photos and videos here.</p>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	<c:set var="count" value="0"></c:set>

	<!-- Video Page Body -->
	<p><a href="#" onclick="getPhotos();">Photos</a>|<a href="#" onclick="getVideos();">Videos</a></p>
	<p><a href="#" onclick="addvideo();">Add a video</a>&nbsp;Sort By:<a href="#" onclick="sortVideos('date');">Date</a>|<a href="#" onclick="sortVideos('rating');">Rating</a>|<a href="#" onclick="sortVideos('favourites');">My Favorites</a></p>
	<div id="videos">
		<c:forEach var="video" items="${videos}" varStatus="items">
			<a href="#" onclick="displayvideo(${video.videoId});">
				<img alt="Embedded Image" width='150px' height='150px' src=data:image/png;base64,${video.tbimginbase64} />
			</a>
			<p>Uploaded by ${video.tripMemberDto.tripCaddieUserDto.firstName} ${video.tripMemberDto.tripCaddieUserDto.lastName}</p>
			<p><fmt:formatDate value="${video.createdDate.time}" type="date" dateStyle="long" /> </p>
			<p>(${video.noOfComments})Comments</p>
			<!-- Rating Should come here -->
			<form id='form${video.videoId}'>

			<div class="Clear" onclick="submitform('form${video.videoId}');">
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
		<p><c:out value="${count}" /> Items</p>
	</div>
	
	
</body> 
</html>