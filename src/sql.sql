create table sample(Name varchar(50),Age int)

insert into sample(Name,age) values ('Valli',25)

select * from SYSTEM.ToDoApp

CREATE TABLE "ToDoApp"
( "Id" number(10) NOT NULL,
  "Name" varchar2(50) NOT NULL,
  "Date" date,
  "Priority" varchar2(20),
  constraint "ToDoApp_pk" primary key ("Id")
)

select * from ToDo
insert into ToDo values (2,'Complete Task',sysdate,'Medium')
select sysdate from dual

select * from ToDoApp
drop table ToDoList

CREATE PUBLIC SYNONYM todoapp
FOR system.ToDoAPP

DROP PUBLIC SYNONYM todoapp; 