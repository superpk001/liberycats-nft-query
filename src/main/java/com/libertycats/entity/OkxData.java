package com.libertycats.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Auto-generated: 2025-01-07 15:33:48
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkxData {

    private String cursor;
    private List<OkxDataItem> data;
    private boolean next;
    private int total;

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public List<OkxDataItem> getData() {
        return data;
    }

    public void setData(List<OkxDataItem> data) {
        this.data = data;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}