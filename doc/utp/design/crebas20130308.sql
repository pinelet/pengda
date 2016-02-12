/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/3/8 23:18:31                            */
/*==============================================================*/


drop table if exists deviceRecord;

drop table if exists deviceWork;

/*==============================================================*/
/* Table: deviceRecord                                          */
/*==============================================================*/
create table deviceRecord
(
   GPRSCode             varchar(40) not null,
   recordType           char(1) not null comment 'D预警类 F 监测类 G 运行类 ',
   param                varchar(10) comment 'CARDCOM  LOWPR   HIGHPR  LOWPO  MAKEW  OFFS  ONS(报警类)
            BOXTEM  TDSEL  PH(监测类)
            MN mm LL GU c1 c2 b c y wh Op OT oV cp hp yp cM pT cT  LO LC FU YU tl(运行类)',
   value                varchar(20),
   recordTime           varchar(20) not null
);

/*==============================================================*/
/* Table: deviceWork                                            */
/*==============================================================*/
create table deviceWork
(
   GPRSCode             varchar(40) not null,
   MN                   char(1),
   mm                   char(1),
   LL                   char(1),
   GU                   char(1),
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
   FU                   char(1),
   YU                   char(1),
   tl                   varchar(20),
   primary key (GPRSCode)
);

