//Function select option

var tempHtml,prevOptionTag;

function deleteTeeTime(rowId,teeTimeId) {
	//alert(rowId+" "+teeTimeId);
	
	if(teeTimeId == 0){
		deleteTeeTimeUtil(rowId);
	}else{
		/*$('#teeTimeId-'+rowId).val(0);
		deleteTeeTimeUtil(rowId);*/
		$.post("/tripcaddie/trip/deleteTeeTime.do",{
			teeTimeId : teeTimeId
		},function(data){
			if(data == "success"){
				$('#teeTimeId-'+rowId).val(0);
				deleteTeeTimeUtil(rowId);
			}else{
				window.location.href="/tripcaddie/error.do";
			}
		});
	}
}

function initialize(length){
	var arrayTdTag;
	//var defaultSelectTag,selectHtmlTag;
	var selectHtmlTag;
	var i,j,optionId;
	
	$('#golfSchedule').hide();
	
	//for each for every body
	for(i=1;i<=length;i++){
		
		$('#body'+i+" > tr").each(function() {
			j=0;
			$('#'+this.id+" > td").each(function() {
				
				arrayTdTag = (this.id).split('-');
				selectHtmlTag='player'+j+'-'+arrayTdTag[1]+'-'+arrayTdTag[2];
				optionId = $('#'+selectHtmlTag+' option:selected').val();
				
				if(optionId != -1 ){
					removeOption(selectHtmlTag, i,optionId);
				}
				j++;
			});
		});
	}	
}


function setOPtion(selectId,option){
	$('#'+selectId).val(option).attr('selected',true);
}

function remove_player(selectTagId,index){
	
	var selectedOptionTagOfOther;
	var arrayTdTag,i,selectHtmlTag,arrayTrTag;
	var arraySelectTag = selectTagId.split('-');
	
	var optionId = $("#"+selectTagId+" option:selected").val();
	//optionTag make it as selected which is selected
	var selectedOptionTagOfCurrent=null;
	
	if(optionId != -1){
		//remove element
		selectedOptionTagOfCurrent=makeOptionTag(selectTagId);
		$('#'+selectTagId+' > option').each(function(){
			unselectOption(selectTagId);
			if(this.id == optionId){
				$('#'+selectTagId+' option[value = '+this.id+']').remove();
				tempHtml=$('#'+selectTagId).html();
			}		
		});
	}else{
		//Add Element
		unselectOption(selectTagId);
		$('#'+selectTagId).append(prevOptionTag);
		/*$('#'+selectTagId+" > option").each(function(){
			if(this.id == -1){
				
			}
		});*/
		tempHtml=$('#'+selectTagId).html();
	}
	
	//alert(tempHtml);
		
	//Set option to all dropdown 
	
	$("#body"+arraySelectTag[2]+" > tr").each(function(){                                 //To get Row
		i=0;
		arrayTrTag=(this.id).split('-');
		//$('#'+arrayTrTag[0]+'-'+arrayTrTag[1]+" > td").each(function(){                       //To get column
		$('#'+this.id+" > td").each(function(){
			arrayTdTag=(this.id).split('-');
			//alert(arrayTdTag);
			selectHtmlTag='player'+i+'-'+arrayTdTag[1]+'-'+arrayTdTag[2];
			//alert(selectHtmlTag);
			//Need to add more
			if(selectHtmlTag != selectTagId){
				if($("#"+selectHtmlTag+" option:selected").val() != -1){
					selectedOptionTagOfOther=makeOptionTag(selectHtmlTag);
					$('#'+selectHtmlTag).html(tempHtml);
					$('#'+selectHtmlTag).append(selectedOptionTagOfOther);
				}else{
					$('#'+selectHtmlTag).html(tempHtml);
				}
				
			}else{
				$('#'+selectHtmlTag).html(tempHtml);
				if(selectedOptionTagOfCurrent != null){
					$('#'+selectHtmlTag).append(selectedOptionTagOfCurrent);
				}
			}
			i++;
		});
	});
}

function setPrev(selectTagId){
	
	var optionId = $("#"+selectTagId+" option:selected").val();
	var optionText = $("#"+selectTagId+" option:selected").text();
	
	optionTag = document.createElement('option');
	optionTag.setAttribute("id",optionId);
	optionTag.setAttribute("value", optionId);
	
	optionTag.innerHTML=optionText;
}

function makeOptionTag(selectTagId){
	
	var optionId = $("#"+selectTagId+" option:selected").val();
	var optionText = $("#"+selectTagId+" option:selected").text();
	
	optionTag = document.createElement('option');
	optionTag.setAttribute("id",optionId);
	optionTag.setAttribute("value", optionId);
	optionTag.setAttribute("selected", "selected");
	optionTag.innerHTML=optionText;
	
	return optionTag;
}

function unselectOption(selectTag) {
	
	$('#'+selectTag+" > option").each(function(){
		$('#'+selectTag+' option[value = '+this.id+']').removeAttr('selected');
	});
	
}

function removeOption(selectTag,item,optionId){
	var j;
	$('#body'+item+" > tr").each(function() {
		j=0;
		$('#'+this.id+" > td").each(function() {
			
			arrayTdTag = (this.id).split('-');
			selectHtmlTag='player'+j+'-'+arrayTdTag[1]+'-'+arrayTdTag[2];
			
			if(selectTag != selectHtmlTag){
				$('#'+selectHtmlTag+' option[value = '+optionId+']').remove();
			}
			
			j++;
		});
	});
}

function deleteTeeTimeUtil(rowId) {
	var j;
	var arrRowId = rowId.split('-');
	
	$('#body'+arrRowId[1]+' > tr').each(function(){
		j=0;
		$('#'+this.id+" > td").each(function(){
			arrayTdTag = (this.id).split('-');
			selectHtmlTag = 'player'+j+'-'+arrayTdTag[1]+'-'+arrayTdTag[2];
			//alert(selectHtmlTag);
			setPrev(selectHtmlTag);
			unselectOption(selectHtmlTag);
			$('#'+selectHtmlTag+' option:first').attr('selected','selected');
			remove_player(selectHtmlTag, arrRowId[2]);
			j++;
		});
		
	});
	
	
}