<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TripCaddie</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
 <link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
	
<script type="text/javascript">
	var tripId;
	var pictureId;
	var commentId;
	
	function back(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	function deletePhoto(pictureID){
		var op=confirm("Are you sure you want to delete?</br> This action cannot be undone.");
		if(op){
			$.post('./deletePicture.do',{
				pictureId : pictureID,
				tripId : tripId
			},function(data){
				if(data=="success"){
					window.location.href="/tripcaddie/trip/getPictures.do?tripId="+tripId;
				}else{
					window.location.href="./error.do";
				}
			});
		}
	}
	function addComment(pictureID){
		$.post("/tripcaddie/trip/addComment.do",{	
			pictureId : pictureID,
			comment : $('#comment').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
			}else{
				window.location.href="/tripcaddie/trip/error.do";
			}
		});
	}
	
	
	function editComment(commentID){
		
		$('#addComment').hide();
		$('#editComment').show();
		$('#comment'+commentID).hide();
		$('#commentEdit').val($('#commentSpan').text());
		
		commentId=commentID;
	}
	
	function deleteComment(commentID){
		$.post("/tripcaddie/trip/deleteComment.do",{
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
		
		/* $('#comment'+commentId).show();
		alert('#comment'+commentId);
		$('#addComment').show();
		$('#editComment').hide();
		
		$('#commentEdit').val(""); */
		/* commentId=""; */
		window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
	}
	
	function updateComment(){
		
		$.post("/tripcaddie/trip/editComment.do",{
			commentId : commentId,
			comment : $('#commentEdit').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
			}else{
				alert('error');
			}
		});
	}
	
	function editPhoto(){
		window.location.href="/tripcaddie/trip/editPicture.do?tripId="+tripId+'&pictureId='+pictureId;
	}
	
	function initialize(){
		$('#editComment').hide();
	}
	
	function addtoMyfav(pictureId){
		
		$.post("/tripcaddie/trip/AddFavPicture.do",{
			pictureId : pictureId,
			tripId:tripId
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
			}else{
				
			}
		});
	}
		
		
		function removefromMyfav(pictureId){
			$.post("/tripcaddie/trip/RemoveFavPicture.do",{
				pictureId : pictureId,
				tripId:tripId
			},function(data){
				if(data=="success"){
					window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
				}else{
					alert('error');
				}
			});
		}
	
			
      
        function submitform(id){
            var inputs = $('#'+id+' :input');
            inputs.each(function() {
            if(this.checked) 
             {
            	//send ajax request
            	$.post("/tripcaddie/trip/addPicRating.do",{
				pictureId : pictureId,
				tripId:tripId,
				rating:this.value
			 },function(data){
			 });
            	
              
              }
             });
          }

		
</script>
</head>
<body onload="initialize();">
	<%@ include file="/pages/header2.jsp"%>
	<script type="text/javascript">
		tripId=${trip.tripId};
		pictureId=${picture.pictureId};
	</script>
	<input type="button" value="back" onclick="back()" />

	<!-- Photo -->
	<div id="photo">
		<p></p>
		<p>
			<img alt="Embedded Image" width='500px' height='450px'
				src=data:image/png;base64,${picture.imageInBase64}>
		</p>
		<p>
			<a href="#" onclick="editPhoto();">Edit</a> | <a href="#"
				onclick="deletePhoto(${picture.pictureId})">Delete</a>
		</p>
		<h4>
			<c:out value="${picture.description }" />
		</h4>
		<p>
			Added
			<fmt:formatDate value="${picture.createdDate.time }" type="date"
				dateStyle="long" />
			|
			<c:out value="${picture.noOfComments}" />
			Comment(s).
		</p>
		<p>

			<c:choose>
				<c:when test="${picture.favpic==true}">
					<a href="#" onclick="removefromMyfav(${picture.pictureId});">Remove
						From My Favorites </a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="addtoMyfav(${picture.pictureId});">Add to
						My Favorites </a>
				</c:otherwise>
			</c:choose>
		</p>
		<!-- Rating should be added here -->
		<form id='form${picture.pictureId}'>

			<div class="Clear" onclick="submitform('form${picture.pictureId}');">
				<c:choose>
				<c:when test="${picture.avgRating==1}">
					<input class="star" type="radio" name="test1" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==2}">
					<input class="star" type="radio" name="test1" value="2" title="2/5"  checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="2" title="2/5"  />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==3}">
					<input class="star" type="radio" name="test1" value="3" title="3/5"  checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==4}">
					<input class="star" type="radio" name="test1" value="4" title="4/5" checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${picture.avgRating==5}">
					<input class="star" type="radio" name="test1" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
				
				
				
				
			</div>
			<br />
		</form>

		<!-- Comments -->
		<h5>Comments:</h5>
		<c:forEach items="${pictureComment}" var="pictureComment">
			<div id="comment${pictureComment.pictureCommentId}">
				<p>
					<img alt="Embedded Image" width='50px' height='45px'
						src=data:image/png;base64,${pictureComment.tripMemberDto.tripCaddieUserDto.imageBase64} />
				</p>
				<p>
					<c:out
						value="${pictureComment.tripMemberDto.tripCaddieUserDto.firstName }" />
					<c:out
						value="${pictureComment.tripMemberDto.tripCaddieUserDto.lastName }" />
					<span id="commentSpan"> <c:out
							value="${pictureComment.comments }" /> </span>
				</p>
				<p>
					<c:choose>
						<c:when test="${empty pictureComment.lastUpdatedDate }">
							<fmt:formatDate value="${pictureComment.createdDate.time }"
								type="both" dateStyle="long" timeStyle="medium" />
						</c:when>
						<c:otherwise>
							<fmt:formatDate value="${pictureComment.lastUpdatedDate.time }"
								type="both" dateStyle="long" timeStyle="medium" />
						</c:otherwise>
					</c:choose>
					&nbsp;<a href="#"
						onclick="editComment(${pictureComment.pictureCommentId});">Edit</a>|<a
						href="#"
						onclick="deleteComment(${pictureComment.pictureCommentId});">Delete</a>
				</p>
			</div>
		</c:forEach>
		<div id="addComment">
			Add a comment:
			<p>
				<input type="text" id="comment" />
			</p>
			<p>
				<input type="button" value="comment"
					onclick="addComment(${picture.pictureId})">
			</p>
		</div>
		<div id="editComment" style="display: none;">
			Edit a comment:
			<p>
				<input type="text" id="commentEdit" />
			</p>
			<p>
				<input type="button" value="Edit comment" onclick="updateComment()">
				<input type="button" value="Cancel" onclick="cancelEdit()" />
			</p>
		</div>
	</div>
</body>
</html>