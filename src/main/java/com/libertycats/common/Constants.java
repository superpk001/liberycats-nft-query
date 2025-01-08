package com.libertycats.common;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/16 14:39
 **/
public class Constants {

    // 交易信息保存位置
    public static final String tradeSavePath = "/etc/libertycat/";

    // 猫猫合约地址
    public static final String contractAddress = "0x0030f47d6a73bc518cf18fe027ea91dd6b2b6003";

	// chainbase conf
    public static final String chainbaseApiKey = "xxxxxxxx";

    // okx conf
    public static final String okxSecretKey = "xxxxxxxx";
    public static final String okxApiKey = "xxxxxxxx";
    public static final String okxApiPassword = "xxxxxxxx";

    // White Paper Date(白皮书之日)
    public static final String whitePaperDate = "2024-08-24";

    // 参考 https://docs.chainbase.com/platform/features/api/web3-api
    // Polygon 对应 137
    public static final String chainId = "137";

    public static final String chain = "polygon";

    public static final String proxyHost = "127.0.0.1";
    public static final String proxyPort = "3333333";

    /**
     * 是否承认赚哥交易的属于砖石手
     * Y-在赚哥手中买入后不交易也是砖石手
     * N-在赚哥手中买入的不属于砖石手
     */
    public static final String bugisDiamondHand = "Y";

    public static final String[] zhuanEthAddress = {
            "0x09425fa05b234e5263c01d51410482433c746698",
            "0xaaa2029cdec42a697f808a241a8b80ca56e2b973",
            "0x56a9d5b887f5ab9480c3b5c285866ccbf10784cf",
            "0x52a3afde4d009465893d53e40739db4e9c52f3bf",
            "0x474ee8a5d05657a782a56dedd541824fa49304c4"
    };

}
