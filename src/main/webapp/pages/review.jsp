<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Reviews</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript">
	
	$.mynamespace ={
			row : null,
			rowCount : 65536
	};
	var fRow;
	
	function editReview(reviewId,courseId){
		$.post('editReview.do',{
			reviewId : reviewId,
			reviewTxt : $('#editReviewtxt').val()
		},function(data){
			if(data == "success"){
				window.location.reload();
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
	function loadMore(){
		if($.mynamespace.row == null){
			fRow = 10
		}
		else{
			//alert($.mynamespace.row);
			fRow = $.mynamespace.row+10
		}
		var days=new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
		/* alert("frow:"+fRow);
		alert("rowCount:"+$.mynamespace.rowCount); */
		if(fRow < $.mynamespace.rowCount){
			//alert("In review");
			$.post("./ajax/myReviews.do",{
				firstRow : fRow
			},function(data){
				var i,append;
				var year;
				var day;
			
				$.mynamespace.row=data.requestObject.firstRow;
				$.mynamespace.rowCount=data.requestObject.rowCount;
			//alert($.mynamespace.row);
				for(i=0;i<data.requestObject.reviews.length;i++){
					year=data.requestObject.reviews[i].createdDate.time.year+1900;
					day=data.requestObject.reviews[i].createdDate.time.day;
					var divTag = document.createElement("div");
					divTag.setAttribute("id","myReview");
					append='<p>'+ days[day]+' '+data.requestObject.reviews[i].createdDate.time.date+'/'+(data.requestObject.reviews[i].createdDate.time.month)+'/'+year+' '
						+'<a href="/tripcaddie/getClubDetails.do?courseId='+data.requestObject.reviews[i].golfCourseDto.golfClubInquiryId+'">'+data.requestObject.reviews[i].golfCourseDto.courseName +'-'+ data.requestObject.reviews[i].golfCourseDto.addressDto.state +','+ data.requestObject.reviews[i].golfCourseDto.addressDto.country+' </a>'
						+data.requestObject.reviews[i].createdBy +' says:'
						+data.requestObject.reviews[i].review+' <a href="#" onclick="editReviewPopup('+data.requestObject.reviews[i].reviewId+',\''+data.requestObject.reviews[i].review+'\','+data.requestObject.reviews[i].golfCourseDto.golfClubInquiryId+')">Edit</a> | <a href="#" onclick="removeReview('+data.requestObject.reviews[i].reviewId+')">Remove</a> </p>';
					divTag.innerHTML = append;
					document.getElementById("myReviews").appendChild(divTag);
				}
			});
		}
	}
	$(window).scroll(function(){
		if(($(window).scrollTop()) == ($(document).height()) - ($(window).height())){
			loadMore();
		}
	});
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<div>
		<b>Date Asked&nbsp;&nbsp;Place/Review&nbsp;&nbsp;Like</b>
	</div>
	<div id="myReviews">
		
		<c:forEach var="data" items="${myReviews}" >
				<div id="myReview">
						<fmt:formatDate value="${data.createdDate.time}" pattern="EE"/><br/><fmt:formatDate value="${data.createdDate.time}" pattern="dd/MM/yyyy"/>&nbsp;
						<a href="/tripcaddie/getClubDetails.do?courseId=${data.golfCourseDto.golfCourseId}">${data.golfCourseDto.courseName}-${data.golfCourseDto.addressDto.state},${data.golfCourseDto.addressDto.country} </a> ${data.createdBy} says:${data.review}
						<a href="#" onclick="editReviewPopup(${data.reviewId},'${data.review}',${data.golfCourseDto.golfCourseId})">Edit</a> | <a href="#" onclick="removeReview(${data.reviewId})">Remove</a>
				</div> 
			
		</c:forEach>
	</div>
	<div>
		<input type="button" value="Load more" onclick="loadMore();" />
	</div>
	
	<!-- Popup -->
	<div id="editReviewPopup" style="display: none;">
		<p>Edit Review:</p>
		<p><input type="text" value="" id="editReviewtxt" /></p>
		<p><input type="button" value="Submit" id="editReview"/> <input type="button" value="Close" id="close" />  </p>
	</div>
</body>
</html>