package cn.tucci.management.service.sys.impl;

import cn.tucci.management.core.constant.CacheConstant;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.dao.sys.SysRoleMapper;
import cn.tucci.management.model.dao.sys.SysRoleResMapper;
import cn.tucci.management.model.domain.sys.SysRole;
import cn.tucci.management.model.domain.sys.SysRoleRes;
import cn.tucci.management.model.query.RoleQuery;
import cn.tucci.management.service.sys.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tucci.lee
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleResMapper sysRoleResMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysRoleResMapper sysRoleResMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleResMapper = sysRoleResMapper;
    }

    @Cacheable(value = {CacheConstant.ROLE_CHAR_4_UID}, key = "#p0")
    @Override
    public List<String> listRoleCharByUid(Long uid) {
        return sysRoleMapper.listRoleCharByUid(uid);
    }

    @Override
    public Page<SysRole> list(RoleQuery query) {
        // 分页+排序
        Page<SysRole> page = new Page<SysRole>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));
        // 查询条件
        return sysRoleMapper.selectPage(page, query);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(SysRole role, List<Long> resIds) {
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getIsDeleted, Boolean.FALSE)
                .eq(SysRole::getName, role.getName());
        // 校验角色名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysRoleMapper.selectOne(wrapper), "角色名称重复");
            sysRoleMapper.insert(role);
        }
        // 添加关联的资源
        sysRoleResMapper.insertList(role.getId(), resIds);
    }

    @CacheEvict(value = {CacheConstant.ROLE_CHAR_4_UID}, allEntries = true)
    @Override
    public int edit(SysRole role) {
        synchronized (this) {
            Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getName, role.getName())
                    .eq(SysRole::getIsDeleted, Boolean.FALSE);
            SysRole sysRole = sysRoleMapper.selectOne(wrapper);
            Assert.isTrue(sysRole == null || sysRole.getId().equals(role.getId()), "角色名称重复");
            return sysRoleMapper.updateById(role);
        }
    }

    @CacheEvict(value = {CacheConstant.RES_CHAR_4_UID}, allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int editRes(Long id, List<Long> resIds) {
        synchronized (this) {
            // 删除之前绑定的资源
            Wrapper<SysRoleRes> wrapper = new LambdaUpdateWrapper<SysRoleRes>()
                    .eq(SysRoleRes::getRoleId, id);
            sysRoleResMapper.delete(wrapper);

            // 添加新绑定的资源
            return sysRoleResMapper.insertList(id, resIds);
        }
    }

    @Override
    public int delete(SysRole role) {
        int userCount = sysRoleMapper.countUserById(role.getId());
        Assert.isTrue(userCount == 0, "当前角色有用户关联");

        return sysRoleMapper.updateById(role);
    }

    @Override
    public List<SysRole> list() {
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getIsDeleted, Boolean.FALSE);
        return sysRoleMapper.selectList(wrapper);
    }

    @Override
    public List<Long> listIdByUid(Long uid) {
        return sysRoleMapper.listIdByUid(uid);
    }
}
