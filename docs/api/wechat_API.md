微信 接口规格
=============

[TOC]

## 1 常规染色/冰冻预约

### 1.1 创建常规染色/冰冻预约
* __Method__
  POST

* __URL__
  /api/wechat/application

* __Request__

> 请求参数Json格式

```
{
	"applicant":"科研测试4", #申请者姓名
	"identity":1, #申请者身份
	"tutor":"", #导师
	"faculty":"", #院系
	"specialty":"", #专业
	"studentNumber":"", #学号
	"unit":"", #单位
	"wno":"", #职工号
	"taskName":"", #课题名称
	"taskType":1, #课题类型
	"funds":"经费来源",
	"phone":"1111111",
	"researchType":1, # 1-常规染色 2-冰冻预约
	"samples":[{"name":"eee","category":1}],
	"books": [{
	    "freezeStartTime":149839898877, #冰冻预约开始时间
	    "instrumentId": 1,  #1 代表一号机
      	"cells": [
      	   16,17
      	]
	  },{
	    "freezeStartTime":149839898877, #冰冻预约开始时间
        "instrumentId": 2,  #2 代表二号机
        "cells": [
           16,17
        ]
	  }
	],
	"departments":1 #科室
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```



### 1.2 冰冻预约查询
* __Method__
  GET

* __URL__
  /api/wechat/booking

* __Request__

Param | Type | Description

timeStart | Integer | 开始日期
timeEnd | Integer | 结束日期


* __Response__

> 成功

```
{
  "code": 0,
  "data": [
    {
      "instrumentId": 1,  #切片机1，前端拼下名称哈
      "date": 1490716800000, #代表年，月，日
      "bookingDto": [
        {
          "timeflag": 16,   #时间的标识 16代表8点到8点半
          "checked": null,
          "booked": true,  #true 代表预定了
          "bookingPerson": {   #预订人的名字，和电话
            "bookingName": "1",
            "phone": "1"
          }
        },
        {
          "timeflag": 17,
          "checked": null,
          "booked": true,
          "bookingPerson": {
            "bookingName": "1",
            "phone": "1"
          }
        },
        {
          "timeflag": 18,
          "checked": null,
          "booked": true,
          "bookingPerson": {
            "bookingName": "1",
            "phone": "1"
          }
        },
        {
          "timeflag": 19,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 20,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 21,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 22,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 23,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 24,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 25,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 26,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 27,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 28,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 29,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 30,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 31,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 32,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 33,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 34,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        },
        {
          "timeflag": 35,
          "checked": null,
          "booked": false,
          "bookingPerson": null
        }
      ]
    }
   ]
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```



### 1.3 创建特染申请
* __Method__
  POST
* __URL__
  /api/wechat/dye
* __Request__

> 请求参数Json格式

```
{
"applyTel":"2333", #联系电话
"applyUser":"特染1", #申请人
"departments":1, #科室
"source":1, #1代表诊断工作台申请 在染色申请页面申请为空
"ihcBlocks":[{"serialNumber":"Z17000008", #病理号
			 "subId":"2", #蜡块号
			 "specialDye":1, #特染类别 1-免疫组化
			 "ihcMarker":["ddff","ddfes"], #特染要求
			 "note":"" #备注
			 }]
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 1.4 查询我的蜡块
* __Method__
  GET
* __URL__
  /api/wechat/blocks
* __Request__

 Param | Type | Description

page | Integer | 页数
length | Integer | 每页的记录数
createTimeStart | Long | 开始时间
createTimeEnd  | Long | 结束时间
pathNo | String | 病理号




* __Response__

> 成功

```
{
  "code": 0,
  "data": {
    "total": 1,
    "data": [
      {
        "blockId": 112, #蜡块ID
        "pathologySerialNumber": "Z17000021", #病理号
        "grossingRemark": "fee", #取材备注
        "subId": "1", #蜡块号
        "bodyPart": "", #取材部位
        "unit": 1, #单位
        "unitDesc": "块",
        "count": 1, #组织数
        "biaoshi": 1,
        "biaoshiDesc": "常规", #取材标识
        "status": 11,
        "statusDesc": "待取材确认", #状态
        "specialDye": 0,
        "specialDyeDesc": "HE染色" #染色类别
      }
    ],
    "length": 10,
    "page": 1
  }
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 1.5 获取染色类别
* __Method__
  GET
* __URL__
  /api/wechat/dye/type
* __Request__

 Param | Type | Description
 无
 
 * __Response__
 
 > 成功
 
 ```
{
  "code": 0,
  "data": [
    {
      "code": 1,
      "name": "免疫组化"
    }]
 }
 
 ```

## 2.科室相关
### 2.1 获取科室列表
* __Method__
  GET
* __URL__
  /api/wechat/departments
* __Request__

 Param | Type | Description
 无
 
 * __Response__
 
 > 成功
 
 ```
{
  "code": 0,
  "data": [
    {
      "code": 1,
      "name": "呼吸内科"
    }]
 }
 
 ```
 
 ### 2.2 获取科室描述
 * __Method__
   GET
 * __URL__
   /api/wechat/departments/{code}
 * __Request__
 
  Param | Type | Description
   code | int | 科室编码
  
  * __Response__
  
  > 成功
  
  ```
  {
    "code": 0,
    "data": "呼吸内科"
  }
  
  ```
  
  ## 3.用户信息
  ### 3.1 获取用户信息
   * __Method__
     GET
   * __URL__
     /api/wechat/user
   * __Request__
   
    Param | Type | Description
     无
    
    * __Response__
    
    > 成功
    
    ```
{
  "code": 0,
  "data": {
    "id": 2,
    "userName": "admin",
    "firstName": "管理员",
    "lastName": null,
    "email": null,
    "phone": "1234567",
    "createByName": "admin@flucloud.com.cn",
    "createTime": 1495594449000,
    "status": true,
    "statusName": "正常",
    "lockStatus": false,
    "identity": null,
    "identityDesc": null,
    "tutor": null,
    "faculty": null,
    "specialty": null,
    "studentNumber": null,
    "unit": null,
    "departments": 2,
    "departmentsDesc": "消化内科",
    "wno": null,
    "taskName": null,
    "taskType": null,
    "taskTypeDesc": null,
    "projectCode": null
  }
}
    
    ```


   ## 4 查看信息
   ### 4.1 获取申请信息
    * __Method__
      GET
    * __URL__
      /api/wechat/application
    * __Request__

     Param | Type | Description

       page | int | 页数

       length | int | 每页记录数

       createTimeStart | Long | 申请开始时间

       createTimeEnd | Long | 申请结束时间

       status | int | 1 代表处理中，2代表已完成

       type | int | 1 代表常规染色 ，2代表 冰冻预约 ，3代表染色申请，什么都不传是全部

       filter | String | 病理号

       sort | int | 1 代表升序，2代表降序


     * __Response__

     > 成功

     ```
 {
   "code": 0,
   "data": {
     "total": 8,
     "data": [
       {
         "pid": null,                         //病理ID
         "aid": 10,                           //申请ID
         "applicationTime": 1495681592000,    //申请时间
         "aserialNumber": "AP17000010",        //申请号
         "pserialNumber": null,                //病理号
         "bookingStartTime": 149817600000,     //预约开始时间
         "bookingEndTime": 149821200000,       //预约结束时间
         "instrumentId": 1,                   //切片机类型，1代表切片机一，2代表切片机二
         "instrumentIdDesc": "切片机一",
         "pstatus": null,                       //病理状态
         "pstatusDesc": null,                   //处理中和已完成 ，待登记，已拒收，已撤销，已结束   申请的状态
         "bookingId": 9,                          //预约ID
         "astatus": 30,                         //常规申请状态  1 待登记 ， 2 已登记 ，3 已拒收 ，4 已撤销 ，30 已结束
         "astatusDesc": "冰冻预约",
         "aiId": null,                          //染色申请ID
         "aiApplicationTime": null,             // 染色申请时间
         "pserialNumbers": null                 //一个染色申请会有多个病理号，这是一个数组
          "specialDyeStatus": null,             //染色申請狀態  1 待確認 2已確認 3已撤銷
          "specialDyeStatusDesc": null              //显示
       },
       {
         "pid": null,
         "aid": 10,
         "applicationTime": 1495681592000,
         "aserialNumber": "AP17000010",
         "pserialNumber": null,
         "bookingStartTime": 149817600000,
         "bookingEndTime": 149821200000,
         "instrumentId": 2,
         "instrumentIdDesc": "切片机二",
         "pstatus": null,
         "pstatusDesc": null,
         "bookingId": 10,
         "astatus": 30,
         "astatusDesc": "冰冻预约",
         "aiId": null,
         "aiApplicationTime": null,
         "pserialNumbers": null
          "specialDyeStatus": null,
          "specialDyeStatusDesc": null
       },
       {
         "pid": 13,                                //病理ID
         "aid": 12,                                 //申请ID
         "applicationTime": 1496198734000,         //常规申请时间
         "aserialNumber": "AP17000012",             //申请号
         "pserialNumber": "Z17000013",              //病理号
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": 10,                             //病理状态
         "pstatusDesc": "处理中",
         "bookingId": null,
         "astatus": 2,                             //常规申请状态  1 待登记    2已登记 （已登记显示处理中或者已完成）3 已拒收 ，4 已撤销
         "astatusDesc": "常规染色",                 //申请类型
         "aiId": null,
         "aiApplicationTime": null,
         "pserialNumbers": null,
          "specialDyeStatus": null,
          "specialDyeStatusDesc": null
       },
       {
         "pid": null,
         "aid": 14,
         "applicationTime": 1496282851000,
         "aserialNumber": "AP17000014",
         "pserialNumber": null,
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": null,
         "pstatusDesc": null,
         "bookingId": null,
         "astatus": 1,
         "astatusDesc": "常规染色",
         "aiId": null,
         "aiApplicationTime": null,
         "pserialNumbers": null
         "specialDyeStatus": null,
         "specialDyeStatusDesc": null
       },
       {
         "pid": 14,
         "aid": 16,
         "applicationTime": 1496995215000,
         "aserialNumber": "AP17000016",
         "pserialNumber": "Z17000014",
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": 12,
         "pstatusDesc": "处理中",
         "bookingId": null,
         "astatus": 2,
         "astatusDesc": "常规染色",
         "aiId": null,
         "aiApplicationTime": null,
         "pserialNumbers": null
         "specialDyeStatus": null,
        "specialDyeStatusDesc": null
       },
       {
         "pid": 16,
         "aid": 18,
         "applicationTime": 1496996031000,
         "aserialNumber": "AP17000018",
         "pserialNumber": "Z17000016",
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": 12,
         "pstatusDesc": "处理中",
         "bookingId": null,
         "astatus": 2,
         "astatusDesc": "常规染色",
         "aiId": null,
         "aiApplicationTime": null,
         "pserialNumbers": null
          "specialDyeStatus": null,
          "specialDyeStatusDesc": null
       },
       {
         "pid": null,
         "aid": null,
         "applicationTime": null,
         "aserialNumber": null,
         "pserialNumber": "Z17000014",      //  病理号
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": 12,                       //病理状态
         "pstatusDesc": "null",
         "bookingId": null,
         "astatus": null,
         "astatusDesc": "染色申请",          //申请类别
         "aiId": 5,
         "aiApplicationTime": 1496995292000,   //染色的申请时间
         "pserialNumbers": [                  //一个染色申请有多个病理号
           "Z17000014"
         ]
         "specialDyeStatus": 1,               //染色的状态 1代表待确认  2代表已确认 （已确认状态下 specialDyeStatusDesc会显示处理中和已完成）  3代表撤销（specialDyeStatusDesc会显示撤销）
          "specialDyeStatusDesc": 待確認
       },
       {
         "pid": null,
         "aid": null,
         "applicationTime": null,
         "aserialNumber": null,
         "pserialNumber": "Z17000015",
         "bookingStartTime": null,
         "bookingEndTime": null,
         "instrumentId": null,
         "instrumentIdDesc": null,
         "pstatus": 12,
         "pstatusDesc": "null",
         "bookingId": null,
         "astatus": null,
         "astatusDesc": "染色申请",
         "aiId": 6,
         "aiApplicationTime": 1496996122000,
         "pserialNumbers": [
           "Z17000015",
           "Z17000016"
         ],
           "specialDyeStatus": 2,
           "specialDyeStatusDesc": "处理中"
       }
     ],
     "length": 20,
     "page": 1
   }
 }

     ```
 
 