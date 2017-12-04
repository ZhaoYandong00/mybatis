#根据用户id查询用户的其他信息
DROP PROCEDURE IF EXISTS `select_user_by_id`;
delimiter ;;
CREATE PROCEDURE `select_user_by_id`(
  IN userId BIGINT,
  OUT userName VARCHAR(50),
  OUT userPassWord VARCHAR(50),
  OUT userEmail VARCHAR(50),
  OUT userInfo TEXT,
  OUT headImg BLOB,
  OUT createTime DATETIME
)
BEGIN
  SELECT user_name,user_password,user_email,user_info,head_img,create_time
  INTO userName,userPassWord,userEmail,userInfo,headImg,createTime
  FROM sys_user
  WHERE id = userId;
END
;;
delimiter ;

#简单根据用户名和分页参数进行查询，返回总数和分页数据
DROP PROCEDURE IF EXISTS `select_user_page`;
delimiter ;;
CREATE PROCEDURE `select_user_page`(
  IN userName VARCHAR(50),
  IN _offset BIGINT,
  IN _limit BIGINT,
  OUT total BIGINT
)
BEGIN
  #查询数据总数
  SELECT COUNT(*) INTO total
  FROM sys_user 
  WHERE user_name LIKE CONCAT('%',userName,'%');
SELECT 
    *
FROM
    sys_user
WHERE
    user_name LIKE CONCAT('%', userName, '%')
LIMIT _OFFSET , _LIMIT;
END
;;
delimiter ;

#保存用户信息和角色关联信息
DROP PROCEDURE IF EXISTS `insert_user_and_roles`;
delimiter ;;
CREATE PROCEDURE `insert_user_and_roles`(
  OUT userId BIGINT,
  IN userName VARCHAR(50),
  IN userPassWord VARCHAR(50),
  IN userEmail VARCHAR(50),
  IN userInfo TEXT,
  IN headImg BLOB,
  OUT createTime DATETIME,
  IN roleIds VARCHAR(200)
)
BEGIN
  #设置当前时间
  SET createTime = NOW();
  #插入数据
  INSERT INTO sys_user(user_name,user_password,user_email,user_info,head_img,create_time)
  VALUES(userName,userPassWord,userEmail,userInfo,headImg,createTime);
  SELECT LAST_INSERT_ID() INTO userId;
  #保存用户和角色关系数据
  SET roleIds = CONCAT(',',roleIds,',');
  INSERT INTO sys_user_role(user_id,role_id)
  SELECT userId,id FROM sys_role
  WHERE INSTR(roleIds,CONCAT(',',id,','))>0;
END
;;
delimiter ;

#删除用户信息和角色关联信息
DROP PROCEDURE IF EXISTS `delete_user_by_id`;
delimiter ;;
CREATE PROCEDURE `delete_user_by_id`(
  IN userId BIGINT
)
BEGIN
  DELETE FROM sys_user_role WHERE user_id = userId;
DELETE FROM sys_user 
WHERE
    id = userId;
END
;;
delimiter ;