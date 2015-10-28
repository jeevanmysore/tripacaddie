package com.tripcaddie.common.securityService;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.frontend.user.dto.RolesDto;
import com.tripcaddie.frontend.user.dto.TripcaddieUserDto;
import com.tripcaddie.frontend.user.service.LoginService;

@Transactional
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private static Logger logger=Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	public UserDetails loadUserByUsername(String username)
		      throws UsernameNotFoundException, DataAccessException {
			
		
		    TripcaddieUserDto userEntity = null;
			try {
				userEntity = loginService.getUserByUserName(username);
			} catch (Exception e) {
				logger.info(e.getMessage()+" "+e.getCause());
				//e.printStackTrace();
			}
		    logger.info("Its for Spring security");
		    
		    if (userEntity == null)
		    	throw new UsernameNotFoundException("user not found");

		    boolean flag;
		    String userName = userEntity.getUserName();
		   
		    if(userEntity.getUserStatusDto().getStatus().equalsIgnoreCase("active")){
		    	flag=true;
		    }else{
		    	flag=false;
		    }
		    String password = userEntity.getPassword();
		    
		    boolean enabled = flag;
		    boolean accountNonExpired = flag;
		    boolean credentialsNonExpired = flag;
		    boolean accountNonLocked = flag;

		    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		    for (RolesDto role : userEntity.getRolesDtos()) {
		      authorities.add(new GrantedAuthorityImpl(role.getRoleName()));
		    }
		    
		    User user = new User(userName,password, enabled,accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		    		   
		    return (UserDetails)user;
		   
		  }

}
