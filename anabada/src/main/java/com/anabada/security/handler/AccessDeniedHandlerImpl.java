package com.anabada.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.anabada.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		UserDTO user = (UserDTO) auth.getPrincipal();
		
		log.debug("사용자 이메일 : {}", user.getUser_email());
		log.debug("사용자 권한 : {}", user.getAuthorities());
		
	}

}
