DROP TABLE IF EXISTS `spider_config`;
DROP TABLE IF EXISTS `spider_field`;
DROP TABLE IF EXISTS `spider_filed_rule`;
DROP TABLE IF EXISTS `spider_mission`;

SELECT @parentId := (select menu_id from sys_menu where menu_name='爬虫模块');
delete from sys_menu where parent_id=@parentId;
delete from sys_menu where menu_id=@parentId;

delete from sys_config where config_key='webdriver.chrome.driver';

delete from sys_dict_type where dict_type='spider_mission_status';
delete from sys_dict_type where dict_type='spider_exit_way';
delete from sys_dict_type where dict_type='spider_extract_type';
delete from sys_dict_type where dict_type='field_value_process_type';
delete from sys_dict_type where dict_type='spider_field_high_setting';

delete from sys_dict_data where dict_type='spider_mission_status';
delete from sys_dict_data where dict_type='spider_exit_way';
delete from sys_dict_data where dict_type='spider_extract_type';
delete from sys_dict_data where dict_type='field_value_process_type';
delete from sys_dict_data where dict_type='spider_field_high_setting';
