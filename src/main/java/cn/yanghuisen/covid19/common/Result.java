package cn.yanghuisen.covid19.common;

import cn.yanghuisen.covid19.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 啥也不会的程序员
 * @date 2022/3/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{

    public Integer code;
    private String msg;
    private T data;

    /**
     * success
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> success(T data){
        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),data);
    }

    /**
     * success
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> success(String msg,T data){
        return new Result<>(ResultEnum.SUCCESS.getCode(),msg,data);
    }

    /**
     * fail
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> fail(T data){
        return new Result<>(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), data);
    }

    /**
     * fail
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> fail(String msg,T data){
        return new Result<>(ResultEnum.FAIL.getCode(),msg,data);
    }

    /**
     * warning
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> warning(T data){
        return new Result<>(ResultEnum.WARNING.getCode(), ResultEnum.WARNING.getMsg(), data);
    }

    /**
     * warning
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> warning(String msg,T data){
        return new Result<>(ResultEnum.WARNING.getCode(),msg,data);
    }

    /**
     * error
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> error(T data){
        return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), data);
    }

    /**
     * error
     * @param msg msg
     * @param data data
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> error(String msg,T data){
        return new Result<>(ResultEnum.ERROR.getCode(),msg,data);
    }
}
