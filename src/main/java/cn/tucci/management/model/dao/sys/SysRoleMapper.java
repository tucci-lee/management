package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysRole;
import cn.tucci.management.model.query.SysRoleQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<String> listRoleCharByUid(Long uid);

    Page<SysRole> selectPage(Page<SysRole> page, @Param("query") SysRoleQuery query);

    /**
     * 统计角色有多少用户关联
     *
     * @param id id
     * @return int
     */
    int countUserById(Long id);

    List<Long> listIdByUid(Long uid);
}