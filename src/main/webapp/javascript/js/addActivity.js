var tripId;
var startDate;
var endDate;
var minDays;
var maxDays;

function initialize(){
	startDateArray=startDate.split("/");
	endDateArray=endDate.split("/");
	$('#teeTimeBtn').hide();
}

//For Tee Time
function addTeeTime(){
	var type=$('#activityType option:selected').val();
	var title=$('#title').val();
	var date=$('#date').val();
	var timetxt=$('#time').val();
	var pending=$('#pending').is(':checked');
	var url=$('#url').val();
/*	var mapInfo=$('#mapInfo').val();*/
	var comments=$('#comments').val();
	var time=timetxt.split(' ').join(':');
	//alert(time);
	//alert(type);
	if(pending){
		pending=1;
	}else{
		pending=0;
	}
	if(validate()){
		/*query="tripId="+tripId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&mapInfo="+mapInfo+"&comment="+comments+"&pending="+pending;*/
		query="tripId="+tripId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&comment="+comments+"&pending="+pending;
		$.ajax({
			url: "addActivities.do",
			type: "POST",
			async: false,
			data: query,
			success: function(data){
				if(data=="error"){
					window.location.href='/tripcaddie/error.do';
				}else{
					window.location.href='/tripcaddie/trip/teeTime.do?tripId='+tripId;
				}
			}
		});
	}
}

//For Add another Activity
function addAnother(){
	var type=$('#activityType option:selected').val();
	var title=$('#title').val();
	var date=$('#date').val();
	var timetxt=$('#time').val();	
	var pending=$('#pending').is(':checked');
	var url=$('#url').val();
	/*var mapInfo=$('#mapInfo').val();*/
	var comments=$('#comments').val();
	var time=timetxt.split(' ').join(':');
	//alert(time);
	if(pending){
		pending=1;
	}else{
		pending=0;
	}
	if(validate()){
		/*query="tripId="+tripId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&mapInfo="+mapInfo+"&comment="+comments+"&pending="+pending;*/
		query="tripId="+tripId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&comment="+comments+"&pending="+pending;
		$.ajax({
			url: "addActivities.do",
			type: "POST",
			async: false,
			data: query,
			success: function(data){
				if(data=="error"){
					window.location.href='/tripcaddie/error.do';
				}else{
					window.location.href='/tripcaddie/addActivities.do?tripId='+tripId;
				}
			}
		});
	}
}

//For saving activity
function save(){
	
	var type=$('#activityType').val();
	var title=$('#title').val();
	var date=$('#date').val();
	var timetxt=$('#time').val();
	var pending=$('#pending').is(':checked');
	var url=$('#url').val();
	/*var mapInfo=$('#mapInfo').val();*/
	var comments=$('#comments').val();
	var time=timetxt.split(' ').join(':');
	//alert(time);
	if(pending){
		pending=1;
	}else{
		pending=0;
	}
	if(validate()){
		query="tripId="+tripId+"&activityTypeId="+type+"&activityTitle="+title+"&date="+date+"&time="+time+"&url="+url+"&comment="+comments+"&pending="+pending;
		$.ajax({
			url: "addActivities.do",
			type: "POST",
			async: false,
			data: query,
			success: function(data){
				if(data=="error"){
					window.location.href='/tripcaddie/error.do';
				}else{
					window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
				}
			}
		});
	}
}

//For cancel
function cancel(){
	
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}

function validate(){
	
	var flag=true;
	
	var type=$('#activityType option:selected').text();
	var title=$('#title').val();
	var date=$('#date').val();
	var time=$('#time').val();
	var pending=$('#pending').attr('checked');
	//var url=$('#url').val();
	/*var mapInfo=$('#mapInfo').val();*/
	//var comments=$('#comments').val();
	if(pending){
		pending=1;
	}else{
		pending=0;
	}
	if(type == "-Type-"){
		$('#activityTypeError').text("Activity Type is required");
		flag=false;
	}
	
	if(title =="" && title.trim() == ""){
		$('#titleError').text("Activity Title is required");
		flag=false;
	}else{
		$('#titleError').text("");
	}
	
	if(date == ""){
		$('#dateError').text("date is required");	
		flag=false;
	}else{
		$('#dateError').text("");
	}
	
	if(time == "" || time.trim() == ""){
		if(pending==0){
			$('#timeError').text("time is required");
			flag=false;
		}
	}else{
		$('#timeError').text("");
	}
	
	return flag;
}

function caluculateDays(selectedDate){
	//alert(selectedDate)
	var one_day = 1000*60*60*24;
	var date = new Date();
	var dateArray=selectedDate.split("/");
	
	var year=dateArray[2];
	var month=(dateArray[0])-1;
	var day=dateArray[1];
	
	var selecteddate=new Date(year,month,day);
	var diff=(selecteddate.getTime())-(date.getTime());
	return (Math.round(diff/one_day)+1);
}

$(document).ready(function(){
	
	$('#activityType').live("change",function(){
		var op=$('#activityType option:selected').text();
		if(op == 'GOLF'){
			$('#teeTimeBtn').show();
			$('#activityTypeError').text("");
		}else if(op == '-Type-'){
			$('#activityTypeError').text("Activity Type should be selected");
		}else{
			$('#teeTimeBtn').hide();
			$('#activityTypeError').text("");
		}
	});
	
	$('#date').datepicker({
		autoSize:true,
		hideIfNoPrevNext:true,
		minDate:minDays,
		maxDate:maxDays,
		gotoCurrent:true		
	});	
	
	$('#pending').live("click",function(){
		var op=$(this).is(':checked');
		if(op){
			$('#time').attr("disabled","disabled");
		}else{
			$('#time').removeAttr("disabled");
		}
	});
	
	
});

