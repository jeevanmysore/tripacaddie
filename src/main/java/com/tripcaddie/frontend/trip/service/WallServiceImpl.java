package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.trip.dao.TripDao;
import com.tripcaddie.backend.trip.dao.WallDao;
import com.tripcaddie.backend.trip.model.Trip;
import com.tripcaddie.backend.trip.model.TripMember;
import com.tripcaddie.backend.trip.model.Wall;
import com.tripcaddie.backend.trip.model.WallComment;
import com.tripcaddie.frontend.trip.dto.WallCommentDto;
import com.tripcaddie.frontend.trip.dto.WallDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("wallService")
public class WallServiceImpl implements WallService{

	@Resource(name="loginService")
	private LoginService loginService;
	@Resource(name="wallDao")
	private WallDao wallDao;
	@Resource(name="tripDao")
	private TripDao tripDao;
	@Resource(name="recentUpdateService")
	private RecentUpdateService recentUpdateService;
	
	@Override
	public void writeWall(int tripId, String conetnt) throws Exception {
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripCaddieUser=loginService.getUserByUserName(username);
		Wall wall =new Wall();
		Trip trip=tripDao.getTrip(tripId);
		TripMember member=tripDao.getTripMember(tripCaddieUser, tripId);
				
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		wall.setCreatedBy(username);
		wall.setCreatedDate(instantTime);
		wall.setMessage(conetnt);
		wall.setTrip(trip);
		wall.setTripMember(member);
		
		wallDao.writeWall(wall);
		recentUpdateService.logUpdates(tripId, "Wrote on Wall","participant");
	}

	@Override
	public void writeWallComment(int wallId, String comment) throws Exception {
		
		WallComment wallComment=new WallComment();
		
		String username=loginService.getAuthenticatedUser();
		TripcaddieUserDto tripCaddieUser=loginService.getUserByUserName(username);
		Wall wall =wallDao.getWall(wallId);
		TripMember member=tripDao.getTripMember(tripCaddieUser, wall.getTrip().getTripId());
				
		Calendar instantTime=Calendar.getInstance();
		Date date=new Date();
		instantTime.setTime(date);
		
		wallComment.setComment(comment);
		wallComment.setCreatedBy(username);
		wallComment.setCreatedDate(instantTime);
		wallComment.setTripMember(member);
		wallComment.setWall(wall);
		
		wallDao.writeWallComment(wallComment);
		
		
	}

	@Override
	public ArrayList<WallDto> getWallContent(int tripID) throws Exception {
		
		ArrayList<WallDto> wallDtos=new ArrayList<WallDto>();
		ArrayList<Wall> walls=wallDao.getWallContent(tripID);
		WallDto wallDto=new WallDto();
		WallCommentDto commentDto=new WallCommentDto();
		
		
		for (Wall wall : walls) {
			
			wallDto=WallDto.instantiate(wall);
			for (WallComment wallComment : wall.getComments()) {
				commentDto=WallCommentDto.instantiate(wallComment);
				wallDto.getCommentDtos().add(commentDto);
			}
			wallDtos.add(wallDto);
		}
		
		return wallDtos;
	}

	@Override
	public void deleteWall(int wallId) throws Exception {
		
		Wall wall=wallDao.getWall(wallId);
		wallDao.deleteWall(wall);
		
	}

	@Override
	public void deleteComment(int commentId) throws Exception {
		
		WallComment comment=wallDao.getWallComment(commentId);
		wallDao.deleteComment(comment);
	}

}
