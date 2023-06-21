package com.gaurav.springcloudproject.databases.blogdb.repo;

import com.gaurav.springcloudproject.databases.blogdb.entity.BlogCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Repository
public interface BlogServiceRepo extends JpaRepository<BlogCollection,Long> {

    @Query(value = "SELECT b.* from blogs b where b.id = :id",nativeQuery = true)
    public BlogCollection  getBlogById(Long id);
}
