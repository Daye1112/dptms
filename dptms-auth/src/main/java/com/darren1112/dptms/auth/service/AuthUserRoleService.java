package com.darren1112.dptms.auth.service;

import com.darren1112.dptms.common.spi.auth.dto.AuthRoleDto;

import java.util.List;

/**
 * 用户角色Service
 *
 * @author darren
 * @since 2020/12/23 01:51
 */
public interface AuthUserRoleService {

    /**
     * 分配角色
     *
     * @param userId  用户id
     * @param roleIds 角色ids，逗号分隔
     * @param updater 更新者
     * @author darren
     * @since 20/12/23 01:48
     */
    void assignedRole(Long userId, String roleIds, Long updater);

    /**
     * 查询用户关联的角色list
     *
     * @param userId 用户id
     * @return {@link AuthRoleDto}
     * @author darren
     * @since 20/12/13 21:43
     */
    List<AuthRoleDto> listUserAssigned(Long userId);
}
