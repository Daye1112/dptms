package com.darren1112.dptms.config.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darren1112.dptms.common.spi.service.dto.ServiceConfigReleasePropDto;
import com.darren1112.dptms.config.dao.ServiceConfigReleasePropDao;
import org.springframework.stereotype.Repository;

/**
 * 配置发布属性表Repository
 *
 * @author darren
 * @since 2022/11/20
 */
@Repository
public class ServiceConfigReleasePropRepository extends ServiceImpl<ServiceConfigReleasePropDao, ServiceConfigReleasePropDto> {
}
