function displaySignUpForm(){
	jQuery("#signUpPopup").dialog({modal: true,
		resizable: false,
		autoOpen: false,
		height:450,
		width: 500,
		top:-535,
		borderWeight:15,
		borderColor:'0059ff',
		backgroundColor:'FFA500',
		left:'50%'
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
    });
	
	jQuery('#signUpPopup').dialog('open');
	$('#forgetPassword').click(function(){
		jQuery('#signUpPopup').dialog('close');
		displaypasswordPopup();
	});
} 