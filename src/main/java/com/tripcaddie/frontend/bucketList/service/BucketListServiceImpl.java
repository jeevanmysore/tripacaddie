package com.tripcaddie.frontend.bucketList.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.bucketList.dao.BucketListDao;
import com.tripcaddie.backend.bucketList.model.BucketList;
import com.tripcaddie.backend.bucketList.model.BucketListPK;
import com.tripcaddie.backend.bucketList.model.GolfClubAdviceResponse;
import com.tripcaddie.backend.bucketList.model.GolfClubInquiry;
import com.tripcaddie.backend.bucketList.model.GolfCourseReview;
import com.tripcaddie.backend.bucketList.model.VisitedPlace;
import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.courses.searchDao.CourseDao;
import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.bucketList.dto.BucketListDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubAdviceResponseDto;
import com.tripcaddie.frontend.bucketList.dto.GolfClubInquiryDto;
import com.tripcaddie.frontend.bucketList.dto.GolfCourseReviewDto;
import com.tripcaddie.frontend.courses.service.DestinationSearchService;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("bucketListService")
public class BucketListServiceImpl implements BucketListService {

	@Resource(name="bucketListDao")
	private BucketListDao bucketListDao;
	@Resource(name="courseDao")
	private CourseDao courseDao;
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="destinationSearch")
	private DestinationSearchService destinationSearchService;
	
	@Override
	public void addToBucketList(int courseId, int playedId ,String ranking) throws Exception{
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto user=loginService.getUserByUserName(username);
		TripCaddieUser tripCaddieUser=loginService.createUserFrom(user);
		
		Calendar calendar=Calendar.getInstance();
		Date date=new Date();
		calendar.setTime(date);
		
							
		VisitedPlace placesPlayed=bucketListDao.getPlaceVisitedStatus(playedId);
		GolfCourse golfCourse=courseDao.getGolfCourse(courseId);
			
		BucketListPK bucketListPK=new BucketListPK();
		bucketListPK.setGolfCourse(golfCourse);
		bucketListPK.setTripCaddieUser(tripCaddieUser);
		
		BucketList bucketList=new BucketList();
		int count=bucketListDao.getBucketListCount(user.getUserId(), playedId);
		if(ranking.isEmpty() || Integer.parseInt(ranking)>count){		
			List<BucketList> bucketLists=bucketListDao.getAllBucketLists(user.getUserId(), playedId, 1);
			for (BucketList bucketList2 : bucketLists) {
				bucketList2.setPriority(bucketList2.getPriority()+1);
			}
			bucketListDao.saveOrUpdateCollection(bucketLists);
			bucketList.setPriority(1);
		}
		/*else if(Integer.parseInt(ranking)>count){
			bucketList.setPriority(count+1);
		}*/
		else{
			List<BucketList> bucketLists=bucketListDao.getAllBucketLists(user.getUserId(), playedId, Integer.parseInt(ranking));
			for(BucketList bucketList2:bucketLists){
				bucketList2.setPriority(bucketList2.getPriority()+1);
			}
			//updating the collection
			bucketListDao.saveOrUpdateCollection(bucketLists);
			bucketList.setPriority(Integer.parseInt(ranking));
		}
		
		bucketList.setPlacesPlayed(placesPlayed);
		bucketList.setBucketListPK(bucketListPK);
		
		bucketList.setCreatedBy(username);
		bucketList.setCreatedDate(calendar);
		bucketListDao.saveOrUpdateBucketList(bucketList);
	}

	@Override
	public void updateAdviceResponse(int responseId, String response) throws Exception{
		
		GolfClubAdviceResponseDto adviceResponseDto=destinationSearchService.getAdviceResponse(responseId);
		GolfClubAdviceResponse adviceResponse=this.createGolfClubAdviceResponse(adviceResponseDto);
		adviceResponse.setAnswer(response);
		
	}

	@Override
	public ArrayList<BucketListDto> getBucketLists(int placeVisitedId) throws Exception {
		
		int recommendationCount = 0,ratingCount,inquiryCount;
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(username);
		TripCaddieUser tripCaddieUser=loginService.createUserFrom(tripcaddieUserDto);
		
		ArrayList<BucketList> bucketLists= bucketListDao.getBucketLists(tripCaddieUser, placeVisitedId);
		ArrayList<BucketListDto> bucketListDtos=new ArrayList<BucketListDto>();
		for(BucketList bucketList:bucketLists){
			bucketListDtos.add(BucketListDto.instantiate(bucketList));	
		}
		for(BucketListDto bucketListDto:bucketListDtos){
			recommendationCount=this.getRecommendationsCount(bucketListDto.getGolfCourseDto().getGolfCourseId());
			bucketListDto.setRecommendationCount(recommendationCount);
			
			ratingCount=this.getRatingCount(bucketListDto.getGolfCourseDto().getGolfCourseId(), placeVisitedId);
			bucketListDto.setRatingCount(ratingCount);

			inquiryCount=this.getInquiryCount(bucketListDto.getGolfCourseDto().getGolfCourseId());
			bucketListDto.setInquiryCount(inquiryCount);
		}
		return bucketListDtos;
	}

	@Override
	public void updateAdviceRequest(int responseId, String response)  throws Exception{
		// TODO Auto-generated method stub
	}

	@Override
	public void updatePriority(String updatePriorityway,int courseId,int visitedId) throws Exception {
		
		String direction;
		int noofMoves;
				
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		
		BucketList bucketList=bucketListDao.getBucketList(tripCaddieUser.getUserId(), courseId, visitedId);
		
		if(updatePriorityway.equalsIgnoreCase("up")){
			direction=updatePriorityway;
			noofMoves=-1;
		}else if(updatePriorityway.equalsIgnoreCase("down")){
			direction=updatePriorityway;
			noofMoves=1;
		}else{
			System.out.println(updatePriorityway+" is"+this.getNumberFromString(updatePriorityway));
			Integer num=this.getNumberFromString(updatePriorityway);
			noofMoves=num-(bucketList.getPriority());
			if(noofMoves>0)
				direction="down";
			else
				direction="up";
		}
		this.updateBucketList(tripCaddieUser, noofMoves, courseId, visitedId, direction);
		//I just update priority by swapping priority
		/*if(position==1 && count != bucketList1.getPriority()){
			bucketList2=bucketListDao.getBucketListByPriority(tripCaddieUser.getUserId(), bucketList1.getPriority()+1, visitedId);
		}
		else if(position==-1 && bucketList1.getPriority() != 1){
			bucketList2=bucketListDao.getBucketListByPriority(tripCaddieUser.getUserId(), bucketList1.getPriority()-1, visitedId);
		}else
			return;
		
		int priority=bucketList1.getPriority();
		System.out.println("priority:"+priority);
		bucketList1.setPriority(bucketList2.getPriority());
		bucketListDao.updateBucketList(bucketList1);
		bucketList2.setPriority(priority);
		bucketListDao.updateBucketList(bucketList2);*/
	}
	
	//update bucketList
	private void updateBucketList(TripCaddieUser tripCaddieUser,int noOfmoves,int courseId,int visitedId,String direction) throws Exception {
		
		int count=bucketListDao.getBucketListCount(tripCaddieUser.getUserId(), visitedId);
		int moves=Math.abs(noOfmoves);
		
		Calendar calendar=Calendar.getInstance();
		Date date=new Date();
		calendar.setTime(date);
		
		BucketList bucketList=null;
		BucketList bucketListtoMove=bucketListDao.getBucketList(tripCaddieUser.getUserId(), courseId, visitedId);
		
		/*System.out.println("In update bucketList");	
		System.out.println("Priority:"+bucketListtoMove.getPriority());*/
		for(int i=1;i<=moves;i++){
			//I have to change the way of checking below
			if(direction.equalsIgnoreCase("up") && bucketListtoMove.getPriority() != 1){
				bucketList=bucketListDao.getBucketListByPriority(tripCaddieUser.getUserId(), bucketListtoMove.getPriority()-i, visitedId);
				System.out.println("Priority:"+bucketList.getPriority());
				bucketList.setPriority(bucketList.getPriority()+1);
				bucketList.setLastUpdatedBy(tripCaddieUser.getUserName());
				bucketList.setLastUpdatedDate(calendar);
			}
			else if(direction.equalsIgnoreCase("down") && bucketListtoMove.getPriority() != count){
				bucketList=bucketListDao.getBucketListByPriority(tripCaddieUser.getUserId(), bucketListtoMove.getPriority()+i, visitedId);
				System.out.println("bucketList.getPriority():"+bucketList.getPriority());
				bucketList.setPriority(bucketList.getPriority()-1);
				bucketList.setLastUpdatedBy(tripCaddieUser.getUserName());
				bucketList.setLastUpdatedDate(calendar);
			}
			else
				break;
			bucketListDao.updateBucketList(bucketList);
		
		}
		bucketListtoMove.setPriority(bucketListtoMove.getPriority()+noOfmoves);
		bucketListtoMove.setLastUpdatedBy(tripCaddieUser.getUserName());
		bucketListtoMove.setLastUpdatedDate(calendar);
		bucketListDao.updateBucketList(bucketListtoMove);
		System.out.println("Success in update bucket list");
	}
	//get the integer from String
	private Integer getNumberFromString(String number) throws Exception{
		int num=0;
		for(char c:number.toCharArray()){
			num=(num*10)+c-48;
		}
		
		return num;
	}
	
	@Override
	public boolean updateReview(int reviewId, String review)  throws Exception{
		
		boolean flag;
		
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		
		String username=loginService.getAuthenticatedUser();
		
		GolfCourseReviewDto courseReviewDto=this.destinationSearchService.getReview(reviewId);
		GolfCourseReview courseReview=this.createGolfCourseReviewFrom(courseReviewDto);
		
		courseReview.setReview(review);
		courseReview.setLastUpdatedBy(username);
		courseReview.setLastUpdatedDate(calendar);
		
		try{
			bucketListDao.updateReview(courseReview);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
	public GolfCourseReview createGolfCourseReviewFrom(GolfCourseReviewDto courseReviewDto) throws Exception{
		
		GolfCourseReview golfCourseReview=new GolfCourseReview();
		golfCourseReview.setCreatedBy(courseReviewDto.getCreatedBy());
		golfCourseReview.setCreatedDate(courseReviewDto.getCreatedDate());
		golfCourseReview.setGolfCourse(destinationSearchService.createGolfCourseFrom(courseReviewDto.getGolfCourseDto()));
		golfCourseReview.setLastUpdatedBy(courseReviewDto.getLastUpdatedBy());
		golfCourseReview.setLastUpdatedDate(courseReviewDto.getLastUpdatedDate());
		golfCourseReview.setRating(courseReviewDto.getRating());
		golfCourseReview.setReview(courseReviewDto.getReview());
		golfCourseReview.setReviewId(courseReviewDto.getReviewId());
		golfCourseReview.setSource(courseReviewDto.getSource());
		golfCourseReview.setUser(loginService.createUserFrom(courseReviewDto.getUserDto()));
		/*for(TripcaddieUserDto tripcaddieUserDto:courseReviewDto.getUsersDtos()){
			TripCaddieUser user=loginService.createUserFrom(tripcaddieUserDto);
			golfCourseReview.getUsers().add(user);
		}*/
		return golfCourseReview;
	}
	
	public GolfClubAdviceResponse createGolfClubAdviceResponse(GolfClubAdviceResponseDto clubAdviceResponseDto) throws Exception {
		
		GolfClubAdviceResponse clubAdviceResponse=new GolfClubAdviceResponse();
		clubAdviceResponse.setInquiry(this.createGolfClubInquiryFrom(clubAdviceResponseDto.getAdviceRequestDto()));
		clubAdviceResponse.setAnswer(clubAdviceResponseDto.getAnswer());
		clubAdviceResponse.setCreatedBy(clubAdviceResponseDto.getCreatedBy());
		clubAdviceResponse.setCreatedDate(clubAdviceResponseDto.getCreatedDate());
		clubAdviceResponse.setLastUpdatedBy(clubAdviceResponseDto.getLastUpdatedBy());
		clubAdviceResponse.setLastUpdatedDate(clubAdviceResponseDto.getLastUpdatedDate());
		clubAdviceResponse.setResponseId(clubAdviceResponseDto.getResponseId());
		clubAdviceResponse.setUser(loginService.createUserFrom(clubAdviceResponseDto.getUserDto()));
		for(TripcaddieUserDto tripCaddieUserDto: clubAdviceResponseDto.getUsersDtos()){
			clubAdviceResponse.getUsers().add(loginService.createUserFrom(tripCaddieUserDto));
		}
		
		return clubAdviceResponse;
	}
	
	public GolfClubInquiry createGolfClubInquiryFrom(GolfClubInquiryDto golfClubInquiryDto) throws Exception {
		
		GolfClubInquiry golfClubInquiry=new GolfClubInquiry();
		golfClubInquiry.setCreatedBy(golfClubInquiryDto.getCreatedBy());
		golfClubInquiry.setCreatedDate(golfClubInquiryDto.getCreatedDate());
		golfClubInquiry.setGolfClubInquiryId(golfClubInquiryDto.getgolfClubInquiryId());
		golfClubInquiry.setGolfCourse(destinationSearchService.createGolfCourseFrom(golfClubInquiryDto.getGolfCourseDto()));
		golfClubInquiry.setLastUpdatedBy(golfClubInquiryDto.getLastUpdatedBy());
		golfClubInquiry.setLastUpdatedDate(golfClubInquiryDto.getLastUpdatedDate());
		golfClubInquiry.setQuestion(golfClubInquiryDto.getQuestion());
		golfClubInquiry.setUser(loginService.createUserFrom(golfClubInquiryDto.getUserDto()));
		
		return golfClubInquiry;
	}

	@Override
	public void deleteFromList(int courseId, int visitId) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		
		BucketList bucketList=bucketListDao.getBucketList(tripCaddieUser.getUserId(), courseId, visitId);
		int priority=bucketList.getPriority();
		this.bucketListDao.deleteFromList(bucketList);
		ArrayList<BucketList> bucketLists=bucketListDao.getBucketLists(tripCaddieUser, visitId);
		for(BucketList list:bucketLists){
			if(priority<list.getPriority()){
				list.setPriority(list.getPriority()-1);
				bucketListDao.updateBucketList(list);
			}
		}
	}

	@Override
	public int getRecommendationsCount(int courseId) throws Exception {
		return bucketListDao.getRecommendationsCount(courseId);
	}

	@Override
	public int getInquiryCount(int courseId) throws Exception {
		return bucketListDao.getInquiryCount(courseId);
	}

	@Override
	public int getRatingCount(int courseId, int visitId) throws Exception {
		return 0;
	}

	@Override
	public int getPlacesWantToGoCount() throws Exception {
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		int count=0;
		ArrayList<VisitedPlace> visitedPlaceStatus=bucketListDao.getPlaceVisitedStatus();
		for (VisitedPlace visitedPlace : visitedPlaceStatus) {
			if(visitedPlace.getStatus().equalsIgnoreCase("visited"))
					count=bucketListDao.getBucketListCount(tripCaddieUser.getUserId(), visitedPlace.getPlacesPlayedId());		
		}
		return count;
	}

	@Override
	public int getPlacesIHaveBeenCount() throws Exception {
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		int count=0;
		ArrayList<VisitedPlace> visitedPlaceStatus=bucketListDao.getPlaceVisitedStatus();
		for (VisitedPlace visitedPlace : visitedPlaceStatus) {
			if(visitedPlace.getStatus().equalsIgnoreCase("Not visited"))
					count=bucketListDao.getBucketListCount(tripCaddieUser.getUserId(), visitedPlace.getPlacesPlayedId());		
		}
		return count;
	}

	@Override
	public int getInquiryCount() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReviewCount() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isExistinList(int courseId, int userId) throws Exception {
		return bucketListDao.isExistInList(courseId, userId);
	}

	@Override
	public String getVisitedorNotStatus(int courseId, int userId)
			throws Exception {
		return bucketListDao.getVisitedorNotStatus(courseId, userId);
	}
	@Override
	public ArrayList<GolfClubInquiryDto> getMyInquiries(int firstRow) throws DataAccessException, Exception {
		
		ArrayList<GolfClubAdviceResponseDto> adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
		GolfClubAdviceResponseDto adviceResponseDto=new GolfClubAdviceResponseDto();
		GolfClubInquiryDto clubInquiryDto = new GolfClubInquiryDto();
		
		ArrayList<GolfClubInquiryDto> clubInquiryDtos=new ArrayList<GolfClubInquiryDto>();
				
		String userName=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(userName);
		ArrayList<GolfClubInquiry> clubInquiries=bucketListDao.getMyInquiries(tripcaddieUserDto.getUserId(),firstRow);
		
		for (GolfClubInquiry golfClubInquiry : clubInquiries) {
			
			clubInquiryDto = GolfClubInquiryDto.instantiate(golfClubInquiry);
			clubInquiryDto.setNoOfAdvices(golfClubInquiry.getAdviceResponse().size());
			
			for (GolfClubAdviceResponse adviceResponse : golfClubInquiry.getAdviceResponse()) {
				
				adviceResponseDto = GolfClubAdviceResponseDto.instantiate(adviceResponse);
				adviceResponseDtos.add(adviceResponseDto);
				
			}
			clubInquiryDto.setAdviceResponseDtos(adviceResponseDtos);
			
			clubInquiryDtos.add(clubInquiryDto);
			clubInquiryDto = new GolfClubInquiryDto();
						
		}
		return clubInquiryDtos;
	}

	@Override
	public ArrayList<GolfCourseReviewDto> getMyReviews(int firstRow) throws DataAccessException, Exception {
		
		ArrayList<GolfCourseReviewDto> courseReviewDtos=new ArrayList<GolfCourseReviewDto>();
		String userName=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(userName);
		ArrayList<GolfCourseReview> courseReviews= bucketListDao.getMyReviews(tripcaddieUserDto.getUserId(),firstRow);
		for (GolfCourseReview golfCourseReview : courseReviews) {
			courseReviewDtos.add(GolfCourseReviewDto.instantiate(golfCourseReview));
		}
		return courseReviewDtos;
	}

	@Override
	public int getRowCountForMyInquiries() throws DataAccessException,
			Exception {
		
		String userName=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(userName);
		return bucketListDao.getRowCountForMyInquiries(tripcaddieUserDto.getUserId());
	}

	@Override
	public int getRowCountForMyReviews() throws DataAccessException, Exception {
		
		String userName=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto=loginService.getUserByUserName(userName);
		return bucketListDao.getRowCountForMyReviews(tripcaddieUserDto.getUserId());
	}

	@Override
	public int getBucketListCount(int visitedId) throws Exception {
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		int count=bucketListDao.getBucketListCount(tripCaddieUser.getUserId(), visitedId);		
		return count;
	}

	@Override
	public Integer categorize(int courseId) throws Exception {
		
		BucketList bucketList=new BucketList();
		BucketListPK bucketListPK=new BucketListPK();
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		String username = loginService.getAuthenticatedUser();
		TripCaddieUser user = userDao.getUserByUserName(username);
		
		GolfCourse course = courseDao.getGolfCourse(courseId);
		
		//Which is going to move
		BucketList movingbucketList = bucketListDao.getBucketList(user, course);
		int visitedId=movingbucketList.getPlacesPlayed().getPlacesPlayedId();
		
		bucketListPK.setGolfCourse(movingbucketList.getBucketListPK().getGolfCourse());
		bucketListPK.setTripCaddieUser(movingbucketList.getBucketListPK().getTripCaddieUser());
		bucketList.setBucketListPK(bucketListPK);
		bucketList.setLastUpdatedBy(username);
		bucketList.setLastUpdatedDate(instantTime);
		bucketList.setCreatedBy(movingbucketList.getCreatedBy());
		bucketList.setCreatedDate(movingbucketList.getCreatedDate());
				
		ArrayList<VisitedPlace> visitedPlaces = bucketListDao.getPlaceVisitedStatus();
		
		for (VisitedPlace visitedPlace : visitedPlaces) {
			if(!visitedPlace.getStatus().equalsIgnoreCase(movingbucketList.getPlacesPlayed().getStatus())){
				bucketList.setPlacesPlayed(visitedPlace);
				bucketList.setPriority(bucketListDao.getBucketListCount(user.getUserId(), visitedPlace.getPlacesPlayedId())+1);
				break;
			}
		}
		
		//removing and updating bucketList
		this.deleteFromList(movingbucketList.getBucketListPK().getGolfCourse().getGolfCourseId(), movingbucketList.getPlacesPlayed().getPlacesPlayedId());
		
		//moving list	
		bucketListDao.saveOrUpdateBucketList(bucketList);
		return visitedId;
	}

	@Override
	public ArrayList<GolfClubInquiryDto> getInquiries(int golfCourseId)
			throws Exception {
		
		ArrayList<GolfClubAdviceResponseDto> adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
		GolfClubAdviceResponseDto adviceResponseDto=new GolfClubAdviceResponseDto();
		
		ArrayList<GolfClubInquiryDto> clubInquiryDtos = new ArrayList<GolfClubInquiryDto>();
		
		ArrayList<GolfClubInquiry> clubInquiries = bucketListDao.getInquiries(golfCourseId);
		
		for (GolfClubInquiry golfClubInquiry : clubInquiries) {
			GolfClubInquiryDto clubInquiryDto = GolfClubInquiryDto.instantiate(golfClubInquiry);
			clubInquiryDto.setNoOfAdvices(golfClubInquiry.getAdviceResponse().size());
			
			for (GolfClubAdviceResponse adviceResponse : golfClubInquiry.getAdviceResponse()) {
				adviceResponseDto=(GolfClubAdviceResponseDto.instantiate(adviceResponse));
				adviceResponseDtos.add(adviceResponseDto);
			}
			
			clubInquiryDto.setAdviceResponseDtos(adviceResponseDtos);
			
			clubInquiryDtos.add(clubInquiryDto);
			adviceResponseDtos=new ArrayList<GolfClubAdviceResponseDto>();
		}
		return clubInquiryDtos;
	}
}
