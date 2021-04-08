## 图床(PicBed)开发说明（项目示例地址:http://8.129.15.139:8080/picbed）

#### 1.技术框架

springboot+mysql+nginx静态服务器

#### 2.现有功能

1.首页分页图片展示，支持分页查看，点击图片大图查看

![image-20210331151636486](C:\Users\zt\AppData\Roaming\Typora\typora-user-images\image-20210331151636486.png)

2.爬虫配置，目前只支持花瓣网的爬取，支持栏目爬取（对栏目下所有画板进行爬取）和指定画板爬取，每个画板采用一个单独线程进行。

![image-20210331151850939](C:\Users\zt\AppData\Roaming\Typora\typora-user-images\image-20210331151850939.png)

#### 3.下一步实现功能

1.增加爬虫源，先从爬取微博开始（爬取微博用户所有微博配图，已用python实现，需要使用java重新实现）；
2021-4-1：基本代码实现，目前采用单线程对单个用户进行爬取配图，后续将采用线程池对每页的微博进行线程分配式爬取。

2.首页显示图片细化分类，如图片来源（花瓣网，微博...）,图片类型(动漫，美女...)
2021-4-6:前端页面以及后端查询已支持实现分类查询

3.首页增加随机图片选项，随机从数据库抽取20张图片展示；
2021-4-6：新增随机展示20图（不分类型及来源），每次刷新页面重新查取

4.界面ui优化(主要解决图片大小不一的问题)；
2021-4-6:实现尺寸不一的图片自适应同一大小（虽然会有拉伸效果影响观感）

#### 4.未来预想功能

1.爬虫过程设置进度条显示（可显示启动的爬虫爬取进度）

实现想法：

以花瓣网为例，首先获取所有画板，每个画板一个进度条，其次查出画板下所有图片数量设为进度条总数，之后实时存储已爬取数量到redis中，前端定时查询redis更新进度条。

技术参考：

https://www.jianshu.com/p/836e5a9daeb2

![image-20210331152608148](C:\Users\zt\AppData\Roaming\Typora\typora-user-images\image-20210331152608148.png)

2.可能增加用户管理登录验证等门户管理模块，对外宣传开放平台。（目前自用）

3.移动端或小程序开发或前后端（vue）分离，采用热门友好的前端框架重构前端页面

#### 5.开发过程记录
1.目前项目采取的线程模块为java原生线程，通过继承Thread类实现，而且需要手动创建实例对象，后续考虑引入线程池管理和分配线程。--2021-4-1

2.已将爬虫多线程的实现方式重构为线程池的统一管理分配方式。--2021-4-8