package cn.yanghuisen.covid19.mapper;

import cn.yanghuisen.covid19.pojo.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@Mapper
public interface LogMapper {
    int addLog(Log log);
}
