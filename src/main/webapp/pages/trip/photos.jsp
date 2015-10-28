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
	function addPhoto(){
		window.location.href="/tripcaddie/trip/addPhoto.do?tripId="+tripId;
	}
	function displayPhoto(pictureId){
		window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
	}
	function submitform(id){
        var inputs = $('#'+id+' :input');
        inputs.each(function() {
        if(this.checked) 
         {
        	//send ajax request
        	$.post("/tripcaddie/trip/addPicRating.do",{
			pictureId : this.name,
			tripId:tripId,
			rating:this.value
		 },function(data){
		 });
        	
          
          }
         });
      }
	
	function sortPhotos(order){
		window.location.href="/tripcaddie/trip/picturesbysortorder.do?tripId="+tripId+"&sortorder="+order;
	}

</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Photo / Video Gallery</h3> <input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<p>This is your private Photo and Video Gallery for this trip. Only the trip participants can upload and view photos and videos here.</p>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	<c:set var="count" value="0"></c:set>
	
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image } />
		<span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span>
		<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	
	<!-- Picture Page Body -->
	<p><a href="#" onclick="getPhotos();">Photos</a>|<a href="#" onclick="getVideos();">Videos</a></p>
	<p><a href="#" onclick="addPhoto();">Add a Photo</a>&nbsp;Sort By:<a href="#" onclick="sortPhotos('date');">Date</a>|<a href="#" onclick="sortPhotos('rating');">Rating</a>|<a href="#" onclick="sortPhotos('favourites');">My Favorites</a></p>
	<div id="pictures">
		<c:forEach var="picture" items="${pictures}" varStatus="items">
			<a href="#" onclick="displayPhoto(${picture.pictureId});">
				<img alt="Embedded Image" width='150px' height='150px' src=data:image/png;base64,${picture.imageInBase64} />
			</a>
			<p>Uploaded by ${picture.tripMemberDto.tripCaddieUserDto.firstName} ${picture.tripMemberDto.tripCaddieUserDto.lastName}</p>
			<p><fmt:formatDate value="${picture.createdDate.time}" type="date" dateStyle="long" /> </p>
			<p>(${picture.noOfComments})Comments</p>
			<!-- Rating Should come here -->
			<form id='form${picture.pictureId}'>

			<div class="Clear" onclick="submitform('form${picture.pictureId}');">
			
			<c:choose>
				<c:when test="${picture.avgRating==1}">
					<input class="star" type="radio" name="${picture.pictureId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${picture.pictureId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==2}">
					<input class="star" type="radio" name="${picture.pictureId}" value="2" title="2/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${picture.pictureId}" value="2" title="2/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==3}">
					<input class="star" type="radio" name="${picture.pictureId}" value="3" title="3/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${picture.pictureId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${picture.avgRating==4}">
				<input class="star" type="radio" name="${picture.pictureId}" value="4" title="4/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${picture.pictureId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${picture.avgRating==5}">
					<input class="star" type="radio" name="${picture.pictureId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${picture.pictureId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
			</div>
			<br />
		</form>
			<c:set var="count" value="${items.count}" />
		</c:forEach>
		<p><c:out value="${count}" /> Items</p>
	</div>
	
	<div id="videos"></div>
</body> 
</html>