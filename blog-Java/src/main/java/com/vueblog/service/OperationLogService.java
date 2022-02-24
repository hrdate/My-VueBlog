package com.vueblog.service;

import com.vueblog.dto.OperationLogDTO;
import com.vueblog.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vueblog.vo.ConditionVO;
import com.vueblog.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
public interface OperationLogService extends IService<OperationLog> {

    PageVO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);
}
