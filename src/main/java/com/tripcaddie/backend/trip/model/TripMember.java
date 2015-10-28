package com.tripcaddie.backend.trip.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tripcaddie.backend.galleries.model.AwardVote;
import com.tripcaddie.backend.galleries.model.AwardVoteCount;
import com.tripcaddie.backend.galleries.model.Awards;
import com.tripcaddie.backend.galleries.model.AwardsComment;
import com.tripcaddie.backend.galleries.model.PictureComment;
import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.RatingPictureFile;
import com.tripcaddie.backend.galleries.model.RatingVideoFile;
import com.tripcaddie.backend.galleries.model.VideoComment;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.backend.itinerary.model.RoundScore;
import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;

@Entity
@Table(name="trip_members")
public class TripMember {
	
	@Id @GeneratedValue
	@Column(name="member_id")
	private int memberId;
	@ManyToOne
	@JoinColumn(name="trip_id")
	private Trip trip;
	@ManyToOne
	@JoinColumn(name="user_id")
	private TripCaddieUser tripCaddieUser;
	@ManyToOne
	@JoinColumn(name="role_in_trip")
	private Roles roleInTrip;
	@ManyToOne
	@JoinColumn(name="trip_member_status")
	private TripMemberStatus tripMemberStatus;
	@ManyToOne
	@JoinColumn(name="paid_mode")
	private PaidMode paidMode;
	@Column(name="invited_email")
	private String invitedEmail;
	@Column(name="position")
	private Integer position;
	@Column(name="Total")
	private Integer Total;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdatedDate;
	@Column(name="invite_status")
	private String inviteStatus;

	@OneToMany(mappedBy = "member",cascade=CascadeType.ALL)
	private Collection<AwardVote> awardVotes = new ArrayList<AwardVote>();
	@OneToMany(mappedBy = "player1",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> member1 = new ArrayList<RoundTeeTime>();
	@OneToMany(mappedBy = "player2",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> member2 = new ArrayList<RoundTeeTime>();
	@OneToMany(mappedBy = "player3",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> member3 = new ArrayList<RoundTeeTime>();
	@OneToMany(mappedBy = "player4",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> member4 = new ArrayList<RoundTeeTime>();
	@OneToMany(mappedBy = "player5",cascade=CascadeType.ALL)
	private Collection<RoundTeeTime> member5 = new ArrayList<RoundTeeTime>();	
	@OneToMany(mappedBy = "member",cascade=CascadeType.ALL)
	private Collection<RoundScore> roundScores = new ArrayList<RoundScore>();
	@OneToMany(mappedBy = "createdby",cascade=CascadeType.ALL)
	private Collection<Awards> awards = new ArrayList<Awards>();
	@OneToMany(mappedBy = "member",cascade=CascadeType.ALL)
	private Collection<AwardVoteCount> awardVoteCounts = new ArrayList<AwardVoteCount>();
	@OneToMany(mappedBy = "tripMember",cascade=CascadeType.ALL)
	private Collection<AwardsComment> awardsComments = new ArrayList<AwardsComment>();	
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<RecentUpdate> recentUpdates=new ArrayList<RecentUpdate>();
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<Wall> walls=new ArrayList<Wall>();
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<WallComment> wallComments=new ArrayList<WallComment>();
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<Discussion> discussions=new ArrayList<Discussion>();
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<DiscussionComment> discussionComments=new ArrayList<DiscussionComment>();
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<PictureFile> pictureFiles=new ArrayList<PictureFile>();
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<PictureComment> pictureComments=new ArrayList<PictureComment>();
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<VideoFile> videoFiles=new ArrayList<VideoFile>();

	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<VideoComment> videoComments=new ArrayList<VideoComment>();
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<Expense> expenses=new ArrayList<Expense>();
	
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<WinningAmount> winningAmounts=new ArrayList<WinningAmount>(); 
	@OneToMany(mappedBy="tripMember",cascade=CascadeType.ALL)
	private Collection<PollQuestions> pollQuestions=new ArrayList<PollQuestions>();
	@ManyToMany(mappedBy="pollParticipants",cascade=CascadeType.ALL)
	private Collection<PollQuestions> questions=new ArrayList<PollQuestions>();
	
	@OneToMany(mappedBy="ratingDiscussionPK.tripMember",cascade=CascadeType.ALL)
	private Collection<RatingDiscussion> ratingDiscussions=new ArrayList<RatingDiscussion>();
	@OneToMany(mappedBy="ratingPictureFilePK.tripMember",cascade=CascadeType.ALL)
	private Collection<RatingPictureFile> ratingPictureFiles=new ArrayList<RatingPictureFile>();
	@OneToMany(mappedBy="ratingVideoFilePK.tripMember",cascade=CascadeType.ALL)
	private Collection<RatingVideoFile> ratingVideoFiles=new ArrayList<RatingVideoFile>();
	
	public Collection<RatingDiscussion> getRatingDiscussions() {
		return ratingDiscussions;
	}

	public void setRatingDiscussions(Collection<RatingDiscussion> ratingDiscussions) {
		this.ratingDiscussions = ratingDiscussions;
	}

	public Collection<RatingPictureFile> getRatingPictureFiles() {
		return ratingPictureFiles;
	}

	public void setRatingPictureFiles(
			Collection<RatingPictureFile> ratingPictureFiles) {
		this.ratingPictureFiles = ratingPictureFiles;
	}

	public Collection<RatingVideoFile> getRatingVideoFiles() {
		return ratingVideoFiles;
	}

	public void setRatingVideoFiles(Collection<RatingVideoFile> ratingVideoFiles) {
		this.ratingVideoFiles = ratingVideoFiles;
	}

	public Collection<PictureFile> getPicturefiles() {
		return picturefiles;
	}

	public void setPicturefiles(Collection<PictureFile> picturefiles) {
		this.picturefiles = picturefiles;
	}

	public Collection<VideoFile> getVideofiles() {
		return videofiles;
	}

	public void setVideofiles(Collection<VideoFile> videofiles) {
		this.videofiles = videofiles;
	}

	@ManyToMany(mappedBy="favouritePicture",cascade=CascadeType.ALL)
	private Collection<PictureFile> picturefiles = new ArrayList<PictureFile>();
	@ManyToMany(mappedBy="favouriteVideo",cascade=CascadeType.ALL)
	private Collection<VideoFile> videofiles=new ArrayList<VideoFile>();
	
	public Collection<Wall> getWalls() {
		return walls;
	}

	public void setWalls(Collection<Wall> walls) {
		this.walls = walls;
	}

	public Collection<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(Collection<Discussion> discussions) {
		this.discussions = discussions;
	}

	public Collection<PictureFile> getPictureFiles() {
		return pictureFiles;
	}

	public void setPictureFiles(Collection<PictureFile> pictureFiles) {
		this.pictureFiles = pictureFiles;
	}

	public Collection<VideoFile> getVideoFiles() {
		return videoFiles;
	}

	public void setVideoFiles(Collection<VideoFile> videoFiles) {
		this.videoFiles = videoFiles;
	}
	public Collection<PictureComment> getPictureComments() {
		return pictureComments;
	}

	public void setPictureComments(Collection<PictureComment> pictureComments) {
		this.pictureComments = pictureComments;
	}

	public Collection<VideoComment> getVideoComments() {
		return videoComments;
	}

	public void setVideoComments(Collection<VideoComment> videoComments) {
		this.videoComments = videoComments;
	}

	public Collection<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(Collection<Expense> expenses) {
		this.expenses = expenses;
	}

	public Collection<WallComment> getWallComments() {
		return wallComments;
	}

	public void setWallComments(Collection<WallComment> wallComments) {
		this.wallComments = wallComments;
	}

	public Collection<DiscussionComment> getDiscussionComments() {
		return discussionComments;
	}

	public void setDiscussionComments(
			Collection<DiscussionComment> discussionComments) {
		this.discussionComments = discussionComments;
	}
	
	public Collection<AwardVote> getAwardVotes() {
		return awardVotes;
	}

	public Collection<Awards> getAwards() {
		return awards;
	}

	public void setAwards(Collection<Awards> awards) {
		this.awards = awards;
	}

	public Collection<AwardsComment> getAwardsComments() {
		return awardsComments;
	}

	public void setAwardsComments(Collection<AwardsComment> awardsComments) {
		this.awardsComments = awardsComments;
	}

	public void setAwardVotes(Collection<AwardVote> awardVotes) {
		this.awardVotes = awardVotes;
	}

	public Collection<RoundTeeTime> getMember1() {
		return member1;
	}

	public void setMember1(Collection<RoundTeeTime> member1) {
		this.member1 = member1;
	}

	public Collection<RoundTeeTime> getMember2() {
		return member2;
	}

	public void setMember2(Collection<RoundTeeTime> member2) {
		this.member2 = member2;
	}

	public Collection<RoundTeeTime> getMember3() {
		return member3;
	}

	public void setMember3(Collection<RoundTeeTime> member3) {
		this.member3 = member3;
	}

	public Collection<RoundTeeTime> getMember4() {
		return member4;
	}

	public void setMember4(Collection<RoundTeeTime> member4) {
		this.member4 = member4;
	}

	public Collection<RoundTeeTime> getMember5() {
		return member5;
	}

	public void setMember5(Collection<RoundTeeTime> member5) {
		this.member5 = member5;
	}

	public String getInviteStatus() {
		return inviteStatus;
	}
	public void setInviteStatus(String inviteStatus) {
		this.inviteStatus = inviteStatus;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public TripCaddieUser getTripCaddieUser() {
		return tripCaddieUser;
	}
	public void setTripCaddieUser(TripCaddieUser tripCaddieUser) {
		this.tripCaddieUser = tripCaddieUser;
	}
	public Roles getRoleInTrip() {
		return roleInTrip;
	}
	public void setRoleInTrip(Roles roleInTrip) {
		this.roleInTrip = roleInTrip;
	}
	public TripMemberStatus getTripMemberStatus() {
		return tripMemberStatus;
	}
	public void setTripMemberStatus(TripMemberStatus tripMemberStatus) {
		this.tripMemberStatus = tripMemberStatus;
	}
	public PaidMode getPaidMode() {
		return paidMode;
	}
	public void setPaidMode(PaidMode paidMode) {
		this.paidMode = paidMode;
	}
	public String getInvitedEmail() {
		return invitedEmail;
	}
	public void setInvitedEmail(String invitedEmail) {
		this.invitedEmail = invitedEmail;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getTotal() {
		return Total;
	}
	public void setTotal(Integer total) {
		Total = total;
	}
	
	public Collection<RoundScore> getRoundScores() {
		return roundScores;
	}

	public void setRoundScores(Collection<RoundScore> roundScores) {
		this.roundScores = roundScores;
	}

	public Collection<AwardVoteCount> getAwardVoteCounts() {
		return awardVoteCounts;
	}

	public void setAwardVoteCounts(Collection<AwardVoteCount> awardVoteCounts) {
		this.awardVoteCounts = awardVoteCounts;
	}

	public Collection<RecentUpdate> getRecentUpdates() {
		return recentUpdates;
	}

	public void setRecentUpdates(Collection<RecentUpdate> recentUpdates) {
		this.recentUpdates = recentUpdates;
	}

	public Collection<WinningAmount> getWinningAmounts() {
		return winningAmounts;
	}

	public void setWinningAmounts(Collection<WinningAmount> winningAmounts) {
		this.winningAmounts = winningAmounts;
	}

	public Collection<PollQuestions> getPollQuestions() {
		return pollQuestions;
	}

	public void setPollQuestions(Collection<PollQuestions> pollQuestions) {
		this.pollQuestions = pollQuestions;
	}

	public Collection<PollQuestions> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<PollQuestions> questions) {
		this.questions = questions;
	}

}
