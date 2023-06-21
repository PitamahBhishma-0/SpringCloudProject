package com.gaurav.springcloudproject.service;

import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.dto.request.UserRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
public interface UserService {

    public ResponseEntity<Object> createUser(UserRequestDto userRequestDto);

    public BlogUsers getLoggedInUser();
}
