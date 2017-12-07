package com.zyd.simple.plugin;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据库方言，针对不同数据库分别实现
 * 
 * @author ZYD
 *
 */
@SuppressWarnings("rawtypes")
public interface Dialect {
	/**
	 * 跳过count和分页查询
	 * 
	 * @param msId
	 *            执行的MyBatis方法全名
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @return true 跳过，返回默认查询结果，false 则执行分页查询
	 */
	boolean skip(String msId, Object parameterObject, RowBounds rowBounds);

	/**
	 * 执行分页前，返回true会进行count查询，返回false会继续进行下面的beforePage判断
	 * 
	 * @param msId
	 *            执行的MyBatis方法全名
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @return
	 */
	boolean beforeCount(String msId, Object parameterObject, RowBounds rowBounds);

	/**
	 * 生成count查询sql
	 * 
	 * @param boundSql
	 *            绑定SQL对象
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @param countKey
	 *            count缓存key
	 * @return
	 */
	String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey);

	/**
	 * 执行count查询完
	 * 
	 * @param count
	 *            查询结果总数
	 * @param parameterObject
	 *            接口参数
	 * @param rowBounds
	 *            分页参数
	 */
	void afterCount(long count, Object parameterObject, RowBounds rowBounds);

	/**
	 * 执行分页前，返回true会进行分页查询，返回false会返回默认查询结果
	 * 
	 * @param msId
	 *            执行的MyBatis方法全名
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @return
	 */
	boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds);

	/**
	 * 生成分页查询sql
	 * 
	 * @param boundSql
	 *            绑定SQL对象
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @param pageKey
	 *            分页缓存key
	 * @return
	 */
	String getPageSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey);

	/**
	 * 分页查询后，处理分页结果，拦截器中直接return该方法的返回值
	 * 
	 * @param pageList
	 *            分页查询结果
	 * @param parameterObject
	 *            方法参数
	 * @param rowBounds
	 *            分页参数
	 * @return
	 */
	Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds);

	/**
	 * 设置参数
	 * 
	 * @param properties
	 *            插件属性
	 */
	void setProperties(Properties properties);
}
