<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jokes | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.js' type="text/javascript" language="javascript"></script>

 <style type="text/css">
.form-required {
	color: red;
}

</style>
<script type="text/javascript">
	
function validateForm(){
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	var title=$('#title').val();
	var desc=$('#textarea').val();
		
	if(title == "" || title==null || filter.test(title) || title.trim()== ""){
	
		$('#titleError').text('');
		$('#titleError').text('invalid Title');
		flag=false;
	}else{
		
		$('#titleError').text('');
	}
	
	if(desc == "" || desc==null || desc.trim()== ""){
		
		$('#descError').text('');
		$('#descError').text('invalid Description');
		flag=false;
	}else{
		
		$('#descError').text('');
	}
	
	
	return flag;
}

	function save(){
		if(validateForm()){
			$.post("/tripcaddie/jokes/AddJoke.do",{
				jokeName : $('#title').val(),
				Description : $('#textarea').val()
			},function(data){
				window.location.href="/tripcaddie/jokes/recent.do";
			})
		}
		
	}
	function cancel(){
		window.location.href="/tripcaddie/jokes/recent.do";
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<p><h2>Create Joke</h2>
	<p>This content will get posted to the entire tripcaddie community!</p>
	
	<p><label>Title :&nbsp; <span class="form-required">*</span></label><input type="text" id="title"> <div class="form-required" id="titleError"></div></p>
	<p><label>Description : &nbsp;<span class="form-required">*</span></label>
	<textarea id="textarea" rows="4" cols="50"></textarea><div class="form-required" id="descError"></div></p>
	<input type="button" value="save" onclick="save();">
	<input type="button" value="cancel" onclick="cancel();">
	
	
	
</body> 
</html>