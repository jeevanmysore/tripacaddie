package com.tripcaddie.frontend.user.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.user.dao.UserDao;
import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.backend.user.model.UserStatus;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService{

	@Resource(name="userDao")
	private UserDao userDao;
		
	@Override
	public boolean isExistUser(String userName)  throws Exception{
		return this.userDao.isExistUser(userName);
	}
	
	@Override
	public boolean isExistEmail(String email)  throws Exception{
		return this.userDao.isExistEmail(email);
	}

	@Override
	public void registerUser(String userName, String password, String email,
			String firstName, String lastName)  throws Exception{
		
		TripCaddieUser tripCaddieUser=new TripCaddieUser();
		tripCaddieUser=setTripCaddieUser(userName,password,email,firstName,lastName);
		userDao.registerUser(tripCaddieUser);
		tripCaddieUser=userDao.getUserByUserName(userName);
		
	}

	private TripCaddieUser setTripCaddieUser(String userName, String password,
			String email, String firstName, String lastName)  throws Exception{
		
		TripCaddieUser tripCaddieUser=new TripCaddieUser();
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		Calendar dateofbirth=Calendar.getInstance();
		calendar.setTime(date);
		/*DateFormat dateFormat;
		Date dateOfBirth=null;*/
		
		/*dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		try {
			dateOfBirth=(Date)dateFormat.parse(dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateofbirth.setTime(dateOfBirth);*/
		ArrayList<Roles> roles=this.userDao.getRoles();
		
		for(Roles role: roles){
			if(role.getRoleName().equalsIgnoreCase("ROLE_USER")){
				tripCaddieUser.getRoles().add(role);
				break;
			}
		}
		
		
		ArrayList<UserStatus> userStatus=this.userDao.getUserStatus();
		for(UserStatus status: userStatus){
			if(status.getStatus().equalsIgnoreCase("Inactive")){
				tripCaddieUser.setUserStatus(status);
				break;
			}
		}
				
		tripCaddieUser.setUserName(userName);
		tripCaddieUser.setPassword(password);
		tripCaddieUser.setEmail(email);
		tripCaddieUser.setFirstName(firstName);
		tripCaddieUser.setLastName(lastName);
		tripCaddieUser.setDateOfBirth(dateofbirth);
		tripCaddieUser.setNoOfLogins(0);
		tripCaddieUser.setCreatedBy(userName);
		tripCaddieUser.setCreatedDate(calendar);
		tripCaddieUser.setLastLoginDate(calendar);
		tripCaddieUser.setLanguage("en");
		tripCaddieUser.setInitialEmail(email);
		tripCaddieUser.setImageUrl("no-image.gif");
		
		return tripCaddieUser;
	}

}
