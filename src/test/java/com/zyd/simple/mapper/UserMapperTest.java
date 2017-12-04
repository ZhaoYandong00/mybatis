package com.zyd.simple.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import com.zyd.simple.model.SysPrivilege;
import com.zyd.simple.model.SysRole;
import com.zyd.simple.model.SysUser;

public class UserMapperTest extends BaseMapperTest {
	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1l);
			Assert.assertNotNull(user);
			Assert.assertEquals("admin", user.getUserName());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAll();
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() > 0);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRolesByUserId() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRolesByUserId(1l);
			Assert.assertNotNull(roleList);
			Assert.assertTrue(roleList.size() > 0);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个user对象
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassWord("12346");
			user.setUserEmail("test@zyd.com");
			user.setUserInfo("test info");
			// 正常是图片
			user.setHeadImg(new byte[] { 1, 2, 3 });
			user.setCreateTime(new Date());
			int result = userMapper.insert(user);
			Assert.assertEquals(1, result);
			Assert.assertNull(user.getId());
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
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个user对象
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassWord("12346");
			user.setUserEmail("test@zyd.com");
			user.setUserInfo("test info");
			// 正常是图片
			user.setHeadImg(new byte[] { 1, 2, 3 });
			user.setCreateTime(new Date());
			int result = userMapper.insert2(user);
			Assert.assertEquals(1, result);
			Assert.assertNotNull(user.getId());
			System.out.println(user.getId());
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
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个user对象
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassWord("12346");
			user.setUserEmail("test@zyd.com");
			user.setUserInfo("test info");
			// 正常是图片
			user.setHeadImg(new byte[] { 1, 2, 3 });
			user.setCreateTime(new Date());
			int result = userMapper.insert3(user);
			Assert.assertEquals(1, result);
			Assert.assertNotNull(user.getId());
			System.out.println(user.getId());
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
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 从数据库查询一个对象
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("admin", user.getUserName());
			// 修改用户名
			user.setUserName("admin_test");
			// 修改邮箱
			user.setUserEmail("test@zyd.com");
			// 更新数据,返回执行SQL影响的行数
			int result = userMapper.updateById(user);
			Assert.assertEquals(1, result);
			// 查询修改后的数据
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin_test", user.getUserName());
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
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 从数据库查询一个对象
			SysUser user = userMapper.selectById(1L);
			Assert.assertNotNull(user);
			// 删除对象
			Assert.assertEquals(1, userMapper.deleteById(1L));
			// 再次查询
			Assert.assertNull(userMapper.selectById(1L));

			// 使用SysUser参数测试
			SysUser user2 = userMapper.selectById(1001L);
			Assert.assertNotNull(user2);
			// 删除对象
			Assert.assertEquals(1, userMapper.deleteById(user2));
			// 再次查询
			Assert.assertNull(userMapper.selectById(1001L));
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRolesByUserIdAndRoleEnabled() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> userList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() > 0);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRolesByUserAndRole() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			SysRole role = userMapper.selectRolesByUserId(1L).get(0);
			List<SysRole> userList = userMapper.selectRolesByUserAndRole(user, role);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() > 0);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByUser() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 只查询用户名时
			SysUser query = new SysUser();
			query.setUserName("ad");
			List<SysUser> userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			// 只查询用户邮箱
			query = new SysUser();
			query.setUserEmail("test@zyd.com");
			userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			// 同时查询用户名和邮箱
			query = new SysUser();
			query.setUserName("ad");
			query.setUserEmail("test@zyd.com");
			userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() == 0);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateByIdSelective() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个新的user对象
			SysUser user = new SysUser();
			// 更新id为1的用户
			user.setId(1L);
			// 修改邮箱
			user.setUserEmail("test@zyd.com");
			// 执行
			int result = userMapper.updateByIdSelective(user);
			// 只更新一条数据
			Assert.assertEquals(1, result);
			// 查询更改后的数据
			user = userMapper.selectById(1L);
			// 名字未变，邮箱改变
			Assert.assertEquals("admin", user.getUserName());
			Assert.assertEquals("test@zyd.com", user.getUserEmail());
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testInstert2Selective() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个新的user对象
			SysUser user = new SysUser();
			user.setUserName("test-selective");
			user.setUserPassWord("123456");
			user.setUserInfo("test info");
			user.setCreateTime(new Date());
			// 插入数据库
			userMapper.insert2(user);
			// 获取插入数据
			user = userMapper.selectById(user.getId());
			Assert.assertEquals("test@zyd.com", user.getUserEmail());
		} finally {
			// 不影响其他测试，进行回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByIdOrUserName() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 只查询id时
			SysUser query = new SysUser();
			query.setId(1L);
			query.setUserName("admin");
			SysUser user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			// 当没有id时
			query.setId(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			// 当id和name都为空
			query.setUserName(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNull(user);
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectByIdList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertEquals(2, userList.size());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsertList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 创建一个用户列表
			List<SysUser> userList = new ArrayList<SysUser>();
			for (int i = 0; i < 2; i++) {
				SysUser user = new SysUser();
				user.setUserName("test" + i);
				user.setUserPassWord("123456");
				user.setUserEmail("test@zyd.com");
				userList.add(user);
			}
			int result = userMapper.insertList(userList);
			for (SysUser sysUser : userList) {
				System.out.println(sysUser.getId());
			}
			assertEquals(2, result);
		} finally {
			// 回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateByMap() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> map = new HashMap<String, Object>();
			// 查询条件
			map.put("id", 1L);
			// 要更新的字段
			map.put("user_email", "test@zyd.com");
			map.put("user_password", "12345678");
			// 更新数据
			userMapper.updateByMap(map);
			// 查询
			SysUser user = userMapper.selectById(1L);
			assertEquals("test@zyd.com", user.getUserEmail());
		} finally {
			// 回滚
			sqlSession.rollback();
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUseAndRoleById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			// user不为空
			assertNotNull(user);
			// user.role也不为空
			assertNotNull(user.getRole());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUseAndRoleById2() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById2(1001L);
			// user不为空
			assertNotNull(user);
			// user.role也不为空
			assertNotNull(user.getRole());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUseAndRoleByIdSelect() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
			// user不为空
			assertNotNull(user);
			System.out.println("调用user.equals(null)");
			user.equals(null);
			System.out.println("调用user.getRole()");
			// user.role也不为空
			assertNotNull(user.getRole());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUserAndRoles() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectUserAndRoles();
			System.out.println("用户数:" + userList.size());
			for (SysUser user : userList) {
				System.out.println("用户名:" + user.getUserName());
				for (SysRole role : user.getRoleList()) {
					System.out.println("角色名:" + role.getRoleName());
					for (SysPrivilege privilege : role.getPrivilegeList()) {
						System.out.println("权限名:" + privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectAllUserAndRoleSelect() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllUserAndRoleSelect(1L);
			System.out.println("用户名：" + user.getUserName());
			for (SysRole role : user.getRoleList()) {
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
	public void testSelectUserById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserById(user);
			assertNotNull(user.getUserName());
			System.out.println("用户名：" + user.getUserName());
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUserPage() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", "ad");
			params.put("offset", 0);
			params.put("limit", 10);
			List<SysUser> userList = userMapper.selectUserPage(params);
			Long total = (Long) params.get("total");
			System.out.println("总数：" + total);
			for (SysUser user : userList) {
				System.out.println("用户名：" + user.getUserName());
			}
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testInsertUserAndRoles() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassWord("123456");
			user.setUserEmail("test@zyd.com");
			user.setUserInfo("test info");
			user.setHeadImg(new byte[] { 1, 2, 3 });
			userMapper.insertUserAndRoles(user, "1,2");
			assertNotNull(user.getId());
			assertNotNull(user.getCreateTime());
			// 测试删除刚刚插入的数据
			userMapper.deleteUserById(user.getId());
		} finally {
			sqlSession.close();
		}
	}
}
