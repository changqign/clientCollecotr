package com.changing.logback.builder;

import ch.qos.logback.core.spi.ContextAwareBase;
import com.changing.logback.transfer.TransferStrategy;

/**
 * @Description : 读取logback的配置，并进行
 * @Author : wuchangqing
 * @Date : 2017/9/23
 */
public abstract class BuilderStrategy extends ContextAwareBase {

    /**
     * 消息传送者
     */
    protected static TransferStrategy transfer;

    /**
     * 获取传送者
     *
     * @return
     */
    public static TransferStrategy transfer() {
        return transfer;
    }

    /**
     * 构建 {@link TransferStrategy}
     *
     * @return TransferStrategy
     */
    public abstract TransferStrategy build();

}
