package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@TableName("m_unique_view")
public class UniqueView implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 访问量
     */
    private Integer viewsCount;


}
