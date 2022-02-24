package com.vueblog.mapper;

import com.vueblog.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-15
 */
@Mapper
@Repository
public interface TestMapper extends BaseMapper<Test> {

}
