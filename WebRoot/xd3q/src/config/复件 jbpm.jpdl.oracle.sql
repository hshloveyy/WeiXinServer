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
create index IDX_ACTION_ACTNDL on JBPM_ACTION (ACTIONDELEGATION_);
create index IDX_ACTION_PROCDF on JBPM_ACTION (PROCESSDEFINITION_);
create index IDX_ACTION_EVENT on JBPM_ACTION (EVENT_);
alter table JBPM_ACTION add constraint FK_ACTION_REFACT foreign key (REFERENCEDACTION_) references JBPM_ACTION;
alter table JBPM_ACTION add constraint FK_CRTETIMERACT_TA foreign key (TIMERACTION_) references JBPM_ACTION;
alter table JBPM_ACTION add constraint FK_ACTION_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_ACTION add constraint FK_ACTION_EVENT foreign key (EVENT_) references JBPM_EVENT;
alter table JBPM_ACTION add constraint FK_ACTION_ACTNDEL foreign key (ACTIONDELEGATION_) references JBPM_DELEGATION;
alter table JBPM_ACTION add constraint FK_ACTION_EXPTHDL foreign key (EXCEPTIONHANDLER_) references JBPM_EXCEPTIONHANDLER;
alter table JBPM_BYTEARRAY add constraint FK_BYTEARR_FILDEF foreign key (FILEDEFINITION_) references JBPM_MODULEDEFINITION;
alter table JBPM_BYTEBLOCK add constraint FK_BYTEBLOCK_FILE foreign key (PROCESSFILE_) references JBPM_BYTEARRAY;
create index IDX_COMMENT_TSK on JBPM_COMMENT (TASKINSTANCE_);
create index IDX_COMMENT_TOKEN on JBPM_COMMENT (TOKEN_);
alter table JBPM_COMMENT add constraint FK_COMMENT_TOKEN foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_COMMENT add constraint FK_COMMENT_TSK foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE;
alter table JBPM_DECISIONCONDITIONS add constraint FK_DECCOND_DEC foreign key (DECISION_) references JBPM_NODE;
create index IDX_DELEG_PRCD on JBPM_DELEGATION (PROCESSDEFINITION_);
alter table JBPM_DELEGATION add constraint FK_DELEGATION_PRCD foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_EVENT add constraint FK_EVENT_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_EVENT add constraint FK_EVENT_TRANS foreign key (TRANSITION_) references JBPM_TRANSITION;
alter table JBPM_EVENT add constraint FK_EVENT_NODE foreign key (NODE_) references JBPM_NODE;
alter table JBPM_EVENT add constraint FK_EVENT_TASK foreign key (TASK_) references JBPM_TASK;
alter table JBPM_ID_GROUP add constraint FK_ID_GRP_PARENT foreign key (PARENT_) references JBPM_ID_GROUP;
alter table JBPM_ID_MEMBERSHIP add constraint FK_ID_MEMSHIP_GRP foreign key (GROUP_) references JBPM_ID_GROUP;
alter table JBPM_ID_MEMBERSHIP add constraint FK_ID_MEMSHIP_USR foreign key (USER_) references JBPM_ID_USER;
create index IDX_JOB_TSKINST on JBPM_JOB (TASKINSTANCE_);
create index IDX_JOB_TOKEN on JBPM_JOB (TOKEN_);
create index IDX_JOB_PRINST on JBPM_JOB (PROCESSINSTANCE_);
alter table JBPM_JOB add constraint FK_JOB_PRINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_JOB add constraint FK_JOB_ACTION foreign key (ACTION_) references JBPM_ACTION;
alter table JBPM_JOB add constraint FK_JOB_TOKEN foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_JOB add constraint FK_JOB_NODE foreign key (NODE_) references JBPM_NODE;
alter table JBPM_JOB add constraint FK_JOB_TSKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE;
alter table JBPM_LOG add constraint FK_LOG_SOURCENODE foreign key (SOURCENODE_) references JBPM_NODE;
alter table JBPM_LOG add constraint FK_LOG_DESTNODE foreign key (DESTINATIONNODE_) references JBPM_NODE;
alter table JBPM_LOG add constraint FK_LOG_TOKEN foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_LOG add constraint FK_LOG_TRANSITION foreign key (TRANSITION_) references JBPM_TRANSITION;
alter table JBPM_LOG add constraint FK_LOG_TASKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE;
alter table JBPM_LOG add constraint FK_LOG_CHILDTOKEN foreign key (CHILD_) references JBPM_TOKEN;
alter table JBPM_LOG add constraint FK_LOG_OLDBYTES foreign key (OLDBYTEARRAY_) references JBPM_BYTEARRAY;
alter table JBPM_LOG add constraint FK_LOG_SWIMINST foreign key (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE;
alter table JBPM_LOG add constraint FK_LOG_NEWBYTES foreign key (NEWBYTEARRAY_) references JBPM_BYTEARRAY;
alter table JBPM_LOG add constraint FK_LOG_ACTION foreign key (ACTION_) references JBPM_ACTION;
alter table JBPM_LOG add constraint FK_LOG_VARINST foreign key (VARIABLEINSTANCE_) references JBPM_VARIABLEINSTANCE;
alter table JBPM_LOG add constraint FK_LOG_NODE foreign key (NODE_) references JBPM_NODE;
alter table JBPM_LOG add constraint FK_LOG_PARENT foreign key (PARENT_) references JBPM_LOG;
create index IDX_MODDEF_PROCDF on JBPM_MODULEDEFINITION (PROCESSDEFINITION_);
alter table JBPM_MODULEDEFINITION add constraint FK_MODDEF_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_MODULEDEFINITION add constraint FK_TSKDEF_START foreign key (STARTTASK_) references JBPM_TASK;
create index IDX_MODINST_PRINST on JBPM_MODULEINSTANCE (PROCESSINSTANCE_);
alter table JBPM_MODULEINSTANCE add constraint FK_MODINST_PRCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_MODULEINSTANCE add constraint FK_TASKMGTINST_TMD foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION;
create index IDX_PSTATE_SBPRCDEF on JBPM_NODE (SUBPROCESSDEFINITION_);
create index IDX_NODE_PROCDEF on JBPM_NODE (PROCESSDEFINITION_);
create index IDX_NODE_ACTION on JBPM_NODE (ACTION_);
create index IDX_NODE_SUPRSTATE on JBPM_NODE (SUPERSTATE_);
alter table JBPM_NODE add constraint FK_DECISION_DELEG foreign key (DECISIONDELEGATION) references JBPM_DELEGATION;
alter table JBPM_NODE add constraint FK_NODE_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_NODE add constraint FK_NODE_ACTION foreign key (ACTION_) references JBPM_ACTION;
alter table JBPM_NODE add constraint FK_PROCST_SBPRCDEF foreign key (SUBPROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_NODE add constraint FK_NODE_SCRIPT foreign key (SCRIPT_) references JBPM_ACTION;
alter table JBPM_NODE add constraint FK_NODE_SUPERSTATE foreign key (SUPERSTATE_) references JBPM_NODE;
create index IDX_TSKINST_SWLANE on JBPM_POOLEDACTOR (SWIMLANEINSTANCE_);
create index IDX_PLDACTR_ACTID on JBPM_POOLEDACTOR (ACTORID_);
alter table JBPM_POOLEDACTOR add constraint FK_POOLEDACTOR_SLI foreign key (SWIMLANEINSTANCE_) references JBPM_SWIMLANEINSTANCE;
create index IDX_PROCDEF_STRTST on JBPM_PROCESSDEFINITION (STARTSTATE_);
alter table JBPM_PROCESSDEFINITION add constraint FK_PROCDEF_STRTSTA foreign key (STARTSTATE_) references JBPM_NODE;
create index IDX_PROCIN_SPROCTK on JBPM_PROCESSINSTANCE (SUPERPROCESSTOKEN_);
create index IDX_PROCIN_ROOTTK on JBPM_PROCESSINSTANCE (ROOTTOKEN_);
create index IDX_PROCIN_PROCDEF on JBPM_PROCESSINSTANCE (PROCESSDEFINITION_);
create index IDX_PROCIN_KEY on JBPM_PROCESSINSTANCE (KEY_);
alter table JBPM_PROCESSINSTANCE add constraint FK_PROCIN_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_PROCESSINSTANCE add constraint FK_PROCIN_ROOTTKN foreign key (ROOTTOKEN_) references JBPM_TOKEN;
alter table JBPM_PROCESSINSTANCE add constraint FK_PROCIN_SPROCTKN foreign key (SUPERPROCESSTOKEN_) references JBPM_TOKEN;
create index IDX_RTACTN_ACTION on JBPM_RUNTIMEACTION (ACTION_);
create index IDX_RTACTN_PRCINST on JBPM_RUNTIMEACTION (PROCESSINSTANCE_);
alter table JBPM_RUNTIMEACTION add constraint FK_RTACTN_PROCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_RUNTIMEACTION add constraint FK_RTACTN_ACTION foreign key (ACTION_) references JBPM_ACTION;
alter table JBPM_SWIMLANE add constraint FK_SWL_ASSDEL foreign key (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION;
alter table JBPM_SWIMLANE add constraint FK_SWL_TSKMGMTDEF foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION;
create index IDX_SWIMLINST_SL on JBPM_SWIMLANEINSTANCE (SWIMLANE_);
alter table JBPM_SWIMLANEINSTANCE add constraint FK_SWIMLANEINST_TM foreign key (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE;
alter table JBPM_SWIMLANEINSTANCE add constraint FK_SWIMLANEINST_SL foreign key (SWIMLANE_) references JBPM_SWIMLANE;
create index IDX_TASK_PROCDEF on JBPM_TASK (PROCESSDEFINITION_);
create index IDX_TASK_TSKNODE on JBPM_TASK (TASKNODE_);
create index IDX_TASK_TASKMGTDF on JBPM_TASK (TASKMGMTDEFINITION_);
alter table JBPM_TASK add constraint FK_TASK_STARTST foreign key (STARTSTATE_) references JBPM_NODE;
alter table JBPM_TASK add constraint FK_TASK_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_TASK add constraint FK_TASK_ASSDEL foreign key (ASSIGNMENTDELEGATION_) references JBPM_DELEGATION;
alter table JBPM_TASK add constraint FK_TASK_SWIMLANE foreign key (SWIMLANE_) references JBPM_SWIMLANE;
alter table JBPM_TASK add constraint FK_TASK_TASKNODE foreign key (TASKNODE_) references JBPM_NODE;
alter table JBPM_TASK add constraint FK_TASK_TASKMGTDEF foreign key (TASKMGMTDEFINITION_) references JBPM_MODULEDEFINITION;
alter table JBPM_TASK add constraint FK_TSK_TSKCTRL foreign key (TASKCONTROLLER_) references JBPM_TASKCONTROLLER;
alter table JBPM_TASKACTORPOOL add constraint FK_TASKACTPL_TSKI foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE;
alter table JBPM_TASKACTORPOOL add constraint FK_TSKACTPOL_PLACT foreign key (POOLEDACTOR_) references JBPM_POOLEDACTOR;
alter table JBPM_TASKCONTROLLER add constraint FK_TSKCTRL_DELEG foreign key (TASKCONTROLLERDELEGATION_) references JBPM_DELEGATION;
create index IDX_TSKINST_TMINST on JBPM_TASKINSTANCE (TASKMGMTINSTANCE_);
create index IDX_TSKINST_SLINST on JBPM_TASKINSTANCE (SWIMLANINSTANCE_);
create index IDX_TASKINST_TOKN on JBPM_TASKINSTANCE (TOKEN_);
create index IDX_TASK_ACTORID on JBPM_TASKINSTANCE (ACTORID_);
create index IDX_TASKINST_TSK on JBPM_TASKINSTANCE (TASK_, PROCINST_);
alter table JBPM_TASKINSTANCE add constraint FK_TSKINS_PRCINS foreign key (PROCINST_) references JBPM_PROCESSINSTANCE;
alter table JBPM_TASKINSTANCE add constraint FK_TASKINST_TMINST foreign key (TASKMGMTINSTANCE_) references JBPM_MODULEINSTANCE;
alter table JBPM_TASKINSTANCE add constraint FK_TASKINST_TOKEN foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_TASKINSTANCE add constraint FK_TASKINST_SLINST foreign key (SWIMLANINSTANCE_) references JBPM_SWIMLANEINSTANCE;
alter table JBPM_TASKINSTANCE add constraint FK_TASKINST_TASK foreign key (TASK_) references JBPM_TASK;
create index IDX_TOKEN_PARENT on JBPM_TOKEN (PARENT_);
create index IDX_TOKEN_PROCIN on JBPM_TOKEN (PROCESSINSTANCE_);
create index IDX_TOKEN_NODE on JBPM_TOKEN (NODE_);
create index IDX_TOKEN_SUBPI on JBPM_TOKEN (SUBPROCESSINSTANCE_);
alter table JBPM_TOKEN add constraint FK_TOKEN_SUBPI foreign key (SUBPROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_TOKEN add constraint FK_TOKEN_PROCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_TOKEN add constraint FK_TOKEN_NODE foreign key (NODE_) references JBPM_NODE;
alter table JBPM_TOKEN add constraint FK_TOKEN_PARENT foreign key (PARENT_) references JBPM_TOKEN;
create index IDX_TKVVARMP_TOKEN on JBPM_TOKENVARIABLEMAP (TOKEN_);
create index IDX_TKVARMAP_CTXT on JBPM_TOKENVARIABLEMAP (CONTEXTINSTANCE_);
alter table JBPM_TOKENVARIABLEMAP add constraint FK_TKVARMAP_TOKEN foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_TOKENVARIABLEMAP add constraint FK_TKVARMAP_CTXT foreign key (CONTEXTINSTANCE_) references JBPM_MODULEINSTANCE;
create index IDX_TRANS_PROCDEF on JBPM_TRANSITION (PROCESSDEFINITION_);
create index IDX_TRANSIT_FROM on JBPM_TRANSITION (FROM_);
create index IDX_TRANSIT_TO on JBPM_TRANSITION (TO_);
alter table JBPM_TRANSITION add constraint FK_TRANSITION_FROM foreign key (FROM_) references JBPM_NODE;
alter table JBPM_TRANSITION add constraint FK_TRANS_PROCDEF foreign key (PROCESSDEFINITION_) references JBPM_PROCESSDEFINITION;
alter table JBPM_TRANSITION add constraint FK_TRANSITION_TO foreign key (TO_) references JBPM_NODE;
alter table JBPM_VARIABLEACCESS add constraint FK_VARACC_PROCST foreign key (PROCESSSTATE_) references JBPM_NODE;
alter table JBPM_VARIABLEACCESS add constraint FK_VARACC_SCRIPT foreign key (SCRIPT_) references JBPM_ACTION;
alter table JBPM_VARIABLEACCESS add constraint FK_VARACC_TSKCTRL foreign key (TASKCONTROLLER_) references JBPM_TASKCONTROLLER;
create index IDX_VARINST_TK on JBPM_VARIABLEINSTANCE (TOKEN_);
create index IDX_VARINST_TKVARMP on JBPM_VARIABLEINSTANCE (TOKENVARIABLEMAP_);
create index IDX_VARINST_PRCINS on JBPM_VARIABLEINSTANCE (PROCESSINSTANCE_);
alter table JBPM_VARIABLEINSTANCE add constraint FK_VARINST_PRCINST foreign key (PROCESSINSTANCE_) references JBPM_PROCESSINSTANCE;
alter table JBPM_VARIABLEINSTANCE add constraint FK_VARINST_TKVARMP foreign key (TOKENVARIABLEMAP_) references JBPM_TOKENVARIABLEMAP;
alter table JBPM_VARIABLEINSTANCE add constraint FK_VARINST_TK foreign key (TOKEN_) references JBPM_TOKEN;
alter table JBPM_VARIABLEINSTANCE add constraint FK_BYTEINST_ARRAY foreign key (BYTEARRAYVALUE_) references JBPM_BYTEARRAY;
alter table JBPM_VARIABLEINSTANCE add constraint FK_VAR_TSKINST foreign key (TASKINSTANCE_) references JBPM_TASKINSTANCE;
create sequence hibernate_sequence;

create table WF_AUTHFIELD
(
  AUTHFIELDID        VARCHAR2(36) not null,
  TASKID             NUMBER(20),
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
  PROCESSID             NUMBER(19),
  BUSINESSID            VARCHAR2(36),
  DEPARTMENTID          VARCHAR2(36),
  BOID                  VARCHAR2(50),
  PROCESSURL            VARCHAR2(200),
  INSCREATETIME         VARCHAR2(14),
  INSENDTIME            VARCHAR2(14),
  ENDNODENAME           VARCHAR2(50),
  BUSINESSNOTE          VARCHAR2(1000),
  PARENTCOMMONPROCESSID VARCHAR2(36),
  CREATORID             VARCHAR2(36),
  CREATORNAME           VARCHAR2(100),
  primary key (PROCESSINSID)
);
create table WF_TASKACTOR
(
  TASKACTORID VARCHAR2(36) not null,
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
  primary key (TASKINSID)
);
create table WF_TRANSITION
(
  EXTENDTRANSITIONID VARCHAR2(36) not null,
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

comment on column YWF_PROCESSDEF.ISPUBLIC
  is '0 未激活 1 激活';
insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('1', '1', '用户', '1', 'USERID', 'USERNAME', 'YHUSER');

insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('2', '2', '用户组', '1', 'USERGROUPID', 'USERGROUPNAME', 'YHUSERGROUP');

insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('3', '3', '角色', '1', 'ROLEID', 'ROLEDESC', 'YHROLE');

insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('4', '4', '组织', '1', 'ORGANIZATIONID', 'ORGANIZATIONNAME', 'YHORGANIZATION');

insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('5', 'select userid from yuser where userid=''1''', '部门负责人', '2', ' ', ' ', ' ');

insert into wf_thenstatetype (TYPEID, STATEMENT, TYPEDESC, VALUEFECTHTYPE, SHLPIDCOLUMN, SHLPTEXTCOLUMN, SHLPNAME)
values ('6', '动态SQL', '动态SQL', '3', ' ', ' ', ' ');

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
  /*******         工作流已完成事项视图                         ******/
  /*******          创建：张崇镇                              ******/
  /*******          时间：2009-5-16                           ******/
  /*******                                                    ******/
  /*****************************************************************/;