package com.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ……hrdate……
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_operation_log")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作模块
     */
    private String optModule;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 操作url
     */
    private String optUrl;

    /**
     * 操作方法
     */
    private String optMethod;

    /**
     * 操作描述
     */
    private String optDesc;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 返回数据
     */
    private String responseData;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 操作ip
     */
    private String ipAddress;

    /**
     * 操作地址
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
