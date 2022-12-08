package com.myclass.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.myclass.dto.RoleDto;
import com.myclass.service.RoleService;

@CrossOrigin(origins = { "*"})
@RestController
@RequestMapping("api/role")
public class ApiRoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping(value = "")
	public ResponseEntity<Object> getAllRole() {
		try {
			List<RoleDto> dtos = roleService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable("id") int id) {
		try {
			RoleDto dto = roleService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "post")
	public ResponseEntity<Object> createRole(@RequestBody RoleDto dto) { // add
		try {
			roleService.save(dto);
			return new ResponseEntity<Object>("Them thanh cong", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "put/{id}") // phai truyen ca Id va data
	public ResponseEntity<Object> updateRole(@RequestBody RoleDto dto, @PathVariable("id") int id) { // edit
		try {
			if (roleService.getById(id) == null) {
				return new ResponseEntity<Object>("Sai id!", HttpStatus.NOT_FOUND);
			}
			roleService.edit(dto);
			return new ResponseEntity<Object>("Cap nhat thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable("id") int id) {
		try {
			if (roleService.getById(id) == null) {
				return new ResponseEntity<Object>("Sai id!", HttpStatus.NOT_FOUND);
			}
			roleService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

}
