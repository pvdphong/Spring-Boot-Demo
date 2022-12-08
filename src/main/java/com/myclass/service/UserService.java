package com.myclass.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myclass.dto.IUserDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;


public interface UserService {
	void save(UserDto dto);
	List<UserDto> getAll();
	UserDto getById(int id);	
	void edit(UserDto dto);
	void delete(int id);
	
	User getUserByFullname(String fullname);
	Page<User> getUserPaging(int pageIndex, int pageSize);
	
	Page<IUserDto> getUserDtoPaging1(Pageable pageable);
	
	Page<UserDto> getUserDtoPaging(Pageable pageable);
	
}
