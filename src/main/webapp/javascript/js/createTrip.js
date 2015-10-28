var mindays=0;
var filter= /[^a-zA-Z 0-9]+/g;
var filter1= /[^a-zA-Z 0-9]+/g;
var filter2= /[^a-zA-Z\s 0-9]+/;

function fileSize(){
	var browserInfo = navigator.userAgent.toLowerCase();
	//alert("Info: "+browserInfo);
	if(browserInfo.indexOf("msie") > -1){
        /* IE */
        var filepath = document.getElementById('image').value;
       // alert(filepath + " Test ");
        var myFSO = new ActiveXObject("Scripting.FileSystemObject");
        var thefile = myFSO.getFile(filepath);
        var imgbytes = thefile.size;
       // alert( "name " +  thefile.name + "Size " +  thefile.size );
    }else{
        /* Other */
    	//alert('check:'+document.getElementById('tripForm'));
        var file = document.getElementById('image').files[0];
       // alert( "name " +  file.name + "Size " +  file.size );
        if(file.size > 300000){
        	$('#fileuploadError').text("File size should be less than 3Mb");
        }else{
        	$('#fileuploadError').text("")
        }
    }
}

$(function(){
	$('#startDatePicker').datepicker({
		changeYear:true,
		yearRange:"1900:2150",
		autoSize:true,
		hideIfNoPrevNext:true,
		minDate:0,
		gotoCurrent:true,
		onSelect : function(selectedDate){
			var one_day = 1000*60*60*24;
			var date = new Date();
			var dateArray=selectedDate.split("/");
			
			var year=dateArray[2];
			var month=(dateArray[0])-1;
			var day=dateArray[1];
			
			var selecteddate=new Date(year,month,day,date.getHours(),date.getMinutes(),date.getSeconds());
			var diff=(selecteddate.getTime())-(date.getTime());
			//$.mynamespace.mindays=$.mynamespace.mindays+Math.round(diff/one_day);
			//mindays=mindays+Math.round(diff/one_day);
			mindays=Math.round(diff/one_day);
			//alert(mindays);
			$('#endDatePicker').val('');
			$('#endDatePicker').datepicker("option","minDate",mindays);
			$('#endDatePicker').datepicker("option","maxDate",mindays+25);
			
		}
	});
	
	$('#endDatePicker').datepicker({
		changeYear:true,
		yearRange:"1900:2150",
		autoSize:true,
		hideIfNoPrevNext:true,
		minDate:0,
		gotoCurrent:true,
		onSelect : function(selectedDate){
			mindays=0;
		}
					
	});
});

$(document).ready(function(){
	$('#tripName').focusout(function() {
		var tripname=$('#tripName').val();
		if(filter2.test(tripname)){
			$('#tripnameError').text("Invalid Text");
		
		}else if(tripname == "" || tripname == null || tripname.trim()== ""){
			
			$('#tripnameError').text("Invalid Text");
		
		}else
			{
			$('#tripnameError').text("");
		
		}
	});
	
	/*$('#welcomeMessage').focusout(function(){
		var message=$('#welcomeMessage').val();
		if(filter1.test(message)){
			$('#messageError').text("Invalid Text");
		}else
    	if(message.indexOf(" ") != -1 ){
    		$('#messageError').text("Invalid Text");
    	}else if(message.length > 500 ){
    		$('#messageError').text("Maximum 500 characters only allowed");
    	}else{
    		$('#messageError').text("");
    	}
	});*/
	/*$('#startDatePicker').focusout(function(){
		var startdate=$('#startDatePicker').val();
		alert(startdate);
		if(startdate == "" || startdate == null || startdate.indexOf(" ") != -1 ){
    		$('#startDateError').text("Invalid Text");
    	}else{
    		$('#startDateError').text("");
    	}
	});*/
	/*$('#endDatePicker').focusout(function(){
		var enddate=$('#endDatePicker').val();
		if(enddate == "" || enddate == null || enddate.indexOf(" ") != -1 ){
    		$('#endDateError').text("Invalid Text");
    	}else{
    		$('#endDateError').text("");
    	}
	});*/
	$('#image').live('change',function(){
		validateImageUpload();			
	});
	
});
function validateImageUpload()
{
   var extensions = new Array("jpg","jpeg","gif","png","bmp");
   var image_file = $('#image').val();
   var image_length = image_file.length;
   var pos = image_file.lastIndexOf('.') + 1;
   var ext = image_file.substring(pos, image_length);
   var final_ext = ext.toLowerCase();
  
   fileSize();   
  
   for (i = 0; i < extensions.length; i++)
    {
     if(extensions[i] == final_ext)
      {
    	 $('#fileuploadError').text('');
    	 return true;
      }
   }
   
   $('#fileuploadError').text('Invalid file format');
   		return false;
   
}

/*function ajaxUpload(){
	/*$.post("/tripcaddie/fileSizeValidation.do",{image:image},function(data){
		alert(data);
	});*/
//	var queryString=$('#tripForm').formSerialize();
//	$.ajax({
//		type : "POST",
//		contentType:"multipart/form-data",
//		url : "/tripcaddie/fileSizeValidation.do",
//		data : queryString,
//		success:function(data){
//			alert(data);
//		},
//		error : function(data){
//			alert("in error:"+data);
//		}
//		
//	});
	/*$.ajaxFileUpload
    (
        {
            url:'/tripcaddie/fileSizeValidation.do',
            secureuri:false,
            fileElementId:'image',
            dataType: 'jsonp',
            success: function (data, status)
            {
            	alert(data);
            	if(typeof(data.error) != 'undefined')
                {
                    if(data.error != '')
                    {
                        alert(data.error);
                    }else
                    {
                        alert(data.msg);
                    }
                }
            },
            error: function (data, status, e)
            {
                alert(e);
            }
        }
    );*/
//}



function validate(){
	
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	
	var tripname=$('#tripName').val();
	var startDatePicker=$('#startDatePicker').val();
	var endDatePicker=$('#endDatePicker').val();
	var welcomeMessage=$('#welcomeMessage').val();
	
	if(tripname == "" || tripname==null || filter2.test(tripname) || tripname.trim()== ""){
		$('#tripnameError').text('');
		$('#tripnameError').text('invalid tripname');
		flag=false;
	}else{
		$('#tripnameError').text('');
	}
	if(endDatePicker == null || endDatePicker == "" ){
		$('#endDateError').text('');
		$('#endDateError').text('Invalid date or Date is required');
		flag=false;
	}else{
		$('#endDateError').text('');
	}
	if(startDatePicker == null || startDatePicker =="" ){
		$('#startDateError').text('');
		$('#startDateError').text('Invalid date or Date is required');
		flag=false;
	}else{
		$('#startDateError').text('');
	}	
	/*if(filter.test(welcomeMessage) || welcomeMessage.indexOf(" ") != -1){
		$('#messageError').text('invalid message');
		flag=false;
	}else{
		$('#messageError').text('');
	}*/
	if($('#fileuploadError').text()!="")
	{
		flag=false;
	}
//	alert(flag);
	return flag;
}

function submitForm(){
	if(validate() == true){
		$('#tripForm').submit();
	}
}

function cancel(){
	window.location.href='./userHome.do';
}
