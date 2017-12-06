package com.zyd.simple.mapper;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.zyd.simple.model.SysRole;
import com.zyd.simple.model.SysUser;

public class CacheTest extends BaseMapperTest {
	@Test
	public void testL1Cache() {
		SqlSession sqlSession = getSqlSession();
		SysUser user1 = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user1 = userMapper.selectById(1L);
			user1.setUserName("New Name");
			SysUser user2 = userMapper.selectById(1L);
			assertEquals("New Name", user2.getUserName());
			assertEquals(user1, user2);

		} finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession");
		sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user2 = userMapper.selectById(1L);
			assertEquals("New Name", user2.getUserName());
			assertNotEquals(user1, user2);
			userMapper.deleteById(2L);
			SysUser user3 = userMapper.selectById(1L);
			assertNotEquals(user2, user3);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testL2Cache() {
		SqlSession sqlSession = getSqlSession();
		SysRole role1 = null;
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			role1 = roleMapper.selectById(1L);
			role1.setRoleName("New Name");
			SysRole role2 = roleMapper.selectById(1L);
			assertEquals("New Name", role2.getRoleName());
			assertEquals(role1, role2);
		} finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession");
		sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role2 = roleMapper.selectById(1L);
			assertEquals("New Name", role2.getRoleName());
			assertNotEquals(role1, role2);
			SysRole role3 = roleMapper.selectById(1L);
			assertNotEquals(role3, role2);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testDirtyData() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			assertEquals("普通用户", user.getRole().getRoleName());
			System.out.println("角色名：" + user.getRole().getRoleName());
		} finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession1");
		sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setRoleName("脏数据");
			roleMapper.updateById(role);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession2");
		sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			SysRole role = roleMapper.selectById(2L);
			assertNotEquals("普通用户", user.getRole().getRoleName());
			assertEquals("脏数据", role.getRoleName());
			System.out.println("角色名：" + user.getRole().getRoleName());
			role.setRoleName("普通用户");
			roleMapper.updateById(role);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
