package com.changing.parser;

import com.changing.model.StructLog;
import com.changing.util.NetUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 默认的日志解析类
 * @Author : wuchangqing
 * @Date : 2017/9/22
 */
public class DefaultLogParser implements Parsable {

    private static final String REGEX = "(?<dateTime>\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3})\\s+\\[(?<threadName>.+)\\]\\s+(?<level>[A-Z]+)\\s+(?<loggerName>(([a-z]\\.)+[a-zA-Z]+))\\s+-\\s+(?<data>\\{[\\W\\w]+\\})";

    private  Pattern pattern = Pattern.compile(REGEX);

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogParser.class);

    @Override
    public List<StructLog> parseQuietly(List<String> logs) {
        //%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5p %c - %m%n
        List<StructLog> structLogs = Lists.newArrayList();
        for (String logLine : logs) {
            try {
                Matcher matcher = pattern.matcher(logLine);
                //如果格式不匹配直接不会进入
                if (matcher.find()) {
                    StructLog structLog = new StructLog();
                    //时间转换
                    long timeSpan = 0L;
                    String dateTime = matcher.group("dateTime");
                    structLog.timeStamp(timeSpan).threadName(matcher.group("threadName")).level(matcher.group("level")).LoggerName(matcher.group("loggerName")).data(matcher.group("data")).host(NetUtil.getMyIPLocal());
                    structLogs.add(structLog);
                }
            } catch (Exception e) {
                LOGGER.error("parseQuietly error", e);
            }
        }
        return structLogs;
    }
}
