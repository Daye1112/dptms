package com.darren1112.dptms.file.controller;

import com.darren1112.dptms.common.core.message.JsonResult;
import com.darren1112.dptms.common.core.util.ResponseEntityUtil;
import com.darren1112.dptms.common.core.validate.ValidatorBuilder;
import com.darren1112.dptms.common.core.validate.handler.ValidateHandler;
import com.darren1112.dptms.common.core.validate.validator.callback.common.NotBlankValidatorCallback;
import com.darren1112.dptms.common.core.validate.validator.callback.common.NotNullValidatorCallback;
import com.darren1112.dptms.common.spi.file.dto.FileCenterDto;
import com.darren1112.dptms.file.common.enums.FileManageErrorCodeEnum;
import com.darren1112.dptms.file.service.FileCenterService;
import com.darren1112.dptms.sdk.starter.log.annotation.Log;
import com.darren1112.dptms.sdk.starter.log.enums.BusinessType;
import com.darren1112.dptms.sdk.starter.log.enums.LogLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件中心Controller
 *
 * @author darren
 * @since 2021/12/18
 */
@Slf4j
@Api(tags = "文件中心")
@RestController
@RequestMapping(value = "/fileCenter")
public class FileCenterController {

    @Autowired
    private FileCenterService fileCenterService;

    /**
     * 查询文件列表
     *
     * @param dto 查询条件
     * @return {@link JsonResult}
     * @author darren
     * @since 2021/12/18
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询文件列表")
    @Log(value = "查询文件列表", logLevel = LogLevel.DEBUG, businessType = BusinessType.QUERY)
    public ResponseEntity<JsonResult<List<FileCenterDto>>> list(FileCenterDto dto) {
        ValidateHandler.checkNull(dto.getParentId(), FileManageErrorCodeEnum.FILE_PARENT_ID_NOT_NULL);
        List<FileCenterDto> list = fileCenterService.list(dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccessData(list));
    }

    /**
     * 创建文件/文件夹
     *
     * @param dto 文件/文件夹信息
     * @return {@link JsonResult}
     * @author darren
     * @since 2021/12/18
     */
    @PostMapping("/insert")
    @ApiOperation(value = "创建文件/文件夹")
    @Log(value = "创建文件/文件夹", logLevel = LogLevel.INFO, businessType = BusinessType.INSERT)
    public ResponseEntity<JsonResult> insert(FileCenterDto dto) {
        ValidatorBuilder.build()
                .on(dto.getFileName(), new NotBlankValidatorCallback(FileManageErrorCodeEnum.FILE_NAME_NOT_NULL))
                .on(dto.getFileType(), new NotNullValidatorCallback(FileManageErrorCodeEnum.FILE_TYPE_NOT_NULL))
                .on(dto.getFileParentId(), new NotNullValidatorCallback(FileManageErrorCodeEnum.FILE_PARENT_ID_NOT_NULL))
                .on(dto.getFileParentPath(), new NotNullValidatorCallback(FileManageErrorCodeEnum.FILE_PARENT_PATH_NOT_NULL))
                .doValidate().checkResult();
        fileCenterService.insert(dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccess());
    }

    /**
     * 更新文件/文件夹
     *
     * @param dto 文件/文件夹信息
     * @return {@link JsonResult}
     * @author darren
     * @since 2023/08/12
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新文件/文件夹")
    @Log(value = "更新文件/文件夹", logLevel = LogLevel.INFO, businessType = BusinessType.UPDATE)
    public ResponseEntity<JsonResult> update(FileCenterDto dto) {
        ValidatorBuilder.build()
                .on(dto.getId(), new NotNullValidatorCallback(FileManageErrorCodeEnum.FILE_ID_NOT_NULL))
                .on(dto.getFileName(), new NotBlankValidatorCallback(FileManageErrorCodeEnum.FILE_NAME_NOT_NULL))
                .doValidate().checkResult();
        fileCenterService.update(dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccess());
    }

    /**
     * 删除文件/文件夹
     *
     * @param dto 文件/文件夹信息
     * @return {@link JsonResult}
     * @author darren
     * @since 2023/08/12
     */
    @GetMapping("/deleteById")
    @ApiOperation(value = "删除文件/文件夹")
    @Log(value = "删除文件/文件夹", logLevel = LogLevel.INFO, businessType = BusinessType.DELETE)
    public ResponseEntity<JsonResult> deleteById(FileCenterDto dto) {
        ValidatorBuilder.build()
                .on(dto.getId(), new NotNullValidatorCallback(FileManageErrorCodeEnum.FILE_ID_NOT_NULL))
                .doValidate().checkResult();
        fileCenterService.deleteById(dto);
        return ResponseEntityUtil.ok(JsonResult.buildSuccess());
    }
}
