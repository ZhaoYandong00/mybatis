package com.zyd.simple.mapper;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.zyd.simple.model.SysPrivilege;

public class PrivilegeMapperTest extends BaseMapperTest {

	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取PrivilegeMapper接口
			PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
			// 调用selectById方法
			SysPrivilege privilege = privilegeMapper.selectById(1L);
			// privilege不为空
			assertNotNull(privilege);
			// 名字为用户管理0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			assertEquals("用户管理", privilege.getPrivilegeName());
		} finally {
			sqlSession.close();
		}

	}

}
