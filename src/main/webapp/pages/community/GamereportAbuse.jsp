<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Games | TripCaddie</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src='/tripcaddie/javascript/js/jquery.js' type="text/javascript" language="javascript"></script>

 <style type="text/css">
.form-required {
	color: red;
}

</style>
<script type="text/javascript">
	
	var gameId;
	var userId;
	
function validateForm(){
	var flag=true;
	var filter= /[^a-zA-Z 0-9]+/g;
	var reason=$('#reason').val();
	var message=$('#textarea').val();
		
	if(reason==-1){
	
		$('#reasonError').text('');
		$('#reasonError').text('reason not selected');
		flag=false;
	}else{
		
		$('#reasonError').text('');
	}
	
	if(message == "" || message==null || message.trim()== ""){
		
		$('#messageError').text('');
		$('#messageError').text('invalid Message');
		flag=false;
	}else{
		
		$('#messageError').text('');
	}
	
	
	return flag;
}

	function save(){
		if(validateForm()){
			$.post("/tripcaddie/games/reportAbuse.do",{
				gameId : gameId,
				userId:userId,
				reason : $('#reason').val(),
				message : $('#textarea').val()
			},function(data){
				window.location.href="/tripcaddie/games/recent.do";
			})
		}
		
	}
	function cancel(){
		window.location.href="/tripcaddie/games/recent.do";
	}
</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<script type="text/javascript">
	gameId=${game.gameId};
	userId=${user.userId};
	</script>
	
	<p><h2>Report Abuse</h2>
	<p>from : &nbsp;  ${user.firstName }  ${user.lastName }</p>
	<p>about : &nbsp;  ${game.gameName }</p>
	<p><label>Reason :&nbsp; <span class="form-required">*</span></label><select id="reason">
	<option value="-1">Select</option>
	<c:forEach var="reason" items="${reasons}" varStatus="items">
	<option value="${reason.id }">${reason.reason }</option>
	</c:forEach>
	</select><div class="form-required" id="reasonError"></div>
	</p>
	<p><label>message : &nbsp; <span class="form-required">*</span></label>
	<textarea id="textarea" rows="4" cols="50"></textarea><div class="form-required" id="messageError"></div></p>
	<input type="button" value="send" onclick="save();">
	<input type="button" value="cancel" onclick="cancel();">
	
	
	
</body> 
</html>