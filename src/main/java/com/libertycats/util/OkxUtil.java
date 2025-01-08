package com.libertycats.util;

import jakarta.xml.bind.DatatypeConverter;
import com.libertycats.common.Constants;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2025-01-07 14:07
 **/
public class OkxUtil {

    /**
     *
     * @param subUrl after api/v5 后面那一段，开始不需要加斜杠
     * @param queryString queryString 不需要加问号
     * @throws Exception
     */
    public static String get(String subUrl, String queryString) throws Exception {

        // 将时间戳格式化为字符串
        String timestampStr = getTimeStamp();

        //timestamp + method + request_path
        String message = timestampStr + "GET" + "/api/v5/" + subUrl + "?" + queryString;

        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders(message, timestampStr));
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://www.okx.com/api/v5/" + subUrl + "?" + queryString, HttpMethod.GET,entity, String.class);

        return response.getBody();
    }

    public static String getTimeStamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));

        // 获取当前时间的时间戳
        long timestamp = System.currentTimeMillis();

        // 将时间戳格式化为字符串
        return sdf.format(new Date(timestamp));
    }

    public static HttpHeaders getHttpHeaders(String message, String timestampStr) throws Exception {

        //Secret Key
        SecretKey secretKey = new SecretKeySpec(Constants.okxSecretKey.getBytes(), "HmacSHA256");

        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        mac.init(secretKey);

        HttpHeaders headers = new HttpHeaders();

        //API Key
        headers.set("OK-ACCESS-KEY", Constants.okxApiKey);

        //Api 密码
        headers.set("OK-ACCESS-PASSPHRASE", Constants.okxApiPassword);

        //Sign
        headers.set("OK-ACCESS-SIGN", DatatypeConverter.printBase64Binary(mac.doFinal(message.getBytes())));

        //Timestamp
        headers.set("OK-ACCESS-TIMESTAMP", timestampStr);

        //POST要设置格式
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

}
