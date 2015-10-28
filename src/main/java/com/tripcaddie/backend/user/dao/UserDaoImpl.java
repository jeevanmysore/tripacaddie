package com.tripcaddie.backend.user.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tripcaddie.backend.user.model.Roles;
import com.tripcaddie.backend.user.model.TripCaddieUser;
import com.tripcaddie.backend.user.model.UserStatus;

import javax.annotation.Resource;

@Transactional
@Service("userDao")
public class UserDaoImpl implements UserDao {
	
	private static Logger logger=Logger.getLogger(UserDaoImpl.class);
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	/*@Resource(name="TripCaddieSessionFactory")
	private SessionFactory sessionFactory;*/
	
	protected Integer id;
		
	/*public boolean storeUserSessioninTripCaddie(HttpSession session) {
		Assert.notNull(session);
		
		boolean flag=false;
		try{
			Session hibernteSession=sessionFactory.openSession();
			String sql="INSERT into sessions(uid,sid,hostname,timestamp,cache) values(1076,'"+session.getId()+"','127.0.0.1',1341400054,0)";
			Query query=hibernteSession.createSQLQuery(sql);
			query.executeUpdate();
			flag=true;
			hibernteSession.close();
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
			flag=false;
		}
		return flag;
	}*/

	/*@Override
	public void clearSessionInTripcaddie(String session) {
		
		try{
			Session hibernteSession=sessionFactory.openSession();
			String sql="delete from sessions where sid='"+session+"'";
			Query query=hibernteSession.createSQLQuery(sql);
			query.executeUpdate();
			hibernteSession.close();
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
	}*/

	public UserStatus getUserStatus(int statusId) {
		
		UserStatus userStatus=null;
		try{
			 userStatus=hibernateTemplate.get(UserStatus.class, statusId);
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return userStatus;
	}

	public ArrayList<Roles> getRoles() {
		try{
			return (ArrayList<Roles>) hibernateTemplate.loadAll(Roles.class);
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		return null;
	}

	
	public Roles getRoles(int roleId) {
		
		try{
			return hibernateTemplate.get(Roles.class, roleId);
		}catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage()+" "+e.getCause());
		}
		
		return null;
	}

	
	public void updateNoofLogins(TripCaddieUser user) {
		
		try{
			hibernateTemplate.update(user);
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		
	}

	public String getAuthenticatedUser() {
		if(SecurityContextHolder.getContext()==null){
			System.out.println("context is null");
		}else if(SecurityContextHolder.getContext().getAuthentication()==null){
			System.out.println("auth is null");
		}else
		return SecurityContextHolder.getContext().getAuthentication().getName();
		return null;
	}

	@Override
	public boolean registerUser(TripCaddieUser tripCaddieUser) {
		boolean flag=false;
		try{
			hibernateTemplate.save(tripCaddieUser);
			flag=true;
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			System.out.println("Id:"+tripCaddieUser.getUserId());
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		//	e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isExistUser(String userName) {
		boolean flag=true;
		try{
			TripCaddieUser tripCaddieUser=this.getUserByUserName(userName);
			if(tripCaddieUser!=null){
				flag=true;
			}else{
				flag=false;
			}
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
		//	e.printStackTrace();
		}
		return flag;
	}

	@Override
	public TripCaddieUser getUserByUserName(String userName) {
		try{
			@SuppressWarnings("unchecked")
			ArrayList<TripCaddieUser> tripCaddieUsers= (ArrayList<TripCaddieUser>) hibernateTemplate.find("from TripCaddieUser where userName='"+userName+"'");
			if(!tripCaddieUsers.isEmpty())
				return tripCaddieUsers.get(0);
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TripCaddieUser getUserByEmail(String email){
		try{
			ArrayList<TripCaddieUser> tripCaddieUsers= (ArrayList<TripCaddieUser>) hibernateTemplate.find("from TripCaddieUser where email='"+email+"'");
			if(!tripCaddieUsers.isEmpty())
				return tripCaddieUsers.get(0);
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<UserStatus> getUserStatus() {
		try{
			return (ArrayList<UserStatus>) hibernateTemplate.find("from UserStatus");
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();	
		}
		return null;
	}

	@Override
	public boolean isExistEmail(String email) {
		boolean flag = true;
		try{
			TripCaddieUser tripCaddieUser=this.getUserByEmail(email);
			if(tripCaddieUser!=null){
				flag=true;
			}else{
				flag=false;
			}
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void updateUser(TripCaddieUser tripCaddieUser) {
		try{
			hibernateTemplate.merge(tripCaddieUser);
		}catch (Exception e) {
			logger.info(e.getMessage()+" "+e.getCause());
			//e.printStackTrace();
		}
	}

	@Override
	public TripCaddieUser getUserBasedOnId(int userId) throws Exception {
		return hibernateTemplate.load(TripCaddieUser.class, userId);
	}

}
