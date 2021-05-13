package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.model.query.UserQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysUserService {
    /**
     * 根据wrapper获取用户信息
     *
     * @param wrapper wrapper
     * @return SysUserAuth
     */
    SysUser getOne(Wrapper<SysUser> wrapper);

    /**
     * 分页条件查询用户信息
     *
     * @param query query
     * @return SysUserList
     */
    Page<SysUser> list(UserQuery query);

    /**
     * 根据uid修改用户信息
     *
     * @param user user
     * @return int
     */
    int updateByUid(SysUser user);


    /**
     * 用户添加
     *
     * @param user     user
     * @param roleIds  roleIds
     */
    void add(SysUser user, List<Long> roleIds);

    /**
     * 根据uid修改用户关联的角色
     * @param uid uid
     * @param roleIds roleIds
     */
    int editRole(Long uid, List<Long> roleIds);
}
