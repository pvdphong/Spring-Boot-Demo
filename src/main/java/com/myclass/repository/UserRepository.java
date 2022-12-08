package com.myclass.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.IUserDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findFirstByFullname(String fullname);
	
	@Query(value = "SELECT u.* FROM users u WHERE u.fullname LIKE CONCAT('%',:fullname,'%') LIMIT 1", nativeQuery = true)
	User findUserByFullname(@Param("fullname") String fullname); 
	
	@Query(value = "SELECT u.id as id, u.email as email, u.password as password, u.fullname as fullname, "
			+ "u.phone as phone, u.address as address, u.avatar as avatar, u.role_id as roleId, r.name as roleName"
			+ " FROM users u JOIN roles r ON u.role_id = r.id ", 
			countQuery = "select count(u.id) FROM users u JOIN roles r ON u.role_id = r.id",
			nativeQuery = true) 
	Page<IUserDto> findAllUserRole(Pageable pageable);
	
	
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.email, u.password, u.fullname, u.phone, u.address, u.avatar, u.roleId, r.name) FROM User u JOIN Role r ON u.roleId = r.id") 
	Page<UserDto> findAllUserDto(Pageable pageable);
	
//	@NamedNativeQuery(name = "UserDto", query = "SELECT u.id, u.email, u.password, u.fullname, u.phone, u.address, u.avatar, u.role_id, r.name "
//			+ "FROM users u JOIN roles r ON u.role_id = r.id LIMIT ?1, ?2", resultClass = UserDto.class)
//	List<UserDto> finDtos(int page, int pa); 
	
	@Query("SELECT new com.myclass.dto.UserDto(u.email, u.password, r.name) FROM User u JOIN Role r ON u.roleId = r.id WHERE u.email = :email") 
	UserDto findByEmail(@Param("email") String email);  // phuong thuc check email nguoi dung 
//	
}	
