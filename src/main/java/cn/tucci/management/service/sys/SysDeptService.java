package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysDept;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysDeptService {

    /**
     * 查询所有的dept
     *
     * @return SysDeptList
     */
    List<SysDept> list();

    /**
     * 添加dept
     *
     * @param dept dept
     * @return int
     */
    int add(SysDept dept);

    /**
     * 修改dept
     *
     * @param dept dept
     * @return int
     */
    int edit(SysDept dept);

    /**
     * 删除dept
     *
     * @param dept SysDept
     * @return int
     */
    int delete(SysDept dept);
}
