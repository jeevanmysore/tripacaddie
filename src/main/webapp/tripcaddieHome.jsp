<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Trip-caddie Home Page</title>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
		<script type="text/javascript" src="javascript/js/map.js"></script>
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDYrHPgoC-XZi5Sx4FXjszsOroSNui-Z-E&sensor=false"></script>
		<!-- <script type="text/javascript">
			function displayUserHome(){
				window.location.href="./userHome.do";
			}
		</script> -->
		<style type="text/css">
      		html { height: 100% }
      		body { height: 100%; margin: 0; padding: 0 }
      		#map { height: 100% }
		</style>
	</head>
	<body onload="initialize()" style="position: relative;">
		<%@ include file="/pages/header.jsp" %>
		<h2>Trip-caddie home page</h2>
		<p style="text-align: right;"><input type="button" value="Bucket List" onclick="displayBucketList();" /></p>
		<p style="text-align: right;"><input type="button" value="My trips" onclick="displayUserHome();">
		<!-- <p style="text-align: right;"><input type="button" value="Create A Trip" onclick="createTrip();"> -->
		<div id="destination" style="margin: 0 auto;">
			<p style="color: red;text-align: center;">Note : This map is temporary. You have to type 'USA' for testing</p>
			<p style="text-align:left;padding-left: 273px;"><input type="text" name="destinationBox" id="destinationBox" style="text-align: left;size: 50px;"/>
			<input type="submit" value="search" id="search"/></p>
			<div id="map" style="width: 800px;height: 500px;margin: 0 auto;text-align: center;"></div>
		</div>
		<%@ include file="/pages/footer.jsp" %>
	</body>
	
</html>