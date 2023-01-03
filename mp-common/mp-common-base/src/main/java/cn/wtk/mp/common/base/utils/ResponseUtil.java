package cn.wtk.mp.common.base.utils;


import cn.wtk.mp.common.base.pojo.Msg;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wtk
 * @description 向前端发送响应数据
 */
public class ResponseUtil {

    public static <T> void send(HttpServletResponse response, Msg<T> msg) throws IOException {
        //发送给客户端
        send(response, JsonUtil.toJsonString(msg));
    }

    public static void send(HttpServletResponse response, String jsonString) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
