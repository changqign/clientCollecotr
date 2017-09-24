package com.changing.logback.transfer;

import com.changing.model.Message;

/**
 * @Description : 日志转移策略接口
 * @Author : wuchangqing
 * @Date : 2017/9/22
 */
public interface TransferStrategy {

    void connect();

    /**
     * 发送数据
     */
    void writeData(Message message);

    /**
     * 关闭操作
     */
    void close();
}
