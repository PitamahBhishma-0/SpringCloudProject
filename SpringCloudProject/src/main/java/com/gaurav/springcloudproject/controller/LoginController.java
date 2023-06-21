package com.gaurav.springcloudproject.controller;

import com.gaurav.springcloudproject.authentication.CustomUserDetailsService;
import com.gaurav.springcloudproject.constant.PathConstant;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.databases.blogdb.repo.UserRepository;
import com.gaurav.springcloudproject.dto.request.UserLoginRequest;
import com.gaurav.springcloudproject.dto.request.UserRequestDto;
import com.gaurav.springcloudproject.dto.response.LoginResponse;
import com.gaurav.springcloudproject.exception.ProcessNotAllowedException;
import com.gaurav.springcloudproject.service.UserService;
import com.gaurav.springcloudproject.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@RestController
@RequestMapping(PathConstant.AUTH)
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    public LoginController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping(PathConstant.LOGIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest user) {
        BlogUsers blogUsers=userRepository.findUserByEmail(user.getEmail());
        if(blogUsers==null){
            throw new ProcessNotAllowedException("Invalid login");
        }
         String password=userRepository.findById(blogUsers.getId()).get().getPassword();
        boolean result= passwordEncoder.matches(user.getPassword(),password);
        if(!result) throw new ProcessNotAllowedException("Invalid login");

        UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
        String tok=jwtUtils.generateJwtToken(userDetails);


        return ResponseEntity.ok(new LoginResponse( userDetails.getUsername(),tok
        ));
    }
    @PostMapping(PathConstant.CREATE)
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);

    }



}
