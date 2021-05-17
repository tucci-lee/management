package cn.tucci.management.controller.sys;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.body.RoleAddBody;
import cn.tucci.management.model.body.RoleEditBody;
import cn.tucci.management.model.body.RoleResEditBody;
import cn.tucci.management.model.domain.sys.SysRole;
import cn.tucci.management.model.query.SysRoleQuery;
import cn.tucci.management.service.sys.SysRoleService;
import cn.tucci.management.shiro.util.ShiroUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/sys/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 分页查询角色列表
     *
     * @param query query
     * @return PageResult
     */
    @RequiresPermissions(value = {"sys:role:list"})
    @GetMapping
    public Result list(@Validated SysRoleQuery query) {
        Page<SysRole> rolePage = sysRoleService.list(query);
        return PageResult.ok(rolePage.getRecords(), rolePage.getTotal());
    }

    /**
     * 添加角色
     *
     * @param body body
     * @return Result
     */
    @Log("添加角色")
    @RequiresPermissions(value = {"sys:role:add"})
    @PostMapping
    public Result add(@Validated @RequestBody RoleAddBody body) {
        SysRole role = new SysRole()
                .setCreator(ShiroUtil.getUid());
        BeanUtils.copyProperties(body, role);

        sysRoleService.add(role, body.getResIds());
        return Result.ok();
    }

    /**
     * 修改角色
     *
     * @param body body
     * @return Result
     */
    @Log("修改角色")
    @RequiresPermissions(value = {"sys:role:edit"})
    @PutMapping
    public Result edit(@Validated @RequestBody RoleEditBody body) {
        SysRole role = new SysRole()
                .setUpdater(ShiroUtil.getUid());
        BeanUtils.copyProperties(body, role);
        sysRoleService.edit(role);
        return Result.ok();
    }

    /**
     * 修改角色关联的资源
     *
     * @param body body
     * @return Result
     */
    @Log("修改角色关联的资源")
    @RequiresPermissions(value = {"sys:role:edit"})
    @PutMapping("edit_res")
    public Result editRes(@Validated @RequestBody RoleResEditBody body) {
        sysRoleService.editRes(body.getId(), body.getResIds());
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param id roleId
     * @return Result
     */
    @Log("删除角色")
    @RequiresPermissions(value = {"sys:role:delete"})
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        SysRole role = new SysRole()
                .setId(id)
                .setUpdater(ShiroUtil.getUid())
                .setIsDeleted(Boolean.TRUE);
        sysRoleService.delete(role);
        return Result.ok();
    }

    /**
     * 查询所有的角色，添加用户，查看用户角色时使用
     *
     * @return Result
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping("all")
    public Result list() {
        return Result.ok(sysRoleService.list());
    }

    /**
     * 查询用户关联的角色id，查看用户角色使用
     *
     * @param uid uid
     * @return Result
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping("/user/{uid}")
    public Result listRoleIdByUid(@PathVariable Long uid) {
        return Result.ok(sysRoleService.listIdByUid(uid));
    }

}
