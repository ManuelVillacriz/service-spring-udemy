package com.udemy.marketplace.security.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.marketplace.commons.entity.User;

@FeignClient(name= "user-service")
public interface UserFeignClient {

	@GetMapping("/users/search/find_by_username")
	public User findByUserName(@RequestParam(name = "userName") String userName);
	
	@PutMapping("/users/{id}")
	public User update(@RequestBody User user, @PathVariable Long id);
}
