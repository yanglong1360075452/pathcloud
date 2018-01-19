
新加功能  接口规格
=============

[TOC]

## 1 脱水工作台

### 1.1 查看运行中脱水机蜡块信息

* __Method__
  GET
* __URL__
  /api/dehydrator/{instrumentId}/blocks
* __Request__

 Param | Type | Description

instrumentId | Long | 脱水机ID

 page | Int| 页数

 length | Int| 每页长度

* __Response__

> 成功

```
{
  "code": 0,
  "data": {
    "total": 12,
    "data": [
      {
        "pathNo": "Z17000001",
        "subId": "1",
        "patientName": "ee",
        "count": 1,
        "unit": 1,
        "unitDesc": "块",
        "biaoshi": 1,
        "biaoshiDesc": "常规",
        "status": 13,
        "statusDesc": "脱水中",
        "instrumentId": 1,
        "instrumentName": "1"
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













