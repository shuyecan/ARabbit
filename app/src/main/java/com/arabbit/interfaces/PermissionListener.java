package com.arabbit.interfaces;

import java.util.List;

/**
 * Created by net8 on 2017/8/2.
 */

public interface PermissionListener {
    /**
     * 成功获取权限
     */
    void onGranted();

    /**
     * 为获取权限
     *
     * @param deniedPermission
     */
    void onDenied(List<String> deniedPermission);
}
