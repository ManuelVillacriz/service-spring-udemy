package com.udemy.marketplace.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.udemy.marketplace.commons.entity.User;
import com.udemy.marketplace.security.service.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		// if(authentication.getName().equalsIgnoreCase("frontendapp")) {
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + userDetails.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);

		User user = usuarioService.findByUserName(authentication.getName());
		
		if(user.getIntentos() != null && user.getIntentos() > 0) {
			user.setIntentos(0);
			usuarioService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: " + exception.getMessage();
		log.error(mensaje);
		System.out.println(mensaje);

		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			
			User user = usuarioService.findByUserName(authentication.getName());
			if (user.getIntentos() == null) {
				user.setIntentos(0);
			}
			
			log.info("Intentos actual es de: " + user.getIntentos());
			
			user.setIntentos(user.getIntentos()+1);
			
			log.info("Intentos después es de: " + user.getIntentos());
			
			errors.append(" - Intentos del login: " + user.getIntentos());
			
			if(user.getIntentos() >= 3) {
				String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.", user.getUserName());
				log.error(errorMaxIntentos);
				errors.append(" - " + errorMaxIntentos);
				user.setEnabled(false);
			}
			
			usuarioService.update(user, user.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}

}
