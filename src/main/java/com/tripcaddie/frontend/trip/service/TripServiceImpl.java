package com.tripcaddie.frontend.trip.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.courses.model.GolfCourse;
import com.tripcaddie.backend.courses.searchDao.CourseDao;
import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.model.PaidMode;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripLeaderDelegation;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.TripMemberStatus;
import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripLeaderDelegationDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("tripService")
public class TripServiceImpl implements TripService {

	private static Logger logger=Logger.getLogger(TripServiceImpl.class);
	
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="courseDao")
	private CourseDao courseDao;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	/*@Resource(name="emailSender")
	private EmailSender emailSender;*/
	
	@Override
	public ArrayList<TripDto> getTripsOfUser() throws Exception{
	
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		ArrayList<TripMember> tripMembers=tripDao.getTripsOfUser(tripCaddieUser);
	
		ArrayList<TripDto> tripDtos=new ArrayList<TripDto>();
		for(TripMember tripMember:tripMembers){
			
			tripDtos.add(this.getTrip(tripMember));
		}
		return tripDtos;
	}

	@Override
	public TripDto getTrip(int tripId) throws Exception{
		
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		Trip trip=tripDao.getTrip(tripCaddieUser, tripId);
		return TripDto.instantiate(trip);
	}
	
	public TripDto getTrip(TripMember tripMember) throws Exception{
		
		Trip trip=tripDao.getTrip(tripMember.getTrip().getTripId());		
		return TripDto.instantiate(trip);
	}
	
	@Override
	public Integer createTrip(String tripName, String courseId,
			String startDate, String message, String endDate, String promoCode,
			String annualTrip,String path) throws Exception {
		/*At the time of create trip, we need to add trip leader in trip participant table*/ 
		
		ArrayList<Roles> roles=userDao.getRoles();
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		
		Trip trip=new Trip();				
		trip=this.setTrip(tripCaddieUser,tripName, courseId, startDate, endDate, promoCode,message, annualTrip,path);
		Integer tripId=tripDao.createTrip(trip);
				
		TripMember tripMember=this.setTripMember(tripCaddieUser, trip);
		
		for(Roles role:roles){
			if(role.getRoleName().equalsIgnoreCase("trip leader")){
				tripMember.setRoleInTrip(role);
				break;
			}
		}
		
		this.tripDao.saveTripMember(tripMember);
		
		return tripId;
	}
	
	@Override
	public void editTrip(Integer tripId, String tripName, String courseId,
			String startDate, String message, String endDate, String promoCode,
			String annualTrip, String path) throws Exception{
		
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(username);
		Trip trip=this.setTrip(tripId, tripCaddieUser, tripName, courseId, startDate, endDate, promoCode, message, annualTrip, path);
		this.tripDao.updateTrip(trip);
		recentUpdateService.logUpdates(tripId, "Updated Trip Profile", "leader");
	}
	
	@Override
	public boolean isExistTrip(String tripName,String userName) throws Exception{
		
		TripCaddieUser tripCaddieUser=userDao.getUserByUserName(userName);
		Integer userId=tripCaddieUser.getUserId();
		ArrayList<TripMember> tripMembers=tripDao.getTripsofUserAsTripLeader(userId);
		
		for(TripMember tripMember:tripMembers){
			Trip trip=tripMember.getTrip();
			String name=trip.getTripName();			
			if(tripName.equalsIgnoreCase(name)){
				 return true;
			}
		}
		return false;
	}
	
	private Trip setTrip(TripCaddieUser tripCaddieUser,String tripName, String courseId,String startDate,
			 String endDate, String promoCode,String welcomeMessage, String annualTrip,String imagePath) 
					throws Exception{
		
		Date tripStartDate=null;
		Date tripEndDate=null;
		
		Date date=new Date();
		Calendar instantTime=Calendar.getInstance();
		Calendar tripStartdate=Calendar.getInstance();
		Calendar tripEnddate=Calendar.getInstance();
		
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		tripEndDate=dateFormat.parse(endDate);
		tripStartDate=dateFormat.parse(startDate);
		
		instantTime.setTime(date);
		tripEnddate.setTime(tripEndDate);
		tripStartdate.setTime(tripStartDate);
		GolfCourse golfCourse=courseDao.getGolfCourse(Integer.parseInt(courseId));
		
		Trip trip=new Trip();
		if(annualTrip.equalsIgnoreCase("yes")){
			trip.setAnnualTrip(1);
		}else {
			trip.setAnnualTrip(0);
		}
		trip.setCreatedBy(tripCaddieUser.getUserName());
		trip.setCreatedDate(instantTime);
		trip.setEndDate(tripEnddate);
		trip.setPromoCode(promoCode);
		trip.setStartDate(tripStartdate);
		trip.setTripName(tripName);
		trip.setWelcomeMessage(welcomeMessage);
		trip.setImagePath(imagePath);
		trip.setGolfCourse(golfCourse);
		return trip;
	}

	//By jeevan
	private Trip setTrip(Integer tripId, TripCaddieUser tripCaddieUser,
			String tripName, String courseId, String startDate, String endDate,
			String promoCode, String welcomeMessage, String annualTrip, String imagePath)  throws Exception
	{
		Date tripStartDate=null;
		Date tripEndDate=null;
		
		Date date=new Date();
		Calendar instantTime=Calendar.getInstance();
		Calendar tripStartdate=Calendar.getInstance();
		Calendar tripEnddate=Calendar.getInstance();
		
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		tripEndDate=dateFormat.parse(endDate);
		tripStartDate=dateFormat.parse(startDate);
		
		instantTime.setTime(date);
		tripEnddate.setTime(tripEndDate);
		tripStartdate.setTime(tripStartDate);
		GolfCourse golfCourse=courseDao.getGolfCourse(Integer.parseInt(courseId));
		
		Trip trip=tripDao.getTrip(tripId);
		
		if(annualTrip.equalsIgnoreCase("yes")){
			trip.setAnnualTrip(1);
		}else {
			trip.setAnnualTrip(0);
		}
		trip.setLastUpdatedDate(instantTime);
		trip.setLastUpdatedBy(tripCaddieUser.getUserName());
		trip.setEndDate(tripEnddate);
		trip.setPromoCode(promoCode);
		trip.setStartDate(tripStartdate);
		trip.setTripName(tripName);
		trip.setWelcomeMessage(welcomeMessage);
		if(imagePath!=null)
			trip.setImagePath(imagePath);
		trip.setGolfCourse(golfCourse);
		return trip;
	}
	
	private TripMember setTripMember(TripCaddieUser tripCaddieUser,Trip trip) throws Exception{
		
		Date date=new Date();
		Calendar instantTime=Calendar.getInstance();
		instantTime.setTime(date);
		TripMember tripMember=new TripMember();
		ArrayList<PaidMode> paidModes=tripDao.getPaidStatus();
		ArrayList<TripMemberStatus> tripMemberStatus=tripDao.getTripMemberStatus();
		
		tripMember.setCreatedBy(tripCaddieUser.getUserName());
		tripMember.setCreatedDate(instantTime);
		tripMember.setInvitedEmail(tripCaddieUser.getEmail());
		tripMember.setTotal(0);
		for(PaidMode paidMode:paidModes){
			if(paidMode.getPaidMode().equalsIgnoreCase("unpaid")){
				tripMember.setPaidMode(paidMode);
				break;
			}
		}
		
		for(TripMemberStatus memberStatus:tripMemberStatus){
			if(memberStatus.getMemberStatus().equalsIgnoreCase("TRIP LEADER")){
				tripMember.setTripMemberStatus(memberStatus);
				break;
			}
		}
		
		tripMember.setTrip(trip);
		tripMember.setTripCaddieUser(tripCaddieUser);
		return tripMember;
	}
	
	/*private int getNumberFromString(String number) throws Exception{
		int num=0;
		for(char c:number.toCharArray()){
			num=(num*10)+c-48;
		}
		
		return num;
	}*/

	@Override
	public void deleteTrip(int tripId) throws Exception {
		
		Trip trip=tripDao.getTrip(tripId);
		logger.info(trip.getTripName()+" is deleted");
		tripDao.deleteTrip(trip);		
		
	}

	@Override
	public ArrayList<TripMemberDto> getTripMembers(int tripId) throws Exception{
		ArrayList<TripMemberDto> tripMemberDtos=new ArrayList<TripMemberDto>();
		Trip trip=tripDao.getTrip(tripId);
		
		ArrayList<TripMember> tripMembers=tripDao.getTripMembers(trip);
		if(tripMembers != null){
			/*System.out.println("Size:"+tripMembers.size());*/
			
			for (TripMember tripMember : tripMembers) {
				tripMemberDtos.add(TripMemberDto.instantiate(tripMember));
			}
		}
		return tripMemberDtos;
	}

	@Override
	public void addTripMember(int tripId, String email, String role)
			throws Exception {
		
		System.out.println("Email in service:"+email);
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		Trip trip=tripDao.getTrip(tripId);
		String username=loginService.getAuthenticatedUser();
		
		TripMember tripMember=tripDao.getTripMember(tripId, email);
		
		if(tripMember == null){
			tripMember=new TripMember();
			tripMember.setCreatedBy(username);
			tripMember.setCreatedDate(instantTime);
			tripMember.setInvitedEmail(email);
			
		}else{
			tripMember.setLastUpdatedBy(username);
			tripMember.setLastUpdatedDate(instantTime);
		}
		tripMember.setInviteStatus(role);
		ArrayList<PaidMode> paidModes=tripDao.getPaidStatus();
		ArrayList<TripMemberStatus> tripMembersStatus=tripDao.getTripMemberStatus();
		ArrayList<Roles> roles=userDao.getRoles();
				
		
		for(Roles roleInTrip:roles){
			System.out.println("role:"+role);
			if(roleInTrip.getRoleName().equalsIgnoreCase(role)){
				tripMember.setRoleInTrip(roleInTrip);
				break;
			}
		}
		for(PaidMode paidMode:paidModes){
			//System.out.println("paid status:"+paidMode.getPaidMode());
			if(paidMode.getPaidMode().equalsIgnoreCase("unpaid")){
				tripMember.setPaidMode(paidMode);
				break;
			}
		}
		
		tripMember.setTrip(trip);
		for (TripMemberStatus tripMemberStatus : tripMembersStatus) {
			//System.out.println("paid status:"+tripMemberStatus.getMemberStatus());
			if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("invited")){
				tripMember.setTripMemberStatus(tripMemberStatus);
				break;
			}
		}
		tripMember.setTripCaddieUser(userDao.getUserByUserName("default"));
		tripMember.setTotal(0);
		/*TripMember member=tripDao.getTripMember(tripId, email);
		if(member == null)*/
		tripDao.addTripMembers(tripMember);
		
		recentUpdateService.logUpdates(tripId, "Invited a Trip Participant", "leader");
		
	}

	@Override
	public TripMemberDto getTripMember(int memberId) throws Exception {
		
		TripMember member=tripDao.getTripMember(memberId);
		return TripMemberDto.instantiate(member);
	}

	@Override
	public void deleteTripMember(int memberId) throws Exception {
		
		TripMember tripMember=tripDao.getTripMember(memberId);
		tripDao.deleteTripMember(tripMember);
		recentUpdateService.logUpdates(tripMember.getTrip().getTripId(), "Revoked a Trip Participant", "leader");
			
	}

	@Override
	public ArrayList<TripMemberDto> getTripInvitation(
			String email) throws Exception {
		
		ArrayList<TripMemberDto> invitations=new ArrayList<TripMemberDto>();
		int statusId=0;
		
		ArrayList<TripMemberStatus> tripMemberStatus=tripDao.getTripMemberStatus();
		for(TripMemberStatus status : tripMemberStatus){
			if(status.getMemberStatus().equalsIgnoreCase("INVITED")){
				statusId=status.getTripMemberStatusId();
			}
		}
		ArrayList<TripMember> invites=tripDao.getTripInvitation(email,statusId);
		
		for (TripMember tripMember : invites) {
			// check for current userid and tripid already exists
			TripMember tripMember2=tripDao.getTripMember(loginService.getUserByUserName(loginService.getAuthenticatedUser()).getUserId(), tripMember.getTrip().getTripId());
			if(tripMember2==null){
			invitations.add(TripMemberDto.instantiate(tripMember));
			}
		}
		return invitations;
	}

	@Override
	public void acceptInvite(int memberId) throws Exception {
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);
			
		ArrayList<Roles> roles=userDao.getRoles();
		
		TripMember tripMember=tripDao.getTripMember(memberId);
		ArrayList<TripMember> tripMembers=tripDao.getTripMembers(tripMember.getTrip());
			
		ArrayList<TripMemberStatus> memberStatus=tripDao.getTripMemberStatus();
		
		if(tripMember.getInviteStatus().equalsIgnoreCase("trip leader")){
			System.out.println("In leader");	
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("TRIP LEADER")){
					tripMember.setTripMemberStatus(tripMemberStatus);
					break;
				}
			}
			
			//update leader
			TripMember leader=this.getTripLeader(tripMembers);
			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
					leader.setTripMemberStatus(tripMemberStatus);
					break;
				}
			}
			
			for(Roles role : roles){
				if(role.getRoleName().equalsIgnoreCase("TRIP PARTICIPANT")){
					leader.setRoleInTrip(role);
					break;
				}
			}
			leader.setLastUpdatedBy(username);
			leader.setLastUpdatedDate(instantTime);
			tripDao.updateTripMember(leader);
			
			
		}else if(tripMember.getInviteStatus().equalsIgnoreCase("trip co-leader")){
			System.out.println("In co-leader");			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("TRIP CO-LEADER")){
					tripMember.setTripMemberStatus(tripMemberStatus);
					break;
				}
			}
			
			//update coleader
			TripMember coLeader=this.getCoLeader(tripMembers);
			System.out.println("Coleader:"+coLeader.getTripCaddieUser().getUserId());
			if(coLeader!=null){
				for (TripMemberStatus tripMemberStatus : memberStatus) {
					if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
						coLeader.setTripMemberStatus(tripMemberStatus);
						break;
					}
				}
			
				for(Roles role : roles){
					if(role.getRoleName().equalsIgnoreCase("TRIP PARTICIPANT")){
						coLeader.setRoleInTrip(role);
						break;
					}
				}
				coLeader.setLastUpdatedBy(username);
				coLeader.setLastUpdatedDate(instantTime);
			
				tripDao.updateTripMember(coLeader);
			}
		}else{
			System.out.println("In participant");
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
					tripMember.setTripMemberStatus(tripMemberStatus);
				}
			}
		}
		
		//Set tripmember
		tripMember.setLastUpdatedBy(username);
		tripMember.setLastUpdatedDate(instantTime);
		tripMember.setTripCaddieUser(user);
		
		tripDao.updateTripMember(tripMember);
		recentUpdateService.logUpdates(tripMember.getTrip().getTripId(), "Accepted Invitation","participant");
	}

	@Override
	public void declineInvite(int memberId) throws Exception {
		
		
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);
				
		TripMember tripMember=tripDao.getTripMember(memberId);
			
		ArrayList<TripMemberStatus> memberStatus=tripDao.getTripMemberStatus();
		
		//Set tripmember
		tripMember.setLastUpdatedBy(username);
		tripMember.setLastUpdatedDate(instantTime);
		tripMember.setTripCaddieUser(user);
		for (TripMemberStatus tripMemberStatus : memberStatus) {
			if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("REJECTED")){
				tripMember.setTripMemberStatus(tripMemberStatus);
			}
		}
		
		tripDao.updateTripMember(tripMember);
		
	}

	private TripMember getTripLeader(ArrayList<TripMember> tripMembers) throws Exception{
		
		for (TripMember tripMember : tripMembers) {
			if(tripMember.getTripMemberStatus().getMemberStatus().equalsIgnoreCase("TRIP LEADER")){
				return tripMember;
			}
		}
		return null;
	}
	
	private TripMember getCoLeader(ArrayList<TripMember> tripMembers) throws Exception{
		
		for (TripMember tripMember : tripMembers) {
			if(tripMember.getTripMemberStatus().getMemberStatus().equalsIgnoreCase("TRIP CO-LEADER")){
				return tripMember;
			}
		}
		return null;
	}

	@Override
	public ArrayList<TripMemberDto> getTripMemberswithoutCurrentmember(
			int tripId) throws Exception {
		ArrayList<TripMemberDto> tripMemberDtos=new ArrayList<TripMemberDto>();
		Trip trip=tripDao.getTrip(tripId);
		String username=loginService.getAuthenticatedUser();
		TripCaddieUser user=userDao.getUserByUserName(username);
		ArrayList<TripMember> tripMembers=tripDao.getTripMembers(trip);
		if(tripMembers != null){
			/*System.out.println("Size:"+tripMembers.size());*/
			
			for (TripMember tripMember : tripMembers) {
				if(!(tripMember.getTripCaddieUser().getUserId()==user.getUserId()))
				tripMemberDtos.add(TripMemberDto.instantiate(tripMember));
			}
		}
		return tripMemberDtos;
	}

	@Override
	public ArrayList<TripMember> getTripMembersbyOrder(int tripId)
			throws Exception {
	
		ArrayList<TripMember> tripMembers=tripDao.getTripMembersbyOrder(tripId);
		
		return tripMembers;
	}

	@Override
	public ArrayList<TripLeaderDelegationDto> getTripLeaderDelegations()
			throws Exception {
		
		ArrayList<TripLeaderDelegationDto> delegationDtos=new ArrayList<TripLeaderDelegationDto>();
		ArrayList<TripLeaderDelegation> delegations=tripDao.getTripLeaderDelegations();
		
		for (TripLeaderDelegation tripLeaderDelegation : delegations) {
			delegationDtos.add(TripLeaderDelegationDto.instantiate(tripLeaderDelegation));
		}
		
		return delegationDtos;
	}

	@Override
	public void changeTripMemberRole(int optionId, int memberId)
			throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		ArrayList<Roles> roles=userDao.getRoles();
		TripMember tripMember=tripDao.getTripMember(memberId);
		ArrayList<TripMember> tripMembers=tripDao.getTripMembers(tripMember.getTrip());
		ArrayList<TripMemberStatus> memberStatus=tripDao.getTripMemberStatus();
		
		
		TripLeaderDelegation delegation = tripDao.getTripLeaderDelegation(optionId);
		
		if(delegation.getDelegationOption().equalsIgnoreCase("Change to Trip Leader")){
			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("TRIP LEADER")){
					tripMember.setTripMemberStatus(tripMemberStatus);
				}
			}
			
			for(Roles role : roles){
				if(role.getRoleName().equalsIgnoreCase("TRIP LEADER")){
					tripMember.setRoleInTrip(role);
				}
			}
			//update leader
			TripMember leader=this.getTripLeader(tripMembers);
			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
					leader.setTripMemberStatus(tripMemberStatus);
				}
			}
			
			for(Roles role : roles){
				if(role.getRoleName().equalsIgnoreCase("TRIP PARTICIPANT")){
					leader.setRoleInTrip(role);
				}
			}
			leader.setLastUpdatedBy(username);
			leader.setLastUpdatedDate(instantTime);
			tripDao.updateTripMember(leader);
			
		}else if (delegation.getDelegationOption().equalsIgnoreCase("Change to Co-Trip Leader")) {
			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("TRIP CO-LEADER")){
					tripMember.setTripMemberStatus(tripMemberStatus);
				}
			}
			
			for(Roles role : roles){
				if(role.getRoleName().equalsIgnoreCase("TRIP CO-LEADER")){
					tripMember.setRoleInTrip(role);
				}
			}
			
			//update coleader
			TripMember coLeader=this.getCoLeader(tripMembers);
			if(coLeader!=null){
				for (TripMemberStatus tripMemberStatus : memberStatus) {
					if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
						coLeader.setTripMemberStatus(tripMemberStatus);
					}
				}
			
				for(Roles role : roles){
					if(role.getRoleName().equalsIgnoreCase("TRIP PARTICIPANT")){
						coLeader.setRoleInTrip(role);
					}
				}
				coLeader.setLastUpdatedBy(username);
				coLeader.setLastUpdatedDate(instantTime);
			
				tripDao.updateTripMember(coLeader);
			}
			
		}else if(delegation.getDelegationOption().equalsIgnoreCase("Remove Co-Trip Leader")){
			
			for (TripMemberStatus tripMemberStatus : memberStatus) {
				if(tripMemberStatus.getMemberStatus().equalsIgnoreCase("ACCEPTED")){
					tripMember.setTripMemberStatus(tripMemberStatus);
				}
			}
			
			for(Roles role : roles){
				if(role.getRoleName().equalsIgnoreCase("TRIP PARTICIPANT")){
					tripMember.setRoleInTrip(role);
				}
			}
		}
		
		//Set tripmember
		tripMember.setLastUpdatedBy(username);
		tripMember.setLastUpdatedDate(instantTime);
		tripDao.updateTripMember(tripMember);
	}
	
	@Override
	public ArrayList<TripDto> getTripsOfUser(int userId) throws Exception{
	
		TripCaddieUser tripCaddieUser=userDao.getUserBasedOnId(userId);
		ArrayList<TripMember> tripMembers=tripDao.getTripsOfUser(tripCaddieUser);
	
		ArrayList<TripDto> tripDtos=new ArrayList<TripDto>();
		for(TripMember tripMember:tripMembers){
			
			tripDtos.add(this.getTrip(tripMember));
		}
		return tripDtos;
	}
}
