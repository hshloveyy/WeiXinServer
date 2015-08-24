alter table JBPM_ACTION drop constraint FK_ACTION_REFACT;
alter table JBPM_ACTION drop constraint FK_CRTETIMERACT_TA ;
alter table JBPM_ACTION drop constraint FK_ACTION_PROCDEF;
alter table JBPM_ACTION drop constraint FK_ACTION_EVENT;
alter table JBPM_ACTION drop constraint FK_ACTION_ACTNDEL;
alter table JBPM_ACTION drop constraint FK_ACTION_EXPTHDL;
alter table JBPM_BYTEARRAY drop constraint FK_BYTEARR_FILDEF;
alter table JBPM_BYTEBLOCK drop constraint FK_BYTEBLOCK_FILE;
drop index IDX_COMMENT_TSK ;
drop index IDX_COMMENT_TOKEN;
alter table JBPM_COMMENT drop constraint FK_COMMENT_TOKEN;
alter table JBPM_COMMENT drop constraint FK_COMMENT_TSK;
alter table JBPM_DECISIONCONDITIONS drop constraint FK_DECCOND_DEC;
drop index IDX_DELEG_PRCD;
alter table JBPM_DELEGATION drop constraint FK_DELEGATION_PRCD;
alter table JBPM_EVENT drop constraint FK_EVENT_PROCDEF;
alter table JBPM_EVENT drop constraint FK_EVENT_TRANS;
alter table JBPM_EVENT drop constraint FK_EVENT_NODE;
alter table JBPM_EVENT drop constraint FK_EVENT_TASK ;
alter table JBPM_ID_GROUP drop constraint FK_ID_GRP_PARENT;
alter table JBPM_ID_MEMBERSHIP drop constraint FK_ID_MEMSHIP_GRP;
alter table JBPM_ID_MEMBERSHIP drop constraint FK_ID_MEMSHIP_USR ;
drop index IDX_JOB_TSKINST;
drop index IDX_JOB_TOKEN;
drop index IDX_JOB_PRINST;
alter table JBPM_JOB drop constraint FK_JOB_PRINST;
alter table JBPM_JOB drop constraint FK_JOB_ACTION ;
alter table JBPM_JOB drop constraint FK_JOB_TOKEN ;
alter table JBPM_JOB drop constraint FK_JOB_NODE;
alter table JBPM_JOB drop constraint FK_JOB_TSKINST ;
alter table JBPM_LOG drop constraint FK_LOG_SOURCENODE;
alter table JBPM_LOG drop constraint FK_LOG_DESTNODE;
alter table JBPM_LOG drop constraint FK_LOG_TOKEN;
alter table JBPM_LOG drop constraint FK_LOG_TRANSITION;
alter table JBPM_LOG drop constraint FK_LOG_TASKINST;
alter table JBPM_LOG drop constraint FK_LOG_CHILDTOKEN;
alter table JBPM_LOG drop constraint FK_LOG_OLDBYTES;
alter table JBPM_LOG drop constraint FK_LOG_SWIMINST;
alter table JBPM_LOG drop constraint FK_LOG_NEWBYTES;
alter table JBPM_LOG drop constraint FK_LOG_ACTION;
alter table JBPM_LOG drop constraint FK_LOG_VARINST ;
alter table JBPM_LOG drop constraint FK_LOG_NODE;
alter table JBPM_LOG drop constraint FK_LOG_PARENT;
drop index IDX_MODDEF_PROCDF;
alter table JBPM_MODULEDEFINITION drop constraint FK_MODDEF_PROCDEF;
alter table JBPM_MODULEDEFINITION drop constraint FK_TSKDEF_START;
drop index IDX_MODINST_PRINST ;
alter table JBPM_MODULEINSTANCE drop constraint FK_MODINST_PRCINST;
alter table JBPM_MODULEINSTANCE drop constraint FK_TASKMGTINST_TMD;
drop index IDX_PSTATE_SBPRCDEF ;
drop index IDX_NODE_PROCDEF ;
drop index IDX_NODE_ACTION;
drop index IDX_NODE_SUPRSTATE ;
alter table JBPM_NODE drop constraint FK_DECISION_DELEG;
alter table JBPM_NODE drop constraint FK_NODE_PROCDEF;
alter table JBPM_NODE drop constraint FK_NODE_ACTION;
alter table JBPM_NODE drop constraint FK_PROCST_SBPRCDEF;
alter table JBPM_NODE drop constraint FK_NODE_SCRIPT;
alter table JBPM_NODE drop constraint FK_NODE_SUPERSTATE ;
drop index IDX_TSKINST_SWLANE ;
drop index IDX_PLDACTR_ACTID ;
alter table JBPM_POOLEDACTOR drop constraint FK_POOLEDACTOR_SLI ;
drop index IDX_PROCDEF_STRTST;
alter table JBPM_PROCESSDEFINITION drop constraint FK_PROCDEF_STRTSTA ;
drop index IDX_PROCIN_SPROCTK ;
drop index IDX_PROCIN_ROOTTK;
drop index IDX_PROCIN_PROCDEF;
drop index IDX_PROCIN_KEY;
alter table JBPM_PROCESSINSTANCE drop constraint FK_PROCIN_PROCDEF ;
alter table JBPM_PROCESSINSTANCE drop constraint FK_PROCIN_ROOTTKN ;
alter table JBPM_PROCESSINSTANCE drop constraint FK_PROCIN_SPROCTKN ;
drop index IDX_RTACTN_ACTION ;
drop index IDX_RTACTN_PRCINST ;
alter table JBPM_RUNTIMEACTION drop constraint FK_RTACTN_PROCINST ;
alter table JBPM_RUNTIMEACTION drop constraint FK_RTACTN_ACTION ;
alter table JBPM_SWIMLANE drop constraint FK_SWL_ASSDEL ;
alter table JBPM_SWIMLANE drop constraint FK_SWL_TSKMGMTDEF;
drop index IDX_SWIMLINST_SL;
alter table JBPM_SWIMLANEINSTANCE drop constraint FK_SWIMLANEINST_TM;
alter table JBPM_SWIMLANEINSTANCE drop constraint FK_SWIMLANEINST_SL;
drop index IDX_TASK_PROCDEF;
drop index IDX_TASK_TSKNODE;
drop index IDX_TASK_TASKMGTDF ;
alter table JBPM_TASK drop constraint FK_TASK_STARTST;
alter table JBPM_TASK drop constraint FK_TASK_PROCDEF;
alter table JBPM_TASK drop constraint FK_TASK_ASSDEL ;
alter table JBPM_TASK drop constraint FK_TASK_SWIMLANE ;
alter table JBPM_TASK drop constraint FK_TASK_TASKNODE ;
alter table JBPM_TASK drop constraint FK_TASK_TASKMGTDEF ;
alter table JBPM_TASK drop constraint FK_TSK_TSKCTRL ;
alter table JBPM_TASKACTORPOOL drop constraint FK_TASKACTPL_TSKI ;
alter table JBPM_TASKACTORPOOL drop constraint FK_TSKACTPOL_PLACT ;
alter table JBPM_TASKCONTROLLER drop constraint FK_TSKCTRL_DELEG;
drop index IDX_TSKINST_TMINST;
drop index IDX_TSKINST_SLINST;
drop index IDX_TASKINST_TOKN ;
drop index IDX_TASK_ACTORID ;
drop index IDX_TASKINST_TSK;
alter table JBPM_TASKINSTANCE drop constraint FK_TSKINS_PRCINS ;
alter table JBPM_TASKINSTANCE drop constraint FK_TASKINST_TMINST ;
alter table JBPM_TASKINSTANCE drop constraint FK_TASKINST_TOKEN ;
alter table JBPM_TASKINSTANCE drop constraint FK_TASKINST_SLINST;
alter table JBPM_TASKINSTANCE drop constraint FK_TASKINST_TASK;
drop index IDX_TOKEN_PARENT;
drop index IDX_TOKEN_PROCIN ;
drop index IDX_TOKEN_NODE;
drop index IDX_TOKEN_SUBPI ;
alter table JBPM_TOKEN drop constraint FK_TOKEN_SUBPI ;
alter table JBPM_TOKEN drop constraint FK_TOKEN_PROCINST ;
alter table JBPM_TOKEN drop constraint FK_TOKEN_NODE ;
alter table JBPM_TOKEN drop constraint FK_TOKEN_PARENT ;
drop index IDX_TKVVARMP_TOKEN;
drop index IDX_TKVARMAP_CTXT;
alter table JBPM_TOKENVARIABLEMAP drop constraint FK_TKVARMAP_TOKEN;
alter table JBPM_TOKENVARIABLEMAP drop constraint FK_TKVARMAP_CTXT ;
drop index IDX_TRANS_PROCDEF ;
drop index IDX_TRANSIT_FROM;
drop index IDX_TRANSIT_TO ;
alter table JBPM_TRANSITION drop constraint FK_TRANSITION_FROM ;
alter table JBPM_TRANSITION drop constraint FK_TRANS_PROCDEF ;
alter table JBPM_TRANSITION drop constraint FK_TRANSITION_TO ;
alter table JBPM_VARIABLEACCESS drop constraint FK_VARACC_PROCST;
alter table JBPM_VARIABLEACCESS drop constraint FK_VARACC_SCRIPT ;
alter table JBPM_VARIABLEACCESS drop constraint FK_VARACC_TSKCTRL ;
alter table JBPM_VARIABLEINSTANCE drop constraint FK_VARINST_PRCINST ;
alter table JBPM_VARIABLEINSTANCE drop constraint FK_VARINST_TKVARMP ;
alter table JBPM_VARIABLEINSTANCE drop constraint FK_VARINST_TK ;
alter table JBPM_VARIABLEINSTANCE drop constraint FK_BYTEINST_ARRAY ;
alter table JBPM_VARIABLEINSTANCE drop constraint FK_VAR_TSKINST ;

drop table JBPM_ACTION;
drop table JBPM_BYTEARRAY;
drop table JBPM_BYTEBLOCK;
drop table JBPM_COMMENT;
drop table JBPM_DECISIONCONDITIONS;
drop table JBPM_DELEGATION;
drop table JBPM_EVENT;
drop table JBPM_EXCEPTIONHANDLER;
drop table JBPM_ID_GROUP;
drop table JBPM_ID_MEMBERSHIP;
drop table JBPM_ID_PERMISSIONS;
drop table JBPM_ID_USER;
drop table JBPM_JOB;
drop table JBPM_LOG;
drop table JBPM_MODULEDEFINITION;
drop table JBPM_MODULEINSTANCE;
drop table JBPM_NODE;
drop table JBPM_POOLEDACTOR;
drop table JBPM_PROCESSDEFINITION;
drop table JBPM_PROCESSINSTANCE;
drop table JBPM_RUNTIMEACTION;
drop table JBPM_SWIMLANE;
drop table JBPM_SWIMLANEINSTANCE;
drop table JBPM_TASK;
drop table JBPM_TASKACTORPOOL;
drop table JBPM_TASKCONTROLLER;
drop table JBPM_TASKINSTANCE;
drop table JBPM_TOKEN;
drop table JBPM_TOKENVARIABLEMAP;
drop table JBPM_TRANSITION;
drop table JBPM_VARIABLEACCESS;
drop table JBPM_VARIABLEINSTANCE;
drop table WF_TRANSITION;
drop table WF_TASKINS;
drop table WF_TASKACTOR;
drop table WF_PROCESSINS;
drop table WF_PROCESSDEF;
drop table WF_NODEDEF;
drop table WF_BOPROCESSDEF;
drop table WF_AUTHFIELD;
drop table WF_TASKCONDITION;
drop table WF_THENSTATETYPE;
drop table WF_DYNATASKACTOR;
drop table YWF_PROCESSDEF;
create table JBPM_ACTION (ID_ bigint generated by default as identity, class char(1) not null, NAME_ varchar(255), ISPROPAGATIONALLOWED_ smallint, ACTIONEXPRESSION_ varchar(255), ISASYNC_ smallint, REFERENCEDACTION_ bigint, ACTIONDELEGATION_ bigint, EVENT_ bigint, PROCESSDEFINITION_ bigint, EXPRESSION_ varchar(4000), TIMERNAME_ varchar(255), DUEDATE_ varchar(255), REPEAT_ varchar(255), TRANSITIONNAME_ varchar(255), TIMERACTION_ bigint, EVENTINDEX_ integer, EXCEPTIONHANDLER_ bigint, EXCEPTIONHANDLERINDEX_ integer, primary key (ID_));
create table JBPM_BYTEARRAY (ID_ bigint generated by default as identity, NAME_ varchar(255), FILEDEFINITION_ bigint, primary key (ID_));
create table JBPM_BYTEBLOCK (PROCESSFILE_ bigint not null, BYTES_ varchar(1024) for bit data, INDEX_ integer not null, primary key (PROCESSFILE_, INDEX_));
create table JBPM_COMMENT (ID_ bigint generated by default as identity, VERSION_ integer not null, ACTORID_ varchar(255), TIME_ timestamp, MESSAGE_ varchar(4000), TOKEN_ bigint, TASKINSTANCE_ bigint, TOKENINDEX_ integer, TASKINSTANCEINDEX_ integer, primary key (ID_));
create table JBPM_DECISIONCONDITIONS (DECISION_ bigint not null, TRANSITIONNAME_ varchar(255), EXPRESSION_ varchar(255), INDEX_ integer not null, primary key (DECISION_, INDEX_));
create table JBPM_DELEGATION (ID_ bigint generated by default as identity, CLASSNAME_ varchar(4000), CONFIGURATION_ varchar(4000), CONFIGTYPE_ varchar(255), PROCESSDEFINITION_ bigint, primary key (ID_));
create table JBPM_EVENT (ID_ bigint generated by default as identity, EVENTTYPE_ varchar(255), TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSDEFINITION_ bigint, NODE_ bigint, TRANSITION_ bigint, TASK_ bigint, primary key (ID_));
create table JBPM_EXCEPTIONHANDLER (ID_ bigint generated by default as identity, EXCEPTIONCLASSNAME_ varchar(4000), TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSDEFINITION_ bigint, GRAPHELEMENTINDEX_ integer, NODE_ bigint, TRANSITION_ bigint, TASK_ bigint, primary key (ID_));
create table JBPM_ID_GROUP (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(255), TYPE_ varchar(255), PARENT_ bigint, primary key (ID_));
create table JBPM_ID_MEMBERSHIP (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(255), ROLE_ varchar(255), USER_ bigint, GROUP_ bigint, primary key (ID_));
create table JBPM_ID_PERMISSIONS (ENTITY_ bigint not null, CLASS_ varchar(255), NAME_ varchar(255), ACTION_ varchar(255));
create table JBPM_ID_USER (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(255), EMAIL_ varchar(255), PASSWORD_ varchar(255), primary key (ID_));
create table JBPM_JOB (ID_ bigint generated by default as identity, CLASS_ char(1) not null, VERSION_ integer not null, DUEDATE_ timestamp, PROCESSINSTANCE_ bigint, TOKEN_ bigint, TASKINSTANCE_ bigint, ISSUSPENDED_ smallint, ISEXCLUSIVE_ smallint, LOCKOWNER_ varchar(255), LOCKTIME_ timestamp, EXCEPTION_ varchar(4000), RETRIES_ integer, NAME_ varchar(255), REPEAT_ varchar(255), TRANSITIONNAME_ varchar(255), ACTION_ bigint, GRAPHELEMENTTYPE_ varchar(255), GRAPHELEMENT_ bigint, NODE_ bigint, primary key (ID_));
create table JBPM_LOG (ID_ bigint generated by default as identity, CLASS_ char(1) not null, INDEX_ integer, DATE_ timestamp, TOKEN_ bigint, PARENT_ bigint, MESSAGE_ varchar(2000), EXCEPTION_ varchar(2000), ACTION_ bigint, NODE_ bigint, ENTER_ timestamp, LEAVE_ timestamp, DURATION_ bigint, NEWLONGVALUE_ bigint, TRANSITION_ bigint, CHILD_ bigint, SOURCENODE_ bigint, DESTINATIONNODE_ bigint, VARIABLEINSTANCE_ bigint, OLDBYTEARRAY_ bigint, NEWBYTEARRAY_ bigint, OLDDATEVALUE_ timestamp, NEWDATEVALUE_ timestamp, OLDDOUBLEVALUE_ double, NEWDOUBLEVALUE_ double, OLDLONGIDCLASS_ varchar(255), OLDLONGIDVALUE_ bigint, NEWLONGIDCLASS_ varchar(255), NEWLONGIDVALUE_ bigint, OLDSTRINGIDCLASS_ varchar(255), OLDSTRINGIDVALUE_ varchar(255), NEWSTRINGIDCLASS_ varchar(255), NEWSTRINGIDVALUE_ varchar(255), OLDLONGVALUE_ bigint, OLDSTRINGVALUE_ varchar(2000), NEWSTRINGVALUE_ varchar(2000), TASKINSTANCE_ bigint, TASKACTORID_ varchar(255), TASKOLDACTORID_ varchar(255), SWIMLANEINSTANCE_ bigint, primary key (ID_));
create table JBPM_MODULEDEFINITION (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(4000), PROCESSDEFINITION_ bigint, STARTTASK_ bigint, primary key (ID_));
create table JBPM_MODULEINSTANCE (ID_ bigint generated by default as identity, CLASS_ char(1) not null, VERSION_ integer not null, PROCESSINSTANCE_ bigint, TASKMGMTDEFINITION_ bigint, NAME_ varchar(255), primary key (ID_));
create table JBPM_NODE (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(255), DESCRIPTION_ varchar(4000), PROCESSDEFINITION_ bigint, ISASYNC_ smallint, ISASYNCEXCL_ smallint, ACTION_ bigint, SUPERSTATE_ bigint, SUBPROCNAME_ varchar(255), SUBPROCESSDEFINITION_ bigint, DECISIONEXPRESSION_ varchar(255), DECISIONDELEGATION bigint, SCRIPT_ bigint, SIGNAL_ integer, CREATETASKS_ smallint, ENDTASKS_ smallint, NODECOLLECTIONINDEX_ integer, primary key (ID_));
create table JBPM_POOLEDACTOR (ID_ bigint generated by default as identity, VERSION_ integer not null, ACTORID_ varchar(255), SWIMLANEINSTANCE_ bigint, primary key (ID_));
create table JBPM_PROCESSDEFINITION (ID_ bigint generated by default as identity, CLASS_ char(1) not null, NAME_ varchar(255), DESCRIPTION_ varchar(4000), VERSION_ integer, ISTERMINATIONIMPLICIT_ smallint, STARTSTATE_ bigint, primary key (ID_));
create table JBPM_PROCESSINSTANCE (ID_ bigint generated by default as identity, VERSION_ integer not null, KEY_ varchar(255), START_ timestamp, END_ timestamp, ISSUSPENDED_ smallint, PROCESSDEFINITION_ bigint, ROOTTOKEN_ bigint, SUPERPROCESSTOKEN_ bigint, primary key (ID_));
create table JBPM_RUNTIMEACTION (ID_ bigint generated by default as identity, VERSION_ integer not null, EVENTTYPE_ varchar(255), TYPE_ char(1), GRAPHELEMENT_ bigint, PROCESSINSTANCE_ bigint, ACTION_ bigint, PROCESSINSTANCEINDEX_ integer, primary key (ID_));
create table JBPM_SWIMLANE (ID_ bigint generated by default as identity, NAME_ varchar(255), ACTORIDEXPRESSION_ varchar(255), POOLEDACTORSEXPRESSION_ varchar(255), ASSIGNMENTDELEGATION_ bigint, TASKMGMTDEFINITION_ bigint, primary key (ID_));
create table JBPM_SWIMLANEINSTANCE (ID_ bigint generated by default as identity, VERSION_ integer not null, NAME_ varchar(255), ACTORID_ varchar(255), SWIMLANE_ bigint, TASKMGMTINSTANCE_ bigint, primary key (ID_));
create table JBPM_TASK (ID_ bigint generated by default as identity, NAME_ varchar(255), DESCRIPTION_ varchar(4000), PROCESSDEFINITION_ bigint, ISBLOCKING_ smallint, ISSIGNALLING_ smallint, CONDITION_ varchar(255), DUEDATE_ varchar(255), PRIORITY_ integer, ACTORIDEXPRESSION_ varchar(255), POOLEDACTORSEXPRESSION_ varchar(255), TASKMGMTDEFINITION_ bigint, TASKNODE_ bigint, STARTSTATE_ bigint, ASSIGNMENTDELEGATION_ bigint, SWIMLANE_ bigint, TASKCONTROLLER_ bigint, primary key (ID_));
create table JBPM_TASKACTORPOOL (TASKINSTANCE_ bigint not null, POOLEDACTOR_ bigint not null, primary key (TASKINSTANCE_, POOLEDACTOR_));
create table JBPM_TASKCONTROLLER (ID_ bigint generated by default as identity, TASKCONTROLLERDELEGATION_ bigint, primary key (ID_));
create table JBPM_TASKINSTANCE (ID_ bigint generated by default as identity, CLASS_ char(1) not null, VERSION_ integer not null, NAME_ varchar(255), DESCRIPTION_ varchar(4000), ACTORID_ varchar(255), CREATE_ timestamp, START_ timestamp, END_ timestamp, DUEDATE_ timestamp, PRIORITY_ integer, ISCANCELLED_ smallint, ISSUSPENDED_ smallint, ISOPEN_ smallint, ISSIGNALLING_ smallint, ISBLOCKING_ smallint, TASK_ bigint, TOKEN_ bigint, PROCINST_ bigint, SWIMLANINSTANCE_ bigint, TASKMGMTINSTANCE_ bigint, primary key (ID_));
create table JBPM_TOKEN (ID_ bigint generated by default as identity, VERSION_ integer not null, NAME_ varchar(255), START_ timestamp, END_ timestamp, NODEENTER_ timestamp, NEXTLOGINDEX_ integer, ISABLETOREACTIVATEPARENT_ smallint, ISTERMINATIONIMPLICIT_ smallint, ISSUSPENDED_ smallint, LOCK_ varchar(255), NODE_ bigint, PROCESSINSTANCE_ bigint, PARENT_ bigint, SUBPROCESSINSTANCE_ bigint, primary key (ID_));
create table JBPM_TOKENVARIABLEMAP (ID_ bigint generated by default as identity, VERSION_ integer not null, TOKEN_ bigint, CONTEXTINSTANCE_ bigint, primary key (ID_));
create table JBPM_TRANSITION (ID_ bigint generated by default as identity, NAME_ varchar(255), DESCRIPTION_ varchar(4000), PROCESSDEFINITION_ bigint, FROM_ bigint, TO_ bigint, CONDITION_ varchar(255), FROMINDEX_ integer, primary key (ID_));
create table JBPM_VARIABLEACCESS (ID_ bigint generated by default as identity, VARIABLENAME_ varchar(255), ACCESS_ varchar(255), MAPPEDNAME_ varchar(255), SCRIPT_ bigint, PROCESSSTATE_ bigint, TASKCONTROLLER_ bigint, INDEX_ integer, primary key (ID_));
create table JBPM_VARIABLEINSTANCE (ID_ bigint generated by default as identity, CLASS_ char(1) not null, VERSION_ integer not null, NAME_ varchar(255), CONVERTER_ char(1), TOKEN_ bigint, TOKENVARIABLEMAP_ bigint, PROCESSINSTANCE_ bigint, BYTEARRAYVALUE_ bigint, DATEVALUE_ timestamp, DOUBLEVALUE_ double, LONGIDCLASS_ varchar(255), LONGVALUE_ bigint, STRINGIDCLASS_ varchar(255), STRINGVALUE_ varchar(4000), TASKINSTANCE_ bigint, primary key (ID_));


create table WF_AUTHFIELD
(
  AUTHFIELDID        VARCHAR(36) not null,
  NODEID             BIGINT,
  EXTPROCESSID       VARCHAR(36) not null,
  FIELDNAME          VARCHAR(50),
  FIELDDESC          VARCHAR(100),
  ALLOWOPERATIONTYPE VARCHAR(2),
  BOID               VARCHAR(50),
  primary key (AUTHFIELDID)
);

create table WF_BOPROCESSDEF
(
  BPDEFID               VARCHAR(36) not null,
  APPMODEL              VARCHAR(50) not null,
  BPNAME                VARCHAR(100) not null,
  PROCESSDEFINITIONNAME VARCHAR(200) not null,
  BOID                  VARCHAR(100),
  PROCESSTYPE           VARCHAR(10) not null,
  CREATOR               VARCHAR(36),
  CREATETIME            VARCHAR(24),
  BPDESC                VARCHAR(500) not null,
  LASTMODIFYER          VARCHAR(36),
  LASTMODIFYTIME        VARCHAR(24),
  MEMO                  VARCHAR(500),
  BOMETHODID            VARCHAR(36),
  ORGANIZATIONID        VARCHAR(36),
  ISDEFAULT             VARCHAR(1),
  primary key (BPDEFID)
);

create table WF_NODEDEF
(
  NODEDEFID          VARCHAR(36) not null,
  NODEID             BIGINT,
  BOID               VARCHAR(50),
  NODEDEFINITIONNAME VARCHAR(50),
  EXELOGICTYPE       VARCHAR(1),
  BOMETHODID         VARCHAR(50),
  AUTOSIGNAL         VARCHAR(1),
  EXELOGIC           VARCHAR(500),
  EXTPROCESSID       VARCHAR(36) not null,
  EXAMINETYPE        VARCHAR(1),
  PROCESSID          BIGINT,
  ISSENDMAIL         VARCHAR(1) default '0',
  primary key (NODEDEFID)
);

create table WF_PROCESSDEF
(
  EXTPROCESSID          VARCHAR(36) not null,
  PROCESSDEFINITIONNAME VARCHAR(100) not null,
  PROCESSID             BIGINT not null,
  VERSION               VARCHAR(3) not null,
  ACTIVE                VARCHAR(4) default '0' not null,
  primary key (EXTPROCESSID)
);

create table WF_PROCESSINS
(
  PROCESSINSID          VARCHAR(36) not null,
  EXTPROCESSID          VARCHAR(36)  not null,
  PROCESSID             BIGINT,
  BUSINESSID            VARCHAR(36),
  DEPARTMENTID          VARCHAR(36),
  BOID                  VARCHAR(50),
  PROCESSURL            VARCHAR(500),
  INSCREATETIME         VARCHAR(14),
  INSENDTIME            VARCHAR(14),
  ENDNODENAME           VARCHAR(50),
  BUSINESSNOTE          VARCHAR(1000),
  PARENTCOMMONPROCESSID VARCHAR(36),
  CREATORID             VARCHAR(36),
  CREATORNAME           VARCHAR(100),
  MODELNAME             VARCHAR(100),
  primary key (PROCESSINSID)
);

create table WF_TASKACTOR
(
  TASKACTORID VARCHAR(36) not null,
  EXTPROCESSID          VARCHAR(36)  not null,
  NODEID      BIGINT,
  ACTORID     VARCHAR(36),
  ACTORTYPE   VARCHAR(1),
  ACTORNAME   VARCHAR(40),
  ASSIGNLOGIC VARCHAR(50),
  PROCESSID   BIGINT,
  primary key (TASKACTORID)
);

create table WF_TASKINS
(
  TASKINSID       VARCHAR(36) not null,
  TASKID          BIGINT,
  PROCESSID       BIGINT,
  BUSINESSID      VARCHAR(36),
  TASKNAME        VARCHAR(50),
  TASKDESCRIPTION VARCHAR(100),
  TASKCREATETIME  VARCHAR(14),
  TASKENDTIME     VARCHAR(14),
  EXAMINE         VARCHAR(200),
  EXAMINEPERSON   VARCHAR(50),
  EXAMINERESULT   VARCHAR(500),
  WITHTIME        NVARCHAR(200),
  PARENTBUSINESSID VARCHAR2(36),
  primary key (TASKINSID)
);

create table WF_TRANSITION
(
  EXTENDTRANSITIONID VARCHAR(36) not null,
  TRANSITIONNAME     VARCHAR(50) not null,
  EXTPROCESSID       VARCHAR(36) not null,
  NODEID             VARCHAR(50) not null,
  CONDITION          VARCHAR(1000) not null,
  NEXTNODENAME       VARCHAR(100),
  CONDITIONTYPE      VARCHAR(1) not null,
  PROCESSID          BIGINT,
  JSONCONDITION      VARCHAR(1000) not null,
  primary key (EXTENDTRANSITIONID)
);

create table WF_TASKCONDITION
(
  TSKACTCONDID        VARCHAR(72) not null,
  EXTPROCESSID          VARCHAR(36)  not null,
  PROCESSID           BIGINT,
  NODEID              BIGINT,
  IFSTATEMENT         VARCHAR(2000),
  THENSTATEMENTTYPE   VARCHAR(20),
  THENSTATEMENT       VARCHAR(1000),
  IFSTATEMENTJSON     VARCHAR(2000),
  THENSTATEMENTTEXT   VARCHAR(1000),
  THENSTATEMENTTYPEID VARCHAR(36),
  primary key (TSKACTCONDID)
);

create table WF_THENSTATETYPE
(
  TYPEID         VARCHAR(36) not null,
  STATEMENT      VARCHAR(500) not null,
  TYPEDESC       VARCHAR(100) not null,
  VALUEFECTHTYPE CHAR(1) not null,
  SHLPIDCOLUMN   VARCHAR(100) not null,
  SHLPTEXTCOLUMN VARCHAR(100) not null,
  SHLPNAME       VARCHAR(100) not null,
  primary key (TYPEID)
);

create table WF_DYNATASKACTOR
(
  DYNATASKID VARCHAR(36) not null,
  TASKID     VARCHAR(36) not null,
  ACTORID    VARCHAR(36) not null,
  BUSINESSID VARCHAR(36) not null,
  ACTORTYPE  VARCHAR(2),
  ACTORNAME  VARCHAR(50),
  primary key (DYNATASKID)
);

create table YWF_PROCESSDEF
(
  PROCESSDEFID   VARCHAR(108) default ' ' not null,
  PROCESSNAME    VARCHAR(120) default ' ' not null,
  PROCESSDESC    VARCHAR(150),
  VERSION        BIGINT default 0 not null,
  ISPUBLIC       VARCHAR(3) default '0' not null,
  CREATOR        VARCHAR(108),
  CREATETIME     VARCHAR(42),
  LASTMODIFYER   VARCHAR(108),
  LASTMODIFYTIME VARCHAR(42),
  DEFDOCUMENT    BLOB(527645)      LOGGED  NOT COMPACT,
  POSDOCUMENT    BLOB(527645)      LOGGED  NOT COMPACT,
  primary key (PROCESSDEFID)
);
CREATE VIEW V_WF_CURRENT_JOB AS
SELECT DISTINCT pins.processid,
       pins.businessid,
       pins.boid,
       pins.processurl,
       pins.inscreatetime,
       pins.insendtime,
       pins.endnodename,
       pins.businessnote,
       pins.parentcommonprocessid,
       pins.creatorid,
       pins.creatorname,
       node.id_ nodeid,
       tact.actorid,
       tact.actortype,
       tact.actorname,
       tact.assignlogic,
      jtins.name_,
      jtins.START_,
      jtins.TASK_,
      jtins.create_ taskcreatetime,
      jtins.ID_ AS TASK_ID
FROM  WF_PROCESSINS pins,
      JBPM_PROCESSINSTANCE jpins,
      JBPM_TASKINSTANCE jtins,
      JBPM_TASK task,
      JBPM_NODE node,
      WF_TASKACTOR tact
WHERE  pins.processid = jpins.ID_
  and jpins.ID_ = jtins.PROCINST_
  and jtins.task_ = task.id_
  and node.id_ = task.tasknode_
  and node.id_ = tact.nodeid
  and jtins.end_ is null

  union
  SELECT DISTINCT pins.processid,
       pins.businessid,
       pins.boid,
       pins.processurl,
       pins.inscreatetime,
       pins.insendtime,
       pins.endnodename,
       pins.businessnote,
       pins.parentcommonprocessid,
       pins.creatorid,
       pins.creatorname,
       node.id_ nodeid,
       tact.actorid,
       tact.actortype,
       tact.actorname,
       '' assignlogic,
      jtins.name_,
      jtins.START_,
      jtins.TASK_,
      jtins.create_ taskcreatetime,
      jtins.ID_ AS TASK_ID
FROM  WF_PROCESSINS pins,
      JBPM_PROCESSINSTANCE jpins,
      JBPM_TASKINSTANCE jtins,
      JBPM_TASK task,
      JBPM_NODE node,
      WF_DYNATASKACTOR tact
WHERE  pins.processid = jpins.ID_
  and jpins.ID_ = jtins.PROCINST_
  and jtins.task_ = task.id_
  and node.id_ = task.tasknode_
  and trim(char(task.id_)) = tact.taskid
  and tact.businessid = pins.businessid
  and jtins.end_ is null
  union
  SELECT DISTINCT pins.processid,
       pins.businessid,
       pins.boid,
       pins.processurl,
       pins.inscreatetime,
       pins.insendtime,
       pins.endnodename,
       pins.businessnote,
       pins.parentcommonprocessid,
       pins.creatorid,
       pins.creatorname,
       node.id_ nodeid,
       tact.touserid as actorid,
       '1' as actortype,
       tact.touserid as actorname,
       '' as assignlogic,
      jtins.name_,
      jtins.START_,
      jtins.TASK_,
      jtins.create_ taskcreatetime,
      jtins.ID_ AS TASK_ID
FROM  WF_PROCESSINS pins,
      JBPM_PROCESSINSTANCE jpins,
      JBPM_TASKINSTANCE jtins,
      JBPM_TASK task,
      JBPM_NODE node,
      YUSERAUTHMOVE tact
WHERE  pins.processid = jpins.ID_
  and jpins.ID_ = jtins.PROCINST_
  and jtins.task_ = task.id_
  and node.id_ = task.tasknode_
  and trim(char(node.id_)) = tact.resourceid
  and jtins.end_ is null
  and tact.resourcetype='4';