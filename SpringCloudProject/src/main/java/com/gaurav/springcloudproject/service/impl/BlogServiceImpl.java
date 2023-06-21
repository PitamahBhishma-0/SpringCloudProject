package com.gaurav.springcloudproject.service.impl;

import com.gaurav.springcloudproject.databases.blogdb.entity.BlogCollection;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.dto.request.BlogCreateRequest;
import com.gaurav.springcloudproject.dto.request.BlogModifyRequest;
import com.gaurav.springcloudproject.dto.response.BlogResponse;
import com.gaurav.springcloudproject.exception.ProcessNotAllowedException;
import com.gaurav.springcloudproject.exception.ServerResponse;
import com.gaurav.springcloudproject.databases.blogdb.repo.BlogServiceRepo;
import com.gaurav.springcloudproject.service.BlogService;
import com.gaurav.springcloudproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Service
public class BlogServiceImpl implements BlogService {

   private final BlogServiceRepo blogServiceRepo;

   private  final UserService userService;

    public BlogServiceImpl(BlogServiceRepo blogServiceRepo, UserService userService) {
        this.blogServiceRepo = blogServiceRepo;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Object> createBlog(BlogCreateRequest blogCreateRequest) {
        BlogCollection blogCollection=new BlogCollection();
        BlogUsers blogUsers=userService.getLoggedInUser();
        if(blogUsers==null){
            throw new ProcessNotAllowedException("User does not exist");
        }
        ServerResponse serverResponse=new ServerResponse();
        try{
            if(blogCreateRequest ==null) throw new ProcessNotAllowedException("Invalid Request");
            blogCollection.setCreatedBy(blogUsers.getId());
            blogCollection.setTitle(blogCreateRequest.getTitle());
            blogCollection.setBody(blogCreateRequest.getBody());
            blogServiceRepo.save(blogCollection);
            return ServerResponse.successResponse("Blog Created Successfully");

        }
        catch (Exception e){
            serverResponse.setMessage(e.getMessage());
            serverResponse.setSuccess(Boolean.FALSE);
        }
        return new ResponseEntity<>(serverResponse,HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<Object> modifyBlog(BlogModifyRequest modifyBlogCreateRequest) {
        ServerResponse serverResponse=new ServerResponse();
        try{
            BlogCollection blogCollection=blogServiceRepo.getBlogById(modifyBlogCreateRequest.getId());
            BlogUsers blogUsers=userService.getLoggedInUser();
            if(blogUsers==null){
                throw new ProcessNotAllowedException("User does not exist");
            }
            if(!Objects.equals(blogUsers.getId(), blogCollection.getCreatedBy())) throw new ProcessNotAllowedException("Cannot update the blog");
            blogCollection.setBody(modifyBlogCreateRequest.getBody());
            blogCollection.setTitle(modifyBlogCreateRequest.getTitle());
            blogServiceRepo.save(blogCollection);
            return ServerResponse.successResponse("Blog modified successfully");

        }
        catch (Exception e){
            serverResponse.setMessage(e.getMessage());
            serverResponse.setSuccess(Boolean.FALSE);
        }
        return new ResponseEntity<>(serverResponse,HttpStatus.CONFLICT);    }

    @Override
    public ResponseEntity<Object> deleteBlog(Long id) {
        BlogCollection blogCollection=blogServiceRepo.getBlogById(id);
        if(blogCollection==null) throw new ProcessNotAllowedException("Blog does not exists");
        blogServiceRepo.delete(blogCollection);
        return ServerResponse.successResponse("Blog delete successfully");
    }


    @Override
    public BlogResponse getBlogById(Long blogId) {
        BlogResponse  blogResponse = new BlogResponse();
        BlogCollection blogCollection=blogServiceRepo.getBlogById(blogId);
        if(blogCollection==null) throw new ProcessNotAllowedException("Blog does not exists");
        blogResponse.setBody(blogCollection.getBody());
        blogResponse.setTitle(blogCollection.getTitle());
        return blogResponse;

    }
}
