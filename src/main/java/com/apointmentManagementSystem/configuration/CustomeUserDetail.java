package com.apointmentManagementSystem.configuration;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apointmentManagementSystem.entity.User;

public class CustomeUserDetail implements UserDetails {

	private static final long serialVersionUID = -4915978794891806571L;
	
	private  User user;
	
	private  Set<SimpleGrantedAuthority> getUserAuthorities;

	
	public CustomeUserDetail(User user) {
		super();
		this.user = user;
	}

	public CustomeUserDetail(User user, Set<SimpleGrantedAuthority> getUserAuthorities) {
		super();
		this.user = user;
		this.getUserAuthorities = getUserAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
