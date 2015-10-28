package com.tripcaddie.frontend.itinerary.util;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.tripcaddie.frontend.itinerary.dto.AccommodationDto;
import com.tripcaddie.frontend.itinerary.dto.ActivityDto;
import com.tripcaddie.frontend.itinerary.dto.LogisticsDto;
import com.tripcaddie.frontend.trip.dto.TripDto;
import com.tripcaddie.frontend.trip.dto.TripMemberDto;

public class Pdfdownload extends AbstractPdfView{

	private static int pageNo=0;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> objectModel = (Map<String, Object>) model
				.get("model");
		TripDto trip = (TripDto) objectModel.get("trip");
		AccommodationDto accommodation = (AccommodationDto) objectModel
				.get("accommodation");
		List<ActivityDto> activitylist = (List<ActivityDto>) objectModel
				.get("activitylist");
		LogisticsDto logistics = (LogisticsDto) objectModel.get("logistics");
		List<TripMemberDto> members = (List<TripMemberDto>) objectModel
				.get("members");

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
		header.add(Chunk.NEWLINE);
		header.add(new Paragraph("Golf Trip Itinerary"));
		header.add(Chunk.NEWLINE);

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
		Rectangle page = document.getPageSize();

		PdfPTable footerTbl = new PdfPTable(2);

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

		// For Displaying Itenary
		header.add(new Paragraph("ITINERARY"));
		header.add(Chunk.NEWLINE);
		document.add(header);
		header.clear();
		document.add(Chunk.NEWLINE);
		int itr = 1;
		Calendar previousdate = null;
		for (ActivityDto activityDto : activitylist) {
			SimpleDateFormat dateFormatter2 = new SimpleDateFormat(
					"EEEE, MMMM d, yyyy");
			SimpleDateFormat dateFormatter3 = new SimpleDateFormat(
					" h:m:s a");
			String activitydate=" ";
			String activitytime="Time Pending ";
			if (previousdate != null
					&& activityDto.getActivityDate().equals(previousdate)) {
				if(activityDto.getActivityTime()!=null && activityDto.getTimePending()==0){
					activitytime=dateFormatter3.format(activityDto.getActivityTime());}

			} else {
				int difInDays = (int) ((activityDto.getActivityDate().getTime().getTime() - trip.getStartDate().getTime().getTime())/(1000*60*60*24));
				difInDays=difInDays+1;
				headPhrase.add("Day" + difInDays+ " :");
				activitydate=dateFormatter2.format(activityDto.getActivityDate().getTime());
				if(activityDto.getActivityTime()!=null&& activityDto.getTimePending()==0){
					activitytime=dateFormatter3.format(activityDto.getActivityTime());}
				//itr++;
			}
			previousdate = activityDto.getActivityDate();
			

			/*bodyPhrase.add(activitydate+" \n"+"            "+activitytime
					+ "-" + activityDto.getActivityTypeDto().getActivityType()
					+ ":" + activityDto.getActivityName() + " \n"+"                 "+"  MAP INFO:"
					+ activityDto.getMapInfo());*/
			bodyPhrase.add(activitydate+" \n"+"            "+activitytime
					+ "-" + activityDto.getActivityTypeDto().getActivityType()
					+ ":" + activityDto.getActivityName());
			body.add(headPhrase);
			body.add(bodyPhrase);
			headPhrase.clear();
			bodyPhrase.clear();
			body.add(Chunk.NEWLINE);
			document.add(body);
			body.clear();
			document.add(Chunk.NEWLINE);

		}

		/*
		 * // For Accomodation header.add(new Paragraph("Accomodations"));
		 * header.add(Chunk.NEWLINE); document.add(header); header.clear();
		 * 
		 * body.add("message"); body.add(Chunk.NEWLINE); document.add(body);
		 * body.clear(); document.add(Chunk.NEWLINE);
		 */

		// For Logistics
		header.add(new Paragraph("LOGISTICS"));
		header.add(Chunk.NEWLINE);
		document.add(header);
		header.clear();
		String logisticscontent="";
		if (logistics != null && logistics.getLogisticsContent() != null) {
			logisticscontent=logistics.getLogisticsContent();
		}
		body.add(logisticscontent);
		body.add(Chunk.NEWLINE);
		document.add(body);
		body.clear();
		document.add(Chunk.NEWLINE);

		// Displaying List of Participants.
		header.add(new Paragraph("PARTICIPANTS AND CONTACT INFO"));
		header.add(Chunk.NEWLINE);
		document.add(header);
		header.clear();
	/*	int size = 0;
		if (!members.isEmpty()) {
			size = members.size();
		}*/
		Table table = new Table(4);
		table.setPadding(3);
		table.addCell("");

		table.addCell("Name");
		table.addCell("Email");
		table.addCell("Phone");

		for (TripMemberDto member : members) {
			
			if(member.getTripCaddieUserDto().getUserId()!=0){
			if (member.getRoleInTripDto().getRoleName()
					.equalsIgnoreCase("Trip Leader")) {
				table.addCell("trip Leader");
			} else {
				table.addCell(" ");
			}
			String handicap = "HCI";
			if (member.getTripCaddieUserDto().getGolfHandicap() != null) {
				handicap = String.valueOf(member.getTripCaddieUserDto()
						.getGolfHandicap());
			}
			String firstname="";
			if(member.getTripCaddieUserDto().getFirstName()!=null){
			firstname=member.getTripCaddieUserDto().getFirstName();
			}
			String lastname="";
			if(member.getTripCaddieUserDto().getLastName()!=null){
				lastname=member.getTripCaddieUserDto().getLastName();
				}
			table.addCell( firstname+ " "
					+ lastname + "("
					+ handicap + ")");
			table.addCell(member.getTripCaddieUserDto().getEmail());
			table.addCell(member.getTripCaddieUserDto().getPhoneNo());

		}
		}header.clear();
		document.add(table);
		
		response.setContentType("application/pdf");		
		response.setHeader("Content-Disposition","attachment; filename=\"" + "Itinerary" +"\"");
	}
		
	

}
