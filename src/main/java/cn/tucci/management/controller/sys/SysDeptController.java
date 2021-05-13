package cn.tucci.management.controller.sys;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.body.DeptAddBody;
import cn.tucci.management.model.body.DeptEditBody;
import cn.tucci.management.model.domain.sys.SysDept;
import cn.tucci.management.service.sys.SysDeptService;
import cn.tucci.management.shiro.util.ShiroUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @Autowired
    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 获取所有的dept
     *
     * @return Result
     */
    @RequiresPermissions(value = {"sys:dept:list", "sys:user:list"}, logical = Logical.OR)
    @GetMapping
    public Result list() {
        return Result.ok(sysDeptService.list());
    }

    /**
     * 添加dept
     *
     * @param body body
     * @return Result
     */
    @Log("添加部门")
    @RequiresPermissions(value = {"sys:dept:add"})
    @PostMapping
    public Result add(@Validated @RequestBody DeptAddBody body) {
        SysDept dept = new SysDept();
        dept.setCreator(ShiroUtil.getUid());
        BeanUtils.copyProperties(body, dept);
        sysDeptService.add(dept);
        return Result.ok();
    }

    /**
     * 修改部门
     *
     * @param body body
     * @return Result
     */
    @Log("修改部门")
    @RequiresPermissions(value = {"sys:dept:edit"})
    @PutMapping
    public Result edit(@Validated @RequestBody DeptEditBody body) {
        Assert.isTrue(!body.getId().equals(body.getPid()), "部门上级不能是当前部门");

        SysDept dept = new SysDept();
        BeanUtils.copyProperties(body, dept);
        sysDeptService.edit(dept);
        return Result.ok();
    }

    /**
     * 删除部门
     *
     * @param id id
     * @return Result
     */
    @Log("删除部门")
    @RequiresPermissions(value = {"sys:dept:delete"})
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        sysDeptService.delete(id);
        return Result.ok();
    }
}
