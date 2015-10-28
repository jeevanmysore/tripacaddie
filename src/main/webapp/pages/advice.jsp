<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My advice Requests</title>
<style type="text/css">
	/* p{
		text-decoration: underline;
		color: orange;
		font-size: 11px;
	} */
</style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript">
	$.mynamespace ={
		row : null,
		rowCount : 65536
	};
	var fRow;
	var days=new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
	
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
	
	function loadMore(){
		if($.mynamespace.row == null){
			fRow = 10;
		}
		else{
			fRow = $.mynamespace.row+10;
		}
		if(fRow < $.mynamespace.rowCount){
			$.post("./ajax/myInquiries.do",{
				firstRow : fRow			
			},function(data){
				//alert(data);
				var i,j,append,year,day;
				
				$.mynamespace.row=data.requestObject.firstRow;
				$.mynamespace.rowCount=data.requestObject.rowCount;
				//alert(data.requestObject.inquries.length);
				for(i=0;i<data.requestObject.inquries.length;i++){
					year=data.requestObject.inquries[i].createdDate.time.year+1900;
					day=data.requestObject.inquries[i].createdDate.time.day;
					var trTag=document.createElement("tr");
					//divTag.setAttribute("id","myAdvice");
					append='<td class="1">'+days[day]+'<br/> '+data.requestObject.inquries[i].createdDate.time.date+'/'+(data.requestObject.inquries[i].createdDate.time.month)+'/'+year+'<br/></td><td class="2">'
					+'<a href="/tripcaddie/getClubDetails.do?courseId='+data.requestObject.inquries[i].golfCourseDto.golfCourseId+'">'+data.requestObject.inquries[i].golfCourseDto.courseName+'-'+data.requestObject.inquries[i].golfCourseDto.addressDto.state+','
					+data.requestObject.inquries[i].golfCourseDto.addressDto.country+'</a><a  href="#" onclick="editQuestionPopup('+data.requestObject.inquries[i].golfClubInquiryId+',\''+data.requestObject.inquries[i].question+'\','+data.requestObject.inquries[i].golfCourseDto.golfCourseId+')">Edit</a>|<a href="#" onclick="removeQuestion('+data.requestObject.inquries[i].golfClubInquiryId+');" >Remove</a><br/> I Asked: '
					+data.requestObject.inquries[i].question;
					for(j=0;j<data.requestObject.inquries[i].adviceResponseDtos.length;j++){
						append+='<br />'+data.requestObject.inquries[i].adviceResponseDtos[j].userDto.firstName+' '+data.requestObject.inquries[i].adviceResponseDtos[j].userDto.lastName+' says:'
							+data.requestObject.inquries[i].adviceResponseDtos[j].answer;
					} 
					append+='</td><td>('+data.requestObject.inquries[i].noOfAdvices+') Answers</td>';
					trTag.innerHTML = append;
					//alert(append);
					//document.getElementById("advice").appendChild(trTag);
					$('#advice').append(trTag);
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
		<b>Date Asked&nbsp;&nbsp;Place/Inquiry&nbsp;&nbsp;Like</b>
	</div>
	<div id="myAdvices">
		<c:forEach var="data" items="${myInquiries}">
			<!-- <div id="myAdvice"> -->
			<table id="advice">
				<tr>
					<td><fmt:formatDate value="${data.createdDate.time}" pattern="E"/><br/><fmt:formatDate value="${data.createdDate.time}" pattern="dd/MM/yyyy"/></td>	
					<td>
						<a href="/tripcaddie/getClubDetails.do?courseId=${data.golfCourseDto.golfCourseId}">${data.golfCourseDto.courseName}-${data.golfCourseDto.addressDto.state},${data.golfCourseDto.addressDto.country}</a>
						<a href="#" onclick="editQuestionPopup(${data.golfClubInquiryId},'${data.question}',${data.golfCourseDto.golfCourseId})">Edit</a> | <a href="#" onclick="removeQuestion(${data.golfClubInquiryId})">Remove</a>
						<br/>
						I Asked:${data.question}
						<c:forEach items="${data.adviceResponseDtos }" var="response">
							<br/>
							${response.userDto.firstName } ${response.userDto.lastName } says : <c:out value="${response.answer}" />									
						</c:forEach>
					</td>	
					<!-- Response count -->
					<td>(${data.noOfAdvices }) Answers</td>
				</tr>
			</table>
			<!-- </div>		 -->
		</c:forEach>
	</div>
	<div>
		<input type="button" value="Load more" onclick="loadMore();" />
	</div>
	
	<!-- Popup -->
	<div id="editQuestionPopup" style="display: none;">
		<p>Edit Question:</p>
		<p><input type="text" value="" id="editQuestiontxt" /></p>
		<p><input type="button" value="Submit" id="editQuestion"/> <input type="button" value="Close" id="close" />  </p>
	</div>
</body>
</html>