<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>displayUpdatePriorityPopup
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bucket List</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
    
	/* $.mynamespace ={
		rowCount : null
	}; */
	
    var golfCourseId=null;
    var visitedid=null;
    var rowCount=null;
	
    function switchBetweenList(){
    	
    	$.post("/tripcaddie/categorizeinBucketList.do",{		
			courseId : golfCourseId 		
		},function(data){
			$('#bucketLists').empty();
			jQuery('#updatePriorityPopup').dialog('close');
			if(visitedid == 1){
				playedList(data);
			}else{
				wantToPlayList(data);
			}
		});	
    }
    
	function removeFromList(courseId,visitId){
		//$('#bucketLists').empty();
		
		$.post("./removeFromList.do",{
			courseId : courseId,
			visitId : visitId
		},
		function(data){
			$('#bucketLists').empty();
			if(visitId==1){
				playedList(data);
			}
			else{
				wantToPlayList(data);
			}
		});
	}
	
	function getPlayedList(){
		//$('#bucketLists').empty();
		$.post("./ajax/getBucketList.do",{
			places : 1
		},
		function(data){
			$('#bucketLists').empty();
			playedList(data);
		});
	}
	
	function getNotPlayedList(){
		//$('#bucketLists').empty();
		$.post("./ajax/getBucketList.do",{
			places : 2
		},
		function(data){
			$('#bucketLists').empty();
			wantToPlayList(data);
		});
	}
	
	function playedList(data){
		var i;
		var append;
		//$.mynamespace.rowcount=data.requestObject.rowCount;
		rowCount=data.requestObject.rowCount;
		
		for(i=0;i<data.requestObject.jsonObject.length;i++){
			var divTag = document.createElement("div");
			divTag.setAttribute("id","bucketList");
			append='<span><span><a href="#" onclick=displayUpdatePriorityPopup('+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+','+data.requestObject.jsonObject[i].placesPlayedDto.placesPlayedId+',"'+data.requestObject.jsonObject[i].placesPlayedDto.status+'")>'+data.requestObject.jsonObject[i].priority+'</a></span><span> <a href="/tripcaddie/getClubDetails.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'" >'+data.requestObject.jsonObject[i].golfCourseDto.courseName
			 	   +'-'+data.requestObject.jsonObject[i].golfCourseDto.addressDto.state+','+data.requestObject.jsonObject[i].golfCourseDto.addressDto.country +'</a>'
				   +' <a href="#" onclick="removeFromList('+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+','+data.requestObject.jsonObject[i].placesPlayedDto.placesPlayedId+');">Remove</a></span><span> ('
				   +data.requestObject.jsonObject[i].golfCourseDto.rating+') Rating </span><span> <a href="/tripcaddie/postReview.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'"> ('+data.requestObject.jsonObject[i].recommendationCount+') Recommendations </a></span><span> <a href="/tripcaddie/giveAdvice.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'"> ('
				   +data.requestObject.jsonObject[i].inquiryCount +') Ask a question(s) </a></span></span>';
			divTag.innerHTML = append;
			document.getElementById("bucketLists").appendChild(divTag);
		}
	}
	
	function wantToPlayList(data){
		var i;
		var append;
		//$.mynamespace.rowcount=data.requestObject.rowCount;
		rowCount=data.requestObject.rowCount;
		
		for(i=0;i<data.requestObject.jsonObject.length;i++){
			var divTag = document.createElement("div");
			divTag.setAttribute("id","bucketList");
			append='<span><span><a href="#" onclick=displayUpdatePriorityPopup('+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+','+data.requestObject.jsonObject[i].placesPlayedDto.placesPlayedId+',"'+data.requestObject.jsonObject[i].placesPlayedDto.status+'")>'+data.requestObject.jsonObject[i].priority+'</a></span> <span><a href="/tripcaddie/getClubDetails.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'" >'+data.requestObject.jsonObject[i].golfCourseDto.courseName
				    +'-'+data.requestObject.jsonObject[i].golfCourseDto.addressDto.state+','+data.requestObject.jsonObject[i].golfCourseDto.addressDto.country +'</a>'
					+' <a href="#" onclick="removeFromList('+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+','+data.requestObject.jsonObject[i].placesPlayedDto.placesPlayedId+');">Remove</a></span><span> ('
					+data.requestObject.jsonObject[i].golfCourseDto.rating+') Rating </span><span> <a href="/tripcaddie/postReview.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'">('+data.requestObject.jsonObject[i].recommendationCount+') Recommendations </a> </span><span> <a href="/tripcaddie/giveAdvice.do?courseId='+data.requestObject.jsonObject[i].golfCourseDto.golfCourseId+'"> (' 
					+data.requestObject.jsonObject[i].inquiryCount +') Ask a question(s) </a></span></span>';
			divTag.innerHTML = append;
			document.getElementById("bucketLists").appendChild(divTag);
		}
	}
	
	function displayUpdatePriorityPopup(courseId,visitedId,option){
		jQuery("#updatePriorityPopup").dialog({modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height : 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%'
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
	 	});
		golfCourseId=courseId;
		visitedid=visitedId;
		if(option == 'visited'){
			$('#bucketListOption').text('Change to want to play');
		}else{
			$('#bucketListOption').text('Change to Played There');
		}
		jQuery('#updatePriorityPopup').dialog('open');
		$('#close').click(function(){
			jQuery('#updatePriorityPopup').dialog('close');
		});
	} 
	
	function movePopup(){
		jQuery('#moveToPriorityPopup').dialog({
			modal: true,
			resizable: false,
			autoOpen: false,
			width: 500,
			top:-535,
			height: 'auto',
			borderWeight:15,
			borderColor:'0059ff',
			backgroundColor:'FFA500',
			left:'50%'
		});
		jQuery('#updatePriorityPopup').dialog('close');
		jQuery('#moveToPriorityPopup').dialog('open');
		$('#close').click(function(){
			jQuery('#moveToPriorityPopup').dialog('close');
		});
	}
	
	function updatePrioritybyValue(){
		jQuery('#moveToPriorityPopup').dialog('close');
		//alert("in update priority:"+golfCourseId);
		var position=$("#priority").val();
		
		/* if($.mynamespace.rowcount < position || position == null || position <= 0 ){
			alert("Invalid input");	
		} */
		if(rowCount< position || position == null || position <= 0 )
			alert("Invalid input");	
		else{		
			$('#bucketLists').empty();
			$.post("./updatePriority.do",{
				move : position,
				courseId : golfCourseId,
				visitedId: visitedid
			},
			function(data){
				if(visitedid==1){
					playedList(data);
				}
				else{
					wantToPlayList(data);
				}
				jQuery('#updatePriorityPopup').dialog('close');			
			});
		}
	}
	function displayHomePage(){
		window.location.href="/tripcaddie";
	}
	function updatePriority(position){
		//$('#bucketLists').empty();
		//alert(position);
		$.post("./updatePriority.do",{
			move : position,
			courseId : golfCourseId,
			visitedId: visitedid
		},
		function(data){
			$('#bucketLists').empty();
			if(visitedid==1){
				playedList(data);
			}
			else{
				wantToPlayList(data);
			}
			jQuery('#updatePriorityPopup').dialog('close');			
		});		
	}
	
	
</script>
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
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<div>
		<p style="text-align: right;"><input type="button" value="Add destinations to BucketList" onclick="displayHomePage();" /></p>
		<!-- <p style="text-align: right;"><a href="#">Places I want to play</a></p>
		<p style="text-align: right;"><a href="#">Places I Have played</a></p> -->
		<p style="text-align: right;"><a href="./myInquiries.do">View my advice requests</a></p>
		<p style="text-align: right;"><a href="./myReviews.do">View My recommendations</a></p>
	</div>
	<br>
	<br>
	<br>
	
	<p><a href="#" onclick="getNotPlayedList();">Want to play</a>&nbsp;&nbsp;<a href="#" onclick="getPlayedList();">Played There</a></p>
	<strong><span>Priority</span> <span>Golf course Name</span> <span>Ratings,Requests, and Recommendations</span></strong>
	<script type="text/javascript">
		//alert($.mynamespace);
		rowCount=${bucketLists.rowCount};
		//$.mynamespace.rowCount=${bucketLists.rowCount}
		
	</script>
	<div id="bucketLists">
		<c:forEach var="data" items="${bucketLists.bucketLists}">
			<div id="bucketList">
				<span><span><a href="#" onclick="displayUpdatePriorityPopup(${data.golfCourseDto.golfCourseId},${data.placesPlayedDto.placesPlayedId},'${data.placesPlayedDto.status}');">${data.priority }</a></span> <span> <a href="/tripcaddie/getClubDetails.do?courseId=${data.golfCourseDto.golfCourseId}"> ${data.golfCourseDto.courseName } - ${data.golfCourseDto.addressDto.state },${data.golfCourseDto.addressDto.country } </a></span> <span>${data.golfCourseDto.rating}
				<a href="#" onclick="removeFromList(${data.golfCourseDto.golfCourseId},${data.placesPlayedDto.placesPlayedId})">Remove</a></span>
				<span>(${data.ratingCount}) Ratings</span><span><a href="/tripcaddie/postReview.do?courseId=${data.golfCourseDto.golfCourseId}">(${data.recommendationCount}) Recommendations</a></span> <span><a href="/tripcaddie/giveAdvice.do?courseId=${data.golfCourseDto.golfCourseId}">(${data.inquiryCount}) Ask a question(s)</a></span> </span>
			</div>
		</c:forEach>
	</div>
	<!-- Popup -->
	<div id="updatePriorityPopup" style="display: none;" class="popup">
		<p><a href="#" onclick="updatePriority('up');">Up</a></p>
		<p><a href="#" onclick="updatePriority('down');">down</a></p>
		<p><a href="#" onclick="movePopup();">Move to...</a></p>
		<p><a href="#" id="bucketListOption" onclick="switchBetweenList();">Switch over</a>
	</div>
	<div id="moveToPriorityPopup" style="display: none;" class="popup">
		<p><input type="text" name="priority" id="priority" /></p>
		<p><input type="button" value="Ok" onclick="updatePrioritybyValue();" /></p>
	</div>
</body>
</html>