<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script type="text/javascript">
		var tripId;
		var pictureId;
		
		function save(){
			$.post("/tripcaddie/trip/editPicture.do",{
				tripId : tripId,
				pictureId : pictureId,
				description : $('#description').val()
			},function(data){
				if(data=='success'){
					window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
				}else{
					alert(data);
				}
			})
		}
		function deletePhoto(){
			var op=confirm("Are you sure you want to delete?</br> This action cannot be undone.");
			if(op){
				$.post('./deletePicture.do',{
					pictureId : pictureId,
					tripId : tripId
				},function(data){
					if(data=="success"){
						window.location.href="/tripcaddie/trip/getPictures.do?tripId="+tripId;
					}else{
						window.location.href="./error.do";
					}
				});
			}
		}
		function cancel(){
			window.location.href="/tripcaddie/trip/getPicture.do?tripId="+tripId+'&pictureId='+pictureId;
		}
	</script>
	<style type="text/css">
	.star{
		color: red;
	}
</style>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
		tripId=${trip.tripId};
		pictureId=${picture.pictureId};
	</script>
	<h3>Edit Photo</h3>

	<!-- Trip Details -->
	<p><img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} /></p>
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>
	
	<!-- Image -->
	<div id="photo">
		<p>Image upload:<span class="star">*</span>&nbsp;&nbsp;&nbsp; <img alt='Embedded Image' width='100px' height='100px' src=data:image/png;base64,${picture.imageInBase64} /> </p>
		<p>Description&nbsp;&nbsp;&nbsp;<input type="text" value="${picture.description }" id="description" /></p>
		<p>
			<input type="button" value="Save" onclick="save()" />
			<input type="button" value="Delete" onclick="deletePhoto()" />
			<input type="button" value="Cancel" onclick="cancel()" />
		</p>
		
	</div>
		
</body>
	
</html>