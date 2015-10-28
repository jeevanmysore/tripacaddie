<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${discussion.title } | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
<link href='/tripcaddie/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
<style type="text/css">
	
	.popup{
		 position: absolute;
    	 top: 27%;
		 left: 30%;
		 width: 550px;
		 background: #fff;
		 border: 3px solid #999999;
		 z-index: 999999
	}
	
</style>
<script type="text/javascript">
	var tripId,discussionId;
	var commentID;
	function back(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=discussion";
	}
	
	function displayTripHome(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	function comment(){
		
		$.post("commentOnDiscussion.do",{
			discussionId : discussionId,
			comment : $('#comment').val()
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getDiscussion.do?discussionId="+discussionId;
			}else{
				window.location.href="error.do";
			}
		});
	}
	
	function editDiscussion(discussionId){
		window.location.href="/tripcaddie/editDiscussion.do?discussionId="+discussionId;
	}
	
	function deleteDiscussion(discussionId){
		$.post("deleteDiscussion.do",{
			discussionId : discussionId
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getTrip.do?tripId="+tripId+"&tab=discussion";
			}else{
				window.location.href="error.do";
			}
		});
	}
	
	function editComment(){
		
		$.post("editCommentOfDiscussion.do",{
			comment : $('#commentText').val(),
			commentID : commentID
		},function(data){
			if(data == "success"){
				jQuery('#editCommentPopup').dialog('close');
				window.location.href="/tripcaddie/getDiscussion.do?discussionId="+discussionId;
			}else{
				alert(data);
			}
		});
	}
	
	function editCommentPopup(commentId,spanComment){
		jQuery("#editCommentPopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			height: 'auto',
			width: 500,
			top:-430,
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%'
			/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		});
		commentID=commentId;
		
		$('#commentText').val($('#comment'+spanComment).text());
		jQuery('#editCommentPopup').dialog('open');
		$('#cancel').click(function(){
			jQuery('#editCommentPopup').dialog('close');
		});
	}
	
	function deleteComment(commentId){
		
		$.post("deleteCommentFromDiscussion.do",{
			commentId : commentId
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/getDiscussion.do?discussionId="+discussionId;
			}else{
				window.location.href="error.do";
			}
		});
		
	}
	
	function submitform(id){
        var inputs = $('#'+id+' :input');
        inputs.each(function() {
        	if(this.checked){
        		//send ajax request
        		$.post("/tripcaddie/ratingDiscussion.do",{
        			discussionId : this.name,
					tripId:tripId,
					rating:this.value
		 		},function(data){
		 			//alert(data);
		 		});
  			}
       });
   }
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		tripId=${discussion.tripDto.tripId};
		discussionId = ${discussion.discussionId};
	</script>
	<p> <input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" /></p>
	<p><span style="font-weight: bold;"><c:out value="${discussion.title }" /></span>
	<c:choose>
		<c:when test="${discussion.createdBy eq username  }">    <!-- Need to add one more condition -->
			<a href="#" onclick="editDiscussion(${discussion.discussionId})">Edit</a> | <a href="#" onclick="deleteDiscussion(${discussion.discussionId})">Delete</a> 
		</c:when>
		<c:otherwise>
			<a href="#" onclick="editDiscussion(${discussion.discussionId})">Edit</a> 
		</c:otherwise>
	</c:choose></p>
	<p>
		<c:out value="${discussion.tripMemberDto.tripCaddieUserDto.firstName }" /> <c:out value="${discussion.tripMemberDto.tripCaddieUserDto.lastName }" />
		<c:choose>
			<c:when test="${empty discussion.tripMemberDto.tripCaddieUserDto.golfHandicap }">(HCI?)</c:when>
			<c:otherwise>${discussion.tripMemberDto.tripCaddieUserDto.golfHandicap }</c:otherwise>
		</c:choose> |
		<fmt:formatDate value="${discussion.createdDate.time }" type="both" dateStyle="full" timeStyle="long" /> |
		
		<form id='form${discussion.discussionId}'>

			<div class="Clear" onclick="submitform('form${discussion.discussionId}');">
			
			<c:choose>
				<c:when test="${discussion.avgRating==1}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="1" title="1/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="1" title="1/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==2}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="2" title="2/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="2" title="2/5" />
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==3}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="3" title="3/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="3" title="3/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==4}">
				<input class="star" type="radio" name="${discussion.discussionId}" value="4" title="4/5" checked="checked" />
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="4" title="4/5" />
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${discussion.avgRating==5}">
					<input class="star" type="radio" name="${discussion.discussionId}" value="5" title="5/5" checked="checked"/>
				</c:when>
				<c:otherwise>
					<input class="star" type="radio" name="${discussion.discussionId}" value="5" title="5/5" />
				</c:otherwise>
			  </c:choose>
				
			</div>
			<br />
		</form>
		 | (<c:out value="${discussion.noOfComments }" />) Comments
	</p>
	<p>
		<c:out value="${discussion.description }" />
	</p>
	
	<c:forEach items="${discussion.commentDtos }" var="discussionComment" varStatus="commentList">   <!-- Need to get comment -->
		<p>
			<img alt="Embedded Image" width='50px' height='45px'
			 	src=data:image/png;base64,${discussionComment.tripMemberDto.tripCaddieUserDto.imageBase64} />
			<c:choose>
				<c:when test="${discussionComment.tripMemberDto.tripCaddieUserDto.userName eq username}">
					<a href="/tripcaddie/user/profile.do">${discussionComment.tripMemberDto.tripCaddieUserDto.firstName } ${discussionComment.tripMemberDto.tripCaddieUserDto.lastName }(
						<c:choose>
							<c:when test="${empty discussionComment.tripMemberDto.tripCaddieUserDto.golfHandicap}">
								<a href="/tripcaddie/user/profile/editProfile.do">HCI?)</a>
							</c:when>
							<c:otherwise>${discussionComment.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
						</c:choose>
					</a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="profilePopup();">${discussionComment.tripMemberDto.tripCaddieUserDto.firstName } ${discussionComment.tripMemberDto.tripCaddieUserDto.lastName }(
						<c:choose>
							<c:when test="${empty discussionComment.tripMemberDto.tripCaddieUserDto.golfHandicap}">HCI?)</c:when>
							<c:otherwise>${discussionComment.tripMemberDto.tripCaddieUserDto.golfHandicap})</c:otherwise>
						</c:choose>
					</a>
				</c:otherwise>
			</c:choose>
		</p>
		<p><span id="comment${commentList.count}"><c:out value="${discussionComment.comment }"></c:out></span></p>
		<p>
			<fmt:formatDate value="${discussionComment.createdDate.time }" type="both" dateStyle="full" timeStyle="medium" />
			<a href="#" onclick="editCommentPopup(${discussionComment.discussionCommentId},${commentList.count})">Edit</a> | <a href="#" onclick="deleteComment(${discussionComment.discussionCommentId})">delete</a>
		</p>
	</c:forEach>
	<textarea rows="5" cols="60" name="comment" id="comment"></textarea>
	<input type="button" value="Comment" onclick="comment()" />
	
	<!-- Popup -->
	<div id="editCommentPopup" class="popup" style="display: none;">
		<p>Edit comments here for discussion</p>
		<p><input type="text" name="comment" id="commentText" /></p>
		<p><input type="button" value="Save" name="saveBtn" id="save" onclick="editComment();">
		<input type="button" value="Cancel" id="cancel" name="cancelBtn"></p>
	</div>
</body>
</html>