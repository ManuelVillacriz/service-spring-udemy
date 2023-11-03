package com.udemy.marketplace.security.service;

import com.udemy.marketplace.commons.entity.User;

public interface IUsuarioService {
	
	public User findByUserName(String userName);
	
	public User update(User user, Long id);

}
