package com.example.sparta_modo.domain.user;

import com.example.sparta_modo.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsUserByEmail(String email);	

}