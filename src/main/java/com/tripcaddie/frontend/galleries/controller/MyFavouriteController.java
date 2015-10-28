package com.tripcaddie.frontend.galleries.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripcaddie.backend.galleries.model.PictureFile;
import com.tripcaddie.backend.galleries.model.VideoFile;
import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.galleries.dto.PictureFileDto;
import com.tripcaddie.frontend.galleries.dto.VideoFileDto;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
@Transactional
@RequestMapping("/user/fav/*")
public class MyFavouriteController {

	private static Logger logger = Logger.getLogger(GalleriesController.class);

	@Resource(name = "galleriesService")
	private GalleriesService galleriesService;
	@Resource(name = "tripService")
	private TripService tripService;
	@Resource(name = "imagePath")
	private ImagePath imagePath;

	@RequestMapping(value = "getFavPictures.do", method = RequestMethod.GET)
	public String getFavPictures(Model model) {
		try {

			List<TripDto> trips = tripService.getTripsOfUser();

			for (TripDto trip : trips) {
				trip.setGalleriespresent(false);
				// get Imageasbase64
				if (trip.getImagePath() != null) {

					trip.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+trip
							.getImagePath()));
				}
				List<PictureFileDto> pictures = new ArrayList<PictureFileDto>();
				List<PictureFile> pList = galleriesService
						.getPicturesFileByDate(trip.getTripId());

				for (PictureFile pictureFile : pList) {
					if (pictureFile.getFavouritePicture().contains(
							pictureFile.getTripMember())) {
						trip.setGalleriespresent(true);
                       PictureFileDto pictureFileDto=PictureFileDto.instantiate(pictureFile);
						String imageUrl = imagePath.getTripimagepath()
								+ pictureFileDto.getPictureName();
						pictureFileDto
								.setImageInBase64(getImageEncodedString(imageUrl));
						pictureFileDto
								.setNoOfComments(galleriesService
										.getNoOfcomments(pictureFileDto
												.getPictureId()));
						
						pictures.add(pictureFileDto);

					}
				}
				
				if(pictures!=null && !pictures.isEmpty()){
					trip.setPictures(pictures);
				}

			}

			
			model.addAttribute("trips", trips);

			return "trip/MyfavPhotos";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e);
			return "error";
		}

	}

	private String getImageEncodedString(String imagePath) throws Exception {

		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
	
/**
 * Favourite videos
 * 
 * @param model
 * @return
 */
	
	@RequestMapping(value = "getFavVideos.do", method = RequestMethod.GET)
	public String getFavVideos(Model model) {
		try {

			List<TripDto> trips = tripService.getTripsOfUser();
            System.out.println("trips size"+trips.size());
			

			for (TripDto trip : trips) {
				trip.setGalleriespresent(false);
				// get Imageasbase64
				if (trip.getImagePath() != null) {

					trip.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+trip
							.getImagePath()));
				}
				List<VideoFileDto> videos = new ArrayList<VideoFileDto>();
				List<VideoFile> vList = galleriesService
						.getVideosFileByDate(trip.getTripId());

				for (VideoFile videoFile : vList) {
					if (videoFile.getFavouriteVideo().contains(videoFile.getTripMember())) {
						trip.setGalleriespresent(true);
						VideoFileDto videoFileDto=VideoFileDto.instantiate(videoFile);
						String imageUrl = imagePath.getTripvideopath()
								+ videoFileDto.getVideothumbnailname();
						videoFileDto
								.setTbimginbase64(getImageEncodedString(imageUrl));
						videoFileDto
								.setNoOfComments(galleriesService
										.getNoOfCommentsperVideo(videoFileDto.getVideoId()));
						
						videos.add(videoFileDto);

					}
				}
				if(videos!=null && !videos.isEmpty()){
				trip.setVideos(videos);
				}

			}

			
			model.addAttribute("trips", trips);

			return "trip/MyfavVideos";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage() + " " + e);
			return "error";
		}

	}
}
