package com.myclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.dto.RoleDto;
import com.myclass.dto.UserDto;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService; 
	
	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String index(ModelMap model) {
		List<UserDto> dtos = userService.getAll(); 
		model.addAttribute("users", dtos); 
		return "user/index";
	}
	
	@GetMapping("add")
	public String add(ModelMap model) {
		List<RoleDto> roles = roleService.getAll(); 
		UserDto dto = new UserDto(); 
		model.addAttribute("roles", roles);
		model.addAttribute("user", dto); 
		return "user/add";
	}
	
	@PostMapping("add")
	public String add(@ModelAttribute("user") UserDto dto) {
		userService.save(dto);
		return "redirect:/user";
	}
	
	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, Model model) {
		UserDto dto = userService.getById(id); 
		List<RoleDto> roles = roleService.getAll(); 
		model.addAttribute("roles", roles);
		model.addAttribute("user", dto);
		return "user/edit";
	}
	
	@PostMapping("edit")
	public String edit(@ModelAttribute("user") UserDto dto ) {
		userService.edit(dto);
		return "redirect:/user";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user";
	}
	
}
