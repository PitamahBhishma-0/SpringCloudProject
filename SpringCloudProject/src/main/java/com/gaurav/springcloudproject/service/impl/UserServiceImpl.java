package com.gaurav.springcloudproject.service.impl;

import com.gaurav.springcloudproject.authentication.model.UserDetailImpl;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.databases.blogdb.repo.UserRepository;
import com.gaurav.springcloudproject.dto.request.UserRequestDto;
import com.gaurav.springcloudproject.exception.ProcessNotAllowedException;
import com.gaurav.springcloudproject.exception.ServerResponse;
import com.gaurav.springcloudproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Object> createUser(UserRequestDto userRequestDto) {
        ServerResponse serverResponse=new ServerResponse();
        try {
            BlogUsers  blogUsers=new BlogUsers();
            Long count=userRepository.checkIfEmailExists(userRequestDto.getEmail());
            if(count>0) throw new ProcessNotAllowedException("Email already exists");
            blogUsers.setEmail(userRequestDto.getEmail());
            blogUsers.setName(userRequestDto.getName());
            blogUsers.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            userRepository.save(blogUsers);
            return ServerResponse.successResponse("User Created Successfully");
        }
        catch (Exception e){
            serverResponse.setMessage(e.getMessage());
            serverResponse.setSuccess(Boolean.FALSE);
        }
         return new ResponseEntity<>(serverResponse,HttpStatus.BAD_REQUEST);
    }

    @Override
    public BlogUsers getLoggedInUser() {
        UserDetailImpl userInfo= (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getLoggedInUser();
    }
}
