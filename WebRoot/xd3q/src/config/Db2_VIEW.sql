CREATE VIEW YVEXAMINE AS
SELECT a.outward,
       a.outward3,
       a.certificatedate,
       b.memo,
       b.outward4,
       b.viitem
  FROM YEXAMINE a, YEXAMINEITEM b
 WHERE a.VI = b.VI
 
 
 
create view yvfeedbackanaly as
select a.mandt,        
a.createtime,        
a.materielid,        
a.RESPONSETIME,        
a.SOLUTIONTIME  
from 
(select a.mandt,        
a.createtime,        
a.materielid,        
sum(a.feedbacktimes) RESPONSETIME,        
sum(a.solutiontimes) SOLUTIONTIME  
from (select c.mandt,                
substr(c.createtime, 1, 8) createtime,                
c.materielid,                
1 feedbacktimes,                
case when trim(c.solutiontime) is null then 1 else 0 end solutiontimes         
from ycustfeedback c          
inner join ymateriel m on c.materielid = m.materielid) a 
group by a.mandt, a.materielid, a.createtime) a 


create view yvproductanalyz as
select m.mandt,        
m.materielid,        
m.materielname,        
c.categorydesc,        
s.documentdate,        
case when SUM(p.totalamount) is null then 0 else SUM(p.totalamount) end totalamount   
from ymateriel m   
left outer join yproductsales p on p.materielid = m.materielid   
left outer join ymaterielcategor c on m.materielcategory = c.materielcategory   
left outer join ysalesorder s on p.salesorderid = s.salesorderid  
group by m.mandt,           s.documentdate,           m.materielid,           m.materielno,           m.materielname,           c.categorydesc  

 
 
 
create view yvsalesoppanaly as
select s.mandt,
       s.salesoppotunityi,
       s.startdate,
       s.personnelid,
       s.salesorg,
       s.salesforecast,
       s.chanceofsuccess
  from ysalesoppotunity s


CREATE VIEW YV_MYAUTHRESOURCEROLE AS
SELECT T."USERID",T."MANDT",T."ROLEID",T."ROLENAME",T."ROLEDESC",T."CREATOR",T."CREATETIME",T."LASTMODIFYOR",T."LASTMODIFYTIME",T."MODIFYUSERNAME",T."ISAUTHREMOVE"
  FROM (select a.userid,
               b.*,
               c.UserName as ModifyUserName,
               'Y' as isAuthRemove
          FROM YUSERROLE a, YROLE b, YUSER c
         WHERE a.Roleid = b.Roleid
           and b.lastmodifyor = c.userId
           and a.roleId in (SELECT k.roleId
                              from YUSERAUTHMOVE k
                             where k.fromuserid = a.userid
                               and  k.resourcetype = '1'
                               and k.mandt = a.MANDT)
        union
        select a.userid,
               b.*,
               c.UserName as ModifyUserName,
               'N' as isAuthRemove
          FROM YUSERROLE a, YROLE b, YUSER c
         WHERE a.Roleid = b.Roleid
           and b.lastmodifyor = c.userId
           and a.roleId not in (SELECT k.roleId
                              from YUSERAUTHMOVE k
                             where k.fromuserid = a.userid
                               and  k.resourcetype = '1'
                               and k.mandt = a.MANDT)
        union
        select b.userid,
               d.*,
               f.UserName as ModifyUserName,
               'Y' as isAuthRemove
          from YUSERGROUP     a,
               YUSERGROUPUSER b,
               YUSERGROUPROLE c,
               YROLE          d,
               YUSER          f
         where
           a.usergroupid = b.usergroupid
           and a.mandt = b.mandt
           and b.usergroupid = c.usergroupid
           and b.mandt = a.mandt
           and a.mandt = c.mandt
           and c.roleid = d.roleid
           and d.mandt = a.mandt
           and f.mandt = a.mandt
           and d.lastmodifyor = f.userid
           and d.roleId in (SELECT k.roleId
                              from YUSERAUTHMOVE k
                             where k.fromuserid =  b.userid
                               and k.mandt =  b.mandt
                               and k.resourcetype = '1')
        union
        select b.userid,
               d.*,
               f.UserName as ModifyUserName,
               'N' as isAuthRemove
          from YUSERGROUP     a,
               YUSERGROUPUSER b,
               YUSERGROUPROLE c,
               YROLE          d,
               YUSER          f
         where
            a.usergroupid = b.usergroupid
           and a.mandt = b.mandt
           and b.usergroupid = c.usergroupid
           and b.mandt = a.mandt
           and a.mandt = c.mandt
           and c.roleid = d.roleid
           and d.mandt = a.mandt
           and d.lastmodifyor = f.userid
           and f.mandt = a.mandt
           and d.roleId not in (SELECT k.roleId
                              from YUSERAUTHMOVE k
                             where k.fromuserid =  b.userid
                               and k.mandt =  b.mandt
                               and k.resourcetype = '1')) t

 
 
 
create view yv_myauthresourceworkflow as
select distinct t.bpname,
                        t.bpdefid,
                        t.nodeName,
                        t.Nodeid,
                        t.processid
          from (select c.*, f.bpname, f.bpdefid, g.name_ as nodeName
                  from (select a.Nodeid, a.processid
                          from WF_NODEDEF a) c
                  left outer join WF_PROCESSDEF d on d.processid = c.processid and d.active = 'Y'
                  left outer join WF_BOPROCESSDEF f on d.processdefinitionname = f.processdefinitionname
                  left outer join JBPM_NODE g on g.id_ = c.nodeid) t
 union
    select distinct t.bpname,
                        t.bpdefid,
                        t.nodeName,
                        t.Nodeid,
                        t.processid
          from (
select a.*, f.bpname, f.bpdefid, g.name_ as nodeName from wf_taskactor a
  left outer join WF_PROCESSDEF d on d.processid = a.processid and d.active = 'Y'
  left outer join WF_BOPROCESSDEF f on d.processdefinitionname = f.processdefinitionname
  left outer join JBPM_NODE g on g.id_ = a.nodeid) t



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
      yuserauthmove tact
WHERE  pins.processid = jpins.ID_
  and jpins.ID_ = jtins.PROCINST_
  and jtins.task_ = task.id_
  and node.id_ = task.tasknode_
  and trim(char(node.id_)) = tact.resourceid
  and jtins.end_ is null
  and tact.resourcetype='4'