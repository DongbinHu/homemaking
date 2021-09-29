package com.company.homemaking.common.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @Author 胡东斌
 * @Date: 2020-04-02
 * @Description: 自定义响应数据结构
 *                 200：表示成功
 *                 500：表示错误，错误信息在msg字段中
 *                 402：未登录活登录超时
 *                 502：拦截器拦截到用户token出错
 *                 555：异常抛出信息
 **/
public class JSONResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private Boolean ok;    // 不使用

    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }

    public static JSONResult build(Integer status, String msg) {
        return new JSONResult(status, msg, null);
    }

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    public static JSONResult errorLoginMsg(String msg) {

        return new JSONResult(402, msg, null);
    }
    public static JSONResult errorAuthMsg(String msg) {
        return new JSONResult(403, msg, null);
    }

    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(502, msg, null);

    }
    public static JSONResult errorMobileMsg() {
        return new JSONResult(503, "绑定手机号", null);
    }

    public static JSONResult errorGPSMsg() {
        return new JSONResult(504, "获取位置", null);
    }

    public static JSONResult errorIdAuthMsg() {
        return new JSONResult(505, "完成个人信息认证", null);
    }

    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    public JSONResult() {

    }

    public JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = this.isOK();
    }

    public JSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.ok = this.isOK();
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

}