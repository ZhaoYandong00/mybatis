package com.zyd.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.zyd.simple.model.SysRole;

public interface RoleMapper {

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select({ "select id, role_name roleName,enabled,create_by createBy,create_time createTime", "from sys_role",
			"where id=#{id}" })
	SysRole selectById(Long id);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Results(id = "roleResultMap", value = { @Result(property = "id", column = "id", id = true),
			@Result(property = "roleName", column = "role_name"), @Result(property = "enabled", column = "enabled"),

			@Result(property = "createBy ", column = "create_by "),
			@Result(property = "createTime", column = "create_Time") })
	@Select({ "select id, role_name,enabled,create_by,create_time", "from sys_role", "where id=#{id}" })
	SysRole selectById2(Long id);

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	@ResultMap("roleResultMap")
	@Select("select * from sys_role")
	List<SysRole> selectAll();

	/**
	 * 插入角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Insert({ "insert into sys_role(id,role_name,enabled,create_by,create_time)",
			"values(#{id},#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})" })
	int insert(SysRole sysRole);

	/**
	 * 插入角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Insert({ "insert into sys_role(role_name,enabled,create_by,create_time)",
			"values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert2(SysRole sysRole);

	/**
	 * 插入角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Insert({ "insert into sys_role(role_name,enabled,create_by,create_time)",
			"values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})" })
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
	int insert3(SysRole sysRole);

	/**
	 * 更新角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Update({ "update sys_role ", "set role_name = #{roleName},", "enabled = #{enabled},", "create_by = #{createBy},",
			"create_time = #{createTime,jdbcType=TIMESTAMP}", "where id = #{id}" })
	int updateById(SysRole sysRole);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_role where id=#{id}")
	int deleteById(Long id);

	/**
	 * 查询所有角色对应的权限
	 * 
	 * @return
	 */
	List<SysRole> selectAllRoleAnPrivileges();

	/**
	 * 根据用户ID获取用户的角色信息
	 * 
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRoleByUserIdChoose(Long userId);

}
