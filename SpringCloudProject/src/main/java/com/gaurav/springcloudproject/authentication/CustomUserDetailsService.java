package com.gaurav.springcloudproject.authentication;

import com.gaurav.springcloudproject.authentication.model.UserDetailImpl;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.databases.blogdb.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUsers user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Retrieve user roles/authorities and convert them to GrantedAuthority objects
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailImpl(user, authorities);
    }

    public BlogUsers findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
