package com.libertycats.util;

import com.libertycats.common.Constants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/19 18:08
 **/
public class MyFileUtil {

    /**
     * 由于数据量太大，每 100 0条数据存到一个目录里，方便整理区分
     * @param tradeSavePath
     * @param catId
     * @return
     */
    public static String formatName(String tradeSavePath, String platformType, Integer catId) throws Exception {

        // 每 1000 条分配到一个目录
        int folderNumber = catId / 1000;

        if("OKX".equalsIgnoreCase(platformType)) {
            platformType = "OKX";
        } else if("CHAINBASE".equalsIgnoreCase(platformType)) {
            platformType = "CHAINBASE";
        } else {
            throw new Exception("Error platformType");
        }

        // 返回格式 /etc/libertycat/202408/10/CAT10000.json
        return tradeSavePath + MyDateUtil.getYearMothStr() + "/" + folderNumber + "/" + platformType + String.format("%05d", catId) + ".json";
    }

    /**
     * 保存没有交易过的原始猫地址，分为两种类型<br/>
     * 第一种是没整理直接保存的原始数据<br/>
     * 第二种是根据第一种进行统计分析后的数据<br/>
     *
     * @param ownerAddressList
     * @param type Original/ Combine
     * @throws Exception
     */
    public static void saveNeverTradeAddress(List<String> ownerAddressList, String type) throws Exception {

        String ownerAddressListStr = String.join("\r\n", ownerAddressList);

        String bugisDiamondHandStr = "";
        // 加上是否买入算砖石手
        if("Y".equalsIgnoreCase(Constants.bugisDiamondHand)) {
            bugisDiamondHandStr = "买入也算砖石手";
        } else if("N".equalsIgnoreCase(Constants.bugisDiamondHand)) {
            bugisDiamondHandStr = "买入不是砖石手";
        }

        // 把原始数据保存起来
        FileUtils.write(
                new File(Constants.tradeSavePath +
                        MyDateUtil.getYearMothStr() + "/" +
                        MyDateUtil.getNowDateStr() + "_" +
                        bugisDiamondHandStr + "_" +
                        type.toLowerCase() + ".txt"),
                ownerAddressListStr, "UTF-8", false);

    }


    /**
     * 保存 猫猫交易信息 到本地
     * @param tradeJsonInfo
     * @param catId
     */
    public static void saveCatTradeInfo(String tradeJsonInfo, String platformType, Integer catId) throws Exception {

        // CAT00001.txt
        File file = new File(formatName(Constants.tradeSavePath, platformType, catId));

        // 覆盖模式
        FileUtils.write(file, tradeJsonInfo, "UTF-8", false);

    }
}
