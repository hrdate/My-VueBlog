package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_test")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 测试id
     */
    private Integer id;

    /**
     * 测试名
     */
    private String name;


}
