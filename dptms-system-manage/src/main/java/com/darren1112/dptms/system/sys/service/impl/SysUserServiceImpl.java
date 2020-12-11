package com.darren1112.dptms.system.sys.service.impl;

import com.darren1112.dptms.common.core.base.BaseService;
import com.darren1112.dptms.common.spi.sys.dto.SysUserDto;
import com.darren1112.dptms.system.sys.dao.SysUserDao;
import com.darren1112.dptms.system.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户ServiceImpl
 *
 * @author luyuhao
 * @date 2020/07/23 02:43
 */
@Service
@CacheConfig(cacheNames = "sysUser", keyGenerator = "keyGenerator")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class SysUserServiceImpl extends BaseService implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 根据id查询用户
     *
     * @param id id
     * @return {@link SysUserDto}
     * @author luyuhao
     * @date 20/11/30 23:12
     */
    @Override
    @Cacheable
    public SysUserDto getById(Long id) {
        return sysUserDao.getById(id);
    }
}
