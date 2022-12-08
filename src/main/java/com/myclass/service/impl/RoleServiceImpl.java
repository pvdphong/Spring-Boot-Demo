package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.RoleDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;

@Service
@Transactional(rollbackOn = Exception.class)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

//	@Autowired
//	private ModelMapper modelMapper;

	@Override
	public void save(RoleDto dto) {
		Role entity = new Role();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDesc());

		roleRepository.save(entity);
	}

//	@Override
//	public List<RoleDto> getAll() {
//		List<Role> entities = roleRepository.findAll();
//		TypeMap<Role, RoleDto> typeMap = modelMapper.getTypeMap(Role.class, RoleDto.class);  // vi model map la mot static field 
//		// neu da add r thi add lai se gay loi, neu chua add thi moi add vao
//		if (typeMap == null) { // if not  already added
//			modelMapper.addMappings(new PropertyMap<Role, RoleDto>() {
//				@Override
//				protected void configure() {
//					map().setDesc(source.getDescription());
//				}
//			});
//		}	
//		List<RoleDto> dtos = entities.stream().map(element -> modelMapper.map(element, RoleDto.class))
//				.collect(Collectors.toList());
//
//		return dtos;
//	}
	
	@Override
	public List<RoleDto> getAll() {
		List<Role> entities = roleRepository.findAll();
		List<RoleDto> dtos = new ArrayList<RoleDto>(); 
		
		for(Role role: entities) {
			dtos.add(new RoleDto(role.getId(), 
								role.getName(), 
								role.getDescription()
								)
					);
		}
	
		return dtos;
	}

	@Override
	public RoleDto getById(int id) {
		Optional<Role> optional = roleRepository.findById(id);
		if(optional.isEmpty()) {
			return null; 
		}
		
		Role entity = optional.get();
		return new RoleDto(entity.getId(), 
							entity.getName(), 
							entity.getDescription()
				);
	}

	@Override
	public void edit(RoleDto dto) {
		Role entity = roleRepository.findById(dto.getId()).get();
		if (entity != null) {
			entity.setName(dto.getName());
			entity.setDescription(dto.getDesc());

			roleRepository.save(entity);
		}
	}

	@Override
	public void delete(int id) {
		roleRepository.deleteById(id);
	}
}
