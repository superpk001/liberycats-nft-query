/**
 * Copyright 2024 jb51.net
 */
package com.libertycats.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;

/**
 * Auto-generated: 2025-01-07 15:33:48
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkxDataItem {

    private int amount;
    private String chain;
    @JsonProperty("collectionAddress")
    private String collectionaddress;
    @JsonProperty("currencyAddress")
    private String currencyaddress;
    private String from;
    private String platform;
    private BigDecimal price;
    private int timestamp;
    private String to;
    @JsonProperty("tokenId")
    private String tokenid;
    @JsonProperty("txHash")
    private String txhash;
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }
    public String getChain() {
        return chain;
    }

    public void setCollectionaddress(String collectionaddress) {
        this.collectionaddress = collectionaddress;
    }
    public String getCollectionaddress() {
        return collectionaddress;
    }

    public void setCurrencyaddress(String currencyaddress) {
        this.currencyaddress = currencyaddress;
    }
    public String getCurrencyaddress() {
        return currencyaddress;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getPlatform() {
        return platform;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public int getTimestamp() {
        return timestamp;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getTo() {
        return to;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
    public String getTokenid() {
        return tokenid;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }
    public String getTxhash() {
        return txhash;
    }

}