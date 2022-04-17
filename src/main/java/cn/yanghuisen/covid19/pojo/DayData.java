package cn.yanghuisen.covid19.pojo;

import cn.yanghuisen.covid19.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayData extends BaseEntity {
    /**
     * 城市
     */
    private Long cityId;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    /**
     * 确诊
     */
    private Integer diagnosis;
    /**
     * 无症状
     */
    private Integer asymptomatic;
}
