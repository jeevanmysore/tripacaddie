<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
 
<html lang="en">
<head>
       
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <!-- <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" /> -->
    <link rel="stylesheet" href="/tripcaddie/css/jQueryUI.css" type="text/css" />
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true&libraries=places"></script>
    <style>
    .ui-autocomplete { height: 200px ; overflow: auto;}
    
    .ui-autocomplete-loading {
        background: white url('images/ui-anim_basic_16x16.gif') right center no-repeat;
    }
    #city { width: 25em; }
     #golfCourse { width: 25em; }
  	
  	/* .titleColor{
  		background-color: rgba(226, 250, 215, 0.81);  		
  	} */
    </style>
    <script>
		   var arr = [];
		   
		   var map;
		   var service;
		   var infowindow;
		   var golfCourseArray = [];
		   var resultcount;
		   
		   $(function() {   	
		   	       
		       $( "#city" ).autocomplete({
		           source: function( request, response ) {
		               $.ajax({
		                   url: "http://ws.geonames.org/searchJSON",
		                   dataType: "jsonp",
		                   data: {
		                       featureClass: "P",
		                       style: "full",
		                       maxRows: 12,
		                       name_startsWith: request.term
		                   },
		                   success: function( data ) {
		                   	 resultcount=0;
		                   	arr = [];
		                   	golfCourseArray = [];
		                   	$('#golfCourseList').empty();
		                       response( $.map( data.geonames, function( item ) {
		                       	 arr.push({
		                       	        key: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
		                       	        value:  item.lat+","+item.lng
		                       	    });
		                           return {
		                               label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
		                               value: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName
		                           }
		                       }));
		                   }
		               });
		           },
		           minLength: 2,
		           select: function( event, ui ) {
		           	if(ui.item){
		           		for (i in arr) {
		           			if(ui.item.label==arr[i].key){
		           			
		           			var arg=arr[i].value.split(",");
		           			golfCourseAutocomplt(arg[0] ,arg[1] );
		           			}
		           		}
		         	
		           	}
		               
		           },
		           open: function() {
		               $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		           },
		           close: function() {
		               $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		           }
		       });
		   });
		   
		  
		   function golfCourseAutocomplt(lat,lng){
		   	
		   	initialize(lat,lng);
		   	      
		       $( "#golfCourse" ).autocomplete({
		           source: golfCourseArray,
		           minLength: 2,
		           select: function( event, ui ) {
		           	if(ui.item){
		           		$('#SelectedGolfcourse').text('');
		           		$('#SelectedGolfcourse').text('Golf course selected :'+ui.item.label);
		           	
		           	}
		               
		           }
		
		       });
		   	
		   }
		   
		   function initialize(lat,lng) {
		   	
		   	var latitude=parseFloat(lat);
		   	var longitude=parseFloat(lng);
		     var pointlat = new google.maps.LatLng(latitude,longitude);
		
		     map = new google.maps.Map(document.getElementById('map'), {
		         mapTypeId: google.maps.MapTypeId.ROADMAP,
		         center: pointlat,
		         zoom: 15
		       });
		
		     var request = {
		       location:pointlat,
		       radius: '5500',
		       query: 'golfcourse'
		     };
		
		     service = new google.maps.places.PlacesService(map);
		     service.textSearch(request, callback);
		     
		   }
		
		   function callback(results, status ,pagination) {
		   	$('#loadingImg').show();
		     if (status == google.maps.places.PlacesServiceStatus.OK) {
		   	  
		       for (var i = 0; i < results.length; i++) {
		         var place = results[i].name;
		       
		         golfCourseArray.push(results[i].name);
		       
		       }
		       
		       resultcount=resultcount+results.length;
		       
		 	 
		     }
		     if (!pagination.hasNextPage) {
		   	  $('#loadingImg').hide();
		   	  $('#resultDiv').text('');
		         $('#resultDiv').text(resultcount+' results found');
		     }
		     
		     if (pagination.hasNextPage) {
		        
		           pagination.nextPage();
		        
		       }
		    
		     if (status == google.maps.places.PlacesServiceStatus.ZERO_RESULTS) {
		   	  $('#resultDiv').text('');
		   	  $('#resultDiv').text('no results found');
		     }
		    
		   }
		  
		   function displayInGolfCourse(golfCourse){
		   	
		   	$('#SelectedGolfcourse').text('Golf course selected:'+$('#'+golfCourse.id).text());
		   	$('#golfCourse').val($('#'+golfCourse.id).text());
		   	jQuery('#golfCoursePopup').dialog('close');
		   }
		   
		   function listGC(){
			addPopupOptions("golfCoursePopup");
			golfCourseArray.sort();
			for(var i=0;i<golfCourseArray.length;i++){
			  $('#golfCourseList').append('<br>');
		         $('#golfCourseList').append('<a href="#" id="gc-'+i+'" onclick="displayInGolfCourse(this)">'+golfCourseArray[i]+'</a>');
		         $('#golfCourseList').append('<br>');
			}
			
			jQuery('#golfCoursePopup').dialog('open');
			
		}
		function addPopupOptions(divid){
		  jQuery('#'+divid).dialog({
			  resizable : false,
			  autoOpen : false,
			  height : 325,
			  width : 490,
			 /*  open: function (){
		         			$(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("titleColor");
		         			$(this).parents(".ui-dialog:first").find(".ui-widget-header").removeAttr("background");
		       	  } */
		  });
		}
    </script>
</head>
<body>
 <%@ include file="/pages/header2.jsp" %>
<div class="ui-widget">
<div id="map" style="display: none;"></div>
<br>
<br>
  <p>  <label for="city">Your city: </label>
    <input id="city" /></p>
    <p>
    <label for="golfCourse">Golf course: </label>
    <input id="golfCourse" /></p><img id="loadingImg" src="/tripcaddie/images/loading3.gif" style="display: none;"/><a onclick="listGC();" href="#" id="resultDiv"></a>
    <div id="SelectedGolfcourse"></div>
  
</div>
 
	<div id="golfCoursePopup" style="display: none;">
	<div id="golfCourseList"></div>
	</div>
 
 
</body>
</html>