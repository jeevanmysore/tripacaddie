<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<title>Itinerary | Tripcaddie</title>
<script type="text/javascript">
	var tripId;
	var accommodationId;
	
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
	function saveAccommodation(){

		var queryString='tripId='+tripId+'&accommodationTxt='+$('#accommodationtxt').val();
		$.ajax({
			url: "addAccommodation.do",
			type: "POST",
			async: false,
			data: queryString,
			success: function(data){
				hide();
				var append;
				var divTag = document.createElement("div");
				divTag.setAttribute("id","accommodation");
				accommodationId=data.jsonObject.accommodation.accommodationId;
				append='<p>'+data.jsonObject.accommodation.accomadationContent+' <a href="#" onclick="editAccommodations(\''+data.jsonObject.accommodation.accomadationContent+'\')">Edit</a>|<a href="#" onclick="deleteAccommodation()">Delete</a></p>'
				divTag.innerHTML = append;
				document.getElementById("accommodations").appendChild(divTag);
			}
		});
		jQuery('#accommodationPopup').dialog('close');
		
	}
	
	function displayAccommodations(){
		jQuery("#accommodationPopup").dialog({modal: true,
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
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
	});
		//alert($('#accommodationtxt').val(""));
		$('#accommodationtxt').val("");
		jQuery('#accommodationPopup').dialog('open');
		$('#cancelBtn').click(function(){
			jQuery('#accommodationPopup').dialog('close');
		});
		
	} 
	 
	function updateAccommodation(){
		
		var queryString='accommodationId='+accommodationId+'&accommodationTxt='+$('#accommodationtext').val();
		$.ajax({
			url: "editAccommodation.do",
			type: "POST",
			async: false,
			data: queryString,
			success: function(data){
				$('#accommodations').empty();
				var append;
				var divTag = document.createElement("div");
				divTag.setAttribute("id","accommodation");
				accommodationId=data.jsonObject.accommodation.accommodationId;
				append='<p>'+data.jsonObject.accommodation.accomadationContent+' <a href="#" onclick="editAccommodations(\''+data.jsonObject.accommodation.accomadationContent+'\')">Edit</a>|<a href="#" onclick="deleteAccommodation()">Delete</a></p>'
				divTag.innerHTML = append;
				document.getElementById("accommodations").appendChild(divTag);
			}
		});
		jQuery('#editAccommodationPopup').dialog('close');
	}
	
	function editAccommodations(){
		
		jQuery("#editAccommodationPopup").dialog({modal: true,
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
			/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		});
		$('#accommodationtext').val($('#content').text());
		jQuery('#editAccommodationPopup').dialog('open');
		$('#cancel').click(function(){
			jQuery('#editAccommodationPopup').dialog('close');
		});
	}
	
	function deleteAccommodation(){
		//alert('accommodationId'+accommodationId);			
		$.post("./deleteAccommodation.do",{
			accommodationId:accommodationId
		},function(data){
			if(data=='error'){
				window.location.href="./error.do"
			}else {
				show();
				$('#accommodations').empty();
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
		<img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${accommodation.image } />
		<span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${accommodation.trip.tripId}">${accommodation.trip.tripName }</a></span>
		<p>${accommodation.trip.golfCourseDto.addressDto.city },${accommodation.trip.golfCourseDto.addressDto.state },${accommodation.trip.golfCourseDto.addressDto.country }</p>
		<p><fmt:formatDate value="${accommodation.trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${accommodation.trip.endDate.time }" type="date" dateStyle="long"/></p>
	</p>
	<P>
		<h2>ITINERARY DETAILS</h2>
		<a href="#" onclick="print()">Print</a>
		<a href="#" onclick="save()">Save</a>
	</P>
	<script type="text/javascript">
		tripId=${accommodation.trip.tripId};
		<c:if test="${not empty accommodation.accommodation}">
			accommodationId	= ${accommodation.accommodation.accommodationId}
		</c:if>
	</script>
	<p><a href="#" onclick="getActivities()">Activities</a><a href="#" onclick="getAccommodation();">Accommodations</a><a href="#" onclick="getLogistics();">Logistics</a></p>
	<p><input type="button" value="Add accommodations" id="addBtn" onclick="displayAccommodations();" /></p>
	
	<c:choose>
		<c:when test="${accommodation.trip.tripLeader eq username && accommodation.trip.startDate.time ge now  }">
			<c:choose>
				<c:when test="${empty accommodation.accommodation}">
					<script type="text/javascript">show();</script>
				</c:when>
				<c:otherwise>
					<script type="text/javascript">hide();</script>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise><script type="text/javascript">hide();</script></c:otherwise>
	</c:choose>
	<div id="accommodations" >
		<div id="accommodation">
			<c:choose>
				<c:when test="${empty accommodation.accommodation }">
				<!-- 	<p><input type="button" value="Add accommodations" id="addBtn" onclick="displayAccommodations();" /></p> -->
				</c:when>
				<c:otherwise>
					<p><span id="content">${accommodation.accommodation.accomadationContent }</span> 
						<c:if test="${accommodation.trip.tripLeader eq username && accommodation.trip.startDate.time ge now}">
							<a href="#" onclick='editAccommodations();'>Edit</a>|<a href="#" onclick="deleteAccommodation()">Delete</a>
						</c:if>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!-- Popup -->
	<div id="accommodationPopup" style="display: none;">
		<p>Tell the group about any Special accommodations that need to be considered at the event location. This will appear in the itinerary.</p>
		<p><input type="text" name="accommodation" id="accommodationtxt" /></p>
		<p><input type="button" value="Save" name="saveBtn" id="saveBtn" onclick="saveAccommodation();">
		<input type="button" value="Cancel" id="cancelBtn" name="cancelBtn"></p>
	</div>
	<div id="editAccommodationPopup" style="display: none;">
		<p>Tell the group about any Special accommodations that need to be considered at the event location. This will appear in the itinerary.</p>
		<p><input type="text" name="accommodation" id="accommodationtext" /></p>
		<p><input type="button" value="Save" name="saveBtn" id="save" onclick="updateAccommodation();">
		<input type="button" value="Cancel" id="cancel" name="cancelBtn"></p>
	</div>
</body>
</html>