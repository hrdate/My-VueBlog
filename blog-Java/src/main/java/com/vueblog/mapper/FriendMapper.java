package com.vueblog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vueblog.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-06
 */
@Mapper
@Repository
public interface FriendMapper extends BaseMapper<Friend> {


}
