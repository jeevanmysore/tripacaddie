package com.tripcaddie.frontend.footer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.footer.dao.FooterDao;
import com.tripcaddie.backend.footer.model.FooterBugReport;
import com.tripcaddie.backend.footer.model.FooterSuggestFeature;
import com.tripcaddie.backend.footer.model.FooterTipYourCaddie;
import com.tripcaddie.common.util.EmailSender;

@Transactional
@Service("footerService")
public class FooterServiceImpl implements FooterService{

	@Resource(name = "footerDao")
	private FooterDao footerDao;
	
	@Resource(name="emailSender")
	private EmailSender emailSender;
	
	@Override
	public void sendReportBug(String email, String issue) throws Exception {
		FooterBugReport bugReport=new FooterBugReport();
		bugReport.setIssue(issue);
	if(email!=null && !email.isEmpty()){
		bugReport.setEmail(email);
	}
	footerDao.insertEntity(bugReport);
		//Send Email 
	if(email==null){
		email="anonymous";
	}
	emailSender.sendEmailFeedback(email, issue);
	}

	@Override
	public void sendSuggestFeature(String email, String idea) throws Exception {
		FooterSuggestFeature suggestFeature=new FooterSuggestFeature();
		suggestFeature.setIdea(idea);
		if(email!=null && !email.isEmpty()){
			suggestFeature.setEmail(email);
		}
		footerDao.insertEntity(suggestFeature);
		if(email==null ){
			email="anonymous";
		}
		//Send Email 
		emailSender.sendEmailFeedback(email, idea);
	}

	@Override
	public void sendTipYourCaddie(String email, String feedback) throws Exception {
		FooterTipYourCaddie tipYourCaddie=new FooterTipYourCaddie();
		tipYourCaddie.setFeedback(feedback);
		if(email!=null){
			tipYourCaddie.setEmail(email);
		}
		footerDao.insertEntity(tipYourCaddie);
		if(email==null){
			email="anonymous";
		}
		//Send Email 
		emailSender.sendEmailFeedback(email, feedback);
	}

}
