package com.myclass.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.service.UserService;

@CrossOrigin(origins = { "*"})
@RestController
@RequestMapping("api/user")
public class ApiUserController {
	private UserService userService;    // _ : bien local, # bien cuc bo 
	
	@Autowired
	public ApiUserController(UserService userService) {
		// TODO: inject userservice
		this.userService = userService;
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getAllUser() {
		try {
			List<UserDto> dtos = userService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") int id) {
		try {
			UserDto dto = userService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "post")
	public ResponseEntity<Object> createUser(@RequestBody UserDto dto) { // add
		try {
			userService.save(dto);
			return new ResponseEntity<Object>("Them thanh cong", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "put/{id}") // phai truyen ca Id va data
	public ResponseEntity<Object> updateUser(@RequestBody UserDto dto, @PathVariable("id") int id) { // edit
		try {
			if (userService.getById(id) == null) {
				return new ResponseEntity<Object>("Sai id!", HttpStatus.NOT_FOUND);
			}
			userService.edit(dto);
			return new ResponseEntity<Object>("Cap nhat thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") int id) {
		try {
			if (userService.getById(id) == null) {
				return new ResponseEntity<Object>("Sai id!", HttpStatus.NOT_FOUND);
			}
			userService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/fullname/first/{fullName}")
	public ResponseEntity<Object> getFirstUserByFullname(@PathVariable("fullName") String fullName){
		User result = userService.getUserByFullname(fullName);
		if(result != null)
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("No user was found.", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/paging/{pageIndex}/{pageSize}")
	public ResponseEntity<Object> getUserPaging(@PathVariable("pageIndex") int pageIndex,
												@PathVariable("pageSize") int pageSize) {
		if(pageIndex < 1 || pageSize < 1) {
			return new ResponseEntity<Object>("Invalid parameter", HttpStatus.BAD_REQUEST);
		}
		
		Pageable pageable = PageRequest.of(pageIndex-1, pageSize, Sort.by("id"));  
//		Page<IUserDto> result = userService.getUserDtoPaging1(pageable);
		Page<UserDto> result = userService.getUserDtoPaging(pageable);
		if(result.getSize() > 0)
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("No user was found.", HttpStatus.BAD_REQUEST);
	}

}
