<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Browser</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
	 
 <script src="/tripcaddie/gmap/maps" type="text/javascript"></script><script src="/tripcaddie/gmap/main.js" type="text/javascript"></script><style type="text/css">@media print{.gmnoprint{display:none}}@media screen{.gmnoscreen{display:none}}</style>
    <script type="text/javascript">
    

    var map;
    $(function(){
  
    	
    	if(${map}){
    	$('#map').show();
       if (GBrowserIsCompatible()) {
    	   map = new GMap2(document.getElementById("map"));
           map.addControl(new GLargeMapControl());
        
           if(${lati}==0)
          map.setCenter(new GLatLng(55.943408,-3.183267), 14);
           else
           map.setCenter(new GLatLng(${lati},${longi}), 14);
           map.setUIToDefault();
           map.enableGoogleBar();
      }
    	}
  
    });
    
    function searchFor(){
    	
    	window.location.href="/tripcaddie/browse/activitybrowser.do?DSearch="+$('#DestinationSearchBox').val();
    }
    </script>
</head>
<body>
<%@ include file="/pages/header2.jsp" %>
<h1>Activity Browser</h1>
<p>Use the Activity Browser to help you get ideas of where to go and what to do. Simply enter a location and click the search button. You can then explore the area on the map by entering terms in the Google search bar at the bottom such as golf courses, restaurants, driving range, attractions, bars, clubs, casino, etc. This should give you some great ideas of things to do on your trip!
</p>
<p><input type="text" id="DestinationSearchBox">&nbsp;&nbsp;<input type="button" value="Search" onclick="searchFor();">
<div id="destnSubtextDiv">Enter a destination you would like to search... (ie. Scottsdale, AZ)</div></p>
 <div id="map" style="display:none; width: 500px; height: 550px; position: relative; background-color: rgb(229, 227, 223);">
 <div style="position: absolute; left: 0px; top: 0px; overflow: hidden; width: 100%; height: 100%;">
 <div style="position: absolute; left: 0px; top: 0px; z-index: 0; cursor: url(http://maps.gstatic.com/intl/en_ALL/mapfiles/openhand_8_8.cur), default;">
 <div style="position: absolute; left: 0px; top: 0px; display: none;"><div style="position: absolute; left: 0px; top: 0px; z-index: 0;">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 <img style="position: absolute; left: 0px; top: 0px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/transparent.png">
 </div></div><div style="position: absolute; left: 0px; top: 0px;"><div style="position: absolute; left: 0px; top: 0px; z-index: 0;">
 <img style="position: absolute; left: -135px; top: -307px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32187&s=&y=20424&z=16&s=G">
 <img style="position: absolute; left: -135px; top: -51px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32187&s=&y=20425&z=16&s=Ga">
 <img style="position: absolute; left: -135px; top: 205px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32187&s=&y=20426&z=16&s=Gal">
 <img style="position: absolute; left: 121px; top: -307px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32188&s=&y=20424&z=16&s=Gali">
 <img style="position: absolute; left: 121px; top: -51px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32188&s=&y=20425&z=16&s=Galil">
 <img style="position: absolute; left: 121px; top: 205px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32188&s=&y=20426&z=16&s=Galile">
 <img style="position: absolute; left: 377px; top: -307px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32189&s=&y=20424&z=16&s=Galileo">
 <img style="position: absolute; left: 377px; top: -51px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32189&s=&y=20425&z=16&s=">
 <img style="position: absolute; left: 377px; top: 205px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32189&s=&y=20426&z=16&s=G">
 <img style="position: absolute; left: 633px; top: -307px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32190&s=&y=20424&z=16&s=Ga">
 <img style="position: absolute; left: 633px; top: -51px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32190&s=&y=20425&z=16&s=Gal">
 <img style="position: absolute; left: 633px; top: 205px; width: 256px; height: 256px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/lyrs=m@205000000&hl=en&src=api&x=32190&s=&y=20426&z=16&s=Gali"></div></div>
 <div style="position: absolute; left: 0px; top: 0px; z-index: 100;"></div><div style="position: absolute; left: 0px; top: 0px; z-index: 101;"></div>
 <div style="position: absolute; left: 0px; top: 0px; z-index: 102;"></div><div style="position: absolute; left: 0px; top: 0px; z-index: 103;"></div>
 <div style="position: absolute; left: 0px; top: 0px; z-index: 104; cursor: default;"></div><div style="position: absolute; left: 0px; top: 0px; z-index: 105;">
 </div><div style="position: absolute; left: 0px; top: 0px; z-index: 106;"></div><div style="position: absolute; left: 0px; top: 0px; z-index: 107; cursor: default;"></div></div></div>
 <div style="z-index: 0; position: absolute; right: 3px; bottom: 2px; color: black; font-family: Arial, sans-serif; font-size: 11px; white-space: nowrap; text-align: right;" dir="ltr">
 <span>Map data ©2013  Google - </span><a href="http://www.google.com/intl/en_ALL/help/terms_maps.html" target="_blank" class="gmnoprint terms-of-use-link" style="color: rgb(119, 119, 204);">Terms of Use</a></div>
 <div style="width: 37px; height: 94px; z-index: 0; position: absolute; left: 7px; top: 7px;" class="gmnoprint"><img style="position: absolute; left: 0px; top: 0px; width: 37px; height: 94px; border: 0px; padding: 0px; margin: 0px;" src="/tripcaddie/gmap/smc.png"><div style="position: absolute; left: 9px; top: 0px; width: 18px; height: 18px; cursor: pointer;" title="Pan up"></div>
 <div style="position: absolute; left: 0px; top: 18px; width: 18px; height: 18px; cursor: pointer;" title="Pan left"></div>
 <div style="position: absolute; left: 18px; top: 18px; width: 18px; height: 18px; cursor: pointer;" title="Pan right"></div>
 <div style="position: absolute; left: 9px; top: 36px; width: 18px; height: 18px; cursor: pointer;" title="Pan down"></div>
 <div style="position: absolute; left: 9px; top: 57px; width: 18px; height: 18px; cursor: pointer;" title="Zoom In"></div>
 <div style="position: absolute; left: 9px; top: 75px; width: 18px; height: 18px; cursor: pointer;" title="Zoom Out"></div></div>
 <div class="gmnoprint" style="z-index: 1; position: absolute; left: 3px; bottom: 5px;"><div>
 <div class="gels"><div id=":0:appId" class="gels-app gels-idle"><div id=":0:popupWrapperId" class="gels-popup-wrapper">
 <div id=":0:adId" class="gels-inlinead"></div><div id="undefined" class="gels-popup"><div id=":0:listId" class="gels-list-wrapper"></div>
 <div id=":0:attributionId" class="gels-attribution gels-attribution-none"></div><div id=":0:controlsId" class="gels-controls"></div></div></div>
 <div id=":0:formWrapperId" class="gels-form gels-form-idle"><div id=":0:formId" class="gels-form-div"><img id=":0:logoId" class="gels-logo" src="/tripcaddie/gmap/logo_66x22.png">
 <input id=":0:inputId" class="gels-input" type="text" name="search" title="search" value="search the map" style="width: 159px;">
 <input id=":0:buttonId" class="gels-button" type="submit" value="Search" title="search"></div></div></div></div></div></div></div>
  <br/>
  <br/><br/><br/><br/><br/><br/>
</body>
</html>