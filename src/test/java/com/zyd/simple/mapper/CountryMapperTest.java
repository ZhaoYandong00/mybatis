package com.zyd.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import com.zyd.simple.model.Country;
import com.zyd.simple.model.CountryExample;

public class CountryMapperTest extends BaseMapperTest {

	// @Test
	// public void testSelectAll() {
	// SqlSession sqlSession = getSqlSession();
	// try {
	// // 找到CountryMapper.xml中id="selectAll"的方法，执行SQL查询
	// List<Country> countriyList =
	// sqlSession.selectList("com.zyd.simple.mapper.CountryMapper.selectAll");
	// printCountryList(countriyList);
	// } finally {
	// // 关闭数据库连接
	// sqlSession.close();
	// }
	//
	// }
	//
	private void printCountryList(List<Country> countriyList) {
		for (Country country : countriyList) {
			System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
		}
	}

	@Test
	public void testExample() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取CountryMapper接口
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			// 创建Example对象
			CountryExample example = new CountryExample();
			// 设置排序规则
			example.setOrderByClause("id desc,countryname asc");
			// 去重
			example.setDistinct(true);
			// 创建条件
			CountryExample.Criteria criteria = example.createCriteria();
			// id>=1
			criteria.andIdGreaterThanOrEqualTo(1);
			// id<4
			criteria.andIdLessThan(4);
			// 写通配符
			criteria.andCountrycodeLike("%U%");
			// or的情况
			CountryExample.Criteria or = example.or();
			or.andCountrynameEqualTo("中国");
			// 执行查询
			List<Country> countryList = countryMapper.selectByExample(example);
			printCountryList(countryList);
		} finally {
			// 关闭数据库连接
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateByExampleSelective() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取CountryMapper接口
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			// 创建Example对象
			CountryExample example = new CountryExample();
			// 创建条件
			CountryExample.Criteria criteria = example.createCriteria();
			// id>2
			criteria.andIdGreaterThan(2);
			// 创建一个要设置的对象
			Country country = new Country();
			// 将国家名字设为China
			country.setCountryname("China");
			// 执行修改
			countryMapper.updateByExampleSelective(country, example);
			// 把符合条件的结果输出
			printCountryList(countryMapper.selectByExample(example));
		} finally {
			sqlSession.rollback();
			// 关闭数据库连接
			sqlSession.close();
		}
	}

	@Test
	public void testDeleteByExample() {
		SqlSession sqlSession = getSqlSession();
		try {
			// 获取CountryMapper接口
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			// 创建Example对象
			CountryExample example = new CountryExample();
			// 创建条件
			CountryExample.Criteria criteria = example.createCriteria();
			// id>2
			criteria.andIdGreaterThan(2);
			// 执行删除
			countryMapper.deleteByExample(example);
			// 把符合条件的结果输出
			Assert.assertEquals(0, countryMapper.selectByExample(example));
		} finally {
			sqlSession.rollback();
			// 关闭数据库连接
			sqlSession.close();
		}
	}
}