package com.gaurav.springcloudproject.constant;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
public class HasAuthorityConstant {
    public static final String DELETE_BLOG ="hasAuthority('ADMIN')";
    public static final String MODIFY_BLOG = "hasAnyAuthority('ADMIN','USER')";
}
