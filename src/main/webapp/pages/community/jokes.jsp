<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jokes | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.js' type="text/javascript" language="javascript"></script>
<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
 <link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var tripId;
	var sortType;
	
	function back(){
		window.location.href="/tripcaddie/userHome.do";
	}
	
	
	
	function submitform(id){
		
        var inputs = $('#'+id+' :input');
        
        inputs.each(function() {
        if(this.checked) 
         {
        	//send ajax request
        	$.post("/tripcaddie/jokes/addJokeRating.do",{
        		jokeId : this.name,
			rating:this.value
		 },function(data){
		 });
        	
         
          }
         });
      }
	function createJoke(){
		window.location.href="/tripcaddie/jokes/AddJoke.do";
	}
	function getJoke(jokeId){
		window.location.href="/tripcaddie/jokes/getJoke.do?jokeId="+jokeId;
	}
	function sort(order){
		if(order=='rating'){
			window.location.href="/tripcaddie/jokes/recent.do?order="+order;
		}else{
			window.location.href="/tripcaddie/jokes/recent.do";
		}
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	
	<h3>Jokes</h3> <input type="button" value="Back" onclick="back();" /> <input type="button" value="Create Joke" onclick="createJoke();" />
	<p>Welcome to the Jokes Gallery! The Jokes Gallery is a collection of the best golf jokes and one-liners to take with you on your golf trip for a good laugh. The Joke Gallery is for all TripCaddie users to post new and comment on existing golf jokes and is for entertainment purposes only. TripCaddie is committed to maximizing your fun on your buddy golf trip.

DISCLAIMER: TripCaddie is not responsible for the content that others post and we do not tolerate any racial or sexist jokes. Any such jokes that are offensive will be removed immediately upon being reported to TripCaddie. If you have a concern about a joke, please report it immediately by clicking the "report" link.</p>
	<p align="right"><select id="SelectBox" onchange="sort(this.value);"><option value="date">recent</option><option value="rating">most rated</option></select></p>
		<c:forEach var="joke" items="${jokes}" varStatus="items">
			<p><a href="#" onclick="getJoke(${joke.jokeId});">
				${joke.jokeName}
			</a></p>
			<p>${joke.createdBy}  |<fmt:formatDate value="${joke.createdDate.time}" type="date" dateStyle="full" /> at <fmt:formatDate value="${joke.createdDate.time}" type="time" timeStyle="SHORT" /> | (${joke.noOfComments})Comments</p>
			 <!-- Rating Should come here -->
			
			<form id='form${joke.jokeId}'>

			<div class="Clear" onclick="submitform('form${joke.jokeId}');">
			
			<c:choose>
				<c:when test="${joke.avgRating==1}">
					<input class="star" type="radio" name="${joke.jokeId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${joke.jokeId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${joke.avgRating==2}">
					<input class="star" type="radio" name="${joke.jokeId}" value="2" title="2/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${joke.jokeId}" value="2" title="2/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${joke.avgRating==3}">
					<input class="star" type="radio" name="${joke.jokeId}" value="3" title="3/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${joke.jokeId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${joke.avgRating==4}">
				<input class="star" type="radio" name="${joke.jokeId}" value="4" title="4/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${joke.jokeId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${joke.avgRating==5}">
					<input class="star" type="radio" name="${joke.jokeId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${joke.jokeId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
			</div>
			<br />
		</form>
		
		<br/>
		<p>${joke.description }</p>
		
		</c:forEach>
	<script type="text/javascript">
	
	$(function() {
		<c:if test="${rating}">
		$('#SelectBox').val('rating');
		</c:if>
	});
	
	
	</script>
</body> 
</html>