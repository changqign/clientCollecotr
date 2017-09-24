package com.changing.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * @Description : 日志监听器启动，需要配置
 * @Author : wuchangqing
 * @Date : 2017/9/22
 */
public class LogMonitorInitalizer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMonitorInitalizer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //TODO 如果连不上怎么办？需要额外考虑重连等情况；或网络不稳定的时候如何处理？

        //通过读取客户端配置的路径进行读取
        try {
            //进行加载config-init.properties文件
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("config-init.properties"));
            String logLocation = properties.getProperty("logLocation");
            String collectorHost = properties.getProperty("collectorHost");
            String collectorPort = properties.getProperty("collectorPort");
           /* LogFileMonitor logFileMonitor = new LogFileMonitor( 5 * 1000);
            try {
                logFileMonitor.start();
                TransferStrategy transferStrategy = new SocketTransferStrategy(collectorHost, Integer.parseInt(collectorPort));
                FileAlterationListener listener = new LogFileListener(transferStrategy);
                logFileMonitor.monitor(logLocation,listener);
            } catch (Exception e) {
                LOGGER.error("initialize error", e);
            }*/
        } catch (IOException e) {
            rethrowRuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
