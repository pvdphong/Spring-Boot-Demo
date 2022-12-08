package com.myclass.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JwtAutheticationFilter extends BasicAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;

	public JwtAutheticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// B1: LAY TOKEN TU REQUEST
			String authorization = request.getHeader("Authorization");
			System.out.println(authorization);
			// B2: GIAI NGUOC TOKEN LAY EMAIL DA LUU
			if (authorization != null && authorization.startsWith("Bearer ")) {
				String token = authorization.replace("Bearer ", "");
				String email = Jwts.parser()
						.setSigningKey("chuoi_bi_mat")
						.parseClaimsJws(token)
						.getBody()
						.getSubject();

				// B3: TRUY VAN DB LAY THONG TIN USER (SU DUNG EMAIL VUA LAY TU TOKEN)
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);

				// B4: LUU THONG TIN USER VAO SECURITYCONTEXT(gan giong nhu session, bat ki cho
				// nao cung lay ra xai) ---> DE PHAN QUYEN
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
			}

			chain.doFilter(request, response);

		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus(401);
		}
	}
}
