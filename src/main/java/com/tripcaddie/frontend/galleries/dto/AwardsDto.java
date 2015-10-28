package com.tripcaddie.frontend.galleries.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tripcaddie.backend.galleries.model.Awards;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class AwardsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int awardid;
	private String awardname;
	private String imagename;
	private Date pollenddate;
	private String votecount;
	private String slverimginbas64;
	private String bronzimginbas64;
	private String goldimginbas64;
	private boolean onlycreatepol;
	private int commentcount;
	private TripMemberDto nominee;
	private String awardImgbas64;
	private List<TripMemberDto> nominees;
	private String votenow;
    
	public int getAwardid() {
		return awardid;
	}

	public void setAwardid(int awardid) {
		this.awardid = awardid;
	}

	public String getAwardname() {
		return awardname;
	}

	public void setAwardname(String awardname) {
		this.awardname = awardname;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public Date getPollenddate() {
		return pollenddate;
	}

	public void setPollenddate(Date pollenddate) {
		this.pollenddate = pollenddate;
	}

	public String getVotecount() {
		return votecount;
	}

	public void setVotecount(String votecount) {
		this.votecount = votecount;
	}

	public String getSlverimginbas64() {
		return slverimginbas64;
	}

	public void setSlverimginbas64(String slverimginbas64) {
		this.slverimginbas64 = slverimginbas64;
	}

	public String getBronzimginbas64() {
		return bronzimginbas64;
	}

	public void setBronzimginbas64(String bronzimginbas64) {
		this.bronzimginbas64 = bronzimginbas64;
	}

	public String getGoldimginbas64() {
		return goldimginbas64;
	}

	public void setGoldimginbas64(String goldimginbas64) {
		this.goldimginbas64 = goldimginbas64;
	}

	public boolean isOnlycreatepol() {
		return onlycreatepol;
	}

	public void setOnlycreatepol(boolean onlycreatepol) {
		this.onlycreatepol = onlycreatepol;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	
	public static AwardsDto instantiate(Awards awards) {

		AwardsDto awardsDto = new AwardsDto();
		populate(awardsDto, awards);
		return awardsDto;

	}

	private static void populate(AwardsDto awardsDto, Awards awards) {

		awardsDto.setAwardid(awards.getAwardid());
		awardsDto.setAwardname(awards.getAwardname());
		awardsDto.setImagename(awards.getImagename());
		awardsDto.setPollenddate(awards.getPollenddate());
		
	}

	public TripMemberDto getNominee() {
		return nominee;
	}

	public void setNominee(TripMemberDto nominee) {
		this.nominee = nominee;
	}

	public String getAwardImgbas64() {
		return awardImgbas64;
	}

	public void setAwardImgbas64(String awardImgbas64) {
		this.awardImgbas64 = awardImgbas64;
	}

	public List<TripMemberDto> getNominees() {
		return nominees;
	}

	public void setNominees(List<TripMemberDto> nominees) {
		this.nominees = nominees;
	}

	public String getVotenow() {
		return votenow;
	}

	public void setVotenow(String votenow) {
		this.votenow = votenow;
	}

	
}
