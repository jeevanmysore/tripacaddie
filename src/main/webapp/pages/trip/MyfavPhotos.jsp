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
<script src='/tripcaddie/javascript/js/jquery.js' type="text/javascript" language="javascript"></script>
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
	
	function displayPhoto(pictureId ,tripId){
		window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
	}
	
	function submitform(id ,tripId){
		
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
	function trippicture(tripId){
		window.location.href="/tripcaddie/trip/getPictures.do?tripId="+tripId;
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
	<img alt='Embedded Image' width='70px' height='58px' src=data:image/png;base64,${trip.imagebase64} />
	</td>
	<td><h2><a href="#" style="font-weight: bold;" onclick="trippicture(${trip.tripId});">${trip.tripName }</a></h2>
	${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }
	<fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long" /> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/>
	</td>
	</tr>
	</table>
	<p>
		<c:choose><c:when test="${trip.galleriespresent==true}">
		
		
		<c:forEach var="picture" items="${trip.pictures}" varStatus="items">
			<a href="#" onclick="displayPhoto(${picture.pictureId} ,${trip.tripId});">
				<img alt="Embedded Image" width='150px' height='150px' src=data:image/png;base64,${picture.imageInBase64} />
			</a>
			<p>Uploaded by ${picture.tripMemberDto.tripCaddieUserDto.firstName} ${picture.tripMemberDto.tripCaddieUserDto.lastName}</p>
			<p><fmt:formatDate value="${picture.createdDate.time}" type="date" dateStyle="long" /> </p>
			<p>(${picture.noOfComments})Comments</p>
			<!-- Rating Should come here -->
			
			<form id='form${picture.pictureId}'>

			<div class="Clear" onclick="submitform('form${picture.pictureId}' ,${trip.tripId});">
			
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
		
		
        </c:when>
        <c:otherwise><p> no photos</p>
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