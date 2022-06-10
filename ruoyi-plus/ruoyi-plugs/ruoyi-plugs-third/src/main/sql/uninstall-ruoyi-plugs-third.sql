DROP TABLE IF EXISTS `plugs_third_ai_his`;
DROP TABLE IF EXISTS `plugs_third_sms_his`;


SELECT @parentId := (select menu_id from sys_menu where menu_name='第三方');
delete from sys_menu where parent_id=@parentId;
delete from sys_menu where menu_id=@parentId;

delete from sys_dict_type where dict_type='ai_type';
delete from sys_dict_type where dict_type='baidu_push_type';

delete from sys_dict_data where dict_type='ai_type';
delete from sys_dict_data where dict_type='baidu_push_type';
