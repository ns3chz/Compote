package cn.hu.zc.listener;

public abstract class PermissionCheckListener {

    /**
     * @param grantedPms 权限已获取的权限，多个权限同时获取时，只有当全部权限都获取到了才回调
     */
    public abstract void granted(String... grantedPms);

    /**
     * @param deniedPms 首次检查时，未获取的权限
     */
    public void denied(String... deniedPms) {

    }

    /**
     * @param deniiedAppOpPms 非第一次，被用户禁用的权限
     */
    public void deniedAppOp(String... deniiedAppOpPms) {

    }
}
