package com.udemy.marketplace.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.udemy.marketplace.commons.entity.User;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

	@RestResource(path = "find_by_username")
	public User findByUserName(@Param("userName") String userName);
	// public User findByUserName(String userName);
}
