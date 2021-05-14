package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysRole;
import cn.tucci.management.model.query.RoleQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleService {
    /**
     * 根据uid查询拥有role char
     *
     * @param uid uid
     * @return roleChars
     */
    List<String> listRoleCharByUid(Long uid);

    /**
     * 分页查询role
     *
     * @param query query
     * @return roleList
     */
    Page<SysRole> list(RoleQuery query);

    /**
     * 添加role，添加角色关联的资源
     *
     * @param role   role
     * @param resIds resIds
     */
    void add(SysRole role, List<Long> resIds);

    /**
     * 修改role
     *
     * @param role role
     * @return int
     */
    int edit(SysRole role);

    /**
     * 修改角色关联的资源
     *
     * @param id     roleId
     * @param resIds resIds
     */
    int editRes(Long id, List<Long> resIds);

    /**
     * 根据roleId删除
     *
     * @param role role
     * @return int
     */
    int delete(SysRole role);

    /**
     * 查询所有的role
     *
     * @return RoleList
     */
    List<SysRole> list();

    /**
     * 根据uid查询roleId
     *
     * @param uid uid
     * @return roleIdList
     */
    List<Long> listIdByUid(Long uid);
}
