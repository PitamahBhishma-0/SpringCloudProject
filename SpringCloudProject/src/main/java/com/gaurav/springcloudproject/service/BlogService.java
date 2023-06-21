package com.gaurav.springcloudproject.service;

import com.gaurav.springcloudproject.dto.request.BlogCreateRequest;
import com.gaurav.springcloudproject.dto.request.BlogModifyRequest;
import com.gaurav.springcloudproject.dto.response.BlogResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */

public interface BlogService {

    public ResponseEntity<Object> createBlog(BlogCreateRequest blogCreateRequest);
    public ResponseEntity<Object> modifyBlog(BlogModifyRequest blogCreateRequest);
    public ResponseEntity<Object> deleteBlog(Long id);
    public BlogResponse getBlogById(Long blogId);


}
