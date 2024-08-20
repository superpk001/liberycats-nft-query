package com.libertycats.entity;

/**
 * 最终没有交易的用户信息
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/16 18:16
 **/
public class NeverOwner {

    private String ownerId;
    private Integer catsNumber;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getCatsNumber() {
        return catsNumber;
    }

    public void setCatsNumber(Integer catsNumber) {
        this.catsNumber = catsNumber;
    }
}
