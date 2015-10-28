<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Strudel | TripCaddie</title>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<!-- <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script> -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<style type="text/css">
		.form-required{
			color: red;
			font-size: 8pt;
		}
		.ui-state-error{
		background-color: lime;
		}
	</style>
<script type="text/javascript">
$(function(){
	$('#Expensedate').datepicker({
		autoSize:true
				
	});
});

function displayTripHome(){
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}
function back(){
	window.history.back();
}
function openlbLock(){
	$.post('/tripcaddie/trip/strudel/updateLock.do',{
		stlock : 1,
		tripId : tripId
	},function(data){
		if(data=="success"){
			window.location.href="/tripcaddie/trip/strudel/getStrudelBS.do?tripId="+tripId;
		}else{
			
		}
	});
}

function closelbLock(){
	
	$.post('/tripcaddie/trip/strudel/updateLock.do',{
		stlock : 0,
		tripId : tripId
	},function(data){
		if(data=="success"){
			window.location.href="/tripcaddie/trip/strudel/getStrudelBS.do?tripId="+tripId;
		}else{
			
		}
	});
}
function tripvideo(id){
	window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
}

</script>
</head>
<body>
	<%@ include file="/pages/header2.jsp" %>
	<h3>Strudel - Balance Sheet</h3> <input type="button" value="Back" onclick="back();" /><input type="button" value="Return Trip Home" onclick="displayTripHome();" />
	<p>Welcome to the TripCaddie Strudel! The Strudel is your trips accounting module that reconciles and tracks all of the group's finances so that everyone that attends the trip has an even cash outlay. The Strudel will calculate the group's expenses, side bets (wagers) and winnings so each participant in the group can see how much he owes or is owed. 

Please note: Each trip participant should enter their shareable expenses and the Trip Leader has special privileges to edit the Strudel. Remember, all trip participants must be on TripCaddie and have accepted the trip invitation for this work most effectively.</p>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	
	<!-- 
	 Trip Details....
	 -->
	<table style="width: 45%;">
	<tr>
	<td>
	<img alt='Embedded Image' width='180px' height='150px' src=data:image/png;base64,${trip.imagebase64} /></td>
	<td><h2><a href="#" style="font-weight: bold;" onclick="tripvideo(${trip.tripId});">${trip.tripName }</a></h2>
	${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }
	<fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long" /> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/>
	</td>
	</tr>
	</table>
	Strudel Status :
	<c:choose>
			<c:when test="${trip.strudellock == 0 }">
				opened( <a href="#" onclick="openlbLock();" >Lock</a>)
					
				</c:when>
				<c:otherwise>
				Locked( <a href="#" onclick="closelbLock();">Unlock</a>)
				</c:otherwise>
</c:choose>
	
	 <c:if test="${empty trip.wagerFee}">
		   <input type="button" value="Add Wager" onclick="openNewWagerPopup();" />
		   </c:if>
		  <c:if test="${not empty trip.wagerFee}">
		 <input type="button" value="Edit Wager" onclick="openEditWagerPopup(${trip.wagerFee});" />
		   </c:if>

			
	<br/><br/>
	
	<div id="StrudelBody">
	<div id="hiddenvaluestore" style="display: none;"></div>
	<table >
	<tr>
	<td><table id="default" style="width='${TableSize}%' ">
	<tr><td> </td></tr>
	</table></td>
	
	<c:forEach var="tripmember" items="${tripmembers}" varStatus="items">
	<td><table id="${tripmember.memberId}" width="${TableSize}%">
	</table></td>
	</c:forEach>
	</tr>
	</table>
	</div>
	
	<div id="expensePopup" style="display: none;">
	<br/><br/>
		
		<form id="expenseForm" action="createExpense.do" method="post">

			<input type="hidden" name="tripId" value="${trip.tripId}"
				id="hiddentripId"> 
				<input type="hidden" name="memberId" 	value="" id="hiddenmemberId"> 
				<input type="hidden" name="expenseId" 	value="" id="expenseId"> 
				<p><label>Title : </label><span class="form-required">*</span>&nbsp;&nbsp;&nbsp;
				<input type="text" id="title" name="title"><span
						id="titleError" class="form-required"></span></p>
				
				<p>
					<label>Date:</label><span class="form-required">*</span>
					&nbsp;&nbsp;&nbsp;<input type="text" id="Expensedate"
						name="Expensedate" readonly="readonly" /> <span
						id="ExpensedateError" class="form-required"></span>
				</p>
				
				<p>
					<label>Amount:</label><span class="form-required">*</span>
					&nbsp;&nbsp;&nbsp;<input type="text" id="ExpenseAmount" onkeypress="return onlyNumbers();"
						name="ExpenseAmount"  /> <span
						id="ExpenseAmountError" class="form-required"></span>
				</p>
								
				
				<br/><br/>	
			<input type="button" value="Submit" onclick="submitexpenseForm();">
			<input type="button" value="Cancel" onclick="cancelexpensePopup();">	<br/><br/>
		</form>
	</div>
	
	
	<!-- Winning Popup 
	 -->
	
	<div id="WinningPopup" style="display: none;">
	<br/><br/>
		
		<form id="WinningForm" action="createWinnings.do" method="post">

			<input type="hidden" name="tripId" value="${trip.tripId}"
				id="hiddentripId"> 
				<input type="hidden" name="memberId" 	value="" id="hiddenmemberIdWinningPopup"> 
			<p>
					<label>Amount:</label><span class="form-required">*</span>
					&nbsp;&nbsp;&nbsp;<input type="text" id="winningAmount" onkeypress="return onlyNumbers();"
						name="winningAmount"  /> <span
						id="winningAmountError" class="form-required"></span>
				</p>
								
				
				<br/><br/>	
			<input type="button" value="Submit" onclick="submitWinningForm();">
			<input type="button" value="Cancel" onclick="cancelWinningPopup();">	<br/><br/>
		</form>
	</div>
	
	<!-- Edit winning popup -->
	
	<div id="editWinningPopup" style="display: none;">
	<br/><br/>
		
		<form id="editWinningForm" action="editWinnings.do" method="post">

			<input type="hidden" name="tripId" value="${trip.tripId}"
				id="hiddentripId"> 
				<input type="hidden" name="WinningId" 	value="" id="WinningId"> 
			<p>
					<label>Amount:</label><span class="form-required">*</span>
					&nbsp;&nbsp;&nbsp;<input type="text" id="winningAmountEdit" onkeypress="return onlyNumbers();"
						name="winningAmount"  /> <span
						id="winningAmountEditError" class="form-required"></span>
				</p>
								
				
				<br/><br/>	
			<input type="button" value="Submit" onclick="submitEditWinningForm();">
			<input type="button" value="Cancel" onclick="cancelEditWinningPopup();">	<br/><br/>
		</form>
	</div>
	
	
	
	
	<!-- Wager Popup 
	 -->
	
	<div id="WagerPopup" style="display: none;">
	<br/><br/>
		
		<form id="WagerForm" action="createWager.do" method="post">

			<input type="hidden" name="tripId" value="${trip.tripId}"
				id="hiddentripId"> 
				
			<p>
					<label>Amount:</label><span class="form-required">*</span>
					&nbsp;&nbsp;&nbsp;<input  type="text" id="wagerAmount" onkeypress="return onlyNumbers();"
						name="wagerAmount"  /> <span
						id="wagerAmountError" class="form-required"></span>
				</p>
								
				
				<br/><br/>	
			<input type="button" value="Submit" onclick="submitWagerForm();">
			<input type="button" value="Cancel" onclick="cancelWagerPopup();">	
			<input id="deleteWager" type="button" value="Delete" onclick="DeleteWagerPopup();"><br/><br/>
		</form>
	</div>
	
	
	
	<script type="text/javascript">
	
	
	function onlyNumbers(evt)
	{
	       var e = event || evt; // for trans-browser compatibility
	       var charCode = e.which || e.keyCode;

	       if (charCode > 31 && (charCode < 48 || charCode > 57))
	               return false;

	       return true;

	}
	
	function openNewWinningPopup(memberId){
		addPopupOptions("WinningPopup");
		jQuery('#WinningPopup').dialog('open');
		$('#hiddenmemberIdWinningPopup').val(memberId);
	}
	function openNewWagerPopup(){
		addPopupOptions("WagerPopup");
		jQuery('#WagerPopup').dialog('open');
		$('#deleteWager').hide();
		
	}
	function openEditWagerPopup(amount){
		addPopupOptions("WagerPopup");
		jQuery('#WagerPopup').dialog('open');
		$('#wagerAmount').val(amount);
	}
	function openEditWinningPopup(WinningId,amount){
		addPopupOptions("editWinningPopup");
		jQuery('#editWinningPopup').dialog('open');
		$('#WinningId').val(WinningId);
		$('#winningAmountEdit').val(amount);
	}
	
	
	function openCreateExpensPopup(memberId){
		
		addPopupOptions("expensePopup");
		jQuery('#expensePopup').dialog('open');
		
		$('#hiddenmemberId').val(memberId);
		
		
		}
	function openEditExpensPopup(expenseId){
		$.post('/tripcaddie/trip/strudel/getExpense.do',{
			expenseId : expenseId
		},function(data){
			
			addPopupOptions("expensePopup");
			   jQuery('#expensePopup').dialog('open');
			   $('#title').val(data.json.expense.title);
			   $('#ExpenseAmount').val(data.json.expense.amount);
			   $('#expenseId').val(data.json.expense.expenseId);
			   $("#Expensedate").val(data.json.expense.expensdateformatted);
			
		});
		
		}
	
	
	function DeleteWagerPopup(){
		jQuery('#WagerPopup')
		.dialog('close');
		$.post('/tripcaddie/trip/strudel/deleteWager.do',{
			tripId : tripId
		},function(data){
			if(data=="success"){
				window.location.href="/tripcaddie/trip/strudel/getStrudelBS.do?tripId="+tripId;
			}else{
				
			}
		});
	}

	function cancelWagerPopup(){
		jQuery('#WagerPopup')
		.dialog('close');
	}
	function cancelexpensePopup(){
		jQuery('#expensePopup')
		.dialog('close');
	}
	
	function cancelWinningPopup(){
		jQuery('#WinningPopup')
		.dialog('close');
	}
	function cancelEditWinningPopup(){
		jQuery('#editWinningPopup')
		.dialog('close');
	}
	
	function submitWagerForm(){
		 if(validateWagerForm()){
				
				$('#WagerForm').submit();
			}	
	}
	function submitEditWinningForm(){
		 if(validateEditWinningForm()){
				
				$('#editWinningForm').submit();
			}	
	}
	
	function submitWinningForm(){
		 if(validateWinningForm()){
				
				$('#WinningForm').submit();
			}	
	}
	
	function submitexpenseForm(){
    if(validateCreateExpense()){
			
			$('#expenseForm').submit();
		}
	}
	
	function validateWagerForm(){
		var flag=true;
		var amount=$('#wagerAmount').val();
		if(amount == "" || amount==null){
			$('#wagerAmountError').text('');
			$('#wagerAmountError').text('invalid Amount');
			flag=false;
		 }else{
			 $('#wagerAmountError').text('');
		 }
		
		return flag;	
	}
	
	
	function validateEditWinningForm(){
		var flag=true;
		var amount=$('#winningAmountEdit').val();
		if(amount == "" || amount==null){
			$('#winningAmountEditError').text('');
			$('#winningAmountEditError').text('invalid Amount');
			flag=false;
		 }else{
			 $('#winningAmountEditError').text('');
		 }
		
		return flag;	
	}
	
	
	function validateWinningForm(){
		var flag=true;
		var amount=$('#winningAmount').val();
		if(amount == "" || amount==null){
			$('#winningAmountError').text('');
			$('#winningAmountError').text('invalid Amount');
			flag=false;
		 }else{
			 $('#winningAmountError').text('');
		 }
		
		return flag;	
	}
	
	
	function validateCreateExpense(){
		var flag=true;
		var filter= /[^a-zA-Z 0-9]+/g;
		var Title=$('#title').val();
		var Expensedate=$('#Expensedate').val();
		var amount=$('#ExpenseAmount').val();
			if(Expensedate == null || Expensedate == "" ){
				$('#ExpensedateError').text('');
				$('#ExpensedateError').text('Invalid date or Date is required');
				flag=false;
			}else{
				
				$('#ExpensedateError').text('');
			}
		
		
		
		if(Title == "" || Title==null || filter.test(Title) || Title.trim()== ""){
		
			$('#titleError').text('');
			$('#titleError').text('invalid Title');
			flag=false;
		}else{
			
			$('#titleError').text('');
		}
		
	
		
		
		if(amount == "" || amount==null){
			$('#ExpenseAmountError').text('');
			$('#ExpenseAmountError').text('invalid Amount');
			flag=false;
		 }else{
			 $('#ExpenseAmountError').text('');
		 }
		
		return flag;	
	}
	
	function addPopupOptions(divid){
		  jQuery('#'+divid).dialog(
			{
				resizable : false,
				autoOpen : false,
				height : 325,
				width : 490,
				open: function () {
                $(this).parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("ui-state-error");
            }
			
			});
	}
	
	$(function(){
		
		
		for(i=0 ;i<${expensesize +1};i++){
			$('#default').append("<tr><td>  </td></tr>");
		}
		$('#default').append("<tr><td>  </td></tr>");
		$('#default').append("<tr><td>  </td></tr>");
		$('#default').append("<tr><td>  </td></tr>");
		$('#default').append("<tr><td>Total Expenses</td></tr>");
		$('#default').append("<tr><td>Wager Deposit</td></tr>");
		$('#default').append("<tr><td>Total Winnings</td></tr>");
		$('#default').append("<tr><td>Total Amount I Owe</td></tr>");
		$('#default').append("<tr><td>Total Amount I Collect</td></tr>");
		
		
		
		
		
		<c:forEach var="tripmember" items="${tripmembers}" varStatus="items">
		$('#${tripmember.memberId}').append("<tr><td>${tripmember.membername}</td></tr>");
		//append expenses
		    <c:forEach var="expense" items="${tripmember.expenses}" varStatus="items">
		    
		    $('#${tripmember.memberId}').append("<tr><td><a href='#' onclick='openEditExpensPopup(${expense.expenseId});'>$${expense.amount}</a></td></tr>");
		     </c:forEach>
		    for(i=0 ;i<${expensesize - tripmember.expensesize};i++){
		    	$('#${tripmember.memberId}').append("<tr><td><a href='#' onclick='openCreateExpensPopup(${tripmember.memberId});'>Enter Expense</a></td></tr>");
		    }
		    $('#${tripmember.memberId}').append("<tr><td><a href='#' onclick='openCreateExpensPopup(${tripmember.memberId});'>Enter Expense</a></td></tr>");
		    $('#${tripmember.memberId}').append("<tr><td>$${tripmember.totalexpense}</td></tr>");
		    
		    <c:if test="${empty trip.wagerFee}">
		    $('#${tripmember.memberId}').append("<tr><td id='wagerfee'>0</td></tr>");
		   </c:if>
		  <c:if test="${not empty trip.wagerFee}">
		  $('#${tripmember.memberId}').append("<tr><td id='wagerfee'>$${trip.wagerFee}</td></tr>");
		   </c:if>
		   
		    
		    <c:if test="${empty tripmember.winningAmount}">
		    $('#${tripmember.memberId}').append("<tr><td><a href='#' onclick='openNewWinningPopup(${tripmember.memberId});'>Enter Winnings</a></td></tr>");
		   </c:if>
		  <c:if test="${not empty tripmember.winningAmount}">
		  $('#${tripmember.memberId}').append("<tr><td><a href='#' onclick='openEditWinningPopup(${tripmember.winningAmount.winningAmountId},${tripmember.winningAmount.winningAmount});'>$${tripmember.winningAmount.winningAmount}</a></td></tr>");
		   </c:if>
		//owe and collect
		$('#${tripmember.memberId}').append("<tr><td>$${tripmember.amountOwe}</td></tr>"); 
		$('#${tripmember.memberId}').append("<tr><td>$${tripmember.amoutCollect}</td></tr>"); 
		
		</c:forEach>
		
	});

	</script>
</body> 
</html>