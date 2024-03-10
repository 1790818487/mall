package com.dawn.util;


import com.dawn.enums.login.ResponseEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 响应操作结果
 * <pre>
 *  {
 *      errno： 错误码，
 *      errmsg：错误消息，
 *      data：  响应数据
 *  }
 * </pre>
 *
 * <p>
 * 错误码：
 * <ul>
 * <li> 0，成功；
 * <li> 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
 * <ul>
 * <li> 401，参数错误，即前端没有传递后端需要的参数；
 * <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。
 * </ul>
 * <li> 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码：
 * <ul>
 * <li> 501，验证失败，即后端要求用户登录；
 * <li> 502，系统内部错误，即没有合适命名的后端内部错误；
 * <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
 * <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
 * <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 * </ul>
 * <li> 6xx，小商城后端业务错误码，
 * 具体见litemall-admin-api模块的AdminResponseCode。
 * <li> 7xx，管理后台后端业务错误码，
 * 具体见litemall-wx-api模块的WxResponseCode。
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseVO<T> {
    // 响应码
    private Integer code;

    // 描述信息
    private String message;

    // 响应内容
    private T data;

    private ResponseVO(ResponseEnum responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    private ResponseVO(ResponseEnum responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    private ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    /**
     * 返回自定义的成功信息
     * @param data      信息内容
     * @param <T>
     * @return
     */
    public static<T> ResponseVO<T> success(ResponseEnum responseEnum ,T data) {
        return new ResponseVO<>(responseEnum, data);
    }

    /**
     * 返回成功信息
     * @param data      信息内容
     * @param <T>
     * @return
     */
    public static<T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCCESS, data);
    }

    /**
     * 返回成功信息
     * @return
     */
    public static<T> ResponseVO<T> success() {
        return new ResponseVO<>(ResponseEnum.SUCCESS);
    }

    /**
     * 返回错误信息
     * @param responseCode      响应码
     * @return
     */
    public static<T> ResponseVO<T> error(ResponseEnum responseCode) {
        return new ResponseVO<>(responseCode);
    }

    /**
     * 返回自定义的错误信息
     * @param responseCode
     * @param <T>
     * @return
     */
    public static<T> ResponseVO<T> error(ResponseEnum responseCode,T data) {
        return new ResponseVO<>(responseCode,data);
    }
}

