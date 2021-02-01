package com.autho.authservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuserDetails implements UserDetails {

	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	// SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ADMIN");

	public AuserDetails(User user) {
		this.userName = user.getUserName();
		this.password = user.getSecret();
		this.active = user.getIsActive();
		this.authorities = prepareAuthorities(user.getUserRoles());
	}

	private List<GrantedAuthority> prepareAuthorities(Set<Role> roleSet) {
		if (roleSet == null || roleSet.isEmpty()) {
			return new ArrayList<GrantedAuthority>();
		}
		return roleSet.stream().map(r -> r.getRolePermissions()).flatMap(rp -> rp.stream())
				.map(p -> new SimpleGrantedAuthority("ROLE_" + p.getCode().toUpperCase())).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
}