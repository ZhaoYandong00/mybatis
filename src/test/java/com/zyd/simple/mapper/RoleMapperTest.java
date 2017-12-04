package com.zyd.simple.mapper;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import com.zyd.simple.mapper.RoleMapper;
import com.zyd.simple.model.SysPrivilege;
import com.zyd.simple.model.SysRole;

public class RoleMapperTest extends BaseMapperTest {

	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取RoleMapper接口
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 调用selectById,查询id=1的角色
			SysRole role = roleMapper.selectById(1L);
			// role不为空
			assertNotNull(role);
			// 角色名字为管理员
			assertEquals("管理员", role.getRoleName());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectById2() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取RoleMapper接口
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 调用selectById,查询id=1的角色
			SysRole role = roleMapper.selectById2(1L);
			// role不为空
			assertNotNull(role);
			// 角色名字为管理员
			assertEquals("管理员", role.getRoleName());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取RoleMapper接口
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 调用selectById,查询id=1的角色
			List<SysRole> roleList = roleMapper.selectAll();
			// role不为空
			assertNotNull(roleList);
			assertTrue(roleList.size() > 0);
			assertNotNull(roleList.get(0).getRoleName());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 创建一个Role对象
			SysRole role = new SysRole();

			role.setRoleName("用户");
			role.setEnabled(0);
			role.setCreateBy(1L);
			role.setCreateTime(new Date());
			int result = roleMapper.insert(role);
			assertEquals(1, result);
			assertNull(role.getId());
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testInsert2() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 创建一个Role对象
			SysRole role = new SysRole();

			role.setRoleName("用户");
			role.setEnabled(0);
			role.setCreateBy(1L);
			role.setCreateTime(new Date());
			int result = roleMapper.insert2(role);
			assertEquals(1, result);
			assertNotNull(role.getId());
			System.out.println(role.getId());
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testInsert3() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 创建一个Role对象
			SysRole role = new SysRole();
			role.setRoleName("用户");
			role.setEnabled(0);
			role.setCreateBy(1L);
			role.setCreateTime(new Date());
			int result = roleMapper.insert3(role);
			assertEquals(1, result);
			assertNotNull(role.getId());
			System.out.println(role.getId());
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateById() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 从sys_role获取一个对象
			SysRole role = roleMapper.selectById(1L);
			assertEquals("管理员", role.getRoleName());
			// 修改名字
			role.setRoleName("超级管理员");
			int result = roleMapper.updateById(role);
			assertEquals(1, result);
			// 查询修改后的数据
			role = roleMapper.selectById(1L);
			Assert.assertEquals("超级管理员", role.getRoleName());

		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testDeleteById() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			// 从sys_role获取一个对象
			SysRole role = roleMapper.selectById(1L);
			// 不为空
			assertNotNull(role);
			// 删除
			assertEquals(1, roleMapper.deleteById(1L));
			// 为空
			assertNull(roleMapper.selectById(1L));

		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectAllRoleAnPrivileges() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> roleList = roleMapper.selectAllRoleAnPrivileges();
			for (SysRole role : roleList) {
				System.out.println("角色名：" + role.getRoleName());
				for (SysPrivilege privilege : role.getPrivilegeList()) {
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRoleByUserIdChoose() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setEnabled(0);
			roleMapper.updateById(role);
			List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
			for (SysRole r : roleList) {
				System.out.println("角色名：" + r.getRoleName());
				if (r.getId().equals(1L)) {
					assertNotNull(r.getPrivilegeList());
				} else if (r.getId().equals(2L)) {
					assertNull(r.getPrivilegeList());
					continue;
				}
				for (SysPrivilege privilege : r.getPrivilegeList()) {
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
}
