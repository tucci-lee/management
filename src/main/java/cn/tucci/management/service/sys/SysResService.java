package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysRes;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysResService {
    /**
     * 根据uid查询拥有的资源字符
     *
     * @param uid uid
     * @return resChars
     */
    List<String> listResCharByUid(Long uid);

    /**
     * 根据uid查询拥有的菜单列表
     *
     * @param uid uid
     * @return SysResList
     */
    List<SysRes> listMenuByUid(Long uid);

    /**
     * 查询所有的资源
     *
     * @return SysResList
     */
    List<SysRes> list();

    /**
     * 查询所有的菜单
     *
     * @return SysResList
     */
    List<SysRes> listMenus();

    /**
     * 添加资源
     *
     * @param res res
     * @return int
     */
    int add(SysRes res);

    /**
     * 修改资源
     *
     * @param res res
     * @return int
     */
    int edit(SysRes res);

    /**
     * 根据id删除资源
     *
     * @param id id
     * @return int
     */
    int delete(Long id);

    /**
     * 根据roleId查询关联的所有resId
     * @param roleId roleId
     * @return resIdList
     */
    List<Long> listIdByRoleId(Long roleId);
}
