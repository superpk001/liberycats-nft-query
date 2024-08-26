package com.libertycats;

import com.libertycats.service.TradeInfoService;
import com.libertycats.util.MyFileUtil;

import java.util.*;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/16 14:33
 **/
public class NeverTradeOwner {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 这两个方法需要配合使用，可能要循环两个回合
        getData();

        // 先执行完 getData, 再执行这里 saveData
        saveData();
    }

    /**
     * 获取猫猫交易数据
     * @throws Exception
     */
    private static void getData() throws Exception {

        // 1.获取猫猫交易数据
        TradeInfoService.getAllTradeInfo();

        // 2.删除错误数据
        TradeInfoService.delErrorTradeInfo();
    }

    /**
     * 保存数据
     * @throws Exception
     */
    private static void saveData() throws Exception {

        // 获取没交易过的原始猫用户地址
        List<String> neverTradeOwnerAddressList = TradeInfoService.getNeverTradeOwnerAddress();

        // 保存未经处理的原始猫的持有者
        MyFileUtil.saveNeverTradeAddress(neverTradeOwnerAddressList, "原始数据");

        // 整理 list 数据准备输出到文本
        List<String> entryList = TradeInfoService.tradeDataSorting(neverTradeOwnerAddressList);

        // 保存处理过的原始猫的持有者
        MyFileUtil.saveNeverTradeAddress(entryList, "整合数据");

    }

}
