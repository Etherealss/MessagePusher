package cn.wtk.mp.common.base.utils;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @date 2023-01-11
 */
public class UrlUtil {

    /**
     * 获取请求地址中的某个参数
     * @param url
     * @param name
     * @return
     */
    public static String getUrlParameter(String url, String name) {
        return getUrlParameters(url).get(name);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param url url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        String[] arrSplit;
        url = url.trim().toLowerCase();
        arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 将参数存入map集合
     * @param url  url地址
     * @return url请求参数部分存入map集合
     */
    public static Map<String, String> getUrlParameters(String url) {
        Map<String, String> mapRequest = new HashMap<>(4);
        String strUrlParam = truncateUrlPage(url);
        if (!StringUtils.hasText(strUrlParam)) {
            return mapRequest;
        }
        String[] paramPairs = strUrlParam.split("&");
        for (String paramPair : paramPairs) {
            String[] kv = paramPair.split("=");
            if (kv.length > 1) {
                mapRequest.put(kv[0], kv[1]);
            } else if (StringUtils.hasText(kv[0])) {
                // 参数值为空
                mapRequest.put(kv[0], "");
            }
        }
        return mapRequest;
    }


}