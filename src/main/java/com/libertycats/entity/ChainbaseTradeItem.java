package com.libertycats.entity;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Auto-generated: 2024-08-16 16:45:7
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class ChainbaseTradeItem {

    @JsonProperty("block_number")
    private int block_number;
    @JsonProperty("block_timestamp")
    private Date block_timestamp;
    @JsonProperty("contract_address")
    private String contract_address;
    @JsonProperty("from_address")
    private String from_address;
    @JsonProperty("log_index")
    private int log_index;
    @JsonProperty("operator_address")
    private String operator_address;
    @JsonProperty("to_address")
    private String to_address;
    @JsonProperty("token_id")
    private String token_id;
    @JsonProperty("transaction_hash")
    private String transaction_hash;
    @JsonProperty("transaction_index")
    private int transaction_index;
    private String value;

    public int getBlock_number() {
        return block_number;
    }

    public void setBlock_number(int block_number) {
        this.block_number = block_number;
    }

    public Date getBlock_timestamp() {
        return block_timestamp;
    }

    public void setBlock_timestamp(Date block_timestamp) {
        this.block_timestamp = block_timestamp;
    }

    public String getContract_address() {
        return contract_address;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public int getLog_index() {
        return log_index;
    }

    public void setLog_index(int log_index) {
        this.log_index = log_index;
    }

    public String getOperator_address() {
        return operator_address;
    }

    public void setOperator_address(String operator_address) {
        this.operator_address = operator_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getTransaction_hash() {
        return transaction_hash;
    }

    public void setTransaction_hash(String transaction_hash) {
        this.transaction_hash = transaction_hash;
    }

    public int getTransaction_index() {
        return transaction_index;
    }

    public void setTransaction_index(int transaction_index) {
        this.transaction_index = transaction_index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}