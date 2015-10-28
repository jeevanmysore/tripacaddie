function updateRow(data){
	//alert(data.jsonObject.recentUpdates.length);
	$('#updates').empty();
	var trTag,append,golfHandiCap='HCI?';
	var hour,ampm,i;
	var recentUpdate=data.jsonObject.recentUpdates;
	for(i=0;i<recentUpdate.length;i++){
		
		if(recentUpdate[i].updatedDate.time.hours >=12){
			ampm='pm';
		}else{
			ampm='am';
		}
		if(recentUpdate[i].updatedDate.time.hours > 12){
			hour= recentUpdate[i].updatedDate.time.hours -12;
		}else{
			hour= recentUpdate[i].updatedDate.time.hours;
		}
		hour=recentUpdate[i].updatedDate.time.hours-12;
		if(recentUpdate[i].tripMemberDto.tripCaddieUserDto.golfHandicap != null && recentUpdate[i].tripMemberDto.tripCaddieUserDto.golfHandicap != 0 ){
			golfHandiCap=recentUpdate[i].tripMemberDto.tripCaddieUserDto.golfHandicap;
		}
		trTag=document.createElement("tr");
		append='<td>'+recentUpdate[i].tripMemberDto.tripCaddieUserDto.firstName+' '+recentUpdate[i].tripMemberDto.tripCaddieUserDto.lastName +'('+golfHandiCap+')' +'</td>'
				+'<td>'+recentUpdate[i].message+'</td>'
				+'<td>'+recentUpdate[i].updatedDate.time.month+'/'+recentUpdate[i].updatedDate.time.date+'/'+(recentUpdate[i].updatedDate.time.year+1900)
				+' '+hour+':'+recentUpdate[i].updatedDate.time.minutes+' '+ampm+'</td>';
		
		trTag.innerHTML=append;
		document.getElementById('updates').appendChild(trTag);
		
	}
}

function sendAjaxRequest(type,order){
	
	$.post("/tripcaddie/getRecentUpdates.do",{
		tripId : tripId, 
		type : type,
		order : order
	},function(data){
		updateRow(data);
	});
	
}

function sortDescByWhen(){
	
	$('#when').attr("src","images/arrow-asc.png");
	$('#who').attr("src","images/transparent_bg.png");
	$('#what').attr("src","images/transparent_bg.png");
	$('#when').attr("onclick","sortAscByWhen()");
	$('#who').attr("onclick","sortDescByWho()");
	$('#what').attr("onclick","sortDescByWhat()");
	
	sendAjaxRequest('when','desc');
}

function sortAscByWhen(){
	
	$('#when').attr("src","images/arrow-desc.png");
	$('#who').attr("src","images/transparent_bg.png");
	$('#what').attr("src","images/transparent_bg.png");
	$('#when').attr("onclick","sortDescByWhen()");
	$('#who').attr("onclick","sortDescByWho()");
	$('#what').attr("onclick","sortDescByWhat()");
	
	sendAjaxRequest('when','asc');
}

function sortDescByWho(){
	$('#when').attr("src","images/transparent_bg.png");
	$('#who').attr("src","images/arrow-asc.png");
	$('#what').attr("src","images/transparent_bg.png");
	$('#when').attr("onclick","sortDescByWhen()");
	$('#who').attr("onclick","sortAscByWho()");
	$('#what').attr("onclick","sortDescByWhat()");
	
	sendAjaxRequest('who','desc');
}

function sortAscByWho(){
	$('#when').attr("src","images/transparent_bg.png");
	$('#who').attr("src","images/arrow-desc.png");
	$('#what').attr("src","images/transparent_bg.png");
	$('#when').attr("onclick","sortDescByWhen()");
	$('#who').attr("onclick","sortDescByWho()");
	$('#what').attr("onclick","sortDescByWhat()");
	
	sendAjaxRequest('who','asc');
}

function sortDescByWhat(){
	$('#when').attr("src","images/transparent_bg.png");
	$('#who').attr("src","images/transparent_bg.png");
	$('#what').attr("src","images/arrow-asc.png");
	$('#when').attr("onclick","sortDescByWhen()");
	$('#who').attr("onclick","sortDescByWho()");
	$('#what').attr("onclick","sortAscByWhat()");
	
	sendAjaxRequest('what','desc');
}

function sortAscByWhat(){
	$('#when').attr("src","images/transparent_bg.png");
	$('#who').attr("src","images/transparent_bg.png");
	$('#what').attr("src","images/arrow-desc.png");
	$('#when').attr("onclick","sortDescByWhen()");
	$('#who').attr("onclick","sortDescByWho()");
	$('#what').attr("onclick","sortDescByWhat()");
	
	sendAjaxRequest('what','asc');
}


