package com.libertycats.service;

import com.google.gson.Gson;
import com.libertycats.common.Constants;
import com.libertycats.entity.TradeInfo;
import com.libertycats.entity.TradeItem;
import com.libertycats.util.MyFileUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/19 14:38
 **/
public class TradeInfoService {

    /**
     * 通过 猫猫 id，获取这个猫猫的所有交易信息
     * 猫猫的 id, 总共 10000 个
     * @param catId
     * @return
     */
    public static String getCatTradeInfoOnline(Integer catId) throws Exception {

        String baseUrl = "https://api.chainbase.online/v1/nft/owner/history" +
                "?contract_address=" + Constants.contractAddress +
                "&chain_id=" + Constants.chainId +
                "&token_id=" + catId;

        System.out.println(new Date() + " == begin url == " + baseUrl);

        HttpResponse<String> response =
                Unirest.get(baseUrl)
                        .header("x-api-key", Constants.apiKey)
                        .asString();

        System.out.println(new Date() + " == after url == ");

        return response.getBody();
    }


    /**
     * 如果 本地已经有猫猫交易信息，则直接返回 本地的猫猫交易资料
     * @param catId
     * @return
     * @throws Exception
     */
    public static String getCatTradeInfo(Integer catId) throws Exception {

        // CAT00001.txt
        File file = new File(MyFileUtil.formatName(Constants.tradeSavePath, catId));

        String jsonInfo = null;

        if(file.exists()) {
            jsonInfo = FileUtils.readFileToString(file, "UTF-8");
        }

        if(StringUtils.isBlank(jsonInfo)) {
            return null;
        }

        System.out.println(new Date() + " == get cat info from local == " + catId);

        return jsonInfo;
    }

    /**
     * 遍历所有 json 文件，查看是否有些因为请求太多太快导致报错, 1秒最多 2请求
     *
     * {"message":"Too many requests. The proper API rate-limit you could refer to https:\/\/chainbase.com\/pricing Please wait and retry after a certain period of time.","code":429}
     * @return
     * @throws Exception
     */
    public static void delErrorTradeInfo() throws Exception {

        for (int i = 1; i <= 10000; i++) {

            File file = new File(MyFileUtil.formatName(Constants.tradeSavePath, i));

            if (file.exists()) {

                String jsonInfo = FileUtils.readFileToString(file, "UTF-8");

                // 第一轮判断
                if (jsonInfo.contains("Too many requests")) {
                    System.out.println(new Date() + " == delete file == " + file.getAbsolutePath());
                    FileUtils.delete(file);
                }

                // 第二轮判断
                // 使用 Gson 将 JSON 字符串转换为对象
                Gson gson = new Gson();
                TradeInfo tradeInfo = gson.fromJson(jsonInfo, TradeInfo.class);

                // 如果返回的信息没有 OK, 则表示有问题
                if(!"OK".equalsIgnoreCase(tradeInfo.getMessage())) {
                    System.out.println(new Date() + " == delete file == " + file.getAbsolutePath());
                    FileUtils.delete(file);
                }

            }
        }
    }

    /**
     * 获取所有 猫猫NFT 的交易数据，保存到本地
     * @throws Exception
     */
    public static void getAllTradeInfo() throws Exception {

        for(int i = 1; i <= 10000; i++) {

            // 获取本地数据
            String jsonString = TradeInfoService.getCatTradeInfo(i);

            // 如果本地没有则获取网上数据, 并保存
            if(StringUtils.isBlank(jsonString)) {

                jsonString = TradeInfoService.getCatTradeInfoOnline(i);
                MyFileUtil.saveCatTradeInfo(jsonString, i);

                // 为了防止请求太快导致服务器拒绝访问，需要减慢访问速度
                Thread.sleep(600);
            }
        }

    }

    /**
     * 判断是不是原始猫<br/>
     * 这个方法执行的前提是，所有猫猫NFT的交易数据已经保存到本地<br/>
     * @return
     * @throws Exception
     */
    public static List<String> getNeverTradeOwnerAddress() throws Exception {

        // 记录有原始猫的 Eth 地址
        List<String> addressList = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {

            // 获取本地数据
            String jsonString = TradeInfoService.getCatTradeInfo(i);

            // 使用 Gson 将 JSON 字符串转换为对象
            Gson gson = new Gson();
            TradeInfo tradeInfo = gson.fromJson(jsonString, TradeInfo.class);

            List<TradeItem> tradeItemList = tradeInfo.getData();

            // 剔除 赚哥还没上链的猫猫NFT
            if(tradeItemList == null || tradeItemList.size() == 0) {
                continue;
            }

            // 获取最新的交易数据，以交易数据为判断
            Optional<TradeItem> maxTradeItem = tradeItemList.stream().max((t1, t2) -> {
                return Long.compare(
                        t1.getBlock_timestamp().getTime(),
                        t2.getBlock_timestamp().getTime()
                );
            });

            // 拿最后一次的交易，如果是赚哥发送出去的，则判断为原始猫
            if (maxTradeItem.isPresent() && maxTradeItem.get() != null) {
                // 由于赚哥有几个账号，from address 只要匹配其中一个就可以
                if (Arrays.stream(Constants.zhuanEthAddress).anyMatch(maxTradeItem.get().getFrom_address()::equals)) {
                    addressList.add(maxTradeItem.get().getTo_address());
                }
            }

        }

        return addressList;
    }


    /**
     * 对于持有者数据进行统计整理<br/>
     * 整理后的结果为 持有原始猫NFT用户的交易地址以及持有原始猫数量<br/>
     * @param orinalList
     * @return
     * @throws Exception
     */
    public static List<String> tradeDataSorting(List<String> orinalList) throws Exception {

        // 用 map 统计持有者持有 原始猫NFT 的数量
        HashMap<String, Integer> map = new HashMap<>();

        // 遍历 List, 并且放到 map 里面统计数据
        for(String address: orinalList) {
            //如果 HashMap 中已经存在相同的键
            if(map.containsKey(address)) {
                // 值加 1
                map.put(address, map.get(address) + 1);
            } else {
                // 否则将元素作为键插入到 HashMap
                map.put(address, 1);
            }
        }

        // 把 map 排序
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        // 整理 list 数据
        List<String> entryList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            entryList.add(entry.getKey() + "===" + entry.getValue());
        }

        return entryList;
    }

}
