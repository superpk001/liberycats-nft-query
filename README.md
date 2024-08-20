# libertycats-nft-query
### 前言
这个是因为群里一直有人说 原始猫 难统计，因为我就像做这个查询功能出来给大家参考，毕竟我也是原始猫持有者。
能力有限，代码写得可能有点臃肿。代码我都加了很多注释，方便阅读。

### 原理
首先我们确认了赚哥的4个账号("0x09425fa05b234e5263c01d51410482433c746698","0xaaa2029cdec42a697f808a241a8b80ca56e2b973","0x56a9d5b887f5ab9480c3b5c285866ccbf10784cf","0x52a3afde4d009465893d53e40739db4e9c52f3bf")。
我只需要遍历1万张猫猫NFT，看看每一张猫猫NFT的交易情况。
如果该NFT最后一次交易是赚哥的地址作为交易发送者，则判断这只猫猫NFT是一只原始猫；
如果最后一次交易的发送者地址不是赚哥的地址，则这只猫猫NFT不是原始猫

### 如何运行代码
1. 需要到 com.libertycats.common.Constants 修改你的 apiKey
apikye 需要到 https://console.chainbase.com/login 注册获取
2. 执行 com.libertycats.NeverTradeOwner.main 即可