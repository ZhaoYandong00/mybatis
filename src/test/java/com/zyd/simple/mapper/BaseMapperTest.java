 package com.zyd.simple.mapper;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

/**
 * 
 * 基础测试类
 *
 */
public class BaseMapperTest {
	private static SqlSessionFactory sqlSessionFactory;

	@BeforeClass
	public static void init() {
		try {
			// 通过Resources类读取配置文件
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			// 创建工厂对象
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}

}
