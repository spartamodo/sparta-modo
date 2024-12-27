package com.example.sparta_modo.domain.user;

import static com.example.sparta_modo.global.exception.errorcode.UserErrorCode.ALREADY_DELETED_USER;

import com.example.sparta_modo.domain.user.dto.LoginDto;
import com.example.sparta_modo.domain.user.dto.SignUpDto;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.enums.UserStatus;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.UserException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public SignUpDto.Response signUp(SignUpDto.Request requestDto) {

		if(userRepository.existsUserByEmail(requestDto.getEmail()))
			throw new CommonException(ErrorCode.ALREADY_EXIST, "가입이 불가능한 이메일입니다.");

		User user = requestDto.toEntity(passwordEncoder);

		user = userRepository.save(user);

		return new SignUpDto.Response(user.getId(), user.getAuth().toString());
	}

	public boolean deleteUser(String userEmail) {

		User user = userRepository.findUserByEmail(userEmail);

		if(user.getUserStatus().equals(UserStatus.DEACTIVATED))
			throw new UserException(ALREADY_DELETED_USER);

		user.deleteUser();

		return true;
	}

	public LoginDto.Response confirmLoginRequest(LoginDto.Request requestDto) {
		User user = userRepository.findActiveUserByEmail(requestDto.getEmail())
			.orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_VALUE, "아이디, 비밀번호가 불일치합니다."));

		if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
			throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "아이디, 비밀번호가 불일치합니다.");

		return new LoginDto.Response(user.getId(), user.getEmail(), user.getAuth());
	}

	public User loadUserByEmail(String email) {
		return userRepository.findActiveUserByEmail(email)
			.orElse(null);
	}
}
