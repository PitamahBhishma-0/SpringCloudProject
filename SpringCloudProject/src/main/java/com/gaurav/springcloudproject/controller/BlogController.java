package com.gaurav.springcloudproject.controller;

import com.gaurav.springcloudproject.constant.HasAuthorityConstant;
import com.gaurav.springcloudproject.constant.PathConstant;
import com.gaurav.springcloudproject.dto.request.BlogCreateRequest;
import com.gaurav.springcloudproject.dto.request.BlogModifyRequest;
import com.gaurav.springcloudproject.service.BlogService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@RestController
@RequestMapping(PathConstant.BLOG)
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping(PathConstant.CREATE)
    @PreAuthorize(HasAuthorityConstant.MODIFY_BLOG)
    public ResponseEntity<?> createBlog(@RequestBody BlogCreateRequest blogCreateRequest) {
        return blogService.createBlog(blogCreateRequest);
    }
    @GetMapping(PathConstant.FETCH_BY_ID+"/"+PathConstant.WRAP_ID)
    public ResponseEntity<?> getBlogById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(blogService.getBlogById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping(PathConstant.MODIFY)
    @PreAuthorize(HasAuthorityConstant.MODIFY_BLOG)
    public ResponseEntity<?> updateBlog( @RequestBody BlogModifyRequest blogModifyRequest) {
       return  blogService.modifyBlog(blogModifyRequest);
    }

    @DeleteMapping(PathConstant.DELETE+"/"+PathConstant.WRAP_ID)
    @PreAuthorize(HasAuthorityConstant.DELETE_BLOG)
    public ResponseEntity<?> deleteBlog(@PathVariable("id") Long id) {
        blogService.deleteBlog(id);
    return new ResponseEntity<>(blogService.deleteBlog(id),HttpStatus.OK);
    }
}
