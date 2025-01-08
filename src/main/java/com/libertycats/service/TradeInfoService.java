package com.libertycats.service;

import com.google.gson.Gson;
import com.libertycats.common.Constants;
import com.libertycats.entity.*;
import com.libertycats.util.MyDateUtil;
import com.libertycats.util.MyFileUtil;
import com.libertycats.util.OkxUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.File;
import java.util.*;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/19 14:38
 **/
public class TradeInfoService {

    /**
     * 通过 猫猫 id，获取这个猫猫在 Chainbase 所有交易信息
     * 猫猫的 id, 总共 10000 个
     * @param catId
     * @return
     */
    public static String getChainbaseCatTradeInfoOnline(Integer catId) throws Exception {

        String baseUrl = "https://api.chainbase.online/v1/nft/owner/history" +
                "?contract_address=" + Constants.contractAddress +
                "&chain_id=" + Constants.chainId +
                "&token_id=" + catId;

        System.out.println(new Date() + " == begin Chainbase url == " + baseUrl);

        HttpResponse<String> response =
                Unirest.get(baseUrl)
                        .header("x-api-key", Constants.chainbaseApiKey)
                        .asString();

        System.out.println(new Date() + " == after Chainbase url == ");

        return response.getBody();
    }

    /**
     * 通过 猫猫 id，获取这个猫猫在 OKX 所有交易信息
     * 猫猫的 id, 总共 10000 个
     * @param catId
     * @return
     */
    public static String getOkxCatTradeInfoOnline(Integer catId) throws Exception {

        //代理
        System.setProperty("https.proxyHost", Constants.proxyHost);
        System.setProperty("https.proxyPort", Constants.proxyPort);

        //请求参数
        String queryString = "chain=" + Constants.chain
               + "&collectionAddress=" + Constants.contractAddress
               + "&tokenId=" + catId;

        String subUrl = "mktplace/nft/markets/trades";

        System.out.println(new Date() + " == begin Okx url == " + queryString);

        return OkxUtil.get(subUrl, queryString);
    }


    /**
     * 如果 本地已经有猫猫交易信息，则直接返回 本地的猫猫交易资料
     * @param catId
     * @return
     * @throws Exception
     */
    public static String getCatTradeInfo(String platformType, Integer catId) throws Exception {

        // CAT00001.txt
        File file = new File(MyFileUtil.formatName(Constants.tradeSavePath, platformType, catId));

        String jsonInfo = null;

        if(file.exists()) {
            jsonInfo = FileUtils.readFileToString(file, "UTF-8");
        }

        if(StringUtils.isBlank(jsonInfo)) {
            return null;
        }

        System.out.println(new Date() + " == get cat info from " + platformType + " and catId = " + catId);

        return jsonInfo;
    }

    /**
     * 此方法只针对 Chainbase 以及 OKX
     * 遍历所有 json 文件，查看是否出现以下情况，如果出现则认为错误 json, 需要重新下载
     * Chainbase 因为请求太多太快导致报错, 1秒最多 2请求
     * OKX 检查返回的 message 是否有值, 返回的 code 是否为 0
     *
     * {"message":"Too many requests. The proper API rate-limit you could refer to https:\/\/chainbase.com\/pricing Please wait and retry after a certain period of time.","code":429}
     * @return
     * @throws Exception
     */
    public static void deleteErrorFile() throws Exception {

        for (int i = 1; i <= 10000; i++) {

            System.out.println(new Date() + " == 开始校验文件内容 == " + i);

            File chainbaseFile = new File(MyFileUtil.formatName(Constants.tradeSavePath, "Chainbase", i));

            if (chainbaseFile.exists()) {

                String jsonInfo = FileUtils.readFileToString(chainbaseFile, "UTF-8");

                // 第一轮判断
                if (jsonInfo.contains("Too many requests")) {
                    System.out.println(new Date() + " == delete1 chainbase file == " + chainbaseFile.getAbsolutePath());
                    FileUtils.delete(chainbaseFile);
                    continue;
                }

                // 第二轮判断
                // 使用 Gson 将 JSON 字符串转换为对象
                Gson gson = new Gson();
                ChainbaseTradeInfo chainbaseTradeInfo = gson.fromJson(jsonInfo, ChainbaseTradeInfo.class);

                // 如果返回的信息没有 OK, 则表示有问题
                if(!"OK".equalsIgnoreCase(chainbaseTradeInfo.getMessage())) {
                    System.out.println(new Date() + " == delete2 chainbase file == " + chainbaseFile.getAbsolutePath());
                    FileUtils.delete(chainbaseFile);
                }

            }

            File okxFile = new File(MyFileUtil.formatName(Constants.tradeSavePath, "OKX", i));

            if (okxFile.exists()) {

                String jsonInfo = FileUtils.readFileToString(okxFile, "UTF-8");

                // 使用 Gson 将 JSON 字符串转换为对象
                Gson gson = new Gson();
                OkxTradeInfo okxTradeInfo = gson.fromJson(jsonInfo, OkxTradeInfo.class);

                // 如果返回的 message 有内容, 则表示有问题
                // 如果返回的 code 不是 0, 则表示有问题
                if(StringUtils.isNotBlank(okxTradeInfo.getMsg()) || okxTradeInfo.getCode() != 0) {
                    System.out.println(new Date() + " == delete okx file == " + okxFile.getAbsolutePath());
                    //FileUtils.delete(okxFile);
                }

            }
        }
    }

    /**
     * 获取所有 猫猫NFT 的交易数据，保存到本地
     * @throws Exception
     */
    public static void downloadAllTradeInfo() throws Exception {

        // 为了防止请求太快导致服务器拒绝访问，需要减慢访问速度
        Boolean needSleep = false;

        for(int i = 1; i <= 10000; i++) {

            // 获取本地数据
            String jsonString = TradeInfoService.getCatTradeInfo("OKX", i);

            // 如果本地没有则获取网上数据, 并保存
            if(StringUtils.isBlank(jsonString)) {

                jsonString = TradeInfoService.getOkxCatTradeInfoOnline(i);
                MyFileUtil.saveCatTradeInfo(jsonString, "OKX", i);

                needSleep = true;
            }

            // 获取本地数据
            jsonString = TradeInfoService.getCatTradeInfo("CHAINBASE", i);

            // 如果本地没有则获取网上数据, 并保存
            if(StringUtils.isBlank(jsonString)) {

                jsonString = TradeInfoService.getOkxCatTradeInfoOnline(i);
                MyFileUtil.saveCatTradeInfo(jsonString, "OKX", i);

                jsonString = TradeInfoService.getChainbaseCatTradeInfoOnline(i);
                MyFileUtil.saveCatTradeInfo(jsonString, "CHAINBASE", i);

                needSleep = true;
            }

            if(needSleep) {
                // 为了防止请求太快导致服务器拒绝访问，需要减慢访问速度
                Thread.sleep(600);
            }
        }

    }


    /**
     * 判断是不是原始猫<br/>
     * <br/>
     * 这个方法执行的前提是，所有猫猫NFT的交易数据已经保存到本地<br/>
     * 判断依据，okx 的 json 文件不会保存转移信息，只会保存交易信息，所以json里的 from address ,to address 有可能对不上，参考2号猫<br/>
     * 而除此之前，赚哥在白皮书发布之前也有一些是卖给小伙伴的，如果小伙伴在买了赚哥的猫后面没交易的，那些也认为是砖石手<br/>
     * 由于 okx json 是不知道持有者是谁，因此需要 CHAINBASE json 来知道持有者地址
     * <br/>
     * @return
     * @throws Exception
     */
    public static List<String> getNeverTradeOwnerAddress() throws Exception {

        // 记录有原始猫的 Eth 地址
        List<String> addressList = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {

            // 获取 ChainBase 数据
            String jsonString = TradeInfoService.getCatTradeInfo("CHAINBASE", i);

            // 使用 Gson 将 JSON 字符串转换为对象
            Gson gson = new Gson();
            ChainbaseTradeInfo chainbaseTradeInfo = gson.fromJson(jsonString, ChainbaseTradeInfo.class);

            List<ChainbaseTradeItem> chainbaseTradeItemList = chainbaseTradeInfo.getData();

            // 剔除 赚哥还没上链的猫猫NFT
            if(chainbaseTradeItemList == null || chainbaseTradeItemList.size() == 0) {
                continue;
            }

            // 获取 Okx 数据
            jsonString = TradeInfoService.getCatTradeInfo("OKX", i);

            // 使用 Gson 将 JSON 字符串转换为对象
            OkxTradeInfo okxTradeInfo = gson.fromJson(jsonString, OkxTradeInfo.class);

            List<OkxDataItem> okxDataItemList = okxTradeInfo.getData().getData();

            // 如果在 okx 没有交易记录，表示是钻石手
            // 如果在 okx 有交易记录，那么最后一次交易的 from address 必须是 赚哥的地址
            if(okxDataItemList.size() > 0) {

                // 如果不承认买入是砖石手，那么 okx 有交易就算作废
                if("N".equalsIgnoreCase(Constants.bugisDiamondHand)
                    || "NO".equalsIgnoreCase(Constants.bugisDiamondHand)) {
                    continue;
                }

                // 获取 Okx 最新的交易数据
                Optional<OkxDataItem> maxOkxTradeItem = okxDataItemList.stream().max((t1, t2) -> {
                    return Long.compare(
                            t1.getTimestamp(),
                            t2.getTimestamp()
                    );
                });

                if(!Arrays.stream(Constants.zhuanEthAddress).anyMatch(maxOkxTradeItem.get().getFrom()::equals)) {
                    continue;
                }

            }

            // 获取 Chainbase 最新的交易数据，以交易数据为判断
            Optional<ChainbaseTradeItem> maxChainbaseTradeItem = chainbaseTradeItemList.stream().max((t1, t2) -> {
                return Long.compare(
                        t1.getBlock_timestamp().getTime(),
                        t2.getBlock_timestamp().getTime()
                );
            });

            // 拿最后一次的交易，如果是赚哥发送出去的，则判断为原始猫
            if (maxChainbaseTradeItem.isPresent() && maxChainbaseTradeItem.get() != null) {
                // 赚哥自己倒手的不计算在内
                if(!Arrays.stream(Constants.zhuanEthAddress).anyMatch(maxChainbaseTradeItem.get().getTo_address()::equals)) {
                    // 白皮书诞生日之后在赚哥账号转出的都不作为原始猫
                    if(MyDateUtil.getWhitePaperDate().getTime() >= maxChainbaseTradeItem.get().getBlock_timestamp().getTime()) {
                        addressList.add(maxChainbaseTradeItem.get().getTo_address());
                    }

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
