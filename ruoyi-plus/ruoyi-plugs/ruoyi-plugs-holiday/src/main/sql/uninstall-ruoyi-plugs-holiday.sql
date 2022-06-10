DROP TABLE IF EXISTS `plugs_sys_holiday`;

SELECT @parentId := (select menu_id from sys_menu where menu_name='节假日配置');
delete from sys_menu where parent_id=@parentId;
delete from sys_menu where menu_id=@parentId;

