package com.apointmentManagementSystem.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.apointmentManagementSystem.entity.Role;
import com.apointmentManagementSystem.entity.RolePermission;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IRolePermissionRepository;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.CacheOperationService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRolePermissionRepository rolePermissionRepository;

	@Autowired
	private CacheOperationService cache;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User getUser = null;
		try {
			if (!cache.isKeyExist(username, username)) {

				getUser = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(username).orElseThrow(
						() -> new ResourceNotFoundException(ErrorMessageConstant.INVALID_USERNAME_PASSWORD));
				cache.addInCache(username, username, getUser.toString());

			} else {
				String cacheUser = (String) cache.getFromCache(username, username);
				try {
					String[] parts = cacheUser.split(", ");
					int id = Integer.parseInt(parts[0].substring(parts[0].indexOf('=') + 1));
					String firstName = parts[1].substring(parts[1].indexOf('=') + 1);
					String LastName = parts[1].substring(parts[2].indexOf('=') + 1);
					String email = parts[2].substring(parts[3].indexOf('=') + 1);
					String password = parts[3].substring(parts[4].indexOf('=') + 1, parts[4].length() - 1);

					getUser = new User();
					getUser.setId(id);
					getUser.setFirstName(firstName);
					getUser.setLastName(LastName);
					getUser.setEmail(email);
					getUser.setPassword(password);

				} catch (Exception e) {
					System.out.println("EEOR " + e);
				}
			}
			return new CustomeUserDetail(getUser, getAuthority(getUser.getId()));
		} catch (RedisConnectionException e) {
			log.error(e.getMessage());
		} catch (RedisConnectionFailureException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}

		getUser = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(username)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.INVALID_USERNAME_PASSWORD));

		return new CustomeUserDetail(getUser, getAuthority(getUser.getId()));
	}

	public Set<SimpleGrantedAuthority> getAuthority(int userId) {

		Set<SimpleGrantedAuthority> authorities1 = new HashSet<>();

		String auth = null;

		try {

			if (!cache.isKeyExist(userId + "permissions", userId + "permission")) {
				Role role = userRepository.findByIdAndIsActiveTrue(userId).get().getRole();

				authorities1.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

				for (RolePermission map : rolePermissionRepository.findAllByRoleIdAndIsActiveTrue(role.getId())) {
					authorities1.add(new SimpleGrantedAuthority(map.getPermission().getAction()));
				}
//				System.err.println("come from db");
				cache.addInCache(userId + "permissions", userId + "permission", authorities1.toString());
			} else {
				auth = (String) cache.getFromCache(userId + "permissions", userId + "permission");
				String[] authorityArray = auth.replaceAll("\\[|\\]", "").split(",\\s*");

				for (int i = 0; i < authorityArray.length; i++) {
					authorities1.add(new SimpleGrantedAuthority(authorityArray[i]));
				}
			}

			return authorities1;

		} catch (RedisConnectionException e) {
			log.error(e.getMessage());

		} catch (RedisConnectionFailureException e) {
			log.error(e.getMessage());

		} catch (Exception e) {
			log.error(e.getMessage());
			// TODO: handle exception
		}

		Role role = userRepository.findByIdAndIsActiveTrue(userId).get().getRole();

		authorities1.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

		for (RolePermission map : rolePermissionRepository.findAllByRoleIdAndIsActiveTrue(role.getId())) {
			authorities1.add(new SimpleGrantedAuthority(map.getPermission().getAction()));
		}

		return authorities1;

	}

}
