package com.tripcaddie.backend.trip.dao;

import java.util.ArrayList;

import com.tripcaddie.backend.trip.model.Wall;
import com.tripcaddie.backend.trip.model.WallComment;

public interface WallDao {
	
	public void writeWall(Wall wall) throws Exception;
	public void writeWallComment(WallComment Comment) throws Exception;
	public Wall getWall(int wallId) throws Exception;
	public ArrayList<Wall> getWallContent(int tripID) throws Exception;
	public void deleteWall(Wall wall) throws Exception;
	public void deleteComment(WallComment comment) throws Exception;
	public WallComment getWallComment(int commentId) throws Exception;

}
