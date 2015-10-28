
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
	/*function displayBucketList(){
			<c:choose>
	    		<c:when test="${empty sessionScope.username}">
	    			displayLoginForm();
	   	 		</c:when>
	    		<c:otherwise>
	    			window.location.href="./getBucketList.do?places=2";	
	    		</c:otherwise>
			</c:choose>
			
		}*/
	$(document).ready(function(){
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