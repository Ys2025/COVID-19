package cn.yanghuisen.covid19.enums;

/**
 * @author 啥也不会的程序员
 * @date 2022/3/26
 */
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200,"SUCCESS"),
    /**
     * 失败
     */
    FAIL(300,"FAIL"),
    /**
     * 警告
     */
    WARNING(400,"WARNING"),
    /**
     * 错误
     */
    ERROR(500,"ERROR");

    /**
     * code
     */
    private Integer code;

    /**
     * msg
     */
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
