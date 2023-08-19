package com.darren1112.dptms.common.spi.monitor.dto;

import com.darren1112.dptms.common.spi.monitor.entity.MonitorOperateLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 操作日志Dto
 *
 * @author darren
 * @since 2021/02/06 20:38
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MonitorOperateLogDto extends MonitorOperateLogEntity {
}
