function submitProfilePersonalForm() {
	if (validateProfile() == true) {
		$('#profileform').submit();
	}
}

function submitProfileAccountForm() {
	 
	if (validateAccount() == true) {		
		if($('#fileuploadError').text()==' ' || $('#fileuploadError').text()==''){
			$('#accountform').submit(); 
		 }
		
	}
}
function validateProfile() {

	var flag = true;
	var filter = /[^a-zA-Z 0-9]+/g;

	var firstname = $('#firstname').val();
	var lastname = $('#lastname').val();
	var nickname = $('#nickname').val();
	var phonenumber = $('#phonenumber').val();
	var city = $('#city').val();
	var golfHandicap = $('#golfHandicap').val();
	var averageScore = $('#averageScore').val();
	var birthdaydate = $('#birthdaydate').val();
	var numbers = /^[0-9]+$/;
	

	if (firstname == "" || firstname == null || filter.test(firstname)) {
		$('#firstnameError').text('invalid firstname');
		flag = false;
	} else {
		$('#firstnameError').text('');
	}

	if (lastname == "" || lastname == null || filter.test(lastname)) {
		$('#lastnameError').text('invalid lastname');
		flag = false;
	} else {
		$('#lastnameError').text('');
	}

	if (filter.test(nickname)) {
		$('#nicknameError').text('invalid nickname');
		flag = false;
	} else {
		$('#nicknameError').text('');
	}

	if (filter.test(phonenumber)) {
		$('#phonenumberError').text('invalid phonenumber');
		flag = false;
	} else if (!numbers.test(phonenumber) && phonenumber !='' ) {
		$('#phonenumberError').text('');
		$('#phonenumberError').text('invalid phonenumber format');
		flag = false;
	} else {

		$('#phonenumberError').text('');
	}

	if (filter.test(city)) {
		$('#cityError').text('invalid city');
		flag = false;
	} else {
		$('#cityError').text('');
	}

	if (!numbers.test(golfHandicap) && golfHandicap !='' ) {
		$('#golfHandicapError').text('Invalid golfHandicap');
		flag = false;
	} else {
		$('#golfHandicapError').text('');
	}
	/*if (birthdaydate == null || birthdaydate == "") {
		$('#birthdaydateError').text('Invalid date or Date is required');
		flag = false;
	} else {
		$('#birthdaydateError').text('');
	}*/
	if (!numbers.test(averageScore) && averageScore != '') {
		$('#averageScoreError').text('Invalid averageScore');
		flag = false;
	} else {
		$('#averageScoreError').text('');
	}
	return flag;
}

function validateAccount() {
	var flag = true;
	var filter = /[^a-zA-Z 0-9]+/g;
	
	var password = $("#password").val();
	var cpassword = $("#cpassword").val();
	var email = $("#emails").val();
	
    if (cpassword != password) {
		flag = false;
		$('#cpasswordError').text(" ");
		$('#cpasswordError').text("Password is not matched");
	} else {
		$('#cpasswordError').text(" ");
	}

	if ($.trim(email).length == 0) {
		flag = false;
		$('#emailErrordiv').text("Invalid Email address ");
	} else if (!validateEmailAddr(email)) {
		flag = false;

		$('#emailErrordiv').text(' ');
		$('#emailErrordiv').text("Invalid Email address ");

	} else {

		$('#emailErrordiv').text(" ");
	}
	return flag;
}
function validateEmailAddr(sEmail) {

	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(sEmail)) {

		return true;
	} else {

		return false;
	}
}


function validateImageUpload()
{
   var extensions = new Array("jpg","jpeg","gif","png","bmp");
   var image_file = document.accountform.fileupload.value;
   var image_length = document.accountform.fileupload.value.length;
   var pos = image_file.lastIndexOf('.') + 1;
   var ext = image_file.substring(pos, image_length);
   var final_ext = ext.toLowerCase();
   for (i = 0; i < extensions.length; i++)
    {
     if(extensions[i] == final_ext)
      {
    	 $('#fileuploadError').text(' ');
    	 return;
    	
      }
   }
   
   $('#fileuploadError').text('Invalid file format');
}
