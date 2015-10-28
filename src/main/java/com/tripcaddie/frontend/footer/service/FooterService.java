package com.tripcaddie.frontend.footer.service;

public interface FooterService {

	void sendReportBug(String email , String issue) throws Exception;
	
	void sendSuggestFeature(String email , String idea) throws Exception;
	
	void sendTipYourCaddie(String email , String feedback) throws Exception;
}
