package com.myclass.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("api/auth")
public class ApiAuthController  {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value = "")
	public Object login(String email, String password) {
		try {
			// goi ham thuc hien dang nhap
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			
			//Neu khong xay ra exception
			//Set thong tin authetication vao security context 
			SecurityContextHolder.getContext().setAuthentication(authentication);	
			
			// goi phuong thuc tao chuoi token
			final String JWT_SECRET =  "chuoi_bi_mat";
			final long JWT_EXPIRATION = 846000000L;
			Date now = new Date();
			Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
			
			String token =  Jwts.builder()
					.setSubject(email)
					.setIssuedAt(now)
					.setExpiration(expiryDate)	
					.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
					.compact();
			
			// Nếu dang nhap thanh cong tra ve token cho nguoi dung 
			return new ResponseEntity<Object>(token,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("wrong email or password",HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
