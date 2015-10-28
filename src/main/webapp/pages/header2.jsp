<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
<head>
<title>My Profile</title> -->
<link type="text/css" rel="stylesheet" media="all"
	href="/tripcaddie/css/header2.css">
<script type="text/javascript" src="/tripcaddie/javascript/js/header.js"></script>
<body
	class="not-front logged-in page-user no-sidebars popups-processed tableHeader-processed">
	<div class="header-bg">&nbsp;</div>
	
		<div id="page-width">
			<div id="header" class="clearfix">
				<div class="header-inner clearfix">
					<div class="logo clearfix">
						<div class="logo-inner">
							<a href="/tripcaddie" title="TripCaddie"
								rel="home"><img src="http://www.tripcaddie.com/sites/default/files/tripcaddie_logo.png"
								alt="TripCaddie">
							</a>
						</div>

						<div class="mission">
							Where Buddies <u>PLAN</u> Golf Trips!
						</div>
					</div>
					<!--logo-->
					<c:set var="username" value= "${sessionScope.username}" scope= "session" />
					<div class="header-top">
						<div class="loginform">

							<div id="block-tc_public_site-2"
								class="clear-block block block-tc_public_site ">
								<div class="block-content-main">
									<div class="form-elements clearfix">
										<div class="flt-lt">
											<label class="option"><b>Welcome, </b>
											</label> <span><b><a
													href="/tripcaddie//user/profile.do" title="My Profile"
													alt="My Profile" class="active"><c:out value= "${sessionScope.username}"/></a>
											</b>
											</span>
										</div>
										<div class="button-center">
											<a href="#" title="Logout"
												alt="Logout" onclick="logout();">Logout</a>
										</div>
									</div>
								</div>
							</div>

						</div>
						<!--loginform-->

						<!-- <div class="secondary-menu">
							<ul class="links secondary-links">
								<li class="menu-132 first"><a href="#" target="_blank">TripCaddie
										Blog</a> |</li>
								<li class="menu-979"><a href="#"
									class="popups popups-processed" title="">Tell A Friend</a> |</li>
								<li class="menu-134 last"><a href="#" title="">FAQ</a>
								</li>
							</ul>
						</div> -->
						<!--secondary-menu-->

					</div>
					<!--header-top-->
				</div>
				<!--header-inner-->
				<div class="header-bottom-main clearfix">
					<div class="header-bottom">
						<div class="primary-menu ">
							<ul class="primary-links">
								<li class="menu-1696 first"><a
									href="/tripcaddie/" title="">PLAN
										A NEW TRIP</a>
								</li>
								<li class="menu-1697"><a
									href="/tripcaddie/userHome.do" title="">MY
										TRIPS</a>
								</li>
								<li class="menu-1698 active-trail last active"><a
									href="/tripcaddie/user/profile.do" title=""
									class="active">MY PROFILE</a>
								</li>
							</ul>
						</div>
						<!--primary-menu-->
						<div class="feed-icons">

							<div id="block-tc_public_site-4"
								class="clear-block block block-tc_public_site ">
								<div class="block-content-main"></div>
							</div>

						</div>
						<!--feed-icons-->
					</div>
					<!--header-bottom-->
				</div>

			</div>
			<!--header-->
