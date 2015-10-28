<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	<script type="text/javascript">	
		function sendMail(){
			$.get("./forgetPassword.do",{
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
		function displaypasswordPopup(){
			jQuery("#forgetPasswordPopup").dialog({modal: true,
				resizable: false,
				autoOpen: false,
				height: 'auto',
				width: 500,
				top:-535,
				borderWeight:15,
				borderColor:'0059ff',
				backgroundColor:'FFA500',
				left:'50%'
		/* open:function(){ $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar-close").remove();$('.ui-widget-overlay').remove();} */
		});
			jQuery('#forgetPasswordPopup').dialog('open');
			$('#close').click(function(){
				jQuery('#forgetPasswordPopup').dialog('close');
			});
		}
	</script>
	<style type="text/css">
		.popup{
   			 position: absolute;
    		 top: 27%;
			 left: 30%;
			 /*margin: -200px 0 0 -275px;*/
			 width: 550px;
			 background: #fff;
			 border: 3px solid #999999;
			 z-index: 999999
		}
	</style>
</head>
<body>
	<%@ include file="/pages/header.jsp" %>
	<form method="POST" action="<c:url value='/j_spring_security_check'/>" >
    	<table>
    		<tr>
    			<td/>
    			<td align="right">
    				<c:if test="${not empty param.error}">
    					<font color="red">Username or password is wrong</font>
					</c:if> 
    			</td>
    		</tr>
        	<tr>
            	<td align="right">Username</td>
            	<td><input type="text" name="j_username" /></td>
        	</tr>
        	<tr>
            	<td align="right">Password</td>
            	<td><input type="password" name="j_password" /></td>
        	</tr>
	        <tr>
    	        <td colspan="2" align="right">
        	        <input type="submit" value="Login" />
           		</td>
        	</tr>
        	<tr>
        		<td><a href="#" id="forgetPassword" onclick="displaypasswordPopup();">Forget Password</a></td>
        	</tr>
    	</table>
	</form>
	<div id="forgetPasswordPopup" style="display: none;" class="popup">
	<form method="get">
		<p>Enter your Email-id</p>
		<p><input name="email" type="text" id="email" />
		<label id="emailError"></label></p>
		<p><input type="button" value="Yes,Email it to me" onclick="sendMail()"/>
		<input type="button" value="close" id="close" /></p>
	</form>
</div>
</body>
</html>