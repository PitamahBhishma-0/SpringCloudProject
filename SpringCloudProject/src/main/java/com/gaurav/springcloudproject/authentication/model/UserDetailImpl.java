package com.gaurav.springcloudproject.authentication.model;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */

@Data
public class UserDetailImpl implements UserDetails {

    private List<String> grantedAuthorities = Collections.emptyList();

    private final BlogUsers blogUsers;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailImpl(BlogUsers blogUsers, Collection<? extends GrantedAuthority> authorities) {
        this.blogUsers = blogUsers;
        this.authorities = authorities;
    }
    @Override
    public String getUsername() {
        return this.blogUsers.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        getGrantedAuthorities().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });

        // Extract list of roles (ROLE_name)
        getGrantedAuthorities().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(r);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.blogUsers.getPassword();
    }

    public List<String> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public BlogUsers getLoggedInUser(){
        return blogUsers;
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
        return true;
    }


}