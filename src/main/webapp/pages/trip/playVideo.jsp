<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript" src="/tripcaddie/javascript/js/flowplayer-3.2.11.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
 <link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var tripId;
	var videoId;
	function back(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	
		
	function addComment(videoid){
		if($('#textarea').val().length!=0 ){
			//alert('in comm');
		$.post("/tripcaddie/trip/addVideoComment.do",{	
			videoid : videoid,
			comment : $('#textarea').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+videoid;
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
		$.post("/tripcaddie/trip/deleteVideoComment.do",{
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
		
		$.post("/tripcaddie/trip/editVideoComment.do",{
			commentId : commentId,
			comment : $('#commentEdit').val()
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+${video.videoId};
			}else{
				
			}
		});
	}
	
function addtoMyfav(videoId){
		
		$.post("/tripcaddie/trip/AddFavVideo.do",{
			videoId : videoId,
			tripId:tripId
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+${video.videoId};
			}else{
				alert('error');
			}
		});
	}
	
function removefromMyfav(videoId){
	$.post("/tripcaddie/trip/RemoveFavVideo.do",{
		videoId : videoId,
		tripId:tripId
	},function(data){
		if(data=="success"){
			window.location.href="/tripcaddie/trip/getVideo.do?tripId="+tripId+'&videoId='+${video.videoId};
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
    	$.post("/tripcaddie/trip/addVideoRating.do",{
    	videoId : videoId,
		tripId:tripId,
		rating:this.value
	 },function(data){
	 });	
    	
     
      }
     });
  }
	
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		tripId=${trip.tripId};
		videoId=${video.videoId};
	</script>
	<input type="button" value="back" onclick="back();"/>
	<!-- Photo -->
	<div id="video">
		<p></p>
		
		<a  
			 href="/videos/${video.videoName} "
			 style="display:block;width:520px;height:330px"  
			 id="player"> 
		</a> 
	
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
			flowplayer("player", "/tripcaddie/swf/flowplayer-3.2.15.swf");
		</script>
		
		
		<p>Added <fmt:formatDate value="${video.createdDate.time }" type="date" dateStyle="long" /> | <c:out value="${video.noOfComments}" /> Comment(s). </p>
		
		<p>
		<c:choose><c:when test="${video.favvideo==true}"><a href="#" onclick="removefromMyfav(${video.videoId});">Remove From My Favorites </a>
        </c:when>
        <c:otherwise><a href="#" onclick="addtoMyfav(${video.videoId});">Add to My Favorites </a>
        </c:otherwise>
        </c:choose>
		</p>
		
		<!-- Rating should be added here -->
		
		<form id='form${video.videoId}'>
      <div class="Clear" onclick="submitform('form${video.videoId}');">
	        <c:choose>
				<c:when test="${video.avgRating==1}">
					<input class="star" type="radio" name="test1" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==2}">
					<input class="star" type="radio" name="test1" value="2" title="2/5"  checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="2" title="2/5"  />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==3}">
					<input class="star" type="radio" name="test1" value="3" title="3/5"  checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==4}">
					<input class="star" type="radio" name="test1" value="4" title="4/5" checked="checked"  />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="test1" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${video.avgRating==5}">
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
		<div id="comment">
		<h5>Comments:</h5>
		<c:forEach items="${videoComment}" var="videoComment">
		<div id="comment${videoComment.videoCommentId}">
			<p><img alt="Embedded Image" width='100px' height='70px' src=data:image/png;base64,${videoComment.tripMemberDto.tripCaddieUserDto.imageBase64} /></p>
			<p><c:out value="${videoComment.tripMemberDto.tripCaddieUserDto.firstName }"/> <c:out value="${videoComment.tripMemberDto.tripCaddieUserDto.lastName }"/> 
			<div id="commentspan${videoComment.videoCommentId}">
			<c:out value="${videoComment.comment }"/> 
			</div>
			</p>
			<p><fmt:formatDate value="${videoComment.lastUpdatedDate.time }" type="both" dateStyle="long" timeStyle="long"/>&nbsp;<a href="#" onclick="editComment(${videoComment.videoCommentId});">Edit</a>|<a href="#" onclick="deleteComment(${videoComment.videoCommentId})">Delete</a> </p>
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
		
		<input type="button" value="Comment"  onclick="addComment(${video.videoId});" >
		<p><br/></p>
		</div>
	</div>
	
</body>
</html>