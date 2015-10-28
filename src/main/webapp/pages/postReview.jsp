<!-- This page is for display all review of particular course  -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript">
	
	function editReview(reviewId,courseId){
		
		$.post('editReview.do',{
			reviewId : reviewId,
			reviewTxt : $('#editReviewtxt').val()
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/postReview.do?courseId="+courseId;
			}
			else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	
	function editReviewPopup(reviewId,reviewtxt,courseId){
		
		jQuery("#editReviewPopup").dialog({
			resizable: false,
			autoOpen: false,
			height:'auto',
			width: 500,
			top:-535,
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		        $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		    }
		});
		$('#editReviewtxt').val(reviewtxt);
		jQuery('#editReviewPopup').dialog('open');
		$('#editReview').click(function(){
			editReview(reviewId,courseId);
		});
		$('#close').click(function(){
			jQuery('#editReviewPopup').dialog('close');
		});
	}
	
	function removeReview(reviewId) {
		
		$.post("revomeReview.do",{
			reviewId : reviewId
		},function(data){
			
			if(data == "success"){
				window.location.reload();	
			}else{
				window.location.href="/tripcaddie/error.do";
			}
			
		});
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<br/><br/><br/><br/><br/>
	<p> My bucketList &gt; Review</p>
	
	<table>
		<thead>
			<tr>
				<th>Date Posted</th>
				<th>Place/Review</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reviews }" var="recommendation">
				<tr>
					<td>
						<fmt:formatDate value="${recommendation.createdDate.time }" type="date" pattern="E"/><br><fmt:formatDate value="${recommendation.createdDate.time}" type="date" pattern="dd/MM/yyyy"/>
					</td>
					<td>	
						<a href="/tripcaddie/getClubDetails.do?courseId=${recommendation.golfCourseDto.golfCourseId}"><c:out value="${recommendation.golfCourseDto.courseName }"/> - <c:out value="${recommendation.golfCourseDto.addressDto.state }" />,<c:out value="${recommendation.golfCourseDto.addressDto.country }" /></a>
						<c:if test="${ username eq recommendation.createdBy }">
							<a href="#" onclick="editReviewPopup(${recommendation.reviewId },'${recommendation.review }',${recommendation.golfCourseDto.golfCourseId });">Edit</a> | <a href="#" onclick="removeReview(${recommendation.reviewId })">Remove</a>
						</c:if>
						<br/><br/>
						<u> <c:out value="${recommendation.userDto.firstName}" /> <c:out value="${recommendation.userDto.lastName}" /> </u> says:<br/>
						<c:out value="${recommendation.review }"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- Popup -->
	<div id="editReviewPopup" style="display: none;">
		<p>Edit Review:</p>
		<p><input type="text" value="" id="editReviewtxt" /></p>
		<p><input type="button" value="Submit" id="editReview"/> <input type="button" value="Close" id="close" />  </p>
	</div>
</body>
</html>