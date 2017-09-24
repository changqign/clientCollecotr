package com.changing.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.changing.model.Message;
import com.changing.model.StructLog;
import com.changing.util.NetUtil;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description : 可扩展的Appender，其中提供些模板方法
 * @Author : wuchangqing
 * @Date : 2017/9/23
 */
public abstract class AbstractExtensionAppender  extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final int BULK_SIZE = 4000;

    /**
     * 避免内存激增
     */
    private static final int MAX_QUEUE_SIZE = 12000;

    private LinkedBlockingQueue<StructLog> structLogs = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    @Override
    protected void append(ILoggingEvent event) {
        //如果达到一定量会发送
        Message message = appendToMessage(event);
        if(message != null){
            doTransfe(message);
        }
    }

    public abstract void doTransfe(Message message);

    /**
     * 转换为StructLog并达到一定量之后进行
     * @param event
     * @return
     */
    protected  Message appendToMessage(ILoggingEvent event){
        try {
            structLogs.put(convertToLog(event));
        } catch (InterruptedException e) {
        }
        //保证线程安全
        if(structLogs.size() > BULK_SIZE){
            synchronized (this){
                if(structLogs.size() > BULK_SIZE){
                    List<StructLog> copy =  Lists.newLinkedList();
                    structLogs.drainTo(copy,BULK_SIZE);
                    return new Message(copy);
                }
            }
        }
        return null;
    }

    protected StructLog convertToLog(ILoggingEvent event){
        StructLog structLog = new StructLog();
        //TODO 异常是否还需要额外处理呢？
//        StringBuilder stringBuilder = new StringBuilder();
//        IThrowableProxy tp = event.getThrowableProxy();
        structLog.LoggerName(event.getLoggerName()).threadName(event.getThreadName()).host(NetUtil.getMyIPLocal()).level(event.getLevel().toString()).data(event.getFormattedMessage()).timeStamp(event.getTimeStamp());
        return structLog;
    }


}
