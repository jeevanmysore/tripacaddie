package com.tripcaddie.common.scheduler;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	
	protected static Logger logger=Logger.getLogger(SchedulerService.class);
	
	@Resource(name="scheduler")
	private SchedulerMethod schedulerMethod;
	
	//Cron format min hour days of month month weekday year 
	/*@Scheduled(cron="15 17 11 * * *")*/
	/*12:05
	@Scheduled(cron="0 05 12 * * *")*/
	//@Scheduled(cron="* * * * * *")
	public void doSchedule(){
		logger.debug("start schedule");
		System.out.println("start schedule");
		
		try {
			//schedulerMethod.sendAnnualTripRemainder();
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		try {
			schedulerMethod.sendBirthdayMail();
		} catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		
		System.out.println("End schedule");
		
	}
	

}
