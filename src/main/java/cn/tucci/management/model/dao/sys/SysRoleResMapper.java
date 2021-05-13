package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysRoleRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleResMapper extends BaseMapper<SysRoleRes> {
    /**
     * 添加角色关联的资源
     *
     * @param roleId roleId
     * @param resIds resIds
     * @return int
     */
    int insertList(@Param("roleId") Long roleId, @Param("resIds") List<Long> resIds);
}