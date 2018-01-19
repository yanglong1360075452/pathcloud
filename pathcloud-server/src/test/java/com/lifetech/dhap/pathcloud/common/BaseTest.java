package com.lifetech.dhap.pathcloud.common;

import com.lifetech.dhap.pathcloud.security.SecurityUserDetails;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import org.junit.Before;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gdw on 4/21/16.
 */
public class BaseTest extends AbstractAuthenticationToken {

    public String username = "";
    public String password = "";

    public BaseTest(){
        super(null);
        this.setAuthenticated(true);
    }

    public Object getCredentials() {
        return password;
    }

    @Before
    public void setup() throws Exception {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(this);
        SecurityContextHolder.setContext(context);
    }

    public Object getPrincipal() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority;

        for (Permission permission : Permission.values()){
            authority = new SimpleGrantedAuthority(permission.toCode().toString());
            authorities.add(authority);
        }
        SecurityUserDetails userDetails = new SecurityUserDetails(1L, username, password, 1L, true, authorities);
        return userDetails;
    }

    public void setDetails(SecurityUserDetails details){
        super.setDetails(details);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
