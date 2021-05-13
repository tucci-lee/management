package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 添加用户关联的角色
     *
     * @param uid     uid
     * @param roleIds roleIds
     * @return int
     */
    int insertList(@Param("uid") Long uid, @Param("roleIds") List<Long> roleIds);
}