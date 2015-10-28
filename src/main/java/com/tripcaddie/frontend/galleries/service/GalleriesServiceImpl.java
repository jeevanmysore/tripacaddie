package com.tripcaddie.frontend.galleries.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.galleries.dao.GalleriesDao;
import com.tripcaddie.backend.galleries.model.AwardVote;
import com.tripcaddie.backend.galleries.model.AwardVoteCount;
import com.tripcaddie.backend.galleries.model.Awards;
import com.tripcaddie.backend.galleries.model.AwardsComment;
import com.tripcaddie.backend.galleries.model.PictureComment;
import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFilePK;
import com.tripcaddie.backend.galleries.model.RatingVideoFile;
import com.tripcaddie.backend.galleries.model.RatingVideoFilePK;
import com.tripcaddie.backend.galleries.model.VideoComment;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.itinerary.dao.ActivityDao;
import com.tripcaddie.backend.itinerary.dao.ItineraryDao;
import com.tripcaddie.backend.itinerary.model.Activity;
import com.tripcaddie.backend.itinerary.model.RoundScore;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.Expense;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.WinningAmount;
import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.galleries.dto.AwardCommentDto;
import com.tripcaddie.frontend.galleries.dto.AwardsDto;
import com.tripcaddie.frontend.galleries.dto.PictureCommentDto;
import com.tripcaddie.frontend.galleries.dto.PictureFileDto;
import com.tripcaddie.frontend.galleries.dto.VideoCommentDto;
import com.tripcaddie.frontend.galleries.dto.VideoFileDto;
import com.tripcaddie.frontend.itinerary.dto.RoundScoreDto;
import com.tripcaddie.frontend.trip.dto.ExpenseDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.dto.WinningAmountDto;
import com.tripcaddie.frontend.trip.service.RecentUpdateService;
import com.tripcaddie.frontend.trip.service.TripService;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("galleriesService")
public class GalleriesServiceImpl implements GalleriesService {

	@Resource(name = "galleriesDao")
	private GalleriesDao galleriesDao;
	@Resource(name = "loginService")
	private LoginService loginService;
	@Resource(name = "tripDao")
	private TripDao tripDao;

	@Resource(name = "recentUpdateService")
	private RecentUpdateService recentUpdateService;

	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "activityyDao")
	private ActivityDao activityDao;
	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "itineraryDao")
	private ItineraryDao itineraryDao;

	@Override
	public ArrayList<PictureFileDto> getPicturesByDate(int tripId)
			throws Exception {

		ArrayList<PictureFile> pictureFiles = galleriesDao
				.getPicturesByDate(tripId);
		ArrayList<PictureFileDto> pictureFileDtos = InstantiatePicture(pictureFiles);
		return pictureFileDtos;
	}

	private ArrayList<PictureFileDto> InstantiatePicture(
			ArrayList<PictureFile> pictureFiles) {
		ArrayList<PictureFileDto> pictureFileDtos = new ArrayList<PictureFileDto>();
		for (PictureFile pictureFile : pictureFiles) {

			PictureFileDto pictureFileDto = PictureFileDto
					.instantiate(pictureFile);
			pictureFileDtos.add(pictureFileDto);
		}
		return pictureFileDtos;
	}

	@Override
	public PictureFileDto getPicture(int pictureId, int tripId)
			throws Exception {

		PictureFile pictureFile = galleriesDao.getPicture(pictureId, tripId);
		PictureFileDto pictureFileDto = PictureFileDto.instantiate(pictureFile);
		List<RatingPictureFile> ratingPictureFiles = galleriesDao
				.getPictureAvgRating(pictureId);
		double avg = 0;
		for (RatingPictureFile ratingPictureFile : ratingPictureFiles) {
			avg = avg + ratingPictureFile.getRating();
		}
		pictureFileDto.setAvgRating((int) (avg / ratingPictureFiles.size()));
		return pictureFileDto;
	}

	@Override
	public int getMaxPictureId() throws Exception {
		return galleriesDao.getMaxIdInPicture();
	}

	@Override
	public void addPicture(int tripId, String filename, String description)
			throws Exception {

		PictureFile pictureFile = new PictureFile();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		Trip trip = tripDao.getTrip(tripId);
		TripcaddieUserDto user = loginService.getUserByUserName(username);
		TripMember member = tripDao.getTripMember(user, tripId);

		pictureFile.setCreatedBy(username);
		pictureFile.setCreatedDate(instantTime);
		pictureFile.setDescription(description);
		pictureFile.setPictureName(filename);
		pictureFile.setTrip(trip);
		pictureFile.setTripMember(member);
		pictureFile.setAvgRating((double) 0);
		pictureFile.setNooffav(0);

		galleriesDao.addPicture(pictureFile);
		recentUpdateService.logUpdates(tripId, "Uploaded a Picture",
				"participant");

	}

	@Override
	public int getNoOfcomments(int pictureId) throws Exception {
		return galleriesDao.getNoOfcomments(pictureId);
	}

	@Override
	public ArrayList<PictureCommentDto> getPictureComments(int pictureId)
			throws Exception {
		ArrayList<PictureCommentDto> pictureCommentDtos = new ArrayList<PictureCommentDto>();
		ArrayList<PictureComment> pictureComments = galleriesDao
				.getPictureComments(pictureId);

		for (PictureComment pictureComment : pictureComments) {
			pictureCommentDtos.add(PictureCommentDto
					.instantiate(pictureComment));
		}
		return pictureCommentDtos;
	}

	@Override
	public void deletePicture(int pictureId, int tripId) throws Exception {

		PictureFile pictureFile = galleriesDao.getPicture(pictureId, tripId);
		galleriesDao.deletePicture(pictureFile);

	}

	@Override
	public void addComment(int pictureId, String comment) throws Exception {

		PictureFile pictureFile = galleriesDao.getPicture(pictureId);
		PictureComment pictureComment = new PictureComment();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto = loginService
				.getUserByUserName(username);
		TripMember tripMember = tripDao.getTripMember(tripcaddieUserDto,
				pictureFile.getTrip().getTripId());

		pictureComment.setComments(comment);
		pictureComment.setCreatedBy(username);
		pictureComment.setCreatedDate(instantTime);
		pictureComment.setLastUpdatedDate(instantTime);
		pictureComment.setPictureFile(pictureFile);
		pictureComment.setTripMember(tripMember);

		galleriesDao.addComment(pictureComment);

	}

	@Override
	public void deleteComment(int commentId) throws Exception {

		PictureComment pictureComment = galleriesDao
				.getPictureComment(commentId);
		galleriesDao.deleteComment(pictureComment);

	}

	@Override
	public void editPictureComment(int commentId, String comment)
			throws Exception {

		PictureComment pictureComment = galleriesDao
				.getPictureComment(commentId);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		pictureComment.setComments(comment);
		pictureComment.setLastUpdatedBy(username);
		pictureComment.setLastUpdatedDate(instantTime);

		galleriesDao.editComment(pictureComment);

	}

	@Override
	public void editPicture(int pictureId, int tripId, String description)
			throws Exception {

		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		PictureFile file = galleriesDao.getPicture(pictureId, tripId);
		file.setDescription(description);
		file.setLastUpdatedBy(username);
		file.setLastUpdatedDate(instantTime);

		galleriesDao.editPicture(file);

	}

	@Override
	public ArrayList<VideoFileDto> getVideosByDate(int tripId) throws Exception {

		ArrayList<VideoFile> videoFiles = galleriesDao.getVideosByDate(tripId);
		ArrayList<VideoFileDto> videoFileDtos = instantiateVideo(videoFiles);
		return videoFileDtos;
	}

	private ArrayList<VideoFileDto> instantiateVideo(
			ArrayList<VideoFile> videoFiles) {
		ArrayList<VideoFileDto> videoFileDtos = new ArrayList<VideoFileDto>();
		for (VideoFile videoFile : videoFiles) {
			VideoFileDto videoFileDto = VideoFileDto.instantiate(videoFile);
			videoFileDtos.add(videoFileDto);
		}
		return videoFileDtos;
	}

	@Override
	public int getNoOfCommentsperVideo(int videoid) throws Exception {
		return galleriesDao.getNoOfCommentsperVideo(videoid);
	}

	@Override
	public int getMaxIdInVideo() throws Exception {
		return galleriesDao.getMaxIdInVideo();
	}

	@Override
	public void addVideo(int tripId, String filename, String description,
			String tbimagename) throws Exception {
		VideoFile videoFile = new VideoFile();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		Trip trip = tripDao.getTrip(tripId);
		TripcaddieUserDto user = loginService.getUserByUserName(username);
		TripMember member = tripDao.getTripMember(user, tripId);

		videoFile.setCreatedBy(username);
		videoFile.setCreatedDate(instantTime);
		videoFile.setDescription(description);
		videoFile.setVideoName(filename);
		videoFile.setVideothumbnailname(tbimagename);
		videoFile.setTrip(trip);
		videoFile.setTripMember(member);
		videoFile.setAvgRating((double) 0);
		videoFile.setNooffav(0);

		galleriesDao.addVideo(videoFile);
		recentUpdateService.logUpdates(tripId, "Uploaded a Video",
				"participant");

	}

	@Override
	public VideoFileDto getVideo(int videoId, int tripId) throws Exception {
		VideoFile videoFile = galleriesDao.getVideo(videoId, tripId);

		VideoFileDto videoFileDto = VideoFileDto.instantiate(videoFile);
		List<RatingVideoFile> ratingVideoFiles = galleriesDao
				.getVideoAvgRating(videoFile.getVideoId());
		double avg = 0;
		for (RatingVideoFile ratingVideoFile : ratingVideoFiles) {
			avg = avg + ratingVideoFile.getRating();
		}
		videoFileDto.setAvgRating((int) (avg / ratingVideoFiles.size()));

		return videoFileDto;
	}

	@Override
	public ArrayList<VideoCommentDto> getVideoComments(int videoId)
			throws Exception {
		ArrayList<VideoCommentDto> videoCommentDtos = new ArrayList<VideoCommentDto>();
		ArrayList<VideoComment> videoComments = galleriesDao
				.getVideoComments(videoId);

		for (VideoComment videoComment : videoComments) {
			videoCommentDtos.add(VideoCommentDto.instantiate(videoComment));
		}
		return videoCommentDtos;
	}

	@Override
	public void addCommentforVideo(int videoId, String comment)
			throws Exception {
		VideoFile videoFile = galleriesDao.getVideo(videoId);
		VideoComment videoComment = new VideoComment();
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto = loginService
				.getUserByUserName(username);
		TripMember tripMember = tripDao.getTripMember(tripcaddieUserDto,
				videoFile.getTrip().getTripId());

		videoComment.setComment(comment);
		videoComment.setCreatedBy(username);
		videoComment.setCreatedDate(instantTime);
		videoComment.setLastUpdatedDate(instantTime);
		videoComment.setVideoFile(videoFile);
		videoComment.setTripMember(tripMember);

		galleriesDao.addCommentforVideo(videoComment);

	}

	@Override
	public void deleteCommentforVideo(int commentId) throws Exception {
		VideoComment videoComment = galleriesDao.getVideoComment(commentId);
		galleriesDao.deleteComment(videoComment);

	}

	@Override
	public void editCommentforVideo(int commentId, String comment)
			throws Exception {
		VideoComment videoComment = galleriesDao.getVideoComment(commentId);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		videoComment.setComment(comment);
		videoComment.setLastUpdatedBy(username);
		videoComment.setLastUpdatedDate(instantTime);

		galleriesDao.editComment(videoComment);

	}

	@Override
	public void updateEntity(Object entity) {
		galleriesDao.updateEntity(entity);

	}

	@Override
	public VideoFile getVideoFile(int videoId, int tripId) throws Exception {
		return galleriesDao.getVideo(videoId, tripId);

	}

	@Override
	public PictureFile getPictureFile(int pictureId, int tripId)
			throws Exception {
		return galleriesDao.getPicture(pictureId, tripId);
	}

	@Override
	public ArrayList<PictureFile> getPicturesFileByDate(int tripId)
			throws Exception {
		ArrayList<PictureFile> pictureFiles = galleriesDao
				.getPicturesByDate(tripId);
		return pictureFiles;
	}

	@Override
	public ArrayList<VideoFile> getVideosFileByDate(int tripId)
			throws Exception {
		ArrayList<VideoFile> videoFiles = galleriesDao.getVideosByDate(tripId);
		return videoFiles;
	}

	@Override
	public void updateorAddPicRating(int tripId, int pictureId, int rating)
			throws Exception {
		RatingPictureFile ratingPictureFile = null;
		PictureFile pictureFile = galleriesDao.getPicture(pictureId, tripId);
		TripMember member = pictureFile.getTripMember();

		// get rating of picture
		ratingPictureFile = galleriesDao.getPictureRating(pictureId,
				member.getMemberId());

		if (ratingPictureFile == null) {
			ratingPictureFile = new RatingPictureFile();
			ratingPictureFile.setRating(Double.parseDouble(String
					.valueOf(rating)));
			RatingPictureFilePK pk = new RatingPictureFilePK();
			pk.setPictureFile(pictureFile);
			pk.setTripMember(member);
			ratingPictureFile.setRatingPictureFilePK(pk);
			galleriesDao.saveorUpdateEntity(ratingPictureFile);
		} else {
			ratingPictureFile.setRating(Double.parseDouble(String
					.valueOf(rating)));
			galleriesDao.saveorUpdateEntity(ratingPictureFile);
		}

	}

	@Override
	public void updateorAddVideoRating(int tripId, int videoId, int rating)
			throws Exception {
		RatingVideoFile ratingVideoFile = null;
		VideoFile videoFile = galleriesDao.getVideo(videoId, tripId);
		TripMember member = videoFile.getTripMember();

		// get rating of picture
		ratingVideoFile = galleriesDao.getVideoRating(videoId,
				member.getMemberId());

		if (ratingVideoFile == null) {
			ratingVideoFile = new RatingVideoFile();
			ratingVideoFile
					.setRating(Double.parseDouble(String.valueOf(rating)));
			RatingVideoFilePK pk = new RatingVideoFilePK();
			pk.setVideoFile(videoFile);
			pk.setTripMember(member);
			ratingVideoFile.setRatingVideoFilePK(pk);
			galleriesDao.saveorUpdateEntity(ratingVideoFile);
		} else {
			ratingVideoFile
					.setRating(Double.parseDouble(String.valueOf(rating)));
			galleriesDao.saveorUpdateEntity(ratingVideoFile);
		}

	}

	@Override
	public ArrayList<PictureFileDto> getPicturesByRating(int tripId)
			throws Exception {
		ArrayList<PictureFile> pictureFiles = galleriesDao
				.getPicturesByRating(tripId);
		ArrayList<PictureFileDto> pictureFileDtos = InstantiatePicture(pictureFiles);
		return pictureFileDtos;
	}

	@Override
	public ArrayList<PictureFileDto> getPicturesByFav(int tripId)
			throws Exception {
		ArrayList<PictureFile> pictureFiles = galleriesDao
				.getPicturesByFav(tripId);
		ArrayList<PictureFileDto> pictureFileDtos = InstantiatePicture(pictureFiles);
		return pictureFileDtos;
	}

	@Override
	public ArrayList<VideoFileDto> getVideosByRating(int tripId)
			throws Exception {
		ArrayList<VideoFile> videoFiles = galleriesDao
				.getVideosByRating(tripId);
		ArrayList<VideoFileDto> videoFileDtos = instantiateVideo(videoFiles);
		return videoFileDtos;
	}

	@Override
	public ArrayList<VideoFileDto> getVideosByFav(int tripId) throws Exception {
		ArrayList<VideoFile> videoFiles = galleriesDao.getVideosByFav(tripId);
		ArrayList<VideoFileDto> videoFileDtos = instantiateVideo(videoFiles);
		return videoFileDtos;
	}

	@Override
	public List<AwardsDto> getAwardsWon(int tripId) throws Exception {

		List<AwardsDto> awardsDtos = new ArrayList<AwardsDto>();
		List<Awards> awardsList = new ArrayList<Awards>();
		awardsList.addAll(galleriesDao.getAwardsbyDate(tripId));
		awardsList.addAll(galleriesDao.getAwardsbydirectlyAwarded(tripId));
		for (Awards awards : awardsList) {
			if (awards.getAwarded() == 1) {
				// directly awarded
				AwardVote awardVote = galleriesDao.getAwardVotebyId(awards
						.getAwardid());

				AwardsDto awardsDto = AwardsDto.instantiate(awards);
				awardsDto.setNominee(TripMemberDto.instantiate(awardVote
						.getNomineeid()));
				awardsDto.setCommentcount(galleriesDao
						.getNoOfcommentsAward(awards.getAwardid()));
				awardsDtos.add(awardsDto);

			} else {

				AwardVoteCount awardVoteCount = galleriesDao
						.getMaxVoteforAward(awards.getAwardid());
				if (awardVoteCount != null) {
					AwardsDto awardsDto = AwardsDto.instantiate(awards);
					awardsDto.setNominee(TripMemberDto
							.instantiate(awardVoteCount
									.getMember()));
					awardsDto.setCommentcount(galleriesDao
							.getNoOfcommentsAward(awards.getAwardid()));
					awardsDtos.add(awardsDto);
				}
			}
		}
		return awardsDtos;
	}

	@Override
	public List<AwardsDto> getAwardsPending(int tripId) throws Exception {
		int loggedinUserId = userDao.getUserByUserName(
				loginService.getAuthenticatedUser()).getUserId();

		List<AwardsDto> awardsDtos = new ArrayList<AwardsDto>();
		List<Awards> awardsPending=galleriesDao.getAwardsPendingbyDate(tripId);
		for (Awards awards : awardsPending) {
			AwardsDto awardsDto = AwardsDto.instantiate(awards);
			awardsDto.setVotenow("0"); // default
			awardsDto.setVotecount("counted");
			// get Top 3 Nominees
			List<AwardVoteCount> voteCounts = galleriesDao
					.getTop3Nominees(awards.getAwardid());
			List<TripMemberDto> memberDtos = null;
			if (voteCounts != null && !voteCounts.isEmpty()) {
				memberDtos = new ArrayList<TripMemberDto>();
				for (AwardVoteCount awardVoteCount : voteCounts) {
					TripMember member = awardVoteCount.getMember();
					TripMemberDto memberDto = TripMemberDto.instantiate(member);
					memberDto.setVotecount(awardVoteCount.getCount());
					memberDtos.add(memberDto);
				}
				awardsDto.setNominees(memberDtos);

			} else {
				awardsDto.setVotecount("notcounted");
			}

			// To enable votenow for other members
			if (!awardsDto.getVotecount().equals("notcounted")) {
				List<AwardVote> awardVotes = galleriesDao
						.getAwardVoteByid(awards.getAwardid());
				for (AwardVote awardVote : awardVotes) {
					if (awardVote.getMember()
							.getTripCaddieUser().getUserId() == loggedinUserId) {
						// 2 means votenow should not be available
						awardsDto.setVotenow("2");
					}
				}
				if (!awardsDto.getVotenow().equalsIgnoreCase("2")) {
					// 1 means votenow should be available
					awardsDto.setVotenow("1");
				}
			}
			awardsDtos.add(awardsDto);
		}
		return awardsDtos;
	}

	@Override
	public int getMaxAwardId() {
		return galleriesDao.getMaxAwardId();
	}

	@Override
	public void createPoll(String awardname, String imagename,
			String pollenddate, String tripId) throws Exception {
		Awards awards = setAward(awardname, imagename, tripId);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		awards.setPollenddate(dateFormat.parse(pollenddate));
		awards.setAwarded(0);
		galleriesDao.saveorUpdateEntity(awards);
	}

	private Awards setAward(String awardname, String imagename, String tripId)
			throws Exception {
		Awards awards = new Awards();
		awards.setAwardname(awardname);
		awards.setCreateddate(new Date());
		awards.setImagename(imagename);

		TripCaddieUser tripCaddieUser = userDao.getUserByUserName(loginService
				.getAuthenticatedUser());
		TripMember member = tripDao.getTripMember(tripCaddieUser.getUserId(),
				Integer.valueOf(tripId));
		Trip trip = tripDao.getTrip(Integer.valueOf(tripId));

		awards.setTrip(trip);
		awards.setCreatedby(member);

		return awards;
	}

	@Override
	public int insertAwardnow(String awardname, String imagename, String tripId)
			throws Exception {
		Awards awards = setAward(awardname, imagename, tripId);
		awards.setPollenddate(new Date());
		awards.setAwarded(1);
	//	int awardId = galleriesDao.getMaxAwardId() + 1;
		//awards.setAwardid(awardId);
		int awardId=galleriesDao.saveAwards(awards);
		return awardId;
	}

	@Override
	public ArrayList<AwardCommentDto> getAwardComment(int awardId)
			throws Exception {
		ArrayList<AwardCommentDto> awardCommentDtos = new ArrayList<AwardCommentDto>();
		ArrayList<AwardsComment> awardsComments = galleriesDao
				.getAwardsComments(awardId);

		for (AwardsComment awardsComment : awardsComments) {
			awardCommentDtos.add(AwardCommentDto.instantiate(awardsComment));
		}
		return awardCommentDtos;
	}

	@Override
	public void editAwardComment(int commentID, String comment)
			throws Exception {
		AwardsComment awardsComment = galleriesDao
				.getAwardsCommentbyId(commentID);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		String username = loginService.getAuthenticatedUser();

		awardsComment.setComment(comment);
		awardsComment.setLastUpdatedBy(username);
		awardsComment.setLastUpdatedDate(instantTime);

		galleriesDao.editComment(awardsComment);
	}

	@Override
	public void deleteCommentforAward(int commentID) throws Exception {
		AwardsComment awardsComment = galleriesDao
				.getAwardsCommentbyId(commentID);
		galleriesDao.deleteComment(awardsComment);
	}

	@Override
	public void addCommentforAward(int awardid, String comment ,String datetime)
			throws Exception {
		Awards awards = galleriesDao.getAwards(awardid);
		AwardsComment awardsComment = new AwardsComment();
		
		Calendar instantTime = new GregorianCalendar();
		Date date = new Date(Long.valueOf(datetime));
		instantTime.setTime(date);

		String username = loginService.getAuthenticatedUser();
		TripcaddieUserDto tripcaddieUserDto = loginService
				.getUserByUserName(username);
		TripMember tripMember = tripDao.getTripMember(tripcaddieUserDto, awards
				.getTrip().getTripId());

		awardsComment.setComment(comment);
		awardsComment.setCreatedBy(username);
		awardsComment.setCreatedDate(instantTime);
		awardsComment.setLastUpdatedDate(instantTime);
		awardsComment.setAward(awards);
		awardsComment.setTripMember(tripMember);

		galleriesDao.saveorUpdateEntity(awardsComment);

	}

	@Override
	public void deleteAward(int awardId, int tripId) throws Exception {
		Awards awards = galleriesDao.getAwards(awardId);
		galleriesDao.deleteEntity(awards);

	}

	@Override
	public void insertAwardsVote(String tripId, String awardId, String nomineeId)
			throws Exception {
		Awards awards = galleriesDao.getAwards(Integer.parseInt(awardId));

		AwardVote awardVote = new AwardVote();
		awardVote.setAward(awards);
		awardVote.setMember(awards.getCreatedby());
		TripMember nomineeid = tripDao
				.getTripMember(Integer.valueOf(nomineeId));
		awardVote.setNomineeid(nomineeid);
		galleriesDao.saveEntity(awardVote);

		// Add or Update Award Vote Count

		AwardVoteCount awardVoteCount = galleriesDao.getAwardVoteCount(
				awards.getAwardid(), Integer.valueOf(nomineeId));
		if (awardVoteCount != null) {
			// update
			awardVoteCount.setCount(awardVoteCount.getCount() + 1);
			galleriesDao.updateEntity(awardVoteCount);
		} else {
			// insert
			awardVoteCount = new AwardVoteCount();
			awardVoteCount.setAward(awards);
			awardVoteCount.setMember(nomineeid);
			awardVoteCount.setCount(1);
			galleriesDao.saveEntity(awardVoteCount);
		}
	}

	@Override
	public List<Expense> getExpensesbyTripMember(int memberId) {
		return galleriesDao.getExpensesbyTripMember(memberId);

	}

	@Override
	public List<Object> getCalculatedExpense(int tripId) throws Exception {
		Trip trip = tripDao.getTrip(tripId);
		List<Object> objects = new ArrayList<Object>();
		Integer maxExpenseSize = 0;
		List<TripMemberDto> memberDtos = new ArrayList<TripMemberDto>();
		List<TripMember> tripMembers = tripDao.getTripMembersbyOrder(tripId);
		for (TripMember member : tripMembers) {

			TripMemberDto memberDto = TripMemberDto.instantiate(member);

			List<Expense> expenses = getExpensesbyTripMember(member
					.getMemberId());
			List<ExpenseDto> expenseDtos = new ArrayList<ExpenseDto>();
			// To get max Expense size
			if (expenses != null) {
				if (maxExpenseSize == 0) {
					maxExpenseSize = expenses.size();
				} else if (maxExpenseSize < expenses.size()) {
					maxExpenseSize = expenses.size();
				}
			}

			double totalexpense = 0;
			if (expenses != null && !expenses.isEmpty()) {
				for (Expense expense : expenses) {
					// calculate Total Expenses
					totalexpense = totalexpense + expense.getAmount();
					expenseDtos.add(ExpenseDto.instantiate(expense));
				}
				memberDto.setExpensesize(expenseDtos.size());
			} else {
				memberDto.setExpensesize(0);
			}
			memberDto.setTotalexpense(totalexpense);
			memberDto.setExpenses(expenseDtos);

			// evaluvate Winning Amount
			WinningAmount winningAmount = galleriesDao.getWinningAmount(member
					.getMemberId());
			if (winningAmount != null)
				memberDto.setWinningAmount(WinningAmountDto
						.instantiate(winningAmount));

			memberDtos.add(memberDto);
		}

		// Calculate Owe and Collect
		if (memberDtos != null && !memberDtos.isEmpty())
			calculateOweCollect(memberDtos, trip.getWagerFee());

		objects.add(memberDtos);
		objects.add(maxExpenseSize);
		return objects;
	}

	private void calculateOweCollect(List<TripMemberDto> memberDtos,
			Double wagerFee) {
		if (wagerFee == null) {
			wagerFee = (double) 0;
		}
		double totalExpenseAllTripmembr = 0;
		for (TripMemberDto tripMemberDto : memberDtos) {
			totalExpenseAllTripmembr = totalExpenseAllTripmembr
					+ tripMemberDto.getTotalexpense();
		}
		double avgExpense = totalExpenseAllTripmembr / memberDtos.size();
		for (TripMemberDto memberDto : memberDtos) {

			// To avoid null pointer for winning Amount
			double WinningAmount;
			if (memberDto.getWinningAmount() == null
					|| memberDto.getWinningAmount().getWinningAmount() == null) {
				WinningAmount = 0;
			} else {
				WinningAmount = memberDto.getWinningAmount().getWinningAmount();
			}

			memberDto.setAmoutCollect(0);
			memberDto.setAmountOwe(0);
			if (memberDto.getTotalexpense() >= avgExpense) {
				double collect = memberDto.getTotalexpense() - avgExpense;
				memberDto.setAmoutCollect(collect);
			} else if (memberDto.getTotalexpense() <= avgExpense) {
				double owe = avgExpense - memberDto.getTotalexpense();
				memberDto.setAmountOwe(owe);
			}

			if (wagerFee > memberDto.getAmoutCollect()) {
				memberDto.setAmountOwe(memberDto.getAmountOwe()
						+ (wagerFee - memberDto.getAmoutCollect()));
				memberDto.setAmoutCollect(0);
			} else if (wagerFee <= memberDto.getAmoutCollect()) {
				memberDto.setAmoutCollect(memberDto.getAmoutCollect()
						- wagerFee);
			}

			if (WinningAmount > memberDto.getAmountOwe()) {
				memberDto.setAmoutCollect(memberDto.getAmoutCollect()
						+ (WinningAmount - memberDto.getAmountOwe()));
				memberDto.setAmountOwe(0);
			} else if (WinningAmount <= memberDto.getAmountOwe()) {
				memberDto
						.setAmountOwe(memberDto.getAmountOwe() - WinningAmount);
			}
		}
	}

	@Override
	public void createExpense(String Title, String Amount, String expenseDate,
			String memberId) throws Exception {
		Expense expense = new Expense();
		expense.setAmount(Double.parseDouble(Amount));
		expense.setTitle(Title);
		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		expense.setCreatedDate(instantTime);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(expenseDate));
		expense.setExpenseDate(calendar);
		TripMember member = tripDao.getTripMember(Integer.parseInt(memberId));
		expense.setCreatedBy(member.getTripCaddieUser().getFirstName());
		expense.setTripMember(member);
		galleriesDao.saveorUpdateEntity(expense);

	}

	@Override
	public void createWinning(String amount, String memberId) throws Exception {
		WinningAmount winningAmount = new WinningAmount();
		winningAmount.setWinningAmount(Double.parseDouble(amount));

		Calendar instantTime = Calendar.getInstance();
		Date date = new Date();
		instantTime.setTime(date);
		winningAmount.setCreatedDate(instantTime);
		TripMember member = tripDao.getTripMember(Integer.parseInt(memberId));
		winningAmount.setCreatedBy(member.getTripCaddieUser().getFirstName());

		winningAmount.setTripMember(member);
		galleriesDao.saveorUpdateEntity(winningAmount);

	}

	@Override
	public void editWinning(String amount, String winningId) {
		WinningAmount winningAmount = galleriesDao.getWinningbyId(Integer
				.parseInt(winningId));
		winningAmount.setWinningAmount(Double.parseDouble(amount));
		galleriesDao.updateEntity(winningAmount);
	}

	@Override
	public void updateWager(String amount, String tripId) throws Exception {
		Trip trip = tripDao.getTrip(Integer.parseInt(tripId));
		trip.setWagerFee(Double.parseDouble(amount));
		galleriesDao.updateEntity(trip);
	}

	@Override
	public void updateExpense(String title, String amount, String date,
			String expenseId) throws Exception {
		Expense expense = galleriesDao.getExpensebyId(Integer
				.parseInt(expenseId));
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		expense.setExpenseDate(calendar);
		expense.setAmount(Double.parseDouble(amount));
		expense.setTitle(title);
		Calendar instantTime = Calendar.getInstance();
		Date date2 = new Date();
		instantTime.setTime(date2);
		expense.setLastUpdatedDate(instantTime);
		expense.setLastUpdatedBy(expense.getTripMember().getTripCaddieUser()
				.getFirstName());
		galleriesDao.saveorUpdateEntity(expense);

	}

	@Override
	public ExpenseDto getExpense(int ExpenseId) {
		return ExpenseDto.instantiate(galleriesDao.getExpensebyId(ExpenseId));
	}

	@Override
	public List<Object> getLeaderboardbyTrip(int tripId) throws Exception {
		List<Object> objects = new ArrayList<Object>();
		List<TripMemberDto> memberDtos = tripService.getTripMembers(tripId);
		List<Activity> activities = activityDao.getActivitiesforLeaderbrd(
				tripId, 1);
		// inserting memberid and round id for first time
		insertEntity(tripId, activities);
		for (TripMemberDto memberDto : memberDtos) {
			memberDto.setRoundSize(0);
			if (activities != null && !activities.isEmpty()) {
				memberDto.setRoundSize(activities.size());
				List<RoundScore> roundScores = itineraryDao
						.getRoundScore(memberDto.getMemberId());
				if (roundScores != null && !roundScores.isEmpty()) {
					List<RoundScoreDto> roundScoreDtos = new ArrayList<RoundScoreDto>();
					int total = 0;
					for (RoundScore roundScore : roundScores) {
						total = total + roundScore.getTotal();
						roundScoreDtos.add(RoundScoreDto
								.instantiate(roundScore));
					}
					memberDto.setRoundScores(roundScoreDtos);
					memberDto.setTotal(total);
					// update total
					TripMember member = tripDao.getTripMember(memberDto
							.getMemberId());
					member.setTotal(total);
					tripDao.updateTripMember(member);
				}
			}
		}
		objects.add(activities);
		objects.add(memberDtos);
		return objects;
	}

	private void insertEntity(int tripId, List<Activity> activities)
			throws Exception {
		List<TripMember> members = tripService.getTripMembersbyOrder(tripId);
		for (TripMember member : members) {

			for (Activity activity : activities) {

				RoundScore roundScore = itineraryDao
						.getRoundScorebyActivityndTrip(
								activity.getActivityId(), member.getMemberId());
				if (roundScore == null) {
					RoundScore roundScore2 = new RoundScore();
					roundScore2.setActivity(activity);
					roundScore2.setMember(member);
					itineraryDao.insertEntity(roundScore2);
				}
			}
		}

	}

	@Override
	public List<RoundScoreDto> getActivities(int roundId) {
		List<RoundScoreDto> roundScoreDtos = new ArrayList<RoundScoreDto>();
		List<RoundScore> scores = itineraryDao.getRoundScores(roundId);
		for (RoundScore roundScore : scores) {
			roundScoreDtos.add(RoundScoreDto.instantiate(roundScore));
		}
		return roundScoreDtos;
	}

	@Override
	public void updateRound(int roundId, int memberId, int front, int back,
			int total) {
		RoundScore roundScore = itineraryDao.getRoundScorebyActivityndTrip(
				roundId, memberId);
		roundScore.setBack9(back);
		roundScore.setFront9(front);
		roundScore.setTotal(total);
		galleriesDao.updateEntity(roundScore);

	}

	@Override
	public List<TripMemberDto> setPosition(List<TripMemberDto> memberDtos) {

		List<TripMemberDto> appendList = new ArrayList<TripMemberDto>();
		Collections.sort(memberDtos, new Comparator<TripMemberDto>() {

			@Override
			public int compare(TripMemberDto o1, TripMemberDto o2) {
				return o1.getTotal().compareTo(o2.getTotal());
			}
		});
		int i = 1;
		for (TripMemberDto memberDto : memberDtos) {
			List<RoundScore> roundScores = itineraryDao.getRoundScore(memberDto
					.getMemberId());
			if (MemberhasAllrounds(roundScores)) {
				memberDto.setPosiotion(i);
				i++;
			} else {
				memberDto.setPosiotion(0);
			}
		}
		// put position 0 at last ---- Sort
		for (TripMemberDto memberDto : memberDtos) {
			if (memberDto.getPosiotion() == 0) {
				appendList.add(memberDto);
			}
		}
		memberDtos.removeAll(appendList);
		memberDtos.addAll(appendList);
		return memberDtos;
	}

	private boolean MemberhasAllrounds(List<RoundScore> roundScores) {
		if(roundScores==null || roundScores.isEmpty()){
			return false;
		}
		for (RoundScore roundScore : roundScores) {
			if (roundScore.getTotal() == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void updateLock(int tripId, int lock) throws Exception {
		Trip trip = tripDao.getTrip(tripId);
		trip.setLeaderboardlock(lock);
		tripDao.updateTrip(trip);
	}

	@Override
	public void updateStLock(int tripId, int lock) throws Exception {

		Trip trip = tripDao.getTrip(tripId);
		trip.setStrudellock(lock);
		tripDao.updateTrip(trip);
	}

	@Override
	public void deleteWager(int tripId) throws Exception {
		Trip trip = tripDao.getTrip(tripId);
		trip.setWagerFee(null);
		tripDao.updateTrip(trip);

	}

	@Override
	public void insertNomineforAwardNow(int awardId, String memberId)
			throws Exception {
		TripMember nomineeid = tripDao.getTripMember(Integer.valueOf(memberId));
		Awards awards2 = galleriesDao.getAwards(awardId);
		if(awards2!=null){
			System.out.println("AWARDS FOR AWARDID NOT NULL"+awardId);
		}
		System.out.println(awards2);
		System.out.println(awards2.getAwardid());
		System.out.println(awards2.getAwardname());
		System.out.println(awards2.getPollenddate());
		System.out.println(awards2.getCreatedby());
		AwardVote awardVote = new AwardVote();
		awardVote.setAward(awards2);
		awardVote.setMember(awards2.getCreatedby());
		
		awardVote.setNomineeid(nomineeid);
		galleriesDao.saveEntity(awardVote);

	}

	@Override
	public ArrayList<AwardsDto> getAwardsWonForUserId(int userId) throws Exception {
		
		ArrayList<TripDto> tripDtos = tripService.getTripsOfUser(userId);
		
		//get awards won by user
		ArrayList<AwardsDto> awardsDtos=new ArrayList<AwardsDto>();
		for (TripDto tripDto : tripDtos) {
			ArrayList<AwardsDto> awarddtos =(ArrayList<AwardsDto>) this.getAwardsWon(tripDto.getTripId());
			for (AwardsDto awardsDto : awarddtos) {
				if(awardsDto.getNominee().getTripCaddieUserDto().getUserId() == userId){
					awardsDtos.add(awardsDto);
				}
			}
		}

		return awardsDtos;
	}

}
