package com.vueblog.mapper;

import com.vueblog.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vueblog.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ……hrdate……
 * @since 2021-12-10
 */
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    List<MessageDTO>userGetMssagesList();
}
