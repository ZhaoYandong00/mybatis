package com.zyd.simple.model;

/**
 * 
 * 用户角色关系表
 *
 */
public class SysUserRole {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
