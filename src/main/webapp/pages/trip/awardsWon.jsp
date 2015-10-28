<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Awards | TripCaddie</title>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<style type="text/css">
		.form-required{
			color: red;
		}
		.ui-state-error{
		background-color: lime;
		}
	</style>
<script type="text/javascript">

function back(){
	window.history.back();
}
function displayTripHome(){
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}

$(function(){
	$('#EndPolldate').datepicker({
		autoSize:true,
		hideIfNoPrevNext:true,
		minDate:1,
		maxDate:16
	});
});


	var tripId;
	var sortType;
	
	function getAwards(awardId){
		window.location.href="/tripcaddie/trip/displayAward.do?tripId="+tripId+'&awardId='+awardId;
	}
	
	function deleteAwards(awardId){
		var op=confirm("Are you sure you want to delete?</br> This action cannot be undone.");
		if(op){
			$.post('/tripcaddie/trip/deleteAward.do',{
				awardId : awardId,
				tripId : tripId
			},function(data){
				if(data=="success"){
					window.location.href="/tripcaddie/trip/getAwardsWon.do?tripId="+tripId;
				}else{
					window.location.href="/tripcaddie/error.do";
				}
			});
		}
	}
	function getAwardsWon(){
		window.location.href="/tripcaddie/trip/getAwardsWon.do?tripId="+tripId;
	}
	function getAwardsPending(){
		window.location.href="/tripcaddie/trip/getAwardsPending.do?tripId="+tripId;
	}
	function tripvideo(id){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}

</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Awards</h3> <input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<p>There are two ways to create virtual trophies for anyone on your golf trip. You can create a trophy and immediately award it to someone, or you can create a trophy and allow anyone on your trip vote for the winner. Once a trophy is awarded, it will become part of your profile.</p>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	
	<!-- 
	 Trip Details....
	 -->
	<table style="width: 45%;">
	<tr>
	<td>
	<img alt='Embedded Image' width='180px' height='150px' src=data:image/png;base64,${trip.imagebase64} /></td>
	<td><h2><a href="#" style="font-weight: bold;" onclick="tripvideo(${trip.tripId});">${trip.tripName }</a></h2>
	${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }
	<fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long" /> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/>
	</td>
	</tr>
	</table>
	
	<input type="button" value="Create Award" onclick="createAward();" />
	<!-- Picture Page Body -->
	<p><a href="#" onclick="getAwardsWon();">Awards Won</a>|<a href="#" onclick="getAwardsPending();">Awards Pending</a></p>
	
	<div id="awardsWon">
	<c:forEach var="award" items="${awards}" varStatus="items">
	<p><img alt='Embedded Image' width='180px' height='180px' src=data:image/png;base64,${award.awardImgbas64} /></p>
	<p><a href="#" onclick="getAwards(${award.awardid});">${award.awardname }</a></p>
	<p>Winner :${award.nominee.membername} </p>
	Awarded :<fmt:formatDate value="${award.pollenddate }" type="date" dateStyle="long" />
	
	<c:choose>
				<c:when test="${award.commentcount==0}">
					<a href="#" onclick="getAwards(${award.awardid});">Be the first to comment</a>
				</c:when>
				<c:otherwise>
					comment(${award.commentcount})
				</c:otherwise>
			  </c:choose>
			  
 <a href="#" onclick="deleteAwards(${award.awardid});">Delete</a><br /><br />
 </c:forEach><br /><br />
	</div>
	<!-- -
	create awards popup
	 -->
	<div id="createAwardPopup" style="display: none;">
	<form id="createAwardForm" action="createAward.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="awardtypeId" id="awardtypeId">
	<input type="hidden" name="tripId" value="${trip.tripId}" id="hiddentripId">
	<p><label>Select Image :</label>
	<select id="getimageselected" name="selectImage" >
	<option value="-1">--select</option>
	<option value="Silver">Silver</option>
	<option value="Bronze">Bronze</option>
	<option value="Gold">Gold</option>
	</select>
	</p><br/>
	<p>
	<div id="img_selected">
	<div id="imgSilver" style="display: none;">
	<img alt="" src=data:image/png;base64,${newaward.slverimginbas64} />
	</div>
	
	<div id="imgBronze" style="display: none;">
	<img alt="" src=data:image/png;base64,${newaward.bronzimginbas64} />
	</div>
	
	<div id="imgGold" style="display: none;">
	<img alt="" src=data:image/png;base64,${newaward.goldimginbas64} />
	</div>
	</div>
	
	<p><label>Upload your own trophy image :</label></p> 
	<p>&nbsp;&nbsp;&nbsp;<input type="file" name="imageupload" />
	</p> <br/>
	<p>
	<label>Add a Title: </label>
	<span class="form-required">*</span>&nbsp;&nbsp;&nbsp;<input type="text" id="awardTitle" name="title"> <span id="awardTitleError" class="form-required"></span><br/>
	</p>
	
	<p><label><input type="radio" name="awardType" id="createpoll" checked="checked">Create Poll :</label> 
	<c:if test="${newaward.onlycreatepol }">
	<label>&nbsp;&nbsp;&nbsp;<input type="radio" id="awardnow" name="awardType" >Award Now :</label> 
	</c:if></p>
	 <br/>
	 <div id="endPolldateDiv">
	<p><label>End Poll On:</label><span class="form-required">*</span>
	&nbsp;&nbsp;&nbsp;<input type="text" id="EndPolldate" name="EndPolldate" readonly="readonly"/>
	<span id="EndPolldateError" class="form-required"></span></p>
	
	<p> <br/> </div>
	
	 <div id="awardNowDiv">
	<p><label>Award Now:</label><span class="form-required">*</span>
	&nbsp;&nbsp;&nbsp;<select id="tripmemberDiv" name="tripmember" >
	<option value="-1">--select</option>
	<c:forEach var="tripmember" items="${tripmembers}" varStatus="items">
		<option value="${tripmember.memberId}">${tripmember.membername}</option>
					
	</c:forEach>
	</select>
	<span id="awardNowDivError" class="form-required"></span></p>
	
	<p> <br/> </div>
	
	<input type="button" value="submit" onclick="submitForm();">
	<input type="button" value="cancel" onclick="cancelPopup();">
	</form>	
	</div>
	
	
	<script type="text/javascript">
	function createAward(){
	addPopupOptions("createAwardPopup");
	jQuery('#createAwardPopup').dialog('open');
	
	}
	function cancelPopup(){
		jQuery('#createAwardPopup')
		.dialog('close');
	}
	function submitForm(){
		if(validateForm()){
			
			$('#createAwardForm').submit();
		}
	}
	function validateForm(){
		var flag=true;
		var filter= /[^a-zA-Z 0-9]+/g;
		var awardTitle=$('#awardTitle').val();
		if(document.getElementById('createpoll').checked) {
			var EndPolldate=$('#EndPolldate').val();
			if(EndPolldate == null || EndPolldate == "" ){
				$('#EndPolldateError').text('');
				$('#EndPolldateError').text('Invalid date or Date is required');
				flag=false;
			}else{
				$('#awardtypeId').val('createpoll');
				$('#EndPolldateError').text('');
			}
			}
		else if(document.getElementById('awardnow').checked){
			var tripmember=$('#tripmemberDiv').val();
			if(tripmember == -1 ){
				$('#awardNowDivError').text('');
				$('#awardNowDivError').text('Award Now is required');
				flag=false;
			}else{
				$('#awardtypeId').val('awardnow');
				$('#awardNowDivError').text('');
			}
		}
		
		
		if(awardTitle == "" || awardTitle==null || filter.test(awardTitle) || awardTitle.trim()== ""){
		
			$('#awardTitleError').text('');
			$('#awardTitleError').text('invalid Title');
			flag=false;
		}else{
			
			$('#awardTitleError').text('');
		}
		
		return flag;
	}
	function addPopupOptions(divid){
		  jQuery('#'+divid).dialog(
			{
				
				resizable : false,
				autoOpen : false,
				height : 455,
				width : 550,
				open: function () {
                    $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
                }
			
							});
	}
	
	
	$('#createpoll').live("change",function(){
        if(document.getElementById('createpoll').checked) {
			
			$("#awardNowDiv").hide();
			$("#endPolldateDiv").show();
			}else if(document.getElementById('awardnow').checked) {
				
				$("#endPolldateDiv").hide();
				$("#awardNowDiv").show();
			}
	});
	
	$('#awardnow').live("change",function(){
     if(document.getElementById('createpoll').checked) {
			
			$("#awardNowDiv").hide();
			$("#endPolldateDiv").show();
			}else if(document.getElementById('awardnow').checked) {
				
				$("#endPolldateDiv").hide();
				$("#awardNowDiv").show();
			}
	});
	
	
	$(document).ready(function() {
		
		
		
		
		if(document.getElementById('createpoll').checked) {
			$("#awardNowDiv").hide();
			$("#endPolldateDiv").show();
			}else if(document.getElementById('awardnow').checked) {
				$("#endPolldateDiv").hide();
				$("#awardNowDiv").show();
			}
		
	    $("#getimageselected").change(function() {
	       
	        if($(this).val()=="Silver"){
	        	$("#img_selected").show();
	        	$("#imgSilver").show();
	        	$("#imgGold").hide();
	        	$("#imgBronze").hide();
	        }else if($(this).val()=="Bronze"){
	        	$("#img_selected").show();
	        	$("#imgBronze").show();
	        	$("#imgSilver").hide();
	        	$("#imgGold").hide();
	        }else if($(this).val()=="Gold"){
	        	$("#img_selected").show();
	        	$("#imgGold").show();
	        	$("#imgBronze").hide();
	        	$("#imgSilver").hide();
	        }else{
	        	$("#img_selected").hide();
	        }
	    });
	    
	    
	    
	});
	
	</script>
</body> 
</html>