package com.changing.logback.builder;

import com.changing.logback.config.SocketConfig;
import com.changing.logback.transfer.SocketTransferStrategy;
import com.changing.logback.transfer.TransferStrategy;

/**
 * @Description :
 * @Author : wuchangqing
 * @Date : 2017/9/23
 */
public class SocketBuilderStrategy extends BuilderStrategy {

    private SocketConfig config;

    @Override
    public TransferStrategy build() {
        TransferStrategy transferStrategy = new SocketTransferStrategy(config);
        return transferStrategy;
    }

    public SocketConfig getConfig() {
        return config;
    }

    public void setConfig(SocketConfig config) {
        this.config = config;
    }
}
