package com.zyd.simple.plugin;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/**
 * MySQL实现
 * 
 * @author ZYD
 *
 */
@SuppressWarnings("rawtypes")
public class MySqlDialect implements Dialect {

	@Override
	public boolean skip(String msId, Object parameterObject, RowBounds rowBounds) {
		// 使用RowRounds分页
		// 没有RowRounds参数时，会使用RowRounds.DEAFAULT作为默认值
		if (rowBounds != RowBounds.DEFAULT) {
			return false;
		}
		return true;
	}

	@Override
	public boolean beforeCount(String msId, Object parameterObject, RowBounds rowBounds) {
		// 只有使用PageRowBounds才能记录总数，否则查询了也没用
		if (rowBounds instanceof PageRowBounds) {
			return true;
		}
		return false;
	}

	@Override
	public String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey) {
		// 简单嵌套实现MySQL count查询
		return "select count(*) from (" + boundSql.getSql() + ") temp";
	}

	@Override
	public void afterCount(long count, Object parameterObject, RowBounds rowBounds) {
		// 记录总数，按照beforeCount逻辑，只有PageRowBounds才会查询count
		// 强制转换
		((PageRowBounds) rowBounds).setTotal(count);
	}

	@Override
	public boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds) {
		if (rowBounds != RowBounds.DEFAULT) {
			return true;
		}
		return false;
	}

	@Override
	public String getPageSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey) {
		// pageKey会影响换成，通过固定的RowBounds可以保证二级缓存有效
		pageKey.update("RowBounds");
		return boundSql.getSql() + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
	}

	@Override
	public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
		// 直接返回查询结果
		return pageList;
	}

	@Override
	public void setProperties(Properties properties) {
		// 暂没有其他参数

	}

}
