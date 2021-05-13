package cn.tucci.management.shiro.credential;

import cn.tucci.management.core.util.BCrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author tucci.lee
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {

    /**
     * 校验密码是否正确
     *
     * @param token 登陆填写的密码
     * @param info  认证获取的密码
     * @return boolean
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginCredential = String.valueOf((char[]) token.getCredentials());
        String userCredential = (String) info.getCredentials();
        return BCrypt.checkpw(loginCredential, userCredential);
    }
}
