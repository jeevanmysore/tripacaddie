package com.tripcaddie.frontend.itinerary.util;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.trip.dto.RoundTeeTimeDto;
import com.tripcaddie.frontend.trip.dto.TripDto;

public class teeTimePdfViewer extends AbstractPdfView{

	private static int pageNo = 1;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		
		Map<String, Object> objectModel=(Map<String, Object>) model.get("teeTime");
		
		TripDto trip=(TripDto) objectModel.get("trip");
		ArrayList<ActivityDto> activityDtos= (ArrayList<ActivityDto>) objectModel.get("activities");
		
		// Setting Properties for Titles
		Paragraph header = new Paragraph();
		Font headFont = new Font();
		headFont.setColor(Color.black);
		headFont.setFamily(BaseFont.TIMES_BOLD);
		headFont.setSize(10f);
		headFont.setStyle(1);
		header.setFont(headFont);
		header.setAlignment(Element.ALIGN_LEFT);

		// Setting Properties for Content
		Paragraph body = new Paragraph();
		Font bodyFont = new Font();
		bodyFont.setFamily(BaseFont.TIMES_ROMAN);
		bodyFont.setSize(10f);
		body.setFont(bodyFont);
		body.setSpacingAfter(0f);

		LineSeparator lineSeperator = new LineSeparator();
		lineSeperator.setLineColor(Color.black);
		lineSeperator.setAlignment(Element.ALIGN_MIDDLE);
		
		// Properties for Body
		Phrase headPhrase = new Phrase();
		Font hPhrase = new Font();
		hPhrase.setColor(Color.BLACK);
		hPhrase.setFamily(BaseFont.HELVETICA_BOLD);
		hPhrase.setSize(10f);
		hPhrase.setStyle(1);
		headPhrase.setFont(hPhrase);
		Phrase bodyPhrase = new Phrase();

		Phrase phrase = new Phrase();
		
		// Setting Header
		//First line of header
		header.add(Chunk.NEWLINE);
		header.add(new Paragraph("Pairings and Tee Times"));
		header.add(Chunk.NEWLINE);
		
		//Second line
		header.add(new Paragraph(trip.getTripName()));
		phrase.add(header);
		header.clear();
		body.add(Chunk.NEWLINE);
		
		// format date
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");

		String startformatdate = "";
		
		if (trip.getStartDate() != null) {
			
			startformatdate = dateFormatter.format(trip.getStartDate().getTime() );
		}
		String endformatdate = "";
		if (trip.getEndDate() != null) {
			endformatdate = dateFormatter.format(trip.getEndDate().getTime());
		}

		body.add(new Paragraph(startformatdate + " - " + endformatdate));
		phrase.add(body);
		header.clear();
		body.clear();
		
		Image img = Image
				.getInstance("http://www.tripcaddie.com/sites/all/themes/tripcaddie/images/trip_logo_new.png");
		float actualDpi = img.getDpiX();
		img.scalePercent(40f / actualDpi * 100);
		img.setAlignment(Image.RIGHT);
		document.add(Chunk.NEWLINE);
		Chunk chunk = new Chunk(img, 350, -5);
		phrase.add(chunk);
		phrase.add(Chunk.NEWLINE);
		phrase.add(new Chunk(lineSeperator));
		/*Rectangle page = document.getPageSize();
		
		PdfPTable footerTbl = new PdfPTable(2);*/

		Phrase ftrPhrase = new Phrase();

		ftrPhrase.add("Page " + pageNo);
		ftrPhrase.add("  @ 2011. TripCaddie, LLC. All Rights Reserved.");

		// Setting Header and Footer
		HeaderFooter header1 = new HeaderFooter(phrase, false);
		header1.setBorder(0);
		document.setHeader(header1);

		HeaderFooter footer = new HeaderFooter(ftrPhrase, true);

		document.setFooter(footer);
		footer.setAlignment(Element.ALIGN_BOTTOM);

		// Opening the Document
		document.open();
		System.out.println(document.getPageNumber());
		pageNo = document.getPageNumber() + 1;

		// document.add(new Chunk(lineSeperator));

		// For Displaying Overview
		header.add(Chunk.NEWLINE);
		header.add(new Paragraph("OVERVIEW"));
		header.add(Chunk.NEWLINE);
		document.add(header);
		header.clear();
		
		document.add(body);
		body.clear();
		
		document.add(Chunk.NEWLINE);
		headPhrase.add("Destination: ");
		String city = "";
		if (trip.getGolfCourseDto().getAddressDto().getCity() != null) {
			city = trip.getGolfCourseDto().getAddressDto().getCity();
		}
		String state = "";
		if (trip.getGolfCourseDto().getAddressDto().getState() != null) {
			state = trip.getGolfCourseDto().getAddressDto().getState();
		}
		String country = "";
		if (trip.getGolfCourseDto().getAddressDto().getCountry() != null) {
			country = trip.getGolfCourseDto().getAddressDto().getCountry();
		}

		bodyPhrase.add(city + "," + state + "," + country);
		body.add(headPhrase);
		body.add(bodyPhrase);
		headPhrase.clear();
		bodyPhrase.clear();
		document.add(body);
		body.clear();
		headPhrase.add("Trip Leader: ");
		String tripleader = "";
		if (trip.getTripLeader() != null) {
			tripleader = trip.getTripLeader();
		}
		bodyPhrase.add(tripleader);
		body.add(headPhrase);
		body.add(bodyPhrase);
		headPhrase.clear();
		bodyPhrase.clear();
		document.add(body);
		body.clear();

		String phone = "";
		if (trip.getPhone() != null) {
			phone = trip.getPhone();
		}
		headPhrase.add("Phone: ");
		bodyPhrase.add(phone);
		body.add(headPhrase);
		body.add(bodyPhrase);
		headPhrase.clear();
		bodyPhrase.clear();
		body.add(Chunk.NEWLINE);
		document.add(body);
		body.clear();
		document.add(Chunk.NEWLINE);
		
		// For Displaying Tee Time
		header.add(new Paragraph("Pairings and Tee Times"));
		header.add(Chunk.NEWLINE);
		document.add(header);
		header.clear();
		document.add(Chunk.NEWLINE);
		
		long dayCount=0;
		int round=1;
		Calendar startDate=trip.getStartDate();
		Calendar previousDate=null;
		
		String member1=null;
		String member2=null;
		String member3=null;
		String member4=null;
		String member5=null;
		
		String hdcp1=null;
		String hdcp2=null;
		String hdcp3=null;
		String hdcp4=null;
		String hdcp5=null;
		
		String phoneNo1=null;
		String phoneNo2=null;
		String phoneNo3=null;
		String phoneNo4=null;
		String phoneNo5=null;
		
		
		for (ActivityDto activityDto : activityDtos) {
			
			SimpleDateFormat dateFormatter2 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
			SimpleDateFormat dateFormatter3 = new SimpleDateFormat(" h:m a");
			
			if(previousDate != null && !activityDto.getActivityDate().equals(previousDate)){
				dayCount=(startDate.getTimeInMillis()-activityDto.getActivityDate().getTimeInMillis())/(1000*60*60*24);
				headPhrase.add("DAY "+dayCount+" : "+dateFormatter2.format(activityDto.getActivityDate().getTime()));
			}	
			headPhrase.add("  Round "+round+":"+activityDto.getActivityTypeDto().getActivityType()+":"+activityDto.getActivityName());
			
			if(activityDto.getTeeTimeDtos().size() != 0){
				
				for (RoundTeeTimeDto teeTimeDto : activityDto.getTeeTimeDtos()) {
					if(teeTimeDto.getPlayer1() != null)
					if(teeTimeDto.getPlayer1().getTripCaddieUserDto().getUserId() == 0){
						member1=teeTimeDto.getPlayer1().getInvitedEmail();
						hdcp1="-";
						phoneNo1="-";
					}else{
						member1=teeTimeDto.getPlayer1().getTripCaddieUserDto().getFirstName()+" "+teeTimeDto.getPlayer1().getTripCaddieUserDto().getLastName();
						if(teeTimeDto.getPlayer1().getTripCaddieUserDto().getGolfHandicap() != null){
							hdcp1=teeTimeDto.getPlayer1().getTripCaddieUserDto().getGolfHandicap().toString();
						}else {
							hdcp1="-";
						}
						if (teeTimeDto.getPlayer1().getTripCaddieUserDto().getPhoneNo() != null) {
							phoneNo1=teeTimeDto.getPlayer1().getTripCaddieUserDto().getPhoneNo();
						}else {
							phoneNo1 = "-";
						}
					}
					if(teeTimeDto.getPlayer2() != null)
					if(teeTimeDto.getPlayer2().getTripCaddieUserDto().getUserId() == 0){
						member2=teeTimeDto.getPlayer2().getInvitedEmail();
						hdcp2="-";
						phoneNo2="-";
					}else{
						member2=teeTimeDto.getPlayer2().getTripCaddieUserDto().getFirstName()+" "+teeTimeDto.getPlayer2().getTripCaddieUserDto().getLastName();
						if(teeTimeDto.getPlayer2().getTripCaddieUserDto().getGolfHandicap() != null){
							hdcp2=teeTimeDto.getPlayer2().getTripCaddieUserDto().getGolfHandicap().toString();
						}else {
							hdcp2="-";
						}
						if (teeTimeDto.getPlayer2().getTripCaddieUserDto().getPhoneNo() != null) {
							phoneNo2=teeTimeDto.getPlayer2().getTripCaddieUserDto().getPhoneNo();
						}else {
							phoneNo2 = "-";
						}
					}
					if(teeTimeDto.getPlayer3() != null)
					if(teeTimeDto.getPlayer3().getTripCaddieUserDto().getUserId() == 0){
						member3=teeTimeDto.getPlayer3().getInvitedEmail();
						hdcp3="-";
						phoneNo3="-";
					}else{
						member3=teeTimeDto.getPlayer3().getTripCaddieUserDto().getFirstName()+" "+teeTimeDto.getPlayer3().getTripCaddieUserDto().getLastName();
						if(teeTimeDto.getPlayer3().getTripCaddieUserDto().getGolfHandicap() != null){
							hdcp3=teeTimeDto.getPlayer3().getTripCaddieUserDto().getGolfHandicap().toString();
						}else {
							hdcp3="-";
						}
						if (teeTimeDto.getPlayer3().getTripCaddieUserDto().getPhoneNo() != null) {
							phoneNo3=teeTimeDto.getPlayer3().getTripCaddieUserDto().getPhoneNo();
						}else {
							phoneNo3 = "-";
						}
					}
					if(teeTimeDto.getPlayer4() != null)
					if(teeTimeDto.getPlayer4().getTripCaddieUserDto().getUserId() == 0){
						member4=teeTimeDto.getPlayer4().getInvitedEmail();
						hdcp4="-";
						phoneNo4="-";
					}else{
						member4=teeTimeDto.getPlayer4().getTripCaddieUserDto().getFirstName()+" "+teeTimeDto.getPlayer4().getTripCaddieUserDto().getLastName();
						if(teeTimeDto.getPlayer4().getTripCaddieUserDto().getGolfHandicap() != null){
							hdcp4=teeTimeDto.getPlayer4().getTripCaddieUserDto().getGolfHandicap().toString();
						}else {
							hdcp4="-";
						}
						if (teeTimeDto.getPlayer4().getTripCaddieUserDto().getPhoneNo() != null) {
							phoneNo4=teeTimeDto.getPlayer4().getTripCaddieUserDto().getPhoneNo();
						}else {
							phoneNo4 = "-";
						}
					}
					if(teeTimeDto.getPlayer5() != null)
					if(teeTimeDto.getPlayer5().getTripCaddieUserDto().getUserId() == 0){
						member5=teeTimeDto.getPlayer5().getInvitedEmail();
						hdcp5="-";
						phoneNo5="-";
					}else{
						member5=teeTimeDto.getPlayer5().getTripCaddieUserDto().getFirstName()+" "+teeTimeDto.getPlayer5().getTripCaddieUserDto().getLastName();
						if(teeTimeDto.getPlayer5().getTripCaddieUserDto().getGolfHandicap() != null){
							hdcp5=teeTimeDto.getPlayer5().getTripCaddieUserDto().getGolfHandicap().toString();
						}else {
							hdcp5="-";
						}
						if (teeTimeDto.getPlayer5().getTripCaddieUserDto().getPhoneNo() != null) {
							phoneNo5=teeTimeDto.getPlayer5().getTripCaddieUserDto().getPhoneNo();
						}else {
							phoneNo5 = "-";
						}
					}
						
					headPhrase.add("     Tee Time : "+dateFormatter3.format(teeTimeDto.getTimings()));
					headPhrase.add("     Player Name                    Hdcp                  Phone Number");
					if(member1 != null)
						bodyPhrase.add("     "+member1+"                "+hdcp1+"                "+phoneNo1+"\n");
					if(member2 != null)
						bodyPhrase.add("     "+member2+"                "+hdcp2+"                "+phoneNo2+"\n");
					if(member3 != null)
						bodyPhrase.add("     "+member3+"                "+hdcp3+"                "+phoneNo3+"\n");
					if(member4 != null)
						bodyPhrase.add("     "+member4+"                "+hdcp4+"                "+phoneNo4+"\n");
					if(member5 != null)
						bodyPhrase.add("     "+member5+"                "+hdcp5+"                "+phoneNo5+"\n");
					
				}
			}else{
				bodyPhrase.add("            No Tee times added \n");
			}
			
			body.add(headPhrase);
			body.add(bodyPhrase);
			headPhrase.clear();
			bodyPhrase.clear();
			body.add(Chunk.NEWLINE);
			document.add(body);
			body.clear();
			document.add(Chunk.NEWLINE);
				
		}
		document.close();
		
		
		
		
	}

}
