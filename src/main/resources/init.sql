CREATE TABLE SYS_LOG (
   ID NUMBER(20) NOT NULL ,
   USERNAME varchar (100) NULL ,
   OPERATION varchar (255) NULL ,
   TIME NUMBER(11) NULL ,
   METHOD varchar (255) NULL ,
   PARAMS varchar (255) NULL ,
   IP varchar (255) NULL ,
   CREATE_TIME DATE NULL 
);