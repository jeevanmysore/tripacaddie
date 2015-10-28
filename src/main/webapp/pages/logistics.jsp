<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Itinerary | Tripcaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript">
		var tripId;
		var logisticsId;
		
		function back(){
			window.history.back();
		}
		function displayTripHome(){
			window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
		}
		function print(){
			window.open('/tripcaddie/pdf.do?tripId='+tripId+'&format=view','_newtab');
		}
		function save(){
			window.location.href="/tripcaddie/pdf.do?tripId="+tripId+"&format=download";
		}
		function show(){
			$('#addBtn').show();
		}
		function hide(){
			$('#addBtn').hide();
		}
		function getAccommodation(){
			window.location.href="./accommodation.do?tripId="+tripId;	
		}
		function getLogistics(){
			window.location.href="./logistics.do?tripId="+tripId;
		}
		function getActivities(){
			window.location.href="./activities.do?tripId="+tripId;
		}
		function saveLogistics(){
			
			var queryString='tripId='+tripId+'&logisticsTxt='+$('#logisticstxt').val();
			$.ajax({
				url: "addLogistic.do",
				type: "POST",
				async: false,
				data: queryString,
				success: function(data){
					hide();
					var append;
					var divTag = document.createElement("div");
					divTag.setAttribute("id","logistic");
					logisticsId=data.jsonObject.logistics.logisticsId;
					append='<p>'+data.jsonObject.logistics.logisticsContent+' <a href="#" onclick="editLogistics(\''+data.jsonObject.logistics.logisticsContent+'\')">Edit</a>|<a href="#" onclick="deleteLogistics()">Delete</a></p>'
					divTag.innerHTML = append;
					document.getElementById("logistics").appendChild(divTag);
				}
			});
			jQuery('#logisticsPopup').dialog('close');
		}
		
		function displayLogistics(){
			jQuery("#logisticsPopup").dialog({modal: true,
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
			/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		});
			$('#logisticstxt').val("");
			jQuery('#logisticsPopup').dialog('open');
			$('#cancelBtn').click(function(){
				jQuery('#logisticsPopup').dialog('close');
			});
		}
		
		function updateLogistics(){
			
			var queryString='logisticsId='+logisticsId+'&logisticsText='+$('#logisticstext').val();
			$.ajax({
				url: "editLogistics.do",
				type: "POST",
				async: false,
				data: queryString,
				success: function(data){
					$('#logistics').empty();
					var append;
					var divTag = document.createElement("div");
					divTag.setAttribute("id","logistic");
					logisticsId=data.jsonObject.logistics.logisticsId;
					append='<p>'+data.jsonObject.logistics.logisticsContent+' <a href="#" onclick="editLogistics(\''+data.jsonObject.logistics.logisticsContent+'\')">Edit</a>|<a href="#" onclick="deleteLogistics()">Delete</a></p>'
					divTag.innerHTML = append;
					document.getElementById("logistics").appendChild(divTag);
				}
			});
			jQuery('#editLogisticsPopup').dialog('close');
		}
		
		function editLogistics(content){
			
			jQuery("#editLogisticsPopup").dialog({modal: true,
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
				/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
			});
			$('#accommodationtext').val($('#content').text());
			jQuery('#editLogisticsPopup').dialog('open');
			$('#cancel').click(function(){
				jQuery('#editLogisticsPopup').dialog('close');
			});
		 }
		
		 function deleteLogistics(){
			//alert('logisticsId '+logisticsId);			
			$.post("./deleteLogistics.do",{
				logisticsId:logisticsId
			},function(data){
				if(data=='error'){
					window.location.href="./error.do"
				}else {
					show();
					$('#logistics').empty();
				}
			});
		}
</script>
<style type="text/css">
	.ui-state-error{
		background-color: lime;
		}
</style>	
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<jsp:useBean id="now" class="java.util.Date" /> 
	<input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<h2>Itinerary</h2>
	<p>
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${logistics.image } />
		<span style="font-weight: bold;">${logistics.trip.tripName }</span>
		<p>${logistics.trip.golfCourseDto.addressDto.city },${logistics.trip.golfCourseDto.addressDto.state },${logistics.trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${logistics.trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${logistics.trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	<P>
		<h2>ITINERARY DETAILS</h2>
		<a href="#" onclick="print()">Print</a>
		<a href="#" onclick="save()">Save</a>
	</P>
	<script type="text/javascript">
		tripId=${logistics.trip.tripId};
		<c:if test="${not empty logistics.logistics}">
			logisticsId	= ${logistics.logistics.logisticsId}
		</c:if>
	</script>
	<p><a href="#" onclick="getActivities()">Activities</a><a href="#" onclick="getAccommodation();">Accommodations</a><a href="#" onclick="getLogistics();">Logistics</a></p>
	<p><input type="button" value="Add Logistics" id="addBtn" onclick="displayLogistics();" /></p>
	<c:choose>
		<c:when test="${logistics.trip.tripLeader eq username && logistics.trip.startDate.time ge now}">
			<c:choose>
				<c:when test="${empty logistics.logistics}">
					<script type="text/javascript">show();</script>
				</c:when>
				<c:otherwise>
					<script type="text/javascript">hide();</script>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise><script type="text/javascript">hide();</script></c:otherwise>
	</c:choose>
	<div id="logistics" >
		<div id="logistic">
			<c:choose>
				<c:when test="${empty logistics.logistics }">
				<!-- 	<p><input type="button" value="Add accommodations" id="addBtn" onclick="displayAccommodations();" /></p> -->
				</c:when>
				<c:otherwise>
					<p><span id="content"> ${logistics.logistics.logisticsContent }</span>
					<c:if test="${logistics.trip.tripLeader eq username && logistics.trip.endDate.time ge now}">
					 	<a href="#" onclick='editLogistics("${logistics.logistics.logisticsContent }");'>Edit</a>|<a href="#" onclick="deleteLogistics()">Delete</a>
					 </c:if>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!-- Popup -->
	<div id="logisticsPopup" class="popup" style="display: none;">
		<p>Tell the group about any Special travel arrangements to/from the event location. This will appear in the itinerary.</p>
		<p><input type="text" name="logistics" id="logisticstxt" /></p>
		<p><input type="button" value="Save" name="saveBtn" id="saveBtn" onclick="saveLogistics()">
		<input type="button" value="Cancel" id="cancelBtn" name="cancelBtn"></p>
	</div>
	<div id="editLogisticsPopup" class="popup" style="display: none;">
		<p>Tell the group about any Special travel arrangements to/from the event location. This will appear in the itinerary.</p>
		<p><input type="text" name="logistics" id="logisticstext" /></p>
		<p><input type="button" value="Save" name="saveBtn" id="save" onclick="updateLogistics()">
		<input type="button" value="Cancel" id="cancel" name="cancelBtn"></p>
	</div>
</body>
</html>