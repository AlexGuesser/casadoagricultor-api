USE casa_do_agricultor;

INSERT INTO `user` (`last_user`,`name`,`last_name`,`email`,`password`)
values
(1,'admin','','admin','pwdAdmin'); 

INSERT INTO `user` (`last_user`,`name`,`last_name`,`email`,`password`)
values
(1,'tech_job_processor','','tech_job_processor','pwdTech_job_processor');

INSERT INTO `ceasa` (`last_user`,`name`,`city`,`state`,`country`)
values
(1,'Ceasa de São José, SC','São José','SC','BR');

INSERT INTO `processing_errors_warnings`
(`last_user`,`error_or_warning`,`code`,`problem`,`solution`)
VALUES
(1,'e','PARSING_DATE_COTATION_ERROR','There was an exception while parsing the date of cotation.',"Analyze that day's file cotation and adjust the code.");

INSERT INTO `processing_errors_warnings`
(`last_user`,`error_or_warning`,`code`,`problem`,`solution`)
VALUES
(1,'e','TYPE_NOT_IN_ENUM_ERROR','Some type was not found inside TypeListEnum.',"Analyze PDF and insert this new type.");

INSERT INTO `processing_errors_warnings`
(`last_user`,`error_or_warning`,`code`,`problem`,`solution`)
VALUES
(1,'e','PACKING_NOT_IN_ENUM_ERROR','Some packing was not found inside PackagingListEnum.',"Analyze PDF and insert this new packing.");

INSERT INTO `processing_errors_warnings`
(`last_user`,`error_or_warning`,`code`,`problem`,`solution`)
VALUES
(1,'e','UNKNOWN_ERROR','Some unexpected exception occured while processing.',"Analyze logs, file and code!");

INSERT INTO `processing_errors_warnings`
(`last_user`,`error_or_warning`,`code`,`problem`,`solution`)
VALUES
(1,'e','FILE_NOT_FOUND_ERROR','The cotation file searched was not found!',"Analyze if the specific file does exists at Ceasa site, if don't it's OK, if exists analyze!");