package com.vueblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "分页对象")
public class PageVO<T> {
    /**
     * 分页列表
     */
    @ApiModelProperty(name = "records", value = "分页列表", required = true, dataType = "List<T>")
    private List<T> records;


    /**
     * 总数
     */
    @ApiModelProperty(name = "total", value = "总数", required = true, dataType = "long")
    private long total;


}
