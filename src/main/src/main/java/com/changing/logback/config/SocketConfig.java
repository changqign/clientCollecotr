package com.changing.logback.config;

/**
 * @Description : socket连接的基本信息
 * @Author : wuchangqing
 * @Date : 2017/9/23
 */
public class SocketConfig {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
