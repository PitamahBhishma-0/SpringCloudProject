package com.gaurav.springcloudproject.databases.blogdb.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Entity
@Table(name = "roles")
@Data
public class BlogUserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

}
