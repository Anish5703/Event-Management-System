package com.ems.repository;

import org.springframework.stereotype.Repository;

import com.ems.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	User findByUsername(String username);

	User findByCookieId(String cookieId);

	
	
	@Query("SELECT u FROM User u WHERE u.username = :naam ORDER BY u.createdAt LIMIT 1")
	Optional<User> getMostRecentlyRegisteredUser(@Param("naam") String name);



	
	
}
