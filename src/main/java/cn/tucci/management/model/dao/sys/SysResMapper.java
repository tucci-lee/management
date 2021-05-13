package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysResMapper extends BaseMapper<SysRes> {
    List<String> listResCharByUid(Long uid);

    List<SysRes> listMenuByUid(Long uid);

    int countRoleByResId(Long id);

    List<Long> listIdByRoleId(Long roleId);
}