package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 统计当前部门有多少用户关联
     *
     * @param id deptId
     * @return user count
     */
    int countUserByDeptId(Long id);

    /**
     * 统计当前部门有多少下级部门
     *
     * @param id deptId
     * @return sub dept count
     */
    int countSubDeptById(Long id);
}