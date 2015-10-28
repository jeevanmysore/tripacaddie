<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit a Trip</title>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
	<script type="text/javascript" src="javascript/js/createTrip.js"></script>	
	<script type="text/javascript" src="javascript/js/ajaxfileupload.js"></script>	
	
	<style type="text/css">
		.form-required{
			color: red;
		}
	</style>
	
	<script type="text/javascript">
		var tripId; 
		function remove()
		{
			$("#img").remove();
			$("#rmvImg").remove();
			$("#imgLoad").show();
			$("#removeImg").val('removed');
			
		}
	
		 function hideImg()
		{
			 $("#imgLoad").hide();
			<c:if test="${tripMap.noimage}">
				$("#rmvImg").remove();
				$("#imgLoad").show();
			</c:if>
			
		} 
	 
 	 $(document).ready(function()
	 {
 		
			$("#tripName").change(function()
			{		
				 if($('#tripName').val()!='${tripMap.trip.tripName}'){
				$.post("/tripcaddie/tripExistance.do",{
	    			tripName : $('#tripName').val()
	    			
	    		},
	    		function(data){
	    			if(data=="success"){
	    				$('#tripnameError').text("");
	    				$('#tripnameError').text("Trip Name already exists");	    					
	    			}
	    			else{
	    				$('#tripnameError').text("");
	    			}
				});
				
				 }
				
			});		
 		
			
	 });
	  

/* 	 function validateImage()
	 {
	 	var img=$('#image').val();
	 	
	 	alert(img);
	 		$.ajax({
	 	      type: "POST",
	 	      url: "./validateImage.do",
	 	      encType: 'multipart/form-data',
	 	      data: {image:img},
	 	      success: function (data) {
	 	        if(data=="success")
	 	        	{
	 	        		alert("hai");
	 	        	}
	 	        else
	 	        	{
	 	        	alert("bye");
	 	        	}
	 	      }
	 	    });
	 }
 */
	
 
  function submitEditForm(){
	 	//alert(validate());
		if(validate() == true){
			$('#tripEditForm').submit();
		}
  }
 function cancelEditTrip(){
	 window.location.href='/tripcaddie/getTrip.do?tripId='+tripId;
 }
 
 /* function ajaxFileUpload()
	{
	
		
		$.ajaxFileUpload
		(
			{
				url:'/tripcaddie/validateImage.do', 
				secureuri:false,
				fileElementId:'image',
				success: function (data, status)
				{
					alert('sucess');
					alert(data);
					alert(status);
					$('#tripEditForm').submit();
		
				},
				error: function (data, status, e)
				{
					alert(e);
				}
			
			}
		)
		
		return true;

	}   */
	</script>
	
	
</head>
<body onload="hideImg();">
	<%@ include file="/pages/header2.jsp" %>
	
	<script type="text/javascript">
		tripId=${tripMap.trip.tripId};
	</script>

	<form id="tripEditForm" name="tripEditForm" enctype="multipart/form-data" method="post" >
		<input type="hidden" name="tripId" id="tripId" value="${tripMap.trip.tripId}" />
		<input type="hidden" name="removeImg" id="removeImg" value="" />
		<p>Name your trip <span class="form-required">*</span> :</p>
		<p><input type="text" id="tripName" name="tripName" value="${tripMap.trip.tripName }" /><span id="tripnameError" class="form-required"></span></p>
		<p>Destination  <span class="form-required">*</span> :</p>
		<p><input type="text" id="destination"  readonly="readonly" name="destination" value="${tripMap.trip.golfCourseDto.courseName}" /></p>
		<input type="hidden" name="courseId" value="${tripMap.trip.golfCourseDto.golfCourseId}">
		<p>Trip start date  <span class="form-required">*</span> :</p>
		<p><input type="text" id="startDatePicker" name="startDate"  readonly="readonly" value='<fmt:formatDate value="${tripMap.trip.startDate.time }" pattern="MM/dd/yyyy"/>'/><span id="startDateError"></span></p>
		<p>Trip End date  <span class="form-required">*</span> :</p>
		<p><input type="text" id="endDatePicker" name="endDate" readonly="readonly" value='<fmt:formatDate value="${tripMap.trip.endDate.time }" pattern="MM/dd/yyyy"/>'      /><span id="endDateError"></span></p>
		<p>Welcome Message:</p>
		<p><input type="text" name="message" id="welcomeMessage" value="${tripMap.trip.welcomeMessage}"><span id="messageError"></span></p>
		<p>Golf Trip or Group Logo:</p>
		<div id="removePic">
		<p>
			<img alt='Embedded Image' width='100px' id="img" height='100px' src=data:image/png;base64,${tripMap.image} />  
		
			<input type="button" id="rmvImg" value="remove" onclick="remove();" />
			
		</p>
		<p>
		   <div id="imgLoad">	
		   			 <img id="img" src="" />	
					 <input type="file" name="image" id="image" /><span id="fileError"></span>
			</div>	
		</p>
		</div>
		<p>Promo code:</p>
		<p><input type="text" id="promoCode" name="promoCode" value="${tripMap.trip.promoCode}"/></p>
		<p>Is this a Annual trip?:</p>
		
		
		<c:choose>
			<c:when test="${tripMap.trip.annualTrip eq 1}">
				<input type="radio" id="YesBtn" value="yes" checked="checked" name="annualTrip"  />Yes&nbsp;
				<input type="radio" name="annualTrip" id="noBtn"  value="no"  />No
			</c:when>
			<c:otherwise>
				<input type="radio" id="YesBtn" value="yes"  name="annualTrip"  />Yes&nbsp;
				<input type="radio" name="annualTrip" checked="checked" id="noBtn"   />No
			</c:otherwise>
		</c:choose>
		<p><input type="button" value="Save" id="EditBtn" name="EditBtn" onclick="submitEditForm();"/>
		<input type="button" value="Cancel" onclick="cancelEditTrip();" /></p>
	</form>
</body>
</html>