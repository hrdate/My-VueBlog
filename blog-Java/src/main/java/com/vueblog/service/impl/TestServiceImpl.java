package com.vueblog.service.impl;

import cn.hutool.core.lang.Assert;
import com.vueblog.entity.Test;
import com.vueblog.mapper.TestMapper;
import com.vueblog.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-15
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public boolean testAssert() {
        Assert.isTrue(false,"测试断言错误");
        return true;
    }
}
