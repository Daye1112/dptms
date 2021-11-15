package com.darren1112.dptms.common.core.util;

import org.springframework.util.AntPathMatcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author luyuhao
 * @since 19/12/14 21:44
 */
public class UrlUtil {

    private static AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 判断path是否在需要校验patterns中
     * 若patterns为空，则默认为false
     *
     * @param path     访问path
     * @param patterns 待校验patterns
     * @return true:是; false:否
     */
    public static boolean matchUri(String path, String... patterns) {
        if (CollectionUtil.isEmpty(patterns)) {
            return false;
        }
        for (String pattern : patterns) {
            if (matcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断path是否在需要校验patterns中
     * 若patterns为空，则默认为false
     *
     * @param path     访问path
     * @param patterns 待校验patterns
     * @return true:是; false:否
     */
    public static boolean matchUri(String path, List<String> patterns) {
        if (CollectionUtil.isEmpty(patterns)) {
            return false;
        }
        for (String pattern : patterns) {
            if (matcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    public static String getPath(String url) {
        String path;
        try {
            URL parseUrl = new URL(url);
            path = parseUrl.getPath();
        } catch (MalformedURLException e) {
            path = "";
        }
        return path;
    }
}
