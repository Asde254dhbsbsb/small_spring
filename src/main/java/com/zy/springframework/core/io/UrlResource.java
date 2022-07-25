package com.zy.springframework.core.io;

import cn.hutool.core.convert.impl.URLConverter;
import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author zy
 * @since 2022/7/23  22:47
 */

/**
 * 通过HTTP的方式读取云服务的文件-----可以把配置文件放在GitHub或者Gitee上
 * */
public class UrlResource implements Resource{

    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        } catch (IOException e) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect(); // 如果是http就关闭连接！
            }
            throw e;
        }
    }
}
