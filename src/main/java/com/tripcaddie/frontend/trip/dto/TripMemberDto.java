package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.frontend.itinerary.dto.RoundScoreDto;
import com.tripcaddie.frontend.user.dto.RolesDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;

/**
 * @author Praga
 * 
 */
public class TripMemberDto implements Serializable {

	private static final long serialVersionUID = -1651976668760186736L;
	private int memberId;
	private TripDto tripDto;
	private TripcaddieUserDto tripCaddieUserDto;
	private RolesDto roleInTripDto;
	private TripMemberStatusDto tripMemberStatusDto;
	private PaidModeDto paidModeDto;
	private String invitedEmail;
	private Integer posiotion;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	private String inviteStatus;
	private int votecount;
	private String membername;
	private List<ExpenseDto> expenses;
	private WinningAmountDto winningAmount;
	private double totalexpense;
	private int expensesize;
	private double amountOwe;
	private double amoutCollect;
	private int roundSize;
	private Integer total;
	private List<RoundScoreDto> roundScores;

	public double getAmountOwe() {
		return amountOwe;
	}

	public void setAmountOwe(double amountOwe) {
		this.amountOwe = amountOwe;
	}

	public double getAmoutCollect() {
		return amoutCollect;
	}

	public void setAmoutCollect(double amoutCollect) {
		this.amoutCollect = amoutCollect;
	}

	public double getTotalexpense() {
		return totalexpense;
	}

	public void setTotalexpense(double totalexpense) {
		this.totalexpense = totalexpense;
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

	public TripDto getTripDto() {
		return tripDto;
	}

	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}

	public TripcaddieUserDto getTripCaddieUserDto() {
		return tripCaddieUserDto;
	}

	public void setTripCaddieUserDto(TripcaddieUserDto tripCaddieUserDto) {
		this.tripCaddieUserDto = tripCaddieUserDto;
	}

	public RolesDto getRoleInTripDto() {
		return roleInTripDto;
	}

	public void setRoleInTripDto(RolesDto roleInTripDto) {
		this.roleInTripDto = roleInTripDto;
	}

	public TripMemberStatusDto getTripMemberStatusDto() {
		return tripMemberStatusDto;
	}

	public void setTripMemberStatusDto(TripMemberStatusDto tripMemberStatusDto) {
		this.tripMemberStatusDto = tripMemberStatusDto;
	}

	public PaidModeDto getPaidModeDto() {
		return paidModeDto;
	}

	public void setPaidModeDto(PaidModeDto paidModeDto) {
		this.paidModeDto = paidModeDto;
	}

	public String getInvitedEmail() {
		return invitedEmail;
	}

	public void setInvitedEmail(String invitedEmail) {
		this.invitedEmail = invitedEmail;
	}

	public Integer getPosiotion() {
		return posiotion;
	}

	public void setPosiotion(Integer posiotion) {
		this.posiotion = posiotion;
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

	public static void populate(TripMemberDto tripMemberDto,
			TripMember tripMember) {
		//if (tripMember != null) {
			tripMemberDto.setCreatedBy(tripMember.getCreatedBy());
			tripMemberDto.setCreatedDate(tripMember.getCreatedDate());
			tripMemberDto.setInvitedEmail(tripMember.getInvitedEmail());
			tripMemberDto.setLastUpdatedBy(tripMember.getLastUpdatedBy());
			tripMemberDto.setLastUpdatedDate(tripMember.getLastUpdatedDate());
			tripMemberDto.setMemberId(tripMember.getMemberId());
			tripMemberDto.setPaidModeDto(PaidModeDto.instantiate(tripMember
					.getPaidMode()));
			tripMemberDto.setPosiotion(tripMember.getPosition());
			tripMemberDto.setRoleInTripDto(RolesDto.instantiate(tripMember
					.getRoleInTrip()));
			tripMemberDto.setTripDto(TripDto.instantiate(tripMember.getTrip()));
			tripMemberDto.setTripCaddieUserDto(TripcaddieUserDto
					.instantiate(tripMember.getTripCaddieUser()));
			tripMemberDto.setTripMemberStatusDto(TripMemberStatusDto
					.instantiate(tripMember.getTripMemberStatus()));
			tripMemberDto.setInviteStatus(tripMember.getInviteStatus());
			if (tripMember.getTotal() != null)
				tripMemberDto.setTotal(tripMember.getTotal());
			if (tripMember.getTripCaddieUser().getFirstName() == null
					&& tripMember.getTripCaddieUser().getLastName() == null) {
				tripMemberDto.setMembername(tripMember.getInvitedEmail());
			} else {
				tripMemberDto.setMembername(tripMember.getTripCaddieUser()
						.getFirstName()
						+ " "
						+ tripMember.getTripCaddieUser().getLastName());
			}
		/*} else {
			tripMemberDto = null;
		}*/
	}

	public static TripMemberDto instantiate(TripMember tripMember) {

		if(tripMember == null){
			return null;
		}
		TripMemberDto tripMemberDto = new TripMemberDto();
		populate(tripMemberDto, tripMember);
		return tripMemberDto;
	}

	public int getVotecount() {
		return votecount;
	}

	public void setVotecount(int votecount) {
		this.votecount = votecount;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public List<ExpenseDto> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseDto> expenses) {
		this.expenses = expenses;
	}

	public WinningAmountDto getWinningAmount() {
		return winningAmount;
	}

	public void setWinningAmount(WinningAmountDto winningAmount) {
		this.winningAmount = winningAmount;
	}

	public int getExpensesize() {
		return expensesize;
	}

	public void setExpensesize(int expensesize) {
		this.expensesize = expensesize;
	}

	public int getRoundSize() {
		return roundSize;
	}

	public void setRoundSize(int roundSize) {
		this.roundSize = roundSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<RoundScoreDto> getRoundScores() {
		return roundScores;
	}

	public void setRoundScores(List<RoundScoreDto> roundScores) {
		this.roundScores = roundScores;
	}

}
