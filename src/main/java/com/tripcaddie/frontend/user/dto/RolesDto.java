package com.tripcaddie.frontend.user.dto;

import java.io.Serializable;

import com.tripcaddie.backend.user.model.Roles;

public class RolesDto implements Serializable {
	
	private static final long serialVersionUID = 6954912286812457258L;
	private int roleId;
	private String roleName;
	/*private Collection<TripCaddieUser> users=new ArrayList<TripCaddieUser>();*/
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/*public Collection<TripCaddieUser> getUsers() {
		return users;
	}
	public void setUsers(Collection<TripCaddieUser> users) {
		this.users = users;
	}*/
	public static void populate(RolesDto rolesDto,Roles roles) {
		
		rolesDto.setRoleId(roles.getRoleId());
		rolesDto.setRoleName(roles.getRoleName());
		//rolesDto.setUsers(null);
		/*for(TripCaddieUser user:roles.getUsers()){
			rolesDto.getUsers().add(TripcaddieUserDto.instantiate(user));
		}*/
		
	}
	
	public static RolesDto instantiate(Roles roles) {
		
		RolesDto rolesDto=new RolesDto();
		populate(rolesDto, roles);
		return rolesDto;
		
	}
}
