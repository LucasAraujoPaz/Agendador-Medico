package com.group18.medical_scheduler.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group18.medical_scheduler.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByEmail(final String email);
}
