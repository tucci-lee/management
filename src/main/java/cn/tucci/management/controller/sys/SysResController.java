package cn.tucci.management.controller.sys;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.model.body.ResAddBody;
import cn.tucci.management.model.body.ResEditBody;
import cn.tucci.management.model.domain.sys.SysRes;
import cn.tucci.management.service.sys.SysResService;
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
@RequestMapping("/sys/res")
public class SysResController {

    private final SysResService sysResService;

    @Autowired
    public SysResController(SysResService sysResService) {
        this.sysResService = sysResService;
    }

    /**
     * 获取用户拥有的菜单列表，前端菜单栏显示
     *
     * @return Result
     */
    @GetMapping("owned_menus")
    public Result ownedMenus() {
        Long uid = ShiroUtil.getUid();

        return Result.ok(sysResService.listMenuByUid(uid));
    }

    /**
     * 获取所有的资源列表，树节点展示
     *
     * @return Result
     */
    @RequiresPermissions(value = {"sys:res:list", "sys:role:list"}, logical = Logical.OR)
    @GetMapping
    public Result list() {
        return Result.ok(sysResService.list());
    }

    /**
     * 获取所有的菜单，添加、修改资源选择上级使用
     *
     * @return Result
     */
    @RequiresPermissions(value = {"sys:res:list"})
    @GetMapping("menus")
    public Result menus() {
        return Result.ok(sysResService.listMenus());
    }

    /**
     * 添加资源
     *
     * @param body body
     * @return Result
     */
    @Log("添加资源")
    @RequiresPermissions(value = {"sys:res:add"})
    @PostMapping
    public Result add(@Validated @RequestBody ResAddBody body) {
        SysRes res = new SysRes()
                .setCreator(ShiroUtil.getUid());
        BeanUtils.copyProperties(body, res);
        sysResService.add(res);
        return Result.ok();
    }

    /**
     * 修改资源
     *
     * @param body body
     * @return Result
     */
    @Log("修改资源")
    @RequiresPermissions(value = {"sys:res:edit"})
    @PutMapping
    public Result edit(@Validated @RequestBody ResEditBody body) {
        Assert.isTrue(!body.getId().equals(body.getPid()), "资源上级不能是当前资源");
        SysRes res = new SysRes()
                .setUpdater(ShiroUtil.getUid());
        BeanUtils.copyProperties(body, res);
        sysResService.edit(res);
        return Result.ok();
    }

    /**
     * 删除资源
     *
     * @param id id
     * @return Result
     */
    @Log("删除资源")
    @RequiresPermissions(value = {"sys:res:delete"}, logical = Logical.OR)
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        SysRes res = new SysRes()
                .setId(id)
                .setUpdater(ShiroUtil.getUid())
                .setIsDeleted(Boolean.TRUE);
        sysResService.delete(res);
        return Result.ok();
    }

    /**
     * 查询角色关联的所有资源id
     *
     * @param roleId roleId
     * @return Result
     */
    @RequiresPermissions(value = {"sys:role:list"})
    @GetMapping("/role/{roleId}")
    public Result listResIdByRoleId(@PathVariable Long roleId) {
        return Result.ok(sysResService.listIdByRoleId(roleId));
    }
}
