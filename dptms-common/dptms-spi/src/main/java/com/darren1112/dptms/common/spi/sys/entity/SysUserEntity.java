package com.darren1112.dptms.common.spi.sys.entity;

import com.darren1112.dptms.common.spi.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户Entity
 *
 * @author luyuhao
 * @date 20/07/23 02:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserEntity extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 性别 1：男 2：女
     */
    private Integer gender;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 密码更新时间
     */
    private Date pwdUpdateTime;

    /**
     * 头像文件id
     */
    private Long fileId;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 是否被锁定 0：否 1：是
     */
    private Integer isLocked;
}
