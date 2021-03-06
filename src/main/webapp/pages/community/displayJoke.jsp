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
	var jokeId;
	
	
	function addComment(jokeId){
		if($('#textarea').val().length!=0 ){
			
		$.post("/tripcaddie/jokes/addJokeComment.do",{	
			jokeId : jokeId,
			comment : $('#textarea').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/jokes/getJoke.do?jokeId="+jokeId;
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
		}
	}
	
	
	function editComment(commentID){
		$('#editComment').hide();
		$('#addcomment').hide();
		$('#editComment').show();
		$('#comment'+commentID).hide();
		$('#commentEdit').val($('#commentspan'+commentID).text());
		commentId=commentID;
		}
	
	function deleteComment(commentID){
		$.post("/tripcaddie/jokes/deleteJokeComment.do",{
			commentId : commentID
		},function(data){
			if(data=="success"){
				$('#comment'+commentID).remove();
			}else{
				alert('error');
			}
		});
	}
	
	function cancelEdit(){
		
		$('#comment'+commentId).show();
		
		$('#addcomment').show();
		$('#editComment').hide();
		
		$('#commentEdit').val(""); 
		 commentId=""; 
		/* window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId; */
	}
	
	function updateComment(){
		
		$.post("/tripcaddie/jokes/editJokeComment.do",{
			commentId : commentId,
			comment : $('#commentEdit').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/jokes/getJoke.do?jokeId="+jokeId;
			}else{
				
			}
		});
	}
	
	function back(){
		window.history.back();
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
	function Delete(){
		var op=confirm("Are you sure you want to delete?</br> This action cannot be undone.");
		if(op){
			$.post('/tripcaddie/jokes/deleteJoke.do',{
				jokeId : jokeId
				
			},function(data){
				if(data=="success"){
					window.location.href="/tripcaddie/jokes/recent.do";
				}else{
					window.location.href="./error.do";
				}
			});
		}
	}
	function reportAbuse(){
		window.location.href="/tripcaddie/jokes/reportAbuse.do?jokeId="+jokeId;
	}
	function back(){
		window.location.href="/tripcaddie/jokes/recent.do";
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	
	<script type="text/javascript">
		
	jokeId=${joke.jokeId};
	</script>
		<input type="button" value="Back" onclick="back();" />
			<p>	${joke.jokeName}   <a href="#" onclick="Delete();">Delete</a>
			</p>
			<p>${joke.createdBy}  |<fmt:formatDate value="${joke.createdDate.time}" type="date" dateStyle="long" /> | (${joke.noOfComments})Comments</p>
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
		
		<br/><br/>
		<a href="#" onclick="reportAbuse();">Report Abuse</a>
		<br/><br/>
		<div id="comment">
		<h5>Comments:</h5>
		<c:forEach items="${comments}" var="comment">
		<div id="comment${comment.jokeCommentId}">
			<p><img alt="Embedded Image" width='100px' height='70px' src=data:image/png;base64,${comment.user.imageBase64} /></p>
			<p><c:out value="${comment.user.firstName }"/> <c:out value="${comment.user.lastName }"/> 
			<div id="commentspan${comment.jokeCommentId}">
			<c:out value="${comment.comments }"/> 
			</div>
			
			<p><fmt:formatDate value="${comment.lastUpdatedDate.time }" type="both" dateStyle="long" timeStyle="long"/>&nbsp;<a href="#" onclick="editComment(${comment.jokeCommentId});">Edit</a>|<a href="#" onclick="deleteComment(${comment.jokeCommentId})">Delete</a> </p>
			</div>
		</c:forEach>
		</div>
		<br/><br/>
		<div id="editComment" style="display: none;">
		<textarea id="commentEdit" rows="4" cols="50" ></textarea>
		<input type="button" value="update"  onclick="updateComment();" >
		<input type="button" value="cancel"  onclick="cancelEdit();" >
		</div>
		<p><br/></p>
		<p><br/></p>
		<div id="addcomment">
		<h5>Add Comment:</h5>
		<textarea id="textarea" rows="4" cols="50"></textarea>
		
		<input type="button" value="Comment"  onclick="addComment(${joke.jokeId});" >
		<p><br/></p>
		</div>
	</div>
	
</body> 
</html>