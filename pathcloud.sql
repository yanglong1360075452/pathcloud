CREATE DATABASE IF NOT EXISTS path_test
  DEFAULT CHARSET = utf8
  COLLATE utf8_general_ci;

create user 'pathtraq' identified by 'xxxx';
grant ALL privileges on path_test.* to 'pathtraq';

##获取当前值函数
##Need to make change below otherwise create function will fail
##set global log_bin_trust_function_creators=1;
DELIMITER $
CREATE FUNCTION currentVal(seq_name VARCHAR(50))
  RETURNS INTEGER
CONTAINS SQL
  BEGIN
    DECLARE current INTEGER;
    SET current = 0;
    SELECT currentValue
    INTO current
    FROM sequence
    WHERE seqname = seq_name;
    RETURN current;
  END$
DELIMITER ;

##获取下一个值函数
DELIMITER $
CREATE FUNCTION nextVal(seq_name VARCHAR(50))
  RETURNS INTEGER
CONTAINS SQL
  BEGIN
    UPDATE sequence
    SET currentValue = currentValue + increment
    WHERE seqname = seq_name;
    RETURN currentVal(seq_name);
  END$
DELIMITER ;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `user_name`       VARCHAR(30)  NOT NULL UNIQUE, #用户名
  `first_name`      VARCHAR(30)           DEFAULT NULL, #真实姓名
  `last_name`       VARCHAR(30)           DEFAULT NULL,
  `password`        VARCHAR(100) NOT NULL, #密码
  `email`           VARCHAR(50)           DEFAULT NULL, #电子邮箱
  `phone`           VARCHAR(18)           DEFAULT NULL, #联系电话
  `status`          BIT(1)       NOT NULL, #状态
  `role_id`         BIGINT(20)            DEFAULT NULL, #角色
  `identity`        INT(11)               DEFAULT NULL, #身份
  `tutor`           VARCHAR(50)           DEFAULT NULL, #导师
  `faculty`         VARCHAR(50)           DEFAULT NULL, #院系
  `student_number`  VARCHAR(50)           DEFAULT NULL, #学号
  `unit`            VARCHAR(50)           DEFAULT NULL, #单位
  `wno`             VARCHAR(50)           DEFAULT NULL, #职工号
  `departments`     INT(11)               DEFAULT NULL, #科室
  `task_type`       INT(11)               DEFAULT NULL, #课题类型
  `task_name`       VARCHAR(50)           DEFAULT NULL, #课题名称
  `specialty`       VARCHAR(50)           DEFAULT NULL, #专业
  `qr_code`         VARCHAR(255)          DEFAULT NULL, #登录二维码
  `project_code`    VARCHAR(50)           DEFAULT NULL, #项目代码
  `lock_status`     BIT(1)       NOT NULL,
  `lock_count`      INT(11)               DEFAULT NULL, #登录失败计数
  `lock_count_time` DATETIME              DEFAULT NULL, #登录失败计数开始时间
  `last_login_time` DATETIME              DEFAULT NULL,
  `last_login_ip`   VARCHAR(30)           DEFAULT NULL,
  `create_by`       BIGINT(20)   NOT NULL,
  `update_by`       BIGINT(20)            DEFAULT NULL,
  `create_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `user`
  ADD INDEX `index_role_id` USING BTREE (`role_id`);

INSERT INTO `user` (`id`, `user_name`, `first_name`, `last_name`, `password`, `email`, `phone`, `status`, `role_id`, `lock_status`, `last_login_time`, `last_login_ip`, `create_by`, `update_by`, `create_time`, `update_time`, `identity`, `tutor`, `faculty`, `student_number`, `unit`, `wno`, `task_name`, `specialty`, `project_code`, `departments`, `task_type`)
VALUES (1, 'admin@flucloud.com.cn', 'admin', '', '$2a$10$s.XKjkYQ.4C7w0fWq0jR2.zkrBcDlvuWDuPFdrHkOmVSatMFsSf3q',
           'admin@flucloud.com.cn', '68654588', b'1', 1, b'0', NULL, '127.0.0.1', 1, NULL, now(), NULL, NULL, NULL,
                                                                     NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL);


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(30) NOT NULL, #角色名
  `description` VARCHAR(1000)        DEFAULT NULL, #角色描述
  `create_by`   BIGINT(20)  NOT NULL, #创建人
  `update_by`   BIGINT(20)           DEFAULT NULL, #更新人
  `create_time` DATETIME             DEFAULT CURRENT_TIMESTAMP, #创建时间
  `update_time` DATETIME             DEFAULT CURRENT_TIMESTAMP, #更新时间
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id`       BIGINT(20), #角色ID
  `permission_id` INT(11), #权限ID
  PRIMARY KEY (`role_id`, `permission_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(30) NOT NULL, #权限名称
  `code`        INT(11)     NOT NULL, #权限编码
  `description` VARCHAR(1000)        DEFAULT NULL, #权限描述
  `create_by`   BIGINT(20)  NOT NULL, #创建人
  `update_by`   BIGINT(20)           DEFAULT NULL, #更新人
  `create_time` DATETIME             DEFAULT CURRENT_TIMESTAMP, #创建时间
  `update_time` DATETIME             DEFAULT CURRENT_TIMESTAMP, #更新时间
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `permission`
  ADD INDEX `index_code` USING BTREE (`code`);

INSERT INTO role (id, name, description, create_by) VALUE (1, '管理员', '', 1);

INSERT INTO permission (id, name, code, description, create_by) VALUE (1, '系统管理', 0, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (2, '病理申请', 1, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (3, '一级诊断', 2, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (4, '取材', 4, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (5, '脱水', 8, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (6, '包埋', 16, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (7, '切片', 32, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (8, '染色', 64, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (9, '制片确认', 128, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (10, '登记', 256, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (11, '存档', 512, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (12, '信息查询', 1024, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (13, '报告', 2048, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (14, '二级诊断', 4096, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (15, '三级诊断', 8192, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (16, '统计报表', 16384, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (17, '特染', 32768, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (18, '免疫组化', 65536, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (19, '冰冻取材', 131072, '', 1);
INSERT INTO permission (id, name, code, description, create_by) VALUE (20, '试剂耗材', 262144 , '', 1);

INSERT INTO role_permission VALUE (1, 1);
INSERT INTO role_permission VALUE (1, 2);
INSERT INTO role_permission VALUE (1, 3);
INSERT INTO role_permission VALUE (1, 4);
INSERT INTO role_permission VALUE (1, 5);
INSERT INTO role_permission VALUE (1, 6);
INSERT INTO role_permission VALUE (1, 7);
INSERT INTO role_permission VALUE (1, 8);
INSERT INTO role_permission VALUE (1, 9);
INSERT INTO role_permission VALUE (1, 10);
INSERT INTO role_permission VALUE (1, 11);
INSERT INTO role_permission VALUE (1, 12);
INSERT INTO role_permission VALUE (1, 13);
INSERT INTO role_permission VALUE (1, 14);
INSERT INTO role_permission VALUE (1, 15);
INSERT INTO role_permission VALUE (1, 16);
INSERT INTO role_permission VALUE (1, 17);
INSERT INTO role_permission VALUE (1, 18);
INSERT INTO role_permission VALUE (1, 19);
INSERT INTO role_permission VALUE (1, 20);

#病理申请
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `pathology_id`       BIGINT(20)          DEFAULT NULL
  COMMENT '病理ID',
  `number`             CHAR(10)            DEFAULT NULL
  COMMENT '特殊申请号',
  `serial_number`      CHAR(11)            DEFAULT NULL
  COMMENT '病理申请号',
  `his_id`             VARCHAR(15)         DEFAULT NULL, #病人ID
  `patient_name`       VARCHAR(30)         DEFAULT NULL, #病人姓名
  `patient_no`         VARCHAR(30)         DEFAULT NULL, #门诊号
  `admission_no`       VARCHAR(30)         DEFAULT NULL, #住院号
  `status`             INT(11)    NOT NULL, #申请状态 1，未登记 2，已登记 3，已拒绝  4,已撤销
  `reject_reason`      VARCHAR(255)        DEFAULT NULL, #拒收原因
  `reason_type`        VARCHAR(255)        DEFAULT NULL, #拒收原因类别
  `age`                INT(3)              DEFAULT NULL, #年龄
  `sex`                INT(1)              DEFAULT NULL, #性别 0未知，1男， 2女
  `marital_status`     INT(2)              DEFAULT 90, #婚否，10未婚，20已婚，30丧偶，40离婚，90未知
  `origin_place`       VARCHAR(20)         DEFAULT NULL, #籍贯
  `profession`         VARCHAR(20)         DEFAULT NULL, #职业
  `patient_tel`        VARCHAR(18)         DEFAULT NULL, #联系电话
  `address`            VARCHAR(30)         DEFAULT NULL, #联系地址
  `hospital`           INT(11)             DEFAULT 0
  COMMENT '送检医院',
  `doctor`             VARCHAR(10)         DEFAULT NULL, #送检医生
  `departments`        INT(11)             DEFAULT NULL, #送检科室
  `doctor_tel`         VARCHAR(18)         DEFAULT NULL, #送检医生电话
  `inspection_item`    VARCHAR(20)         DEFAULT NULL, #检查项目
  `visit_cat`          VARCHAR(20)         DEFAULT NULL, #就诊类别
  `gynaecology`        BIT(1)              DEFAULT NULL, #妇科类, true是，false否
  `menopause`          BIT(1)              DEFAULT NULL, #是否绝经, true是，false否
  `menopause_time`     VARCHAR(20)         DEFAULT NULL, #经期及持续时间
  `menopause_end`      DATE                DEFAULT NULL, #绝经日期
  `hcg`                FLOAT(9, 3)         DEFAULT NULL, #绒毛膜促性腺激素值
  `gynaecology_remark` TEXT                DEFAULT NULL, #妇科备注
  `clinical_diagnosis` TEXT                DEFAULT NULL, #临床诊断
  `medical_summary`    TEXT                DEFAULT NULL, #病历摘要
  `clinical_findings`  TEXT                DEFAULT NULL, #临床所见
  `imaging_findings`   TEXT                DEFAULT NULL, #影像所见
  `operation_findings` TEXT                DEFAULT NULL, #手术名称及所见
  `medical_history`    TEXT                DEFAULT NULL, #过往病史
  `history_medicine`   TEXT                DEFAULT NULL, #曾用药品及剂量
  `history_pathology`  TEXT                DEFAULT NULL, #历史病理
  `urgent_flag`        BIT(1)              DEFAULT NULL, #是否加急
  `research`           TEXT                DEFAULT NULL, #科研申请
  `apply_type`         INT(1)              DEFAULT 1, #申请类别
  `applicant`          VARCHAR(30)         DEFAULT NULL, #申请人
  `create_by`          BIGINT(20) NOT NULL,
  `update_by`          BIGINT(20)          DEFAULT NULL,
  `create_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP, #申请时间
  `update_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `application`
  ADD INDEX `index_status` USING BTREE (`status`);
ALTER TABLE `application`
  ADD UNIQUE `index_serial_number` USING BTREE (`serial_number`);
ALTER TABLE `application`
  ADD INDEX `idx_patient_name` USING BTREE (`patient_name`);
ALTER TABLE `application`
  ADD INDEX `idx_departments` USING BTREE (`departments`);
ALTER TABLE `application`
  ADD INDEX `index_create_by` USING BTREE (`create_by`);

#病理
DROP TABLE IF EXISTS `pathology`;
CREATE TABLE `pathology` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `serial_number`      CHAR(9)             DEFAULT NULL, #病理号
  `admission_no`       VARCHAR(30)         DEFAULT NULL, #住院号
  `result`             LONGTEXT            DEFAULT NULL, #诊断结果
  `micro_diagnose`     TEXT                DEFAULT NULL, #显微所见
  `diagnose`           TEXT                DEFAULT NULL, #诊断描述
  `jujian_note`        TEXT                DEFAULT NULL, #巨检描述
  `bingdong_note`      TEXT                DEFAULT NULL, #冰冻诊断
  `re_grossing`        BIT(1)              DEFAULT 0
  COMMENT '是否重补取',
  `label`              INT(11)             DEFAULT NULL
  COMMENT '病例标注',
  `status`             INT(11)             DEFAULT 10, #状态
  `coincidence`        INT(11)             DEFAULT NULL
  COMMENT '诊断符合度',
  `inspect_category`   INT(11)    NOT NULL, #检查类别
  `patient_name`       VARCHAR(30)         DEFAULT NULL, #病人姓名
  `departments`        INT(11)             DEFAULT NULL, #送检科室
  `apply_type`         INT(1)              DEFAULT 1, #申请类别
  `assign_grossing`    BIGINT(20)          DEFAULT NULL, #指定取材医生
  `assign_diagnose`    BIGINT(20)          DEFAULT NULL, #指定诊断医生
  `report_pic`         LONGTEXT            DEFAULT NULL, #报告图片Base64
  `note`               TEXT                DEFAULT NULL, #备注
  `after_frozen`       BIT(1)              DEFAULT 0
  COMMENT '是否冻后',
  `out_consult`        BIT(1)              DEFAULT 0
  COMMENT '是否外院会诊',
  `out_consult_result` LONGTEXT            DEFAULT NULL
  COMMENT '外院会诊结果',
  `create_by`          BIGINT(20) NOT NULL,
  `update_by`          BIGINT(20)          DEFAULT NULL,
  `create_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP, #登记时间
  `update_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `pathology`
  ADD INDEX `index_serial_number` USING BTREE (`serial_number`);
ALTER TABLE `pathology`
  ADD INDEX `index_status` USING BTREE (`status`);
ALTER TABLE `pathology`
  ADD INDEX `index_create_time` USING BTREE (`create_time`);
CREATE FULLTEXT INDEX idx_jujian
  ON pathology (`jujian_note`) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_mirco
  ON pathology (`micro_diagnose`) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_diagnose
  ON pathology (`diagnose`) WITH PARSER ngram;
CREATE FULLTEXT INDEX idx_ngram
  ON pathology (`jujian_note`, `diagnose`, `micro_diagnose`) WITH PARSER ngram;
ALTER TABLE `pathology`
  ADD INDEX `index_assign_diagnose` USING BTREE (`assign_diagnose`);
ALTER TABLE `pathology`
  ADD INDEX `index_departments` USING BTREE (`departments`);

#样本
DROP TABLE IF EXISTS `sample`;
CREATE TABLE `sample` (
  `id`             BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `application_id` BIGINT(20)  NOT NULL, #申请ID
  `serial_number`  CHAR(14)             DEFAULT NULL, #样本号
  `name`           VARCHAR(20) NOT NULL, #样本名
  `category`       INT(11)              DEFAULT NULL, #样本类别，1 大样本 2 小样本
  `note`           TEXT                 DEFAULT NULL, #备注
  `delete`         BIT(1)               DEFAULT 0, #是否删除
  `create_by`      BIGINT(20)  NOT NULL,
  `update_by`      BIGINT(20)           DEFAULT NULL,
  `create_time`    DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time`    DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `sample`
  ADD UNIQUE `index_serial_number` USING BTREE (`serial_number`);
ALTER TABLE `sample`
  ADD INDEX `index_application_id` USING BTREE (`application_id`);

#腊块或玻片
DROP TABLE IF EXISTS `block`;
CREATE TABLE `block` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `serial_number` CHAR(15)            DEFAULT NULL,
  `number`        CHAR(10)            DEFAULT NULL
  COMMENT '特殊申请号',
  `path_id`       BIGINT(20) NOT NULL, #病理ID
  `sub_id`        VARCHAR(9) NOT NULL, #编号
  `biaoshi`       INT(11)             DEFAULT NULL, #取材标识，1，常规，2，重取，3 脱钙
  `body_part`     VARCHAR(20)         DEFAULT NULL, #取材部位
  `count`         INT(11)             DEFAULT NULL, #组织数
  `unit`          INT(11)             DEFAULT NULL, #组织单位
  `note`          TEXT                DEFAULT NULL, #取材备注
  `print`         INT(11)             DEFAULT 0, #打印次数
  `embed_pause`   BIT(1)              DEFAULT b'0', #暂停包埋
  `parent_id`     BIGINT(20)          DEFAULT NULL, #玻片对应的蜡块ID
  `marker`        VARCHAR(1024)       DEFAULT NULL, #标记物
  `deep_section`  BIT(1)              DEFAULT 0, #是否深切
  `special_dye`   INT(11)             DEFAULT NULL, #蜡块-是否申请染色  0-未申请, 1-申请 玻片-染色类别
  `apply_id`      BIGINT(20)          DEFAULT NULL, #特殊操作申请对应tracking记录ID
  `status`        INT(11)             DEFAULT 11, #状态  11-待取材确认  12-待脱水...
  `create_by`     BIGINT(20) NOT NULL,
  `update_by`     BIGINT(20)          DEFAULT NULL,
  `create_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `block`
  ADD INDEX `index_path_id` USING BTREE (`path_id`);
ALTER TABLE `block`
  ADD INDEX `index_status` USING BTREE (`status`);
ALTER TABLE `block`
  ADD INDEX `index_serial_number` USING BTREE (`serial_number`);
ALTER TABLE `block`
  ADD INDEX `index_sub_id` USING BTREE (`sub_id`);
ALTER TABLE `block`
  ADD INDEX `index_parent_id` USING BTREE (`parent_id`);

#腊块或玻片的track
DROP TABLE IF EXISTS `tracking`;
CREATE TABLE `tracking` (
  `id`              BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `block_id`        BIGINT(20)  NOT NULL, #
  `operation`       INT(11)     NOT NULL, #操作
  `operator_id`     BIGINT(20)  NOT NULL, #操作人
  `operator_name`   VARCHAR(30) NOT NULL, #操作人姓名
  `sec_operator_id` BIGINT(20)           DEFAULT NULL, #第二操作人
  `operation_time`  DATETIME    NOT NULL, #操作时间
  `manual_flag`     BIT(1)               DEFAULT b'0', #手动添加或自动
  `instrument_id`   INT(11)     NULL, #设备ID，或脱水篮编号
  `note`            TEXT                 DEFAULT NULL, #备注
  `note_type`       VARCHAR(255)         DEFAULT NULL, #备注类别
  `create_by`       BIGINT(20)  NOT NULL,
  `update_by`       BIGINT(20)           DEFAULT NULL,
  `create_time`     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `tracking`
  ADD INDEX `index_operation` USING BTREE (`operation`);
ALTER TABLE `tracking`
  ADD INDEX `index_block_id` USING BTREE (`block_id`);
ALTER TABLE `tracking`
  ADD INDEX `index_instrument_id` USING BTREE (`instrument_id`);
ALTER TABLE `tracking`
  ADD INDEX `index_operator_id` USING BTREE (`operator_id`);
ALTER TABLE `tracking`
  ADD INDEX `index_sec_operator_id` USING BTREE (`sec_operator_id`);
ALTER TABLE `tracking`
  ADD INDEX `index_operation_time` USING BTREE (`operation_time`);

#模板
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id`            INT(11)    NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(20)         DEFAULT NULL, #模板名
  `category`      INT(11)    NOT NULL, #模板或分类（0分类，1模板）
  `parent`        INT(11)    DEFAULT NULL COMMENT '模板父类别 取材/免疫组化/诊断系统模板 对应送检科室code值',
  `level`         INT(11)    NOT NULL, #模板层级管理
  `content`       MEDIUMTEXT          DEFAULT NULL, #模板内容
  `position`      VARCHAR(4) NOT NULL, #模板位置（取材的相关模板、1是取材模板，2是诊断模板 ，3是免疫组化标记物 ，4是报告模板）
  `display_order` INT(11)    NOT NULL, #显示顺序
  `create_by`     BIGINT(20) NOT NULL,
  `update_by`     BIGINT(20)          DEFAULT NULL,
  `create_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `template`
  ADD INDEX `index_category` USING BTREE (`category`);
ALTER TABLE `template`
  ADD INDEX `index_position` USING BTREE (`position`);

#初始化报告模板
INSERT INTO `template` (`id`, `name`, `category`, `parent`, `level`, `content`, `position`, `display_order`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES ('1', '报告模板', '1', '0', '1',
             '{\"imageURL\":\"data:image/jpeg;base64,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\u003d\u003d\",\"reportBigName\":\"赛默飞世尔科技\",\"reportSmallName\":\"病理云端解决方案\",\"patientInformation\":[{\"name\":\"病人ID\",\"check\":true},{\"name\":\"登记/住院号\",\"check\":true},{\"name\":\"病理号\",\"check\":true},{\"name\":\"门诊号\",\"check\":true},{\"name\":\"姓名\",\"check\":true},{\"name\":\"年龄\",\"check\":true},{\"name\":\"性别\",\"check\":true},{\"name\":\"床号\",\"check\":true},{\"name\":\"申请科室\",\"check\":true},{\"name\":\"样本接收时间\",\"check\":true},{\"name\":\"申请医生\",\"check\":true},{\"name\":\"住院患者位置\",\"check\":true}],\"diagnosticContent\":[{\"name\":\"肉眼所见\",\"check\":true},{\"name\":\"显微所见\",\"check\":true},{\"name\":\"病理诊断\",\"check\":true}],\"diagnosticInformation\":[{\"name\":\"诊断医生\",\"check\":true},{\"name\":\"诊断时间\",\"check\":true},{\"name\":\"报告打印者\",\"check\":true}],\"hospitalInformation\":\"病理收发室：028-00000000    病理诊断查询：028-11111111\n会诊接待：028-22222222       门诊细胞病理：028-33333333\n通讯地址：成都市国学巷37号四川大学华西医院病理科  邮编：610041\",\"specialTip\":\"此报告仅反映送检样本的情况\"}',
             '4', '1', '1', '1', NOW(), NOW());

#参数设置
DROP TABLE IF EXISTS `param_setting`;
CREATE TABLE `param_setting` (
  `id`          INT(11)    NOT NULL AUTO_INCREMENT,
  `key`         VARCHAR(50)         DEFAULT NULL, #设置标识
  `content`     TEXT                DEFAULT NULL, #内容
  `create_by`   BIGINT(20) NOT NULL,
  `update_by`   BIGINT(20)          DEFAULT NULL,
  `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `param_setting`
  ADD UNIQUE `index_key` USING BTREE (`key`);

INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (1, 'departments',
                                                                            '[{"id":1,"departmentCategory": "内科","code": 1, "name": "呼吸内科"}, {"id":1,"departmentCategory": "内科","code": 2, "name": "消化内科"}, {"id":1,"departmentCategory": "内科","code": 3, "name": "肾脏内科"}, {"id":1,"departmentCategory": "内科","code": 4, "name": "心脏内科"}, {"id":1,"departmentCategory": "内科","code": 5, "name": "血液内科"}, {"id":1,"departmentCategory": "内科","code": 6, "name": "内分泌代谢科"}, {"id":1,"departmentCategory": "内科","code": 7, "name": "风湿免疫科"}, {"id":1,"departmentCategory": "内科","code": 8, "name": "传染科"}, {"id":1,"departmentCategory": "内科","code": 9, "name": "干部医疗科"}, {"id":1,"departmentCategory": "内科","code": 10, "name": "神经内科"}, {"id":1,"departmentCategory": "内科","code": 11, "name": "中西医结合科"}, {"id":1,"departmentCategory": "内科","code": 12, "name": "皮肤科"}, {"id":1,"departmentCategory": "内科","code": 13, "name": "康复医学科"}, {"id":1,"departmentCategory": "内科","code": 14, "name": "ICU"}, {"id":1,"departmentCategory": "内科","code": 15, "name": "肿瘤科"}, {"id":2,"departmentCategory": "外科","code": 16, "name": "烧伤整形科"}, {"id":2,"departmentCategory": "外科","code": 17, "name": "骨科"}, {"id":2,"departmentCategory": "外科","code": 18, "name": "泌尿外科"}, {"id":2,"departmentCategory": "外科","code": 19, "name": "神经外科"}, {"id":2,"departmentCategory": "外科","code": 20, "name": "胸外科"}, {"id":2,"departmentCategory": "外科","code": 21, "name": "心脏大血管外科"}, {"id":2,"departmentCategory": "外科","code": 22, "name": "小儿外科"}, {"id":2,"departmentCategory": "外科","code": 23, "name": "器官移植中心"}, {"id":2,"departmentCategory": "外科","code": 24, "name": "甲状腺乳腺外科"}, {"id":2,"departmentCategory": "外科","code": 25, "name": "胃肠外科"},{"id":2,"departmentCategory": "外科","code": 26, "name": "肝脏外科"}, {"id":2,"departmentCategory": "外科","code": 27, "name": "胆道外科"}, {"id":2,"departmentCategory": "外科","code": 28, "name": "胰腺外科"}, {"id":2,"departmentCategory": "外科","code": 29, "name": "血管外科"}, {"id":2,"departmentCategory": "外科","code": 30, "name": "眼科"}, {"id":2,"departmentCategory": "外科","code": 31, "name": "耳鼻咽喉-头颈外科"}, {"id":2,"departmentCategory": "外科","code": 32, "name": "麻醉科"}, {"id":2,"departmentCategory": "外科","code": 33, "name": "疼痛科"}, {"id":3,"departmentCategory": "医技科室","code": 34, "name": "放射科"}, {"id":3,"departmentCategory": "医技科室","code": 35, "name": "核医学科"}, {"id":3,"departmentCategory": "医技科室","code": 36, "name": "超声诊断科"}, {"id":3,"departmentCategory": "医技科室","code": 37, "name": "病理科"}, {"id":3,"departmentCategory": "医技科室","code": 38, "name": "检验科"}, {"id":3,"departmentCategory": "医技科室","code": 39, "name": "神经生物检测中心"}, {"id":3,"departmentCategory": "医技科室","code": 40, "name": "放射物理技术中心"}, {"id":3,"departmentCategory": "医技科室","code": 41, "name": "药剂科"}, {"id":4,"departmentCategory": "科研","code": 42, "name": "科研基地科"}, {"id":4,"departmentCategory": "科研","code": 43, "name": "疾病遗传资源中心"}, {"id":4,"departmentCategory": "科研","code": 44, "name": "动物实验中心"}, {"id":4,"departmentCategory": "科研","code": 45, "name": "临床药理基地"}, {"id":4,"departmentCategory": "科研","code": 46, "name": "肿瘤生物治疗研究室"}, {"id":4,"departmentCategory": "科研","code": 47, "name": "医学遗传研究室"}, {"id":4,"departmentCategory": "科研","code": 48, "name": "干细胞生物学研究室"}, {"id":4,"departmentCategory": "科研","code": 49, "name": "分子医学研究中心"}, {"id":4,"departmentCategory": "科研","code": 50, "name": "呼吸病学研究室"},{"id":4,"departmentCategory": "科研","code": 51, "name": "精神医学研究室"}, {"id":4,"departmentCategory": "科研","code": 52, "name": "人类疾病相关多肽研究室"}, {"id":4,"departmentCategory": "科研","code": 53, "name": "病理研究室A区"}, {"id":4,"departmentCategory": "科研","code": 54, "name": "干细胞与组织工程研究室"}, {"id":4,"departmentCategory": "科研","code": 55, "name": "实验肿瘤研究室"}, {"id":4,"departmentCategory": "科研","code": 56, "name": "老年医学研究室"}, {"id":4,"departmentCategory": "科研","code": 57, "name": "肿瘤分子诊断研究室"}, {"id":4,"departmentCategory": "科研","code": 58, "name": "信号转导及分子靶向治疗研究室"}, {"id":4,"departmentCategory": "科研","code": 59, "name": "癌基因研究室"}, {"id":4,"departmentCategory": "科研","code": 60, "name": "新药安全性评价中心"},{"id":4,"departmentCategory": "科研","code": 61, "name": "灵长类疾病动物模型研究室"}, {"id":4,"departmentCategory": "科研","code": 62, "name": "眼科学研究室"}, {"id":4,"departmentCategory": "科研","code": 63, "name": "神经发育与代谢研究室"}, {"id":4,"departmentCategory": "科研","code": 64, "name": "再生医学研究中心"}, {"id":4,"departmentCategory": "科研","code": 65, "name": "血管稳态与重塑研究室"}, {"id":4,"departmentCategory": "科研","code": 66, "name": "移植免疫研究室"}, {"id":4,"departmentCategory": "科研","code": 67, "name": "病理研究室B区"}, {"id":4,"departmentCategory": "科研","code": 68, "name": "中药药理研究室"}, {"id":4,"departmentCategory": "科研","code": 69, "name": "心血管疾病研究室"}, {"id":4,"departmentCategory": "科研","code": 70, "name": "内分泌与代谢病研究室"},{"id":4,"departmentCategory": "科研","code": 71, "name": "麻醉与危重急救研究室"}, {"id":4,"departmentCategory": "科研","code": 72, "name": "神经疾病研究室"}, {"id":4,"departmentCategory": "科研","code": 73, "name": "循证医学与流行病学研究中心"}, {"id":4,"departmentCategory": "科研","code": 74, "name": "临床研究与循证评价研究中心"}, {"id":4,"departmentCategory": "科研","code": 75, "name": "循证医学与临床流行病学教研室"}, {"id":4,"departmentCategory": "科研","code": 76, "name": "临床磁共振研究中心"}, {"id":4,"departmentCategory": "科研","code": 77, "name": "分子影像研究室"}, {"id":4,"departmentCategory": "科研","code": 78, "name": "生物膜及膜蛋白研究室"}, {"id":4,"departmentCategory": "科研","code": 79, "name": "临床药学与药品不良反应研究室"}, {"id":4,"departmentCategory": "科研","code": 80, "name": "泌尿外科研究所"},{"id":4,"departmentCategory": "科研","code": 81, "name": "血液病理研究室"}, {"id":4,"departmentCategory": "科研","code": 82, "name": "消化外科研究室"}, {"id":4,"departmentCategory": "科研","code": 83, "name": "胸外科研究室"}, {"id":4,"departmentCategory": "科研","code": 84, "name": "神经外科研究室"}, {"id":4,"departmentCategory": "科研","code": 85, "name": "肾脏病研究室"}, {"id":4,"departmentCategory": "科研","code": 86, "name": "男科学研究室"}, {"id":4,"departmentCategory": "科研","code": 87, "name": "临床影像药物研究室"}, {"id":4,"departmentCategory": "科研","code": 88, "name": "感染性疾病研究室"}, {"id":4,"departmentCategory": "科研","code": 89, "name": "核医学研究室"}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (2, 'block_biaoshi',
                                                                            '[{"code": 1, "name": "常规"}, {"code": 2, "name": "重补取"}, {"code": 3, "name": "脱钙"}, {"code": 4, "name": "冰冻"}, {"code": 5, "name": "冻后"}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (3, 'count_type', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (4, 'block_unit', '[{"code": 1, "name": "块"}, {"code": 2, "name": "堆"}]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (5, 'reject_reason',
                                                                            '[{"code": 1, "name": "样本数量不全"}, {"code": 2, "name": "样本信息与申请单信息不匹配"},{"code": 3, "name": "固定液泄露"},{"code": 4, "name": "样本缺失"}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (6, 'embed_remark', '[{"code": 1, "name": "组织缺失"}, {"code": 2, "name": "组织数量与取材数量不匹配"}]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (7, 'section_remark', '[{"code": 1, "name": "组织包含线头等杂物"}, {"code": 2, "name": "组织脱水不完全"}]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (8, 'tracking_list',
                                                                            '[{"checked":true,"code":1,"name":"病理申请","operation":[],"status":[]},{"checked":true,"code":2,"name":"登记","operation":[],"status":[]},{"checked":true,"code":3,"name":"取材","operation":[1,2],"status":[10,11]},{"checked":true,"code":4,"name":"脱水","operation":[3,4],"status":[12,13]},{"checked":true,"code":5,"name":"包埋","operation":[5],"status":[14]},{"checked":true,"code":6,"name":"切片","operation":[6],"status":[15]},{"checked":true,"code":7,"name":"染色","operation":[7,22],"status":[16,17]},{"checked":true,"code":8,"name":"制片确认","operation":[8,9],"status":[18,19]},{"checked":true,"code":9,"name":"病理诊断","operation":[10,11,12,13],"status":[20,21,22,23,24,25]},{"checked":true,"code":10,"name":"报告","operation":[],"status":[]},{"checked":true,"code":11,"name":"存档","operation":[14],"status":[28,29]}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (9, 'tracking_status', '[10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,28,29,30]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (10, 'inspect_category',
                                                                            '[{"code":1,"letter":"Z","typeDesc":"常规"},{"code":2,"letter":"Q","typeDesc":"加快"},{"code":3,"letter":"F","typeDesc":"冷冻"},{"code":4,"letter":"A","typeDesc":"尸解"},{"code":5,"letter":"C","typeDesc":"细胞学"},{"code":6,"letter":"N","typeDesc":"细针"},{"code":7,"letter":"T","typeDesc":"体检"},{"code":8,"letter":"H","typeDesc":"会诊"},{"code":9,"letter":"G","typeDesc":"肝穿"},{"code":10,"letter":"K","typeDesc":"肾穿"},{"code":11,"letter":"B","typeDesc":"骨髓"},{"code":12,"letter":"L","typeDesc":"淋巴"},{"code":13,"letter":"E","typeDesc":"眼科"},{"code":14,"letter":"M","typeDesc":"肌肉"},{"code":15,"letter":"P","typeDesc":"前列腺"},{"code":16,"letter":"S","typeDesc":"ESD"}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (11, 'tracking_operation', '[1,2,3,4,5,6,7,22,8,9,10,11,12,13,14]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (13, 'application_required',
                                                                            '[{"code":1,"name":"patientName","description":"病人姓名","required":true},{"code":2,"name":"sex","description":"性别","required":true}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (14, 'special_dye', '[{"code": -1, "name": "白片"},{"code": 0, "name": "HE"},{"code": 1, "name": "免疫组化"}]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (15, 'report_deadline', '7', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (16, 'query_time_range',
                                                                            '[{"code": 1, "name": "6个月内","checked":true},{"code": 2, "name": "1年内","checked":false}, {"code": 3, "name": "3年内","checked":false}, {"code": 4, "name": "5年内","checked":false}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (17, 'prepare_grossing_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (18, 'prepare_grossing_confirm_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (19, 'prepare_dehydrate_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (20, 'prepare_embed_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (21, 'prepare_section_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (22, 'prepare_Dye_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (23, 'prepare_confirm_alarm', '8', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (24, 'freeze_report_deadline', '7', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (25, 'ihc_report_deadline', '7', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (26, 'special_dye_report_deadline', '7', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (27, 'notification_task', '0', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (28, 'print_type', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (29, 'print_count', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (30, 'grossing_confirm_photo', '1', 1, 1); #1-必须 2-可选
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (31, 'application_default', '1', 1, 1); #0-科研 1-临床
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (32, 'grossing_note', '[{"code": 1, "name": "包"}, {"code": 2, "name": "包全"}]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (33, 'freeze_tracking_list',
                                                                            '[{"checked":true,"code":1,"name":"病理申请","operation":[],"status":[]},{"checked":true,"code":2,"name":"登记","operation":[],"status":[]},{"checked":true,"code":3,"name":"取材","operation":[1,2],"status":[10,11]},{"checked":true,"code":4,"name":"脱水","operation":[3,4],"status":[12,13]},{"checked":true,"code":5,"name":"包埋","operation":[5],"status":[14]},{"checked":true,"code":6,"name":"切片","operation":[6],"status":[15]},{"checked":true,"code":7,"name":"染色","operation":[7,22],"status":[16,17]},{"checked":true,"code":8,"name":"制片确认","operation":[8,9],"status":[18,19]},{"checked":true,"code":9,"name":"病理诊断","operation":[10,11,12,13],"status":[20,21,22,23,24,25]},{"checked":true,"code":10,"name":"报告","operation":[],"status":[]},{"checked":true,"code":11,"name":"存档","operation":[14],"status":[28,29]}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (34, 'freeze_tracking_status', '[10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,28,29,30]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (35, 'freeze_tracking_operation', '[1,2,3,4,5,6,7,22,8,9,10,11,12,13,14]', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (36, 'hospital', 'xiehe', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (37, 'sample_category',
                                                                            '[{"code": 1, "name": "大样本"}, {"code": 2, "name": "小样本"},{"code": 3, "name": "亚专科一"},{"code": 4, "name": "亚专科二"}]',
                                                                            1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (39, 'path_no_rule', '{"letter":0,"time":"yyyy","digit":5}', 1, 1);
/**
letter 表示病理字母位数 0-表示不使用字母 其中字母为系统检查类别设置的字母
time 表示年份 yyyy表示四位数年 如2017 yy表示两位年 如17
digit 表示后面数字位数 5表示5位数字
病理号所有元素长度相加等于9
 */

INSERT INTO param_setting (id, `key`, content, create_by, update_by)
  VALUE (40, 'using_frozen', '0', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (41, 'frozen_count_type', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (42, 'print_frozen', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (43, 'consult_report_deadline', '7', 1, 1);

#所有仪器设备
DROP TABLE IF EXISTS `instrument`;
CREATE TABLE `instrument` (
  `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `type`            INT(11)      NOT NULL, #设备类型
  `name`            VARCHAR(20)  NOT NULL, #设备名
  `sn`              VARCHAR(255) NOT NULL, #序列号
  `in_use`          BIT(1)                DEFAULT b'0', #是否在使用
  `description`     TEXT                  DEFAULT NULL, #描述
  `last_heartbreak` BIGINT(20)            DEFAULT NULL, #最后一次规律性反馈时间
  `disabled`        BIT(1)                DEFAULT b'0', #是否禁用
  `status`          INT(11)      NOT NULL, #状态
  `create_by`       BIGINT(20)   NOT NULL,
  `update_by`       BIGINT(20)            DEFAULT NULL,
  `create_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `instrument`
  ADD UNIQUE `index_sn_name` USING BTREE (`type`, `sn`);
ALTER TABLE `instrument`
  ADD UNIQUE `index_type_name` USING BTREE (`type`, `name`);

#脱水机
DROP TABLE IF EXISTS `dehydrator`;
CREATE TABLE `dehydrator` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `instrument_id` BIGINT(20) NOT NULL, #instrument表主键
  `status`        INT(11)    NOT NULL, #运行状态
  `used_block`    INT(11)    NOT NULL, #已用容量
  `capacity`      INT(11)    NOT NULL, #脱水机容量
  `reset_time`    DATETIME            DEFAULT NULL, #最后一次重置报警信息时间
  `start_time`    DATETIME            DEFAULT NULL, #开始时间
  `end_time`      DATETIME            DEFAULT NULL, #预计结束时间
  `create_by`     BIGINT(20) NOT NULL,
  `update_by`     BIGINT(20)          DEFAULT NULL,
  `create_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `dehydrator`
  ADD UNIQUE `index_instrumentid` USING BTREE (`instrument_id`)
  COMMENT '';

#设备事件
DROP TABLE IF EXISTS `instrument_event`;
CREATE TABLE `instrument_event` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `instrument_id` BIGINT(20)   NOT NULL, #instrument表主键
  `level`         INT(11)      NOT NULL, #错误级别
  `occur_time`    DATETIME     NOT NULL, #事件发生时间
  `msg`           VARCHAR(255) NOT NULL, #事件信息
  `status`        BIT(1)       NOT NULL DEFAULT b'1', #事件是否解除 默认未解除
  `create_by`     BIGINT(20)   NOT NULL,
  `update_by`     BIGINT(20)            DEFAULT NULL,
  `create_time`   DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `instrument_event`
  ADD INDEX `index_instrumentid` USING BTREE (`instrument_id`);
ALTER TABLE `instrument_event`
  ADD INDEX `index_occur_instrumentid` USING BTREE (`occur_time`, `instrument_id`);

#设备消息代码对照
DROP TABLE IF EXISTS `instrument_event_code`;
CREATE TABLE `instrument_event_code` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `instrument_id` BIGINT(20)   NOT NULL, #instrument表主键
  `code`          VARCHAR(20)  NOT NULL, #时间代码
  `msg`           VARCHAR(255) NOT NULL, #事件信息
  `level`         INT(11)      NOT NULL, #事件等级
  `create_by`     BIGINT(20)   NOT NULL,
  `update_by`     BIGINT(20)            DEFAULT NULL,
  `create_time`   DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `instrument_event_code`
  ADD UNIQUE `index_instrumentid_code` USING BTREE (`instrument_id`, `code`);

#常用模板设置
DROP TABLE IF EXISTS `template_setting`;
CREATE TABLE `template_setting` (
  `template_id` INT(11), #模板ID
  `operator_id` BIGINT(20), #记录员
  `position`    VARCHAR(4) NOT NULL, #模板位置（取材的相关模板、）
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`template_id`, `operator_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#文件存储
DROP TABLE IF EXISTS `pathology_file`;
CREATE TABLE `pathology_file` (
  `id`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `pathology_id` BIGINT(20)   NOT NULL,
  `operation`    INT(11)      NOT NULL,
  `type`         INT(11)      NOT NULL,
  `content`      VARCHAR(255) NOT NULL,
  `keep_flag`    BIT(1)                DEFAULT b'1', #保存标志
  `tag`          VARCHAR(30)  DEFAULT NULL COMMENT '诊断图片tag',
  `create_by`    BIGINT(20)   NOT NULL,
  `update_by`    BIGINT(20)            DEFAULT NULL,
  `create_time`  DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `pathology_file`
  ADD INDEX `index_pathology_id` USING BTREE (`pathology_id`);
ALTER TABLE `pathology_file`
  ADD INDEX `index_operation` USING BTREE (`operation`);

#系统消息
DROP TABLE IF EXISTS `system_notification`;
CREATE TABLE `system_notification` (
  `id`              BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `subject`         VARCHAR(50)   NOT NULL, #主题
  `source`          INT(11)       NOT NULL, #消息来源（1-取材，path_id为病理ID, 此时还没有block, block_id为null, 非1-消息来源-block_id为block表ID）
  `content`         VARCHAR(1024) NULL, #消息内容
  `note`            VARCHAR(1024) NULL, #备注信息
  `type`            INT(11)       NULL, #消息类型（1-需要选择处理方式， 2-不需要选择处理方式）
  `path_id`         BIGINT(20)    NULL, #
  `block_id`        BIGINT(20)    NULL, #
  `block_status`    INT(11)       NULL, #发消息时腊块的状态
  `abnormal_handle` INT(11)       NULL, #消息处理方式（1-重补取，2-重切， 3-异常终止， 4-通知技术人员）
  `read_flag`       BIT(1)                 DEFAULT b'0', #已读标志
  `receiver_type`   INT(11)       NOT NULL, #接收人类型（1-特定用户接收，receiver_id为接收人ID, 2-特定权限用户接收，receiver_id为权限）
  `receiver_id`     BIGINT(20)    NOT NULL, #接收人, 对借阅超时系统消息，存借阅ID
  `create_by`       BIGINT(20)    NOT NULL, #发送人
  `update_by`       BIGINT(20)             DEFAULT NULL,
  `create_time`     DATETIME               DEFAULT CURRENT_TIMESTAMP, #发送时间
  `update_time`     DATETIME               DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `system_notification`
  ADD INDEX `index_block_status` USING BTREE (`block_status`);

ALTER TABLE `system_notification`
  ADD INDEX `index_source` USING BTREE (`source`);

CREATE FULLTEXT INDEX index_subject
  ON system_notification (`subject`) WITH PARSER ngram;

#系统消息
DROP TABLE IF EXISTS `system_notification_receiver`;
CREATE TABLE `system_notification_receiver` (
  `notification_id` BIGINT(20) NOT NULL,
  `receiver_id`     BIGINT(20) NOT NULL, #接收人
  `read_flag`       BIT(1)   DEFAULT b'0', #已读标志
  `read_time`       DATETIME DEFAULT NULL, #读取时间
  PRIMARY KEY (`notification_id`, `receiver_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `system_notification_receiver`
  ADD INDEX `index_receiver_id` USING BTREE (`receiver_id`);

-- ----------------------------
-- Table structure for block_score
-- ----------------------------
DROP TABLE IF EXISTS `block_score`;
CREATE TABLE `block_score` (
  `parent_id`   BIGINT(20)   NOT NULL,
  `block_id`    BIGINT(20)   NOT NULL,
  `type`        INT(11)      NULL
  COMMENT '类别 1-诊断打分 2-制片确认打分',
  `average`     FLOAT(11, 2) NOT NULL,
  `grossing`    FLOAT(11, 2) NOT NULL,
  `dehydrate`   FLOAT(11, 2) NOT NULL,
  `embedding`   FLOAT(11, 2) NOT NULL,
  `sectioning`  FLOAT(11, 2) NOT NULL,
  `staining`    FLOAT(11, 2) NOT NULL,
  `sealing`     FLOAT(11, 2) NOT NULL,
  `note`        VARCHAR(255) DEFAULT NULL,
  `create_by`   BIGINT(20)   NOT NULL,
  `update_by`   BIGINT(20)   DEFAULT NULL,
  `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`block_id`, `type`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#病理的track
DROP TABLE IF EXISTS `path_tracking`;
CREATE TABLE `path_tracking` (
  `id`              BIGINT(20) NOT NULL AUTO_INCREMENT,
  `path_id`         BIGINT(20) NOT NULL, #
  `operation`       INT(11)    NOT NULL, #操作
  `operator_id`     BIGINT(20) NOT NULL, #操作人
  `sec_operator_id` BIGINT(20)          DEFAULT NULL, #第二操作人
  `operation_time`  DATETIME   NOT NULL, #操作时间
  `note`            LONGTEXT            DEFAULT NULL, #备注
  `note_type`       VARCHAR(255)        DEFAULT NULL, #备注类别
  `create_by`       BIGINT(20) NOT NULL,
  `update_by`       BIGINT(20)          DEFAULT NULL,
  `create_time`     DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `path_tracking`
  ADD INDEX `index_path_id` USING BTREE (`path_id`);
ALTER TABLE `path_tracking`
  ADD INDEX `index_operation` USING BTREE (`operation`);

#制片扫码玻片结果
DROP TABLE IF EXISTS `block_scan`;
CREATE TABLE `block_scan` (
  `slide_id`     BIGINT(20) NOT NULL
  COMMENT '玻片ID',
  `batch_id`     BIGINT(20) NOT NULL
  COMMENT '批次ID',
  `path_id`      BIGINT(20) NOT NULL
  COMMENT '病理ID',
  `block_id`     BIGINT(20) NOT NULL
  COMMENT '腊块ID',
  `path_no`      CHAR(15)    DEFAULT NULL
  COMMENT '病理号',
  `patient_name` CHAR(15)    DEFAULT NULL
  COMMENT '病人姓名',
  `block_sub_id` VARCHAR(3) NOT NULL
  COMMENT '腊块号',
  `biaoshi`      INT(11)     DEFAULT NULL
  COMMENT '取材标识，1，常规，2，重取，3 脱钙',
  `marker`       VARCHAR(30) DEFAULT NULL
  COMMENT '标记物',
  `special_dye`  INT(11)     DEFAULT NULL
  COMMENT '特染类别  0-HE染色, 1-免疫组化, null-白片',
  `sub_id`       VARCHAR(3) NOT NULL
  COMMENT '玻片号',
  `create_time`  DATETIME    DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`slide_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `block_scan`
  ADD INDEX `index_batch_id` USING BTREE (`batch_id`);

#制片扫码图片结果
DROP TABLE IF EXISTS `block_scan_image`;
CREATE TABLE `block_scan_image` (
  `batch_id`      BIGINT(20)    NOT NULL AUTO_INCREMENT
  COMMENT '批次ID',
  `scan_location` INT(11)       NOT NULL
  COMMENT '扫描位置',
  `image_path`    VARCHAR(2048) NULL
  COMMENT '图片路径',
  `image_name`    VARCHAR(2048) NULL
  COMMENT '文件名',
  `image_size`    INT(11)       NULL
  COMMENT '文件大小',
  `error_blocks`  TEXT          NULL
  COMMENT '异常玻片',
  `create_time`   DATETIME               DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`batch_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#冰冻预约
DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
  `id`             BIGINT(20) NOT NULL AUTO_INCREMENT,
  `start_time`     DATETIME            DEFAULT NULL, #预约的开始时间
  `end_time`       DATETIME            DEFAULT NULL, #预约的结束时间
  `time_flag`      VARCHAR(100)        DEFAULT NULL, #标识时间段的, 0代表0点到0点半,1代表0:30~1:00
  `booking_pid`    BIGINT(20)          DEFAULT NULL, #预约人id
  `application_id` BIGINT(20)          DEFAULT NULL, #申请ID
  `booking_person` VARCHAR(20)         DEFAULT NULL, #预约人
  `booking_phone`  VARCHAR(18)         DEFAULT NULL, #预约人电话
  `create_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `instrument_id`  INT(11)             DEFAULT 1,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `booking`
  ADD INDEX `index_application_id` USING BTREE (`application_id`);

#特染申请
DROP TABLE IF EXISTS `application_ihc`;
CREATE TABLE `application_ihc` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `departments` INT(11)              DEFAULT NULL
  COMMENT '送检科室',
  `apply_user`  VARCHAR(10) NOT NULL
  COMMENT '申请人',
  `apply_tel`   VARCHAR(18) NOT NULL
  COMMENT '联系电话',
  `create_by`   BIGINT(20)  NOT NULL,
  `update_by`   BIGINT(20)           DEFAULT NULL,
  `create_time` DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#特染申请腊块
DROP TABLE IF EXISTS `block_ihc`;
CREATE TABLE `block_ihc` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ihc_id`        BIGINT(20) NOT NULL
  COMMENT '特染申请ID',
  `serial_number` CHAR(10)            DEFAULT NULL
  COMMENT '病理号',
  `number`        CHAR(10)            DEFAULT NULL
  COMMENT '特染申请号',
  `path_id`       BIGINT(20) NOT NULL
  COMMENT '病理ID',
  `sub_id`        VARCHAR(3) NOT NULL
  COMMENT '腊块号',
  `block_id`      BIGINT(20) NOT NULL
  COMMENT '腊块ID',
  `status`        INT(11)    NOT NULL
  COMMENT '状态',
  `special_dye`   INT(11)             DEFAULT NULL
  COMMENT '特染类别  0-HE染色, 1-免疫组化',
  `source`        INT(11)             DEFAULT NULL
  COMMENT '申请来源  0-特检申请工作站, 1-诊断工作站',
  `ihc_marker`    VARCHAR(1024)       DEFAULT NULL
  COMMENT '特染要求',
  `note`          VARCHAR(1024)       DEFAULT NULL
  COMMENT '备注',
  `result`        LONGTEXT            DEFAULT NULL
  COMMENT '申请特染时常规病理的诊断结果',
  `create_by`     BIGINT(20) NOT NULL,
  `update_by`     BIGINT(20)          DEFAULT NULL,
  `create_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `block_ihc`
  ADD INDEX `index_ihc_id` USING BTREE (`ihc_id`);
ALTER TABLE `block_ihc`
  ADD INDEX `index_status` USING BTREE (`status`);

#腊块或玻片归档信息
DROP TABLE IF EXISTS `block_archive`;
CREATE TABLE `block_archive` (
  `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `serial_number` CHAR(10)             DEFAULT NULL
  COMMENT '病理号',
  `path_id`       BIGINT(20)  NOT NULL
  COMMENT '病理ID',
  `block_sub_id`  VARCHAR(3)  NOT NULL
  COMMENT '腊块号',
  `block_id`      BIGINT(20)  NOT NULL
  COMMENT '腊块ID',
  `slide_id`      BIGINT(20)           DEFAULT NULL
  COMMENT '玻片ID',
  `slide_sub_id`  VARCHAR(3)           DEFAULT NULL
  COMMENT '玻片号',
  `status`        INT(11)     NOT NULL
  COMMENT '状态，0-晾片 1-存档 2-借出',
  `ihc_marker`    VARCHAR(30)          DEFAULT NULL
  COMMENT '试剂抗体',
  `patient_name`  VARCHAR(30)          DEFAULT NULL
  COMMENT '病人姓名',
  `archive_box`   VARCHAR(32) NOT NULL
  COMMENT '抽屉编号',
  `archive_index` VARCHAR(10)          DEFAULT NULL
  COMMENT '蜡块位置',
  `create_by`     BIGINT(20)  NOT NULL,
  `update_by`     BIGINT(20)           DEFAULT NULL,
  `create_time`   DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `block_archive`
  ADD INDEX `index_path_id` USING BTREE (`path_id`);
ALTER TABLE `block_archive`
  ADD INDEX `index_block_id` USING BTREE (`block_id`);
ALTER TABLE `block_archive`
  ADD INDEX `index_slide_id` USING BTREE (`slide_id`);

#腊块或玻片借阅归还信息
DROP TABLE IF EXISTS `block_borrow`;
CREATE TABLE `block_borrow` (
  `id`             BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `archive_id`     BIGINT(20)  NOT NULL
  COMMENT '归档ID',
  `borrow_name`    VARCHAR(50) NOT NULL
  COMMENT '姓名',
  `borrow_phone`   VARCHAR(20) NOT NULL
  COMMENT '联系电话',
  `id_number`      VARCHAR(20) NOT NULL
  COMMENT '身份证号',
  `cash_pledge`    VARCHAR(20)          DEFAULT NULL
  COMMENT '押金费',
  `plan_back`      DATETIME    NOT NULL
  COMMENT '计划归还日期',
  `real_back`      DATETIME    NULL
  COMMENT '实际归还日期',
  `borrow_type`    INT(11)     NOT NULL
  COMMENT '借阅人类别',
  `tutor`          VARCHAR(50)          DEFAULT NULL, #导师
  `unit`           VARCHAR(50)          DEFAULT NULL, #单位
  `note`           VARCHAR(50)          DEFAULT NULL, #备注
  `departments`    INT(11)              DEFAULT NULL, #科室
  `status`         INT(11)     NOT NULL
  COMMENT '状态 1-未归还，2-已归还',
  `archive_status` INT(11)     NOT NULL
  COMMENT '借阅时蜡块或玻片存档状态',
  `create_by`      BIGINT(20)  NOT NULL,
  `update_by`      BIGINT(20)           DEFAULT NULL,
  `create_time`    DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time`    DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE `block_borrow`
  ADD INDEX `index_archive_id` USING BTREE (`archive_id`);

#显微镜使用记录
DROP TABLE IF EXISTS `microscope_tracking`;
CREATE TABLE `microscope_tracking` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `start_time`    DATETIME            DEFAULT NULL
  COMMENT '开始使用时间',
  `end_time`      DATETIME            DEFAULT NULL
  COMMENT '结束使用时间',
  `booking_pid`   BIGINT(20)          DEFAULT NULL
  COMMENT '使用人ID',
  `microscope_id` INT(11)             DEFAULT 1
  COMMENT '显微镜ID',
  `microscope`    VARCHAR(50)         DEFAULT NULL
  COMMENT '显微镜',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

##sequence记录表
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE sequence (
  seqname      VARCHAR(50) NOT NULL
  COMMENT 'sequence名称',
  currentValue INT         NOT NULL
  COMMENT '当前sequence值',
  increment    INT         NOT NULL DEFAULT 1
  COMMENT '增长系数',
  PRIMARY KEY (seqname)
)
  ENGINE = InnoDB;

##collect 收藏表
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `target_id`   BIGINT(20)  NOT NULL
  COMMENT '病理ID或者特殊申请Id',
  `category`    INT(11)     NOT NULL
  COMMENT '1 常规申请 2特殊申请',
  `label`       VARCHAR(30) NOT NULL
  COMMENT '标签',
  `permission`  INT(11)     NOT NULL
  COMMENT '权限',
  `note`        TEXT                 DEFAULT NULL
  COMMENT '备注',
  `create_by`   BIGINT(20)  NOT NULL,
  `update_by`   BIGINT(20)           DEFAULT NULL,
  `create_time` DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

##冰冻号
INSERT INTO sequence VALUES ('frozen_number', 0, 1);
##免疫组化号
INSERT INTO sequence VALUES ('ihc_number', 0, 1);
##特染号
INSERT INTO sequence VALUES ('dye_number', 0, 1);
##会诊号
INSERT INTO sequence VALUES ('consult_number', 0, 1);

#特殊申请
DROP TABLE IF EXISTS `special_application`;
CREATE TABLE `special_application` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cause_id`           BIGINT(20) NOT NULL
  COMMENT '产生此申请的id,为病理ID或蜡块ID',
  `path_no`            CHAR(10)   NOT NULL
  COMMENT '病理号',
  `number`             CHAR(10)   NOT NULL
  COMMENT '特殊申请号',
  `type`               INT(11)    NOT NULL
  COMMENT '特殊申请类别',
  `note`               VARCHAR(50)         DEFAULT NULL, #备注
  `bingdong_note`      TEXT                DEFAULT NULL
  COMMENT '冰冻描述',
  `diagnose`           LONGTEXT            DEFAULT NULL
  COMMENT '诊断结果内容',
  `result`             LONGTEXT            DEFAULT NULL
  COMMENT '诊断结果DOM结构',
  `special_result`     LONGTEXT            DEFAULT NULL
  COMMENT '特检结果',
  `out_consult_result` LONGTEXT            DEFAULT NULL
  COMMENT '外院会诊结果',
  `status`             INT(11)             DEFAULT NULL
  COMMENT '状态',
  `report_pic`         LONGTEXT            DEFAULT NULL
  COMMENT '报告图片',
  `out_consult`        BIT(1)              DEFAULT 0
  COMMENT '是否外院会诊',
  `assign_diagnose`    BIGINT(20)          DEFAULT NULL
  COMMENT '指定诊断医生',
  `assign_grossing`    BIGINT(20)          DEFAULT NULL
  COMMENT '指定取材医生',
  `create_by`          BIGINT(20) NOT NULL,
  `update_by`          BIGINT(20)          DEFAULT NULL,
  `create_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#取材操作tracking operator_name字段 记录有误 用以下sql调整
UPDATE tracking
SET operator_name = (SELECT first_name
                     FROM user
                     WHERE id = operator_id);

##南京口腔医院 报告模板
DELETE FROM template
WHERE id = 1;
INSERT INTO `template` (`id`, `name`, `category`, `parent`, `level`, `content`, `position`, `display_order`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES ('1', '报告模板', '1', '0', '1',
             '{\"imageURL\":\"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAEAAWQDASIAAhEBAxEB/8QAHAABAAIDAQEBAAAAAAAAAAAAAAYHBAUIAgMB/8QAQxAAAQQCAgECBAMEBQoFBQAAAQACAwQFEQYSIQcxEyJBURQycRVhgZEIFiM2oSRCUmKCg7Gys8EzNDV00ThjcnWE/8QAGgEBAAMBAQEAAAAAAAAAAAAAAAIDBAUBBv/EADARAQACAQIDBQYGAwAAAAAAAAABAgMEERIhMQUTQWGBIjJCUcHwFHFyobHRMzSR/9oADAMBAAIRAxEAPwC/kREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREJABJOgPqgIodnuXhofUxjvmB06x9P8AZ+/6/wAvoVtuRV8tMys/EveJI3kva2QN39tg+CPHsVl/F0txcEcXDt0+jT+GtHDx8uL5t2igzs7ybGtlNur8RrT5klgPVv8AtN0Fl1+eQOJFmjIwa8GN4fs/odKFe0MO+1t6z5wnOhzbb12mPKUuRaetyjD2SxotiN7hvrK0t1+p9v8AFbSGxDZj+JBNHKzeu0bg4fzC00y0v7kxLNfHenvRMPoiIrEBERARY/4+n+0f2f8Ai4Px3wvj/hviD4nw99e/X3678b9trIQEREBF85LEMMkMcs0b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\u003d\",\"reportBigName\":\"南京市口腔医院\",\"subheading\":\"南京大学医学附属口腔医院\",\"reportSmallName\":\"病理检查报告单\",\"patientInformation\":[{\"name\":\"姓名\",\"check\":true},{\"name\":\"年龄\",\"check\":true},{\"name\":\"性别\",\"check\":true},{\"name\":\"病人ID\",\"check\":true},{\"name\":\"登记/住院号\",\"check\":true},{\"name\":\"病理号\",\"check\":true},{\"name\":\"门诊号\",\"check\":true},{\"name\":\"病区\",\"check\":true},{\"name\":\"床号\",\"check\":true},{\"name\":\"送检医院\",\"check\":true},{\"name\":\"送检日期\",\"check\":true},{\"name\":\"送检科室\",\"check\":true},{\"name\":\"送检医生\",\"check\":true},{\"name\":\"检查部位\",\"check\":true},{\"name\":\"临床诊断\",\"check\":true}],\"diagnosticContent\":[{\"name\":\"巨检所见\",\"check\":true},{\"name\":\"镜下所见\",\"check\":true},{\"name\":\"病理诊断\",\"check\":true}],\"diagnosticInformation\":[{\"name\":\"诊断医师\",\"check\":true},{\"name\":\"诊断时间\",\"check\":true},{\"name\":\"审核医师\",\"check\":true},{\"name\":\"报告打印者\",\"check\":true},{\"name\":\"报告时间\",\"check\":true}],\"hospitalInformation\":\"\",\"specialTip\":\"\"}',
             '4', '1', '1', '1', NOW(), NOW());

#会诊表
DROP TABLE IF EXISTS `consult`;
CREATE TABLE `consult` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `number`             CHAR(10)   NOT NULL
  COMMENT '会诊号',
  `application_id`     BIGINT(20) NOT NULL
  COMMENT '申请ID',
  `result`             LONGTEXT            DEFAULT NULL
  COMMENT '诊断结果',
  `diagnose`           TEXT                DEFAULT NULL
  COMMENT '诊断描述',
  `status`             INT(11)             DEFAULT 20
  COMMENT '状态',
  `patient_name`       VARCHAR(30)         DEFAULT NULL
  COMMENT '病人姓名',
  `departments`        INT(11)             DEFAULT NULL
  COMMENT '送检科室',
  `hospital`           INT(11)    NOT NULL
  COMMENT '送检医院',
  `requirement`        TEXT                DEFAULT NULL
  COMMENT '会诊目的和要求',
  `note`               TEXT                DEFAULT NULL
  COMMENT '备注',
  `consult_result`     LONGTEXT            DEFAULT NULL
  COMMENT '会诊意见',
  `out_consult`        BIT(1)              DEFAULT 0
  COMMENT '是否外院会诊',
  `out_consult_result` LONGTEXT            DEFAULT NULL
  COMMENT '外院会诊意见',
  `report_pic`         LONGTEXT            DEFAULT NULL
  COMMENT '报告图片',
  `assign_diagnose`    BIGINT(20)          DEFAULT NULL
  COMMENT '指定诊断医生',
  `create_by`          BIGINT(20) NOT NULL,
  `update_by`          BIGINT(20)          DEFAULT NULL,
  `create_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  `update_time`        DATETIME            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for ihc_marker_index
-- ----------------------------
DROP TABLE IF EXISTS `ihc_marker_index`;
CREATE TABLE `ihc_marker_index` (
  `seq` INT(3) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


/**
此表用于分隔特检申请标记物
 */
-- ----------------------------
-- Records of ihc_marker_index
-- ----------------------------
INSERT INTO `ihc_marker_index` VALUES ('0');
INSERT INTO `ihc_marker_index` VALUES ('1');
INSERT INTO `ihc_marker_index` VALUES ('2');
INSERT INTO `ihc_marker_index` VALUES ('3');
INSERT INTO `ihc_marker_index` VALUES ('4');
INSERT INTO `ihc_marker_index` VALUES ('5');
INSERT INTO `ihc_marker_index` VALUES ('6');
INSERT INTO `ihc_marker_index` VALUES ('7');
INSERT INTO `ihc_marker_index` VALUES ('8');
INSERT INTO `ihc_marker_index` VALUES ('9');
INSERT INTO `ihc_marker_index` VALUES ('10');
INSERT INTO `ihc_marker_index` VALUES ('11');
INSERT INTO `ihc_marker_index` VALUES ('12');
INSERT INTO `ihc_marker_index` VALUES ('13');
INSERT INTO `ihc_marker_index` VALUES ('14');
INSERT INTO `ihc_marker_index` VALUES ('15');
INSERT INTO `ihc_marker_index` VALUES ('16');
INSERT INTO `ihc_marker_index` VALUES ('17');
INSERT INTO `ihc_marker_index` VALUES ('18');
INSERT INTO `ihc_marker_index` VALUES ('19');
INSERT INTO `ihc_marker_index` VALUES ('20');
INSERT INTO `ihc_marker_index` VALUES ('21');
INSERT INTO `ihc_marker_index` VALUES ('22');
INSERT INTO `ihc_marker_index` VALUES ('23');
INSERT INTO `ihc_marker_index` VALUES ('24');
INSERT INTO `ihc_marker_index` VALUES ('25');
INSERT INTO `ihc_marker_index` VALUES ('26');
INSERT INTO `ihc_marker_index` VALUES ('27');
INSERT INTO `ihc_marker_index` VALUES ('28');
INSERT INTO `ihc_marker_index` VALUES ('29');
INSERT INTO `ihc_marker_index` VALUES ('30');
INSERT INTO `ihc_marker_index` VALUES ('31');
INSERT INTO `ihc_marker_index` VALUES ('32');
INSERT INTO `ihc_marker_index` VALUES ('33');
INSERT INTO `ihc_marker_index` VALUES ('34');
INSERT INTO `ihc_marker_index` VALUES ('35');
INSERT INTO `ihc_marker_index` VALUES ('36');
INSERT INTO `ihc_marker_index` VALUES ('37');
INSERT INTO `ihc_marker_index` VALUES ('38');
INSERT INTO `ihc_marker_index` VALUES ('39');
INSERT INTO `ihc_marker_index` VALUES ('40');
INSERT INTO `ihc_marker_index` VALUES ('41');
INSERT INTO `ihc_marker_index` VALUES ('42');
INSERT INTO `ihc_marker_index` VALUES ('43');
INSERT INTO `ihc_marker_index` VALUES ('44');
INSERT INTO `ihc_marker_index` VALUES ('45');
INSERT INTO `ihc_marker_index` VALUES ('46');
INSERT INTO `ihc_marker_index` VALUES ('47');
INSERT INTO `ihc_marker_index` VALUES ('48');
INSERT INTO `ihc_marker_index` VALUES ('49');
INSERT INTO `ihc_marker_index` VALUES ('50');
INSERT INTO `ihc_marker_index` VALUES ('51');
INSERT INTO `ihc_marker_index` VALUES ('52');
INSERT INTO `ihc_marker_index` VALUES ('53');
INSERT INTO `ihc_marker_index` VALUES ('54');
INSERT INTO `ihc_marker_index` VALUES ('55');
INSERT INTO `ihc_marker_index` VALUES ('56');
INSERT INTO `ihc_marker_index` VALUES ('57');
INSERT INTO `ihc_marker_index` VALUES ('58');
INSERT INTO `ihc_marker_index` VALUES ('59');
INSERT INTO `ihc_marker_index` VALUES ('60');
INSERT INTO `ihc_marker_index` VALUES ('61');
INSERT INTO `ihc_marker_index` VALUES ('62');
INSERT INTO `ihc_marker_index` VALUES ('63');
INSERT INTO `ihc_marker_index` VALUES ('64');
INSERT INTO `ihc_marker_index` VALUES ('65');
INSERT INTO `ihc_marker_index` VALUES ('66');
INSERT INTO `ihc_marker_index` VALUES ('67');
INSERT INTO `ihc_marker_index` VALUES ('68');
INSERT INTO `ihc_marker_index` VALUES ('69');
INSERT INTO `ihc_marker_index` VALUES ('70');
INSERT INTO `ihc_marker_index` VALUES ('71');
INSERT INTO `ihc_marker_index` VALUES ('72');
INSERT INTO `ihc_marker_index` VALUES ('73');
INSERT INTO `ihc_marker_index` VALUES ('74');
INSERT INTO `ihc_marker_index` VALUES ('75');
INSERT INTO `ihc_marker_index` VALUES ('76');
INSERT INTO `ihc_marker_index` VALUES ('77');
INSERT INTO `ihc_marker_index` VALUES ('78');
INSERT INTO `ihc_marker_index` VALUES ('79');
INSERT INTO `ihc_marker_index` VALUES ('80');
INSERT INTO `ihc_marker_index` VALUES ('81');
INSERT INTO `ihc_marker_index` VALUES ('82');
INSERT INTO `ihc_marker_index` VALUES ('83');
INSERT INTO `ihc_marker_index` VALUES ('84');
INSERT INTO `ihc_marker_index` VALUES ('85');
INSERT INTO `ihc_marker_index` VALUES ('86');
INSERT INTO `ihc_marker_index` VALUES ('87');
INSERT INTO `ihc_marker_index` VALUES ('88');
INSERT INTO `ihc_marker_index` VALUES ('89');
INSERT INTO `ihc_marker_index` VALUES ('90');
INSERT INTO `ihc_marker_index` VALUES ('91');
INSERT INTO `ihc_marker_index` VALUES ('92');
INSERT INTO `ihc_marker_index` VALUES ('93');
INSERT INTO `ihc_marker_index` VALUES ('94');
INSERT INTO `ihc_marker_index` VALUES ('95');
INSERT INTO `ihc_marker_index` VALUES ('96');
INSERT INTO `ihc_marker_index` VALUES ('97');
INSERT INTO `ihc_marker_index` VALUES ('98');
INSERT INTO `ihc_marker_index` VALUES ('99');

##南京口腔医院检查类别
DELETE FROM param_setting
WHERE id = 10;
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (10, 'inspect_category',
                                                                            '[{"code":1,"letter":"","typeDesc":"常规"},{"code":2,"letter":"","typeDesc":"加快"},{"code":3,"letter":"","typeDesc":"冷冻"},{"code":4,"letter":"","typeDesc":"尸解"},{"code":5,"letter":"","typeDesc":"细胞学"},{"code":6,"letter":"","typeDesc":"细针"},{"code":7,"letter":"","typeDesc":"体检"},{"code":8,"letter":"","typeDesc":"会诊"},{"code":9,"letter":"","typeDesc":"肝穿"},{"code":10,"letter":"","typeDesc":"肾穿"},{"code":11,"letter":"","typeDesc":"骨髓"},{"code":12,"letter":"","typeDesc":"淋巴"},{"code":13,"letter":"","typeDesc":"眼科"},{"code":14,"letter":"","typeDesc":"肌肉"},{"code":15,"letter":"","typeDesc":"前列腺"},{"code":16,"letter":"","typeDesc":"ESD"}]',
                                                                            1, 1);

INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (46, 'section_print_medium', '1', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (47, 'section_print_way', '1', 1, 1);

#历史数据批量更新
UPDATE application a
SET pathology_id = (SELECT id
                    FROM pathology
                    WHERE application_id = a.id);

#一个病理对应多个冰冻号 1-是 2-否
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (48, 'multi_frozen', '1', 1, 1);

INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (50, 'special_number_print', '1', 1, 1);
#添加疑难杂症/脱钙报告期限
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (51, 'difficult_deadline', '7', 1, 1);
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (52, 'decalcify_deadline', '7', 1, 1);

ALTER TABLE block_score
  ADD COLUMN `type` INT(11) NULL
COMMENT '类别 1-诊断打分 2-制片确认打分';
UPDATE block_score
SET type = 1;
ALTER TABLE block_score
  DROP PRIMARY KEY;
ALTER TABLE block_score
  ADD PRIMARY KEY (block_id, type);

ALTER TABLE pathology
  ADD COLUMN `label` INT(11) DEFAULT NULL
COMMENT '病例标注';

##reagent 试剂耗材表
DROP TABLE IF EXISTS `reagent`;
CREATE TABLE `reagent` (
  `id`                BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name`              VARCHAR(30) NOT NULL UNIQUE
  COMMENT '名称',
  `category`          INT(11)     NOT NULL
  COMMENT '类别 1-试剂 2-耗材',
  `type`              INT(11)     NOT NULL
  COMMENT '类型',
  `clone_number`      VARCHAR(30)          DEFAULT NULL
  COMMENT '克隆号',
  `pre_process`       INT(11)              DEFAULT NULL
  COMMENT '预处理',
  `positive_position` VARCHAR(50)          DEFAULT NULL
  COMMENT '阳性部位',
  `diagnose`          VARCHAR(1024)        DEFAULT NULL
  COMMENT '鉴别诊断',
  `create_by`         BIGINT(20)  NOT NULL,
  `update_by`         BIGINT(20)           DEFAULT NULL,
  `create_time`       DATETIME             DEFAULT CURRENT_TIMESTAMP,
  `update_time`       DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE name_index (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

##store 库存表
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id`                    BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `reagent_id`            BIGINT(20)   NOT NULL
  COMMENT '试剂耗材ID',
  `order_number`          VARCHAR(30)           DEFAULT NULL
  COMMENT '订单号',
  `batch_number`          VARCHAR(100) NOT NULL
  COMMENT '生产批号',
  `product_number`        VARCHAR(30)           DEFAULT NULL
  COMMENT '产品编号',
  `manufacturer`          VARCHAR(30)           DEFAULT NULL
  COMMENT '制造商',
  `article_number`        VARCHAR(100)          DEFAULT NULL
  COMMENT '货号',
  `produce_time`          DATETIME              DEFAULT NULL
  COMMENT '生产日期',
  `expiry_time`           DATETIME              DEFAULT NULL
  COMMENT '失效日期',
  `manufacturer_phone`    VARCHAR(30)           DEFAULT NULL
  COMMENT '制造商电话',
  `spec`                  VARCHAR(100)          DEFAULT NULL
  COMMENT '说明书',
  `receive_status`        INT(11)               DEFAULT NULL
  COMMENT '接受状态 ，1-合格，2-破损',
  `preparation`           VARCHAR(32)           DEFAULT NULL
  COMMENT '制备人',
  `preparation_time`      DATETIME              DEFAULT NULL
  COMMENT '制备日期',
  `pre_experiment`        VARCHAR(32)           DEFAULT NULL
  COMMENT '预实验人',
  `pre_experiment_time`   DATETIME              DEFAULT NULL
  COMMENT '试验日期',
  `pre_experiment_result` VARCHAR(1024)         DEFAULT NULL
  COMMENT '预实验结果记录',
  `specification`         DOUBLE(11, 2)         DEFAULT NULL
  COMMENT '规格',
  `type`                  INT(11)               DEFAULT NULL
  COMMENT '试剂类型 1-浓缩液 2-工作液',
  `dilution_rate_front`   DOUBLE(11, 2)         DEFAULT NULL
  COMMENT '稀释比例-前面的数值',
  `dilution_rate_rear`    DOUBLE(11, 2)         DEFAULT NULL
  COMMENT '稀释比例-后面的数值',
  `real_capacity`         DOUBLE(20, 4)         DEFAULT NULL
  COMMENT '真正计算容量  浓缩液稀释后的',
  `count`                 INT(11)      NOT NULL
  COMMENT '数量',
  `count_unit`            INT(11)      NOT NULL
  COMMENT '数量单位 1-瓶 2-盒',
  `total_capacity`        DOUBLE(20, 4)         DEFAULT NULL
  COMMENT '总量',
  `used_capacity`         DOUBLE(20, 4)         DEFAULT NULL
  COMMENT '已使用量',
  `create_by`             BIGINT(20)   NOT NULL,
  `update_by`             BIGINT(20)            DEFAULT NULL,
  `create_time`           DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`           DATETIME              DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE batch_order_reagent_index (reagent_id, order_number, batch_number)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

##reagent_record 使用记录表
DROP TABLE IF EXISTS `reagent_record`;
CREATE TABLE `reagent_record` (
  `id`          BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `store_id`    BIGINT(20)    NOT NULL
  COMMENT '库存ID',
  `dosage`      DOUBLE(20, 2) NOT NULL
  COMMENT '用量',
  `note`        VARCHAR(1024)          DEFAULT NULL
  COMMENT '使用备注',
  `create_by`   BIGINT(20)    NOT NULL,
  `update_by`   BIGINT(20)             DEFAULT NULL,
  `create_time` DATETIME               DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME               DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

##试剂使用量(单位:ml)
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (53, 'reagent_usage', '10', 1, 1);

ALTER TABLE reagent
  MODIFY COLUMN `clone_number` VARCHAR(30) DEFAULT NULL;
ALTER TABLE store
  MODIFY COLUMN `manufacturer` VARCHAR(30) DEFAULT NULL
  COMMENT '制造商';
ALTER TABLE store
  MODIFY COLUMN `preparation` VARCHAR(32) DEFAULT NULL
  COMMENT '制备人';
ALTER TABLE store
  MODIFY COLUMN `pre_experiment` VARCHAR(32) DEFAULT NULL
  COMMENT '预实验人';
ALTER TABLE store
  MODIFY COLUMN `pre_experiment_result` VARCHAR(1024) DEFAULT NULL
  COMMENT '预实验结果记录';
UPDATE store
SET used_capacity = 0;

ALTER TABLE application
  ADD COLUMN `number` CHAR(10) DEFAULT NULL
COMMENT '特殊申请号';

ALTER TABLE reagent
  MODIFY COLUMN `name` VARCHAR(30) NOT NULL UNIQUE
  COMMENT '名称';

##文件_病理Mapping表
DROP TABLE IF EXISTS `file_mapping`;
CREATE TABLE `file_mapping` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `path_id`     BIGINT(20) NOT NULL
  COMMENT '病理ID',
  `special_id`  BIGINT(20) DEFAULT NULL
  COMMENT '特殊申请病理ID',
  `file_id`     BIGINT(20) NOT NULL
  COMMENT '文件ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique` (`path_id`,`special_id`,`file_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE template MODIFY COLUMN `parent`        INT(11)    DEFAULT NULL COMMENT '模板父类别 取材/免疫组化/诊断系统模板 对应送检科室code值';

##取材模板配置
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (54, 'grossing_template', '1', 1, 1);

##诊断模板配置
INSERT INTO param_setting (id, `key`, content, create_by, update_by) VALUE (55, 'diagnose_template', '1', 1, 1);

ALTER TABLE pathology_file
  MODIFY COLUMN `tag` VARCHAR(30)  DEFAULT NULL
COMMENT '图片tag';

ALTER TABLE `special_application`
ADD INDEX `index_path_no` USING BTREE (`path_no`);

CREATE INDEX `index_content` ON pathology_file(content(15)) USING BTREE;

CREATE INDEX `index_pathology_id` ON application(pathology_id) USING BTREE;

CREATE INDEX `index_serial_number` ON block_ihc(serial_number) USING BTREE;

CREATE INDEX `index_number` ON block_ihc(number) USING BTREE;