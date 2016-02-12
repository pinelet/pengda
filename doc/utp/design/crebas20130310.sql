/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/3/10 15:05:33                           */
/*==============================================================*/


drop table if exists deviceRecord;

drop table if exists deviceWork;

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

