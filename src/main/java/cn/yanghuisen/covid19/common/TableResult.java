package cn.yanghuisen.covid19.common;

import cn.yanghuisen.covid19.enums.TableResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableResult<T> {
    public Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    /**
     * success
     * @param count count
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> success(long count,List<T> data){
        return new TableResult<T>(TableResultEnum.SUCCESS.getCode(),TableResultEnum.SUCCESS.getMsg(),count,data);
    }

    /**
     * success
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> success(String msg,long count,List<T> data){
        return new TableResult<>(TableResultEnum.SUCCESS.getCode(),msg,count,data);
    }

    /**
     * fail
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> fail(long count,List<T> data){
        return new TableResult<>(TableResultEnum.FAIL.getCode(), TableResultEnum.FAIL.getMsg(),count, data);
    }

    /**
     * fail
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> fail(String msg,long count,List<T> data){
        return new TableResult<>(TableResultEnum.FAIL.getCode(),msg,count,data);
    }

    /**
     * warning
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> warning(long count,List<T> data){
        return new TableResult<>(TableResultEnum.WARNING.getCode(), TableResultEnum.WARNING.getMsg(), count,data);
    }

    /**
     * warning
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> warning(String msg,long count,List<T> data){
        return new TableResult<>(TableResultEnum.WARNING.getCode(),msg,count,data);
    }

    /**
     * error
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> error(long count,List<T> data){
        return new TableResult<>(TableResultEnum.ERROR.getCode(), TableResultEnum.ERROR.getMsg(),count, data);
    }

    /**
     * error
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return TableResult
     */
    public static <T> TableResult<T> error(String msg,long count,List<T> data){
        return new TableResult<>(TableResultEnum.ERROR.getCode(),msg,count, data);
    }
}
