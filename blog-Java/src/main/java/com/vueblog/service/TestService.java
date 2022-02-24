package com.vueblog.service;

import com.vueblog.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-15
 */
public interface TestService extends IService<Test> {

    public boolean testAssert();
}
