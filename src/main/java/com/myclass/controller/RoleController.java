package com.myclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.dto.RoleDto;
import com.myclass.service.RoleService;

@Controller
@RequestMapping("role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String index(ModelMap model) {
		List<RoleDto> dtos = roleService.getAll();
		model.addAttribute("roles", dtos);
		return "role/index";
	}
	
	@RequestMapping(value = {"add"}, method = RequestMethod.GET)
	public String add(Model model) {
		// Tạo đối tượng rỗng (ko có dữ liệu)
		RoleDto dto = new RoleDto();
		// Truyền đối tượng vừa tạo qua cho add.html
		model.addAttribute("role", dto);
		return "role/add";
	}
	
	@RequestMapping(value = {"add"}, method = RequestMethod.POST)
	public String add(@ModelAttribute("role") RoleDto role) {
		
		roleService.save(role);
		
		return "redirect:/role";
	}
	
	@RequestMapping(value = {"edit"}, method = RequestMethod.GET)
	public String edit(@RequestParam("id") int id, Model model) {
		RoleDto dto = roleService.getById(id);
		model.addAttribute("role", dto);
		return "role/edit";
	}
	
	@RequestMapping(value = {"edit"}, method = RequestMethod.POST)
	public String edit(@ModelAttribute("role") RoleDto role) {
		
		roleService.edit(role);
		
		return "redirect:/role";
	}
	
	@RequestMapping(value = {"delete"}, method = RequestMethod.GET)
	public String delete(@RequestParam("id") int id) {
		
		roleService.delete(id);
		
		return "redirect:/role";
	}
}
