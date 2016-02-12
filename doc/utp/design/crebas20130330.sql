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
   pId                  varchar(40) not null comment '�˴���Ա��ָ�����ߣ����豸��Ӫ�ߣ��û�����ID',
   cardname             varchar(20),
   phone                varchar(20),
   address              varchar(400),
   balance              decimal(5,2),
   applydate            varchar(20),
   transdate            varchar(20),
   cardstatus           char(1) comment '�ֵ䣺1������2��ʧ/��������0����',
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
   pId                  varchar(40) not null comment '�˴���Ա��ָ�����ߣ����豸��Ӫ�ߣ��û�����ID',
   synctype             char(1) comment '1.������  2.��������������������',
   syncresult           char(1) comment '�ֵ䣺1֪ͨ�ɹ���2֪ͨʧ�ܣ�0δ֪ͨ',
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
   pId                  varchar(40) comment '�豸��Ӧ���û�ID',
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
   deviceCode           varchar(20) comment 'ͬһ���ͻ��µ��豸���Ψһ',
   pId                  varchar(40) comment '�˴���Ա��ָ�����ߣ����豸��Ӫ�ߣ��û�����ID',
   phoneCode            varchar(15) not null,
   Model                varchar(40) comment '�����ֵ�',
   contactCall          varchar(20),
   Address              varchar(200),
   ppDate               varchar(10),
   carbonDate           varchar(10),
   roDate               varchar(10),
   mineDate             varchar(10),
   deviceStatus         varchar(2) comment '0.ͣ�� 1.���������ݱ���������',
   status               char(1) comment '�Ƿ����ʹ��ƽ̨�����״̬��1.��������  0.�رշ���',
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
   recordType           char(1) not null comment 'DԤ���� F ����� G ������ ',
   devicetemp           varchar(10) comment 'CARDCOM  LOWPR   HIGHPR  LOWPO  MAKEW  OFFS  ONS(������)
            BOXTEM  TDSEL  PH(�����)
            MN mm LL GU c1 c2 b c y wh Op OT oV cp hp yp cM pT cT  LO LC FU YU tl(������)',
   tdsel                varchar(10),
   ph                   varchar(10),
   recordTime           varchar(20) not null
);

alter table deviceRecord comment '����豸��Ϣ�������ݱ�';

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

alter table deviceWork comment '����豸��Ϣ�����ĺ��';

/*==============================================================*/
/* Table: personalInfo                                          */
/*==============================================================*/
create table personalInfo
(
   pId                  varchar(40) not null,
   pname                varchar(20),
   orgno                varchar(20) comment '��Կͻ��ṩ����ʵ���ţ�������ҳ��ά��',
   orgname              varchar(200),
   telephone            varchar(20),
   roleId               varchar(20) comment '�ֵ䣺R_ADMIN����Ա��R_CLIENT2�ͻ����ң�R_USER3�û�',
   status               bool comment 'ֻ��Դ����Ƿ���֤��¼��״̬',
   pwd                  varchar(40),
   usermax              int comment 'Ϊ�ͻ���ɫ�趨��ά����Ա����',
   parentId             varchar(40) comment '����û��鲢���ṩ�����û�
            ����Ա��ɫΪ�û�ʱ',
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

alter table personrel comment '�˱����ڱ�ʾ�û���Ӧ�ͻ��Ĵ�����ϵ��һ���ͻ����������ж���ͻ�����Ŀǰ��֧��ͬһ���û�Ҳ�������ڶ���ͻ�����չ';

/*==============================================================*/
/* Table: transInfo                                             */
/*==============================================================*/
create table transInfo
(
   transId              char(32) not null,
   cardcode             varchar(40),
   pId                  varchar(40) comment '�˴���Ա��ָ�����ߣ����豸��Ӫ�ߣ��û�����ID',
   GPRSCode             varchar(40),
   transtype            char(1) comment 'A��ѯ  B���ѽ���  C��ֵ����  D��������  E������֤����',
   expenditure          decimal(4,2) comment '���Ѷ���ֵ��',
   cardbalance          decimal(5,2),
   version              varchar(5),
   transdate            varchar(20) comment '����ʱ��',
   primary key (transId)
);

INSERT INTO personalinfo (pid, pname, orgno, orgname, telephone, roleid, status, pwd ) VALUES  ( 'admin', '����Ա', '', '', '010-87370000', 'R_ADMIN', true, '123' );