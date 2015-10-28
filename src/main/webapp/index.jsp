<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home Page</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDYrHPgoC-XZi5Sx4FXjszsOroSNui-Z-E&sensor=false"></script>
	<script type="text/javascript">
		function validateEmail(sEmail) {
	    	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	    	if (filter.test(sEmail)) {
	        	return true;
	    	}
	    	else{
	        	return false;
	    	}
		}
		function sendMail(){
			$.get("./forgetPassword.do",{
    			email : $('#email').val()
    		},
    		function(data){
    			if(data=="fail"){
    				$('#emailError').text("Email is invalid");
    			}else{
    				jQuery('#forgetPasswordPopup').dialog('close');
    			}
    		});			
		}
		$(document).ready(function(){
			$("#email").focusout(function(){
				var email=$('#email').val();
				if ($.trim(email).length == 0) {
					$('#emailError').text("Email address is not valid");
		        }
				else if (!validateEmail(email)) {
	        		$('#emailError').text("Email address is not valid");
	        	}else{
	        		$('#emailError').text("");
	        	}
	    	});
			
			$("#search").click(function(){
					var dest=$('#destinationBox').val();
					clearOverlays();
					$.ajax({
						url: './getDestinations.do',
						data:({places:dest}),
						success: function(jsonObj){
							addMarkers(jsonObj);						
						}
					});
				});
			});
		 
		function displayLoginForm(){
			jQuery("#loginPopup").dialog({modal: true,
    		resizable: false,
    		autoOpen: false,
    		height: 'auto',
    		width: 500,
    		top:-535,
    		borderWeight:15,
    		borderColor:'0059ff',
    		backgroundColor:'FFA500',
    		left:'50%'
    		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
    	});
			jQuery('#loginPopup').dialog('open');
			$('#forgetPassword').click(function(){
				jQuery('#loginPopup').dialog('close');
				displaypasswordPopup();
			});
		} 
		
		function displayProfile(){
			<c:choose>
    			<c:when test="${empty sessionScope.username}">
    				displayLoginForm();
   	 			</c:when>
    			<c:otherwise>
    				window.location.href="./profile.do"	
    			</c:otherwise>
			</c:choose>			
		}
		
		function logout(){
			$.get("./logout.do",function(){
				window.location.href="./";
			});
		}
		
        function displaypasswordPopup(){
			jQuery("#forgetPasswordPopup").dialog({modal: true,
    		resizable: false,
    		autoOpen: false,
    		height: 'auto',
    		width: 500,
    		top:-535,
    		borderWeight:15,
    		borderColor:'0059ff',
    		backgroundColor:'FFA500',
    		left:'50%'
    		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
    	});
			jQuery('#forgetPasswordPopup').dialog('open');
			$('#close').click(function(){
				jQuery('#forgetPasswordPopup').dialog('close');
			});
		} 
        function tellfriend(){
        	jQuery("#tellAFriendPopup").dialog({modal: true,
        		resizable: false,
        		autoOpen: false,
        		height: 'auto',
        		width: 500,
        		top:-535,
        		borderWeight:15,
        		borderColor:'0059ff',
        		backgroundColor:'FFA500',
        		left:'50%'
        		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
        	});
    		jQuery('#tellAFriendPopup').dialog('open');
    		$('#close').click(function(){
    				jQuery('#tellAFriendPopup').dialog('close');
    		});
        }
	</script>
	<!-- Map -->
	<script type="text/javascript">
	    var destinationMarkersArray=[];
	    var map;
	    var infoWindow; 
		function initialize(){
			var myOptions = {
		      center: new google.maps.LatLng(39.724089, -101.491699),
		      zoom: 5,
		      mapTypeId: google.maps.MapTypeId.ROADMAP
		    };
			map = new google.maps.Map(document.getElementById("map"),myOptions);
		}
		
		function clearOverlays() {
			 if (destinationMarkersArray) {
				for (i in destinationMarkersArray) {
					destinationMarkersArray[i].setMap(null);
				}
			}
			 destinationMarkersArray.length=0;
		}
		function test(marker){
			//alert(marker.id);
			/* $.post("./getClubDetails",{
				courseId : marker.id
    		}); */
    		window.location.href="./getClubDetails.do?courseId="+marker.id;
		}
		function displayBucketList(){
			
			<c:choose>
	    		<c:when test="${empty sessionScope.username}">
	    			displayLoginForm();
	   	 		</c:when>
	    		<c:otherwise>
	    			window.location.href="./getBucketList.do?places=2";	
	    		</c:otherwise>
			</c:choose>
			
		}
		function addMarkers(json){
			infoWindow=new google.maps.InfoWindow();
			for(i=0;i<json.requestObject.jsonObject.length;i++){
				var mylatlng = new google.maps.LatLng(json.requestObject.jsonObject[i].locationLattitudePoly, json.requestObject.jsonObject[i].locationLongitudePoly);
				map.setCenter(mylatlng)
				var marker = new google.maps.Marker({
					position : mylatlng,
					map : map,
					id : json.requestObject.jsonObject[i].golfCourseId,
					title:json.requestObject.jsonObject[i].courseName,
					draggable: false,
					clickable:true,
				});
				destinationMarkersArray.push(marker);
				var contentString=json.requestObject.jsonObject[i].courseName;
				bindInfoW(marker, contentString, infoWindow);
		}
		function bindInfoW(marker, contentString, infoWindow){
			google.maps.event.addListener(marker, 'mouseover', function() {
				infoWindow.setContent(contentString);
				infoWindow.open(map,marker);
			});
			google.maps.event.addListener(marker,'click',function(){
				test(marker);
			});
		}
	}
	</script>
	<style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map { height: 100% }
	</style>
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
</head>

<body onload="initialize()">
	<header style="text-align: right;">
		<a href="http://blog.tripcaddie.com/">TripCaddie Blog</a>|<a href="#" onclick="tellfriend();">Tell A Friend</a>|<a href="http://www.tripcaddie.com/faq">FAQ</a>
	</header>
	<form method="get" action="registration.do">
	<c:set var="username" value= "${sessionScope.username}" scope= "session" />
	<c:choose>
    	<c:when test="${empty sessionScope.username}">
    	    <p style="text-align: right;"><input type="Submit" value="Join Now"></p>
        	<p style="text-align: right;"><input type="button" value="Login" onclick="displayLoginForm()"></p>
   	 	</c:when>
    	<c:otherwise>
     		hi..<c:out value= "${sessionScope.username}"/>
      		<p style="text-align: right;"><input type="button" value="Logout" onclick="logout();"></p>
    	</c:otherwise>
	</c:choose>
		
		<p style="text-align: right;"><input type="button" value="Bucket List" onclick="displayBucketList();" /></p>
		<p style="text-align: right;"><input type="button" value="My trips"></p>
		<p style="text-align: right;"><input type="button" value="My profile" onclick="displayProfile();" ></p>
	</form>
	<div id="loginPopup" style="display: none;" class="popup">
		<form method="POST" action="<c:url value="/j_spring_security_check" />">
    		<table>
        		<tr>
            		<td align="right">Username</td>
            		<td><input type="text" name="j_username" /></td>
        		</tr>
        		<tr>
	            	<td align="right">Password</td>
            		<td><input type="password" name="j_password" /></td>
        		</tr>
	        	<tr>
    	        	<td colspan="2" align="right">
        	        	<input type="submit" value="Login" />
            		</td>
        		</tr>
        		<tr>
        			<td><a href="#" id="forgetPassword">Forget Password</a></td>
        		</tr>
    		</table>
		</form>
	</div>
	<div id="forgetPasswordPopup" style="display: none;" class="popup">
		<form method="get" action="">
			<p>Enter your Email-id</p>
			<p><input name="email" type="text" id="email" />
				<label id="emailError"></label>
			</p>
			<p><input type="button" value="Yes,Email it to me" onclick="sendMail()"/>
			<input type="button" value="close" id="close" /></p>
		</form>
	</div>
	<div id="destination" style="margin: 0 auto;">
		<p style="text-align:left;padding-left: 273px;"><input type="text" name="destinationBox" id="destinationBox" style="text-align: left;size: 50px;"/>
		<input type="submit" value="search" id="search"/></p>
		<div id="map" style="width: 800px;height: 500px;margin: 1 auto;"></div>
	</div>
	
	<div id="tellAFriendPopup" style="display: none;" class="popup">
		<form action="tellAFriend.do" method="post">
			<p>Sender name: <input type="text" name="senderName" /></p>
			<p>Recipent name: <input type="text" name="recipentName" /></p>
			<p>Recipent Email: <input type="text" name="recipentEmail" /></p>
			<input type="submit" value="Submit"/>
		</form>
	</div>
	<footer style="text-align: center;">
		<a href="http://www.tripcaddie.com/terms-of-use">Terms of use</a>|<a href="http://www.tripcaddie.com/privacy-policy">Privacy Policy</a>|<a href="http://www.tripcaddie.com/news-press-release">News &amp; Press Releases</a>|<a href="#">Contact US</a>|<a href="#">Advertise with us</a>|<a href="http://www.tripcaddie.com/node/3929">Website feedback</a>
	</footer>
	
</body>
</html>
