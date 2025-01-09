# libertycats-nft-query
### What is Liberty Cats
Liberty Cats live life in their own favorite ways.

### A statement(声明)
This project is **unofficial**（这个项目是**非官方**的）

### How to join us
Social media
Website
https://www.libertycatsnfts.com/home

white-paper
https://libertycatsnfts.gitbook.io/white-paper

X
https://x.com/LibertyCatNFT

Ins
https://www.instagram.com/liberty.cats/

Discord
https://discord.gg/wPqMzFEv

Rarity ranking
https://raritysniper.com/libertycats

### 前言
群里一直有人说 **原始猫** 难统计，因此我就像做这个查询功能出来给大家参考，毕竟我也是原始猫持有者。（**一建三连，建设建设**）

能力有限，代码写得可能有点臃肿，**如果发现有bug，麻烦告知我一声**。代码我都加了很多注释，方便阅读。

### 为方便大家阅读
我把最后结果的文档复制到 结果.txt

### 原理
首先我们确认了赚哥的5个账号("0x09425fa05b234e5263c01d51410482433c746698","0xaaa2029cdec42a697f808a241a8b80ca56e2b973","0x56a9d5b887f5ab9480c3b5c285866ccbf10784cf","0x52a3afde4d009465893d53e40739db4e9c52f3bf","0x474ee8a5d05657a782a56dedd541824fa49304c4")。

其次，我们到 okx 开发者平台以及 chainbase 平台 拿到所有的猫猫交易数据，但是不知道是转移还是买卖的。

chainbase 的数据不会区分是转移数据还是交易数据，全部都会提供给你。

okx 的数据只会记录交易数据，不会记录转移数据。

因此我们需要结合两个平台的数据进行分析：

我只需要遍历1万张猫猫NFT，看看每一张猫猫NFT的交易情况。

1. 第一种情况是 chainbase 没有交易，那就是这猫猫NFT还在赚哥手里

2. 如果在 okx 没有交易记录，表示是钻石手，在 chainbase 数据里面找最后一位交易的接收地址，**记录下来**

3. 如果在 okx 有交易记录，并且最后一次交易的接收地址是赚哥的地址，赚哥自己倒手的不计算在内

3. 如果在 okx 有交易记录，并且最后一次交易的发送地址是赚哥的地址，**记录下来**

因为**2024/08/24**是白皮书发布之日，因此白皮书诞生日之后在赚哥账号转出的都不作为原始猫。

### 如何运行代码

1. 到 okx 注册 https://www.okx.com/zh-hant/web3/build/docs/waas/okx-waas-what-is-waas
   获得 okxSecretKey，okxApiKey，okxApiPassword

2. 到 https://console.chainbase.com/login 注册获取 chainbaseApiKey

3. 把上面的 okxSecretKey, okxApiKey, okxApiPassword, chainbaseApiKey 覆盖到 com.libertycats.common.Constants

2. 执行 com.libertycats.NeverTradeOwner.main 即可

### 如何查看数据
1. 通过程序运行的数据会储存到 /etc/libertycat/，你也可以通过修改 Constants.tradeSavePath 更改目录位置

2. 对于不懂程序的赚友，我直接把数据放到 resources 目录下面，按月份整理，每个月可能会更新一次

3. xxxxxxxxxxxx_原始数据.txt 文件里面有所有的原始猫NFT持有者的ETH地址，有重复数据代表这个持有者有多个原始猫NFT

4. xxxxxxxxxxxx_整合数据.txt 文件里面有所有的原始猫NFT持有者的ETH地址&持有原始猫的数量

### 解释
1. 买入不是砖石手/买入也算砖石手

因为赚哥偶尔会卖出一些NFT，那么对于这类型的NFT并不是空投所得，是否属于钻石手有一定争议，因此我弄成了两份数据，一份数据是认为在白皮书发布之前在赚哥手里买入的NFT是砖石手，另一份就表示不是。