package com.zyd.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import com.zyd.simple.model.SysPrivilege;
import com.zyd.simple.provider.PrivilegeProvider;

public interface PrivilegeMapper {
	@SelectProvider(type = PrivilegeProvider.class, method = "selectById")
	SysPrivilege selectById(Long id);
}
