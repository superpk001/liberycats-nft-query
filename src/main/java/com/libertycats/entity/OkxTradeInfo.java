/**
 * Copyright 2024 jb51.net
 */
package com.libertycats.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Auto-generated: 2025-01-07 15:33:48
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkxTradeInfo {

    private int code;
    private OkxData data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public OkxData getData() {
        return data;
    }

    public void setData(OkxData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}