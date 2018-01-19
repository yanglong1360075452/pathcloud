package com.lifetech.dhap.pathcloud.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义的filter，必须包含authenticationManager,accessDecisionManager,
 * securityMetadataSource三个属性 
 * 
 * @author Eric
 */
public class MyFilterSecurityInterceptor
				extends AbstractSecurityInterceptor implements Filter {
	private static Logger logger = LoggerFactory
			.getLogger(MyFilterSecurityInterceptor.class);

	@Autowired
	private MyInvocationSecurityMetadataSource
				myInvocationSecurityMetadataSource;
	
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;
	
	@Autowired
	@Qualifier("myAuthenticationManager")
	private AuthenticationManager authenticationManager;

	@PostConstruct
	public void init(){
		super.setAuthenticationManager(authenticationManager);
		super.setAccessDecisionManager(myAccessDecisionManager);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		FilterInvocation fi = new FilterInvocation(request, response, chain);

		logger.debug("requestURL:" + fi.getRequestUrl());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof UsernamePasswordAuthenticationToken){
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			List<Integer> permissions = UserContext.getLoginUserPermissions();

			SimpleGrantedAuthority authority;
			for (Integer permission : permissions) {
				authority = new SimpleGrantedAuthority(permission.toString());
				authorities.add(authority);
			}

			UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, authorities);
			newAuthentication.setDetails(authentication.getDetails());
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		}

		//在执行doFilter之前，进行权限的检查		
//        InterceptorStatusToken statusToken = super.beforeInvocation(fi);
		fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
	}

	
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	
	public void destroy() {
	}

	
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.myInvocationSecurityMetadataSource;
	}

	public MyInvocationSecurityMetadataSource getMyInvocationSecurityMetadataSource() {
		return myInvocationSecurityMetadataSource;
	}

	public void setMyInvocationSecurityMetadataSource(MyInvocationSecurityMetadataSource myInvocationSecurityMetadataSource) {
		this.myInvocationSecurityMetadataSource = myInvocationSecurityMetadataSource;
	}
}
