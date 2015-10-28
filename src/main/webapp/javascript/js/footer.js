
function reportBug(){
	
		addPopupOptions("reportBugPopup");
		$('#emailreportBug').val('');
		$('#IssuereportBug').val('');
		jQuery('#reportBugPopup').dialog('open');
		
	}


function suggestFeature(){
	addPopupOptions("suggestFeaturePopup");
	$('#emailsuggestFeature').val('');
	$('#ideasuggestFeature').val('');
	jQuery('#suggestFeaturePopup').dialog('open');
	
}


function tipYourCaddie(){
	addPopupOptions("tipYourCaddiePopup");
	$('#emailtipYourCaddie').val('');
	 $('#ideatipYourCaddie').val('');
	jQuery('#tipYourCaddiePopup').dialog('open');
	
}



function sendReportBug(){
	if(validateReportBug()){
		jQuery('#reportBugPopup')
		.dialog('close');
		$.post('/tripcaddie/footer/sendReportBug.do',{
			email : $('#emailreportBug').val(),
			issue: $('#IssuereportBug').val()
		},function(data){
			
		});
		
	}
}

function sendSuggestFeature(){
	if(validatesuggestFeature()){
		jQuery('#suggestFeaturePopup')
		.dialog('close');
		$.post('/tripcaddie/footer/sendSuggestFeature.do',{
			email : $('#emailsuggestFeature').val(),
			idea: $('#ideasuggestFeature').val()
		},function(data){
			
		});
		
	}
}

function sendtipYourCaddie(){
	if(validatetipurCaddie()){
		jQuery('#tipYourCaddiePopup')
		.dialog('close');
		$.post('/tripcaddie/footer/sendTipYourCaddie.do',{
			email : $('#emailtipYourCaddie').val(),
			feedback: $('#ideatipYourCaddie').val()
		},function(data){
			
		});
		
	}
}


function addPopupOptions(divid){
	  jQuery('#'+divid).dialog(
		{
			resizable : false,
			autoOpen : false,
			height : 525,
			width : 690,
			open: function () {
          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
      }
		
		});
}



function validatetipurCaddie(){
	
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	var feedback=$('#ideatipYourCaddie').val();
		
	
	if(feedback == "" || feedback==null || feedback.trim()== ""){
	
		$('#ideatipYourCaddieError').text('');
		$('#ideatipYourCaddieError').text('invalid Feedback');
		flag=false;
	}else{
		
		$('#ideatipYourCaddieError').text('');
	}
	
	return flag;
}

function  validatesuggestFeature(){
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	var feedback=$('#ideasuggestFeature').val();
		
	
	if(feedback == "" || feedback==null || feedback.trim()== ""){
	
		$('#ideasuggestFeatureError').text('');
		$('#ideasuggestFeatureError').text('invalid Feedback');
		flag=false;
	}else{
		
		$('#ideasuggestFeatureError').text('');
	}
	
	return flag;
	
}

function validateReportBug(){
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	var feedback=$('#IssuereportBug').val();
		
	
	if(feedback == "" || feedback==null || feedback.trim()== ""){
	
		$('#IssuereportBugError').text('');
		$('#IssuereportBugError').text('invalid Feedback');
		flag=false;
	}else{
		
		$('#IssuereportBugError').text('');
	}
	
	return flag;
}







function customerService(){
	jQuery("#customerServicePopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 750,
		/*top: -1200,*/
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#customerServicePopup').dialog('open');
	$('#closeCustomerService').click(function(){
			jQuery('#customerServicePopup').dialog('close');
	});
}

function aboutTripcaddie(){
	jQuery("#aboutTripCaddiePopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 500,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#aboutTripCaddiePopup').dialog('open');
	$('#close').click(function(){
			jQuery('#aboutTripCaddiePopup').dialog('close');
	});
}

function newsAndPress() {
	jQuery("#news&pressReleasePopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 500,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#news&pressReleasePopup').dialog('open');
	$('#close').click(function(){
			jQuery('#news&pressReleasePopup').dialog('close');
	});
}

function privacyPolicy() {
	jQuery("#privacyPolicyPopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 500,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#privacyPolicyPopup').dialog('open');
	$('#close').click(function(){
			jQuery('#privacyPolicyPopup').dialog('close');
	});
}

function termsOfUser() {
	jQuery("#termsOfUserPopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 500,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#termsOfUserPopup').dialog('open');
	$('#close').click(function(){
			jQuery('#termsOfUserPopup').dialog('close');
	});
}

function tellfriend(){
        
 	jQuery("#tellAFriendPopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 450,
		width: 750,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#tellAFriendPopup').dialog('open');
	$('#close').click(function(){
			jQuery('#tellAFriendPopup').dialog('close');
	});
}

function tripCaddieForBusiness(){
	jQuery("#tripCaddieForBusinessPopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 500,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#tripCaddieForBusinessPopup').dialog('open');
	$('#close').click(function(){
			jQuery('#tripCaddieForBusinessPopup').dialog('close');
	});
}

function advertiseWithUs() {
	jQuery("#advertiseWithUsPopup").dialog({
		resizable: false,
		autoOpen: false,
		height: 500,
		width: 600,
		borderWeight:15,
		left:'50%',
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		open: function () {
	          $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
	      }
	});
	jQuery('#advertiseWithUsPopup').dialog('open');
	$('#close').click(function(){
			jQuery('#advertiseWithUsPopup').dialog('close');
	});
}