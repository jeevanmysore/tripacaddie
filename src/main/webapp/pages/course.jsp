<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Golf Course</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css"
	type="text/css" />
<script src='/tripcaddie/javascript/js/jquery.rating.js'
	type="text/javascript" language="javascript"></script>
<link href='/tripcaddie/css/jquery.rating.css' type="text/css"
	rel="stylesheet" />
<style type="text/css">
.form-required {
	color: red;
}

.ui-state-error {
	background-color: lime;
}
</style>

<!-- For Edit and remove links START -->
<script type="text/javascript">
	
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
	
	function editQuestion(questionId,courseId){
		$.post('editQuestion.do',{
			questionId : questionId,
			questionTxt : $('#editQuestiontxt').val()
		},function(data){
			if(data == "success"){
				//window.location.href="/tripcaddie/giveAdvice.do?courseId="+courseId;
				window.location.reload();
			}
			else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
	
	function editQuestionPopup(questionId,questiontxt,courseId){
		
		jQuery("#editQuestionPopup").dialog({
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
		$('#editQuestiontxt').val(questiontxt);
		jQuery('#editQuestionPopup').dialog('open');
		$('#editQuestion').click(function(){
			editQuestion(questionId,courseId);
		});
		$('#close').click(function(){
			jQuery('#editQuestionPopup').dialog('close');
		});
	}
	
	function removeQuestion(golfClubInquiryId){
		
		$.post("removeQuestion.do",{
			questionId : golfClubInquiryId
		},function(data){
			if(data == "success"){
				window.location.reload();	
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
</script>
<!-- For Edit and remove links END -->
<script type="text/javascript">
	/* $.mynamespace ={
		courseId : null
		flag  : review
	};  */
	var inquiryid;
	var courseId;
	var loggedin;
	$(function() {
		var statusModel=${courseDetails.login};
		if(statusModel){
			loggedin=true;
		}else{
			loggedin=false;
		}
		 // Handler for .ready() called.
		 var popup=${courseDetails.popup};
		 if(popup){
			 getRespondPopup(${courseDetails.adviceId});
		 }
		 var AdvicePopup=${courseDetails.advicePopup};
		 var ReviewPopup=${courseDetails.reviewPopup};
		 var bucketlistPopup=${courseDetails.BucketlistPopup};
		 
		 if(AdvicePopup){
			 askAdvice();
		 }
         if(ReviewPopup){
        	 askReview();
		 }
         if(bucketlistPopup){
        	 <c:if test="${empty courseDetails.course.bucketListStatus}">
        	 addTolist();
        	 </c:if>
         }
		
		 
		});
	
	function addTolist(){
		
		if(loggedin){
			$('#loginContentforBucketlist').hide();
			$('#BucketlistContent').show();
		}else{
			$('#redirectionLoginBucketlist').attr('value',"/getClubDetails.do?courseId="+courseId+"&popup=Bucketlist");
			$('#loginContentforBucketlist').show();
			$('#BucketlistContent').hide();
		}
		
		jQuery("#bucklistOption").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height: 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		      }
				
			});
		jQuery('#bucklistOption').dialog('open');
		$('#close').click(function(){
			jQuery('#bucklistOption').dialog('close');
		});
	}
	
	function openPlaceshavebeenPopup(){
		jQuery('#bucklistOption').dialog('close');
		jQuery("#Placeshavebeen").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height: 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		      }
			});
		jQuery('#Placeshavebeen').dialog('open');
		$('#close').click(function(){
			jQuery('#Placeshavebeen').dialog('close');
		});
		
	}
	
	function openPlacesWanttogoPopup(){
		jQuery('#bucklistOption').dialog('close');
		jQuery("#PlacesWanttogo").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height: 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		      }
			});
		jQuery('#PlacesWanttogo').dialog('open');
		$('#close').click(function(){
			jQuery('#PlacesWanttogo').dialog('close');
		});
		
	}
	
	function validatehavebeenrankingBox(){
		var flag=true;
		var rankingval=$('#havebeenrankingBox').val();
		if(rankingval<=0 || rankingval>=200){
			flag=false;
			$('#ErrorhavebeenrankingBox').text('');
			$('#ErrorhavebeenrankingBox').text('ranking cannot be more than 200 and less than 0');
		}
		if(flag){
			addtoBucketList(1);
		}
	}
	
	function validateWanttogorankingBox(){
		var flag=true;
		var rankingval=$('#WanttogorankingBox').val();
		if(rankingval<=0 || rankingval>=20){
			flag=false;
			$('#ErrorWanttogorankingBox').text('');
			$('#ErrorWanttogorankingBox').text('ranking cannot be more than 20 or less than 0');
		}
		if(flag){
			addtoBucketList(2);
		}
	}
	
	function addtoBucketList(option){
		var ranking=0;
		if(option==1){
				ranking=$('#havebeenrankingBox').val();
				jQuery('#Placeshavebeen').dialog('close');
		}
		if(option==2){
			ranking=$('#WanttogorankingBox').val();
			jQuery('#PlacesWanttogo').dialog('close');
		}
			//alert(ranking);
		$.get("./addBucketList.do",{
			id : ${courseDetails.course.golfCourseId},
			played:option,
			ranking:ranking
		},
		function(data){
			if(data=="success"){
				$('#addLink').hide();
				/* $('#bucketList').text("Place is added"); */
				window.location.href="/tripcaddie/getClubDetails.do?courseId="+courseId;
				jQuery('#bucklistOption').dialog('close');
			}else if(data=="fail"){
				//$('#bucketList').text("Already in your bucket List");
				window.location.href="/tripcaddie/getClubDetails.do?courseId="+courseId;
				jQuery('#bucklistOption').dialog('close');
			}
		});
	}
	
	function updateLike(reviewId){
		$.post("./updateReviewLike.do",{
			reviewId: reviewId
		},
		function(data){
			//alert(data);
		});
	}
	
	function getReviews(){
		$('#advices').empty();
		$('#reviews').empty(); //for temporary or permanent
		$('#reviewBtn').show();
		$('#adviceBtn').hide();
		$.post("./getReviews.do",{
			courseId : ${courseDetails.course.golfCourseId}
		},
		function(data){
			var i;
			var append;
			for(i=0;i<data.requestObject.jsonObject.length;i++){
				var divTag = document.createElement("div");
				var year=data.requestObject.jsonObject[i].createdDate.time.year+1900;
				courseId=data.requestObject.jsonObject[i].golfCourseDto.golfCourseId;
				divTag.setAttribute("id","review"); 
				/* append='<p>'+data.requestObject.jsonObject[i].userDto.userName+' says:</p><p>'+data.requestObject.jsonObject[i].review+'</p>'
						+'<p>Posted on '+ data.requestObject.jsonObject[i].createdDate.time.date+'/'+((data.requestObject.jsonObject[i].createdDate.time.month)+1)+'/'+year+' <a href="#" onclick=updateLike('+data.requestObject.jsonObject[i].reviewId+')>Like</a>(<span id="likecount">'+data.requestObject.jsonObject[i].likeCount+'</span> likes.)</p>'; */
				if(data.requestObject.jsonObject[i].currentUser  == "true"){
					append='<p>'+data.requestObject.jsonObject[i].userDto.userName+' says:</p><p>'+data.requestObject.jsonObject[i].review+'</p>'
						+'<p>Posted on '+ data.requestObject.jsonObject[i].createdDate.time.date+'/'+((data.requestObject.jsonObject[i].createdDate.time.month)+1)+'/'+year+' <span> <a href="#" onclick="editReviewPopup('+data.requestObject.jsonObject[i].reviewId+',\''+data.requestObject.jsonObject[i].review+'\','+courseId+')">Edit</a> | <a href="#" onclick="removeReview('+data.requestObject.jsonObject[i].reviewId+');">Remove</a> </span></p>';
				}else{
					append='<p>'+data.requestObject.jsonObject[i].userDto.userName+' says:</p><p>'+data.requestObject.jsonObject[i].review+'</p>'
						+'<p>Posted on '+ data.requestObject.jsonObject[i].createdDate.time.date+'/'+((data.requestObject.jsonObject[i].createdDate.time.month)+1)+'/'+year+'</p>';
				}
				
				divTag.innerHTML = append;
				document.getElementById("reviews").appendChild(divTag);
			}
		});
	}
	
	function getAdvices(){
		$('#reviews').empty();
		$('#advices').empty(); //for temporary or permanent
		$('#reviewBtn').hide();
		$('#adviceBtn').show();
		$.post("./getAdvices.do",{
			courseId : ${courseDetails.course.golfCourseId}
		},
		function(data){
			var i;
			var append;
			for(i=0;i<data.requestObject.jsonObject.length;i++){
				var divTag = document.createElement("div");
				var year=data.requestObject.jsonObject[i].createdDate.time.year+1900;
				divTag.setAttribute("id","advice"); 
				
				if(data.requestObject.jsonObject[i].currentUser == "true"){
					append='<p>'+data.requestObject.jsonObject[i].userDto.userName+' asked:</p><p>'+data.requestObject.jsonObject[i].question+'</p>'
						+'<p> Posted on '+ data.requestObject.jsonObject[i].createdDate.time.date+'/'+((data.requestObject.jsonObject[i].createdDate.time.month)+1)+'/'+year+' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject[i].golfClubInquiryId+');">Respond</a></p>'
						+'('+ data.requestObject.jsonObject[i].noOfAdvices +' people responded)'
						+'<a href="#" onclick="editQuestionPopup('+data.requestObject.jsonObject[i].golfClubInquiryId+',\''+data.requestObject.jsonObject[i].question+'\','+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+')">Edit</a> | <a href="#" onclick="removeQuestion('+data.requestObject.jsonObject[i].golfClubInquiryId+')">Remove</a> ';
				}else{
					append='<p>'+data.requestObject.jsonObject[i].userDto.userName+' asked:</p><p>'+data.requestObject.jsonObject[i].question+'</p>'
						+'<p> Posted on '+ data.requestObject.jsonObject[i].createdDate.time.date+'/'+((data.requestObject.jsonObject[i].createdDate.time.month)+1)+'/'+year+' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject[i].golfClubInquiryId+');">Respond</a></p>'
						+'('+ data.requestObject.jsonObject[i].noOfAdvices +' people responded)';
				}
				
				divTag.innerHTML = append;
				document.getElementById("advices").appendChild(divTag);
			}
		});
	}
	
	function getRespondPopup(inquiryId){
		
		$.post('/tripcaddie/getAdvice.do',{
			adviceId : inquiryId
		},function(data){
			$('#adviceContent').empty();
			$('#adviceContent').append('<p>'+data.json.advice.userDto.firstName+'&nbsp;'+data.json.advice.userDto.lastName+' asked for advice about </p>' );
			$('#adviceContent').append('<p>'+data.json.advice.question+' ?</p>');
			if(data.json.login){
				$('#loginContent').hide();
				$('#respondContent').show();
			}else{
				$('#redirectionLogin').attr('value',"/getClubDetails.do?courseId="+courseId+"&adviceId="+inquiryId+"");
				$('#loginContent').show();
				$('#respondContent').hide();
			}
			$('#responseContent').empty();
			for(var i=0;i<data.json.advice.adviceResponseDtos.length;i++){
				$('#responseContent').append('<p>'+data.json.advice.adviceResponseDtos[i].userDto.firstName+'&nbsp;'+data.json.advice.adviceResponseDtos[i].userDto.lastName+' says</p>');
				$('#responseContent').append('<p>'+data.json.advice.adviceResponseDtos[i].answer+'</p>');
				$('#responseContent').append('<p>'+data.json.advice.adviceResponseDtos[i].days+' days ago</p>');
			}
			
		});
		
		
		
		jQuery("#respondPopup").dialog({
			
			resizable: false,
			autoOpen: false,
			width: 500,
			height: 'auto',
			top:-535,
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		      }
		});
		inquiryid=inquiryId;
		jQuery('#respondPopup').dialog('open');
		$('#close').click(function(){
			jQuery('#respondPopup').dialog('close');
		});
	}
	
	function askReview(){
		if(loggedin){
			$('#loginContentforReview').hide();
			$('#reviewContent').show();
		}else{
			$('#redirectionLoginReview').attr('value',"/getClubDetails.do?courseId="+courseId+"&popup=Review");
			$('#loginContentforReview').show();
			$('#reviewContent').hide();
		}
		
		
		jQuery("#askReviewPopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height: 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%',
			open: function () {
		          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
		      }
			});
		jQuery('#askReviewPopup').dialog('open');
		$('#close').click(function(){
			jQuery('#askReviewPopup').dialog('close');
		});
	}
	
	function askReviewSubmit(){
		$.post("./writeReview.do",{
			courseId : ${courseDetails.course.golfCourseId},
			review: $('#reviewtxt').val()
		},
		function(data){
			var divTag = document.createElement("div");
			var year=data.requestObject.jsonObject.createdDate.time.year+1900;
			
			courseId=data.requestObject.jsonObject.golfCourseDto.golfCourseId;
			divTag.setAttribute("id","review");
			/*append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.review+'</p>'
					+'<p>Posted on '+ data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year +' <a href="#" onclick=updateLike('+data.requestObject.jsonObject.reviewId+')>Like</a>(<span id="likecount">'+data.requestObject.jsonObject.likeCount+'</span> likes.)</p>';*/
			if(data.requestObject.jsonObject.currentUser  == "true"){
				append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.review+'</p>'
				+'<p>Posted on '+ data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year+' <span> <a href="#" onclick="editReviewPopup('+data.requestObject.jsonObject.reviewId+',\''+data.requestObject.jsonObject.review+'\','+courseId+')">Edit</a> | <a href="#" onclick="removeReview('+data.requestObject.jsonObject.reviewId+');">Remove</a> </span></p>';
			}else{
				append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.review+'</p>'
				+'<p>Posted on '+ data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year+'</p>';
			}
			
			divTag.innerHTML = append;
			document.getElementById("reviews").appendChild(divTag);
			jQuery('#askReviewPopup').dialog('close');
		});
	}
	
	function respondSubmit(){
		//alert("InquiryId "+inquiryid);
		$.post("/tripcaddie/giveResponse.do",{
			inquiryId : inquiryid,
			responseText : $('#responsetxt').val()
		},
		function(data){
			window.location.href="/tripcaddie/getClubDetails.do?courseId="+courseId;
		});
	}
	
	function askAdvice(){
		if(loggedin){
			$('#loginContentforAdvice').hide();
			$('#adviceContent').show();
		}else{
			$('#redirectionLoginAdvice').attr('value',"/getClubDetails.do?courseId="+courseId+"&popup=Advice");
			$('#loginContentforAdvice').show();
			$('#adviceContent').hide();
		}
		
		
		
		jQuery("#askAdvicePopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			height: 'auto',
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
		jQuery('#askAdvicePopup').dialog('open');
		$('#close').click(function(){
			jQuery('#askAdvicePopup').dialog('close');
		});
	}
	
	function askAdviceSubmit(){
		$.post("/tripcaddie/askAdvice.do",{
			courseId : ${courseDetails.course.golfCourseId},
			inquiry: $('#inquiry').val()
		},
		function(data){
			var divTag = document.createElement("div");
			var year=data.requestObject.jsonObject.createdDate.time.year+1900;
			divTag.setAttribute("id","advice");
			/* append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.question+'</p>'
					+'<p> Posted on '+data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year +' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject.golfClubInquiryId+');">Respond</a></p>'+
					'('+ data.requestObject.jsonObject.noOfAdvices +' people responded)'; */
			if(data.requestObject.jsonObject.currentUser  == "true"){
				append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.question+'</p>'
					+'<p> Posted on '+data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year +' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject.golfClubInquiryId+');">Respond</a></p>'+
					'('+ data.requestObject.jsonObject.noOfAdvices +' people responded)'
					+'<a href="#" onclick="editQuestionPopup('+data.requestObject.jsonObject.golfClubInquiryId+',\''+data.requestObject.jsonObject.question+'\','+data.requestObject.jsonObject.golfCourseDto.golfCourseId+')">Edit</a> | <a href="#" onclick="removeQuestion('+data.requestObject.jsonObject.golfClubInquiryId+')">Remove</a> ';
			}else{
				append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.question+'</p>'
				+'<p> Posted on '+data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year +' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject.golfClubInquiryId+');">Respond</a></p>'+
				'('+ data.requestObject.jsonObject.noOfAdvices +' people responded)';
			}
			
			divTag.innerHTML = append;
			document.getElementById("advices").appendChild(divTag);
			jQuery('#askAdvicePopup').dialog('close');
		});
	}
	
	function viewYahooImages(){
		
		var text=$('#golfCourseName').text();
		var keywords=text.replace(/ /g,"+");
		var url='http://images.search.yahoo.com/search/images;_ylt=A2KJkPyqIWRQAHsA0nOJzbkF?p='+keywords+'&fr=yfp-t-701&ei=utf-8&n=30&x=wrt&y=Search';
		
		$('#reviews').hide();
		$('#advices').hide();
		$('#linkToAdviceReview').hide();
		
		$('#iframe').removeAttr("src");
		$('#iframe').attr("src",url);
		$('#imageFrames').show();
	}
	
	function backToDestination(){
		
		$('#reviews').show();
		$('#advices').show();
		$('#linkToAdviceReview').show();
		$('#imageFrames').hide();
		$('#iframe').removeAttr("src");
		$('#iframe').attr("src","");
	}
	

	function onlyNumbers(evt)
	{
	       var e = event || evt; // for trans-browser compatibility
	       var charCode = e.which || e.keyCode;

	       if (charCode > 31 && (charCode < 48 || charCode > 57))
	               return false;

	       return true;
	}
	function moveTo(){
			
		$.post("/tripcaddie/categorize.do",{
			
			courseId : courseId 
			
		},function(data){
			
			if(data == 'success'){
				window.location.href="/tripcaddie/getClubDetails.do?courseId="+courseId;
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
		
	}
	
	function updateRatingFunction(id){
		var inputs = $('#'+id+' :input');
        inputs.each(function() {
        	if(this.checked){
        		//send ajax request
        		$.post("/tripcaddie/updateRating.do",{
        			courseId : this.name,
					rating:this.value
		 		},function(data){
		 			//alert(data);
		 		});
  			}
       });
	}
	
	function submitform(id) {
		
		<c:choose>
			<c:when test="${not empty sessionScope.username}">
				updateRatingFunction(id);
			</c:when>
		</c:choose>
		/* var inputs = $('#'+id+' :input');
        inputs.each(function() {
        	if(this.checked){
        		//send ajax request
        		$.post("/tripcaddie/updateRating.do",{
        			courseId : this.name,
					rating:this.value
		 		},function(data){
		 			//alert(data);
		 		});
  			}
       }); */
		
	}
</script>

<style type="text/css">
</style>
</head>
<body>
	<%-- <%@ include file="/pages/header.jsp" %> --%>
	<jsp:include page="/pages/header.jsp"></jsp:include>
	<c:choose>
		<c:when test="${not empty courseDetails.course.bucketListStatus}">
			<p>Already in your bucket List</p>
			<c:choose>
				<c:when test="${courseDetails.course.bucketListStatus == 'visited'}">
					<p>
						I have played here |<a href="#" onclick="moveTo();">move to...</a>
						I want to play here
					</p>
				</c:when>
				<c:otherwise>
					<p>
						<a href="#" onclick="moveTo();">move to...</a> I have played here
						| I want to play here
					</p>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<a href="javascript:void(0);" id="addLink" onclick="addTolist();">Add
				to BucketList</a>
			<span id="bucketList"></span>
		</c:otherwise>
	</c:choose>
	<p style="text-align: right;"><input type="button" value="Bucket List" onclick="displayBucketList();" /></p>
	<p style="text-align: right;"><input type="button" value="My trips" onclick="displayUserHome();"></p>
	<p style="text-align: right;">
		<input type="button" value="Create A Trip" onclick="createTrip();">
		<!-- <input type="button" value="BucketList" onclick="displayBucketList();"> -->
		<script type="text/javascript">
		//$.mynamespace.courseId = ${courseDetails.course.golfCourseId};
		courseId=${courseDetails.course.golfCourseId};
		//alert("courseId:"+$.mynamespace.courseId);
	</script>
	<p style="font-weight: bold;" id="golfCourseName">${courseDetails.course.courseName
		}</p>
	<p style="font-weight: bold;">${courseDetails.course.addressDto.city
		},${courseDetails.course.addressDto.state }</p>
	<p style="font-weight: bold;">${courseDetails.course.addressDto.country
		}</p>
	<p>${courseDetails.course.description}</p>

	<div id="courseRating" class="rating">
		<form id='form${courseDetails.course.golfCourseId}'>

			<div class="Clear"
				onclick="submitform('form${courseDetails.course.golfCourseId}');">

				<c:choose>
					<c:when test="${courseDetails.course.rating==1}">
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="1" title="1/5"
							checked="checked" />
					</c:when>
					<c:otherwise>
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="1" title="1/5" />
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${courseDetails.course.rating==2}">
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="2" title="2/5"
							checked="checked" />
					</c:when>
					<c:otherwise>
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="2" title="2/5" />
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${courseDetails.course.rating==3}">
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="3" title="3/5"
							checked="checked" />
					</c:when>
					<c:otherwise>
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="3" title="3/5" />
					</c:otherwise>
				</c:choose>


				<c:choose>
					<c:when test="${courseDetails.course.rating==4}">
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="4" title="4/5"
							checked="checked" />
					</c:when>
					<c:otherwise>
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="4" title="4/5" />
					</c:otherwise>
				</c:choose>


				<c:choose>
					<c:when test="${courseDetails.course.rating==5}">
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="5" title="5/5"
							checked="checked" />
					</c:when>
					<c:otherwise>
						<input class="star" type="radio"
							name="${courseDetails.course.golfCourseId}" value="5" title="5/5" />
					</c:otherwise>
				</c:choose>

			</div>
			<br />
		</form>
	</div>
	<p>
		<a href="#" onclick="viewYahooImages();">view yahoo images</a>
	</p>

	<div id="linkToAdviceReview">
		<a id="reviewLink" href="javascript:void(0);" onclick="getReviews();">Reviews</a>&nbsp;<a
			id="adviceLink" href="javascript:void(0);" onclick="getAdvices()">Advice</a>
		<input type="button" id="adviceBtn" onclick="askAdvice();"
			value="Ask for Advice" style="display: none" /> <input type="button"
			id="reviewBtn" onclick="askReview();" value="review" />
	</div>
	<div id="reviews">
		<c:forEach var="data" items="${courseDetails.review}">
			<div id="review">
				<p>${data.userDto.userName } says:</p>
				<p>${data.review}</p>
				<p>
					Posted on
					<fmt:formatDate value="${data.createdDate.time}"
						pattern="dd/MM/yyyy" />
					<%-- &nbsp;<a href="#" onclick='updateLike(${data.reviewId})'>Like</a>(<span
						id="likecount">${data.likeCount}</span> likes) --%>
					<c:if test="${username eq data.userDto.userName }">
						&nbsp;<a href="#" onclick="editReviewPopup(${data.reviewId},'${data.review}',${data.golfCourseDto.golfCourseId})">Edit</a> | <a href="#" onclick="removeReview(${data.reviewId});">Remove</a>
					</c:if>
				</p>
			</div>
		</c:forEach>
	</div>

	<div id="advices">
		<div id="advice"></div>
	</div>

	<div id="imageFrames"
		style="display: none; width: 1000px; height: 500px; margin: 0 auto;">
		<p style="text-align: right;">
			<a href="#" onclick="backToDestination();">Back to Destination</a>
		</p>
		<iframe id="iframe" src="" width="100%" height="100%">
			<p>Browser doesn't support iFrame</p>
		</iframe>
	</div>
	<!-- Pop up division tags -->
	<div id="askAdvicePopup" style="display: none;">

		<div id="loginContentforAdvice">
			<p>Login to ask advice :</p>
			<form method="POST"
				action="<c:url value="/j_spring_security_check" />">
				<p>
					<label>Username</label> <input type="text" name="j_username" />
				</p>
				<p>
					<label>Password</label> <input type="password" name="j_password" />
				</p>
				<p>
					<input type="submit" value="Login" />
				</p>
				<input id="redirectionLoginAdvice" type="hidden"
					name="spring-security-redirect" />
				<p>
					<a href="#" id="forgetPasswordinPopup">Forget Password</a>
				</p>
				<p>
					Not a member?<a href="/tripcaddie/registration.do">Register</a>
				</p>

			</form>

		</div>
		<div id="adviceContent">
			<p>Ask advice :</p>
			<form method="post" id="askAdvicePopup">
				<input type="text" name="inquiry" id="inquiry" /> <input
					type="button" value="ask Advice" onclick="askAdviceSubmit()" />
			</form>
		</div>
	</div>

	<div id="askReviewPopup" style="display: none;">

		<div id="loginContentforReview">
			<p>Login to add Review :</p>
			<form method="POST"
				action="<c:url value="/j_spring_security_check" />">
				<p>
					<label>Username</label> <input type="text" name="j_username" />
				</p>
				<p>
					<label>Password</label> <input type="password" name="j_password" />
				</p>
				<p>
					<input type="submit" value="Login" />
				</p>
				<input id="redirectionLoginReview" type="hidden"
					name="spring-security-redirect" />
				<p>
					<a href="#" id="forgetPasswordinPopup">Forget Password</a>
				</p>
				<p>
					Not a member?<a href="/tripcaddie/registration.do">Register</a>
				</p>

			</form>

		</div>
		<div id="reviewContent">
			<p>Give review :</p>
			<form method="post" id="askReviewPopup">
				<input type="text" name="reviewtxt" id="reviewtxt" /> <input
					type="button" value="Give Review" onclick="askReviewSubmit()" />
			</form>
		</div>
	</div>

	<div id="respondPopup" style="display: none">

		<div id="adviceContent"></div>

		<div id="loginContent">
			<p>Login to Respond :</p>
			<form method="POST"
				action="<c:url value="/j_spring_security_check" />">
				<p>
					<label>Username</label> <input type="text" name="j_username" />
				</p>
				<p>
					<label>Password</label> <input type="password" name="j_password" />
				</p>
				<p>
					<input type="submit" value="Login" />
				</p>
				<input id="redirectionLogin" type="hidden"
					name="spring-security-redirect" />
				<p>
					<a href="#" id="forgetPasswordinPopup">Forget Password</a>
				</p>
				<p>
					Not a member?<a href="/tripcaddie/registration.do">Register</a>
				</p>

			</form>

		</div>
		<div id="respondContent">
			<form method="post" id="askRespondPopup">
				<input type="text" name="responsetxt" id="responsetxt" /> <input
					type="button" value="Submit Response" onclick="respondSubmit(5)" />
			</form>
		</div>
		<div id="responseContent"></div>
	</div>

	<div id="bucklistOption" style="display: none;">

		<div id="loginContentforBucketlist">
			<p>Login to Add in BucketList :</p>
			<form method="POST"
				action="<c:url value="/j_spring_security_check" />">
				<p>
					<label>Username</label> <input type="text" name="j_username" />
				</p>
				<p>
					<label>Password</label> <input type="password" name="j_password" />
				</p>
				<p>
					<input type="submit" value="Login" />
				</p>
				<input id="redirectionLoginBucketlist" type="hidden"
					name="spring-security-redirect" />
				<p>
					<a href="#" id="forgetPasswordinPopup">Forget Password</a>
				</p>
				<p>
					Not a member?<a href="/tripcaddie/registration.do">Register</a>
				</p>

			</form>
		</div>
		<div id="BucketlistContent">
			<p>
				<a href="#" onclick="openPlaceshavebeenPopup();">Places I have
					been</a>
			</p>
			<p>
				<a href="#" onclick="openPlacesWanttogoPopup();">Places I Want
					to go</a>
			</p>
		</div>
	</div>




	<div id="Placeshavebeen" style="display: none;">
		<p>
			<label>Enter value to ranking /priority</label>
		</p>
		<input type="text" id="havebeenrankingBox"
			onkeypress="return onlyNumbers();"><span
			class="form-required" id="ErrorhavebeenrankingBox"></span> <input
			type="button" value="submit" onclick="validatehavebeenrankingBox();">
		<p>
			<a href="#" onclick="addtoBucketList(1);"> Skip </a> (Skip to add at top)
		</p>

	</div>

	<div id="PlacesWanttogo" style="display: none;">
		<p>
			<label>Enter value to ranking /priority</label>
		</p>
		<input type="text" id="WanttogorankingBox"
			onkeypress="return onlyNumbers();"><span
			class="form-required" id="ErrorWanttogorankingBox"></span> <input
			type="button" value="submit" onclick="validateWanttogorankingBox();">
		<p>
			<a href="#" onclick="addtoBucketList(2);"> Skip </a>
		</p>
	</div>
	
	<div id="editReviewPopup" style="display: none;">
		<p>Edit Review:</p>
		<p><input type="text" value="" id="editReviewtxt" /></p>
		<p><input type="button" value="Submit" id="editReview"/> <input type="button" value="Close" id="close" />  </p>
	</div>
	
	<div id="editQuestionPopup" style="display: none;">
		<p>Edit Question:</p>
		<p><input type="text" value="" id="editQuestiontxt" /></p>
		<p><input type="button" value="Submit" id="editQuestion"/> <input type="button" value="Close" id="close" />  </p>
	</div>
</body>
</html>