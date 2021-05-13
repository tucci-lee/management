package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.model.query.UserQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUser> selectPage(Page<SysUser> page, @Param("query") UserQuery query);

}