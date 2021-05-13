package cn.tucci.management;

import cn.tucci.management.model.dao.sys.SysRoleMapper;
import cn.tucci.management.model.domain.sys.SysRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private SysRoleMapper roleMapper;

    @Test
    public void mybatis() {
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<>(SysRole.class)
                .eq(SysRole::getIsDeleted, false);
        Page<SysRole> sysRolePage = roleMapper.selectPage(new Page<>(1, 10), wrapper);
        System.out.println(sysRolePage);
    }
}
