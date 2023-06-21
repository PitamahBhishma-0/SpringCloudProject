package com.gaurav.springcloudproject.databases.blogdb.repo;

import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Repository
public interface RoleRepository extends JpaRepository<BlogUserRoles,Long> {
}
