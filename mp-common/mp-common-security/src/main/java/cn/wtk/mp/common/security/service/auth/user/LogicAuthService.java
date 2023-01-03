package cn.wtk.mp.common.security.service.auth.user;


import cn.wtk.mp.common.security.annotation.RequiresPermissions;
import cn.wtk.mp.common.security.annotation.RequiresRoles;
import cn.wtk.mp.common.security.config.AuthConstants;
import cn.wtk.mp.common.security.enums.Logical;
import cn.wtk.mp.common.security.exception.NotPermissionException;
import cn.wtk.mp.common.security.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

/**
 * 逻辑校验是否包含必要的权限、角色
 * @author wtk
 * @date 2022-08-30
 */
@Component
@Slf4j
public class LogicAuthService {

    public UserCredential requireToken() {
        return UserSecurityContextHolder.require();
    }

    /**
     * 根据注解传入参数鉴权, 如果验证未通过，则抛出异常: NotPermissionException
     * @param requiresPermissions 权限注解
     */
    public void checkPermi(RequiresPermissions requiresPermissions) {
        if (requiresPermissions.logical() == Logical.AND) {
            checkPermiAnd(requiresPermissions.value());
        } else {
            checkPermiOr(requiresPermissions.value());
        }
    }

    /**
     * 根据注解(@RequiresRoles)鉴权
     * @param requiresRoles 注解对象
     */
    public void checkRole(RequiresRoles requiresRoles) {
        if (requiresRoles.logical() == Logical.AND) {
            checkRoleAnd(requiresRoles.value());
        } else {
            checkRoleOr(requiresRoles.value());
        }
    }

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     * @param roles 角色标识数组
     */
    public void checkRoleAnd(String... roles) {
        Set<String> roleList = getRoleList();
        for (String role : roles) {
            if (!hasRole(roleList, role)) {
                throw new NotRoleException(role);
            }
        }
    }

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     * @param roles 角色标识数组
     */
    public void checkRoleOr(String... roles) {
        Set<String> roleList = getRoleList();
        for (String role : roles) {
            if (hasRole(roleList, role)) {
                return;
            }
        }
        if (roles.length > 0) {
            throw new NotRoleException(roles);
        }
    }


    /**
     * 验证用户是否含有指定权限，必须全部拥有
     * @param permissions 权限列表
     */
    public void checkPermiAnd(String... permissions) {
        Set<String> permissionList = getPermiList();
        for (String permission : permissions) {
            if (!hasPermi(permissionList, permission)) {
                throw new NotPermissionException(permission);
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     * @param permissions 权限码数组
     */
    public void checkPermiOr(String... permissions) {
        Set<String> permissionList = getPermiList();
        for (String permission : permissions) {
            if (hasPermi(permissionList, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new NotPermissionException(permissions);
        }
    }

    /**
     * 获取当前账号的权限列表
     * @return 权限列表
     */
    public Set<String> getPermiList() {
        UserCredential loginUser = UserSecurityContextHolder.require();
        return loginUser.getPermissions();
    }

    /**
     * 获取当前账号的角色列表
     * @return 角色列表
     */
    public Set<String> getRoleList() {
        UserCredential loginUser = UserSecurityContextHolder.require();
        return loginUser.getRoles();
    }

    /**
     * 判断是否包含权限
     * @param authorities 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(Collection<String> authorities, String permission) {
        return authorities.stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> AuthConstants.ALL_PERMISSION.contains(x) ||
                        PatternMatchUtils.simpleMatch(x, permission));
    }

    /**
     * 判断是否包含角色
     * @param roles 角色列表
     * @param role 角色
     * @return 用户是否具备某角色权限
     */
    public boolean hasRole(Collection<String> roles, String role) {
        return roles.stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> AuthConstants.SUPER_ADMIN.contains(x) ||
                        PatternMatchUtils.simpleMatch(x, role));
    }
}
