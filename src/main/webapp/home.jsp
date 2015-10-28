<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<title>Trip caddie</title>
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<link rel="stylesheet" href="css/home.css" />
  	<link rel="shortcut icon" href="images/tripcaddie_logo.png" />
	<link rel="icon" href="images/tripcaddie_logo.png" type="image/x-icon" />
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script type="text/javascript" src="javascript/js/home.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  	<!-- <script src="javascript/js/jquery-1.4.4.min.js"></script> -->
  	<script src="javascript/js/functions.js"></script>
</head>
<body>
<div class="page">
  <div class="main_page">
		<header>
			<div class="top_content">
				<div id="top_left">
					<a href="#"><img src="images/logo.png"></a>
				</div>	
				<div id="top_right">
					<div class="welcome_form">
						<h4>Returning Golfers,Welcome Back!</h4>
						<form action="">
							<div class="input_username"><input name="j_username" placeholder="username"></div>
							<div class="input_password"><input name="j_password" placeholder="password"></div>
							<div id="form_button">
							<div class="remember_me">
								Remember me<input type="checkbox" name="vehicle" value="" /> 
							</div>
								<div class="final_login"><input type="button" name="submit" value="Login"></div>
							</div>
							<div id="forgot_password">Forgot Username Or Password?</div>
						</form>
					</div>
				</div>
			</div>
			
			<div class="secondtop_banner">
				<div id="banner_leftside"></div>
				<div id="banner_rightside">
					<div class="rightside_content">	
						<div class="every_great">Every <span >Great</span></div>
						<div class="trip">Golf Trip Begins Here...</div>
						<input type="button" class="signup_free" value="Sign up its free!" onclick="displaySignUpForm();"/>
						<!-- Have to ask radhakrishnan -->
						<!-- <input type="submit" class="signup_free" value="Sign up its free!" /> -->
						<div class="welcome_text">
							I would like to personally welcome you to best gold trip website on
							Earth!.We are committed to providing you the best community,tools 
							and services to help you have the best gold trips of your life!. I hope
							you enjoy your stay and visit often!.
						</div>
						<div class="quote">jon-morse - president/co-founder</div>
					</div>	
				</div>
			</div>
		</header>
		<div id="gap"></div>
		<section id="main_content">
		
			<div class="first_contentbox">
				<div class="research_box">Research Popular</div>
				<div class="golftrip_box">Golf Trip Destinations</div>
				<div class="resarch_list">
					<div class="contents_resarchlists"><span>Get Trip Ideas</span></div>
					<div class="contents_resarchlists"><span>Read Recommandations</span></div>
					<div class="contents_resarchlists"><span>Ask for Advice</span></div>
				</div>
				<div id="view_destination">View Destinations</div>
				<div class="watch_idea">Watch Video</div>
			</div>
			
			<div class="second_contentbox">
				<div class="research_box">Build And Manage</div>
				<div class="bucket_box">Your Bucket List</div>
				<div class="build_list">
					<div class="contents_resarchlists"><span>Track Places You've played</span></div>
					<div class="contents_resarchlists"><span>track places you want to play</span></div>
					<div class="contents_resarchlists"><span>Before you kick the bucket!</span></div>
				</div>
				<div id="lear_more">Learn More</div>
				<div class="watch_video">Watch Video</div>
			</div>
			<div class="third_contentbox">
				<div class="Create_box">Create a private Website for</div>
				<div class="group_box">Your Golf Trip group</div>
				<div class="build_list">
					<div class="contents_resarchlists"><span>Track Places You've played</span></div>
					<div class="contents_resarchlists"><span>track places you want to play</span></div>
					<div class="contents_resarchlists"><span>Before you kick the bucket!</span></div>
				</div>
				<div id="create_tripsite">Create a trip site</div>
				<div class="watch_video">Watch Video</div>
			</div>
			<div id="second_gap"></div>
			<div id="bottom_content">
				<div id="bottom_header">Are You a Golf Trip Leader, Trip Captian, Or Commissionar for a Group?</div>
				<div class="bottom_contentright">
					<div id="image_content"><img src="images/content_img.png"></div>
					<div class="leader_button">For Trip Leaders</div>
				</div>
				<div class="bottom_contentleft">
					<div id="first_bottom_content">
						<p> What ever you call yourself,you are the person who makes it all happen for your buddies golf trip.
							Don't plan it alone TripCadie can help!.TripCadie is your one-stop-shop for organizing all of the golf
							in your own private space and then makes it easy for your group to access anytime.
						</p>
					</div>
					<div id="second_bottom_content">
						<ul>
							<li>Explore and customize world class packages from top tour Operators and Resorts all over the world.</li>
							<li>Create a private website to organize all the information for your group.</li>
							<li>Easily load and Itinerary,Build pairings,Track Scores and Expenses,post picturs and videos.</li>
							<li>Exchange friendly banter or communicate using our fun chat,Discussion and polls social features</li>
						</ul>
					</div>
					<div id="third_bottom_content">
						<p><span class="headache">No more headaches!</span> With TripCaddie you will enjoy organizing,socializing and Remembering all of 
							Your gold trips-Year over year! So be sure to check out <span class="leadership" >our Special Trip Leadersection</span> just for you!
						</p>
					</div>
				</div>
			</div>
		</section>
	
		<div id="scrollbar_down">Golf Trip Products And Services</div>
		<div id="scrollbar_section"></div>
	
		<footer>
			<div id="footer_top">
				<div class="footern_top_content">
				<ul>
					<li>Customer Services</li>
					<li><a href="#">About TripCaddie</a></li>
					<li><a href="#">News and Press</a></li>
				</ul>
				</div>
					<div class="footern_top_content">
					<span><a href="#">Tell A Friend</a></span>
					<br>
					<span>Give Us Feedback-We Love it!</span>
					<div class="inside_content">
					<ul>
					<li><a href="#">Report a bug</a></li>
					<li><a href="#">Suggest A Feature</a></li>
					<li><a href="#">Trip your Caddie</a></li>
					</ul>
					</div>
				</div>
				<div class="footern_top_right">
					<p>Follow Us...</p>
					<div class="facebook_icon"><img src="images/fb_icon.png"></div>
					<div class="twitter_icon"><img src="images/twitter_icon.png"></div>
				</div>
			</div>
			<div id="footer_bottom">
				<div class="footern_bottom_content">
				<ul>
					<li><a href="#">Privacy policy</a></li>
					<li><a href="#">Terms of Use</a></li>
				</ul>
				</div>
					<div class="footern_bottom_content">
					<span><a href="#">For Business</a></span>
					<br>
					<span><a href="#">Advertise with us</a></span>
				
				</div>
				<div class="footern_bottom_right">
					<img src="images/footer_logo.png">
				</div>
				<div class="copyright_footer">&copy;2009-2012.TripCaddie<sup>&reg;</sup> is a registered Trademark of TripCaddie,LLC.All right reserved.</div>
			</div>
			
		</footer>
	</div>
</div> 
<!-- popup -->
<!-- Sign up popup -->
<div id="signUpPopup" class="popup" style="display: none;">
	<div class="close_popup"></div>
	<div class="popup_gap"></div>
	<div class="popup_header">Sign up for TripCaddie!  <span class="members"> Already a member?</span> <span class="signin">Sign in</span></div>
	<div class="contact_form">
	<form action="">
			<input name="username" placeholder="username">
			<input name="username" placeholder="password">
			<input name="username" placeholder="confirm Password">
			<input name="username" placeholder="Email Address">
			<input name="username" placeholder="First Name">
			<input name="username" placeholder="Last Name">
	</div>
	<div id="connect_icons">
		<div class="connect_icons_right">
			<div class="or_word">OR</div>
			<div id="closebracket"><img src="images/closebracket_icon.png"></div>
			<div class="icons_of_login">
			<div><img src="images/fb_button.png"></div>
			<div><img src="images/twitter_button.png"></div>
			</div>
		</div>
	</div>
	<div id="signup_text">
		By clicking "join" your agreeing to the <span class="popup_termsofuse">Terms of use</span> and 
		the <span  class="popup_termsofuse">privacy policy</span>.You will also recieve email from 
		TripCaddie where you can opt-out in your profile.
	</div>
	<div id="popup_login_button"><input type="button" name="join" value="Join"></div>
	</form>
</div>

</body>
</html>