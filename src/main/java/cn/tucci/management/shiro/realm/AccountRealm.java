package cn.tucci.management.shiro.realm;

import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.service.sys.SysResService;
import cn.tucci.management.service.sys.SysRoleService;
import cn.tucci.management.service.sys.SysUserService;
import cn.tucci.management.shiro.credential.BCryptCredentialsMatcher;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 处理账号密码登陆的Realm
 *
 * @author tucci.lee
 */
@Component
public class AccountRealm extends AuthorizingRealm {

    private final SysUserService userService;
    private final SysRoleService roleService;
    private final SysResService resService;

    @Autowired
    public AccountRealm(SysUserService userService, SysRoleService roleService, SysResService resService){
        this.userService = userService;
        this.roleService = roleService;
        this.resService = resService;
        // 凭证校验器
        this.setCredentialsMatcher(new BCryptCredentialsMatcher());
    }

    /**
     * 授权：获取用户的权限
     *
     * @param principalCollection principal
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //将用户所拥有的的角色字符和权限字符添加到SimpleAuthorizationInfo中
        List<String> roleChars = roleService.listRoleCharByUid(user.getUid());
        List<String> resChars = resService.listResCharByUid(user.getUid());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleChars);
        info.addStringPermissions(resChars);
        return info;
    }

    /**
     * 认证：用户登陆
     *
     * @param token token
     * @return AuthenticationInfo
     * @throws AuthenticationException exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<>(SysUser.class)
                .select(field -> true)
                .eq(SysUser::getAccount, token.getPrincipal())
                .eq(SysUser::getIsDeleted, false);

        SysUser user = userService.getOne(wrapper);;
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getIsLock()) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    /**
     * 多种登陆方式：当前realm处理的token类型
     *
     * @return Class
     */
    @Override
    public Class<? extends AuthenticationToken> getAuthenticationTokenClass() {
        return UsernamePasswordToken.class;
    }

}
