package com.tripcaddie.frontend.courses.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.courses.model.Address;
import com.tripcaddie.backend.courses.model.CourseRatingDetails;
import com.tripcaddie.backend.courses.model.CourseRatingDetailsPK;
import com.tripcaddie.backend.courses.model.CourseType;
import com.tripcaddie.backend.courses.model.DressCode;
import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.courses.model.Image;
//import com.tripcaddie.backend.courses.model.Image;
import com.tripcaddie.backend.courses.searchDao.CourseDao;
import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.bucketList.dto.GolfClubAdviceResponseDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;
import com.tripcaddie.frontend.bucketList.service.BucketListService;
import com.tripcaddie.frontend.courses.dto.AddressDto;
import com.tripcaddie.frontend.courses.dto.CourseTypeDto;
import com.tripcaddie.frontend.courses.dto.DressCodeDto;
import com.tripcaddie.frontend.courses.dto.GolfCourseDto;
import com.tripcaddie.frontend.courses.dto.ImageDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("destinationSearch")
public class DestinationSearchServiceImpl implements DestinationSearchService {

	@Resource(name="courseDao")
	private CourseDao courseDao;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="bucketListService")
	private BucketListService bucketListService;
	@Resource(name="userDao")
	private UserDao userDao;
	
	String places;
	
	public ArrayList<GolfCourseDto> getTopDestinations(){
		return null;
	}

	public GolfCourseDto getClubDetails(int courseId,HttpSession session) throws Exception {
		boolean flag=false;
		String bucketListStatus=null;
		GolfCourse golfCourse=courseDao.getGolfCourse(courseId);
		String username=(String)session.getAttribute("username");
		if(username != null){
			TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(username);
			flag=bucketListService.isExistinList(courseId, tripcaddieUserDto.getUserId());
			bucketListStatus=bucketListService.getVisitedorNotStatus(courseId, tripcaddieUserDto.getUserId());
		}
		if(golfCourse != null){
			GolfCourseDto golfCourseDto=GolfCourseDto.instantiate(golfCourse);
			golfCourseDto.setExist(flag);
			golfCourseDto.setBucketListStatus(bucketListStatus);
			return golfCourseDto;
		}else{
			return null;
		}
	}

	public GolfClubInquiryDto getAdvice(int adviceId) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		
		GolfClubInquiry golfClubInquiry=courseDao.getAdvice(adviceId);
		ArrayList<GolfClubAdviceResponseDto> adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
		GolfClubAdviceResponseDto adviceResponseDto=new GolfClubAdviceResponseDto();
		if(golfClubInquiry != null){
			GolfClubInquiryDto golfClubInquiryDto=GolfClubInquiryDto.instantiate(golfClubInquiry);
			golfClubInquiryDto.setNoOfAdvices(golfClubInquiry.getAdviceResponse().size());
			
			if(username.equalsIgnoreCase(golfClubInquiry.getCreatedBy())){
				golfClubInquiryDto.setCurrentUser("true");
			}else{
				golfClubInquiryDto.setCurrentUser("false");
			}
			
			for (GolfClubAdviceResponse golfClubAdviceResponse : golfClubInquiry.getAdviceResponse()) {
				
				adviceResponseDto=GolfClubAdviceResponseDto.instantiate(golfClubAdviceResponse);
				adviceResponseDtos.add(adviceResponseDto);
				adviceResponseDto=new GolfClubAdviceResponseDto();
				
			}
			golfClubInquiryDto.setAdviceResponseDtos(adviceResponseDtos);
			return golfClubInquiryDto;
		}else{
			return null;
		}
	}

	public ArrayList<GolfClubInquiryDto> getAdvices(int courseId) throws Exception {
		
		GolfClubInquiryDto clubInquiryDto = new GolfClubInquiryDto();
		String username=loginService.getAuthenticatedUser();
		
		ArrayList<GolfClubInquiry> clubInquiries= courseDao.getAdvices(courseId);
		ArrayList<GolfClubInquiryDto> clubInquiryDtos=new ArrayList<GolfClubInquiryDto>();
		
		for(GolfClubInquiry clubInquiry:clubInquiries){
			clubInquiryDto = GolfClubInquiryDto.instantiate(clubInquiry);
			clubInquiryDto.setNoOfAdvices(clubInquiry.getAdviceResponse().size());
			
			if(username.equalsIgnoreCase(clubInquiry.getCreatedBy())){
				clubInquiryDto.setCurrentUser("true");
			}else{
				clubInquiryDto.setCurrentUser("false");
			}
			clubInquiryDtos.add(clubInquiryDto);
			clubInquiryDto = new GolfClubInquiryDto();
		}
		return clubInquiryDtos;
			
	}

	public void updateAdviceResponseLike(int responseId) throws Exception {
		
		String userName=loginService.getAuthenticatedUser();
		
		TripcaddieUserDto user=loginService.getUserByUserName(userName);
		TripCaddieUser tripCaddieUser=loginService.createUserFrom(user);
		GolfClubAdviceResponse response=courseDao.getAdviceResponse(responseId);
		response.getUsers().add(tripCaddieUser);
		courseDao.updateAdviceResponse(response);
	}

	public void updateReviewLike(int reviewId) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		TripCaddieUser tripCaddieUser=loginService.createUserFrom(userDto);
		GolfCourseReview review=courseDao.getReview(reviewId);
		review.getUsers().add(tripCaddieUser);
		courseDao.updateReview(review);
	}

	@Override
	public ArrayList<GolfCourseDto> getGolfCourses(String destination) throws Exception {
		
		places=destination;
		
		ArrayList<GolfCourse> courses=null;
		ArrayList<GolfCourseDto> courseDtos=new ArrayList<GolfCourseDto>();
		
		String country=findPlace();
		String state=findPlace();	
		String city=findPlace();
		String courseName=findPlace();
		
		if(courseName!=null){
			courses= courseDao.getGolfCourses(courseName, courseName);
		}else if(city!=null){
			courses= courseDao.getGolfCourses("address.city", city);
		}else if(state!=null){
			courses= courseDao.getGolfCourses("address.state", state);
		}else if(country !=null){
			courses= courseDao.getGolfCourses("address.country", country);
		}
		if(courses !=null && !courses.isEmpty()){
			for(GolfCourse golfCourse:courses){
				courseDtos.add(GolfCourseDto.instantiate(golfCourse));
			}
		}
		return courseDtos;
	}
	
	@Override
	public GolfCourseReviewDto getReview(int reviewId) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		GolfCourseReview courseReview= courseDao.getReview(reviewId);
		GolfCourseReviewDto courseReviewDto=new GolfCourseReviewDto();
		if(courseReview != null){
			courseReviewDto=GolfCourseReviewDto.instantiate(courseReview);
			
			if(username.equalsIgnoreCase(courseReview.getCreatedBy())){
				courseReviewDto.setCurrentUser("true");
			}else{
				courseReviewDto.setCurrentUser("false");
			}
		}
		return courseReviewDto;
	}

	@Override
	public ArrayList<GolfCourseReviewDto> getReviews(int courseId) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		ArrayList<GolfCourseReview> courseReviews= courseDao.getReviews(courseId);
		ArrayList<GolfCourseReviewDto> courseReviewDtos=new ArrayList<GolfCourseReviewDto>();
		for(GolfCourseReview courseReview:courseReviews){
			
			GolfCourseReviewDto reviewDto=GolfCourseReviewDto.instantiate(courseReview);
			System.out.println("Username:"+username);
			System.out.println("CreatedBy:"+courseReview.getCreatedBy());
			if(username.equalsIgnoreCase(courseReview.getCreatedBy())){
				reviewDto.setCurrentUser("true");
			}else{
				reviewDto.setCurrentUser("false");
			}
			courseReviewDtos.add(reviewDto);
		}
		return courseReviewDtos;
	
	}

	@Override
	public GolfClubAdviceResponseDto getAdviceResponse(int responseId) throws Exception {
		
		GolfClubAdviceResponse adviceResponse= courseDao.getAdviceResponse(responseId);
		GolfClubAdviceResponseDto adviceResponseDto=new GolfClubAdviceResponseDto();
		if(adviceResponse != null){
			adviceResponseDto=GolfClubAdviceResponseDto.instantiate(adviceResponse);
			return adviceResponseDto;
		}else{
			return null;
		}
		
	}

	@Override
	public GolfClubAdviceResponseDto giveAdviceResponse(int adviceId,
			String responseText) throws Exception {
		
		Date date=new Date();
		Calendar createdDate=Calendar.getInstance();
		createdDate.setTime(date);
		String username=loginService.getAuthenticatedUser();
				
		GolfClubAdviceResponse adviceResponse=new GolfClubAdviceResponse();
		GolfClubInquiry inquiry=courseDao.getAdvice(adviceId);
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		TripCaddieUser user=loginService.createUserFrom(userDto);
		
		adviceResponse.setInquiry(inquiry);
		adviceResponse.setAnswer(responseText);
		adviceResponse.setUser(user);
		adviceResponse.setCreatedBy(user.getUserName());
		adviceResponse.setCreatedDate(createdDate);
				
		int responseId=courseDao.storeResponse(adviceResponse);
		adviceResponse=courseDao.getAdviceResponse(responseId);
		
		//Dto
		GolfClubAdviceResponseDto adviceResponseDto=GolfClubAdviceResponseDto.instantiate(adviceResponse);
		
		GolfClubInquiryDto clubInquiryDto=GolfClubInquiryDto.instantiate(adviceResponse.getInquiry());
		clubInquiryDto.setNoOfAdvices(adviceResponse.getInquiry().getAdviceResponse().size());
		adviceResponseDto.setAdviceRequestDto(clubInquiryDto);
		
		return adviceResponseDto;
	}

	@Override
	public GolfClubInquiryDto askAdvice(int courseId, String adviceText,HttpSession session) throws Exception {
		
		GolfClubInquiry adviceRequest=new GolfClubInquiry();
		String username=loginService.getAuthenticatedUser();
		
		TripcaddieUserDto userDto=loginService.getUserByUserName(username);
		GolfCourseDto golfCourseDto=this.getClubDetails(courseId,session);
		
		TripCaddieUser user=loginService.createUserFrom(userDto);
		
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		
		adviceRequest.setUser(user);
		adviceRequest.setQuestion(adviceText);
		adviceRequest.setGolfCourse(createGolfCourseFrom(golfCourseDto));
		adviceRequest.setCreatedDate(calendar);
		adviceRequest.setCreatedBy(username);
		
		int adviceId=courseDao.storeAdvice(adviceRequest);
		adviceRequest=courseDao.getAdvice(adviceId);
		
		GolfClubInquiryDto clubInquiryDto=GolfClubInquiryDto.instantiate(adviceRequest);
		if(username.equalsIgnoreCase(adviceRequest.getCreatedBy())){
			clubInquiryDto.setCurrentUser("true");
		}else{
			clubInquiryDto.setCurrentUser("false");
		}
		return clubInquiryDto;
	}

	@Override
	public GolfCourseReviewDto writeReview(String reviewText,int courseId) throws Exception {
		
		GolfCourse golfCourse=courseDao.getGolfCourse(courseId);
				
		TripcaddieUserDto userDto=loginService.getUserByUserName(loginService.getAuthenticatedUser());
		TripCaddieUser user=loginService.createUserFrom(userDto);
		
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		
		GolfCourseReview courseReview=new GolfCourseReview();
		courseReview.setGolfCourse(golfCourse);
		courseReview.setUser(user);
		courseReview.setReview(reviewText);
		courseReview.setCreatedBy(user.getUserName());
		courseReview.setCreatedDate(calendar);
				
		int reviewId=courseDao.storeReview(courseReview);
		GolfCourseReview golfCourseReview = courseDao.getReview(reviewId);
		
		GolfCourseReviewDto reviewDto=GolfCourseReviewDto.instantiate(golfCourseReview);
		
		if(userDto.getUserName().equalsIgnoreCase(golfCourseReview.getCreatedBy())){
			reviewDto.setCurrentUser("true");
		}else{
			reviewDto.setCurrentUser("false");
		}
		
		return reviewDto; 
	}

	String findPlace() throws Exception{
		if(places==null)
			return null;
		int index=places.indexOf(',');
		String dest;
		if(index == -1){
			dest=places.substring(0, places.length());
			places=null;
		}else{
			dest=places.substring(0,index);
			try{
				places=places.substring(index+1, places.length());
			}catch(Exception e){
				places=null;
			}
		}
		return dest;
	}
	
	public GolfCourse createGolfCourseFrom(GolfCourseDto golfCourseDto) throws Exception {
		
			GolfCourse golfCourse=new GolfCourse();
			golfCourse.setAddress(createAddressFrom(golfCourseDto.getAddressDto()));
			golfCourse.setAdvanceTee(golfCourseDto.getAdvanceTee());
			golfCourse.setCourseName(golfCourseDto.getCourseName());
			golfCourse.setCourseType(createCourseTypeFrom(golfCourseDto.getCourseTypeDto()));
			golfCourse.setDescription(golfCourseDto.getDescription());
			golfCourse.setDesigner(golfCourseDto.getDesigner());
			golfCourse.setDressCode(createDressCodeFrom(golfCourseDto.getDressCodeDto()));
			golfCourse.setGolfCourseId(golfCourseDto.getGolfCourseId());
			for(ImageDto imageDto:golfCourseDto.getGolfImageDtos()){
				golfCourse.getGolfImage().add(createGolfImageFrom(imageDto));
			}
			golfCourse.setGreenFees(golfCourseDto.getGreenFees());
			golfCourse.setGuestPolicy(golfCourseDto.getGuestPolicy());
			golfCourse.setHoles(golfCourseDto.getHoles());
			golfCourse.setLocationDST(golfCourseDto.getLocationDST());
			golfCourse.setLocationLattitudeCentroid(golfCourseDto.getLocationLattitudeCentroid());
			golfCourse.setLocationLattitudePoly(golfCourseDto.getLocationLattitudePoly());
			golfCourse.setLocationLongitudeCentroid(golfCourseDto.getLocationLongitudeCentroid());
			golfCourse.setLocationLongitudePoly(golfCourse.getLocationLongitudePoly());
			golfCourse.setLocationTZ(golfCourseDto.getLocationTZ());
			golfCourse.setMetalSpikes(golfCourseDto.getMetalSpikes());
			golfCourse.setPhone(golfCourseDto.getPhone());
			golfCourse.setPlayFive(golfCourseDto.getPlayFive());
			golfCourse.setRating(golfCourseDto.getRating());
			golfCourse.setSeason(golfCourseDto.getSeason());
			golfCourse.setWeeekendFees(golfCourseDto.getWeeekendFees());
			golfCourse.setYearBuilt(golfCourseDto.getYearBuilt());
			return golfCourse;
	}
	
	public Address createAddressFrom(AddressDto addressDto)  throws Exception{
		
		Address address=new Address();
		address.setAddressLine1(addressDto.getAddressLine1());
		address.setAddressLine2(addressDto.getAddressLine2());
		address.setAddressLine3(addressDto.getAddressLine3());
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setLocationAreaCode(addressDto.getLocationAreaCode());
		address.setLocationCounty(addressDto.getLocationCounty());
		address.setLocationFIPS(addressDto.getLocationFIPS());
		address.setLocationMSA(addressDto.getLocationMSA());
		address.setLocationPMSA(addressDto.getLocationPMSA());
		return address;
	}
	public CourseType createCourseTypeFrom(CourseTypeDto courseTypeDto) throws Exception{
		
		CourseType courseType=new CourseType();
		courseType.setCourseTypeId(courseTypeDto.getCourseTypeId());
		courseType.setCourseType(courseTypeDto.getCourseType());
		return courseType;
	}
	public DressCode createDressCodeFrom(DressCodeDto dressCodeDto) throws Exception {
		
		DressCode dressCode=new DressCode();
		dressCode.setDressCode(dressCodeDto.getDressCode());
		dressCode.setDressCodeId(dressCodeDto.getDressCodeId());
		return dressCode;
	}
	public Image createGolfImageFrom(ImageDto imageDto) throws Exception {
		
		Image image=new Image();
		image.setPrimary(imageDto.getPrimary());
		image.setImageId(imageDto.getImageId());
		image.setImageName(imageDto.getImageName());
		image.setUrl(imageDto.getUrl());
		return image;
	}

	@Override
	public int getNoOfLikes(int reviewId) throws Exception {
		
		return courseDao.getNoOfLikes(reviewId);
	}

	@Override
	public ArrayList<GolfClubAdviceResponseDto> getAdviceResponses(int adviceId) throws Exception {
		
		ArrayList<GolfClubAdviceResponseDto> adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
		ArrayList<GolfClubAdviceResponse> adviceResponses=courseDao.getAdviceResponses(adviceId);
		for (GolfClubAdviceResponse golfClubAdviceResponse : adviceResponses) {
			adviceResponseDtos.add(GolfClubAdviceResponseDto.instantiate(golfClubAdviceResponse));			
		}
		return adviceResponseDtos;
	}

	@Override
	public void updateorAddDiscussionRating(int courseId, Integer rating)
			throws Exception {
		
		int totalRating=0;
		
		CourseRatingDetails courseRatingDetails=new CourseRatingDetails();
		CourseRatingDetailsPK courseRatingDetailsPK=new CourseRatingDetailsPK();
		
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);
		
		GolfCourse course=courseDao.getGolfCourse(courseId);
		
		courseRatingDetailsPK.setGolfCourse(course);
		courseRatingDetailsPK.setTripCaddieUser(user);
		courseRatingDetails.setCourseRatingDetailsPK(courseRatingDetailsPK);
		courseRatingDetails.setRating(rating);
		
		courseDao.saveOrUpdateEntity(courseRatingDetails);
		
		System.out.println("Until this ok in destinationsearch service");
		//Update Avg rating
		ArrayList<CourseRatingDetails> ratingDetails = courseDao.getCourseRatingDetails(courseId);
		for (CourseRatingDetails courseRatingDetail : ratingDetails) {
			
			totalRating+=courseRatingDetail.getRating();
		}
		
		if(ratingDetails.size() == 0){
			course.setRating(0.0);
		}else {
			course.setRating((double)totalRating/ratingDetails.size());
		}
		
		
		courseDao.updateCourseDetails(course);
	}

	@Override
	public void removeReview(int reviewId) throws Exception {
		
		GolfCourseReview review = courseDao.getReview(reviewId);
		courseDao.deleteEntity(review);
		
	}

	@Override
	public void removeQuestion(int questionId) throws Exception {
		
		GolfClubInquiry clubInquiry=courseDao.getAdvice(questionId);
		courseDao.deleteEntity(clubInquiry);
		
	}

	@Override
	public void editReview(int reviewId, String review) throws Exception {
		
		String username = loginService.getAuthenticatedUser();
		Calendar instantTime = Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		GolfCourseReview courseReview = courseDao.getReview(reviewId);
		courseReview.setLastUpdatedBy(username);
		courseReview.setLastUpdatedDate(instantTime);
		courseReview.setReview(review);
		
		courseDao.saveOrUpdateEntity(courseReview);
		
	}

	@Override
	public void editInquiry(int inquiryId, String questionTxt) throws Exception {
		
		String username = loginService.getAuthenticatedUser();
		Calendar instantTime = Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		GolfClubInquiry inquiry=courseDao.getAdvice(inquiryId);
		inquiry.setLastUpdatedBy(username);
		inquiry.setLastUpdatedDate(instantTime);
		inquiry.setQuestion(questionTxt);
		
		courseDao.saveOrUpdateEntity(inquiry);
		
	}
}
