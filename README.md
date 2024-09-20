# libertycats-nft-query
### What is Liberty Cats
Liberty Cats live life in their own favorite ways.

### A statement(声明)
This project is **unofficial**（这个项目是**非官方**的）

### How to join us
Social media
Website
https://www.libertycatsnfts.com/

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
首先我们确认了赚哥的4个账号("0x09425fa05b234e5263c01d51410482433c746698","0xaaa2029cdec42a697f808a241a8b80ca56e2b973","0x56a9d5b887f5ab9480c3b5c285866ccbf10784cf","0x52a3afde4d009465893d53e40739db4e9c52f3bf")。

我只需要遍历1万张猫猫NFT，看看每一张猫猫NFT的交易情况。

如果该NFT最后一次交易是赚哥的地址作为交易发送者，则判断这只猫猫NFT是一只原始猫；

如果最后一次交易的发送者地址不是赚哥的地址，则这只猫猫NFT不是原始猫。

因为**2024/08/24**是白皮书发布之日，因此白皮书诞生日之后在赚哥账号转出的都不作为原始猫。

### 如何运行代码
1. 需要到 com.libertycats.common.Constants 修改你的 apiKey，apikye 需要到 https://console.chainbase.com/login 注册获取

2. 执行 com.libertycats.NeverTradeOwner.main 即可

### 如何查看数据
1. 通过程序运行的数据会储存到 /etc/libertycat/，你也可以通过修改 Constants.tradeSavePath 更改目录位置

2. 对于不懂程序的赚友，我直接把数据放到 resources 目录下面，按月份整理，每个月可能会更新一次

3. xxxxxxxxxxxx_original.txt 文件里面有所有的原始猫NFT持有者的ETH地址，有重复数据代表这个持有者有多个原始猫NFT

4. xxxxxxxxxxxx_combine.txt 文件里面有所有的原始猫NFT持有者的ETH地址&持有原始猫的数量
