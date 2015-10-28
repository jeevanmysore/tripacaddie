<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<script type="text/javascript" src="/tripcaddie/javascript/js/header.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
	
	<script type="text/javascript">
		function displayBucketList(){
			//	window.location.href="./getBucketList.do?places=2";
			$('#redirection').attr('value',"/getBucketList.do?places=2");
			<c:choose>
				<c:when test="${empty sessionScope.username}">
					displayLoginForm();
		 		</c:when>
				<c:otherwise>
					window.location.href="/tripcaddie/getBucketList.do?places=2";	
				</c:otherwise>
			</c:choose>
		}
		function displayUserHome(){
			$('#redirection').attr('value','/userHome.do');
			<c:choose>
				<c:when test="${empty sessionScope.username}">
					displayLoginForm();
				</c:when>
				<c:otherwise>
					window.location.href="/tripcaddie/userHome.do";	
				</c:otherwise>
			</c:choose>
			
		}
		function displayProfile(){
		     window.location.href='/tripcaddie/user/profile.do';
		}
		function createTrip(){
			$('#redirection').attr('value','/createTrip.do?courseId='+courseId);
			<c:choose>
				<c:when test="${empty sessionScope.username}">
					displayLoginForm();
				</c:when>
				<c:otherwise>
					window.location.href="/tripcaddie/createTrip.do?courseId="+courseId;	
				</c:otherwise>
			</c:choose>
		}
	</script>
	<style type="text/css">
		.ui-state-error{
		background-color: lime;
		}
	</style>
	<div style="background-color: gray;">
	<p style="text-align: right;"><a href="/tripcaddie/">Home</a></p>
	<c:set var="username" value= "${sessionScope.username}" scope= "session" />
	<c:choose>
    	<c:when test="${empty sessionScope.username}">
    	
    	  <form method="POST" action="<c:url value='/j_spring_security_check'/>" >
    		<table>
    			<tr>
    				<td/>
    				<td align="right">
    					<c:if test="${not empty param.error}">
    						<font color="red">Username/password may be wrong or your account may not be activated</font>
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
        			<td colspan="2"><a href="#" id="forgetPassword" onclick="displaypasswordPopup();">Forget Password or Username</a></td>
        		</tr>
    		 </table>
			</form>
	
			 <p style="text-align: right;"><input type="button" value="Join Now" onclick="registration();"></p>
   	 	</c:when>
    	<c:otherwise>
     		<%-- <p style="text-align: right;color: blue;">welcome,<c:out value= "${sessionScope.username}"/></p> --%>
     		<p style="text-align: right;color: blue;">welcome,<a href="#" onclick=displayProfile();><c:out value= "${sessionScope.username}"/></a></p>
      		<p style="text-align: right;"><input type="button" value="Logout" onclick="logout();"></p>
    	</c:otherwise>
	</c:choose>
	</div>
	<div id="loginPopup" style="display: none;" >
		<form method="POST" action="<c:url value="/j_spring_security_check" />">
    		<table>
    			<tr>
    				<td colspan="2">Please Login or Register</td>
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
        		<tr><td><input id="redirection" type="hidden" name="spring-security-redirect" /></td></tr>
        		<tr>
        			<td><a href="#" id="forgetPasswordinPopup">Forget Password</a></td>
        			<td>Not a member?<a href="/tripcaddie/registration.do">Register</a></td>
        		</tr>
    		</table>
		</form>
	</div>
	<div id="forgetPasswordPopup" style="display: none;height: 136px;" >
		<form method="get" action="">
			<p>Enter your Email-id</p>
			<p><input name="email" type="text" id="email" />
				<label id="emailError"></label>
			</p>
			<p><input type="button" value="Yes,Email it to me" onclick="sendMail()"/>
			<input type="button" value="close" id="close" /></p>
		</form>
	</div>
