
/*$.mynamespace ={
	row : null,
	rowCount : 65536
}; */
	
function validateEmail(sEmail) {
   	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
   	if (filter.test(sEmail)) {
       	return true;
   	}
   	else{
       	return false;
   	}
}

//$.get(url[,data][,function()]);
function sendMail(){
	$.get("/tripcaddie/forgetPassword.do",{
		email : $('#email').val()
	},
	function(data){
		if(data=="fail"){
			$('#emailError').text("Email is invalid");
		}else{
			jQuery('#forgetPasswordPopup').dialog('close');
		}
	});			
}

$(document).ready(function(){
	$("#email").focusout(function(){
		var email=$('#email').val();
		if ($.trim(email).length == 0) {
			$('#emailError').text("Email address is not valid");
        }
		else if (!validateEmail(email)) {
    		$('#emailError').text("Email address is not valid");
    	}else{
    		$('#emailError').text("");
    	}
	});
});

function logout(){
	$.get("/tripcaddie/logout.do",function(){
		window.location.href="/tripcaddie/";
	});
}

function registration(){
	//alert('hi');
	//$.get("./registration.do");
	window.location.href="/tripcaddie/registration.do";
}

function displayLoginForm(){
	jQuery("#loginPopup").dialog({
	resizable: false,
	autoOpen: false,
	height:350,
	width: 500,
	top:-535,
	borderWeight:15,
	borderColor:'0059ff',
	backgroundColor:'FFA500',
	left:'50%',
	open: function () {
        $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
    }
	/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
});
	jQuery('#loginPopup').dialog('open');
	$('#forgetPasswordinPopup').click(function(){
		jQuery('#loginPopup').dialog('close');
		displaypasswordPopup();
	});
} 

function displaypasswordPopup(){
	jQuery("#forgetPasswordPopup").dialog({
	resizable: false,
	autoOpen: false,
	height:450,
	width: 500,
	top:-535,
	borderWeight:15,
	borderColor:'0059ff',
	backgroundColor:'FFA500',
	left:'50%',
	open: function () {
        $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
    }
	/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
});
	jQuery('#forgetPasswordPopup').dialog('open');
	$('#close').click(function(){
		jQuery('#forgetPasswordPopup').dialog('close');
	});
} 