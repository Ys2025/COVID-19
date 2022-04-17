package cn.yanghuisen.covid19.pojo;

import cn.yanghuisen.covid19.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {

    /**
     * 每天Id
     */
    private Long dayDataId;
    /**
     * 居住地
     */
    private String address;
}
