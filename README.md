nForumSDK
=========

### 基本说明

本项目是基于[nForum API][1]编写的的SDK，用于开发Java以及Android应用。经测试适用于[北邮人论坛][2]和[天地人大][3]。

+ [测试站点][9]部署有最新的nForum API版本，在取得appkey前可用此站点进行测试；
+ [虎踞龙盘][4]虽然使用了nForum的Web页面，但是并未使用nForumAPI，不适用本SDK；
+ [水木社区][5]的API经过较大程度的定制，可能某些功能并不适用；
+ 根据站点的设置不同，某些功能可能不可用，比如[北邮人论坛][2]使用API发文时无法显示签名档；

### 快速上手

使用本SDK，你需要创建一个nForumSDK对象，然后get到不同的Service，例如：

```java
NForumSDK nSDK = new NForumSDK(Host.HOST_BYR_TEST, "username", "password");
BoardService boardService = nSDK.getBoardService();
```

然后你就可以使用这个Service提供的方法了：

```java
Board board = boardService.getBoard("SL", new ParamOption());
LogUtils.d("Board name", board.name);
```

请注意，天地人大和nForum的测试站点不需要appkey，但是北邮人论坛是需要appkey的，此时需要使用可以传入appkey的构造函数创建nForumSDK对象：

```java
NForumSDK nSDK = new NForumSDK(Host.HOST_BYR, "yourAppkey", "username", "password");
```

可参考 [GetDemo][7]

### 数据结构

本SDK将API返回的元数据封装成了类，但是由于API不同的接口返回的元数据并不完全一致（例如`/board/:name`返回的文章元数据并没有像`/artilce/:board/:id`返回的文章元数据一样包含content字段），请在调用时检查属性是否为null。数据结构中还有一些易忽略的bug：

+ `/section`返回的「section_count」、「section」字段和`/section/:name`返回的「sub_section」、「board」字段均被整合进了Section类，使用时注意区分。
+ Article类和`/user/query/id`返回的user元数据当该用户不存在时（或已注销）仅返回一个String，此时User类中除id字段其他字段均为null，使用前请检查。

### 参数使用

使用Service调用接口时，如果该接口有可选参数供调用，需要传入一个ParamOption类，如：

```java
public Board getBoard(String name, ParamOption params) {...}
```

如果你不需要使用参数（使用默认参数），传入一个空的ParamOption对象即可，下面的例子中使用了默认为20的count参数和默认为1的page：

```java
Board board = boardService.getBoard("SL", new ParamOption());
```

如果你需要自定义参数，调用ParamOption类的`addParam`方法添加参数即可，该方法被设计成返回自身以供链式调用，下面的例子中自定义count参数为5，page参数为2：

```java
ParamOption params = new ParamOption()
                    .addParams("count", "5")
                    .addParams("page", "2");
Board board = boardService.getBoard("SL", params);
```

可参考 [ParamDemo][8]

### 错误提示

当遇到论坛的内部错误时（例如帖子已被删除、已经投票后再次投票等），API会返回一个包含错误信息的JSON，会作为NForumException抛出，例如：

```java
com.dss886.nForumSDK.http.NForumException: 你已经投过票了
at com.dss886.nForumSDK.http.PostMethod.postJSON(PostMethod.java:83)
at com.dss886.nForumSDK.service.VoteService.vote(VoteService.java:91)
at com.dss886.nForumSDKDemo.Main.main(Main.java:26)
```

### 上传文件

//TODO

### 关于本项目

本项目是由[dss886][6]发起的开源项目，欢迎Fork及发起Pull Request。

如有任何问题，请给我发送邮件dss886@gmail.com，或在北邮人论坛上给我发送站内信：dss886。

[1]:https://github.com/xw2423/nForum/wiki/nForum-API
[2]:http://bbs.byr.cn/
[3]:http://www.tdrd.org/
[4]:http://bbs.seu.edu.cn/
[5]:http://www.newsmth.net/
[6]:http://www.dss886.com/
[7]:https://github.com/dss886/nForumSDK/blob/master/src/com/dss886/nForumSDKDemo/GetDemo.java
[8]:https://github.com/dss886/nForumSDK/blob/master/src/com/dss886/nForumSDKDemo/ParamDemo.java
[9]:http://nforum.byr.edu.cn/byr/#!default
