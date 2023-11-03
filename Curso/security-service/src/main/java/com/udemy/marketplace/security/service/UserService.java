package com.udemy.marketplace.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.marketplace.commons.entity.User;
import com.udemy.marketplace.security.clients.UserFeignClient;

import feign.FeignException;

@Service
public class UserService implements IUsuarioService,UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class); 

	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		//try {
			User user = userFeignClient.findByUserName(userName);
			
			log.info("log mvc",user.getName());

			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(r -> new SimpleGrantedAuthority(r.getName())).peek(a -> log.info("Role: " + a.getAuthority()))
					.collect(Collectors.toList());
			
			log.info("usuario autenticado " + userName);

			return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
					user.getEnabled(), true, true, true, authorities);
			
//		} catch (FeignException e) {
//			String error = "Error en el login, no existe el usuario '" + userName + "' en el sistema";
//			log.error(error);
//
//			throw new UsernameNotFoundException(error);
//		}
	}

	@Override
	public User findByUserName(String userName) {
		return userFeignClient.findByUserName(userName);
	}
	
	@Override
	public User update(User user, Long id) {
		return userFeignClient.update(user, id);
	}
	

}
