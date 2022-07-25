package com.zy.springframework.core.io;

/**
 * @author zy
 * @since 2022/7/23  23:18
 */
public interface ResourceLoader {
    /**
     * Pseudo URL prefix for loading from the class path : "classpath:"
     * */
    String CLASSPATH_URL_PREFIX = "classpath:";
    Resource getResource(String location) ;
}
