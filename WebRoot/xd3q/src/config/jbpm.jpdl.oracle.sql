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

drop table JBPM_ACTION cascade constraints;
drop table JBPM_BYTEARRAY cascade constraints;
drop table JBPM_BYTEBLOCK cascade constraints;
drop table JBPM_COMMENT cascade constraints;
drop table JBPM_DECISIONCONDITIONS cascade constraints;
drop table JBPM_DELEGATION cascade constraints;
drop table JBPM_EVENT cascade constraints;
drop table JBPM_EXCEPTIONHANDLER cascade constraints;
drop table JBPM_ID_GROUP cascade constraints;
drop table JBPM_ID_MEMBERSHIP cascade constraints;
drop table JBPM_ID_PERMISSIONS cascade constraints;
drop table JBPM_ID_USER cascade constraints;
drop table JBPM_JOB cascade constraints;
drop table JBPM_LOG cascade constraints;
drop table JBPM_MODULEDEFINITION cascade constraints;
drop table JBPM_MODULEINSTANCE cascade constraints;
drop table JBPM_NODE cascade constraints;
drop table JBPM_POOLEDACTOR cascade constraints;
drop table JBPM_PROCESSDEFINITION cascade constraints;
drop table JBPM_PROCESSINSTANCE cascade constraints;
drop table JBPM_RUNTIMEACTION cascade constraints;
drop table JBPM_SWIMLANE cascade constraints;
drop table JBPM_SWIMLANEINSTANCE cascade constraints;
drop table JBPM_TASK cascade constraints;
drop table JBPM_TASKACTORPOOL cascade constraints;
drop table JBPM_TASKCONTROLLER cascade constraints;
drop table JBPM_TASKINSTANCE cascade constraints;
drop table JBPM_TOKEN cascade constraints;
drop table JBPM_TOKENVARIABLEMAP cascade constraints;
drop table JBPM_TRANSITION cascade constraints;
drop table JBPM_VARIABLEACCESS cascade constraints;
drop table JBPM_VARIABLEINSTANCE cascade constraints;
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
drop sequence hibernate_sequence;
create table JBPM_ACTION (ID_ number(19,0) not null, class char(1 char) not null, NAME_ varchar2(255 char), ISPROPAGATIONALLOWED_ number(1,0), ACTIONEXPRESSION_ varchar2(255 char), ISASYNC_ number(1,0), REFERENCEDACTION_ number(19,0), ACTIONDELEGATION_ number(19,0), EVENT_ number(19,0), PROCESSDEFINITION_ number(19,0), EXPRESSION_ varchar2(4000 char), TIMERNAME_ varchar2(255 char), DUEDATE_ varchar2(255 char), REPEAT_ varchar2(255 char), TRANSITIONNAME_ varchar2(255 char), TIMERACTION_ number(19,0), EVENTINDEX_ number(10,0), EXCEPTIONHANDLER_ number(19,0), EXCEPTIONHANDLERINDEX_ number(10,0), primary key (ID_));
create table JBPM_BYTEARRAY (ID_ number(19,0) not null, NAME_ varchar2(255 char), FILEDEFINITION_ number(19,0), primary key (ID_));
create table JBPM_BYTEBLOCK (PROCESSFILE_ number(19,0) not null, BYTES_ raw(1024), INDEX_ number(10,0) not null, primary key (PROCESSFILE_, INDEX_));
create table JBPM_COMMENT (ID_ number(19,0) not null, VERSION_ number(10,0) not null, ACTORID_ varchar2(255 char), TIME_ timestamp, MESSAGE_ varchar2(4000 char), TOKEN_ number(19,0), TASKINSTANCE_ number(19,0), TOKENINDEX_ number(10,0), TASKINSTANCEINDEX_ number(10,0), primary key (ID_));
create table JBPM_DECISIONCONDITIONS (DECISION_ number(19,0) not null, TRANSITIONNAME_ varchar2(255 char), EXPRESSION_ varchar2(255 char), INDEX_ number(10,0) not null, primary key (DECISION_, INDEX_));
create table JBPM_DELEGATION (ID_ number(19,0) not null, CLASSNAME_ varchar2(4000 char), CONFIGURATION_ varchar2(4000 char), CONFIGTYPE_ varchar2(255 char), PROCESSDEFINITION_ number(19,0), primary key (ID_));
create table JBPM_EVENT (ID_ number(19,0) not null, EVENTTYPE_ varchar2(255 char), TYPE_ char(1 char), GRAPHELEMENT_ number(19,0), PROCESSDEFINITION_ number(19,0), NODE_ number(19,0), TRANSITION_ number(19,0), TASK_ number(19,0), primary key (ID_));
create table JBPM_EXCEPTIONHANDLER (ID_ number(19,0) not null, EXCEPTIONCLASSNAME_ varchar2(4000 char), TYPE_ char(1 char), GRAPHELEMENT_ number(19,0), PROCESSDEFINITION_ number(19,0), GRAPHELEMENTINDEX_ number(10,0), NODE_ number(19,0), TRANSITION_ number(19,0), TASK_ number(19,0), primary key (ID_));
create table JBPM_ID_GROUP (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(255 char), TYPE_ varchar2(255 char), PARENT_ number(19,0), primary key (ID_));
create table JBPM_ID_MEMBERSHIP (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(255 char), ROLE_ varchar2(255 char), USER_ number(19,0), GROUP_ number(19,0), primary key (ID_));
create table JBPM_ID_PERMISSIONS (ENTITY_ number(19,0) not null, CLASS_ varchar2(255 char), NAME_ varchar2(255 char), ACTION_ varchar2(255 char));
create table JBPM_ID_USER (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(255 char), EMAIL_ varchar2(255 char), PASSWORD_ varchar2(255 char), primary key (ID_));
create table JBPM_JOB (ID_ number(19,0) not null, CLASS_ char(1 char) not null, VERSION_ number(10,0) not null, DUEDATE_ timestamp, PROCESSINSTANCE_ number(19,0), TOKEN_ number(19,0), TASKINSTANCE_ number(19,0), ISSUSPENDED_ number(1,0), ISEXCLUSIVE_ number(1,0), LOCKOWNER_ varchar2(255 char), LOCKTIME_ timestamp, EXCEPTION_ varchar2(4000 char), RETRIES_ number(10,0), NAME_ varchar2(255 char), REPEAT_ varchar2(255 char), TRANSITIONNAME_ varchar2(255 char), ACTION_ number(19,0), GRAPHELEMENTTYPE_ varchar2(255 char), GRAPHELEMENT_ number(19,0), NODE_ number(19,0), primary key (ID_));
create table JBPM_LOG (ID_ number(19,0) not null, CLASS_ char(1 char) not null, INDEX_ number(10,0), DATE_ timestamp, TOKEN_ number(19,0), PARENT_ number(19,0), MESSAGE_ varchar2(4000 char), EXCEPTION_ varchar2(4000 char), ACTION_ number(19,0), NODE_ number(19,0), ENTER_ timestamp, LEAVE_ timestamp, DURATION_ number(19,0), NEWLONGVALUE_ number(19,0), TRANSITION_ number(19,0), CHILD_ number(19,0), SOURCENODE_ number(19,0), DESTINATIONNODE_ number(19,0), VARIABLEINSTANCE_ number(19,0), OLDBYTEARRAY_ number(19,0), NEWBYTEARRAY_ number(19,0), OLDDATEVALUE_ timestamp, NEWDATEVALUE_ timestamp, OLDDOUBLEVALUE_ double precision, NEWDOUBLEVALUE_ double precision, OLDLONGIDCLASS_ varchar2(255 char), OLDLONGIDVALUE_ number(19,0), NEWLONGIDCLASS_ varchar2(255 char), NEWLONGIDVALUE_ number(19,0), OLDSTRINGIDCLASS_ varchar2(255 char), OLDSTRINGIDVALUE_ varchar2(255 char), NEWSTRINGIDCLASS_ varchar2(255 char), NEWSTRINGIDVALUE_ varchar2(255 char), OLDLONGVALUE_ number(19,0), OLDSTRINGVALUE_ varchar2(4000 char), NEWSTRINGVALUE_ varchar2(4000 char), TASKINSTANCE_ number(19,0), TASKACTORID_ varchar2(255 char), TASKOLDACTORID_ varchar2(255 char), SWIMLANEINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_MODULEDEFINITION (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(4000 char), PROCESSDEFINITION_ number(19,0), STARTTASK_ number(19,0), primary key (ID_));
create table JBPM_MODULEINSTANCE (ID_ number(19,0) not null, CLASS_ char(1 char) not null, VERSION_ number(10,0) not null, PROCESSINSTANCE_ number(19,0), TASKMGMTDEFINITION_ number(19,0), NAME_ varchar2(255 char), primary key (ID_));
create table JBPM_NODE (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(255 char), DESCRIPTION_ varchar2(4000 char), PROCESSDEFINITION_ number(19,0), ISASYNC_ number(1,0), ISASYNCEXCL_ number(1,0), ACTION_ number(19,0), SUPERSTATE_ number(19,0), SUBPROCNAME_ varchar2(255 char), SUBPROCESSDEFINITION_ number(19,0), DECISIONEXPRESSION_ varchar2(255 char), DECISIONDELEGATION number(19,0), SCRIPT_ number(19,0), SIGNAL_ number(10,0), CREATETASKS_ number(1,0), ENDTASKS_ number(1,0), NODECOLLECTIONINDEX_ number(10,0), primary key (ID_));
create table JBPM_POOLEDACTOR (ID_ number(19,0) not null, VERSION_ number(10,0) not null, ACTORID_ varchar2(255 char), SWIMLANEINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_PROCESSDEFINITION (ID_ number(19,0) not null, CLASS_ char(1 char) not null, NAME_ varchar2(255 char), DESCRIPTION_ varchar2(4000 char), VERSION_ number(10,0), ISTERMINATIONIMPLICIT_ number(1,0), STARTSTATE_ number(19,0), primary key (ID_));
create table JBPM_PROCESSINSTANCE (ID_ number(19,0) not null, VERSION_ number(10,0) not null, KEY_ varchar2(255 char), START_ timestamp, END_ timestamp, ISSUSPENDED_ number(1,0), PROCESSDEFINITION_ number(19,0), ROOTTOKEN_ number(19,0), SUPERPROCESSTOKEN_ number(19,0), primary key (ID_));
create table JBPM_RUNTIMEACTION (ID_ number(19,0) not null, VERSION_ number(10,0) not null, EVENTTYPE_ varchar2(255 char), TYPE_ char(1 char), GRAPHELEMENT_ number(19,0), PROCESSINSTANCE_ number(19,0), ACTION_ number(19,0), PROCESSINSTANCEINDEX_ number(10,0), primary key (ID_));
create table JBPM_SWIMLANE (ID_ number(19,0) not null, NAME_ varchar2(255 char), ACTORIDEXPRESSION_ varchar2(255 char), POOLEDACTORSEXPRESSION_ varchar2(255 char), ASSIGNMENTDELEGATION_ number(19,0), TASKMGMTDEFINITION_ number(19,0), primary key (ID_));
create table JBPM_SWIMLANEINSTANCE (ID_ number(19,0) not null, VERSION_ number(10,0) not null, NAME_ varchar2(255 char), ACTORID_ varchar2(255 char), SWIMLANE_ number(19,0), TASKMGMTINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_TASK (ID_ number(19,0) not null, NAME_ varchar2(255 char), DESCRIPTION_ varchar2(4000 char), PROCESSDEFINITION_ number(19,0), ISBLOCKING_ number(1,0), ISSIGNALLING_ number(1,0), CONDITION_ varchar2(255 char), DUEDATE_ varchar2(255 char), PRIORITY_ number(10,0), ACTORIDEXPRESSION_ varchar2(255 char), POOLEDACTORSEXPRESSION_ varchar2(255 char), TASKMGMTDEFINITION_ number(19,0), TASKNODE_ number(19,0), STARTSTATE_ number(19,0), ASSIGNMENTDELEGATION_ number(19,0), SWIMLANE_ number(19,0), TASKCONTROLLER_ number(19,0), primary key (ID_));
create table JBPM_TASKACTORPOOL (TASKINSTANCE_ number(19,0) not null, POOLEDACTOR_ number(19,0) not null, primary key (TASKINSTANCE_, POOLEDACTOR_));
create table JBPM_TASKCONTROLLER (ID_ number(19,0) not null, TASKCONTROLLERDELEGATION_ number(19,0), primary key (ID_));
create table JBPM_TASKINSTANCE (ID_ number(19,0) not null, CLASS_ char(1 char) not null, VERSION_ number(10,0) not null, NAME_ varchar2(255 char), DESCRIPTION_ varchar2(4000 char), ACTORID_ varchar2(255 char), CREATE_ timestamp, START_ timestamp, END_ timestamp, DUEDATE_ timestamp, PRIORITY_ number(10,0), ISCANCELLED_ number(1,0), ISSUSPENDED_ number(1,0), ISOPEN_ number(1,0), ISSIGNALLING_ number(1,0), ISBLOCKING_ number(1,0), TASK_ number(19,0), TOKEN_ number(19,0), PROCINST_ number(19,0), SWIMLANINSTANCE_ number(19,0), TASKMGMTINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_TOKEN (ID_ number(19,0) not null, VERSION_ number(10,0) not null, NAME_ varchar2(255 char), START_ timestamp, END_ timestamp, NODEENTER_ timestamp, NEXTLOGINDEX_ number(10,0), ISABLETOREACTIVATEPARENT_ number(1,0), ISTERMINATIONIMPLICIT_ number(1,0), ISSUSPENDED_ number(1,0), LOCK_ varchar2(255 char), NODE_ number(19,0), PROCESSINSTANCE_ number(19,0), PARENT_ number(19,0), SUBPROCESSINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_TOKENVARIABLEMAP (ID_ number(19,0) not null, VERSION_ number(10,0) not null, TOKEN_ number(19,0), CONTEXTINSTANCE_ number(19,0), primary key (ID_));
create table JBPM_TRANSITION (ID_ number(19,0) not null, NAME_ varchar2(255 char), DESCRIPTION_ varchar2(4000 char), PROCESSDEFINITION_ number(19,0), FROM_ number(19,0), TO_ number(19,0), CONDITION_ varchar2(255 char), FROMINDEX_ number(10,0), primary key (ID_));
create table JBPM_VARIABLEACCESS (ID_ number(19,0) not null, VARIABLENAME_ varchar2(255 char), ACCESS_ varchar2(255 char), MAPPEDNAME_ varchar2(255 char), SCRIPT_ number(19,0), PROCESSSTATE_ number(19,0), TASKCONTROLLER_ number(19,0), INDEX_ number(10,0), primary key (ID_));
create table JBPM_VARIABLEINSTANCE (ID_ number(19,0) not null, CLASS_ char(1 char) not null, VERSION_ number(10,0) not null, NAME_ varchar2(255 char), CONVERTER_ char(1 char), TOKEN_ number(19,0), TOKENVARIABLEMAP_ number(19,0), PROCESSINSTANCE_ number(19,0), BYTEARRAYVALUE_ number(19,0), DATEVALUE_ timestamp, DOUBLEVALUE_ double precision, LONGIDCLASS_ varchar2(255 char), LONGVALUE_ number(19,0), STRINGIDCLASS_ varchar2(255 char), STRINGVALUE_ varchar2(4000 char), TASKINSTANCE_ number(19,0), primary key (ID_));

create sequence hibernate_sequence;

create table WF_AUTHFIELD
(
  AUTHFIELDID        VARCHAR2(36) not null,
  NODEID             NUMBER(20),
  EXTPROCESSID       VARCHAR2(36) not null,
  FIELDNAME          VARCHAR2(50),
  FIELDDESC          VARCHAR2(100),
  ALLOWOPERATIONTYPE VARCHAR2(2),
  BOID               VARCHAR2(50),
  primary key (AUTHFIELDID)
);
create table WF_BOPROCESSDEF
(
  BPDEFID               VARCHAR2(36) not null,
  APPMODEL              VARCHAR2(50) not null,
  BPNAME                VARCHAR2(100) not null,
  PROCESSDEFINITIONNAME VARCHAR2(200) not null,
  BOID                  VARCHAR2(100),
  PROCESSTYPE           VARCHAR2(10) not null,
  CREATOR               VARCHAR2(36),
  CREATETIME            VARCHAR2(24),
  BPDESC                NVARCHAR2(500) not null,
  LASTMODIFYER          VARCHAR2(36),
  LASTMODIFYTIME        VARCHAR2(24),
  MEMO                  VARCHAR2(500),
  BOMETHODID            VARCHAR2(36),
  ORGANIZATIONID        VARCHAR2(36),
  ISDEFAULT             VARCHAR2(1),
  primary key (BPDEFID)
);
create table WF_NODEDEF
(
  NODEDEFID          VARCHAR2(36) not null,
  NODEID             NUMBER(20),
  BOID               VARCHAR2(50),
  NODEDEFINITIONNAME VARCHAR2(50),
  EXELOGICTYPE       VARCHAR2(1),
  BOMETHODID         VARCHAR2(50),
  AUTOSIGNAL         VARCHAR2(1),
  EXELOGIC           VARCHAR2(500),
  EXTPROCESSID       VARCHAR2(36) not null,
  EXAMINETYPE        VARCHAR2(1),
  PROCESSID          NUMBER(19),
  ISSENDMAIL         VARCHAR2(1) default '0',
  primary key (NODEDEFID)
);
create table WF_PROCESSDEF
(
  EXTPROCESSID          VARCHAR2(36) not null,
  PROCESSDEFINITIONNAME VARCHAR2(100) not null,
  PROCESSID             NUMBER(20) not null,
  VERSION               VARCHAR2(3) not null,
  ACTIVE                VARCHAR2(4) default 0 not null,
  primary key (EXTPROCESSID)
);
create table WF_PROCESSINS
(
  PROCESSINSID          VARCHAR2(36) not null,
  EXTPROCESSID      VARCHAR2(36) not null,
  PROCESSID             NUMBER(19),
  BUSINESSID            VARCHAR2(36),
  DEPARTMENTID          VARCHAR2(36),
  BOID                  VARCHAR2(50),
  PROCESSURL            VARCHAR2(500),
  INSCREATETIME         VARCHAR2(14),
  INSENDTIME            VARCHAR2(14),
  ENDNODENAME           VARCHAR2(50),
  BUSINESSNOTE          VARCHAR2(1000),
  PARENTCOMMONPROCESSID VARCHAR2(36),
  CREATORID             VARCHAR2(36),
  CREATORNAME           VARCHAR2(100),
  MODELNAME          VARCHAR2(100),
  primary key (PROCESSINSID)
);
create table WF_TASKACTOR
(
  TASKACTORID VARCHAR2(36) not null,
  EXTPROCESSID      VARCHAR2(36) not null,
  NODEID      NUMBER(20),
  ACTORID     VARCHAR2(36),
  ACTORTYPE   VARCHAR2(1),
  ACTORNAME   VARCHAR2(40),
  ASSIGNLOGIC VARCHAR2(50),
  PROCESSID   NUMBER(19),
  primary key (TASKACTORID)
);
create table WF_TASKINS
(
  TASKINSID       VARCHAR2(36) not null,
  TASKID          NUMBER(19),
  PROCESSID       NUMBER(19),
  BUSINESSID      VARCHAR2(36),
  TASKNAME        VARCHAR2(50),
  TASKDESCRIPTION VARCHAR2(100),
  TASKCREATETIME  VARCHAR2(14),
  TASKENDTIME     VARCHAR2(14),
  EXAMINE         VARCHAR2(200),
  EXAMINEPERSON   VARCHAR2(50),
  EXAMINERESULT   VARCHAR2(500),
  WITHTIME        VARCHAR2(200),
  PARENTBUSINESSID VARCHAR2(36),
  primary key (TASKINSID)
);
create table WF_TRANSITION
(
  EXTENDTRANSITIONID VARCHAR2(36) not null,
  EXTPROCESSID       VARCHAR2(36) not null,
  TRANSITIONNAME     VARCHAR2(50) not null,
  NODEID             VARCHAR2(50) not null,
  CONDITION          VARCHAR2(1000) not null,
  NEXTNODENAME       VARCHAR2(100),
  CONDITIONTYPE      VARCHAR2(1) not null,
  PROCESSID          NUMBER(19),
  JSONCONDITION      VARCHAR2(1000) not null,
  primary key (EXTENDTRANSITIONID)
);
create table WF_TASKCONDITION
(
  TSKACTCONDID        VARCHAR2(72) not null,
  EXTPROCESSID      VARCHAR2(36) not null,
  PROCESSID           NUMBER(19),
  NODEID              NUMBER(19),
  IFSTATEMENT         VARCHAR2(2000),
  THENSTATEMENTTYPE   VARCHAR2(20),
  THENSTATEMENT       VARCHAR2(1000),
  IFSTATEMENTJSON     VARCHAR2(2000),
  THENSTATEMENTTEXT   VARCHAR2(1000),
  THENSTATEMENTTYPEID VARCHAR2(36),
  primary key (TSKACTCONDID)
);
create table WF_THENSTATETYPE
(
  TYPEID         VARCHAR2(36) not null,
  STATEMENT      VARCHAR2(500) not null,
  TYPEDESC       VARCHAR2(100) not null,
  VALUEFECTHTYPE CHAR(1) not null,
  SHLPIDCOLUMN   VARCHAR2(100) not null,
  SHLPTEXTCOLUMN VARCHAR2(100) not null,
  SHLPNAME       VARCHAR2(100) not null,
  primary key (TYPEID)
);
create table WF_DYNATASKACTOR
(
  DYNATASKID VARCHAR2(36) not null,
  TASKID     VARCHAR2(36) not null,
  ACTORID    VARCHAR2(36) not null,
  BUSINESSID VARCHAR2(36) not null,
  ACTORTYPE  VARCHAR2(2),
  ACTORNAME  VARCHAR2(50),
  primary key (DYNATASKID)
);
create table YWF_PROCESSDEF
(
  PROCESSDEFID   VARCHAR2(108) default ' ' not null,
  PROCESSNAME    VARCHAR2(120) default ' ' not null,
  PROCESSDESC    VARCHAR2(150),
  VERSION        NUMBER(3) default 0 not null,
  ISPUBLIC       VARCHAR2(3) default '0' not null,
  CREATOR        VARCHAR2(108),
  CREATETIME     VARCHAR2(42),
  LASTMODIFYER   VARCHAR2(108),
  LASTMODIFYTIME VARCHAR2(42),
  DEFDOCUMENT    BLOB,
  POSDOCUMENT    BLOB,
  primary key (PROCESSDEFID)
);

CREATE OR REPLACE VIEW V_WF_CURRENT_JOB AS
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
  and task.id_ = tact.taskid
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
      yuserauthmove tact
WHERE  pins.processid = jpins.ID_
  and jpins.ID_ = jtins.PROCINST_
  and jtins.task_ = task.id_
  and node.id_ = task.tasknode_
  and node.id_ = tact.resourceid
  and jtins.end_ is null
  and tact.resourcetype='4';



CREATE OR REPLACE VIEW V_WF_HISTORY_JOB AS
SELECT DISTINCT tpi.processid,
      tpi.businessid,
      tpi.departmentid,
      tpi.boid,
      tpi.inscreatetime,
      tpi.insendtime,
      tpi.endnodename,
      tpi.businessnote,
      ta.actorid,
      ta.actortype,
      ta.actorname,
      pi.end_,
      pi.START_
FROM WF_PROCESSINS tpi,
      JBPM_PROCESSINSTANCE pi,
      WF_TASKACTOR ta
WHERE  tpi.processid = pi.ID_
  and pi.end_ is not null
  and pi.id_ = ta.processid
    /*****************************************************************/
  /*******         �����������������ͼ                         ******/
  /*******          �������ų���                              ******/
  /*******          ʱ�䣺2009-5-16                           ******/
  /*******                                                    ******/
  /*****************************************************************/;