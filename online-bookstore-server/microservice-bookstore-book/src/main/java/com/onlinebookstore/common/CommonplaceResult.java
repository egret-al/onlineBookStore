package com.onlinebookstore.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一返回的数据类型
 * 我们规定：code为0表示失败，code为1表示成功
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonplaceResult {

    private int code;
    private Object data;
    private String message;

    /**
     * 成功，没有数据有文本信息
     * @param message 文本信息
     * @return 约定协议
     */
    public static CommonplaceResult buildSuccessNoData(String message) {
        return buildSuccess(1, null, message);
    }

    /**
     * 成功，有数据和文本信息
     * @param data 数据
     * @param message 文本信息
     * @return 约定协议
     */
    public static CommonplaceResult buildSuccess(Object data, String message) {
        return buildSuccess(1, data, message);
    }

    /**
     * 成功，有数据无文本信息
     * @param data 数据
     * @return 约定协议
     */
    public static CommonplaceResult buildSuccessNoMessage(Object data) {
        return buildSuccess(1, data, null);
    }

    /**
     * 成功，有状态码、数据、文本信息
     * @param code 状态码，默认1表示成功
     * @param data 数据
     * @param message 文本信息
     * @return 约定协议
     */
    public static CommonplaceResult buildSuccess(int code, Object data, String message) {
        return new CommonplaceResult(code, data, message);
    }

    /**
     * 失败，有数据、有文本信息
     * @param data 数据
     * @param message 文本信息
     * @return 约定协议
     */
    public static CommonplaceResult buildError(Object data, String message) {
        return buildError(0, data, message);
    }

    /**
     * 失败，无数据，有文本信息
     * @param message 文本信息
     * @return 约定协议
     */
    public static CommonplaceResult buildErrorNoData(String message) {
        return buildError(0, null, message);
    }

    /**
     * 失败，有状态码、数据、文本信息
     * @param code 失败状态码，默认0表示失败
     * @param data 数据
     * @param message 文本信息
     * @return 约定协议
     */
    private static CommonplaceResult buildError(int code, Object data, String message) {
        return new CommonplaceResult(code, data, message);
    }
}
