package cn.tucci.management.service.sys.impl;

import cn.tucci.management.core.constant.CacheConstant;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.dao.sys.SysUserMapper;
import cn.tucci.management.model.dao.sys.SysUserRoleMapper;
import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.model.domain.sys.SysUserRole;
import cn.tucci.management.model.query.SysUserQuery;
import cn.tucci.management.service.sys.SysUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tucci.lee
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    public SysUser getOne(Wrapper<SysUser> wrapper) {
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public Page<SysUser> list(SysUserQuery query) {
        // 分页+排序
        Page<SysUser> page = new Page<SysUser>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));
        return sysUserMapper.selectPage(page, query);
    }

    @CacheEvict(value = {CacheConstant.ROLE_CHAR_4_UID, CacheConstant.RES_CHAR_4_UID}, allEntries = true)
    @Override
    public int updateByUid(SysUser user) {
        synchronized (this) {
            return sysUserMapper.updateById(user);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(SysUser user, List<Long> roleIds) {
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<>(SysUser.class)
                .eq(SysUser::getIsDeleted, Boolean.FALSE)
                .eq(SysUser::getAccount, user.getAccount());

        synchronized (this) {
            Assert.isNull(sysUserMapper.selectOne(wrapper), "账号已经存在");
            // 添加成功后会将主键设置到实体类中
            sysUserMapper.insert(user);
        }
        // 添加关联的角色
        sysUserRoleMapper.insertList(user.getUid(), roleIds);
    }

    @CacheEvict(value = {CacheConstant.RES_CHAR_4_UID, CacheConstant.ROLE_CHAR_4_UID}, allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int editRole(Long uid, List<Long> roleIds) {
        synchronized (this) {
            // 删除之前绑定的角色
            Wrapper<SysUserRole> wrapper = new LambdaUpdateWrapper<>(SysUserRole.class)
                    .eq(SysUserRole::getUid, uid);
            sysUserRoleMapper.delete(wrapper);

            // 添加新绑定的角色
            return sysUserRoleMapper.insertList(uid, roleIds);
        }
    }
}
