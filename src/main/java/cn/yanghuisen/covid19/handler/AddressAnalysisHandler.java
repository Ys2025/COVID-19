package cn.yanghuisen.covid19.handler;

import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 * 居住地址解析
 */
public interface AddressAnalysisHandler {
    /**
     * 解析
     * @param params params
     * @throws Exception Exception
     */
    void analysis(Map<String,Object> params) throws Exception;
}
