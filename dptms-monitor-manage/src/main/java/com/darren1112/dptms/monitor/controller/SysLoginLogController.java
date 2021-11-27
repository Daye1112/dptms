package com.darren1112.dptms.monitor.controller;

import com.darren1112.dptms.common.core.base.BaseController;
import com.darren1112.dptms.common.core.message.JsonResult;
import com.darren1112.dptms.common.core.util.ResponseEntityUtil;
import com.darren1112.dptms.common.log.starter.annotation.Log;
import com.darren1112.dptms.common.log.starter.enums.BusinessType;
import com.darren1112.dptms.common.log.starter.enums.LogLevel;
import com.darren1112.dptms.common.security.starter.util.DptmsSecurityUtil;
import com.darren1112.dptms.common.spi.common.dto.ActiveUser;
import com.darren1112.dptms.common.spi.common.dto.PageBean;
import com.darren1112.dptms.common.spi.common.dto.PageParam;
import com.darren1112.dptms.common.spi.sys.dto.SysLoginLogDto;
import com.darren1112.dptms.monitor.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志Controller
 *
 * @author luyuhao
 * @since 2021/02/06 20:45
 */
@Slf4j
@Api(tags = "登录日志管理")
@RestController
@RequestMapping(value = "/loginLog")
public class SysLoginLogController extends BaseController {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 插入登录日志信息
     *
     * @param dto 日志参数
     * @return {@link JsonResult)
     * @author luyuhao
     * @since 2021/02/06 20:50
     */
    @PostMapping("/insert")
    @ApiOperation(value = "插入登录日志", hidden = true)
    public ResponseEntity<JsonResult> insert(@RequestBody SysLoginLogDto dto) {
        sysLoginLogService.insert(dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccess());
    }

    /**
     * 分页查询登录日志
     *
     * @param dto       筛选参数
     * @param pageParam 分页参数
     * @return {@link JsonResult}
     * @author luyuhao
     * @since 20/12/10 01:08
     */
    @GetMapping("/listPage")
    @ApiOperation("分页查询登录日志")
    @Log(value = "分页查询登录日志", logLevel = LogLevel.DEBUG, businessType = BusinessType.QUERY)
    public ResponseEntity<JsonResult<PageBean<SysLoginLogDto>>> listPage(PageParam pageParam,
                                                                         SysLoginLogDto dto) {
        PageBean<SysLoginLogDto> pageBean = sysLoginLogService.listPage(getPageParam(pageParam), dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccessData(pageBean));
    }

    /**
     * 查询当前用户的登录日志
     *
     * @return {@link SysLoginLogDto}
     * @author luyuhao
     * @since 2021/11/27
     */
    @GetMapping("/listCurrentUser")
    @ApiOperation("查询当前用户的登录日志")
    @Log(value = "查询当前用户的登录日志", logLevel = LogLevel.DEBUG, businessType = BusinessType.QUERY)
    public ResponseEntity<JsonResult<List<SysLoginLogDto>>> listCurrentUser() {
        ActiveUser activeUser = DptmsSecurityUtil.get();
        List<SysLoginLogDto> list = sysLoginLogService.listLastSevenByUserId(activeUser.getId());
        return ResponseEntityUtil.ok(JsonResult.buildSuccessData(list));
    }
}
