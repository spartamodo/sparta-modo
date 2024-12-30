package com.example.sparta_modo.domain.user.security;

import com.example.sparta_modo.domain.user.UserRepository;
import com.example.sparta_modo.global.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findActiveUserByEmail(username)
			.orElse(null);

		return new UserDetailsImpl(user);
	}
}
