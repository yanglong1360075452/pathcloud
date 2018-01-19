#相关文档
- angular-bootstrap : http://angular-ui.github.io/bootstrap/versioned-docs/1.3.3/
- bootstrap : http://v3.bootcss.com/
- font-awesome : http://www.bootcss.com/p/font-awesome/
- ui-router : http://angular-ui.github.io/ui-router/site
- sass : http://docs.angularjs.org/api/ngResource

# 标签打印 A4纸的打印 引入了 lodop打印插件 http://www.lodop.net/LodopDemo.html 
## lodop打印插件文档： http://www.lodop.net/download/Lodop6.2NoteBook20161216.zip
## 安装插件后在本地启动打印服务 http://localhost:8000/


# 项目结构
├── bower_components               bower模块目录
├── dist                   项目打包后的文件
├── gulp                       gulp配置文件
├── node_modules               node模块目录
├── src
│   ├── assets                  静态资源目录
│   │   ├── i18n               国际化json配置
│   │   ├── images             图片
│   │   ├── lib                直接从根目录引入的依赖 不会被gulp 打包
│   │   ├── printConfig        打印方法配置json文件
│   ├── app                     核心目录
│   │   ├── components         通用功能组件
│   │   │   ├── departmentSelect              科室下拉选择组件
│   │   │   ├── dateSeletor                   日期选择组件
│   │   │   ├── yearSeletor                   年份选择组件
│   │   │   ├── monthSeletor                  月份选择组件
│   │   │   ├── pathologicalForm              病理申请表单组件
│   │   │   │   ├── applicationForm                       申请表单组件     【目前在查询工作站使用了 显示查看申请表单 防止scope传值失败】
│   │   │   │   ├── pathologicalForm.directive.js         临床申请表单组件 【通过scope传值的临床表单组件 跟上面那个冗余】
│   │   │   │   ├── applicationResearch     科研申请表单组件
│   │   │   │   └── consultFrom             会诊申请表单组件
│   │   │   ├── report                       诊断报告显示组件
│   │   │   ├── searchInput                   搜索输入框组件
│   │   ├── directive          指令目录
│   │   ├── filter             过滤器目录
│   │   ├── lib                依赖库插件目录
│   │   ├── services              工具类服务模块目录
│   │   │   ├── csv.factory.js       导出css服务
│   │   │   ├── department.factory.js      送检科室
│   │   │   ├── ihc.factory.js          通用请求服务 
│   │   │   ├── 。。。。。。。          对应工作站的service
│   │   │   └── video.factory.js        拍照服务
│   │   ├── site              所有页面
│   │   │   ├── application       申请工作站
│   │   │   ├── archive           存档工作站
│   │   │   ├── caseQuery         查询工作站
│   │   │   ├── diagnosis         诊断工作站
│   │   │   ├── drawMaterial      取材工作站
│   │   │   ├── dye               染色工作站
│   │   │   ├── embed             包埋工作站
│   │   │   ├── freeze            冰冻取材工作站
│   │   │   ├── grossing          脱水工作站
│   │   │   ├── home              首页
│   │   │   ├── ihc               免疫组化工作站
│   │   │   ├── login             登录页面
│   │   │   ├── main              。。。。框架的页面
│   │   │   ├── modal             # 通用弹窗页面
│   │   │   ├── notification        通知页面
│   │   │   ├── product             制片确认工作站
│   │   │   ├── profile             个人资料页面
│   │   │   ├── reagent             试剂管理工作站
│   │   │   ├── report              报告工作站
│   │   │   ├── sampleRegistration             样本登记工作站
│   │   │   ├── section             切片工作站
│   │   │   ├── specialDye          特染申请工作站
│   │   │   ├── statisticalReport   统计报告工作站
│   │   │   └── userManage        系统管理工作站
│   │   └── app.module.js          前端路由
│   │   └── en.scss             英文模式样式
│   │   └── index.config.js          angular插件配置
│   │   └── index.constant.js        API地址配置
│   │   └── index.routes.js          前端路由
│   │   └── index.run.js        angular启动时执行一次的方法  ## 配置每个工作站的导航在此处配置
│   │   └── index.scss          通用页面样式
│   └── index.html              前端入口文件
├── .gitignore
├── package.json
├── gulpfile.js
└── README.md
