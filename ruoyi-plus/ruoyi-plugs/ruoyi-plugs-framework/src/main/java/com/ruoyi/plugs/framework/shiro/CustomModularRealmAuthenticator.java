package com.ruoyi.plugs.framework.shiro;

import com.ruoyi.framework.shiro.realm.UserRealm;
import com.ruoyi.plugs.framework.shiro.realm.NoPwdUserRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Iterator;

public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 重写doMultiRealmAuthentication，抛出异常，便于自定义ExceptionHandler捕获
     */
    @Override
    public AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) throws AuthenticationException {

        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        Iterator var5 = realms.iterator();
        AuthenticationException authenticationException = null;

        while(var5.hasNext()) {
            Realm realm = (Realm)var5.next();
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {

                AuthenticationInfo info = null;
                //Throwable t = null;
                if(token.getClass().getName().equals(UsernamePasswordToken.class.getName())&&realm.getClass().getName().equals(UserRealm.class.getName())){
                    try {
                        info = realm.getAuthenticationInfo(token);
                    } catch (AuthenticationException e) {
                        authenticationException = e;
                    }
                }
                if(token.getClass().getName().equals(com.ruoyi.plugs.framework.shiro.token.UsernamePasswordToken.class.getName())&&realm.getClass().getName().equals(NoPwdUserRealm.class.getName())){
                    try {
                        info = realm.getAuthenticationInfo(token);
                    } catch (AuthenticationException e) {
                        authenticationException = e;
                    }
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, authenticationException);
            }
        }
        if (authenticationException != null) {
            throw authenticationException;
        }
        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
