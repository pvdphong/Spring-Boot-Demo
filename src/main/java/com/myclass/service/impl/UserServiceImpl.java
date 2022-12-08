package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myclass.dto.IUserDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository; 

	@Override
	public void save(UserDto dto) {
		User entity = new User();
		
		// ma hoa mat khau su dung Bcrypt
		String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12)); // hash 12 lan
		
		entity.setEmail(dto.getEmail());
		entity.setPassword(hashed);
		entity.setFullname(dto.getFullname());
		entity.setPhone(dto.getPhone());
		entity.setAddress(dto.getAddress());
		entity.setAvatar(dto.getAvatar());
		entity.setRoleId(dto.getRoleId());
		
		userRepository.save(entity);
	}	
	

	@Override
	public List<UserDto> getAll() {
		List<User> entities = userRepository.findAll(); 
		List<UserDto> dtos = new ArrayList<UserDto>(); 
		
		for(User user: entities) {
			dtos.add(new UserDto(user.getId(), 
								user.getEmail(), 
								user.getPassword(), 
								user.getFullname(), 
								user.getPhone(), 
								user.getAddress(), 
								user.getAvatar(), 
								user.getRoleId(), 
								user.getRole().getName()
								)
					);
		}
	
		return dtos;
	}


	@Override
	public UserDto getById(int id) {	
		User user = userRepository.findById(id).get(); 
		UserDto dto = new UserDto( 	user.getId(),	
									user.getEmail(), 
									user.getPassword(), 
									user.getFullname(), 
									user.getPhone(), 
									user.getAddress(), 
									user.getAvatar(), 
									user.getRoleId(), 
									user.getRole().getName()
									); 
		return dto; 
	}
	

	@Override
	public void edit(UserDto dto) {
		User entity = userRepository.findById(dto.getId()).get();
		
		if(entity != null) {
			entity.setEmail(dto.getEmail());
			entity.setFullname(dto.getFullname());
			entity.setPhone(dto.getPhone());
			entity.setAddress(dto.getAddress());
			entity.setAvatar(dto.getAvatar());
			entity.setRoleId(dto.getRoleId());

			userRepository.save(entity);
		}	
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserByFullname(String fullname) {
		 return userRepository.findUserByFullname(fullname); 
	}


	@Override
	public Page<User> getUserPaging(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		PageRequest paging = PageRequest.of(pageIndex, pageSize); 
		return userRepository.findAll(paging);
	}


	@Override
	public Page<IUserDto> getUserDtoPaging1(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<IUserDto> dtos = userRepository.findAllUserRole(pageable);	
		return dtos; 
	}
	
	@Override
	public Page<UserDto> getUserDtoPaging(Pageable pageable) {
		Page<UserDto> dtos = userRepository.findAllUserDto(pageable);	
		return dtos; 
	}

}
