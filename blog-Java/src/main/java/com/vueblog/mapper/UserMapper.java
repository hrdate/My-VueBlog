package com.vueblog.mapper;

import com.vueblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface UserMapper extends BaseMapper<User> {

    User getByEmail(@Param("email")String email);

    User getOneById(@Param("id")int id);

    void updateUserInfo(@Param("email")String email, @Param("userName")String userName,
                        @Param("webSite") String webSite, @Param("introduce")String introduce);
}
