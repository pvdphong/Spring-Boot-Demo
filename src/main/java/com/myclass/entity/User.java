package com.myclass.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myclass.dto.UserDto;

@Entity
@NamedNativeQuery(
	    name = "UserDtomapping",
	    query = "SELECT u.id as id, u.email as email, u.password as password, u.fullname as fullname, "
				+ "u.phone as phone, u.address as address, u.avatar as avatar, u.role_id as roleId, r.name as roleName"
				+ " FROM users u JOIN roles r ON u.role_id = r.id LIMIT :pageIndex, :pageSize " ,
	    resultSetMapping = "UserDtomapping"
	)
@SqlResultSetMapping(
		name = "UserDtomapping", 
		classes = @ConstructorResult(
				targetClass = UserDto.class,
				columns = {
						@ColumnResult(name = "id"), 
						@ColumnResult(name = "email"), 
						@ColumnResult(name = "password"), 
						@ColumnResult(name = "fullname"), 
						@ColumnResult(name = "phone"), 
						@ColumnResult(name = "address"), 
						@ColumnResult(name = "avatar"), 
						@ColumnResult(name = "roleId"), 
						@ColumnResult(name = "roleName")
				}
				)
		)

@Table(name = "users")
public class User {
	
	@Id   
	@Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	private int id; 
	
	private String email; 
	private String password; 	
	private String fullname;
	private String phone;
	private String address;
	private String avatar;
	
	@Column(name = "role_id")
	private int roleId;
	
	@ManyToOne
	@JoinColumn(name = "role_id", // khai bao khoa ngoai ben bang roles, thong qua cot role_id
	insertable = false, updatable = false)   // khong cho tu dong cap nhat hoac insert, bat buoc co 
	
	@JsonIgnore // ngat tai day, k parse thanh json
	private Role role;  // role_id
	
	public User() {
	}
	
	public User(int id, String email, String password, String fullname, String phone, String address, String avatar,
			int roleId, Role role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.roleId = roleId;
		this.role = role;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}	
