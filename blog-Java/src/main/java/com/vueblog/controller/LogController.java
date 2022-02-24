package com.vueblog.controller;


import com.vueblog.common.lang.Result;
import com.vueblog.service.OperationLogService;
import com.vueblog.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
@Api("日志模块")
@RestController
public class LogController {
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result listOperationLogs(ConditionVO conditionVO) {
        return Result.succ(operationLogService.listOperationLogs(conditionVO));
    }
}
