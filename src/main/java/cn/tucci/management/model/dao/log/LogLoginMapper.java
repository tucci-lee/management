package cn.tucci.management.model.dao.log;

import cn.tucci.management.model.domain.log.LogLogin;
import cn.tucci.management.model.query.LogLoginQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface LogLoginMapper extends BaseMapper<LogLogin> {

    Page<LogLogin> selectPage(Page<LogLogin> page, @Param("query") LogLoginQuery query);

}