package cn.tucci.management.service.sys.impl;

import cn.tucci.management.core.constant.CacheConstant;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.dao.sys.SysResMapper;
import cn.tucci.management.model.domain.sys.SysRes;
import cn.tucci.management.service.sys.SysResService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
public class SysResServiceImpl implements SysResService {

    private final SysResMapper sysResMapper;

    @Autowired
    public SysResServiceImpl(SysResMapper sysResMapper) {
        this.sysResMapper = sysResMapper;
    }

    @Cacheable(value = {CacheConstant.RES_CHAR_4_UID}, key = "#p0")
    @Override
    public List<String> listResCharByUid(Long uid) {
        return sysResMapper.listResCharByUid(uid);
    }

    @Override
    public List<SysRes> listMenuByUid(Long uid) {
        return sysResMapper.listMenuByUid(uid);
    }

    @Override
    public List<SysRes> list() {
        Wrapper<SysRes> wrapper = new LambdaQueryWrapper<>(SysRes.class)
                .eq(SysRes::getIsDeleted, false);

        return sysResMapper.selectList(wrapper);
    }

    @Override
    public List<SysRes> listMenus() {
        Wrapper<SysRes> wrapper = new LambdaQueryWrapper<>(SysRes.class)
                .eq(SysRes::getIsDeleted, false)
                .eq(SysRes::getType, SysRes.Type.MENU);
        return sysResMapper.selectList(wrapper);
    }

    @Override
    public int add(SysRes res) {
        this.setLevel(res);
        return sysResMapper.insert(res);
    }

    @CacheEvict(value = {CacheConstant.RES_CHAR_4_UID}, allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int edit(SysRes res) {
        SysRes queryRes = sysResMapper.selectOne(new LambdaQueryWrapper<>(SysRes.class)
                .select(field -> true)
                .eq(SysRes::getIsDeleted, false)
                .eq(SysRes::getId, res.getId()));
        Assert.notNull(queryRes, "资源不存在");

        this.setLevel(res);

        synchronized (this) {
            // 如果pid修改了，则修改所有下级的level字段
            if (!res.getPid().equals(queryRes.getPid())) {
                String oldLevel = queryRes.getLevel() + "," + queryRes.getId();
                String newLevel = res.getLevel() + "," + res.getId();
                Wrapper<SysRes> updateWrapper = new LambdaUpdateWrapper<>(SysRes.class)
                        .setSql("level = replace(`level`, '" + oldLevel + "', '" + newLevel + "')")
                        .eq(SysRes::getIsDeleted, false)
                        .likeRight(SysRes::getLevel, oldLevel);
                sysResMapper.update(null, updateWrapper);
            }

            return sysResMapper.updateById(res);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int delete(Long id) {
        // 判断资源是否有角色关联
        int roleCount = sysResMapper.countRoleByResId(id);
        Assert.isTrue(roleCount == 0, "当前资源有角色关联");

        SysRes sysRes = sysResMapper.selectOne(new LambdaQueryWrapper<>(SysRes.class)
                .select(field -> true)
                .eq(SysRes::getIsDeleted, false)
                .eq(SysRes::getId, id));

        synchronized (this) {
            // 删除所有下级
            Wrapper<SysRes> updateWrapper = new LambdaUpdateWrapper<>(SysRes.class)
                    .set(SysRes::getIsDeleted, true)
                    .eq(SysRes::getIsDeleted, false)
                    .likeRight(SysRes::getLevel, sysRes.getLevel() + "," + sysRes.getId());
            sysResMapper.update(null, updateWrapper);

            // 删除资源
            SysRes res = new SysRes()
                    .setId(id)
                    .setIsDeleted(true);
            return sysResMapper.updateById(res);
        }
    }

    @Override
    public List<Long> listIdByRoleId(Long roleId) {
        return sysResMapper.listIdByRoleId(roleId);
    }

    /**
     * 设置level字段，所有父级资源用逗号拼接 ---- 0,1,2。
     * 如果pid等于0，那么level也是0。
     * 如果pid不等于0，父级资源不存在抛出异常，如果是修改资源，则校验是否是向下修改，向下修改抛出异常
     *
     * @param res res
     */
    private void setLevel(SysRes res) {
        if (res.getPid().equals(0L)) {
            res.setLevel("0");
            return;
        }
        SysRes parentRes = sysResMapper.selectOne(new LambdaQueryWrapper<>(SysRes.class)
                .select(field -> true)
                .eq(SysRes::getIsDeleted, false)
                .eq(SysRes::getId, res.getPid()));
        Assert.notNull(parentRes, "父级不存在");

        if (res.getId() != null) {
            Assert.isTrue(!parentRes.getLevel().contains(res.getId().toString()), "父级不能向下级修改");
        }
        res.setLevel(parentRes.getLevel() + "," + res.getPid());
    }
}
