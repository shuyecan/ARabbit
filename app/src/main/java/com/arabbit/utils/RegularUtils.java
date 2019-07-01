package com.arabbit.utils;

import java.util.regex.Pattern;

/**
 * Created by net8 on 2017/7/11.
 */

public class RegularUtils {
    /**
     * 检查URL是否为网址
     */
    public static boolean checkUrl(String url) {
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }
}
