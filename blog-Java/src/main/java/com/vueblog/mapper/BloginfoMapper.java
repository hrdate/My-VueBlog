package com.vueblog.mapper;

import com.vueblog.entity.Bloginfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-22
 */
@Mapper
@Repository
public interface BloginfoMapper extends BaseMapper<Bloginfo> {

}
