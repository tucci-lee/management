package cn.tucci.management.service.sys.impl;

import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.dao.sys.SysDeptMapper;
import cn.tucci.management.model.domain.sys.SysDept;
import cn.tucci.management.service.sys.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tucci.lee
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    @Autowired
    public SysDeptServiceImpl(SysDeptMapper sysDeptMapper) {
        this.sysDeptMapper = sysDeptMapper;
    }

    @Override
    public List<SysDept> list() {
        Wrapper<SysDept> wrapper = new LambdaQueryWrapper<>(SysDept.class)
                .eq(SysDept::getIsDeleted, Boolean.FALSE);
        return sysDeptMapper.selectList(wrapper);
    }

    @Override
    public int add(SysDept dept) {
        this.setLevel(dept);

        return sysDeptMapper.insert(dept);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int edit(SysDept dept) {
        SysDept sysDept = sysDeptMapper.selectOne(new LambdaQueryWrapper<>(SysDept.class)
                .select(field -> true)
                .eq(SysDept::getIsDeleted, Boolean.FALSE)
                .eq(SysDept::getId, dept.getId()));
        Assert.notNull(sysDept, "部门不存在");

        this.setLevel(dept);

        synchronized (this) {
            // 如果pid修改了，则修改所有下级的level字段
            if (!dept.getPid().equals(sysDept.getPid())) {
                String oldLevel = sysDept.getLevel() + "," + sysDept.getId();
                String newLevel = dept.getLevel() + "," + dept.getId();
                Wrapper<SysDept> updateWrapper = new LambdaUpdateWrapper<>(SysDept.class)
                        .setSql("level = replace(`level`, '" + oldLevel + "', '" + newLevel + "')")
                        .eq(SysDept::getIsDeleted, Boolean.FALSE)
                        .likeRight(SysDept::getLevel, oldLevel);
                sysDeptMapper.update(null, updateWrapper);
            }

            return sysDeptMapper.updateById(dept);
        }
    }

    @Override
    public int delete(SysDept dept) {
        // 判断部门是否有用户关联
        int userCount = sysDeptMapper.countUserByDeptId(dept.getId());
        Assert.isTrue(userCount == 0, "当前部门有用户关联");

        // 判断部门是否有下级
        int subDeptCount = sysDeptMapper.countSubDeptById(dept.getId());
        Assert.isTrue(subDeptCount == 0, "当前部门有下级部门");

        // 删除部门
        return sysDeptMapper.updateById(dept);
    }

    /**
     * 设置level字段，所有父级部门id用逗号拼接 ---- 0,1,2。
     * 如果pid等于0，那么level也是0。
     * 如果pid不等于0，父级部门不存在抛出异常，如果是修改部门，则校验是否是向下修改，向下修改抛出异常
     *
     * @param dept dept
     */
    private void setLevel(SysDept dept) {
        if (dept.getPid().equals(0L)) {
            dept.setLevel("0");
            return;
        }
        SysDept parentDept = sysDeptMapper.selectOne(new LambdaQueryWrapper<>(SysDept.class)
                .select(field -> true)
                .eq(SysDept::getIsDeleted, Boolean.FALSE)
                .eq(SysDept::getId, dept.getPid()));
        Assert.notNull(parentDept, "父级不存在");

        if (dept.getId() != null) {
            Assert.isTrue(!parentDept.getLevel().contains(dept.getId().toString()), "父级不能向下级修改");
        }
        dept.setLevel(parentDept.getLevel() + "," + dept.getPid());
    }
}
