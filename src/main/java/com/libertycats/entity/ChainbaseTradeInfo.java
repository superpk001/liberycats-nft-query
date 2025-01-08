package com.libertycats.entity;

import java.util.List;

/**
 * Auto-generated: 2024-08-16 16:45:7
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class ChainbaseTradeInfo {

    private int code;
    private String message;
    private List<ChainbaseTradeItem> data;
    private int count;
    private int next_page;

    public int getNext_page() {
        return next_page;
    }

    public void setNext_page(int next_page) {
        this.next_page = next_page;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(List<ChainbaseTradeItem> data) {
        this.data = data;
    }
    public List<ChainbaseTradeItem> getData() {
        return data;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

}
