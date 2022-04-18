package pers.jl.mioa.common.api;

/**
 * 封装API的错误代码
 *
 * @author: JL Du
 */

public interface IErrorCode {

    /**
     * 获取错误代码
     * @return 错误代码
     */
    long getCode();

    /**
     * 获取描述信息
     * @return 描述信息
     */
    String getMessage();

}
