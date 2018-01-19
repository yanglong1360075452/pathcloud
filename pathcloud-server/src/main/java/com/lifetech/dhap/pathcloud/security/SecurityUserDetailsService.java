package com.lifetech.dhap.pathcloud.security;

import com.lifetech.dhap.pathcloud.user.domain.UserRepository;
import com.lifetech.dhap.pathcloud.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 安全资源（URL）和角色映射关系处理器
 * 
 * @author Eric
 */
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = userRepository.selectByUsername(username);
		
		if (null == user) {
			throw new UsernameNotFoundException("用户名或密码错误");
		}

		Boolean userEnabled = user.getStatus()&&!user.getLockStatus();

		Collection<GrantedAuthority> authorities = new ArrayList<>();

		List<Integer> permissions = userRepository.selectUserPermissionsCode(user.getId());

		SimpleGrantedAuthority authority;
		for (Integer permission : permissions) {
			authority = new SimpleGrantedAuthority(permission.toString());
			authorities.add(authority);
		}

		//创建UserDetails对象
		SecurityUserDetails userDetails = new SecurityUserDetails(user.getId().intValue(),
				user.getUserName(), user.getPassword(), user.getRoleId(),userEnabled, authorities);
		userDetails.setEmail(user.getEmail());
		userDetails.setPermission(permissions);
		userDetails.setPhone(user.getPhone());
		userDetails.setFirstName(user.getFirstName());

		return userDetails;
	}

}
