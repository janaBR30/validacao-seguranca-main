package com.devsuperior.demo.services;

import java.util.List;
import java.util.Optional;

import com.devsuperior.demo.projections.UserDetailsProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.repositories.UserRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

		if (result.size() == 0){
			throw new UsernameNotFoundException("User not found");
		}

		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection: result){
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;
	}
}
