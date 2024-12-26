package com.example.sparta_modo.domain.user;

import com.example.sparta_modo.domain.user.dto.SignUpDto;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public SignUpDto.Response signUp(SignUpDto.Request requestDto) {

		if(userRepository.existsUserByEmail(requestDto.getEmail())){
			throw new CommonException(ErrorCode.ALREADY_EXIST, "이미 가입된 이메일입니다.");
		}

		requestDto.encodePassword(passwordEncoder);

		User user = requestDto.toEntity();

		user = userRepository.save(user);

		return new SignUpDto.Response(user.getId(), user.getAuth().toString());
	}

}
