package com.gaurav.springcloudproject.databases.blogdb.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Entity
@Table(name = "blogs")
@Data
public class BlogCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name="body",nullable = false)
    private String body;
    @Column(name="title",nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "blog_user",referencedColumnName = "id")
    private BlogUsers blogUsers;
    @Column(name = "createdby")
    private Long createdBy;

}
