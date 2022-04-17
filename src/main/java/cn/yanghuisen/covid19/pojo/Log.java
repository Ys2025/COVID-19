package cn.yanghuisen.covid19.pojo;

import cn.yanghuisen.covid19.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log extends BaseEntity {
    private String codeStr;
    private String detail;
}
