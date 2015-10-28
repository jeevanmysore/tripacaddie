<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Games | TripCaddie</title>
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
        	$.post("/tripcaddie/games/addGameRating.do",{
        	gameId : this.name,
			rating:this.value
		 },function(data){
		 });
        	
         
          }
         });
      }
	function createGame(){
		window.location.href="/tripcaddie/games/AddGame.do";
	}
	function getGame(gameId){
		window.location.href="/tripcaddie/games/getGame.do?gameId="+gameId;
	}
	function sort(order){
		if(order=='rating'){
			window.location.href="/tripcaddie/games/recent.do?order="+order;
		}else{
			window.location.href="/tripcaddie/games/recent.do";
		}
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	
	<h3>Games</h3> <input type="button" value="Back" onclick="back();" /> <input type="button" value="Create Game" onclick="createGame();" />
	<p>Welcome to the Games Gallery! The Games Gallery is a collection of formats for groups to play on the golf course. All TripCaddie users are invited to pick any game from the gallery to use on a trip and are welcome to post new and comment on existing games in the gallery. TripCaddie is committed to maximizing your fun on your buddy golf trip.</p>
	<p align="right"><select id="SelectBox" onchange="sort(this.value);"><option value="date">recent</option><option value="rating">most rated</option></select></p>
		<c:forEach var="game" items="${games}" varStatus="items">
			<p><a href="#" onclick="getGame(${game.gameId});">
				${game.gameName}
			</a></p>
			<p>${game.createdBy}  |<fmt:formatDate value="${game.createdDate.time}" type="date" dateStyle="full" /> at <fmt:formatDate value="${game.createdDate.time}" type="time" timeStyle="SHORT" /> | (${game.noOfComments})Comments</p>
			 <!-- Rating Should come here -->
			
			<form id='form${game.gameId}'>

			<div class="Clear" onclick="submitform('form${game.gameId}');">
			
			<c:choose>
				<c:when test="${game.avgRating==1}">
					<input class="star" type="radio" name="${game.gameId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${game.gameId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${game.avgRating==2}">
					<input class="star" type="radio" name="${game.gameId}" value="2" title="2/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${game.gameId}" value="2" title="2/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${game.avgRating==3}">
					<input class="star" type="radio" name="${game.gameId}" value="3" title="3/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${game.gameId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${game.avgRating==4}">
				<input class="star" type="radio" name="${game.gameId}" value="4" title="4/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${game.gameId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${game.avgRating==5}">
					<input class="star" type="radio" name="${game.gameId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${game.gameId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
			</div>
			<br />
		</form>
		
		<br/>
		<p>${game.description }</p>
		
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