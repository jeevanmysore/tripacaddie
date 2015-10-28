function updateWeatherWidget(state,city){
	
	var i,icon,htemp,ltemp,temp;
	var state1=state.replace(" ","_");
	var city1=city.replace(" ","_");
	var url="http://api.wunderground.com/api/fbe844bcf20b90cd/geolookup/conditions/forecast10day/q/"+state1+"/"+city1+".json";
	
	jQuery.ajax({
		url: url,
		dataType: "jsonp",
		success: function(parsed_json) {
			
			temp=Math.round(parsed_json['current_observation']['temp_f']);
			icon=parsed_json['current_observation']['icon'];
			$('#topIcon').html('<img src="images/top_'+icon+'.png"/>');
			$('#city').text(city+','+state);
			$('#tempF').html(temp+'&deg;');
			for(i=0;i<6;i++){
				icon=parsed_json['forecast']['simpleforecast']['forecastday'][i]['icon'];
				htemp=Math.round(parsed_json['forecast']['simpleforecast']['forecastday'][i]['high']['fahrenheit']);
				ltemp=Math.round(parsed_json['forecast']['simpleforecast']['forecastday'][i]['low']['fahrenheit']);
				if(i==0){
					$('#day'+(i+1)).text('Today');
				}else{
					$('#day'+(i+1)).text(parsed_json['forecast']['simpleforecast']['forecastday'][i]['date']['weekday_short']);
				}
				
				$('#image'+(i+1)).html('<img src="images/'+icon+'.png"/>');
				$('#highTemp'+(i+1)).html(htemp+'&deg;F');
				$('#lowTemp'+(i+1)).html(ltemp+'&deg;F');
			}
			
			//For table
			/*$('#weather_report').empty();
			var append='<tr style="font-weight: bold;"><td colspan="5">'+city +','+state+'</tr><tr>';
			for(i=0;i<6;i++){
				append=append+'<td>'+parsed_json['forecast']['simpleforecast']['forecastday'][i]['date']['weekday_short']+'</td>';
			}
			append=append+'</tr><tr>';
			for(i=0;i<6;i++){
				append=append+'<td>'+parsed_json['forecast']['simpleforecast']['forecastday'][i]['high']['fahrenheit']+'&deg;F </td>';
			}
			append=append+'</tr><tr>';
			for(i=0;i<6;i++){
				append=append+'<td>'+parsed_json['forecast']['simpleforecast']['forecastday'][i]['low']['fahrenheit']+'&deg;F </td>';
			}
			append=append+'</tr>';
			
			$('#weather_report').html(append);*/
		}
	});
}