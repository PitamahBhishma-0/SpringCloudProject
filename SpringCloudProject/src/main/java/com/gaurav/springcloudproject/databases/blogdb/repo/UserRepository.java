package com.gaurav.springcloudproject.databases.blogdb.repo;

import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Repository
public interface UserRepository extends JpaRepository<BlogUsers,Long> {

    @Query(value = "SELECT u.* from user u where email = :userName", nativeQuery = true)
    BlogUsers findUserByEmail(String userName);

    @Query(value = "SELECT count(u.id) from user u where u.email =:email ",nativeQuery = true)
    Long checkIfEmailExists(String email);
}

