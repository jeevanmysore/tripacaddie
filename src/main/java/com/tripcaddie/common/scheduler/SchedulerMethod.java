package com.tripcaddie.common.scheduler;

public interface SchedulerMethod {

	public void sendAnnualTripRemainder() throws Exception;
	public void sendBirthdayMail() throws Exception;
}
