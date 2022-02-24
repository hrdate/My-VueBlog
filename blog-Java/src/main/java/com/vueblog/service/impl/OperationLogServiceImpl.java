package com.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.dto.OperationLogDTO;
import com.vueblog.entity.OperationLog;
import com.vueblog.mapper.OperationLogMapper;
import com.vueblog.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueblog.util.BeanCopyUtil;
import com.vueblog.vo.ConditionVO;
import com.vueblog.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public PageVO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO) {
        Page<OperationLog> page = new Page<>(conditionVO.getCurrentPage(),conditionVO.getPageSize());
        // 查询日志列表
        IPage<OperationLog> operationLogPage = this.page(page, new LambdaQueryWrapper<OperationLog>()
                .like(conditionVO.getKeyWords() != null, OperationLog::getOptModule, conditionVO.getKeyWords())
                .or()
                .like(conditionVO.getKeyWords() != null, OperationLog::getOptDesc, conditionVO.getKeyWords())
                .orderByDesc(OperationLog::getId));
        List<OperationLogDTO> operationLogDTOList = BeanCopyUtil.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageVO<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }
}
