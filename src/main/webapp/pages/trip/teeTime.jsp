<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tee Times and Pairings | TripCaddie</title>

<link rel="stylesheet" media="all" type="text/css" href="/tripcaddie/css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript" src="../javascript/js/teeTime.js"></script>
<script type="text/javascript" src="../javascript/timepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../javascript/timepicker/jquery-ui-sliderAccess.js"></script>

<style type="text/css">
	.selectTag{
		width: 150px;
	}
</style>
<script type="text/javascript">
	var teeTime;      //teeTime flag
	var temp,tripId,activityTime;
	var hour,minute,ampm;
	function maxId(id){
		var max=0;
		var arrId;
		var Id;
		$('#body'+id+'>tr').each(function(){
			Id=this.id;
			arrId=Id.split('-');
			max+=parseInt(arrId[0]);
		});
		return parseInt(max)+1;
	}
	
	function getTeeTimes(){
		$('#golfSchedule').hide();
		$('#teeTimes').show();
	}
	
	function timePicker(timeObject){
		//alert(timeObject.id);
		
			$('#'+timeObject.id).timepicker({
				hourGrid: 4,
				minuteGrid: 10,
				timeFormat: 'hh:mm tt'
			});
		
	}
	function addTeeTime(id){
		var maxid=maxId(id);
		var actTime=$('#activity-'+id).val();
		var c1,c2,c3,c4,c5;
		//alert('id:'+maxid);
		c1=5*maxid+1;
		c2=5*maxid+2;
		c3=5*maxid+3;
		c4=5*maxid+4;
		c5=5*maxid+5;
		//alert(c1);
		var trTag=document.createElement('tr');
		trTag.setAttribute("id",maxid+'-'+id);
		var append='<td><input type="hidden" name="teeTimeId" id="teeTimeId-'+maxid+'-'+id+'" value="0"/><p>'
			+'<input type="text" id="time-'+maxid+'-'+id+'" val="" onclick="timePicker(this)" /></p></td>'
			+'<td id="td-'+c1+'-'+id+'"><select id="player1-'+c1+'-'+id+'" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,'+maxid+')"></select></td>'
			+'<td id="td-'+c2+'-'+id+'"><select id="player2-'+c2+'-'+id+'" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,'+maxid+')"></select></td>'
			+'<td id="td-'+c3+'-'+id+'"><select id="player3-'+c3+'-'+id+'" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,'+maxid+')"></select></td>'
			+'<td id="td-'+c4+'-'+id+'"><select id="player4-'+c4+'-'+id+'" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,'+maxid+')"></select></td>'
			+'<td id="td-'+c5+'-'+id+'"><a href="#" onclick="addFifthPlayer('+id+','+c5+')">Add 5th player</td><td><input type="button" value="X" onclick="deleteTeeTime('+maxid+'-'+id+',0);"/></td>';
		trTag.innerHTML	= append;
		document.getElementById('body'+id).appendChild(trTag);
		//alert(temp);
		$('#player1-'+c1+'-'+id).html(temp);
		$('#player2-'+c2+'-'+id).html(temp);
		$('#player3-'+c3+'-'+id).html(temp);
		$('#player4-'+c4+'-'+id).html(temp);
		//alert('#hour-'+id);
		/* $('#hour-'+maxid+'-'+id).html(hour);
	    $('#minute-'+maxid+'-'+id).html(minute);
	    $('#ampm-'+maxid+'-'+id).html(ampm); */
	   // $('#time-'+maxid+'-'+id).val(time);
	    setTime(actTime,id,maxid);
	    initialize(${fn:length(activities)});
	}
	
	function save(id){
		var tdId,arrTdId,i,trId;
		var queryString;
		var player=new Array();
		var timing;
		var flag=true;
		var j=0;
		var returnData;
		for(i=0;i<5;i++){
			player[i]=null;
		}
		$('#body'+id+' > tr').each(function(){
			
			i=-1;
			trId=this.id; 
			
			timingtxt=$('#time-'+trId).val();
			timing=timingtxt.split(' ').join(':');
			
			$('#'+trId+' > td').each(function(){
				tdId=this.id;    //get column id
				arrTdId=tdId.split("-");
							
				if($('#player'+j+'-'+arrTdId[1]+'-'+arrTdId[2]).val() > 0){
					player[i]=$('#player'+j+'-'+arrTdId[1]+'-'+arrTdId[2]).val();
				}
				
				i++;j++;
			});
			j=0;
			
			if($('#teeTimeId-'+trId).val() == 0){
				queryString	= "player1="+player[0]+"&player2="+player[1]+"&player3="+player[2]+"&player4="+player[3]+"&player5="+player[4]+"&activityId="+$('#activityId-'+id).val()+"&timing="+timing+"&teeTimeId=0";
			}else{
				queryString	= "player1="+player[0]+"&player2="+player[1]+"&player3="+player[2]+"&player4="+player[3]+"&player5="+player[4]+"&activityId=0&timing="+timing+"&teeTimeId="+$('#teeTimeId-'+trId).val();
			}			
			//ajax request for saving tee time
			if(player[0] == null && player[1] == null && player[2] == null && player[3] == null && player[4] == null && $('#teeTimeId-'+trId).val() == 0 ){
				flag=false;
			}
			
			if(flag == true){
				$.ajax({
					url: "saveTeeTime.do",
					type: "POST",
					data: queryString,
					success: function(data){
						if(data=="error"){
							window.location.href="/tripcaddie/error.do";
						}else{
							returnData = data.split('-');
							$('#teeTimeId-'+trId).val(returnData[1]);
						}
					}
				});
			} 
			for(i=0;i<5;i++){
				player[i]=null;
			}
			flag=true; 
		});
	}
	function getGolfSchedule(){
		$('#golfSchedule').show();
		$('#teeTimes').hide();
	}
	function viewPairings(){
		window.location.href="/tripcaddie/trip/pairings.do?tripId="+tripId;
	}
	function addFifthPlayer(tBodyId,tdId){
		
		//alert($('#'+tdId+'-'+tBodyId).text());
		var id='player5-'+tdId+'-'+tBodyId;
		var selectTag=document.createElement('select');
		
		var trId=(parseInt(tdId)-5)/5;
		//alert('ID:'+id);
		selectTag.setAttribute("id",id);
		$('#td-'+tdId+'-'+tBodyId+' > a:first').remove();
		selectTag.className = "selectTag";
		selectTag.setAttribute("onfocus","setPrev(this.id)");
		selectTag.setAttribute("onchange","remove_player(this.id,"+trId+")");
		document.getElementById('td-'+tdId+'-'+tBodyId).appendChild(selectTag);
		
		$('#player5-'+tdId+'-'+tBodyId).html(temp);
		initialize(${fn:length(activities)});
	}
	
	function back(){
		window.location.href="/tripcaddie/trip/pairings.do?tripId="+tripId;
	}
	function getTripHome(){
		window.location.href="/tripcaddie/getTrip.do?tripId="+tripId;
	}
	
	function setTime(activityTime,tbodyId,rowId){
		//alert(activityTime);
		var time=activityTime;
		var hh,mm,ampm; 
		//alert(time);
		if(time.trim().length != 0){
			var timeArr=time.split(":");
			
			if(timeArr[0] > 12){
				hh=timeArr[0]-12;
				ampm='pm';
			}else{
				hh=timeArr[0];
				ampm='am';
			}
			mm=timeArr[1];
			//alert('#time-'+rowId+'-'+tbodyId);
			$('#time-'+rowId+'-'+tbodyId).val(hh+':'+mm+' '+ampm);
		}
	}	
</script>
</head>
<body onload="initialize(${fn:length(activities)})">
	<%@ include file="/pages/header2.jsp" %>
	<h1>Tee times and Pairings</h1>
	<div>
		<input type="button" value="Back" name="backBtn" id="backBtn" onclick="back();" />
		<input type="button" value="Return to Trip Home" name="tripHome" id="tripHomeId" onclick="getTripHome();" />
	</div>
	<script type="text/javascript">
		tripId=${trip.tripId};
	</script>
	<p>
		The TripCaddie Pairings Module is for managing your trips tee times at each golf course. After your tee times are made with the courses,
		your Trip Leader has special privileges to enter the tee times and make the pairings for the group. Remember,
		there must be a "Golf" type activity on your itinerary to use this module.
	</p>
	<p><img alt='Embedded Image' width='250px' height='250px' src=data:image/png;base64,${image} /></p>
	<p><span style="font-weight: bold;"><a href="/tripcaddie/getTrip.do?tripId=${trip.tripId}">${trip.tripName }</a></span></p>
	<p>${trip.golfCourseDto.addressDto.city },${trip.golfCourseDto.addressDto.state },${trip.golfCourseDto.addressDto.country }</p>
	<p><fmt:formatDate value="${trip.startDate.time }" type="date" dateStyle="long"/> - <fmt:formatDate value="${trip.endDate.time }" type="date" dateStyle="long"/></p>

	<p>
		<a href="#" onclick="getTeeTimes()">TEE TIMES</a> | <a href="#" onclick="getGolfSchedule()">GOLF SCHEDULE</a> 
	</p>
	<div id="teeTimes">
		
		<c:if test="${trip.tripLeader eq username }">
			<input type="button" value="view Pairings" id="viewPair" name="viewPair" onclick="viewPairings()"/>
		</c:if>
		<!--For Tee times -->
		<!-- Temporary combo box -->
		<select id="temp" style="display: none;" >
			<option id="-1" value="-1">----Select----</option>
			<c:forEach items="${tripMembers}" var="tripMember">
				<c:choose>
					<c:when test="${tripMember.tripCaddieUserDto.userId eq 0 }">
						<option id="${tripMember.memberId }" value="${tripMember.memberId }">${tripMember.invitedEmail }</option>		
					</c:when>
					<c:otherwise>
						<option id="${tripMember.memberId }" value="${tripMember.memberId }">
							${tripMember.tripCaddieUserDto.firstName } ${tripMember.tripCaddieUserDto.lastName }
								<c:choose> 
									<c:when test="${empty tripMember.tripCaddieUserDto.golfHandicap }">
										(HCI?)
									</c:when>
									<c:otherwise>
										(${tripMember.tripCaddieUserDto.golfHandicap })
									</c:otherwise>
								</c:choose>
						</option>		
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		<script type="text/javascript">
			temp=$('#temp').html();
		</script>
		<!-- For combo box in tee time -->
		<c:forEach items="${activities }" var="activity" varStatus="item">
			<div>
				<input type="hidden" id="activity-${item.count}" value="${activity.activityTimeString}" />
				<input type="hidden" id="activityId-${item.count}" value="${activity.activityId }" />
				<p><span style="font-weight: bold;">Round <c:out value="${item.count}" /></span> - <span style="font-weight: bold;"><c:out value="${activity.activityName }"/></span></p> 
				<p><span style="font-weight: bold;"><fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long"/> </span></p>
				<table id="table${item.count}">
					<thead>
						<tr>
							<th>Tee time</th>
							<th>Player 1</th>
							<th>Player 2</th>
							<th>Player 3</th>
							<th>Player 4</th>
							<th>Player 5</th>
						</tr>
					</thead>
					<tbody id="body${item.count}">
						
						<c:choose>
							<%-- <c:set var="length" value="${fn:length(activity.teeTimeDtos) }" /> --%>
							
							<c:when test="${ fn:length(activity.teeTimeDtos) eq 0 }">     
								
								<tr id="1-${item.count}">
									<td>
										<input type="hidden" name="teeTimeId" id="teeTimeId-1-${item.count}" value="0"/>
										<%-- <p><select id="hour-1-${item.count}" style="width: 50px"></select>
										<select id="minute-1-${item.count}" style="width: 50px"></select>
										<select id="ampm-1-${item.count}" style="width: 50px"></select></p> --%>
										<input type="text" id="time-1-${item.count}" val="" onclick="timePicker(this);" />
									</td>
									<td id="td-6-${item.count}">
										<select id="player1-6-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,1)"></select>
									</td>
									<td id="td-7-${item.count}">
										<select id="player2-7-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,1)"></select>
									</td>
									<td id="td-8-${item.count}">
										<select id="player3-8-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,1)"></select>
									</td>
									<td id="td-9-${item.count}">
										<select id="player4-9-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,1)"></select>
									</td>
									<td id="td-10-${item.count}">
										<a href="#" id="player5" onclick="addFifthPlayer(${item.count},10)">Add 5th player</a>
									</td>
									<td>
										<input type="button" value="X" onclick="deleteTeeTime('1-${item.count}',0);"/>
									</td>
								</tr>
								<script type="text/javascript">
									 
									 $("#player1-6-${item.count}").html(temp);
								     $("#player2-7-${item.count}").html(temp);
								     $("#player3-8-${item.count}").html(temp);
								     $("#player4-9-${item.count}").html(temp);
								     /* $("#hour-1-${item.count}").html(hour);
								     $("#minute-1-${item.count}").html(minute);
								     $("#ampm-1-${item.count}").html(ampm); */
								     //setTime argumets are activitityTime,activityCount(used as tbodyId,first Part of rowId(1-2))
								     setTime('${activity.activityTimeString}',${item.count},1);
								</script>
							</c:when>
							<c:otherwise>
								<c:forEach var="teeTime" items="${activity.teeTimeDtos}" varStatus="teeTimeList">
									<tr id='${teeTimeList.count }-${item.count}'>
										
										<td>
											<input type="hidden" name="teeTimeId" id="teeTimeId-${teeTimeList.count }-${item.count}" value="${teeTime.teeTimeId }" />
											<p>
												<%-- <select id="hour-${teeTimeList.count }-${item.count}" style="width: 50px;"></select>
												<select id="minute-${teeTimeList.count }-${item.count}" style="width: 50px;"></select>
												<select id="ampm-${teeTimeList.count }-${item.count}" style="width: 50px;"></select> --%>
												<input type="text" id="time-${teeTimeList.count }-${item.count}" value="" onclick="timePicker(this);" />
											</p>
										</td>
										<td id="td-${(teeTimeList.count*5)+1 }-${item.count}">
											<select id="player1-${(teeTimeList.count*5)+1}-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,${teeTimeList.count })"></select>
										</td>
										<td id="td-${(teeTimeList.count*5)+2 }-${item.count}">
											<select id="player2-${(teeTimeList.count*5)+2}-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,${teeTimeList.count })"></select>
										</td>
										<td id="td-${(teeTimeList.count*5)+3 }-${item.count}">
											<select id="player3-${(teeTimeList.count*5)+3}-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,${teeTimeList.count })"></select>
										</td>
										<td id="td-${(teeTimeList.count*5)+4 }-${item.count}">
											<select id="player4-${(teeTimeList.count*5)+4}-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,${teeTimeList.count })"></select>
										</td> 
										<td id="td-${(teeTimeList.count*5)+5 }-${item.count}">
											<c:choose>
												<c:when test="${empty teeTime.player5 }">
													<a href="#" id="player5" onclick="addFifthPlayer(${item.count},${(teeTimeList.count*5)+5 })" >Add 5th player</a>
												</c:when>
												<c:otherwise>
													<select id="player5-${(teeTimeList.count*5)+5}-${item.count}" style="width: 150px" onfocus="setPrev(this.id)" onchange="remove_player(this.id,${teeTimeList.count })"></select>
													<script type="text/javascript">
														$("#player5-${(teeTimeList.count*5)+5}-${item.count}").html(temp);
														setOPtion('player5-${(teeTimeList.count*5)+5}-${item.count}',${teeTime.player5.memberId});
													</script>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<input type="button" value="X" onclick="deleteTeeTime('${teeTimeList.count }-${item.count}',${teeTime.teeTimeId });"/>
										</td>
									</tr>
									<script type="text/javascript">
										 /* set option */
										 $("#player1-${(teeTimeList.count*5)+1}-${item.count}").html(temp);
									     $("#player2-${(teeTimeList.count*5)+2}-${item.count}").html(temp);
									     $("#player3-${(teeTimeList.count*5)+3}-${item.count}").html(temp);
									     $("#player4-${(teeTimeList.count*5)+4}-${item.count}").html(temp);
									  										
									     /* set respected value */
									    /*  setOPtion('player1-${(teeTimeList.count*5)+1}-${item.count}',${teeTime.player1.memberId});
									     setOPtion('player2-${(teeTimeList.count*5)+2}-${item.count}',${teeTime.player2.memberId});
									     setOPtion('player3-${(teeTimeList.count*5)+3}-${item.count}',${teeTime.player3.memberId});
									     setOPtion('player4-${(teeTimeList.count*5)+4}-${item.count}',${teeTime.player4.memberId});
									      */
									    /*  $("#hour-${teeTimeList.count }-${item.count}").html(hour);
									     $("#minute-${teeTimeList.count }-${item.count}").html(minute);
									     $("#ampm-${teeTimeList.count }-${item.count}").html(ampm); */
									     //setTime argumets are activitityTime,activityCount(used as tbodyId,first Part of rowId(1-2))
									     //alert('exist');
									     setTime('${activity.activityTimeString}',${item.count},${teeTimeList.count });
									</script>
									<c:if test="${not empty teeTime.player1 }">
										<script type="text/javascript">
											 setOPtion('player1-${(teeTimeList.count*5)+1}-${item.count}',${teeTime.player1.memberId});
										</script>
									</c:if>
									<c:if test="${not empty teeTime.player2 }">
										<script type="text/javascript">
											setOPtion('player2-${(teeTimeList.count*5)+2}-${item.count}',${teeTime.player2.memberId});
										</script>
									</c:if>
									<c:if test="${not empty teeTime.player3 }">	
										<script type="text/javascript">
											 setOPtion('player3-${(teeTimeList.count*5)+3}-${item.count}',${teeTime.player3.memberId});
										</script>
									</c:if>
									<c:if test="${not empty teeTime.player4 }">
										<script type="text/javascript">
											 setOPtion('player4-${(teeTimeList.count*5)+4}-${item.count}',${teeTime.player4.memberId});
										</script>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>			
				<input type="button" value="Add Tee time" onclick="addTeeTime(${item.count})" />
				<input type="button" value="save" onclick="save(${item.count})" />
			</div>
			
		</c:forEach>
		
	</div>
	<div id="golfSchedule">
		<span><c:if test="${trip.tripLeader eq username && trip.startDate.time gt now }">
			<a href="/tripcaddie/addActivities.do?tripId=${trip.tripId }">Add Golf Activity</a>
		</c:if></span>
		<p><span style="font-weight: bold;">ROUND</span>&nbsp;<span style="font-weight: bold;">DATE</span>&nbsp;<span style="font-weight: bold;">GOLF COURSE</span></p>
		<c:forEach items="${activities }" var="activity" varStatus="item">
			<p>
				<span>Round <c:out value="${item.count}" /></span>
				<span><fmt:formatDate value="${activity.activityDate.time }" type="date" dateStyle="long"/> </span>
				<span><c:out value="${activity.activityName }"/></span>
			<p>
		</c:forEach>
	</div>
</body>
</html>