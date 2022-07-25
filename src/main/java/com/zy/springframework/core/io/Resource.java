package com.zy.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zy
 * @since 2022/7/23  22:47
 */
/**
 * 获取资源输出流的接口---各种类型的输出流来继承它
 * */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
