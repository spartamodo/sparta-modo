package com.example.sparta_modo.domain.user.security.filter;

import com.example.sparta_modo.domain.user.security.JwtUtil;
import com.example.sparta_modo.domain.user.security.UserDetailsImpl;
import com.example.sparta_modo.domain.user.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

		String tokenValue = jwtUtil.getTokenFromRequest(req);

		if (StringUtils.hasText(tokenValue)) {
			// JWT 토큰 substring
			tokenValue = jwtUtil.substringToken(tokenValue);


			if (!jwtUtil.validateToken(tokenValue)) {
				filterChain.doFilter(req, res);
				return;
			}

			Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

			setAuthentication(info.getSubject());

		}

		filterChain.doFilter(req, res);
	}

	// 인증 처리
	public void setAuthentication(String userEmail) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(userEmail);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String userEmail) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

		return new UsernamePasswordAuthenticationToken(((UserDetailsImpl)userDetails).getUser(), null, userDetails.getAuthorities());
	}
}