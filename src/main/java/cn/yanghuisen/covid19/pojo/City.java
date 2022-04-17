package cn.yanghuisen.covid19.pojo;

import cn.yanghuisen.covid19.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class City extends BaseEntity {
    /**
     * 省ID
     */
    private Long proId;
    /**
     * 城市ID
     */
    private String name;
}
