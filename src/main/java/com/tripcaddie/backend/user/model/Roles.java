package com.tripcaddie.backend.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Roles {
	
	@Id
	@Column(name="role_id")
	private int roleId;
	@Column(name="role_name")
	private String roleName;
	
	/*@ManyToMany(mappedBy="roles")
	private Collection<TripCaddieUser> users=new ArrayList<TripCaddieUser>();  */
	
	/*public Collection<TripCaddieUser> getUsers() {
		return users;
	}
	public void setUsers(Collection<TripCaddieUser> users) {
		this.users = users;
	}*/
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
	
}
