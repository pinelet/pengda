/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013-3-30 15:34:00                           */
/*==============================================================*/


drop table if exists cardInfo;

drop table if exists cardsync;

drop table if exists commandInfo;

drop table if exists deviceInfo;

drop table if exists deviceRecord;

drop table if exists deviceWork;

drop table if exists personalInfo;

drop table if exists personrel;

drop table if exists transInfo;

/*==============================================================*/
/* Table: cardInfo                                              */
/*==============================================================*/
create table cardInfo
(
   cardcode             varchar(40) not null,
   pId                  varchar(40) not null comment '此处人员是指发卡者，即设备经营者（用户）的ID',
   cardname             varchar(20),
   phone                varchar(20),
   address              varchar(400),
   balance              decimal(5,2),
   applydate            varchar(20),
   transdate            varchar(20),
   cardstatus           char(1) comment '字典：1正常、2挂失/黑名单，0作废',
   remark               varchar(400),
   primary key (cardcode, pId)
);

/*==============================================================*/
/* Table: cardsync                                              */
/*==============================================================*/
create table cardsync
(
   ID                   varchar(40) not null,
   GPRSCode             varchar(40) not null,
   cardcode             varchar(40) not null,
   pId                  varchar(40) not null comment '此处人员是指发卡者，即设备经营者（用户）的ID',
   synctype             char(1) comment '1.黑名单  2.白名单（撤销黑名单）',
   syncresult           char(1) comment '字典：1通知成功，2通知失败，0未通知',
   recordtime           bigint,
   synctime             int,
   primary key (ID)
);

/*==============================================================*/
/* Table: commandInfo                                           */
/*==============================================================*/
create table commandInfo
(
   commandId            char(32) not null,
   pId                  varchar(40) comment '设备对应的用户ID',
   GPRSCode             varchar(40),
   commandtype          char(1),
   commandContent       varchar(300),
   syncresult           char(1),
   recordtime           bigint,
   primary key (commandId)
);

/*==============================================================*/
/* Table: deviceInfo                                            */
/*==============================================================*/
create table deviceInfo
(
   GPRSCode             varchar(40) not null,
   deviceCode           varchar(20) comment '同一个客户下的设备编号唯一',
   pId                  varchar(40) comment '此处人员是指发卡者，即设备经营者（用户）的ID',
   phoneCode            varchar(15) not null,
   Model                varchar(40) comment '数据字典',
   contactCall          varchar(20),
   Address              varchar(200),
   ppDate               varchar(10),
   carbonDate           varchar(10),
   roDate               varchar(10),
   mineDate             varchar(10),
   deviceStatus         varchar(2) comment '0.停用 1.启动（根据报文来订）',
   status               char(1) comment '是否可以使用平台服务的状态：1.正常服务  0.关闭服务',
   deadline             varchar(10),
   remark               varchar(200),
   primary key (GPRSCode)
);

/*==============================================================*/
/* Table: deviceRecord                                          */
/*==============================================================*/
create table deviceRecord
(
   GPRSCode             varchar(40) not null,
   recordType           char(1) not null comment 'D预警类 F 监测类 G 运行类 ',
   devicetemp           varchar(10) comment 'CARDCOM  LOWPR   HIGHPR  LOWPO  MAKEW  OFFS  ONS(报警类)
            BOXTEM  TDSEL  PH(监测类)
            MN mm LL GU c1 c2 b c y wh Op OT oV cp hp yp cM pT cT  LO LC FU YU tl(运行类)',
   tdsel                varchar(10),
   ph                   varchar(10),
   recordTime           varchar(20) not null
);

alter table deviceRecord comment '针对设备信息建立的纵表';

/*==============================================================*/
/* Table: deviceWork                                            */
/*==============================================================*/
create table deviceWork
(
   GPRSCode             varchar(40) not null,
   cardcom              bool,
   lowpressure          bool,
   highposition         bool,
   lowposition          bool,
   makewater            bool,
   offsale              bool,
   onsale               bool,
   MN                   varchar(1),
   mm                   varchar(1),
   LL                   varchar(1),
   GU                   varchar(1),
   c1                   varchar(5),
   c2                   varchar(5),
   b                    varchar(8),
   c                    varchar(8),
   y                    varchar(8),
   wh                   varchar(5),
   Op                   varchar(5),
   OT                   varchar(5),
   oV                   varchar(5),
   cp                   varchar(5),
   hp                   varchar(5),
   yp                   varchar(5),
   cM                   varchar(5),
   pT                   varchar(5),
   cT                   varchar(5),
   LO                   varchar(5),
   LC                   varchar(5),
   FU                   varchar(1),
   YU                   varchar(1),
   tl                   varchar(20),
   primary key (GPRSCode)
);

alter table deviceWork comment '针对设备信息建立的横表';

/*==============================================================*/
/* Table: personalInfo                                          */
/*==============================================================*/
create table personalInfo
(
   pId                  varchar(40) not null,
   pname                varchar(20),
   orgno                varchar(20) comment '针对客户提供厂家实体编号，但不在页面维护',
   orgname              varchar(200),
   telephone            varchar(20),
   roleId               varchar(20) comment '字典：R_ADMIN管理员、R_CLIENT2客户厂家，R_USER3用户',
   status               bool comment '只针对此人是否认证登录的状态',
   pwd                  varchar(40),
   usermax              int comment '为客户角色设定可维护人员上限',
   parentId             varchar(40) comment '针对用户归并，提供超级用户
            需人员角色为用户时',
   ramark               varchar(200),
   primary key (pId)
);

/*==============================================================*/
/* Table: personrel                                             */
/*==============================================================*/
create table personrel
(
   pId                  varchar(40) not null,
   orgno                varchar(20) not null,
   primary key (pId, orgno)
);

alter table personrel comment '此表用于表示用户对应客户的从属关系，一个客户厂家下属有多个客户，且目前还支持同一个用户也可以属于多个客户的扩展';

/*==============================================================*/
/* Table: transInfo                                             */
/*==============================================================*/
create table transInfo
(
   transId              char(32) not null,
   cardcode             varchar(40),
   pId                  varchar(40) comment '此处人员是指发卡者，即设备经营者（用户）的ID',
   GPRSCode             varchar(40),
   transtype            char(1) comment 'A轮询  B消费交易  C充值交易  D报警交易  E密码验证交易',
   expenditure          decimal(4,2) comment '消费额或充值额',
   cardbalance          decimal(5,2),
   version              varchar(5),
   transdate            varchar(20) comment '操作时间',
   primary key (transId)
);

INSERT INTO personalinfo (pid, pname, orgno, orgname, telephone, roleid, status, pwd ) VALUES  ( 'admin', '管理员', '', '', '010-87370000', 'R_ADMIN', true, '123' );