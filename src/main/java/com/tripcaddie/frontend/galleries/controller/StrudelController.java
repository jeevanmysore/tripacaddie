package com.tripcaddie.frontend.galleries.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tripcaddie.common.image.ImagePath;
import com.tripcaddie.frontend.galleries.service.GalleriesService;
import com.tripcaddie.frontend.trip.dto.ExpenseDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;
import com.tripcaddie.frontend.trip.service.TripService;

@Controller
@RequestMapping("/trip/strudel/*")
public class StrudelController {
	
	private static Logger logger = Logger.getLogger(StrudelController.class);

	@Resource(name = "galleriesService")
	private GalleriesService galleriesService;

	@Resource(name = "tripService")
	private TripService tripService;

	@Resource(name = "imagePath")
	private ImagePath imagePath;
	
	@Resource(name="jsonView")
	private JsonView jsonView;
	
	@RequestMapping(value = "getStrudelBS.do", method = RequestMethod.GET)
	public String getStrudelBalanceSht(@RequestParam("tripId") int tripId ,HttpServletResponse response ,Model model){
		
		
		try{
		//get Trip using tripId
		TripDto tripDto=tripService.getTrip(tripId);
		
		//Display Strudel based on trip id
		// 0 order for trip member
		List<Object> objects=galleriesService.getCalculatedExpense(tripId);
		List<TripMemberDto> memberDtos=(List<TripMemberDto>) objects.get(0);
		
		// 1 order for Expense size
		int expensesize=(Integer) objects.get(1);
		if (tripDto.getImagePath() != null) {
			tripDto.setImagebase64(getImageEncodedString(imagePath.getTripimagepathperuser()+tripDto
					.getImagePath()));
		}

		//Adding to Model
		model.addAttribute("trip", tripDto);
		model.addAttribute("tripmembers", memberDtos);
		model.addAttribute("expensesize", expensesize);
		model.addAttribute("TableSize", 100/(memberDtos.size()+1));
		return "trip/strudel";
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	private String getImageEncodedString(String imagePath) throws Exception {

		File file = new File(imagePath);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(b);
		return Base64.encodeBase64String(b);
	}
	
	@RequestMapping(value = "createExpense.do", method = RequestMethod.POST)
	public void createExpense(HttpServletRequest request,HttpServletResponse response ,Model model){
		String tripId=request.getParameter("tripId");
		String memberId=request.getParameter("memberId");
		String Title=request.getParameter("title");
		String date=request.getParameter("Expensedate");
		String amount=request.getParameter("ExpenseAmount");
		String expenseId=request.getParameter("expenseId");
		
		
		try{
			if(expenseId!=null && !expenseId.isEmpty()){
				galleriesService.updateExpense(Title, amount, date,expenseId);
			}else{
			galleriesService.createExpense(Title, amount, date, memberId);
			}
			response.sendRedirect("/tripcaddie/trip/strudel/getStrudelBS.do?tripId="
					+ tripId);
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
	}
	
	
	@RequestMapping(value = "getExpense.do", method = RequestMethod.POST)
	public ModelAndView getExpense(@RequestParam("expenseId") String expenseId,HttpServletRequest request,HttpServletResponse response ,Model model){
		
		try{
			ExpenseDto expenseDto=galleriesService.getExpense(Integer.parseInt(expenseId));
			System.out.println("returning expense dto");
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setView(jsonView);
			
			JSONObject json=new JSONObject();
			json.accumulate("expense", expenseDto);
			modelAndView.addObject("json", json);
			return modelAndView;
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
		
		
	
	}
	
	
	@RequestMapping(value = "editWinnings.do", method = RequestMethod.POST)
	public void editWinnings(HttpServletRequest request,HttpServletResponse response ,Model model){
		String tripId=request.getParameter("tripId");
		String WinningId=request.getParameter("WinningId");
		
		String amount=request.getParameter("winningAmount");
		
		try{
			galleriesService.editWinning(amount, WinningId);
			response.sendRedirect("/tripcaddie/trip/strudel/getStrudelBS.do?tripId="
					+ tripId);
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
	}
	
	
	@RequestMapping(value = "createWinnings.do", method = RequestMethod.POST)
	public void createWinnings(HttpServletRequest request,HttpServletResponse response ,Model model){
		String tripId=request.getParameter("tripId");
		String memberId=request.getParameter("memberId");
		
		String amount=request.getParameter("winningAmount");
		
		try{
			galleriesService.createWinning(amount, memberId);
			response.sendRedirect("/tripcaddie/trip/strudel/getStrudelBS.do?tripId="
					+ tripId);
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
	}
	
	
	@RequestMapping(value = "createWager.do", method = RequestMethod.POST)
	public void createWager(HttpServletRequest request,HttpServletResponse response ,Model model){
		String tripId=request.getParameter("tripId");
		
		String amount=request.getParameter("wagerAmount");
		try{
			galleriesService.updateWager(amount, tripId);
			response.sendRedirect("/tripcaddie/trip/strudel/getStrudelBS.do?tripId="
					+ tripId);
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
	}
	
	@RequestMapping(value = "updateLock.do", method = RequestMethod.POST)
	public @ResponseBody
	String updateLock(@RequestParam("stlock") int stlock,
			@RequestParam("tripId") int tripId) {
		try{
		galleriesService.updateStLock(tripId, stlock);
		return "success";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		

	}
	
	@RequestMapping(value = "deleteWager.do", method = RequestMethod.POST)
	public @ResponseBody
	String deleteWager(	@RequestParam("tripId") int tripId) {
		try{
		galleriesService.deleteWager(tripId);
		return "success";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		

	}

}
