package com.example.sparta_modo.domain.user;

import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


	boolean existsUserByEmail(String email);

	@Query("select u from user u "
		+ "where u.email = :email and u.userStatus = 'ACTIVATED'")
	Optional<User> findActiveUserByEmail(@Param("email") String email);

	Optional<User> findByEmail(String email);

	default User findUserByEmail(String email) {
		return findByEmail(email)
			.orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_VALUE, "이메일이 일치하는 유저가 존재하지 않습니다"));
	}

}
