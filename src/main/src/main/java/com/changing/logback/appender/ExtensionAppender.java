package com.changing.logback.appender;

import com.changing.logback.builder.BuilderStrategy;
import com.changing.logback.transfer.TransferStrategy;
import com.changing.model.Message;

/**
 * @Description : ExtensionAppender接口
 * @Author : wuchangqing
 * @Date : 2017/9/23
 */
public class ExtensionAppender extends AbstractExtensionAppender {

    private BuilderStrategy builderStrategy;

    private TransferStrategy transferStrategy;

    @Override
    public void start() {
        transferStrategy = builderStrategy.build();
        if (transferStrategy != null) {
            transferStrategy.connect();
        }
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        if (transferStrategy != null) {
            transferStrategy.close();
        }
    }


    @Override
    public void doTransfe(Message message) {
        //发送数据
        transferStrategy.writeData(message);
    }


    public BuilderStrategy getBuilderStrategy() {
        return builderStrategy;
    }

    public void setBuilderStrategy(BuilderStrategy builderStrategy) {
        this.builderStrategy = builderStrategy;
    }
}
