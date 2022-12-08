package com.myclass.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myclass.filter.JwtAutheticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService; 
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {   // cai 
		http.cors(); 
		
		// Disable crsf cho đường dẫn /api/**
		http.csrf().ignoringAntMatchers("/api/**");// chong gia mao request, form submit len phai do server tao ra, dung cho thymeleaf
		
		http.antMatcher("/api/**")  // chi nhung link bat dau bang /api thi  
		.authorizeRequests()   		 // moi thuc hien phan quyen va chay doan code o duoi de phan quyen 
		.antMatchers("/api/auth").permitAll()   //	 DOI VOI NHUNG LINK NAY KHONG CAN CHECK THONG TIN DANG NHAP                               // 
		.antMatchers("/api/role/**").hasAnyAuthority("ROLE_ADMIN")
		.antMatchers("/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
		.anyRequest()								// NHUNG LINK CON LAI 					
		.authenticated();							// BAT BUOC DANG NHAP TRUOC MOI CO THE TRUY XUAT(PHAI CO TOKEN)
		
		http.addFilter(new JwtAutheticationFilter(authenticationManager(), userDetailsService));
		
		// KHAI BAO KHONG DUNG SESSION
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {     // cau hinh cho nhung file static 
		// TODO Auto-generated method stub
		web.ignoring()
		.antMatchers("/v2/api-docs",
		"/configuration/ui",
		"/swagger-resources/**",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**");
	}
}
