package com.tripcaddie.frontend.trip.service;

import java.util.ArrayList;

import com.tripcaddie.frontend.trip.dto.WallDto;

public interface WallService {

	public void writeWall(int tripId,String conetnt) throws Exception;
	public void writeWallComment(int wallId,String Comment) throws Exception;
	
	public ArrayList<WallDto> getWallContent(int tripID) throws Exception;
	public void deleteWall(int wallId) throws Exception;
	public void deleteComment(int commentId) throws Exception;
	
}
