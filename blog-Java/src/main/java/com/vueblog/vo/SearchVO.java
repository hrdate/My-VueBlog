package com.vueblog.vo;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchVO implements Serializable {

    @NotBlank(message = "搜索关键字不能为空")
    String keyWords;
}
