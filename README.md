# skyeye-report

#### 介绍
skyeye-report是一款高性能的Java报表引擎，提供完善的基于网页的报表设计器，可快速做出各种复杂的中式报表。支持多种数据源以及拖拽式报表功能，支持大屏报表设计。

> 该产品于2021年五月初新开项目，后续不断更新和维护。

#### 注意事项

- 开源不易，给个star吧
- [项目相关文档](https://gitee.com/doc_wei01/skyeye-report/blob/master/%E7%9B%B8%E5%85%B3%E6%96%87%E6%A1%A3.md)
- 本系列其他商业产品：[点击查看](https://docs.qq.com/doc/DQlRxcVRMWWVjbU1i?_from=1&disableReturnList=1)
- 免费进QQ群：1016439713。禁止发广告、招人信息等，一经发现直接踢出。
- 需要进微信群(加我拉你进群，wzq_598748873)，请提供自己的邮箱，默认订阅每周更新内容推送。
- 不需要进群的，请直接加微信：ZhiJinShuLi
- 不会搭建环境的，可以出钱让作者帮忙搭建，一次100，先付
- 该报表引擎属于开源软件，商业用途请保留作者相关信息以及产品著作权信息
- 以下条例视为侵权操作：
    - a.下载项目后，私自修改项目出处、署名、版权、项目名称等信息
    - b.允许商用，但禁止以盈利为目的进行销售(意思就是说禁止销售)
    - c.私自修改项目默认首页信息
- 您的赞赏是我们维持的最大动力

#### 功能说明

|功能|功能|功能|功能|
| ------------- | ------------- | ------------- | ------------- |
|用户管理|角色管理|组织管理|权限管理|
|数据库字典|系统LOGO|菜单管理|日志管理|
|应用商店|基础设置管理|---|---|
|数据库管理|数据源管理(支持XML、JSON、接口、SQL等)|文件模型管理|模型属性配置|
|报表设计|模型属性导入功能|||

##### 技术:

技术|名称|官网
---|---|---
SpringBoot|核心框架|http://spring.io/projects/spring-boot
MyBatis|ORM框架|http://www.mybatis.org/mybatis-3/zh/index.html
Druid|数据库连接池|https://github.com/alibaba/druid
Maven|项目构建管理|http://maven.apache.org/
redis|key-value存储系统|https://redis.io/
jQuery|函式库|http://jquery.com/
layui|模块化前端UI|https://www.layui.com/
winui|win10风格UI|https://gitee.com/doc_wei01_admin/skyeye
handlebars|js模板引擎|http://www.ghostchina.com/introducing-the-handlebars-js-templating-engine/

##### 软件版本：

技术|版本
---|---
SpringBoot|2.0.5.RELEASE
MySql|5.5.X
JDK|1.8
Redis|3.2

#### 安装教程

1.  安装Java环境、MySql环境以及单机Redis环境
2.  将项目导入idea(或者Exlipse)中，运行com.SkyeyeReportApplication
3.  访问http://localhost:8086，初始账号密码：root/123456

#### 数据源

- sql数据源
- rest接口数据源
- xml数据源
- json数据源

#### 目前已适配的模型

|模型|模型|模型|模型|模型|模型|
| ------------- | ------------- | ------------- | ------------- | ------------- | ------------- |
|基础折线图|基础平滑折线图|基础面积图|柱状图|基础饼图|圆角环形图|

#### 效果图

|效果图|效果图|
| ------------- | ------------- |
|![输入图片说明](https://images.gitee.com/uploads/images/2021/0509/215923_b1694e7a_1541735.png "屏幕截图.png")|![输入图片说明](https://images.gitee.com/uploads/images/2021/0509/215939_0cd740c8_1541735.png "屏幕截图.png")|
|![输入图片说明](https://images.gitee.com/uploads/images/2021/0509/220142_0ace6ff3_1541735.png "屏幕截图.png")|![输入图片说明](https://images.gitee.com/uploads/images/2021/0530/112630_49d03cef_1541735.png "屏幕截图.png")|
|![输入图片说明](https://images.gitee.com/uploads/images/2021/0705/222347_bfa3d3f6_1541735.png "屏幕截图.png")|![输入图片说明](https://images.gitee.com/uploads/images/2021/0705/222410_b097d159_1541735.png "屏幕截图.png")|
|![输入图片说明](https://images.gitee.com/uploads/images/2021/0705/222502_4283cfef_1541735.png "屏幕截图.png")|![输入图片说明](https://images.gitee.com/uploads/images/2021/0705/222440_d985e7f3_1541735.png "屏幕截图.png")|

#### 贡献开发者

1.  [卫志强](https://gitee.com/doc_wei01_admin)
2.  [郑杰](https://gitee.com/Jadan-Z)


### 捐助
如果您觉得我们的开源软件对你有所帮助，请扫下方二维码打赏我们一杯咖啡。

| 支付宝 | 微信 | 更新资讯公众号 |
| ------ | ---- | ---- |
| ![      ](https://images.gitee.com/uploads/images/2019/1016/094014_96f92c56_1541735.png "微信截图_20191016093832.png") | ![     ](https://images.gitee.com/uploads/images/2019/1016/094025_65ba24f0_1541735.png "微信截图_20191016093850.png")|![输入图片说明](https://images.gitee.com/uploads/images/2021/0320/091531_8c3ba4d8_1541735.jpeg "qrcode_for_gh_e7f97ff1beda_430.jpg")|
