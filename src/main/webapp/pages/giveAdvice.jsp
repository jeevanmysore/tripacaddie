<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Give Advice</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<style type="text/css">
		.popup{
   			 position: absolute;
    		 top: 27%;
			 left: 30%;
			 /*margin: -200px 0 0 -275px;*/
			 width: 550px;
			 background: #fff;
			 border: 3px solid #999999;
			 z-index: 999999
		}
</style>
<script type="text/javascript">

	var courseId;
	
	function editQuestion(questionId,courseId){
		$.post('editQuestion.do',{
			questionId : questionId,
			questionTxt : $('#editQuestiontxt').val()
		},function(data){
			if(data == "success"){
				window.location.href="/tripcaddie/giveAdvice.do?courseId="+courseId;
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
	
	function response(golfClubInquiryId){
		var responseTxt=$('#responsetxt-'+golfClubInquiryId).val();
		$.post("giveResponse.do",{
			inquiryId : golfClubInquiryId,
			responseText : responseTxt,
		},function(data){
			window.location.href="/tripcaddie/giveAdvice.do?courseId="+${course.golfCourseId};
		});
	}
	
	function askAdviceSubmit(){
		$.post("/tripcaddie/askAdvice.do",{
			courseId : ${course.golfCourseId},
			inquiry: $('#inquiry').val()
		},
		function(data){
			/* var divTag = document.createElement("div");
			var year=data.requestObject.jsonObject.createdDate.time.year+1900;
			divTag.setAttribute("id","advice");
			append='<p>'+data.requestObject.jsonObject.userDto.userName+' says:</p><p>'+data.requestObject.jsonObject.question+'</p>'
					+'<p> Posted on '+data.requestObject.jsonObject.createdDate.time.date+'/'+((data.requestObject.jsonObject.createdDate.time.month)+1)+'/'+year +' <a href="#" onclick="getRespondPopup('+data.requestObject.jsonObject.golfClubInquiryId+');">Respond</a></p>'+
					'('+ data.requestObject.jsonObject.noOfAdvices +' people responded)';
			divTag.innerHTML = append;
			document.getElementById("advices").appendChild(divTag); 
			jQuery('#askAdvicePopup').dialog('close');*/
			window.location.href="/tripcaddie/giveAdvice.do?courseId="+${course.golfCourseId};
		});
	}
	
	function displayAskAdvicePopup(){
		jQuery("#askAdvicePopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			height: 'auto',
			width: 500,
			top:-535,
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%'
		});
		jQuery('#askAdvicePopup').dialog('open');
		$('#close').click(function(){
			jQuery('#askAdvicePopup').dialog('close');
		});
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<br/><br/><br/><br/><br/>
	<p>My BucketList &gt; Give Advice</p>
	<p style="text-align: right;"><input type="button" value="Ask for Advice" onclick="displayAskAdvicePopup();"/></p>
	<table>
		<thead>
			<tr>
				<th>Date Asked</th>
				<th>Place/Question/Response</th>
				<th>Answers</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${inquiries}" var="inquiry" varStatus="inquiryList">
				<tr>
					<td><fmt:formatDate value="${inquiry.createdDate.time }" type="date" pattern="E" /><br/><fmt:formatDate value="${inquiry.createdDate.time }" pattern="dd/MM/yyyy"/> </td>
					<td>
						<a href="/tripcaddie/getClubDetails.do?courseId=${inquiry.golfCourseDto.golfCourseId}"><c:out value="${inquiry.golfCourseDto.courseName }" /> - <c:out value="${inquiry.golfCourseDto.addressDto.state }" />,<c:out value="${inquiry.golfCourseDto.addressDto.country }" /></a>
						
						<c:if test="${username eq  inquiry.createdBy}">
							<a href="#" onclick="editQuestionPopup(${inquiry.golfClubInquiryId},'${inquiry.question}',${inquiry.golfCourseDto.golfCourseId})">Edit</a> | <a href="#" onclick="removeQuestion(${inquiry.golfClubInquiryId})">Remove</a>
						</c:if>
							
						
						<br/><br/>
						<u><c:out value="${inquiry.userDto.firstName}" /> Asked:</u> <c:out value="${inquiry.question}" /><br/>
						<c:forEach items="${inquiry.adviceResponseDtos}" var="advice">
							<c:out value="${advice.userDto.firstName}" /> <c:out value="${advice.userDto.lastName}" /> says: <c:out value="${advice.answer}" /><br/>
						</c:forEach>
						<br/>
						<textarea rows="1" cols="5" id="responsetxt-${inquiry.golfClubInquiryId}" name="responsetxt"></textarea><br/>
						<input type="button" value="Submit" onclick="response(${inquiry.golfClubInquiryId});" />
					</td>
					<td>(${inquiry.noOfAdvices }) Answers</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- Pop up division tags -->
	<div id="askAdvicePopup" class="popup" style="display: none;">
		<form method="post" id="askAdvicePopup">
			<p>Submit your Inquiry for ${course.courseName } - ${course.addressDto.state },${course.addressDto.country }</p>
			<input type="text" name="inquiry" id="inquiry" />
			<input type="button" value="Submit Inquiry" onclick="askAdviceSubmit();"/>
		</form>
	</div>
	
	<!-- Popup -->
	<div id="editQuestionPopup" style="display: none;">
		<p>Edit Question:</p>
		<p><input type="text" value="" id="editQuestiontxt" /></p>
		<p><input type="button" value="Submit" id="editQuestion"/> <input type="button" value="Close" id="close" />  </p>
	</div>
</body>
</html>